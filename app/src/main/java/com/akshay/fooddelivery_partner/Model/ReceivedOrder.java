package com.akshay.fooddelivery_partner.Model;

import com.akshay.fooddelivery_partner.Util.Utils;

import java.util.HashMap;
import java.util.List;

public class ReceivedOrder {

    private String orderId;
    private String orderNo;
    private List<MenuItems> orderedItems;
    private String orderStatus;
    private double orderAmount;
    private HashMap<String, Object> orderDateTimeStamp;

    public ReceivedOrder() {
    }

    public ReceivedOrder(String orderId, List<MenuItems> orderedItems, String orderStatus) {
        this.orderId = orderId;
        this.orderedItems = orderedItems;
        this.orderStatus = orderStatus;
        this.orderDateTimeStamp = Utils.getCurrentTimeStamp();
    }

    public ReceivedOrder(String orderId, List<MenuItems> orderedItems, String orderStatus, double orderAmount) {
        this.orderId = orderId;
        this.orderedItems = orderedItems;
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.orderDateTimeStamp = Utils.getCurrentTimeStamp();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public List<MenuItems> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<MenuItems> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }
}
