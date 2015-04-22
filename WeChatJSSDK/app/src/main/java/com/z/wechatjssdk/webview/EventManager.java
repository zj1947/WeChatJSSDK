package com.z.wechatjssdk.webview;

import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.bean.Response;
import com.z.wechatjssdk.webview.fragment.IFragmentView;
import com.z.wechatjssdk.webview.js.JsCallback;
import com.z.wechatjssdk.webview.service.IEventService;
import com.z.wechatjssdk.webview.service.IOnServiceFinish;
import com.z.wechatjssdk.webview.service.ServiceFactory;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 15-4-22.
 */
public class EventManager implements IPresenter,IOnServiceFinish {

    private IResponseDistributor iResponseDistributor;
    private HashMap<String,JSONObject> reqQueue;
    private JsCallback jsCallBack;

    public EventManager(IFragmentView iFragmentView,JsCallback jsCallBack) {
       iResponseDistributor=new ResponseDistributor(iFragmentView);
        reqQueue=new HashMap<>();
        this.jsCallBack=jsCallBack;
    }


    @Override
    public void processEvent(Request request) {
        reqQueue.put(request.getInterfaceNm(),null);
        IEventService eventService= ServiceFactory.productService(request.getInterfaceNm());
        eventService.getResponse(request,this);
    }

    @Override
    public void onServiceFinish(Response response) {

        String interfaceNm=response.getInterfaceNm();
        if (reqQueue.containsKey(interfaceNm)){
            reqQueue.remove(interfaceNm);
            try {
                jsCallBack.apply(response.getResponseJSON());
            } catch (JsCallback.JsCallbackException e) {
                e.printStackTrace();
            }
        }

        iResponseDistributor.distributeResponse(response);
    }
}
