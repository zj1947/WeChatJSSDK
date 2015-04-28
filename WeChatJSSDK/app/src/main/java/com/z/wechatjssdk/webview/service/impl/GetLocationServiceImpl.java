package com.z.wechatjssdk.webview.service.impl;

import com.z.wechatjssdk.mock.MockGetLocationUtil;
import com.z.wechatjssdk.mock.OnGetLocationListener;
import com.z.wechatjssdk.webview.WebInterfaceContents;
import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.bean.Response;
import com.z.wechatjssdk.webview.bean.event.Location;
import com.z.wechatjssdk.webview.service.IOnServiceFinish;
import com.z.wechatjssdk.webview.service.IService;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 15-4-27.
 */
public class GetLocationServiceImpl implements IService,OnGetLocationListener {

    private IOnServiceFinish listener;
    private String mInterfaceNm;
    private JSONObject mJoResult;
    protected Response mResponse;

    public GetLocationServiceImpl() {
        mJoResult =new JSONObject();

    }

    @Override
    public void processRequest(Request request, IOnServiceFinish listener) {
        this.listener=listener;
        mInterfaceNm=request.getInterfaceNm();
        mResponse=new Response(mInterfaceNm,mJoResult,request.getQueueIndex());

        MockGetLocationUtil locationUtil=new MockGetLocationUtil();
        locationUtil.getLocation(this);
    }

    @Override
    public void onError(String msg) {
        try {
            mJoResult.put(WebInterfaceContents.ERR_MSG, msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listener.onServiceFinish(mResponse);
    }

    @Override
    public void onGetLocation(Location location) {

        try {
            mJoResult.put("accuracy", location.getAccuracy());
            mJoResult.put("latitude", location.getLatitude());
            mJoResult.put("longitude", location.getLongitude());
            mJoResult.put("speed", location.getSpeed());
            mJoResult.put(WebInterfaceContents.ERR_MSG, mInterfaceNm + ":ok");
        } catch (JSONException e) {
            e.printStackTrace();

            try {
                mJoResult.put(WebInterfaceContents.ERR_MSG, e.getMessage());
            } catch (JSONException j) {
                j.printStackTrace();
            }
        }
        listener.onServiceFinish(mResponse);
    }

}
