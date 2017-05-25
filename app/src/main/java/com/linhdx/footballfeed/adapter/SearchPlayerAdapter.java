package com.linhdx.footballfeed.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.View.Activity.MainActivity;
import com.linhdx.footballfeed.entity.TeamPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shine on 23/05/2017.
 */

public class SearchPlayerAdapter extends BaseAdapter implements Filterable {
    private MainActivity mainActivity;
    private PlayerFilter playerFilter;
    private List<TeamPlayer> listPlayer;
    private List<TeamPlayer> filteredList;

    public SearchPlayerAdapter(MainActivity mainActivity, List<TeamPlayer> listPlayer) {
        this.mainActivity = mainActivity;
        this.listPlayer = listPlayer;
        this.filteredList = listPlayer;
        getFilter();
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        final TeamPlayer player = (TeamPlayer) getItem(position);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_player_row_layout, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_search_player_name);

            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        // bind text with view holder content view for efficient use
        holder.name.setText(player.getName());
        return view;
    }

    @Override
    public Filter getFilter() {
        if (playerFilter == null) {
            playerFilter = new PlayerFilter();
        }

        return playerFilter;
    }

    static class ViewHolder {
        TextView name;
    }

    private class PlayerFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                List<TeamPlayer> tempList = new ArrayList<>();
                // search content in friend list
                for (TeamPlayer player : listPlayer) {
                    if (player.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(player);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = listPlayer.size();
                filterResults.values = listPlayer;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<TeamPlayer>) results.values;

            notifyDataSetChanged();
        }
    }
}
