package com.akshay.fooddelivery_partner.Orders;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.akshay.fooddelivery_partner.Menus.MenuItemRecyclerAdapter;
import com.akshay.fooddelivery_partner.Model.MenuItems;
import com.akshay.fooddelivery_partner.R;
import com.akshay.fooddelivery_partner.Util.Constants;
import com.akshay.fooddelivery_partner.Util.FirebaseUtil;
import com.google.firebase.database.DatabaseReference;

public class ShowOrderItemsDialog extends DialogFragment implements MenuItemRecyclerAdapter.MenuItemListClickListener {

    String orderKey, orderStatus;

    RecyclerView mRecyclerShowMenuItemsList;
    MenuItemRecyclerAdapter mMenuCategoryRecyclerAdapter;

    DatabaseReference orderMenuItemsListReference;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static ShowOrderItemsDialog newInstance(String orderKey, String orderStatus) {
        ShowOrderItemsDialog addListDialogFragment = new ShowOrderItemsDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FIREBASE_PROPERTY_ORDER_STATUS, orderStatus);
        bundle.putString(Constants.KEY_ORDER_KEY, orderKey);
        addListDialogFragment.setArguments(bundle);
        return addListDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderKey = getArguments().getString(Constants.KEY_ORDER_KEY);
        orderStatus = getArguments().getString(Constants.FIREBASE_PROPERTY_ORDER_STATUS);
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
        View rootView = inflater.inflate(R.layout.dialog_show_menu_items, null);

       mRecyclerShowMenuItemsList = (RecyclerView) rootView.findViewById(R.id.recycler_show_menu_items);

        orderMenuItemsListReference = FirebaseUtil.getOrderOrderedItemsReference(orderKey);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerShowMenuItemsList.setLayoutManager(layoutManager);

        mMenuCategoryRecyclerAdapter = new MenuItemRecyclerAdapter(MenuItems.class,
                R.layout.list_menu_items,
                MenuItemRecyclerAdapter.MenuItemRecyclerViewHolder.class,
                orderMenuItemsListReference, this, Constants.KEY_DIALOG_ORDERED_ITEMS);

        mRecyclerShowMenuItemsList.setAdapter(mMenuCategoryRecyclerAdapter);

        String positiveButtonText = null;

        if (orderStatus.equals(Constants.ORDER_STATUS_ONGOING))
            positiveButtonText = Constants.ORDER_STATUS_PREPARED;
        else if (orderStatus.equals(Constants.ORDER_STATUS_PENDING))
            positiveButtonText = Constants.ORDER_STATUS_COMPLETED;

        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        if (orderStatus.equals(Constants.ORDER_STATUS_ONGOING))
                            FirebaseUtil.updateOrderStatus(orderKey, Constants.ORDER_STATUS_PENDING);
                        else if (orderStatus.equals(Constants.ORDER_STATUS_PENDING))
                            FirebaseUtil.updateOrderStatus(orderKey, Constants.ORDER_STATUS_COMPLETED);

                        dialog.dismiss();
                    }
                }).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onMenuItemListItemClick(int clickedItemIndex) {

    }
}

