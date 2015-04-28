package com.z.wechatjssdk.webview.service;

import com.z.wechatjssdk.webview.bean.Request;

/**
 * Created by Administrator on 15-4-23.
 */
public interface IService {
    public void processRequest(Request request, IOnServiceFinish listener);
}
