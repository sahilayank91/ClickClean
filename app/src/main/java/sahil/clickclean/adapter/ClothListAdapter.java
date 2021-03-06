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
import sahil.clickclean.Views.fragment.CreateOrderFragment;
import sahil.clickclean.Views.fragment.SelectServiceFragment;
import sahil.clickclean.interfaces.RCVItemClickListener;
import sahil.clickclean.model.RateCard;


import java.util.ArrayList;

import static sahil.clickclean.Views.fragment.CreateOrderFragment.order;


public class ClothListAdapter extends RecyclerView.Adapter<ClothListAdapter.OrderViewHolder> {

    private ArrayList<RateCard> listRateCard;
    private RCVItemClickListener rcvItemClickListener;
    private String service,type;
    private Context context;


    public ClothListAdapter(Context c, ArrayList<RateCard> listRateCard,String service,String type) {
        context = c;
        this.listRateCard = listRateCard;
        this.service = service;
        this.type = type;
    }

    public ClothListAdapter(Context c, ArrayList<RateCard> listRateCard,String service) {
        context = c;
        this.listRateCard = listRateCard;
        this.service = service;
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


        switch (service){
            case "Ironing":
                if(current.getIron().equals("-")){
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                    holder.setIsRecyclable(false);
                }
                break;
            case "Wash and Fold":
                if(current.getWash().equals("-")){
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                    holder.setIsRecyclable(false);
                }

                break;
            case "Wash and Iron":
                if(current.getWashandiron().equals("-")){
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                    holder.setIsRecyclable(false);
                }
                break;
            default:

                break;
        }

        Glide.with(context).load(current.getIcon()).transition(DrawableTransitionOptions.withCrossFade()).into(holder.iconView);
        holder.iconView.setScaleType(ImageView.ScaleType.FIT_XY);


        holder.cloth.setText(current.getCloth());
        holder.cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(holder.num.getText().toString());
                n++;
                switch (service) {
                    case "Ironing":
                        if (!current.getIron().equals("-")) {
                            CreateOrderFragment.total += Integer.parseInt(current.getIron());
                            holder.num.setText(String.valueOf(n));
                            order.put(current.getCloth(),String.valueOf(n));
                            } else {
                            Toast.makeText(context, "Wash and Iron facility not available for this type of cloth", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case "Wash and Fold":
                        if (!current.getWash().equals("-")) {
                            CreateOrderFragment.total += Integer.parseInt(current.getWash());
                            holder.num.setText(String.valueOf(n));
                            order.put(current.getCloth(),String.valueOf(n));


                        } else {
                            Toast.makeText(context, "Wash facility not available for this type of cloth", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case "Wash and Iron":
                        if (!current.getWashandiron().equals("-")) {
                            CreateOrderFragment.total += Integer.parseInt(current.getWashandiron());
                            holder.num.setText(String.valueOf(n));
                            order.put(current.getCloth(),String.valueOf(n));
                        } else {
                            Toast.makeText(context, "Iron facility not available for this type of cloth", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case "Dryclean":
                        if (!current.getDryclean().equals("-")) {
                            CreateOrderFragment.total += Integer.parseInt(current.getDryclean());
                            holder.num.setText(String.valueOf(n));
                            order.put(current.getCloth(),String.valueOf(n));
                        } else {
                            Toast.makeText(context, "Iron facility not available for this type of cloth", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    default:

                        break;
                }

            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(holder.num.getText().toString());
                n--;
                switch (service) {
                    case "Ironing":
                        if(Integer.parseInt(holder.num.getText().toString())>0){
                            if (!current.getIron().equals("-")) {
                                CreateOrderFragment.total -= Integer.parseInt(current.getIron());
                                holder.num.setText(String.valueOf(n));
                                order.put(current.getCloth(),String.valueOf(n));
                            } else {
                                Toast.makeText(context, "Wash and Iron facility not available for this type of cloth", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(context, "Can't Decrease value", Toast.LENGTH_SHORT).show();

                        }


                        break;
                    case "Wash and Fold":
                        if(Integer.parseInt(holder.num.getText().toString())>0) {
                            if (!current.getWash().equals("-")) {
                                CreateOrderFragment.total -= Integer.parseInt(current.getWash());
                                holder.num.setText(String.valueOf(n));
                                order.put(current.getCloth(), String.valueOf(n));


                            } else {
                                Toast.makeText(context, "Wash facility not available for this type of cloth", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(context, "Can't Decrease value", Toast.LENGTH_SHORT).show();

                        }

                        break;
                    case "Wash and Iron":
                        if(Integer.parseInt(holder.num.getText().toString())>0) {
                            if (!current.getWashandiron().equals("-")) {
                                CreateOrderFragment.total -= Integer.parseInt(current.getWashandiron());
                                holder.num.setText(String.valueOf(n));
                                order.put(current.getCloth(), String.valueOf(n));
                            } else {
                                Toast.makeText(context, "Iron facility not available for this type of cloth", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(context, "Can't Decrease value", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case "Dryclean":
                        if(Integer.parseInt(holder.num.getText().toString())>0) {

                            if (!current.getDryclean().equals("-")) {
                                CreateOrderFragment.total -= Integer.parseInt(current.getDryclean());
                                holder.num.setText(String.valueOf(n));
                                order.put(current.getCloth(), String.valueOf(n));
                            } else {
                                Toast.makeText(context, "Iron facility not available for this type of cloth", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(context, "Can't Decrease value", Toast.LENGTH_SHORT).show();

                        }

                        break;

                    default:

                        break;
                }

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