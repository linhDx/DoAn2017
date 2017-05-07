package com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by shine on 11/04/2017.
 */

public class TeamNetWorkWrapper implements Serializable{
    @SerializedName("teams")
    @Expose
    private TeamNetWorkStatus[] teamNetWorkStatuses;

    public TeamNetWorkStatus[] getTeamNetWorkStatuses() {
        return teamNetWorkStatuses;
    }
}
