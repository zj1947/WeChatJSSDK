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
 * 通用的Service层框架
 */
abstract class BaseServiceImpl implements IService {

    protected JSONObject mJoResult;
    protected String mInterfaceNm;
    protected Response mResponse;
    protected Request mRequest;

    public BaseServiceImpl() {
        mJoResult = new JSONObject();
    }

    @Override
    public void processRequest(final Request request, final IOnServiceFinish listener) {

        mRequest = request;
        int queueIndex = request.getQueueIndex();
        mInterfaceNm = request.getInterfaceNm();
        mResponse = new Response(mInterfaceNm, mJoResult, queueIndex);

        new Thread() {
            @Override
            public void run() {

                try {
                    //解析请求
                    parserReqJSON(request.getRequestJSON());
                    //设置处理结果
                    setResultJSON();
                } catch (JSONException j) {
                    j.printStackTrace();

                    try {
                        mJoResult.put(WebInterfaceContents.ERR_MSG, j.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                //获取主线程的handler
                Handler handler = new Handler(Looper.getMainLooper());
                //在主线程中处理
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //处理结束回调
                        listener.onServiceFinish(mResponse);
                    }
                });
            }
        }.start();

    }

    /**
     * 返回成功
     *
     * @throws JSONException
     */
    protected void setOkResult() throws JSONException {
        mJoResult.put(WebInterfaceContents.ERR_MSG, mInterfaceNm + ":ok");
    }

    /**
     * 解析请求JSON
     *
     * @param jsonObject 请求的JSON
     * @throws JSONException
     */
    public abstract void parserReqJSON(JSONObject jsonObject) throws JSONException;

    /**
     * 设置返回的JSON结果值
     *
     * @throws JSONException
     */
    public abstract void setResultJSON() throws JSONException;
}
