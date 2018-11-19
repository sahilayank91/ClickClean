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
import sahil.clickclean.model.RateCard;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class RateCardAdapter extends RecyclerView.Adapter<RateCardAdapter.OrderViewHolder> {

    private ArrayList<RateCard> listRateCard;
    private RCVItemClickListener rcvItemClickListener;
    private String service;
    private Context context;
    public RateCardAdapter(Context context, ArrayList<RateCard> listRateCard) {
        this.listRateCard = listRateCard;
        this.context  = context;
    }

    public RateCardAdapter(Context c, ArrayList<RateCard> listRateCard,String service) {
        context = c;
        this.listRateCard = listRateCard;
        this.service = service;
    }


    public void setRcvItemClickListener(RCVItemClickListener rcvItemClickListener) {
        this.rcvItemClickListener = rcvItemClickListener;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_itemcost, parent, false);
        return new OrderViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int position) {
        final RateCard current = listRateCard.get(position);
        switch (service) {
            case "Wash and Iron":
                holder.cost.setText(current.getWashandiron());

                break;
            case "Wash":
                holder.cost.setText(current.getWash());

                break;
            case "Iron":
                holder.cost.setText(current.getIron());
                break;
            case "Dryclean":
                holder.cost.setText(current.getDryclean());
                break;
        }
        holder.cloth.setText(current.getCloth());
//        holder.wash.setText(current.getWash());
//        holder.iron.setText(current.getIron());

    }

    @Override
    public int getItemCount() {
        return listRateCard.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView cloth, cost, wash, iron;


        private OrderViewHolder(View itemView) {
            super(itemView);

            cloth=itemView.findViewById(R.id.tag_clothes);
            cost = itemView.findViewById(R.id.tag_cost);
//            switch (service) {
//                case "Wash and Iron":
//                    washandiron.setVisibility(View.VISIBLE);
//                    wash.setVisibility(View.GONE);
//                    iron.setVisibility(View.GONE);
//                    break;
//                case "Wash":
//                    washandiron.setVisibility(View.GONE);
//                    wash.setVisibility(View.VISIBLE);
//                    iron.setVisibility(View.GONE);
//                    break;
//                case "Iron":
//                    washandiron.setVisibility(View.GONE);
//                    wash.setVisibility(View.GONE);
//                    iron.setVisibility(View.VISIBLE);
//                    break;
//            }

        }

        @Override
        public void onClick(View v) {
            if (rcvItemClickListener != null) {
                rcvItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}