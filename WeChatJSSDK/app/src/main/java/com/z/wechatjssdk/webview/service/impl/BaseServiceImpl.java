package com.z.wechatjssdk.webview.service.impl;

import android.os.Handler;
import android.os.Looper;

import com.z.wechatjssdk.webview.WebInterfaceContents;
import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.bean.Response;
import com.z.wechatjssdk.webview.service.IService;
import com.z.wechatjssdk.webview.service.IOnServiceFinish;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-23.
 */
abstract class BaseServiceImpl implements IService {

    protected JSONObject mJoResult;
    protected String mInterfaceNm;
    protected Response mResponse;
    protected Request mRequest;

    public BaseServiceImpl() {
        mJoResult=new JSONObject();
    }

    @Override
    public void processRequest(final Request request, final IOnServiceFinish listener) {

        mRequest=request;
        int queueIndex=request.getQueueIndex();
        mInterfaceNm=request.getInterfaceNm();
        mResponse =new Response(mInterfaceNm,mJoResult,queueIndex);

        new Thread(){
            @Override
            public void run(){

                try {
                    parserReqJSON(request.getRequestJSON());
                    setResultJSON();
                }catch (JSONException j){
                    j.printStackTrace();

                    try {
                        mJoResult.put(WebInterfaceContents.ERR_MSG, j.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                //获取主线程的handler
                Handler handler=new Handler(Looper.getMainLooper());
                //在主线程中处理
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (null!=listener)
                            listener.onServiceFinish(mResponse);
                    }
                });
            }
        }.start();

    }

    protected void setOkResult()throws JSONException{
        mJoResult.put(WebInterfaceContents.ERR_MSG, mInterfaceNm+":ok");
    }

    public Request getRequest() {
        return mRequest;
    }

    public abstract void parserReqJSON(JSONObject jsonObject) throws JSONException;
    public abstract void setResultJSON() throws JSONException;
}
