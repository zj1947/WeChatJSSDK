package com.z.wechatjssdk.webview.service;

import com.z.wechatjssdk.webview.WebInterfaceContents;
import com.z.wechatjssdk.webview.service.impl.BaseService;
import com.z.wechatjssdk.webview.service.impl.ChooseImageServiceImpl;
import com.z.wechatjssdk.webview.service.impl.GetLocationServiceImpl;
import com.z.wechatjssdk.webview.service.impl.GetNetworkTypeServiceImpl;
import com.z.wechatjssdk.webview.service.impl.InterfaceNotFoundServiceImpl;
import com.z.wechatjssdk.webview.service.impl.ToastServiceImpl;

/**
 * Created by Administrator on 15-4-23.
 * 服务工厂，返回具体服务
 */
public class ServiceFactory {

    /**
     * 根据接口名返回具体的服务层
     * @param interfaceNm 接口名
     * @return 服务层，具体的业务逻辑处理
     */
    public static IService produceService(String interfaceNm){

        if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_GET_LOCATION)) {
            return new GetLocationServiceImpl();
        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_CHOOSE_IMG)) {
            return new ChooseImageServiceImpl();
        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_TOAST)) {
            return new ToastServiceImpl();
        } else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_GET_NETWORK_TYPE)) {
            return new GetNetworkTypeServiceImpl();
        }else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_HIDE_OPTION_MENU)) {
            return new BaseService();
        }else if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_SHOW_OPTION_MENU)) {
            return new BaseService();
        } else {
            return new InterfaceNotFoundServiceImpl();
        }
    }
}
