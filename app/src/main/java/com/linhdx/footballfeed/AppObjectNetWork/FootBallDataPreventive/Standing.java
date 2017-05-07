package com.linhdx.footballfeed.AppObjectNetWork.FootBallDataPreventive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by shine on 27/04/2017.
 */

public class Standing implements Serializable {
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("team_identifier")
    @Expose
    private String teamIdentifier;
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("overall")
    @Expose
    private DataLeagueTableOverall overall;

    private final static long serialVersionUID = 4292523095024155897L;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getTeamIdentifier() {
        return teamIdentifier;
    }

    public void setTeamIdentifier(String teamIdentifier) {
        this.teamIdentifier = teamIdentifier;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public DataLeagueTableOverall getOverall() {
        return overall;
    }

    public void setOverall(DataLeagueTableOverall overall) {
        this.overall = overall;
    }

}
