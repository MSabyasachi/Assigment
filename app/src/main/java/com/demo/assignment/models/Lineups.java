
package com.demo.assignment.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Lineups  implements Serializable {

    @SerializedName("Data")
    private Data mData;
    @SerializedName("Success")
    private Boolean mSuccess;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
