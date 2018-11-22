package sahil.clickclean.Views.fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;
import sahil.clickclean.Views.MainActivity;
import sahil.clickclean.adapter.OrderAdapter;
import sahil.clickclean.adapter.WashermanOrderAdapter;
import sahil.clickclean.model.Order;
import sahil.clickclean.utilities.Server;

/**
 * Provides UI for the view with Cards.
 */
public class TodayFragment extends Fragment {
    public static ArrayList<Order> listOrders = new ArrayList<>();
    private WashermanOrderAdapter adapter;
    RecyclerView recyclerView;
    View view;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) view = inflater.inflate(R.layout.fragment_today, container, false);
        else return view;
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // Refresh items
                new GetOrders().execute();
            }
        });
        recyclerView = view.findViewById(R.id.order_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new WashermanOrderAdapter(getContext(), listOrders,"Today");
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        new GetOrders().execute();
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    class GetOrders extends AsyncTask<String, String, String> {
        HashMap<String, String> params = new HashMap<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String userid = SharedPreferenceSingleton.getInstance(getContext()).getString("_id","User Not Registered");
            params.put("washerman_id",userid);

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            Date date = calendar.getTime();
            int day = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            params.put("day",String.valueOf(day));
            params.put("month",String.valueOf(month));
            params.put("year",String.valueOf(year));
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                Gson gson = new Gson();
                String json = gson.toJson(params);

                result = Server.post(getResources().getString(R.string.getTodayOrders),json);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("result of getting order",s);


            try {

                JSONObject jsonObject =new JSONObject(s);

                String success = jsonObject.getString("success");
                if (!success.equals("true")) {
                    Toast.makeText(getContext(),"Some error occured in getting Data..Please check your internet connection",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(),MainActivity.class);
                    startActivity(intent);

                }else{
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Log.e("array:" ,jsonArray.toString());
                    listOrders.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);
                        Order current = new Order(post);
                        listOrders.add(current);
                    }
                    adapter.notifyDataSetChanged();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


    }


}