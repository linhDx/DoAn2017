package com.linhdx.footballfeed.AppObjectNetWork;

/**
 * Created by shine on 11/04/2017.
 */
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class TeamCompetitionsNetwork implements Serializable
{

    @SerializedName("href")
    @Expose
    private String href;
    private final static long serialVersionUID = -7131517021347390166L;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
