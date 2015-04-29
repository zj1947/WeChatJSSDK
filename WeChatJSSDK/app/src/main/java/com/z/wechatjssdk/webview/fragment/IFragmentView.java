package com.z.wechatjssdk.webview.fragment;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-22.
 * WebViewFragment{@link com.z.wechatjssdk.webview.fragment.WebViewFragment}的界面更新接口
 */
public interface IFragmentView {

    /**
     * 通知气泡
     * @param content 通知内容
     */
    public void toast(String content);

    /**
     * 显示或者隐藏菜单
     * @param isVisibility true为显示，false为隐藏
     */
    public void setFragmentMenuVisibility(boolean isVisibility);
}
