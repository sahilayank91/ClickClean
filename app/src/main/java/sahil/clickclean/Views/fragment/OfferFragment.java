package sahil.clickclean.Views.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import sahil.clickclean.R;
import sahil.clickclean.Views.MainActivity;
import sahil.clickclean.Views.UploadImage;
import sahil.clickclean.adapter.OfferAdapter;
import sahil.clickclean.adapter.OrderAdapter;
import sahil.clickclean.adapter.WashermanOrderAdapter;
import sahil.clickclean.interfaces.RCVItemClickListener;
import sahil.clickclean.model.Offer;
import sahil.clickclean.model.Order;
import sahil.clickclean.utilities.Server;

public class OfferFragment extends Fragment implements RCVItemClickListener{
    private ArrayList<Offer> offers = new ArrayList<>();
    private OfferAdapter offerAdapter;
    public OfferFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        offerAdapter = new OfferAdapter(getActivity(), offers);
        recyclerView.setAdapter(offerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new getOffer().execute();
        return recyclerView;
    }


    @SuppressLint("StaticFieldLeak")
    class getOffer extends AsyncTask<String, String, String> {
        boolean success = false;
        HashMap<String, String> params = new HashMap<>();



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (success) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                offers.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject post = null;
                    try {
                        post = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Offer current = null;
                    try {
                        current = new Offer(post);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    offers.add(current);
                }
                offerAdapter.notifyDataSetChanged();


            } else {
                Toast.makeText(getContext(), R.string.error, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                Gson gson = new Gson();
                String json = gson.toJson(params);
                result = Server.post(getResources().getString(R.string.getOffer),json);
                success = true;
            } catch (Exception e){
                e.printStackTrace();
            }



            System.out.println("Result:" + result);
            return result;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
