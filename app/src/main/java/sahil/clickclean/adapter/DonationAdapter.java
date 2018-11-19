package sahil.clickclean.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;
import sahil.clickclean.Views.fragment.CreateDonationFragment;
import sahil.clickclean.Views.fragment.CreateOrderFragment;
import sahil.clickclean.Views.fragment.SelectServiceFragment;
import sahil.clickclean.interfaces.RCVItemClickListener;
import sahil.clickclean.model.RateCard;


import java.util.ArrayList;

import static sahil.clickclean.Views.fragment.CreateOrderFragment.order;


public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.OrderViewHolder> {

    private ArrayList<RateCard> listRateCard;
    private RCVItemClickListener rcvItemClickListener;
    private String service,type;
    private Context context;

    public DonationAdapter(Context c, ArrayList<RateCard> listRateCard) {
        context = c;
        this.listRateCard = listRateCard;
        this.service = service;
        this.type = type;
    }

    public void setRcvItemClickListener(RCVItemClickListener rcvItemClickListener) {
        this.rcvItemClickListener = rcvItemClickListener;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_order_selection, parent, false);
        return new OrderViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int position) {
        final RateCard current = listRateCard.get(position);
        holder.cloth.setText(current.getCloth());
        Glide.with(context).load(current.getIcon()).transition(DrawableTransitionOptions.withCrossFade()).into(holder.iconView);
        holder.iconView.setScaleType(ImageView.ScaleType.FIT_XY);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(holder.num.getText().toString());
                n++;
                CreateDonationFragment.total+=1;
                holder.num.setText(String.valueOf(n));
                CreateDonationFragment.order.put(current.getCloth(),String.valueOf(n));

            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(holder.num.getText().toString());
                n--;
                CreateDonationFragment.total-=1;
                holder.num.setText(String.valueOf(n));
                CreateDonationFragment.order.put(current.getCloth(),String.valueOf(n));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRateCard.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView cloth,num;
        ImageView add,minus,iconView;


        private OrderViewHolder(View itemView) {
            super(itemView);

            cloth=itemView.findViewById(R.id.cloth_name);
            add = itemView.findViewById(R.id.cloth_add);
            minus = itemView.findViewById(R.id.cloth_minus);
            num  = itemView.findViewById(R.id.cloth_num);
            iconView = itemView.findViewById(R.id.imageView);


        }

        @Override
        public void onClick(View v) {
            if (rcvItemClickListener != null) {
                rcvItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}