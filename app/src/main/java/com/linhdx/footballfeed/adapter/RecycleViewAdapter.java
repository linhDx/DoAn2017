package com.linhdx.footballfeed.adapter;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.entity.TeamStatus;
import com.linhdx.footballfeed.glide.SvgDecoder;
import com.linhdx.footballfeed.glide.SvgDrawableTranscoder;
import com.linhdx.footballfeed.glide.SvgSoftwareLayerSetter;

import java.io.InputStream;
import java.util.List;

/**
 * Created by shine on 03/05/2017.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    List<TeamStatus> list;
    Context context;
    View view1;
    ViewHolder viewHolder1;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public RecycleViewAdapter(List<TeamStatus> list, Context context) {
        this.list = list;
        this.context = context;
        requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.ic_ball_place)
                .error(R.drawable.img_not_found)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconTeam;
        public TextView teamName;

        public ViewHolder(View itemView) {
            super(itemView);
            iconTeam = (ImageView) itemView.findViewById(R.id.icon_teams);
            teamName = (TextView) itemView.findViewById(R.id.tv_team_name);
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
        String urlImage = list.get(position).getImage();
        if (urlImage.contains(".svg")) {
            loadNet(urlImage, holder.iconTeam);
        } else {
            Glide.with(context).load(urlImage).into(holder.iconTeam);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void loadNet(String url, ImageView imageViewNet) {
        Uri uri = Uri.parse(url);
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(imageViewNet);
    }
}
