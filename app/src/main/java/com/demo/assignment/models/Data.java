
package com.demo.assignment.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Data  implements Serializable {

    @SerializedName("AwayTeam")
    private AwayTeam mAwayTeam;
    @SerializedName("HomeTeam")
    private HomeTeam mHomeTeam;

    public AwayTeam getAwayTeam() {
        return mAwayTeam;
    }

    public void setAwayTeam(AwayTeam awayTeam) {
        mAwayTeam = awayTeam;
    }

    public HomeTeam getHomeTeam() {
        return mHomeTeam;
    }

    public void setHomeTeam(HomeTeam homeTeam) {
        mHomeTeam = homeTeam;
    }

}
