package com.z.wechatjssdk.webview;

import android.webkit.WebView;

import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.bean.Response;
import com.z.wechatjssdk.webview.fragment.IFragmentView;
import com.z.wechatjssdk.webview.js.JsCallback;
import com.z.wechatjssdk.webview.service.IService;
import com.z.wechatjssdk.webview.service.ServiceFactory;
import com.z.wechatjssdk.webview.service.IOnServiceFinish;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 15-4-22.
 */
public class EventManager implements IDelivery,IOnServiceFinish {

    private IResponseDistributor iResponseDistributor;
    private HashMap<String,JSONObject> reqQueue;
    private WebView webView;
    public EventManager(IFragmentView iFragmentView,WebView webView) {
        reqQueue=new HashMap<>();

       iResponseDistributor=new ResponseDistributor(iFragmentView);
        this.webView=webView;
    }


    @Override
    public void deliveryEvent(Request request) {
        String interfaceNm=request.getInterfaceNm();

        addQueue(interfaceNm);
        IService service= ServiceFactory.produceService(interfaceNm);
        service.processRequest(request, this);
    }

    @Override
    public void onServiceFinish(final Response response) {

        final JsCallback jsCallBack=new JsCallback(
                webView,
                WebInterfaceContents.INJECTED_NAME,
                response.getQueueIndex());
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

    public synchronized void addQueue(String interfaceNm){
        reqQueue.put(interfaceNm,null);
    }
}
