package com.z.wechatjssdk.webview.service;

import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.bean.Response;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-23.
 */
public interface IService {
    public Response getResponseJSON(Request request);
}
