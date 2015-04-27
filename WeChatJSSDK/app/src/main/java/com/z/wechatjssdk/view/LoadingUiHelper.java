package com.z.wechatjssdk.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.KeyEvent;

import com.z.wechatjssdk.R;


/**
 * Created by Administrator on 14-8-13.
 * 进度对话框工具，创建、展示、取消进度对话框
 */
public class LoadingUiHelper {

    private Context context;
    private ProgressDialog dialog;
    private Handler handler;
    private boolean isCancelable=true;


    public LoadingUiHelper(Context context, Handler handler) {
        this.context = context;
        this.handler=handler;
    }


    public void showProgressDialog(String strNotify){
        createDialog(strNotify);
        dialog.show();
    }
    public void dismissDialog(){
        if (null!=dialog&&dialog.isShowing())
            dialog.dismiss();
    }

    public boolean isDialogShowing(){
        return null!=dialog&&dialog.isShowing();
    }

    public void  setCancelable(boolean cancelable){
      this.isCancelable=cancelable;
    }

    private void createDialog(String strNotify) {
        if (dialog==null){
            dialog=new ProgressDialog(context);
            dialog.setMessage(strNotify);
            dialog.setCancelable(false);
            dialog.setOnKeyListener(onKeyListener);
        }else {
            dialog.setMessage(strNotify);
        }
    }



    private DialogInterface.OnKeyListener onKeyListener=new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                if (isCancelable)
                    dismissDialog();
                if (null!=handler){
                    handler.sendEmptyMessage(R.id.handle_cancel_progress_dialog);
                }
            }
            return false;
        }

    };
}
