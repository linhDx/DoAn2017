package com.linhdx.footballfeed.utils;

import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.linhdx.footballfeed.R;

/**
 * Created by shine on 06/05/2017.
 */

public class AdapterUtil {
    public static TextView detail(View v, int resId, String text) {
        TextView tv = (TextView) v.findViewById(resId);
        tv.setText(text);
        return tv;
    }

    public static TextView detailHtml(View v, int resId, String text) {
        TextView tv = (TextView) v.findViewById(resId);
        tv.setText(Html.fromHtml(text));
        return tv;
    }

    public static SimpleDraweeView image(View v, int resId, String url) {
        Uri uri = Uri.parse(url);
        SimpleDraweeView draweeView = (SimpleDraweeView) v.findViewById(resId);
        draweeView.setImageURI(uri);
        return draweeView;
    }

//    public static ImageView image(View v, int resId, boolean isFavorite) {
//        ImageView iv = (ImageView) v.findViewById(resId);
//        if (isFavorite) {
//            iv.setImageResource(R.drawable.ic_star);
//        } else {
//            iv.setImageResource(R.drawable.ic_star_black_white);
//        }
//        return iv;
//    }
}
