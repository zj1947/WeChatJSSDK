package com.z.wechatjssdk.webview.bean.event;

import java.util.ArrayList;

/**
 * Created by Administrator on 15-4-24.
 */
public class LocalImgId {
    private ArrayList<String> localIds;//图片路径

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
