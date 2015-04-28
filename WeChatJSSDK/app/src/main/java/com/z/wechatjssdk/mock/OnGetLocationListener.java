package com.z.wechatjssdk.mock;

import com.z.wechatjssdk.webview.bean.event.Location;

/**
 * Created by Administrator on 15-4-28.
 */
public interface OnGetLocationListener {
    /**
     * 获取位置错误
     * @param msg 错误信息
     */
    public void onError(String msg);

    /**
     * 获取位置成功调用
     * @param location {@link com.z.wechatjssdk.webview.bean.event.Location}
     */
    public void onGetLocation(Location location);
}
