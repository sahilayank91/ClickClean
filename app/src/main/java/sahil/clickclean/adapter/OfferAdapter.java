package sahil.clickclean.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.internal.cache.CacheStrategy;
import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;
import sahil.clickclean.Views.SchedulePickup;
import sahil.clickclean.interfaces.RCVItemClickListener;
import sahil.clickclean.model.Offer;
import sahil.clickclean.model.Order;
import sahil.clickclean.model.RateCard;
import sahil.clickclean.utilities.Server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {

    private ArrayList<Offer> listOffers;
    private RCVItemClickListener rcvItemClickListener;
    Context con;
    private static Set<String> user = new HashSet<>();
    public OfferAdapter(Context context, ArrayList<Offer> listOffers) {
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
        final Offer current = listOffers.get(position);
        Glide.with(con).load(current.getUrl()).apply(new RequestOptions().placeholder(R.drawable.placeholder_image).fitCenter()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              checkAvailability(current);

            }
        });
    }

    private void checkAvailability(final Offer current) {

       new checkIfUserHasUsedCoupon(current).execute();

    }




    @SuppressLint("StaticFieldLeak")
    class checkIfUserHasUsedCoupon extends AsyncTask<String, String, String> {
        boolean success = false;
        HashMap<String, String> params = new HashMap<>();
        private Offer current;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            params.put("offerid", current.get_id());
            params.put("user", SharedPreferenceSingleton.getInstance(con.getApplicationContext()).getString("_id", "Customer"));
        }
        checkIfUserHasUsedCoupon(final Offer offer){
                this.current = offer;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if (jsonObject.getString("success").equals("true")) {
                    Toast.makeText(con,"You have already used this coupon", Toast.LENGTH_SHORT).show();
                } else {
                    final Intent intent = new Intent(con, SchedulePickup.class);
                    intent.putExtra("service", current.getService());
                    intent.putExtra("type", "Normal");
                    intent.putExtra("percentage", current.getPercentage());
                    intent.putExtra("code", current.getCode());
                    intent.putExtra("offerid",current.get_id());
                    con.startActivity(intent);
//                    new createUserOfferRelation(current.get_id(),current).execute();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                Gson gson = new Gson();
                String json = gson.toJson(params);
                result = Server.post(con.getResources().getString(R.string.checkIfUserHasUsedCoupon), json);
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }


            System.out.println("Result:" + result);
            return result;
        }
    }

    @SuppressLint("StaticFieldLeak")
    class createUserOfferRelation extends AsyncTask<String, String, String> {
        boolean success = false;
        HashMap<String, String> params = new HashMap<>();
        private String offerid;
        private Offer current;

        createUserOfferRelation(String id,Offer offer){
        this.offerid = id;
        this.current = offer;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            params.put("offerid",offerid);
            params.put("user", SharedPreferenceSingleton.getInstance(con.getApplicationContext()).getString("_id", "Customer"));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                Log.e("jsonobeflsd:",current.getService());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if (jsonObject.getString("success").equals("true")) {
                    final Intent intent = new Intent(con, SchedulePickup.class);
                    intent.putExtra("service", current.getService());
                    intent.putExtra("type", "Normal");
                    intent.putExtra("percentage", current.getPercentage());
                    intent.putExtra("code", current.getCode());
                    con.startActivity(intent);

                } else {
                    Toast.makeText(con,"You have already used this coupon", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                Gson gson = new Gson();
                String json = gson.toJson(params);
                result = Server.post(con.getResources().getString(R.string.createUserOfferRelation), json);
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }


            System.out.println("Result:" + result);
            return result;
        }
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