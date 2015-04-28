package com.z.wechatjssdk.webview;

import com.z.wechatjssdk.webview.bean.Request;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-22.
 * 当有web端调用js接口时，会调用此接口，通过此接口来通知 WebViewFragment{@link com.z.wechatjssdk.webview.fragment.WebViewFragment}
 * 以此执行java端的具体处理
 */
public interface RequestWatcher {
    /**
     * 网络请求事件监听
     * @param request 网络请求 {@link com.z.wechatjssdk.webview.bean.Request}
     */
    public void webReqEvent(Request request);
}
