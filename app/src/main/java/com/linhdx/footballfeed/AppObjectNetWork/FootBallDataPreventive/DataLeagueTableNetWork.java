package com.linhdx.footballfeed.AppObjectNetWork.FootBallDataPreventive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shine on 27/04/2017.
 */

public class DataLeagueTableNetWork implements Serializable {
    @SerializedName("standings")
    @Expose
    private List<Standing> standings = null;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("statusReason")
    @Expose
    private String statusReason;
    private final static long serialVersionUID = -5383507961739600580L;

    public List<Standing> getStandings() {
        return standings;
    }

    public void setStandings(List<Standing> standings) {
        this.standings = standings;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
}
