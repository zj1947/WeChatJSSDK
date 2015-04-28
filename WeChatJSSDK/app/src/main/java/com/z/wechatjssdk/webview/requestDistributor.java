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

    public void distributorReqAction(WebView webView,String interfaceNm, final JSONObject jo, int index) throws WebException{

         reqWatcher = (RequestWatcher)webView.getTag();

        if (null== reqWatcher){
            throw new WebException("webview need to bound EventWatcher");
        }
        Request request=new Request(interfaceNm,jo,index);
        reqWatcher.webReqEvent(request);
    }
}
