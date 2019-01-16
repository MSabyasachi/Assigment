
package com.demo.assignment.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Player  implements Serializable {

    @SerializedName("Id")
    private Long mId;
    @SerializedName("IsCaptain")
    private Boolean mIsCaptain;
    @SerializedName("JerseyNumber")
    private String mJerseyNumber;
    @SerializedName("Name")
    private String mName;
    @SerializedName("Order")
    private Long mOrder;
    @SerializedName("Role")
    private String mRole;
    @SerializedName("StartInField")
    private Boolean mStartInField;
    @SerializedName("XCoordinate")
    private Long mXCoordinate;
    @SerializedName("YCoordinate")
    private Long mYCoordinate;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Boolean getIsCaptain() {
        return mIsCaptain;
    }

    public void setIsCaptain(Boolean isCaptain) {
        mIsCaptain = isCaptain;
    }

    public String getJerseyNumber() {
        return mJerseyNumber;
    }

    public void setJerseyNumber(String jerseyNumber) {
        mJerseyNumber = jerseyNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getOrder() {
        return mOrder;
    }

    public void setOrder(Long order) {
        mOrder = order;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        mRole = role;
    }

    public Boolean getStartInField() {
        return mStartInField;
    }

    public void setStartInField(Boolean startInField) {
        mStartInField = startInField;
    }

    public Long getXCoordinate() {
        return mXCoordinate;
    }

    public void setXCoordinate(Long xCoordinate) {
        mXCoordinate = xCoordinate;
    }

    public Long getYCoordinate() {
        return mYCoordinate;
    }

    public void setYCoordinate(Long yCoordinate) {
        mYCoordinate = yCoordinate;
    }

}
