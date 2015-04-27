package com.z.wechatjssdk.webview.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 15-4-24.
 */
public class LocalImgId {
    private ArrayList<String> localIds;

    public LocalImgId() {
    }

    public LocalImgId(ArrayList<String> localIds) {
        this.localIds = localIds;
    }

    public ArrayList<String> getLocalIds() {
        return localIds;
    }

    public void setLocalIds(ArrayList<String> localIds) {
        this.localIds = localIds;
    }
}
