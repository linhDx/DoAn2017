package com.linhdx.footballfeed.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.View.Activity.MainActivity;
import com.linhdx.footballfeed.entity.Article;
import com.linhdx.footballfeed.utils.AdapterUtil;

import java.util.List;

/**
 * Created by shine on 19/04/2017.
 */

public class CustomListViewListArticle extends RecyclerView.Adapter<CustomListViewListArticle.ViewHolder> {
    private List<Article> mListArticle;
    private LayoutInflater mLayoutInflater;
    private MainActivity mContext = null;
    private OnItemClickListener iClick;

    public CustomListViewListArticle(List<Article> mListArticle, LayoutInflater mLayoutInflater, MainActivity mContext) {
        this.mListArticle = mListArticle;
        this.mLayoutInflater = mLayoutInflater;
        this.mContext = mContext;
    }

    public CustomListViewListArticle(Context context,List<Article> mListArticle) {
        this.mListArticle = mListArticle;
        this.mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_youtube_video, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Article item = mListArticle.get(position);
        if(item!= null){
            holder.title = AdapterUtil.detail(holder.itemView, R.id.lblVideoName, item.getTitlte());
            holder.pubDate = AdapterUtil.detail(holder.itemView, R.id.lblDescription, item.getPubDate().toString());
            holder.imgThumb = AdapterUtil.image(holder.itemView, R.id.imgThumb, item.getImage());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClick!= null){
                        iClick.onClickListener(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListArticle.size();
    }
    public void setOnItemClickListener(OnItemClickListener iClick) {
      this.iClick = iClick;
    }

    public interface OnItemClickListener {
        void onClickListener(int position);
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView pubDate;
        SimpleDraweeView imgThumb;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
