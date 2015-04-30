# WeChatJSSDK
微信JS-SDK接口之Android客户端
###功能
本项目主要是实现Android 中的webview与web端的交互，演示了隐藏显示菜单、显示气泡、选图、定位这几个功能。
###代码一些说明
本项目web与webview之间的交互采用这个项目的交互框架（[Safe Java-JS WebView Bridge](https://github.com/pedant/safe-java-js-webview-bridge)），具体原理可查看原文说明。<\br>
在web端，模仿[微信JS-SDK](http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E8.8E.B7.E5.8F.96.E7.BD.91.E7.BB.9C.E7.8A.B6.E6.80.81.E6.8E.A5.E5.8F.A3)的调用方式。
###调用过程
以显示菜单为例：
#####在web端
```html
  <button onclick="wx.showOptionMenu()">显示右上角菜单</button>
```
其中wx.showOptionMenu()，是jssdk.js中的对象函数。而jssdk.JS文件有点类似于微信的jweixin-1.0.0.js文件，提供了统一访问java的接口。
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
关于websiteReq函数。WxJSBridge.websiteReq是通过JAVA端拼接的JS函数，通过这个函数访问java端。
```js
  //interfaceNm：接口名,parameter：请求参数，JSON格式,a：提供回调函数
    function websiteReq(interfaceNm,parameter,a){
         WxJSBridge.websiteReq(interfaceNm,parameter,function(result){clientReturn(interfaceNm,result,a);})
    }
```
在java端执行完相关的业务处理后，会调用function(result),执行clientReturn(interfaceNm,result,a)<br>
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
```JAVA
```




