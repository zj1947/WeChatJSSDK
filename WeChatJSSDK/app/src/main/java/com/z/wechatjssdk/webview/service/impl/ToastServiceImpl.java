package com.z.wechatjssdk.webview.service.impl;

import android.text.TextUtils;

import com.z.wechatjssdk.webview.WebInterfaceContents;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-23.
 */
public class ToastServiceImpl extends BaseServiceImpl {
    private String strMsg;
    @Override
    public void setResultJSON() throws JSONException {
        if (TextUtils.isEmpty(strMsg)){
            mJoResult.put(WebInterfaceContents.ERR_MSG,"message is empty");
        }else {
            setOkResult();
            mResponse.setT(strMsg);
        }
    }

    @Override
    public void parserReqJSON(JSONObject jsonObject) throws JSONException {
        strMsg=jsonObject.getString("message");
    }
}
