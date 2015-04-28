package com.z.wechatjssdk.webview;

import com.z.wechatjssdk.webview.bean.Request;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-22.
 * WebViewFragment{@link com.z.wechatjssdk.webview.fragment.WebViewFragment}调用，传递给Service层处理
 */
public interface IDelivery {
    /**
     * 传递请求事件给Service层处理
     * @param request 网络请求 {@link com.z.wechatjssdk.webview.bean.Request}
     */
    public void deliveryRequest(Request request);
}
