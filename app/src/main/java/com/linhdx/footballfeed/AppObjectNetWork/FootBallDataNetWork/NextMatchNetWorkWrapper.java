package com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork;

import java.io.Serializable;

/**
 * Created by shine on 09/04/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NextMatchNetWorkWrapper implements Serializable{
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("fixtures")
    @Expose
    private NextMatchNetWorkStatus[] nextMatchStatuses;

    public int getCount() {
        return count;
    }

    public NextMatchNetWorkStatus[] getNextMatchStatusesPL() {
        return nextMatchStatuses;
    }

}
