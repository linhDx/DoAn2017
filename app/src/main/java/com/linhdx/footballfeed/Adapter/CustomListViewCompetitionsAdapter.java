package com.linhdx.footballfeed.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linhdx.footballfeed.Entity.NextMatchStatus;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.utils.Utils;

import java.util.List;

/**
 * Created by shine on 14/04/2017.
 */

public class CustomListViewCompetitionsAdapter extends BaseAdapter {
    List<NextMatchStatus> list;
    Context context;

    public CustomListViewCompetitionsAdapter(Context context, List<NextMatchStatus> list) {
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
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.custom_lv_competitions, parent, false);

        holder.tvTime = (TextView)rowView.findViewById(R.id.tv_competitions_time);
        holder.tvHome= (TextView)rowView.findViewById(R.id.tv_competitions_home_name);
        holder.tvAway = (TextView)rowView.findViewById(R.id.tv_competitions_away_name);
        holder.tvDate=(TextView)rowView.findViewById(R.id.tv_competitions_date);
        holder.tvGoalHome = (TextView)rowView.findViewById(R.id.tv_competitions_goal_home);
        holder.tvGoalAway = (TextView)rowView.findViewById(R.id.tv_competitions_goal_away);

        holder.tvTime.setText(list.get(position).getTime());
        holder.tvHome.setText(list.get(position).getHomeName());
        holder.tvAway.setText(list.get(position).getAwayName());
        holder.tvDate.setText(Utils.convertDate(list.get(position).getDate()));
        if(list.get(position).getStatus().equals("FINISHED")){
            holder.tvGoalHome.setText(list.get(position).getGoalsHomeTeam());
            holder.tvGoalAway.setText(list.get(position).getGoalsAwayTeam());
            holder.tvGoalAway.setVisibility(View.VISIBLE);
            holder.tvGoalHome.setVisibility(View.VISIBLE);
        }
        return rowView;
    }
}
