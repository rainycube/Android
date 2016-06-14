package com.rainycube.petbuddy.dataset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SBKim on 2016-06-13.
 */
public class PetItem {

    @SerializedName("petId")
    @Expose
    private int petId;
    @SerializedName("petName")
    @Expose
    private String petName;
    @SerializedName("petType")
    @Expose
    private String petType;
    @SerializedName("petGender")
    @Expose
    private String petGender;
    @SerializedName("petImgurl")
    @Expose
    private String petImgurl;
    @SerializedName("tradeLocation")
    @Expose
    private String tradeLocation;

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public void setPetImgUrl(String petImgUrl) {
        this.petImgurl = petImgUrl;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public void setTradeLocation(String tradeLocation) {
        this.tradeLocation = tradeLocation;
    }

    public String getPetGender() {
        return petGender;
    }

    public int getPetId() {
        return petId;
    }

    public String getPetImgUrl() {
        return petImgurl;
    }

    public String getPetName() {
        return petName;
    }

    public String getPetType() {
        return petType;
    }

    public String getTradeLocation() {
        return tradeLocation;
    }
}