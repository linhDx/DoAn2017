package com.linhdx.footballfeed.entity;

import com.orm.SugarRecord;

/**
 * Created by shine on 09/04/2017.
 */

public class NextMatchStatus extends SugarRecord{
    private String date;
    private String time;
    private String homeName, awayName,goalsHomeTeam,goalsAwayTeam;
    private String status;

    public NextMatchStatus(String date, String time, String homeName, String awayName, String goalsHomeTeam, String goalsAwayTeam, String status) {
        this.date = date;
        this.time = time;
        this.homeName = homeName;
        this.awayName = awayName;
        this.goalsHomeTeam = goalsHomeTeam;
        this.goalsAwayTeam = goalsAwayTeam;
        this.status = status;
    }

    public NextMatchStatus() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getAwayName() {
        return awayName;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
