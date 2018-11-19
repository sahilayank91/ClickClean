package sahil.clickclean.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;
import sahil.clickclean.Views.LoginActivity;
import sahil.clickclean.Views.MainActivity;
import sahil.clickclean.Views.YourOrders;
import sahil.clickclean.interfaces.RCVItemClickListener;
import sahil.clickclean.model.Order;
import sahil.clickclean.utilities.Server;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static java.security.AccessController.getContext;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<Order> listOrders;
    private RCVItemClickListener rcvItemClickListener;
    Context context1;
    public OrderAdapter(Context context, ArrayList<Order> listOrders) {
        context1 = context;
        this.listOrders = listOrders;
    }

    public void setRcvItemClickListener(RCVItemClickListener rcvItemClickListener) {
        this.rcvItemClickListener = rcvItemClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_orders, parent, false);
        return new OrderViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, final int position) {
        final Order current = listOrders.get(position);

        holder.orderid.setText(current.get_id());
        holder.orderstatus.setText(current.getOrderstatus());


        String jsDate = current.getOrderpickupdate();

        Date pickupdate=null;
        try {
             pickupdate = new SimpleDateFormat("yyyy-MM-dd").parse(jsDate);
            Log.e("date",pickupdate.toString());
            pickupdate.setHours(17);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.orderpickupdate.setText(pickupdate.toLocaleString());



        String orderDate = current.getCreate_time();
        Date order=null;
        try {
            order = new SimpleDateFormat("yyyy-MM-dd").parse(orderDate);
            Log.e("fsfsfsaf",order.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.orderdate.setText(order.toLocaleString().substring(0,12));
        holder.orderservice.setText(current.getOrderservice());
        Calendar calendar = Calendar.getInstance();
        if(pickupdate.getDay() == calendar.get(Calendar.DAY_OF_MONTH) && calendar.get(Calendar.HOUR_OF_DAY)>=17){
            holder.cancel.setEnabled(false);
            holder.cancel.setBackgroundColor(context1.getResources().getColor(R.color.black));
            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context1,"You can't cancel Order after 5:00 PM",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCancelDialog(v,holder.getAdapterPosition());
                }
            });


        }

        if(current.getOffer().equals("Yes")){
            holder.code.setText(current.getCode());
        }else{
            holder.code.setText("Not Applied");
        }

        holder.type.setText(current.getType());
        holder.pickupotp.setText(current.getPickup_otp());
        holder.deliveredotp.setText(current.getDelivered_otp());
    }
    private void openCancelDialog(View view, final int position){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context1);
        alertDialogBuilder.setMessage("Are you sure you want to cancel the Order");
        alertDialogBuilder.setIcon(R.drawable.logo);
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(context1,"You clicked yes button",Toast.LENGTH_LONG).show();

                                new CancelOrder().execute(listOrders.get(position).get_id());

                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView orderid, orderdate, orderpickupdate, orderstatus,orderservice,pickupotp,deliveredotp,type,code;
        Button cancel,edit;

        private OrderViewHolder(View itemView) {
            super(itemView);

            orderid= itemView.findViewById(R.id.order_id);
            orderdate = itemView.findViewById(R.id.order_date);
            orderpickupdate = itemView.findViewById(R.id.order_pickup_date);
            orderstatus = itemView.findViewById(R.id.order_status);
            orderservice = itemView.findViewById(R.id.order_service);
            cancel = itemView.findViewById(R.id.cancelbutton);
            pickupotp = itemView.findViewById(R.id.pickupotp);
            deliveredotp = itemView.findViewById(R.id.deliveredotp);
            type = itemView.findViewById(R.id.type);
            code = itemView.findViewById(R.id.code);
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



    @SuppressLint("StaticFieldLeak")
    class CancelOrder extends AsyncTask<String, String, String> {
        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(context1);
            progress.setMessage("Cancelling Order");
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                HashMap<String,String> params = new HashMap<>();
                params.put("_id",strings[0]);
                Gson gson = new Gson();
                String json = gson.toJson(params);
                result = Server.post(context1.getResources().getString(R.string.cancelOrder),json);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.dismiss();
            try {

                JSONObject jsonObject =new JSONObject(s);

                String success = jsonObject.getString("success");
                if (!success.equals("true")) {
                    Toast.makeText(context1,"Some error occured in cancelling Order..Please check your internet connection",Toast.LENGTH_LONG).show();
                }else{
                   Toast.makeText(context1,"Order has been cancelled",Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(context1,YourOrders.class);
                   context1.startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


    }
}