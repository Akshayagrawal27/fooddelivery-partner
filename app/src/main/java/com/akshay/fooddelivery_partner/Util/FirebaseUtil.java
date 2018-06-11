package com.akshay.fooddelivery_partner.Util;

import com.akshay.fooddelivery_partner.Model.MenuCategory;
import com.akshay.fooddelivery_partner.Model.MenuItems;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Akshay on 13-05-2018.
 */

public class FirebaseUtil {

    public static DatabaseReference getRestaurantMenuListReference() {
        return FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_MENU_ITEM_LIST);
    }

    public static DatabaseReference getMenuCategoryListReference(){
        return FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_MENU_MENUCATEGORY);
    }

    public static DatabaseReference getRestaurantReceivedOrderReference(){
        return FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_RECEIVED_ORDER);
    }

    public static DatabaseReference getOrderOrderedItemsReference(String orderKey){
        return getRestaurantReceivedOrderReference().child(orderKey).child(Constants.FIREBASE_PROPERTY_ORDERED_ITEMS);
    }
    public static void addMenuItem(MenuItems menuItems) {
        getRestaurantMenuListReference().child(menuItems.getMenuId()).setValue(menuItems);
    }

    public static void addMenuCategory(MenuCategory menuCategory) {
        getMenuCategoryListReference().child(menuCategory.getCategoryName()).setValue(menuCategory);
    }

    public static void updateOrderStatus(String orderKey, String status) {

        HashMap<String, Object> updateOrderStatus = new HashMap<String, Object>();
        updateOrderStatus.put(Constants.FIREBASE_LOCATION_RECEIVED_ORDER + "/" + orderKey + "/" + Constants.FIREBASE_PROPERTY_ORDER_STATUS , status);
        updateOrderStatus.put(Constants.FIREBASE_LOCATION_TRACK_ORDER + "/" + Constants.FIREBASE_PROPERTY_ORDER_STATUS, status);
        updateOrderStatus.put(Constants.FIREBASE_LOCATION_USER_ORDERS + "/" + orderKey + "/" + Constants.FIREBASE_PROPERTY_ORDER_STATUS, status);

        FirebaseDatabase.getInstance().getReference().updateChildren(updateOrderStatus);
    }
}
