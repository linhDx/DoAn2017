package com.linhdx.footballfeed.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shine on 06/05/2017.
 */

public class YouTubeVideo implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<YouTubeVideo> CREATOR = new Parcelable.Creator<YouTubeVideo>() {
        @Override
        public YouTubeVideo createFromParcel(Parcel in) {
            return new YouTubeVideo(in);
        }

        @Override
        public YouTubeVideo[] newArray(int size) {
            return new YouTubeVideo[size];
        }
    };
    private String videoId;
    private String title;
    private String thumbnail;
    private String description;
    private String publishedAt;
    private String channelTitle;
    private boolean isFavorite;

    public YouTubeVideo() {
    }

    public YouTubeVideo(String videoId, String title, String thumbnail, String description, String publishedAt, String channelTitle, boolean isFavorite) {
        this.videoId = videoId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.publishedAt = publishedAt;
        this.channelTitle = channelTitle;
        this.isFavorite = isFavorite;
    }

    protected YouTubeVideo(Parcel in) {
        videoId = in.readString();
        title = in.readString();
        thumbnail = in.readString();
        description = in.readString();
        publishedAt = in.readString();
        isFavorite = in.readByte() != 0x00;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void changeFavorite() {
        isFavorite = !isFavorite;
    }

    public boolean isEqual(YouTubeVideo otherYouTubeVideo) {
        if (otherYouTubeVideo.videoId == null || videoId == null) return false;
        return videoId.equals(otherYouTubeVideo.videoId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoId);
        dest.writeString(title);
        dest.writeString(thumbnail);
        dest.writeString(description);
        dest.writeString(publishedAt);
        dest.writeByte((byte) (isFavorite ? 0x01 : 0x00));
    }
}
