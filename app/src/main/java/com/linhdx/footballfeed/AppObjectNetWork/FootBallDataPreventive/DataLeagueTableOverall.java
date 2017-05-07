package com.linhdx.footballfeed.AppObjectNetWork.FootBallDataPreventive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by shine on 27/04/2017.
 */

public class DataLeagueTableOverall implements Serializable {

    @SerializedName("wins")
    @Expose
    private Integer wins;
    @SerializedName("draws")
    @Expose
    private Integer draws;
    @SerializedName("losts")
    @Expose
    private Integer losts;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("scores")
    @Expose
    private Integer scores;
    @SerializedName("conceded")
    @Expose
    private Integer conceded;
    @SerializedName("last_5")
    @Expose
    private String last5;
    @SerializedName("matches_played")
    @Expose
    private Integer matchesPlayed;
    @SerializedName("goal_difference")
    @Expose
    private Integer goalDifference;
    private final static long serialVersionUID = -4828062346754849316L;

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getDraws() {
        return draws;
    }

    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    public Integer getLosts() {
        return losts;
    }

    public void setLosts(Integer losts) {
        this.losts = losts;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getScores() {
        return scores;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }

    public Integer getConceded() {
        return conceded;
    }

    public void setConceded(Integer conceded) {
        this.conceded = conceded;
    }

    public String getLast5() {
        return last5;
    }

    public void setLast5(String last5) {
        this.last5 = last5;
    }

    public Integer getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(Integer matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public Integer getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(Integer goalDifference) {
        this.goalDifference = goalDifference;
    }

}
