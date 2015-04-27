package com.z.wechatjssdk.webview.service.impl;

import com.z.wechatjssdk.webview.WebInterfaceContents;
import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.bean.Response;
import com.z.wechatjssdk.webview.service.IService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 15-4-23.
 */
abstract class BaseServiceImpl<T>implements IService {

    protected JSONObject mJoResult;
    protected String mInterfaceNm;
    protected Response response;
    protected Request request;

    public BaseServiceImpl() {
        mJoResult=new JSONObject();
    }

    @Override
    public Response getResponseJSON(Request request) {

        this.request=request;
        int queueIndex=request.getQueueIndex();
        mInterfaceNm=request.getInterfaceNm();
        response=new Response<T>(mInterfaceNm,mJoResult,queueIndex);

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
        return response;
    }

    protected void setOkResult()throws JSONException{
        mJoResult.put(WebInterfaceContents.ERR_MSG, mInterfaceNm+":ok");
    }

    public abstract void parserReqJSON(JSONObject jsonObject) throws JSONException;
    public abstract void setResultJSON() throws JSONException;
}
