package com.z.wechatjssdk.webview.service;

import com.z.wechatjssdk.webview.WebInterfaceContents;
import com.z.wechatjssdk.webview.service.impl.ChooseImageServiceImpl;
import com.z.wechatjssdk.webview.service.impl.GetLocationServiceImpl;
import com.z.wechatjssdk.webview.service.impl.GetNetworkTypeServiceImpl;
import com.z.wechatjssdk.webview.service.impl.ToastServiceImpl;

/**
 * Created by Administrator on 15-4-23.
 */
public class ServiceFactory {

    public static IService produceService(String interfaceNm){

        if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_GET_LOCATION)) {
            return new GetLocationServiceImpl();
        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_PRE_IMG)) {

        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_UPLOAD_IMG)) {

        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_CHOOSE_IMG)) {
            return new ChooseImageServiceImpl();
        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_GO_BACK)) {

        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_ALERT)) {

        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_TOAST)) {
            return new ToastServiceImpl();
        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_GET_NETWORK_TYPE)) {
            return new GetNetworkTypeServiceImpl();
        } else {
        }
        return null;
    }
}
