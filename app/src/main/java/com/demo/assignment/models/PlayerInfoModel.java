
package com.demo.assignment.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class PlayerInfoModel implements Serializable {

    @SerializedName("Lineups")
    private Lineups mLineups;

    public Lineups getLineups() {
        return mLineups;
    }

    public void setLineups(Lineups lineups) {
        mLineups = lineups;
    }

}
