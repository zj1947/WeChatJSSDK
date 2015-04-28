package com.z.wechatjssdk.webview.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-22.
 */
public class Response{

    private int queueIndex;//回调数组下标
    private String interfaceNm;//接口名
    private JSONObject responseJSON; //返回给web端的JSON结果
    private Object tag;//额外参数

    public Response(String interfaceNm, JSONObject responseJSON,int queueIndex) {
        this.interfaceNm = interfaceNm;
        this.responseJSON = responseJSON;
        this.queueIndex=queueIndex;
    }
    public int getQueueIndex() {
        return queueIndex;
    }

    public void setQueueIndex(int queueIndex) {
        this.queueIndex = queueIndex;
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

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
