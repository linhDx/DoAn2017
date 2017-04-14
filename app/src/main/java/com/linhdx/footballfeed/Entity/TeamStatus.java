package com.linhdx.footballfeed.Entity;

/**
 * Created by shine on 11/04/2017.
 */

public class TeamStatus {
    private String competitions,players, name, shortName, merketValue, image;

    public TeamStatus(String competitions, String players, String name, String shortName, String merketValue, String image) {
        this.competitions = competitions;
        this.players = players;
        this.name = name;
        this.shortName = shortName;
        this.merketValue = merketValue;
        this.image = image;
    }

    public String getCompetitions() {
        return competitions;
    }

    public String getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getMerketValue() {
        return merketValue;
    }

    public String getImage() {
        return image;
    }
}
