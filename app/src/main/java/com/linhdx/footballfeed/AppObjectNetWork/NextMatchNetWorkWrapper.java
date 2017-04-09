package com.linhdx.footballfeed.AppObjectNetWork;

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

    public void setCount(int count) {
        this.count = count;
    }

    public NextMatchNetWorkStatus[] getNextMatchStatusesPL() {
        return nextMatchStatuses;
    }
    public NextMatchNetWorkStatus[] getNextMatchStatusesPD() {
        return nextMatchStatuses;
    }
    public NextMatchNetWorkStatus[] getNextMatchStatusesBL() {
        return nextMatchStatuses;
    }
    public NextMatchNetWorkStatus[] getNextMatchStatusesSA() {
        return nextMatchStatuses;
    }



    public void setNextMatchStatuses(NextMatchNetWorkStatus[] nextMatchStatuses) {
        this.nextMatchStatuses = nextMatchStatuses;
    }
}
