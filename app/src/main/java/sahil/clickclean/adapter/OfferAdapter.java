package sahil.clickclean.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import okhttp3.internal.cache.CacheStrategy;
import sahil.clickclean.R;
import sahil.clickclean.interfaces.RCVItemClickListener;
import sahil.clickclean.model.Order;
import sahil.clickclean.model.RateCard;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {

    private ArrayList<String> listOffers;
    private RCVItemClickListener rcvItemClickListener;
    Context con;
    public OfferAdapter(Context context, ArrayList<String> listOffers) {
        this.listOffers = listOffers;
        con = context;
    }

    public void setRcvItemClickListener(RCVItemClickListener rcvItemClickListener) {
        this.rcvItemClickListener = rcvItemClickListener;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_offer, parent, false);
        return new OfferViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final OfferViewHolder holder, int position) {
        String current = listOffers.get(position);
        Glide.with(con).load(current).apply(new RequestOptions().placeholder(R.drawable.placeholder_image).fitCenter()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return listOffers.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView imageView;

        private OfferViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.offerview);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        }

        @Override
        public void onClick(View v) {
            if (rcvItemClickListener != null) {
                rcvItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}