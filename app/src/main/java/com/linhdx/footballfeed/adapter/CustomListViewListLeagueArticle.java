package com.linhdx.footballfeed.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhdx.footballfeed.entity.LeagueNameRss;
import com.linhdx.footballfeed.R;

import java.util.List;

/**
 * Created by shine on 19/04/2017.
 */

public class CustomListViewListLeagueArticle extends BaseAdapter {
    List<LeagueNameRss> list;
    Context context;
    Integer[] imgid;
    public CustomListViewListLeagueArticle(List<LeagueNameRss> list, Context context,Integer[] imgid) {
        this.list = list;
        this.context = context;
        this.imgid = imgid;
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

    public  class HolderListLeagueArticle {
        TextView name;
        ImageView image;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderListLeagueArticle holder = new HolderListLeagueArticle();
        View rowView;
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = layoutInflater.inflate(R.layout.custom_lv_team_layout, parent, false);

        holder.name = (TextView)rowView.findViewById(R.id.tv_team_name);
        holder.image = (ImageView)rowView.findViewById(R.id.icon_teams);
        holder.name.setText(list.get(position).getName());
        holder.image.setImageResource(imgid[position]);
        return rowView;
    }
}
