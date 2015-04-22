package com.z.wechatjssdk.webview;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.z.wechatjssdk.R;
import com.z.wechatjssdk.view.LoadingUiHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */

public class WebViewFragment extends Fragment implements EventWatcher{

    private static final String TAG=WebViewFragment.class.getSimpleName();

    private static final String ARG_URL = "param1";

    private static final String INJECTED_NAME = "BabJSBridge";

    private WebView mWebView;
    private String strUrl;
    private boolean isDismiss;
    private LoadingUiHelper mLoadingUiHelper;
    private ValueCallback<Uri> mUploadMessage;
    private HashMap<String, JSONObject> mWebReqList;

    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle arg = new Bundle();
        arg.putString(ARG_URL, url);
        fragment.setArguments(arg);
        return fragment;
    }

    public WebViewFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            strUrl = getArguments().getString(ARG_URL);
        }


        if (null != savedInstanceState) {
            strUrl = savedInstanceState.getString("strUrl");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

        if (TextUtils.isEmpty(strUrl)) {
            return;
        }

        loadUrl(strUrl);
    }

    @Override
    public void onResume() {
        mWebView.resumeTimers();
        super.onResume();
    }

    @Override
    public void onPause() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.pauseTimers();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString("strUrl", strUrl);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {

        if (getActivity().RESULT_OK!=resultCode)
            return;

    }

    /**
     * Called when the WebView has been detached from the fragment.
     * The WebView is no longer available after this time.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    @Override
    public void onDestroy() {

        if (mUploadMessage != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }


    @Override
    public void onDetach() {

        mWebView.loadUrl("about:blank");
        mLoadingUiHelper.dismissDialog();
        mLoadingUiHelper = null;
        super.onDetach();
    }

    private void initView(View rootView) {
        mWebView = (WebView) rootView.findViewById(R.id.wbv_page);
    }

    private void initData() {

        mLoadingUiHelper = new LoadingUiHelper(mWebView.getContext(), null);
        mWebReqList = new HashMap<>();

        //支持javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
//            mWebView.getSettings().setTextSize(WebSettings.TextSize.LARGER);
        // 设置可以支持缩放
//            mWebView.getSettings().setSupportZoom(false);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(false);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        mWebView.setTag(this);

        mWebView.setWebChromeClient(new InjectedChromeClient(INJECTED_NAME, HostJsScope.class));
        mWebView.setWebViewClient(new InfoDetailWebViewClient());

    }

    public void loadUrl(String url) {

        Log.d("TAG", url);
        mWebView.loadUrl(url);
    }

    @Override
    public void getLocation(JSONObject jo) {

    }

    @Override
    public void previewImage(JSONObject jo) {

    }

    @Override
    public void uploadImage(JSONObject jo) {

    }

    @Override
    public void chooseImage(JSONObject jo) {

    }

    @Override
    public void goBack(JSONObject jo) {

    }

    @Override
    public void alert(JSONObject jo) {

    }

    @Override
    public void toast(JSONObject jo) {
        Toast.makeText(getActivity(),jo.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void jump(JSONObject jo) {

    }

    @Override
    public void selectTime(JSONObject jo) {

    }

    @Override
    public void error(String errorMsg) {

    }

    class InfoDetailWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //handler.cancel(); 默认的处理方式，WebView变成空白页
//                        //接受证书
            handler.proceed();
//            handleMessage(Message msg); //其他处理
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (null != mLoadingUiHelper && !mLoadingUiHelper.isDialogShowing()) {
                mLoadingUiHelper.showProgressDialog("加载中……");
            }
        }

        @Override
        public void onPageFinished(WebView webView, String url) {

            if (null != mLoadingUiHelper)
                mLoadingUiHelper.dismissDialog();

            super.onPageFinished(webView, url);
        }
    }




}
