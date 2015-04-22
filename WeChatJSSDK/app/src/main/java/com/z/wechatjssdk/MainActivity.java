package com.z.wechatjssdk;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.z.wechatjssdk.webview.WebViewFragment;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null==savedInstanceState){

            Fragment fragment=
                    WebViewFragment.newInstance("file:///android_asset/web_test_main.html");

            getSupportFragmentManager().beginTransaction().add(R.id.container,fragment).commit();
        }
    }
}
