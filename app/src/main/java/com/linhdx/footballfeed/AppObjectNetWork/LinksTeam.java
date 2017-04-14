package com.linhdx.footballfeed.AppObjectNetWork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by shine on 11/04/2017.
 */

public class LinksTeam implements Serializable
{

    @SerializedName("fixtures")
    @Expose
    private TeamCompetitionsNetwork fixtures;
    @SerializedName("players")
    @Expose
    private TeamPlayerNetwork players;
    private final static long serialVersionUID = 6066376766832244629L;

    public TeamCompetitionsNetwork getFixtures() {
        return fixtures;
    }

    public void setFixtures(TeamCompetitionsNetwork fixtures) {
        this.fixtures = fixtures;
    }

    public TeamPlayerNetwork getPlayers() {
        return players;
    }

    public void setPlayers(TeamPlayerNetwork players) {
        this.players = players;
    }

}
