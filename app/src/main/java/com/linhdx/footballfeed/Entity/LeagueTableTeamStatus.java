package com.linhdx.footballfeed.Entity;

/**
 * Created by shine on 09/04/2017.
 */

public class LeagueTableTeamStatus {
    private int position;
    private String teamName;
    private String crestURI;
    private int playedGames;
    private int points;
    private int goalDiff;

    public LeagueTableTeamStatus(int position, String teamName, String crestURI, int playedGames, int points, int goalDiff) {
        this.position = position;
        this.teamName = teamName;
        this.crestURI = crestURI;
        this.playedGames = playedGames;
        this.points = points;
        this.goalDiff = goalDiff;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCrestURI() {
        return crestURI;
    }

    public void setCrestURI(String crestURI) {
        this.crestURI = crestURI;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoalDiff() {
        return goalDiff;
    }

    public void setGoalDiff(int goalDiff) {
        this.goalDiff = goalDiff;
    }
}
