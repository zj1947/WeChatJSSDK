# WeChatJSSDK
webview与web页面交互，参考微信JS-SDK接口调用方式，支持异步回调。
###功能
实现Android 中的webview与web端的交互，演示了隐藏显示菜单、显示气泡、选图、定位这几个功能。
###代码一些说明
web与webview之间的交互采用这个项目（[Safe Java-JS WebView Bridge](https://github.com/pedant/safe-java-js-webview-bridge)）提供的交互框架，具体原理可查看原文说明。</br>
在web端的实现，参考[微信JS-SDK](http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html)的调用方式。
###调用过程
以显示菜单为例：
####在web端
```html
  <button onclick="wx.showOptionMenu()">显示右上角菜单</button>
```
其中wx.showOptionMenu()，是jssdk.js中的对象函数。而jssdk.JS文件有点类似于微信的[jweixin-1.0.0.js](http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E6.AD.A5.E9.AA.A4.E4.BA.8C.EF.BC.9A.E5.BC.95.E5.85.A5JS.E6.96.87.E4.BB.B6)文件，提供了统一访问java的接口。
#####关于jsssk.js
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
#####关于websiteReq函数。
```js
  //interfaceNm：接口名,parameter：请求参数，JSON格式,a：提供回调函数
    function websiteReq(interfaceNm,parameter,a){
         WxJSBridge.websiteReq(interfaceNm,parameter,function(result){clientReturn(interfaceNm,result,a);})
    }
```
WxJSBridge.websiteReq是通过JAVA端注入的JS函数，通过这个函数访问java端。<br>
在java端执行完相关的业务处理后，会调用function(result),并执行clientReturn(interfaceNm,result,a)<br>
#####clientReturn(interfaceNm,result,a)
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
####在java端
web与webview之间的交互采用这个项目的交互框架（[Safe Java-JS WebView Bridge](https://github.com/pedant/safe-java-js-webview-bridge)），具体原理可查看原文说明。
<br>
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
                eventDistributor.distributorReqAction(webView,interfaceNm, joParams, jsCallBackIndex);

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
            reqWatcher = (RequestWatcher)webView.getTag();
        } catch (ClassCastException e) {
            throw new WebException(e.getMessage());
        }

        if (null== reqWatcher){
            throw new WebException("webview need to bound RequestWatcher");
        }
        //把请求参数封装到一个请求对象
        Request request=new Request(interfaceNm,jo,index);
        //转发web请求事件
        reqWatcher.webReqEvent(request);
    }
```
程序中主要是通过reqWatcher = (RequestWatcher)webView.getTag()获取请求监听接口。而在初始化webview时绑定了RequestWatcher接口。<br>
#####关于webviewFragment
在webviewFragment中，包含webview控件，实现RequestWatcher接口和IFragmentView接口。<br>
<br>
RequestWatcher接口里定义了webReqEvent这个接口函数，当有web端调用JS SDK ，会执行此接口。<br>
IFragmentView接口定义了执行view更新的操作，这里是setFragmentMenuVisibility(boolean)方法，显示或隐藏菜单。<br>
关于DeliveryManager类，下面会谈到。
```JAVA
public class WebViewFragment extends Fragment implements RequestWatcher,IFragmentView {

    private WebView mWebView;
    private DeliveryManager deliveryManager;

    private void initData() {
    
        ...
        
        deliveryManager = new DeliveryManager(this, mWebView);
        //绑定事件处理类
        mWebView.setTag(this);
    }

    /**
     * 当有web端调用JS SDK ，此接口会执行
     * @param request 网络请求 {@link com.z.wechatjssdk.webview.bean.Request}
     */
    @Override
    public void webReqEvent(Request request) {

        //传递请求，处理请求
        deliveryManager.deliveryRequest(request);

    }
    
    /**
     * 显示或隐藏右上角菜单
     * @param isVisibility true为显示，false为隐藏
     */
    @Override
    public void setFragmentMenuVisibility(boolean isVisibility) {
        setMenuVisibility(isVisibility);
    }

}
```
#####关于DeliveryManager
在DeliveryManager类中，实现两个接口 IDelivery, IOnServiceFinish。<br>
<br>
接口IDelivery把请求事件传递给Service层处理，定义deliveryRequest(Request)方法<br>
IOnServiceFinish是处理web请求后的回调接口，定义onServiceFinish(Response)方法<br>
```JAVA
/**
 * 请求传递管理，主要是传递request给Service层处理
 * 以及处理结束后回调IOnServiceFinish返回并处理js回调，将处理结果返回给web端，并分配结果给IResponseDistributor
 * 刷新Android端界面
 */
public class DeliveryManager implements IDelivery, IOnServiceFinish {

    /**
     * 界面响应分配，根据执行结果回调界面响应接口
     */
    private IResponseDistributor responseDistributorImpl;

    private WebView webView;

    public DeliveryManager(IFragmentView iFragmentView, WebView webView) {
        responseDistributorImpl = new ResponseDistributorImpl(iFragmentView);
        this.webView = webView;
    }


    /**
     *
     * @param request 网络请求 {@link com.z.wechatjssdk.webview.bean.Request}
     */
    @Override
    public void deliveryRequest(Request request) {
        String interfaceNm = request.getInterfaceNm();
        //使用抽象工厂方法获取具体的Service处理层
        IService service = ServiceFactory.produceService(interfaceNm);
        //处理请求，处理结束回调onServiceFinish 
        service.processRequest(request, this);
    }

    /**
     * @see #deliveryRequest(Request) 处理结束回调
     * @param response 处理结果 {@link com.z.wechatjssdk.webview.bean.Response}
     */
    @Override
    public void onServiceFinish(final Response response) {

        //js回调，相当于执行WxJSBridge.websiteReq中的function(result)
        final JsCallback jsCallBack = new JsCallback(
                webView,
                WebInterfaceContents.INJECTED_NAME,
                response.getQueueIndex());
        try {
            jsCallBack.apply(response.getResponseJSON());
        } catch (JsCallback.JsCallbackException e) {
            e.printStackTrace();
        }
        //界面刷新分配
        responseDistributorImpl.distributeResponse(response);

    }


}
```
#####关于ResponseDistributorImpl
ResponseDistributorImpl实现IResponseDistributor接口<br>
<br>
IResponseDistributor接口分配界面响应，web页面执行完成后，会调用这个接口，定义distributeResponse(Response)方法
```JAVA
public class ResponseDistributorImpl implements IResponseDistributor{

    private IFragmentView iFragmentView;//界面操作接口，webviewFragment中实现它，在定义DeliveryManager传入
    
    public ResponseDistributorImpl(IFragmentView iFragmentView) {
        this.iFragmentView = iFragmentView;
    }

    /**
     * 根据执行结果，执行界面操作
     * @param response
     */
    @Override
    public void distributeResponse(Response response) {

        String interfaceNm=response.getInterfaceNm();
        if (WebInterfaceContents.INTERFACE_NM_HIDE_OPTION_MENU.equals(interfaceNm)){
//            隐藏右上角菜单
            iFragmentView.setFragmentMenuVisibility(false);
        }else if (WebInterfaceContents.INTERFACE_NM_SHOW_OPTION_MENU.equals(interfaceNm)){
//            显示右上角菜单
            iFragmentView.setFragmentMenuVisibility(true);
        }
    }

}
```
#####关于关于业务逻辑的处理
IService接口
```JAVA
/**
 * 具体的业务处理
 * 处理请求，回调传递结果
 */
public interface IService {
    /**
     * 处理请求，并回调结果
     * @param request 网络请求 {@link com.z.wechatjssdk.webview.bean.Request}
     * @param listener 结果回调 {@link com.z.wechatjssdk.webview.service.IOnServiceFinish}
     */
    public void processRequest(Request request, IOnServiceFinish listener);
}
```
关于ServiceFactory
```JAVA
/**
 * 服务工厂，返回具体服务
 */
public class ServiceFactory {

    /**
     * 根据接口名返回具体的服务层
     * @param interfaceNm 接口名
     * @return 服务层，具体的业务逻辑处理
     */
    public static IService produceService(String interfaceNm){

       if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_HIDE_OPTION_MENU)) {
            return new BaseService();
        }else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_SHOW_OPTION_MENU)) {
            return new BaseService();
        } else {
            return new InterfaceNotFoundServiceImpl();
        }
    }
}
```
关于BaseService，什么也没做。
```JAVA
public class BaseService extends BaseServiceImpl {
    @Override
    public void parserReqJSON(JSONObject jsonObject) throws JSONException {

    }

    @Override
    public void setResultJSON() throws JSONException {

    }
}
```
关于BaseServiceImpl，采用模板方法设计模式，定义了两个模板方法:<br>
<br>
parserReqJSON(JSONObject)：解析请求参数<br>
setResultJSON():设置返回的JSON值。
```JAVA
/**
 * 通用的Service层框架
 */
abstract class BaseServiceImpl implements IService {

    protected JSONObject mJoResult;
    protected String mInterfaceNm;
    protected Response mResponse;
    protected Request mRequest;

    public BaseServiceImpl() {
        mJoResult = new JSONObject();
    }

    @Override
    public void processRequest(final Request request, final IOnServiceFinish listener) {

        mRequest = request;
        int queueIndex = request.getQueueIndex();
        mInterfaceNm = request.getInterfaceNm();
        mResponse = new Response(mInterfaceNm, mJoResult, queueIndex);

        new Thread() {
            @Override
            public void run() {

                try {
                    //解析请求
                    parserReqJSON(request.getRequestJSON());
                    //设置处理结果
                    setResultJSON();
                } catch (JSONException j) {
                    j.printStackTrace();

                    try {
                        mJoResult.put(WebInterfaceContents.ERR_MSG, j.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                //获取主线程的handler
                Handler handler = new Handler(Looper.getMainLooper());
                //在主线程中处理
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //处理结束回调
                        listener.onServiceFinish(mResponse);
                    }
                });
            }
        }.start();

    }

    /**
     * 返回成功
     *
     * @throws JSONException
     */
    protected void setOkResult() throws JSONException {
        mJoResult.put(WebInterfaceContents.ERR_MSG, mInterfaceNm + ":ok");
    }

    /**
     * 解析请求JSON
     *
     * @param jsonObject 请求的JSON
     * @throws JSONException
     */
    public abstract void parserReqJSON(JSONObject jsonObject) throws JSONException;

    /**
     * 设置返回的JSON结果值
     *
     * @throws JSONException
     */
    public abstract void setResultJSON() throws JSONException;
}
```

以上就是webview与web端交互大概的思路和处理流程了。








