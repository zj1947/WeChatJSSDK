package com.z.wechatjssdk.webview;

import android.webkit.WebView;

import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.bean.Response;
import com.z.wechatjssdk.webview.fragment.IFragmentView;
import com.z.wechatjssdk.webview.js.JsCallback;
import com.z.wechatjssdk.webview.service.IService;
import com.z.wechatjssdk.webview.service.ServiceFactory;
import com.z.wechatjssdk.webview.service.IOnServiceFinish;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 15-4-22.
 * 请求传递管理，主要是传递request给Service层{@link com.z.wechatjssdk.webview.service.IService}处理
 * 以及处理结束后回调返回{@link com.z.wechatjssdk.webview.service.IOnServiceFinish}
 * @see #onServiceFinish(Response) 处理js回调，将处理结果返回给web端，并分配结果给IResponseDistributor{@link com.z.wechatjssdk.webview.fragment.IFragmentView}
 * 刷新Android端界面
 */
public class DeliveryManager implements IDelivery, IOnServiceFinish {

    /**
     * 界面响应分配，根据执行结果回调界面响应接口{@link com.z.wechatjssdk.webview.fragment.IFragmentView}
     */
    private IResponseDistributor iResponseDistributor;

    private WebView webView;

    public DeliveryManager(IFragmentView iFragmentView, WebView webView) {
        iResponseDistributor = new ResponseDistributor(iFragmentView);
        this.webView = webView;
    }


    /**
     *
     * @param request 网络请求 {@link com.z.wechatjssdk.webview.bean.Request}
     */
    @Override
    public void deliveryRequest(Request request) {
        String interfaceNm = request.getInterfaceNm();
        //使用抽象工厂方法获取具体的Service处理层
        IService service = ServiceFactory.produceService(interfaceNm);
        //处理请求，处理结束回调onServiceFinish @see #onServiceFinish(Response)
        service.processRequest(request, this);
    }

    /**
     * @see #deliveryRequest(Request) 执行这个
     * @param response 处理结果 {@link com.z.wechatjssdk.webview.bean.Response}
     */
    @Override
    public void onServiceFinish(final Response response) {

        //js回调
        final JsCallback jsCallBack = new JsCallback(
                webView,
                WebInterfaceContents.INJECTED_NAME,
                response.getQueueIndex());
        try {
            jsCallBack.apply(response.getResponseJSON());
        } catch (JsCallback.JsCallbackException e) {
            e.printStackTrace();
        }
        //界面刷新分配
        iResponseDistributor.distributeResponse(response);

    }


}
