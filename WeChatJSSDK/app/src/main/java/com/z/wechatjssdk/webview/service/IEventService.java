package com.z.wechatjssdk.webview.service;

import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.js.JsCallback;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-22.
 */
public interface IEventService {
    public void getResponse(Request request,IOnServiceFinish listener);
}
