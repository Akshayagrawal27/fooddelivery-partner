package com.akshay.fooddelivery_partner.Menus;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.akshay.fooddelivery_partner.Model.MenuCategory;
import com.akshay.fooddelivery_partner.Model.MenuItems;
import com.akshay.fooddelivery_partner.R;
import com.akshay.fooddelivery_partner.Util.FirebaseUtil;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class AddMenuDialogFragment extends DialogFragment {

    EditText mMenuCategory, mMenuItemName, mMenuItemPrice;
    private static List menuCategoryLists  = new ArrayList();

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static AddMenuDialogFragment newInstance() {
        AddMenuDialogFragment addListDialogFragment = new AddMenuDialogFragment();
        Bundle bundle = new Bundle();
        addListDialogFragment.setArguments(bundle);
        return addListDialogFragment;
}

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_menu, null);
        mMenuCategory = (EditText) rootView.findViewById(R.id.et_menu_category);
        mMenuItemName = (EditText) rootView.findViewById(R.id.et_menu_item_name);
        mMenuItemPrice = (EditText) rootView.findViewById(R.id.et_menu_item_price);

        //menuCategoryLists = new ArrayList();

        /**
         * Call addBrandList() when user taps "Done" keyboard action
         *//*
        mEditTextListName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addBrandList();
                    getDialog().dismiss();
                }
                return true;
            }
        });*/

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton(R.string.positive_button_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addMenuItem();
                    }
                });

        return builder.create();
    }

    /**
     * Add new brand list
     */
    public void addMenuItem() {

        DatabaseReference restaurantMenuListReference= FirebaseUtil.getRestaurantMenuListReference();
        String menuCategory = mMenuCategory.getText().toString();
        String menuItemName = mMenuItemName.getText().toString();
        Long menuItemPrice = Long.parseLong(mMenuItemPrice.getText().toString());
        String menuId = restaurantMenuListReference.push().getKey();

        if (!menuCategory.equals("") && !menuItemName.equals("") && !menuItemPrice.equals("")){
            FirebaseUtil.addMenuItem(new MenuItems(menuId, menuCategory, menuItemName, menuItemPrice));

            if (!menuCategoryLists.contains(menuCategory)){
                menuCategoryLists.add(menuCategory);
                FirebaseUtil.addMenuCategory(new MenuCategory(menuCategory));
            }
        }
    }
}

