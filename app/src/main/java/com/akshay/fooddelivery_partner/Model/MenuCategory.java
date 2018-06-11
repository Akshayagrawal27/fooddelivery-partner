package com.akshay.fooddelivery_partner.Model;

public class MenuCategory {

    private String categoryName;

    public MenuCategory() {
    }

    public MenuCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
