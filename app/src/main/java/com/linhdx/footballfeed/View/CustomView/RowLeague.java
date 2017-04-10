package com.linhdx.footballfeed.View.CustomView;

import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linhdx.footballfeed.R;

/**
 * Created by shine on 09/04/2017.
 */

public class RowLeague extends LinearLayout {
    private ImageView iconLeague, iconShow;
    private TextView leagueName;
    private View rootView;
    public RowLeague(Context context) {
        super(context);
        init(context);
    }


    public RowLeague(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RowLeague(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.custom_row_league, this, true);
        iconLeague = (ImageView) rootView.findViewById(R.id.icon_league);
        iconShow = (ImageView) rootView.findViewById(R.id.icon_view_league);
        leagueName = (TextView)rootView.findViewById(R.id.tab_league_name);
    }

    public void setIconLeague(int id) {
        iconLeague.setImageResource(id);
    }

    public void setLeagueName(String name) {
        leagueName.setText(name);
    }
}
