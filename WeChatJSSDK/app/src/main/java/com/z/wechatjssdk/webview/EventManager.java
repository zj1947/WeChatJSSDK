package com.z.wechatjssdk.webview;

import android.webkit.WebView;

import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.bean.Response;
import com.z.wechatjssdk.webview.fragment.IFragmentView;
import com.z.wechatjssdk.webview.js.JsCallback;
import com.z.wechatjssdk.webview.service.manager.IServiceManager;
import com.z.wechatjssdk.webview.service.manager.IOnServiceFinish;
import com.z.wechatjssdk.webview.service.manager.impl.ServiceManagerImpl;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 15-4-22.
 */
public class EventManager implements IPresenter,IOnServiceFinish {

    private IResponseDistributor iResponseDistributor;
    private HashMap<String,JSONObject> reqQueue;
    private WebView webView;
    public EventManager(IFragmentView iFragmentView,WebView webView) {
        reqQueue=new HashMap<>();

       iResponseDistributor=new ResponseDistributor(iFragmentView);
        this.webView=webView;
    }


    @Override
    public void processEvent(Request request) {
        reqQueue.put(request.getInterfaceNm(),null);
        IServiceManager eventService= new ServiceManagerImpl();
        eventService.getResponse(request,this);
    }

    @Override
    public void onServiceFinish(Response response) {

        final JsCallback jsCallBack=new JsCallback(webView, WebInterfaceContents.INJECTED_NAME);;
        jsCallBack.setPermanent(true);
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
