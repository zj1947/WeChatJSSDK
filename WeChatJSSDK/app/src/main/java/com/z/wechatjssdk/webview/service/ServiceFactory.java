package com.z.wechatjssdk.webview.service;

import com.z.wechatjssdk.webview.WebInterfaceContents;
import com.z.wechatjssdk.webview.service.impl.ToastServiceImpl;

/**
 * Created by Administrator on 15-4-22.
 */
public class ServiceFactory {

    public static IEventService productService(String interfaceNm) {

        if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_GET_LOCATION)) {

        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_PRE_IMG)) {

        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_UPLOAD_IMG)) {

        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_CHOOSE_IMG)) {

        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_GO_BACK)) {

        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_ALERT)) {

        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_TOAST)) {
//            return new ToastServiceImpl();
        } else {
        }
        return null;
    }
}
