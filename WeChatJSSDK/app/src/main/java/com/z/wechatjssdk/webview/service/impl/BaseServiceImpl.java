package com.z.wechatjssdk.webview.service.impl;

import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.bean.Response;
import com.z.wechatjssdk.webview.dao.DaoFactory;
import com.z.wechatjssdk.webview.dao.IDao;
import com.z.wechatjssdk.webview.service.IEventService;
import com.z.wechatjssdk.webview.service.IOnServiceFinish;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-23.
 */
public class BaseServiceImpl implements IEventService {
    @Override
    public void getResponse(Request request, IOnServiceFinish listener) {

        String interfaceNm=request.getInterfaceNm();

        IDao dao= DaoFactory.produceDao(interfaceNm);
        Response response=dao.getResponseJSON(request);

        listener.onServiceFinish(response);
    }
}
