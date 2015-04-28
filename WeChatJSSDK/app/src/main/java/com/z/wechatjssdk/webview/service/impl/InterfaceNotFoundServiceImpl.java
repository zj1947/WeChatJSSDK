package com.z.wechatjssdk.webview.service.impl;

import com.z.wechatjssdk.webview.WebInterfaceContents;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-28.
 * 接口名未匹配处理
 */
public class InterfaceNotFoundServiceImpl extends BaseServiceImpl {
    @Override
    public void parserReqJSON(JSONObject jsonObject) throws JSONException {

    }

    @Override
    public void setResultJSON() throws JSONException {
        mJoResult.put(WebInterfaceContents.ERR_MSG,"接口"+mInterfaceNm+"不存在");
    }
}
