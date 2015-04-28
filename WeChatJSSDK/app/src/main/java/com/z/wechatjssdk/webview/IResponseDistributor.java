package com.z.wechatjssdk.webview;

import com.z.wechatjssdk.webview.bean.Response;

/**
 * Created by Administrator on 15-4-22.
 * 分配界面响应，web页面执行完成后，会调用这个接口
 */
public interface IResponseDistributor {
    /**
     * 分配界面响应
     * @param response 处理结果 {@link com.z.wechatjssdk.webview.bean.Response}
     */
    public void distributeResponse(Response response);
}
