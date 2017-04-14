package com.linhdx.footballfeed.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhdx.footballfeed.Entity.TeamStatus;
import com.linhdx.footballfeed.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
        Picasso.with(context).load(list.get(position).getImage()).fit().centerInside().into(holder.iconTeam, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("TAG", "succcess");
            }

            @Override
            public void onError() {
                Log.d("TAG", "error");
            }
        });

        return rowView;
    }
}
