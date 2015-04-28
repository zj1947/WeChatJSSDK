package com.z.wechatjssdk.mock;

import com.z.wechatjssdk.webview.bean.event.Location;

/**
 * Created by Administrator on 15-4-28.
 */
public interface OnGetLocationListener {
    public void onError(String msg);
    public void onGetLocation(Location location);
}
