package com.akshay.fooddelivery_partner.Orders;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.akshay.fooddelivery_partner.Orders.Comleted.Completed;
import com.akshay.fooddelivery_partner.Orders.Ongoing.Ongoing;

public class ViewPagerAdapter extends FragmentPagerAdapter{

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Ongoing ongoing = new Ongoing();
                return ongoing;
            case 1:
                Completed completed = new Completed();
                return completed;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Ongoing";
            case 1:
                return "Completed";
        }
        return null;
    }
}
