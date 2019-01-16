
package com.demo.assignment.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class HomeTeam implements Serializable {

    @SerializedName("Players")
    private List<Player> mPlayers;

    public List<Player> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(List<Player> players) {
        mPlayers = players;
    }

}
