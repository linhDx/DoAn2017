package com.linhdx.footballfeed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.Article_BDC;
import com.linhdx.footballfeed.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shine on 19/04/2017.
 */

public class CustomListViewListArticle extends BaseAdapter {
    class ViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView size;
    }

    public List<Article_BDC> mlistAppInfo;
    LayoutInflater infater = null;
    private Context mContext;
    public CustomListViewListArticle(Context context, List<Article_BDC> apps) {
        infater = LayoutInflater.from(context);
       this.mContext = context;
        this.mlistAppInfo = apps;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mlistAppInfo.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mlistAppInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = infater.inflate(R.layout.item_list_articles,
                    null);
            holder = new ViewHolder();
            holder.appIcon = (ImageView) convertView
                    .findViewById(R.id.app_icon);
            holder.appName = (TextView) convertView
                    .findViewById(R.id.app_name);
            holder.size = (TextView) convertView
                    .findViewById(R.id.app_size);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Article_BDC item = (Article_BDC) getItem(position);
        if (item != null) {

            //   holder.appIcon.setImageResource(item.);
            holder.appName.setText(item.getTitle());
            holder.size.setText(item.getPubDate());
            Picasso.with(mContext).load(item.getImage()).into(holder.appIcon);

        }


        return convertView;
    }

}
