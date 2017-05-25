package com.linhdx.footballfeed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.entity.Competitions;
import com.linhdx.footballfeed.utils.Utils;

import java.util.List;

/**
 * Created by shine on 22/05/2017.
 */

public class CustomListViewMatchResultAdapter extends BaseAdapter {
    List<Competitions> list;
    Context context;
    private OnItemClickListener iClick;
    public CustomListViewMatchResultAdapter(Context context, List<Competitions> list) {
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
        TextView tvTime, tvHome, tvAway, tvDate, tvGoalHome, tvGoalAway;
        ImageView imgPlayVideo;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.custom_lv_match_result, parent, false);

        holder.tvTime = (TextView)rowView.findViewById(R.id.tv_competitions_time);
        holder.tvHome= (TextView)rowView.findViewById(R.id.tv_competitions_home_name);
        holder.tvAway = (TextView)rowView.findViewById(R.id.tv_competitions_away_name);
        holder.tvDate=(TextView)rowView.findViewById(R.id.tv_competitions_date);
        holder.tvGoalHome = (TextView)rowView.findViewById(R.id.tv_competitions_goal_home);
        holder.tvGoalAway = (TextView)rowView.findViewById(R.id.tv_competitions_goal_away);
        holder.imgPlayVideo = (ImageView)rowView.findViewById(R.id.iv_play_video);

        holder.tvTime.setText(list.get(position).getTime());
        holder.tvHome.setText(list.get(position).getHomeName());
        holder.tvAway.setText(list.get(position).getAwayName());
        holder.tvDate.setText(Utils.convertDate(list.get(position).getDate()));
        holder.imgPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClick != null)
                    iClick.onClickListener(position);
            }
        });
        if(list.get(position).getStatus().equals("FINISHED")){
            holder.tvGoalHome.setText(list.get(position).getGoalsHomeTeam());
            holder.tvGoalAway.setText(list.get(position).getGoalsAwayTeam());
            holder.tvGoalAway.setVisibility(View.VISIBLE);
            holder.tvGoalHome.setVisibility(View.VISIBLE);
        }

        return rowView;
    }
    public void setOnItemClickListener(OnItemClickListener iClick) {
        this.iClick = iClick;
    }

    public interface OnItemClickListener {
        void onClickListener(int position);
    }

}
