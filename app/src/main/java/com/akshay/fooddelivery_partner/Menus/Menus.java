package com.akshay.fooddelivery_partner.Menus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akshay.fooddelivery_partner.Model.MenuCategory;
import com.akshay.fooddelivery_partner.R;
import com.akshay.fooddelivery_partner.Util.FirebaseUtil;
import com.google.firebase.database.DatabaseReference;


/**
 * Created by Akshay on 13-05-2018.
 */

public class Menus extends Fragment implements MenuCategoryRecyclerAdapter.MenuCategoryListClickListener{

    private static final String TAG = "OrderFragment";
    private static View view;

    private DatabaseReference menuCategoryListReference;

    private RecyclerView mRecyclerMenuCategoryList;
    private MenuCategoryRecyclerAdapter mMenuCategoryRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nav_menus, container, false);

        menuCategoryListReference = FirebaseUtil.getMenuCategoryListReference();

        mRecyclerMenuCategoryList = (RecyclerView) view.findViewById(R.id.recycler_menus);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerMenuCategoryList.setLayoutManager(layoutManager);

        mMenuCategoryRecyclerAdapter = new MenuCategoryRecyclerAdapter(MenuCategory.class,
                R.layout.list_menu_category,
                MenuCategoryRecyclerAdapter.MenuCategoryRecyclerViewHolder.class,
                menuCategoryListReference, this, getContext());

        mRecyclerMenuCategoryList.setAdapter(mMenuCategoryRecyclerAdapter);

        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if (swipeDir == ItemTouchHelper.LEFT){

                }
            }
        }).attachToRecyclerView(mRecyclerBrandList);*/

        return view;
    }

    @Override
    public void onMenuCategoryListItemClick(int clickedItemIndex) {
        Toast.makeText(getContext(), mMenuCategoryRecyclerAdapter.getItem(clickedItemIndex).getCategoryName(), Toast.LENGTH_SHORT).show();
    }
}
