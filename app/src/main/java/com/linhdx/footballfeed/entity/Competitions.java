package com.linhdx.footballfeed.entity;

/**
 * Created by shine on 27/04/2017.
 */

public class Competitions extends NextMatchStatus {
    TeamStatus teamStatus;
    public Competitions() {
    }

    public Competitions(String date, String time, String homeName, String awayName, String goalsHomeTeam, String goalsAwayTeam, String status) {
        super(date, time, homeName, awayName, goalsHomeTeam, goalsAwayTeam, status);
    }

    public Competitions(String date, String time, String homeName, String awayName, String goalsHomeTeam, String goalsAwayTeam, String status, TeamStatus teamStatus) {
        super(date, time, homeName, awayName, goalsHomeTeam, goalsAwayTeam, status);
        this.teamStatus = teamStatus;
    }

    public TeamStatus getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(TeamStatus teamStatus) {
        this.teamStatus = teamStatus;
    }
}
