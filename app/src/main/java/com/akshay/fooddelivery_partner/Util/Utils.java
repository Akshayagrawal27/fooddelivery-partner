package com.akshay.fooddelivery_partner.Util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Utils {

    public static HashMap<String, Object> getCurrentTimeStamp() {
        HashMap<String, Object> timestampJoined = new HashMap<>();
        timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

        return timestampJoined;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isPhoneNumberValid(String phoneNumber){
        if(Pattern.matches("[0-9]{10}", phoneNumber)){
            return true;
        }else
            return false;
    }

    public static String formattedPhoneNumber(String phoneNumber) {
        return "+91" + phoneNumber;
    }
}
