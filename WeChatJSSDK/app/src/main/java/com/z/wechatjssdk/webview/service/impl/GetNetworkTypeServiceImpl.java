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
 * 获取网络状态
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


    /**
     * 获取网络状态
     * @return wifi或手机信号、电信2G、其他网络类型
     */
    private String getNetworkType(){
        ConnectivityManager connectMgr = (ConnectivityManager) JssdkApp.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectMgr.getActiveNetworkInfo();

        if (null==info){
            return "无网络连接";
        }

        if (info.getType()==ConnectivityManager.TYPE_WIFI){
            return "wifi";
        }

        if (info.getType()==ConnectivityManager.TYPE_MOBILE){
            switch (info.getSubtype()){
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return "电信2G";
                default:return "手机信号";
            }
        }

        return "其他网络类型";
    }
}
