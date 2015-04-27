package com.z.wechatjssdk.webview.service.impl;

import com.z.wechatjssdk.webview.bean.LocalImgId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 15-4-24.
 */
public class ChooseImageServiceImpl extends BaseServiceImpl {

    LocalImgId localImgId;

    @Override
    public void parserReqJSON(JSONObject jsonObject) throws JSONException {
        localImgId=(LocalImgId)request.getT();
    }
    @Override
    public void setResultJSON() throws JSONException {
       mJoResult.put("localIds", null==localImgId?"":toJsonArray(localImgId.getLocalIds()));
        setOkResult();
    }
    public JSONArray toJsonArray(ArrayList<String> list) throws JSONException{

        JSONArray array = new JSONArray();
        if (null!=list){
            for (int i=0;i<list.size();i++){
                array.put(i,list.get(i));
            }
        }

        return array;
    }
}
