package com.z.wechatjssdk.webview.fragment;


import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.view.ActionProvider;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.z.wechatjssdk.R;

/**
 * Created by Administrator on 15-4-29.
 */
public class PopupMenuActionProvider extends ActionProvider implements OnClickListener {
    private ContextWrapper mContextWrapper;

    public PopupMenuActionProvider(Context context) {
        super(context);
        mContextWrapper=(ContextWrapper)context;
    }

    @Override
    public View onCreateActionView() {

        LinearLayout linearLayout=new LinearLayout(mContextWrapper);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.gravity= Gravity.CENTER;
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams imgParams=new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imgParams.setMargins(0, 0,20,0);

        ImageView imageView=new ImageView(mContextWrapper);
        imageView.setImageDrawable(mContextWrapper.getResources().getDrawable(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha));
        imageView.setOnClickListener(this);

        linearLayout.addView(imageView,imgParams);

        return linearLayout;
    }

    @Override
    public void onClick(View view) {
        showPopup(view);
    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(mContextWrapper, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_webview_fragment_sub, popup.getMenu());
        popup.show();
    }
}
