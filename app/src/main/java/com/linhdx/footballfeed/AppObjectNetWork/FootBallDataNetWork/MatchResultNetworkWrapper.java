package com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by shine on 18/05/2017.
 */

public class MatchResultNetworkWrapper implements Serializable {
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("fixtures")
    @Expose
    private NextMatchNetWorkStatus[] nextMatchStatuses;

    public int getCount() {
        return count;
    }

    public NextMatchNetWorkStatus[] getMatchResult() {
        return nextMatchStatuses;
    }

}
