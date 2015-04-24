package com.z.wechatjssdk.webview.service.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.z.wechatjssdk.app.JssdkApp;
import com.z.wechatjssdk.webview.WebInterfaceContents;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-23.
 */
public class GetNetworkTypeServiceImpl extends BaseServiceImpl {

    public GetNetworkTypeServiceImpl() {
        super();
    }

    @Override
    public void setResultJSON() throws JSONException {

        String strNetworkType=getNetworkType();
        if (TextUtils.isEmpty(strNetworkType)){
            mJoResult.put(WebInterfaceContents.ERR_MSG, "not network connected");
        }else {
            mJoResult.put("networkType", strNetworkType);
            setOkResult();
        }
    }

    @Override
    public void parserReqJSON(JSONObject jsonObject) throws JSONException {

    }


    private String getNetworkType(){
        ConnectivityManager connectMgr = (ConnectivityManager) JssdkApp.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectMgr.getActiveNetworkInfo();

        if (null==info){
            return null;
        }

        if (info.getType()==ConnectivityManager.TYPE_WIFI){
            return "wifi";
        }

        if (info.getType()==ConnectivityManager.TYPE_MOBILE){
            switch (info.getSubtype()){
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return "µçÐÅµÄ2G";
                default:return "mobile net work";
            }
        }

        return "other network type";
    }
}
