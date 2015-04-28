package com.z.wechatjssdk.webview.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-22.
 */
public class Request {

    private int queueIndex;
    private String interfaceNm;
    private JSONObject requestJSON;
    private Object t;

    public Request(String interfaceNm, JSONObject requestJSON,int queueIndex) {
        this.interfaceNm = interfaceNm;
        this.requestJSON = requestJSON;
        this.queueIndex=queueIndex;
    }

    public String getInterfaceNm() {
        return interfaceNm;
    }

    public void setInterfaceNm(String interfaceNm) {
        this.interfaceNm = interfaceNm;
    }

    public JSONObject getRequestJSON() {
        return requestJSON;
    }

    public void setRequestJSON(JSONObject requestJSON) {
        this.requestJSON = requestJSON;
    }

    public int getQueueIndex() {
        return queueIndex;
    }

    public void setQueueIndex(int queueIndex) {
        this.queueIndex = queueIndex;
    }

    public Object getT() {
        return t;
    }

    public void setT(Object t) {
        this.t = t;
    }
}
