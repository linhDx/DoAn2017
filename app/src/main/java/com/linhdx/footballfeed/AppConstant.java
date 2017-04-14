package com.linhdx.footballfeed;

/**
 * Created by shine on 07/04/2017.
 */

public class AppConstant {
    public static final String BASE_URL = "http://api.football-data.org/v1/";
    public static final String PL_N_MATCH = BASE_URL + "competitions/{id}/fixtures?timeFrame=n7";
    public static final String LEAGUETABLE = BASE_URL + "competitions/{id}/leagueTable";
    public static final String TEAMS = BASE_URL +"competitions/{id}/teams";
    public static final String TEAM_PLAYER = BASE_URL + "teams/{id}/players";
    public static final String TEAM_COMPETITIONS = BASE_URL + "teams/{id}/fixtures";
}
