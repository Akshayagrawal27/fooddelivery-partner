package com.akshay.fooddelivery_partner.Orders.Comleted;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.akshay.fooddelivery_partner.Model.ReceivedOrder;
import com.akshay.fooddelivery_partner.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by Akshay on 15-05-2018.
 */

class CompletedOrderRecyclerAdapter extends FirebaseRecyclerAdapter<ReceivedOrder, CompletedOrderRecyclerAdapter.CompletedOrderRecyclerViewHolder> {

    static private CompletedOrderListClickListener mOnClickListener;

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
    public CompletedOrderRecyclerAdapter(Class<ReceivedOrder> modelClass,
                                         int modelLayout,
                                         Class<CompletedOrderRecyclerViewHolder> viewHolderClass, Query ref,
                                         CompletedOrderListClickListener mOnClickListener) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    protected void populateViewHolder(CompletedOrderRecyclerViewHolder viewHolder, ReceivedOrder receivedOrder, int position) {

        viewHolder.mOrderNumber.setText(receivedOrder.getOrderId());
        viewHolder.mOrderStatus.setText(receivedOrder.getOrderStatus());
        viewHolder.mOrderAmount.setText(String.valueOf(receivedOrder.getOrderAmount()));
    }

    public interface CompletedOrderListClickListener {
        void onCompletedOrderListItemClick(int clickedItemIndex);
    }

    static public class CompletedOrderRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mOrderNumber, mOrderAmount, mOrderDate, mOrderStatus;
        public CompletedOrderRecyclerViewHolder(View itemView) {
            super(itemView);

            mOrderNumber = (TextView) itemView.findViewById(R.id.tv_order_number);
            mOrderAmount = (TextView) itemView.findViewById(R.id.tv_order_amount);
            mOrderDate = (TextView) itemView.findViewById(R.id.tv_order_date);
            mOrderStatus = (TextView) itemView.findViewById(R.id.tv_order_status);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onCompletedOrderListItemClick(getAdapterPosition());
        }
    }
}