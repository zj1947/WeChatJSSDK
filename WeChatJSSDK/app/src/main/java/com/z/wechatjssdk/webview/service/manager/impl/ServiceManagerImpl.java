package com.z.wechatjssdk.webview.service.manager.impl;

import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.bean.Response;
import com.z.wechatjssdk.webview.service.ServiceFactory;
import com.z.wechatjssdk.webview.service.IService;
import com.z.wechatjssdk.webview.service.manager.IServiceManager;
import com.z.wechatjssdk.webview.service.manager.IOnServiceFinish;

/**
 * Created by Administrator on 15-4-23.
 */
public class ServiceManagerImpl implements IServiceManager {
    @Override
    public void getResponse(Request request, IOnServiceFinish listener) {

        String interfaceNm=request.getInterfaceNm();

        IService service= ServiceFactory.produceService(interfaceNm);
        Response response=service.getResponseJSON(request);

        listener.onServiceFinish(response);
    }
}
