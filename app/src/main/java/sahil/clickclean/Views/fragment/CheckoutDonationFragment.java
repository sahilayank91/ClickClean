package sahil.clickclean.Views.fragment;

import android.Manifest;
import android.annotation.SuppressLint;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;
import sahil.clickclean.Views.MainActivity;
import sahil.clickclean.Views.YourOrders;
import sahil.clickclean.adapter.RateCardAdapter;
import sahil.clickclean.helper.LocationAddress;
import sahil.clickclean.model.RateCard;
import sahil.clickclean.utilities.Server;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import static android.app.Activity.RESULT_OK;

public class CheckoutDonationFragment extends Fragment implements OnMapReadyCallback,View.OnClickListener{

    View view;
    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private final static int PLACE_PICKER_REQUEST = 1;
    MapView mMapView;
    GoogleMap mGoogleMap;
    private TextView rateCardView;
    public EditText addressContainer;
    Double latitude,longitude;
    Button getPlaceButton;
    private EditText orderPickupDate;
    public LocationAddress locationAddress;
    Button btnDatePicker, btnTimePicker,checkoutButton;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView textCheckoutTotal,mTotal,mService,numuppper, numbottom, numjacket, numwoollen, numblancketsingle, numblanketdouble, numbedsheetsingle, numbedsheetdouble;
    String pickup_date;
    private String order,total,service;
    private RecyclerView recyclerView;
    private RateCardAdapter adapter;
    ArrayList<RateCard> listRateCard = new ArrayList<>();
    ArrayList<RateCard> orderlist = new ArrayList<>();
    public CheckoutDonationFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) view = inflater.inflate(R.layout.fragment_add_address, container, false);
        else return view;
        addressContainer = view.findViewById(R.id.address_container);
        CardView cardservice = view.findViewById(R.id.servicecard);
        cardservice.setVisibility(View.GONE);

        btnDatePicker=(Button)view.findViewById(R.id.btn_date);
        btnDatePicker.setOnClickListener(this);
        mService = view.findViewById(R.id.check_selectedService);
        mService.setText("Donation");
        checkoutButton = view.findViewById(R.id.checkoutbutton);
        textCheckoutTotal = view.findViewById(R.id.c);
        textCheckoutTotal.setText("Total Clothes");

        assert getArguments() != null;
        order = getArguments().getString("order");
        total = getArguments().getString("total");
        String address =SharedPreferenceSingleton.getInstance(getContext()).getString("address","User Not Registered");
        addressContainer.setText(address);

        requestPermission();
        mTotal = view.findViewById(R.id.totalCost);
        mTotal.setText(total);

        orderPickupDate = view.findViewById(R.id.in_date);

        getPlaceButton = (Button) view.findViewById(R.id.changeAddress);

        getPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(getActivity());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddOrder().execute();
            }
        });



        return view;
    }


    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = view.findViewById(R.id.mapView);
        if(mMapView!=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
        latitude = Double.parseDouble(SharedPreferenceSingleton.getInstance(getContext()).getString("latitude","User Not Registered"));
        longitude = Double.parseDouble(SharedPreferenceSingleton.getInstance(getContext()).getString("longitude","User Not Registered"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("My Home").snippet("Pick my clothes from here"));
        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(latitude,longitude)).zoom(16).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getContext(), data);
                Log.e("address:",place.getName().toString());
                addressContainer.setText(place.getName().toString() + "," + place.getAddress());
                LatLng latLng =  place.getLatLng();
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                mGoogleMap.clear();
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("My Home").snippet("Pick my clothes from here"));
                CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(latitude,longitude)).zoom(16).bearing(0).tilt(45).build();
                mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                if (place.getAttributions() == null) {
                    Toast.makeText(getContext(),"No attribution ",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(),place.getAttributions().toString(),Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            orderPickupDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            cal.set(Calendar.MONTH, monthOfYear);
                            cal.set(Calendar.YEAR, year);
                            Long time = cal.getTimeInMillis();
                            pickup_date = String.valueOf(time);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CreateOrderFragment createOrderFragment = new CreateOrderFragment();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.main_container,createOrderFragment).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    class AddOrder extends AsyncTask<String, String, String> {
        boolean success = false;
        HashMap<String, String> params = new HashMap<>();
        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            params.put("order",order);
            params.put("city",SharedPreferenceSingleton.getInstance(getContext()).getString("city","Jaipur"));
            params.put("latitude",String.valueOf(latitude));
            params.put("longitude",String.valueOf(longitude));
            params.put("status","Recieved");
            params.put("userid", SharedPreferenceSingleton.getInstance(getContext()).getString("_id","User Not Registered"));
            params.put("address",addressContainer.getText().toString());
            params.put("pickup_date",pickup_date);
            params.put("service","Donation");
            params.put("total",total);
            params.put("offer","No");

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            //getTime() returns the current date in default time zone
            Date date = calendar.getTime();
            int day = calendar.get(Calendar.DATE);
            //Note: +1 the month for current month
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
            params.put("day",String.valueOf(day));
            params.put("month",String.valueOf(month));
            params.put("year",String.valueOf(year));
            progress=new ProgressDialog(getContext());
            progress.setMessage("Getting your Order..");
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.dismiss();
            if (success) {
                Toast.makeText(getContext(),"Thank you for Donating!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

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
                System.out.println(json);
                result = Server.post(getResources().getString(R.string.newOrder),json);
                success = true;
            } catch (Exception e){
                e.printStackTrace();
            }



            System.out.println("Result:" + result);
            return result;
        }
    }

}
