package com.rainycube.petbuddy.dataset;

import io.realm.RealmObject;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SBKim on 2016-06-13.
 */
public class PetItem extends RealmObject {

    @PrimaryKey
    private int petId;
    private String petName;
    private String petType;
    private String petGender;
    private String petImgurl;
    private String tradeLocation;
    private long timeStamp;

    final static public String PETID = "petId";
    final static public String PETNAME = "petName";
    final static public String PETTYPE = "petType";
    final static public String PETGENDER = "petGender";
    final static public String TRADELOCATION = "tradeLocation";
    final static public String TIMESTAMP = "timeStamp";

    final static public String DefaultSortField = TIMESTAMP;
    final static public Sort DefaultSortASC = Sort.ASCENDING;

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

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public PetItem() {
        timeStamp = System.currentTimeMillis();
    }
}