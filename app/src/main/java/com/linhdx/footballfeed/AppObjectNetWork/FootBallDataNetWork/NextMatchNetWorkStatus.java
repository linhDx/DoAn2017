package com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.io.Serializable;

/**
 * Created by shine on 09/04/2017.
 */

public class NextMatchNetWorkStatus implements Serializable{
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("matchday")
    @Expose
    private Integer matchday;
    @SerializedName("homeTeamName")
    @Expose
    private String homeTeamName;
    @SerializedName("awayTeamName")
    @Expose
    private String awayTeamName;

    @SerializedName("result")
    @Expose
    private NextMatchNetWorkResult result;

    public NextMatchNetWorkResult getResult() {
        return result;
    }

    public void setResult(NextMatchNetWorkResult result) {
        this.result = result;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMatchday() {
        return matchday;
    }

    public void setMatchday(Integer matchday) {
        this.matchday = matchday;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

}
