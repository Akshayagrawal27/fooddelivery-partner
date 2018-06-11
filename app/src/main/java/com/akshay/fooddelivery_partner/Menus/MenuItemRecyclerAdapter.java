package com.akshay.fooddelivery_partner.Menus;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akshay.fooddelivery_partner.Model.MenuItems;
import com.akshay.fooddelivery_partner.R;
import com.akshay.fooddelivery_partner.Util.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by Akshay on 13-05-2018.
 */

public class MenuItemRecyclerAdapter extends FirebaseRecyclerAdapter<MenuItems, MenuItemRecyclerAdapter.MenuItemRecyclerViewHolder> {

    static private MenuItemListClickListener mOnClickListener;
    String key;

    /**
     * @param modelClass      Firebase will marshall the data at a location into
     *                        an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list.
     *                        You will be responsible for populating an instance of the corresponding
     *                        view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location,
     *                        using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public MenuItemRecyclerAdapter(Class<MenuItems> modelClass,
                                   int modelLayout,
                                   Class<MenuItemRecyclerViewHolder> viewHolderClass, Query ref,
                                   MenuItemListClickListener mOnClickListener, String key) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mOnClickListener = mOnClickListener;
        this.key = key;
    }

    @Override
    protected void populateViewHolder(MenuItemRecyclerViewHolder viewHolder, MenuItems menuItems, int position) {

        if (key.equals(Constants.KEY_DIALOG_ORDERED_ITEMS)){
            viewHolder.mMenuItemName.setText(menuItems.getMenuName());
            viewHolder.mMenuItemPrice.setText("x" +String.valueOf(menuItems.getQuantity()));
        }else{
            viewHolder.mMenuItemName.setText(menuItems.getMenuName());
            viewHolder.mMenuItemPrice.setText(String.valueOf(menuItems.getPrice()));
        }
    }

    public interface MenuItemListClickListener {
        void onMenuItemListItemClick(int clickedItemIndex);
    }

    static public class MenuItemRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mMenuItemName, mMenuItemPrice;
        public MenuItemRecyclerViewHolder(View itemView) {
            super(itemView);

            mMenuItemName = (TextView) itemView.findViewById(R.id.tv_menu_item_name);
            mMenuItemPrice = (TextView) itemView.findViewById(R.id.tv_menu_item_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onMenuItemListItemClick(getAdapterPosition());
        }
    }
}