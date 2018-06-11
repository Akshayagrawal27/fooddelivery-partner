package com.akshay.fooddelivery_partner.Model;

import com.akshay.fooddelivery_partner.Util.Utils;

import java.util.HashMap;

public class RestaurantInfo {

    private String uid;
    private String restaurantName;
    private String ownerName;
    private String contactNumber;
    private String address;
    private HashMap<String, Object> timeStampJoined;

    public RestaurantInfo() {
    }

    public RestaurantInfo(String uid, String restaurantName, String ownerName, String contactNumber, String address) {
        this.uid = uid;
        this.restaurantName = restaurantName;
        this.ownerName = ownerName;
        this.contactNumber = contactNumber;
        this.address = address;
        this.timeStampJoined = Utils.getCurrentTimeStamp();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HashMap<String, Object> getTimeStampJoined() {
        return timeStampJoined;
    }

    public void setTimeStampJoined(HashMap<String, Object> timeStampJoined) {
        this.timeStampJoined = timeStampJoined;
    }
}
