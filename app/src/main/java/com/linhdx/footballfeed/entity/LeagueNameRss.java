package com.linhdx.footballfeed.entity;

/**
 * Created by shine on 19/04/2017.
 */

public class LeagueNameRss {
    String name, value;

    public LeagueNameRss(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
