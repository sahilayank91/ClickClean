package sahil.clickclean.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.format.DateUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class WashermanOrderAdapter extends RecyclerView.Adapter<WashermanOrderAdapter.OrderViewHolder> {

    private ArrayList<Order> listOrders;
    private RCVItemClickListener rcvItemClickListener;
    Context context1;
    public WashermanOrderAdapter(Context context, ArrayList<Order> listOrders) {
        context1 = context;
        this.listOrders = listOrders;
    }

    public void setRcvItemClickListener(RCVItemClickListener rcvItemClickListener) {
        this.rcvItemClickListener = rcvItemClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_pickup, parent, false);
        return new OrderViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, final int position) {
        final Order current = listOrders.get(position);
        holder.orderid.setText(current.get_id());
        holder.orderstatus.setText(current.getOrderstatus());
        holder.name.setText(current.getUser().getFirstname() + " " + current.getUser().getLastname());
        holder.city.setText(current.getUser().getCity());
        holder.locality.setText(current.getAddress());
        if(current.getUser().getFlataddress()!=null){
            holder.flat.setText(current.getUser().getFlataddress());
        }

        String jsDate = current.getOrderpickupdate();

        Date pickupdate=null;
        try {
            pickupdate = new SimpleDateFormat("yyyy-MM-dd").parse(jsDate);
            Log.e("date",pickupdate.toString());
            pickupdate.setHours(17);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.orderpickupdate.setText(pickupdate.toLocaleString().substring(0,12));



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
        if(current.getOrderservice().equals("Donation")){
            holder.totalText.setText("Total Clothes: ");
        }

//        Calendar calendar = Calendar.getInstance();
//        if(calendar.get(Calendar.HOUR_OF_DAY)>=17){
//            holder.cancel.setEnabled(false);
//            holder.cancel.setBackgroundColor(context1.getResources().getColor(R.color.black));
//            holder.cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context1,"You can't cancel Order after 5:00 PM",Toast.LENGTH_LONG).show();
//                }
//            });
//        }else{
//            holder.cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    openCancelDialog(v,holder.getAdapterPosition());
//                }
//            });
//
//
//        }

        holder.total.setText(current.getTotal());
        holder.phone.setText(current.getUser().getPhone());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!current.getOrderstatus().equals("Recieved")){
                    Toast.makeText(context1,"You cannot change the Order after it is picked up",Toast.LENGTH_SHORT).show();
                }else{

                }

            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+current.getUser().getPhone()));//change the number.
                context1.startActivity(callIntent);
            }
        });

        holder.navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lat = SharedPreferenceSingleton.getInstance(context1).getString("latitude", "0.0");
                String lng = SharedPreferenceSingleton.getInstance(context1).getString("longitude", "0.0");

                String uri = "https://www.google.com/maps/dir/?api=1&origin=" +lat + ", "+ lng + "&destination=" + current.getLatitude()+","+current.getLongitude() + "&travelmode=driving&dir_action=navigate";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                context1.startActivity(intent);
            }
        });
        holder.refuseorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCancelDialog(v,position);
            }
        });

        holder.orderwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnterOTPDialog(current.get_id(),current.getStatus());
            }
        });


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
    private void showEnterOTPDialog(final String id,final String status) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context1);
//        LayoutInflater inflater =LayoutI
//        final View dialogView = inflater.inflate(R.layout.otp_reciever, null);
//        dialogBuilder.setView(dialogView);
//

        final EditText input = new EditText(context1);

        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        dialogBuilder.setView(input);
        dialogBuilder.setTitle("Enter OTP");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                Toast.makeText(context1,input.getText().toString(),Toast.LENGTH_LONG).show();
               new verifyOTP(id,status,input.getText().toString()).execute();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView orderid, orderdate, orderpickupdate, orderstatus,orderservice,city,flat,locality,phone,name,total,totalText;
        Button navigate,edit,orderwork,refuseorder,call;

        private OrderViewHolder(View itemView) {
            super(itemView);

            orderid= itemView.findViewById(R.id.orderid);
            orderdate = itemView.findViewById(R.id.orderdate);
            orderpickupdate = itemView.findViewById(R.id.order_pickup_date);
            orderstatus = itemView.findViewById(R.id.orderstatus);
            orderservice = itemView.findViewById(R.id.orderservice);
            orderwork = itemView.findViewById(R.id.orderwork);
            city = itemView.findViewById(R.id.ordercity);
            flat = itemView.findViewById(R.id.flataddress);
            locality = itemView.findViewById(R.id.locality);
            phone = itemView.findViewById(R.id.phone);
            navigate = itemView.findViewById(R.id.navigate);
            name = itemView.findViewById(R.id.cutomer_name);
            total = itemView.findViewById(R.id.total);
            refuseorder = itemView.findViewById(R.id.refuse_order);
            call = itemView.findViewById(R.id.call);
            totalText = itemView.findViewById(R.id.totaltextView);
            refuseorder.setOnClickListener(this);
            navigate.setOnClickListener(this);
            orderwork.setOnClickListener(this);
            edit = itemView.findViewById(R.id.editOrder);
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


    @SuppressLint("StaticFieldLeak")
    class verifyOTP extends AsyncTask<String, String, String> {
        private ProgressDialog progress;

        HashMap<String,String> map = new HashMap<>();
        verifyOTP(String id, String status,String otp){
            map.put("status",status);
            map.put("_id",id);
            if(status.equals("Recieved")){
                map.put("pickup_otp",otp);
            }else{
                map.put("delivered_otp",otp);
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(context1);
            progress.setMessage("Verifying OTP");
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {

                Gson gson = new Gson();
                String json = gson.toJson(map);
                result = Server.post(context1.getResources().getString(R.string.verifyOTP),json);

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
                    Toast.makeText(context1,"Wrong OTP entered. Please check and again Enter",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context1,"Verified OTP",Toast.LENGTH_SHORT).show();

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


    }
}