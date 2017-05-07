package com.linhdx.footballfeed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linhdx.footballfeed.entity.TeamPlayer;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.utils.Utils;

import java.util.List;

/**
 * Created by shine on 14/04/2017.
 */

public class CustomListViewTeamPlayerAdapter extends BaseAdapter {
    List<TeamPlayer> list;
    Context context;

    public CustomListViewTeamPlayerAdapter(List<TeamPlayer> list, Context context) {
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
        TextView name, position, jerseyNumber, birth, nationality, contracUntil;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.custom_lv_team_player_layout, parent, false);

        holder.name = (TextView)rowView.findViewById(R.id.tv_team_player_name);
        holder.position = (TextView)rowView.findViewById(R.id.tv_team_player_position);
        holder.jerseyNumber = (TextView)rowView.findViewById(R.id.tv_team_player_jersey_number);
        holder.birth = (TextView)rowView.findViewById(R.id.tv_team_player_birth);
        holder.nationality = (TextView)rowView.findViewById(R.id.tv_team_player_nationality);
        holder.contracUntil = (TextView)rowView.findViewById(R.id.tv_team_player_contract_until);

        holder.name.setText(list.get(position).getName());
        holder.position.setText(list.get(position).getPosition());
        holder.jerseyNumber.setText(list.get(position).getJerseyNumber()+"");
        holder.birth.setText(Utils.convertDate(list.get(position).getDateOfBirth()));
        holder.nationality.setText(list.get(position).getNationality());
        if(list.get(position).getContracUntil().contains("-")){
        holder.contracUntil.setText(Utils.convertDate(list.get(position).getContracUntil()));}
        else {
            holder.contracUntil.setText("Unpublished");
        }
        return rowView;
    }
}
