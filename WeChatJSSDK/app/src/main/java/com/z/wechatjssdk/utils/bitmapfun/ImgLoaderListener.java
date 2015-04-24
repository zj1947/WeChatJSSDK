package com.z.wechatjssdk.utils.bitmapfun;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Administrator on 15-1-20.
 */
public interface ImgLoaderListener {
    public void onStart(String url, ImageView view);
    public void onLoadingFailed(ImageView view);
    public void onLoadingComplete(ImageView view, Bitmap bitmap);
}
