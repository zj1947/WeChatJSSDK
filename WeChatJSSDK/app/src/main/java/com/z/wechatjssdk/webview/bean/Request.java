package com.z.wechatjssdk.webview.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-22.
 */
public class Request {
    private String interfaceNm;
    private JSONObject requestJSON;

    public Request(String interfaceNm, JSONObject requestJSON) {
        this.interfaceNm = interfaceNm;
        this.requestJSON = requestJSON;
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
}
