package com.akshay.fooddelivery_partner.Orders.Comleted;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akshay.fooddelivery_partner.Model.ReceivedOrder;
import com.akshay.fooddelivery_partner.Orders.ShowOrderItemsDialog;
import com.akshay.fooddelivery_partner.R;
import com.akshay.fooddelivery_partner.Util.Constants;
import com.akshay.fooddelivery_partner.Util.FirebaseUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class Completed extends Fragment implements CompletedOrderRecyclerAdapter.CompletedOrderListClickListener {

    private View view;

    private DatabaseReference receivedOrderListReference;
    private Query completedOrderListQuery;
    private RecyclerView mRecyclerCompletedOrderList;
    private CompletedOrderRecyclerAdapter mCompletedOrderRecyclerAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.completed, container, false);

        receivedOrderListReference = FirebaseUtil.getRestaurantReceivedOrderReference();
        completedOrderListQuery = receivedOrderListReference.orderByChild(Constants.FIREBASE_PROPERTY_ORDER_STATUS)
                                                            .equalTo(Constants.ORDER_STATUS_COMPLETED);

        mRecyclerCompletedOrderList = (RecyclerView) view.findViewById(R.id.recycler_completed_order);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerCompletedOrderList.setLayoutManager(layoutManager);

        mCompletedOrderRecyclerAdapter = new CompletedOrderRecyclerAdapter(ReceivedOrder.class,
                R.layout.list_order,
                CompletedOrderRecyclerAdapter.CompletedOrderRecyclerViewHolder.class,
                completedOrderListQuery, this);

        mRecyclerCompletedOrderList.setAdapter(mCompletedOrderRecyclerAdapter);

        return view;
    }

    @Override
    public void onCompletedOrderListItemClick(int clickedItemIndex) {

        String orderKey = mCompletedOrderRecyclerAdapter.getRef(clickedItemIndex).getKey();
        String orderStatus = mCompletedOrderRecyclerAdapter.getItem(clickedItemIndex).getOrderStatus();

        DialogFragment dialog = ShowOrderItemsDialog.newInstance(orderKey, orderStatus);
        dialog.show(getActivity().getFragmentManager(), Constants.KEY_SHOW_ORDER_MENU_ITEMS);
    }
}
