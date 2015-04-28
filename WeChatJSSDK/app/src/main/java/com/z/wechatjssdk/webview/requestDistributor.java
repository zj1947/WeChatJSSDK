package com.z.wechatjssdk.webview;

import android.webkit.WebView;

import com.z.wechatjssdk.webview.bean.Request;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-3-10.
 */
public class requestDistributor {

    private RequestWatcher reqWatcher;

    public requestDistributor() {
    }

    /**
     * 转发web请求事件到WebViewFragment{@link com.z.wechatjssdk.webview.fragment.WebViewFragment}
     * @param webView
     * @param interfaceNm
     * @param jo
     * @param index
     * @throws WebException
     */
    public void distributorReqAction(WebView webView,String interfaceNm, final JSONObject jo, int index) throws WebException{

        try {
            //获取请求监听接口
            reqWatcher = (RequestWatcher)webView.getTag();
        } catch (ClassCastException e) {
            throw new WebException(e.getMessage());
        }

        if (null== reqWatcher){
            throw new WebException("webview need to bound RequestWatcher");
        }

        Request request=new Request(interfaceNm,jo,index);
        //转发web请求事件
        reqWatcher.webReqEvent(request);
    }
}
