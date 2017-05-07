package com.linhdx.footballfeed.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.View.Activity.MainActivity;
import com.linhdx.footballfeed.entity.YouTubeVideo;
import com.linhdx.footballfeed.utils.AdapterUtil;

import java.util.List;

/**
 * Created by shine on 06/05/2017.
 */

public class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoAdapter.ViewHolder> {
    private List<YouTubeVideo> mListYouTubeVideos;
    private LayoutInflater mLayoutInflater;
    private MainActivity mContext = null;
    private OnItemClickListener iClick;

    public YoutubeVideoAdapter(MainActivity context, List<YouTubeVideo> listYouTubeVideos) {
        this.mListYouTubeVideos = listYouTubeVideos;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
    }

    public YoutubeVideoAdapter(Context context, List<YouTubeVideo> listYouTubeVideos) {
        this.mListYouTubeVideos = listYouTubeVideos;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_youtube_video, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final YouTubeVideo item = mListYouTubeVideos.get(position);
        if (item != null) {
            holder.lblVideoName = AdapterUtil.detailHtml(holder.itemView, R.id.lblVideoName, item.getTitle());
//            holder.lblDescription = AdapterUtil.detail(holder.itemView, R.id.lblDescription, item.getDescription());
            holder.imgThumb = AdapterUtil.image(holder.itemView, R.id.imgThumb, item.getThumbnail());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (iClick != null)
                        iClick.onClickListener(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListYouTubeVideos.size();
    }
    public void setOnItemClickListener(OnItemClickListener iClick) {
        this.iClick = iClick;
    }

    public interface OnItemClickListener {
        void onClickListener(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblVideoName;
        TextView lblDescription;
        SimpleDraweeView imgThumb;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
