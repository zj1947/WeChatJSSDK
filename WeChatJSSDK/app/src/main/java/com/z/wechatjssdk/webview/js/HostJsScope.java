/**
 * Summary: js脚本所能执行的函数空间
 * Version 1.0
 * Date: 13-11-20
 * Time: 下午4:40
 * Copyright: Copyright (c) 2013
 */

package com.z.wechatjssdk.webview.js;

import android.webkit.WebView;

import org.json.JSONObject;


//HostJsScope中需要被JS调用的函数，必须定义成public static，且必须包含WebView这个参数
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