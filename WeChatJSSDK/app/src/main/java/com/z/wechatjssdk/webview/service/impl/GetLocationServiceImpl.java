package com.z.wechatjssdk.webview.service.impl;

import com.z.wechatjssdk.webview.WebInterfaceContents;
import com.z.wechatjssdk.webview.bean.event.Location;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-27.
 */
public class GetLocationServiceImpl extends BaseServiceImpl {

    @Override
    public void parserReqJSON(JSONObject jsonObject) throws JSONException {
    }

    @Override
    public void setResultJSON() throws JSONException {

        Location location = getLocation();
        mJoResult.put("latitude", location.getLatitude());
        mJoResult.put("longitude", location.getLongitude());
        mJoResult.put("speed", location.getSpeed());
        mJoResult.put("accuracy", location.getAccuracy());
        setOkResult();


    }

    public Location getLocation() {

        try {
            //设定获取位置需5s，模拟线程阻塞
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Location location = new Location(33.4f, 26.21f, 5.1f, 10);
        return location;
    }
}
