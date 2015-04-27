package com.z.wechatjssdk.webview.bean.event;

/**
 * Created by Administrator on 15-4-27.
 */
public class Location {
    private float latitude;// 纬度，浮点数，范围为90 ~ -90
    private float longitude;// 经度，浮点数，范围为180 ~ -180。
    private float speed;//速度，以米/每秒计
    private int accuracy;//位置精度

    public Location(float latitude, float longitude, float speed, int accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.accuracy = accuracy;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
