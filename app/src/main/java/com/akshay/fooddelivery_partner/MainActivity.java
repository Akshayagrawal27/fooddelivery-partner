package com.akshay.fooddelivery_partner;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.akshay.fooddelivery_partner.Menus.AddMenuDialogFragment;
import com.akshay.fooddelivery_partner.Menus.Menus;
import com.akshay.fooddelivery_partner.Model.MenuItems;
import com.akshay.fooddelivery_partner.Model.ReceivedOrder;
import com.akshay.fooddelivery_partner.Orders.Orders;
import com.akshay.fooddelivery_partner.Util.Constants;
import com.akshay.fooddelivery_partner.Util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akshay on 13-05-2018.
 */

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = new Orders();

        //Launch Fragment for the First Time
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_container, fragment).addToBackStack(null).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_orders:
                    fragment = new Orders();
                    break;
                case R.id.navigation_menus:
                    fragment = new Menus();
                    break;
                case R.id.navigation_profile:
                    ReceivedOrder receivedOrder = new ReceivedOrder();
                    /*List<MenuItems> orderItems = new ArrayList();
                    orderItems.add(new MenuItems("123", "Soup", "tomato soup", 80L));
                    orderItems.add(new MenuItems("456", "Soup", "Potato Soup", 90L));
                    orderItems.add(new MenuItems("654", "Rice", "jeera rice", 120L));

                    String orderId = FirebaseUtil.getRestaurantReceivedOrderReference().push().getKey();

                    FirebaseUtil.getRestaurantReceivedOrderReference().child(orderId).setValue(new ReceivedOrder(orderId, orderItems, "ongoing"));
                    */break;
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_logout){
            FirebaseAuth.getInstance().signOut();
            return true;
        }
        return false;
    }

    public void showAddMenuItemDialog(View view) {
        DialogFragment dialog = AddMenuDialogFragment.newInstance();
        dialog.show(getFragmentManager(), Constants.KEY_DIALOG_ADD_MENU);
    }
}
