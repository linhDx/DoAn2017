package com.linhdx.footballfeed.entity;

import com.orm.SugarRecord;

/**
 * Created by shine on 14/04/2017.
 */

public class TeamPlayer extends SugarRecord{
    String name, position, jerseyNumber, dateOfBirth, nationality, contracUntil, marketValue;
     public TeamStatus teamStatus;
    public TeamPlayer() {
    }

    public TeamPlayer(String name, String position, String jerseyNumber, String dateOfBirth, String nationality, String contracUntil, String marketValue
                  , TeamStatus teamStatus  ) {
        this.name = name;
        this.position = position;
        this.jerseyNumber = jerseyNumber;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.contracUntil = contracUntil;
        this.marketValue = marketValue;
        this.teamStatus = teamStatus;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(String jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getContracUntil() {
        return contracUntil;
    }

    public void setContracUntil(String contracUntil) {
        this.contracUntil = contracUntil;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public TeamStatus getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(TeamStatus teamStatus) {
        this.teamStatus = teamStatus;
    }
}
