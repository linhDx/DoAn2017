package com.linhdx.footballfeed.View.CustomView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.linhdx.footballfeed.R;

/**
 * Created by shine on 08/04/2017.
 */

public class TabInfor extends LinearLayout {
    private TextView mTabTitle;
    private ListView mTabListView;
    private View rootView;
    public TabInfor(Context context) {
        super(context);
        init(context);
    }

    public TabInfor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TabInfor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.tab_infor, this, true);
        mTabTitle = (TextView)rootView.findViewById(R.id.tab_title);
        mTabListView  = (ListView) rootView.findViewById(R.id.tab_lv_match_day);
    }

    public void setmTabTitle(String title){
        mTabTitle.setText(title);
    }

    public ListView getmTabListView() {
        return mTabListView;
    }

    public void setmTabListView(ListView mTabListView) {
        this.mTabListView = mTabListView;
    }
}
