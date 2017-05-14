package com.linhdx.footballfeed;

/**
 * Created by shine on 07/04/2017.
 */

public class AppConstant {
    public static final String BASE_URL = "http://api.football-data.org/v1/";
//    public static final String BASE_URL = "http://api.football-data.org/v1/cvc/";
    public static final String PL_N_MATCH = BASE_URL + "competitions/{id}/fixtures?timeFrame=n7";
    public static final String LEAGUETABLE = BASE_URL + "competitions/{id}/leagueTable";
    public static final String TEAMS = BASE_URL +"competitions/{id}/teams";
    public static final String TEAM_PLAYER = BASE_URL + "teams/{id}/players";
    public static final String TEAM_COMPETITIONS = BASE_URL + "teams/{id}/fixtures";

    public static final String[] LIST_LEAGUE_RSS ={"Premier League","Premier Division", "Buldesliga","Serie A"};
    public static final String[] LIST_LEAGUE_VALUE_RSS= {"1", "2", "3", "4"};
    public static final String[] LIST_ARTICLE_PL={"bong-da-anh-c8","bong-da-anh","172","bong-da-anh"};
    public static final String[] LIST_ARTICLE_PD={"La-Liga-c59","bong-da-tbn","180","la-liga"};
    public static final String[] LIST_ARTICLE_BL={"bundes-liga-c65","error","193","bundesliga"};
    public static final String[] LIST_ARTICLE_SA={"serie-a-c62","error","176","serie-a"};

    public static final String[] LIST_WEBNAME= {"BDC", "24h", "DT", "247"};


    public static final String SP_TEAM_SAVED="team_saved";
    public static final String SP_PLAYER_SAVED ="player_saved";
    public static final String SP_MATCH_SAVED="match_saved";

    public static final String PRE_BASE_URL="http://soccer.sportsopendata.net/v1/leagues/";
    public static final String PRE_LEAGUE_TABLE=PRE_BASE_URL + "{league_slug}/seasons/16-17/standings";
    public static final String PRE_MATCH_DAY= "http://www.24h.com.vn/lich-thi-dau-bong-da/lich-thi-dau-bong-da-hom-nay-c287a364371.html";

    public static final String FIRST_LAUNCH_APP = "fist";

    //rss website
    public static final String BASE_URL_DANTRI_RSS="http://www.dantri.com.vn/";
    public static final String RSS_DANTRI_ARTICLE= BASE_URL_DANTRI_RSS+ "the-thao/{id_league}.rss";

    public static final String BASE_URL_BONGDACOM_RSS = "http://www.bongda.com.vn/";
    public static final String RSS_BONGDACOM_ARTICLE= BASE_URL_BONGDACOM_RSS +"{id_league}.rss";

    public static final String BASE_URL_THETHAO247_RSS="http://www.thethao247.vn/";
    public static final String RSS_THETHAO247_ARTICLE= BASE_URL_THETHAO247_RSS +"{id_league}.rss";

    public static final String BASE_URL_BONGDA24H_RSS="http://bongda24h.vn/RSS/";
    public static final String RSS_BONGDA24H_ARTICLE= BASE_URL_BONGDA24H_RSS +"{id_league}.rss";
}
