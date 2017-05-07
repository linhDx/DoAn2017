package com.linhdx.footballfeed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.linhdx.footballfeed.entity.TeamStatus;
import com.linhdx.footballfeed.R;

import java.util.List;

/**
 * Created by shine on 11/04/2017.
 */

public class CustomListViewTeamAdapter extends BaseAdapter {
    List<TeamStatus> list;
    Context context;
    public CustomListViewTeamAdapter(List<TeamStatus> list, Context context) {
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

    public  class Holder{
        ImageView iconTeam;
        TextView teamName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.custom_lv_team_layout, parent, false);

        holder.iconTeam= (ImageView)rowView.findViewById(R.id.icon_teams);
        holder.teamName = (TextView)rowView.findViewById(R.id.tv_team_name);

        holder.teamName.setText(list.get(position).getName());


        return rowView;
    }
}
