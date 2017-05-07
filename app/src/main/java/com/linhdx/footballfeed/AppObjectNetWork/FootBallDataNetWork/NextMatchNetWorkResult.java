package com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork;

/**
 * Created by shine on 09/04/2017.
 */
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NextMatchNetWorkResult implements Serializable{
    @SerializedName("goalsHomeTeam")
    @Expose
    private String goalsHomeTeam;
    @SerializedName("goalsAwayTeam")
    @Expose
    private String goalsAwayTeam;
    private final static long serialVersionUID = 1782271746245265050L;

    public String getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public void setGoalsHomeTeam(String goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    public String getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public void setGoalsAwayTeam(String goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
    }

}
