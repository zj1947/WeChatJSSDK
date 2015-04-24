package com.z.wechatjssdk.webview.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-22.
 */
public class Response<T>{

    private String interfaceNm;
    private JSONObject responseJSON;
    private T t;

    public Response(String interfaceNm, JSONObject responseJSON) {
        this.interfaceNm = interfaceNm;
        this.responseJSON = responseJSON;
    }

    public String getInterfaceNm() {
        return interfaceNm;
    }

    public void setInterfaceNm(String interfaceNm) {
        this.interfaceNm = interfaceNm;
    }

    public JSONObject getResponseJSON() {
        return responseJSON;
    }

    public void setResponseJSON(JSONObject responseJSON) {
        this.responseJSON = responseJSON;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
