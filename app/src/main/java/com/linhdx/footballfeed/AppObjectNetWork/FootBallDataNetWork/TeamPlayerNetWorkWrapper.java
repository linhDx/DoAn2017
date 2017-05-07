package com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by shine on 14/04/2017.
 */

public class TeamPlayerNetWorkWrapper implements Serializable{
    @SerializedName("players")
    @Expose
    private PlayerNetWorkStatus[] players;

    public PlayerNetWorkStatus[] getPlayers() {
        return players;
    }
}
