package com.akshay.fooddelivery_partner.Model;

import com.akshay.fooddelivery_partner.Util.Utils;

import java.util.HashMap;

public class MenuItems {

    private String menuId;
    private String category;
    private String menuName;
    private int quantity;
    private boolean available;
    private Long price;
    private HashMap<String, Object> timestampLastChanged;

    public MenuItems() {
    }

    public MenuItems(String menuId, String category, String menuName, int quantity, Long price) {
        this.menuId = menuId;
        this.category = category;
        this.menuName = menuName;
        this.quantity = quantity;
        this.price = price;
    }

    public MenuItems(String menuId, String category, String menuName, Long price) {
        this.menuId = menuId;
        this.category = category;
        this.menuName = menuName;
        this.available = true;
        this.price = price;
        this.timestampLastChanged = Utils.getCurrentTimeStamp();
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }

    public void setTimestampLastChanged(HashMap<String, Object> timestampLastChanged) {
        this.timestampLastChanged = timestampLastChanged;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
