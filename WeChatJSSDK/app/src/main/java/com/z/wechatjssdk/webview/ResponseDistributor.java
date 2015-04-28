package com.z.wechatjssdk.webview;

import com.z.wechatjssdk.webview.bean.Response;
import com.z.wechatjssdk.webview.fragment.IFragmentView;

/**
 * Created by Administrator on 15-4-22.
 */
public class ResponseDistributor implements IResponseDistributor{

    private IFragmentView iFragmentView;//界面操作接口
    public ResponseDistributor(IFragmentView iFragmentView) {
        this.iFragmentView = iFragmentView;
    }

    /**
     * 根据执行结果，执行界面操作
     * @param response
     */
    @Override
    public void distributeResponse(Response response) {

        String interfaceNm=response.getInterfaceNm();

        if (interfaceNm.equals(WebInterfaceContents.INTERFACE_NM_TOAST)){

            String msg=(String)response.getTag();
            iFragmentView.toast(msg);
        }
    }

}
