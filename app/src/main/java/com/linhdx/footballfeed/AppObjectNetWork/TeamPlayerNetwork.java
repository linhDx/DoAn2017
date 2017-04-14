package com.linhdx.footballfeed.AppObjectNetWork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by shine on 11/04/2017.
 */

public class TeamPlayerNetwork implements Serializable {
    @SerializedName("href")
    @Expose
    private String hrefTeamPlay;

    public String getHrefTeamPlay() {
        return hrefTeamPlay;
    }
}
