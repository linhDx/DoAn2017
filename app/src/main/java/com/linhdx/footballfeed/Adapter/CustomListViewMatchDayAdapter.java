package com.linhdx.footballfeed.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linhdx.footballfeed.Entity.NextMatchStatus;
import com.linhdx.footballfeed.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shine on 08/04/2017.
 */

public class CustomListViewMatchDayAdapter extends BaseAdapter {

    List<NextMatchStatus> list;
    Context context;

    public CustomListViewMatchDayAdapter(Context context, List<NextMatchStatus> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        TextView tvTime, tvHome, tvAway;
        ImageView imgAlert;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        Holder holder= new Holder();
        View rowView;
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.custom_lv_match_day, parent, false);

        holder.tvTime = (TextView)rowView.findViewById(R.id.tv_time_matchday);
        holder.tvHome= (TextView)rowView.findViewById(R.id.tv_home_team);
        holder.tvAway = (TextView)rowView.findViewById(R.id.tv_away_team);
        holder.imgAlert = (ImageView) rowView.findViewById(R.id.iv_alert_time);

        holder.tvTime.setText(list.get(position).getTime());
        holder.tvHome.setText(list.get(position).getHomeName());
        holder.tvAway.setText(list.get(position).getAwayName());
        holder.imgAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"dat chuong", Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
