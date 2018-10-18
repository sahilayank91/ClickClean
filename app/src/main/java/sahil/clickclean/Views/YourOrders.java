package sahil.clickclean.Views;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import sahil.clickclean.R;
import sahil.clickclean.adapter.OrderAdapter;
import sahil.clickclean.interfaces.RCVItemClickListener;
import sahil.clickclean.model.Order;
import sahil.clickclean.model.UserData;
import sahil.clickclean.utilities.Server;

import static android.view.View.GONE;

public class YourOrders extends Activity implements RCVItemClickListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static ArrayList<Order> listOrders = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_orders);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // Refresh items

//               prepareOrderItems();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.order_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OrderAdapter(this, listOrders);
        recyclerView.setAdapter(adapter);
        adapter.setRcvItemClickListener(this);

//        prepareOrderItems();




    }
    private void prepareOrderItems() {
         new GetOrders().execute();
    }

    @Override
    public void onItemClick(View view, int position) {

    }


    @SuppressLint("StaticFieldLeak")
    class GetOrders extends AsyncTask<String, String, String> {
        HashMap<String, String> params = new HashMap<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String userid = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("_id","");
            params.put("_id",userid);
            mSwipeRefreshLayout.setRefreshing(true);



        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                Gson gson = new Gson();
                String json = gson.toJson(params);

              result = Server.post(getResources().getString(R.string.getOrder),json);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            mSwipeRefreshLayout.setRefreshing(false);


            try {

                JSONArray jsonArray = new JSONArray(s);
                listOrders.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject post = jsonArray.getJSONObject(i);
                    Order current = new Order(post);
                    listOrders.add(current);

                }

                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(GONE);



        }


    }

}
