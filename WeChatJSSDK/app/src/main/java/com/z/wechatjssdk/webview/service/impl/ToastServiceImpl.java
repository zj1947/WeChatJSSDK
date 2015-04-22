package com.z.wechatjssdk.webview.service.impl;

import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.bean.Response;
import com.z.wechatjssdk.webview.service.IEventService;
import com.z.wechatjssdk.webview.service.IOnServiceFinish;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-22.
 */
public class ToastServiceImpl implements IEventService {
    @Override
    public void getResponse(Request request, IOnServiceFinish listener) {
        Response response=new Response(request.getInterfaceNm(),request.getRequestJSON());
        listener.onServiceFinish(response);
    }
}
