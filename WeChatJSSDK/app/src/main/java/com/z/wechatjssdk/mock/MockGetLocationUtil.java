package com.z.wechatjssdk.mock;

import android.os.Handler;
import android.os.Looper;

import com.z.wechatjssdk.webview.bean.event.Location;

/**
 * Created by Administrator on 15-4-28.
 */
public class MockGetLocationUtil {

    public void getLocation(final OnGetLocationListener listener){
        new Thread(){
            @Override
            public void run() {
                final Location location=getLocation();

                Handler handler=new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onGetLocation(location);
                    }
                });


            }
        }.start();
    }


    public Location getLocation() {

        try {
            //设定获取位置需5s，模拟延时线程阻塞
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Location location = new Location(33.4f, 26.21f, 5.1f, 10);
        return location;
    }
}
