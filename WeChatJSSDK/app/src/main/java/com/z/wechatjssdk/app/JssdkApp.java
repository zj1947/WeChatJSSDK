package com.z.wechatjssdk.app;

import android.app.Application;

/**
 * Created by Administrator on 15-4-23.
 */
public class JssdkApp extends Application {
    private static JssdkApp instance=null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static JssdkApp getInstance() {
        return instance;
    }
}
