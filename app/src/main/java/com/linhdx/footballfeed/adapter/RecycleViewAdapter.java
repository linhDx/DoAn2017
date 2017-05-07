package com.linhdx.footballfeed.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.entity.TeamStatus;

import java.util.List;

/**
 * Created by shine on 03/05/2017.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    List<TeamStatus> list;
    Context context;
    View view1;
    ViewHolder viewHolder1;
    public RecycleViewAdapter(List<TeamStatus> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iconTeam;
        public TextView teamName;
        public ViewHolder(View itemView) {
            super(itemView);
            iconTeam = (ImageView)itemView.findViewById(R.id.icon_teams);
            teamName = (TextView)itemView.findViewById(R.id.tv_team_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view1 = LayoutInflater.from(context).inflate(R.layout.custom_lv_team_layout, parent, false);
        viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.teamName.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
