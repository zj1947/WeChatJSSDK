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

    public static void websiteReq(WebView webView,String interfaceNm,JSONObject jo,final JsCallback jsCallback){
    }

}