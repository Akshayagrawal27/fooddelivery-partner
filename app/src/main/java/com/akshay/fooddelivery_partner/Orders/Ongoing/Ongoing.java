package com.akshay.fooddelivery_partner.Orders.Ongoing;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akshay.fooddelivery_partner.Model.ReceivedOrder;
import com.akshay.fooddelivery_partner.Model.RestaurantInfo;
import com.akshay.fooddelivery_partner.Orders.ShowOrderItemsDialog;
import com.akshay.fooddelivery_partner.R;
import com.akshay.fooddelivery_partner.Util.Constants;
import com.akshay.fooddelivery_partner.Util.FirebaseUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class Ongoing extends Fragment implements OngoingOrderRecyclerAdapter.OngoingOrderListClickListener {

    private View view;

    private DatabaseReference receivedOrderListReference;
    private Query ongoingOrderListQuery;
    private RecyclerView mRecyclerOngoingOrderList;
    private OngoingOrderRecyclerAdapter mOngoingOrderRecyclerAdapter;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.ongoing, container, false);

        receivedOrderListReference = FirebaseUtil.getRestaurantReceivedOrderReference();
        ongoingOrderListQuery = receivedOrderListReference.orderByChild(Constants.FIREBASE_PROPERTY_ORDER_STATUS);

        mRecyclerOngoingOrderList = (RecyclerView) view.findViewById(R.id.recycler_ongoing_order);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerOngoingOrderList.setLayoutManager(layoutManager);

        mOngoingOrderRecyclerAdapter = new OngoingOrderRecyclerAdapter(ReceivedOrder.class,
                R.layout.list_order,
                OngoingOrderRecyclerAdapter.OngoingOrderRecyclerViewHolder.class,
                ongoingOrderListQuery.startAt(Constants.ORDER_STATUS_ONGOING), this);

        mRecyclerOngoingOrderList.setAdapter(mOngoingOrderRecyclerAdapter);

        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if (*//*swipeDir == ItemTouchHelper.RIGHT*//* true){

                    String orderKey = mOngoingOrderRecyclerAdapter.getRef(viewHolder.getLayoutPosition()).getKey();
                    if (mOngoingOrderRecyclerAdapter.getItem(viewHolder.getLayoutPosition()).getOrderStatus().equals(Constants.ORDER_STATUS_PENDING)){
                        FirebaseUtil.updateOrderStatus(orderKey, Constants.ORDER_STATUS_COMPLETED);

                    }
                }
            }
        }).attachToRecyclerView(mRecyclerOngoingOrderList);*/
        return view;
    }

    @Override
    public void onOngoingOrderListItemClick(int clickedItemIndex) {

        String orderKey = mOngoingOrderRecyclerAdapter.getRef(clickedItemIndex).getKey();
        String orderStatus = mOngoingOrderRecyclerAdapter.getItem(clickedItemIndex).getOrderStatus();

        DialogFragment dialog = ShowOrderItemsDialog.newInstance(orderKey, orderStatus);
        dialog.show(getActivity().getFragmentManager(), Constants.KEY_SHOW_ORDER_MENU_ITEMS);
    }
}
