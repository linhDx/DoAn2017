package com.linhdx.footballfeed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linhdx.footballfeed.entity.LeagueTableTeamStatus;
import com.linhdx.footballfeed.R;

import java.util.List;

/**
 * Created by shine on 10/04/2017.
 */

public class CustomListViewLeagueTableAdapter extends BaseAdapter{
    List<LeagueTableTeamStatus> list;
    Context context;

    public CustomListViewLeagueTableAdapter(List<LeagueTableTeamStatus> list, Context context) {
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
        TextView tvName, tvPlayed, tvGoalDiff, tvPoint, tvSTT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.custom_lv_league_table, parent, false);

        holder.tvName = (TextView)rowView.findViewById(R.id.tv_league_table_name);
        holder.tvPlayed = (TextView)rowView.findViewById(R.id.tv_p);
        holder.tvGoalDiff = (TextView)rowView.findViewById(R.id.tv_goal_diff);
        holder.tvPoint = (TextView)rowView.findViewById(R.id.tv_pt);
        holder.tvSTT = (TextView)rowView.findViewById(R.id.tv_stt);

        holder.tvName.setText(list.get(position).getTeamName());
        holder.tvPlayed.setText(list.get(position).getPlayedGames()+"");
        holder.tvGoalDiff.setText(list.get(position).getGoalDiff()+"");
        holder.tvPoint.setText(list.get(position).getPoints()+"");
        holder.tvSTT.setText((position+1)+"");
        return rowView;
    }
}
