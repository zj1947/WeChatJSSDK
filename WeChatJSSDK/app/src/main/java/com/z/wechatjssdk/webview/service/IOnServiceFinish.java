package com.z.wechatjssdk.webview.service;

import com.z.wechatjssdk.webview.bean.Response;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-22.
 */
public interface IOnServiceFinish {
    /**
     * 处理web请求后的回调接口
     * @param response 处理结果 {@link com.z.wechatjssdk.webview.bean.Response}
     */
    public void onServiceFinish(Response response);
}
