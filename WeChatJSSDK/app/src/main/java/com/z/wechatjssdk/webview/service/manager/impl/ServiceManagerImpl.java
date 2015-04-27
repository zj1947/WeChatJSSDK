package com.z.wechatjssdk.webview.service.manager.impl;

import android.os.Handler;
import android.os.Looper;

import com.z.wechatjssdk.webview.bean.Request;
import com.z.wechatjssdk.webview.bean.Response;
import com.z.wechatjssdk.webview.service.ServiceFactory;
import com.z.wechatjssdk.webview.service.IService;
import com.z.wechatjssdk.webview.service.manager.IServiceManager;
import com.z.wechatjssdk.webview.service.manager.IOnServiceFinish;

/**
 * Created by Administrator on 15-4-23.
 */
public class ServiceManagerImpl implements IServiceManager {
    @Override
    public void getResponse(final Request request, final IOnServiceFinish listener) {



        new Thread(){
            @Override
            public void run() {
                super.run();

                String interfaceNm=request.getInterfaceNm();

                IService service= ServiceFactory.produceService(interfaceNm);
                final Response response=service.getResponseJSON(request);


                //获取主线程的handler
                Handler handler=new Handler(Looper.getMainLooper());
                //在主线程中处理结果
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onServiceFinish(response);
                    }
                });
            }
        }.start();
    }
}
