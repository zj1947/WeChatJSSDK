package com.z.wechatjssdk.webview;

import android.webkit.WebView;

import org.json.JSONObject;

/**
 * Created by Administrator on 15-3-10.
 */
public class EventDistributor {
    private static final String ERR_MSG="errMsg";

    private EventWatcher eventWatcher;

    public EventDistributor() {
    }

    public void distributorReqAction(WebView webView,String interfaceNm, final JSONObject jo, int index){

         eventWatcher = (EventWatcher)webView.getTag();

        if (null==eventWatcher){
            eventWatcher.error("(EventWatcher)webView.getTag() is null");
            return;
        }

        if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_GET_LOCATION)){
                eventWatcher.getLocation(jo);
            }else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_PRE_IMG)){
                eventWatcher.previewImage(jo);
            }else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_UPLOAD_IMG)){
                eventWatcher.uploadImage(jo);
            }else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_CHOOSE_IMG)){
                eventWatcher.chooseImage(jo);
            }else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_GO_BACK)){
                eventWatcher.goBack(jo);
            }else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_ALERT)){
                eventWatcher.alert(jo);
            }else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_TOAST)){
                  eventWatcher.toast(jo);
            }else {
            }

    }
}
