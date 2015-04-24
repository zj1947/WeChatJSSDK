package com.z.wechatjssdk.webview.service.manager;

import com.z.wechatjssdk.webview.bean.Request;

/**
 * Created by Administrator on 15-4-22.
 */
public interface IServiceManager {
    public void getResponse(Request request,IOnServiceFinish listener);
}
