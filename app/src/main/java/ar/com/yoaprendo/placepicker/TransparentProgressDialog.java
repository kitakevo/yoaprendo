package ar.com.yoaprendo.placepicker;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import ar.com.yoaprendo.R;


public class TransparentProgressDialog extends Dialog {

    private ImageView iv;

    public TransparentProgressDialog(Context context) {
        super(context, R.style.TransparentProgressDialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv = new ImageView(context);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(iv);
        Glide.with(context).load(R.raw.progressbar).into(imageViewTarget);

        layout.addView(iv, params);
        addContentView(layout, params);
    }

    @Override
    public void show() {
        super.show();
    }

}
