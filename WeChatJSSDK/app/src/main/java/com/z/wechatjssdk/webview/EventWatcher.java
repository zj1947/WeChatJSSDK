package com.z.wechatjssdk.webview;

import org.json.JSONObject;

/**
 * Created by Administrator on 14-11-6.
 */
public interface EventWatcher {

    public void getLocation(JSONObject jo);
    public void previewImage(JSONObject jo);
    public void uploadImage(JSONObject jo);
    public void chooseImage(JSONObject jo);
    public void goBack(JSONObject jo);
    public void alert(JSONObject jo);
    public void toast(JSONObject jo);
    public void jump(JSONObject jo);
    public void selectTime(JSONObject jo);
    public void error(String errorMsg);
}
