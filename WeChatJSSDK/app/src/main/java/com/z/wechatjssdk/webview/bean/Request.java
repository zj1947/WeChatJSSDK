package com.z.wechatjssdk.webview.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-22.
 */
public class Request {

    private int queueIndex;//web回调函数的数组下标
    private String interfaceNm;//接口名
    private JSONObject requestJSON;//请求参数
    private Object tag;//外带参数

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

    public Object getTag() {
        return tag;
    }

    public void setTag(Object t) {
        this.tag = t;
    }
}
