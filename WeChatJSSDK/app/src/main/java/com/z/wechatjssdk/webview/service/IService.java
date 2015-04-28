package com.z.wechatjssdk.webview.service;

import com.z.wechatjssdk.webview.bean.Request;

/**
 * Created by Administrator on 15-4-23.
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
