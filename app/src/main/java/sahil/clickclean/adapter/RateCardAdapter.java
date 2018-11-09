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
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_ratecard, parent, false);
        return new OrderViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int position) {
        final RateCard current = listRateCard.get(position);
        holder.cloth.setText(current.getCloth());
        holder.washandiron.setText(current.getWashandiron());
        holder.wash.setText(current.getWash());
        holder.iron.setText(current.getIron());

    }

    @Override
    public int getItemCount() {
        return listRateCard.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView cloth, washandiron, wash, iron;


        private OrderViewHolder(View itemView) {
            super(itemView);

            cloth=itemView.findViewById(R.id.tag_clothes);
            washandiron = itemView.findViewById(R.id.tag_washandiron);
            wash = itemView.findViewById(R.id.tag_wash);
            iron = itemView.findViewById(R.id.tag_iron);

        }

        @Override
        public void onClick(View v) {
            if (rcvItemClickListener != null) {
                rcvItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}