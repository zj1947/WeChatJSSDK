# WeChatJSSDK
微信JS-SDK接口之Android客户端
###功能
本项目主要是实现Android 中的webview与web端的交互，演示了隐藏显示菜单、显示气泡、选图、定位这几个功能。
###代码一些说明
本项目web与webview之间的交互采用这个项目的交互框架（[Safe Java-JS WebView Bridge](https://github.com/pedant/safe-java-js-webview-bridge)），具体原理可查看原文说明。</br>
在web端，模仿[微信JS-SDK](http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html)的调用方式。
###调用过程
以显示菜单为例：
#####在web端
```html
  <button onclick="wx.showOptionMenu()">显示右上角菜单</button>
```
其中wx.showOptionMenu()，是jssdk.js中的对象函数。而jssdk.JS文件有点类似于微信的[jweixin-1.0.0.js](http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E6.AD.A5.E9.AA.A4.E4.BA.8C.EF.BC.9A.E5.BC.95.E5.85.A5JS.E6.96.87.E4.BB.B6)文件，提供了统一访问java的接口。
######关于jsssk.js
wx.showOptionMenu()
```javascript
  //对象a提供了请求参数以及回调方法
   var wx={
        showOptionMenu:function(a){
            websiteReq('showOptionMenu',
                            {},
                             a);
        }
  }
```
关于websiteReq函数。WxJSBridge.websiteReq是通过JAVA端注入的JS函数，通过这个函数访问java端。<br>
在java端执行完相关的业务处理后，会调用function(result),执行clientReturn(interfaceNm,result,a)
```js
  //interfaceNm：接口名,parameter：请求参数，JSON格式,a：提供回调函数
    function websiteReq(interfaceNm,parameter,a){
         WxJSBridge.websiteReq(interfaceNm,parameter,function(result){clientReturn(interfaceNm,result,a);})
    }
```
在clientReturn函数中，参数result是返回结果，JSON格式，有一个通用属性errMsg，其值格式如下：

    调用成功时："xxx:ok" ，其中xxx为调用的接口名
    用户取消时："xxx:cancel"，其中xxx为调用的接口名
    调用失败时：其值为具体错误信息 

通过判断errMsg的属性值来回调a的回调函数。
```js
  //interfaceNm：接口名,result：返回结果，JSON格式,a：提供回调函数
  function clientReturn(interfaceNm,result,a){
       var jo=toJson(result);
        console.log("JSBridge clientReturn:"+JSON.stringify(jo));
       var responseStatus=(jo.errMsg).substring(interfaceNm.length+1);
        switch(responseStatus){
            case "ok":
                a.success&&a.success(jo);
                break;
            case "cancel":
                 a.cancel&&a.cancel(jo);
                break;
            default:a.fail&&a.fail(jo)
        };
    }
```
#####在java端
web与webview之间的交互采用这个项目的交互框架（[Safe Java-JS WebView Bridge](https://github.com/pedant/safe-java-js-webview-bridge)），具体原理可查看原文说明。</br>
定义websiteReq函数，对应js文件中的WxJSBridge.websiteReq
```JS
  WxJSBridge.websiteReq(interfaceNm,parameter,function(result){clientReturn(interfaceNm,result,a);})
```
```JAVA
public class HostJsScope {

    /**
     * @param webView 装载html的webview，必须包含WebView这个参数
     * @param interfaceNm 接口名 对应JS的interfaceNm
     * @param jo 请求参数 对应JS中的parameter
     * @param jsCallback  对应JS中的function(result)，执行结束会回调这个函数
     */
    public static void websiteReq(WebView webView,String interfaceNm,JSONObject jo,final JsCallback jsCallback){
    }

}
```
在页面调用java方法时，项目[Safe Java-JS WebView Bridge](https://github.com/pedant/safe-java-js-webview-bridge)使用call方法。
```JAVA
public String call(WebView webView, String jsonStr) {
  ...
  ...
}
```
但我这里改写了它的call方法，把请求转发处理，实现程序的低耦合
```JAVA

    public String functionReturnCall(WebView webView,String strJSON){
    
                JSONObject callJson = new JSONObject(strJSON);
                String methodName = callJson.getString("method");
                JSONArray argsTypes = callJson.getJSONArray("types");
                JSONArray argsVals = callJson.getJSONArray("args");
                
                /*
                  argsVals是参数数组，
                  对应WxJSBridge.websiteReq(
                    interfaceNm,
                    parameter,
                    function(result){clientReturn(interfaceNm,result,a);}
                  )
                */
                
                String interfaceNm= argsVals.getString(0);
                JSONObject joParams= argsVals.isNull(1) ? null : (argsVals.getJSONObject(1));
                int jsCallBackIndex=argsVals.getInt(2);//索引储存function(result)的下标
                
                //转发给webviewFragment处理
                ` eventDistributor.distributorReqAction(webView,interfaceNm, joParams, jsCallBackIndex);`

                return getReturn(strJSON, 200, "android client process request");
      
    }

```
在eventDistributor.distributorReqAction中
```JAVA
  /**
     * 转发web请求事件到WebViewFragment{@link com.z.wechatjssdk.webview.fragment.WebViewFragment}
     * @param webView
     * @param interfaceNm
     * @param jo
     * @param index
     * @throws WebException
     */
    public void distributorReqAction(WebView webView,String interfaceNm, final JSONObject jo, int index) throws WebException{

        try {
            //获取请求监听接口
            `reqWatcher = (RequestWatcher)webView.getTag();`
        } catch (ClassCastException e) {
            throw new WebException(e.getMessage());
        }

        if (null== reqWatcher){
            throw new WebException("webview need to bound RequestWatcher");
        }

        Request request=new Request(interfaceNm,jo,index);
        //转发web请求事件
        reqWatcher.webReqEvent(request);
    }
```
程序中主要是通过reqWatcher = (RequestWatcher)webView.getTag()获取请求监听接口。而在初始化webview时绑定了RequestWatcher接口。<br>
在webviewFragment中，包含webview控件，实现RequestWatcher接口。RequestWatcher接口只有webReqEvent这个接口函数，
```JAVA
public class WebViewFragment extends Fragment implements RequestWatcher {

    private WebView mWebView;

    private void initData() {
    
        ...
        
        //绑定事件处理类
        mWebView.setTag(this);
    }

    /**
     * 当有web端调用JS SDK ，此接口会执行
     * @param request 网络请求 {@link com.z.wechatjssdk.webview.bean.Request}
     */
    @Override
    public void webReqEvent(Request request) {

        String strInterfaceNm = request.getInterfaceNm();
        //图片选择接口特殊处理，需选择图片，再处理回调
        if (WebInterfaceContents.INTERFACE_NM_CHOOSE_IMG.equals(strInterfaceNm)) {
            chooseImg(request.getQueueIndex());
            return;
        }
        //传递请求，处理请求
        eventManager.deliveryRequest(request);

    }

}
```
