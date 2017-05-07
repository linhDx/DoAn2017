package com.linhdx.footballfeed.AppObjectNetWork.FootBallDataPreventive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by shine on 27/04/2017.
 */

public class DataWrapper implements Serializable {
    @SerializedName("data")
    @Expose
    private DataLeagueTableNetWork data;
    private final static long serialVersionUID = -2420979164143381626L;

    public DataLeagueTableNetWork getData() {
        return data;
    }

    public void setData(DataLeagueTableNetWork data) {
        this.data = data;
    }
}
