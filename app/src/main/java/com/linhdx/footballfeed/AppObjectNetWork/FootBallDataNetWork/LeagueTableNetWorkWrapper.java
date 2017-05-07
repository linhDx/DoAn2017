package com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork;

import java.io.Serializable;

/**
 * Created by shine on 09/04/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeagueTableNetWorkWrapper implements Serializable {
    @SerializedName("leagueCaption")
    @Expose
    private String leagueName;
    @SerializedName("matchday")
    @Expose
    private int matchday;
    @SerializedName("standing")
    @Expose
    private LeagueTableTeamNetWorkStatus[] tableTeamStatuses;

    public String getLeagueName() {
        return leagueName;
    }

    public int getMatchday() {
        return matchday;
    }

    public LeagueTableTeamNetWorkStatus[] getTableTeamStatuses() {
        return tableTeamStatuses;
    }
}
