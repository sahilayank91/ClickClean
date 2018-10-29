package sahil.clickclean.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import sahil.clickclean.R;
import sahil.clickclean.interfaces.RCVItemClickListener;
import sahil.clickclean.model.Order;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SS Verma on 14-03-2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<Order> listOrders;
    private RCVItemClickListener rcvItemClickListener;

    public OrderAdapter(Context context, ArrayList<Order> listOrders) {
//        Context context1 = context;
        this.listOrders = listOrders;
    }

    public void setRcvItemClickListener(RCVItemClickListener rcvItemClickListener) {
        this.rcvItemClickListener = rcvItemClickListener;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_orders, parent, false);
        return new OrderViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int position) {
        final Order current = listOrders.get(position);
        holder.orderid.setText(current.get_id());
        holder.orderstatus.setText(current.getOrderstatus());
        holder.orderpickupdate.setText(current.getOrderpickupdate());
        holder.orderdate.setText(current.getCreate_time());
    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView orderid, orderdate, orderpickupdate, orderstatus;


        private OrderViewHolder(View itemView) {
            super(itemView);

            orderid= itemView.findViewById(R.id.order_id);
            orderdate = itemView.findViewById(R.id.order_date);
            orderpickupdate = itemView.findViewById(R.id.order_pickup_date);
            orderstatus = itemView.findViewById(R.id.order_status);
            itemView.setOnClickListener(this);
            orderstatus.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (rcvItemClickListener != null) {
                rcvItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}