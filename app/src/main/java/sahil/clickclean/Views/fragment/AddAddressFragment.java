package sahil.clickclean.Views.fragment;

import android.Manifest;
import android.annotation.SuppressLint;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
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
import sahil.clickclean.Views.LoginActivity;
import sahil.clickclean.Views.RegisterActivity;
import sahil.clickclean.Views.YourOrders;
import sahil.clickclean.helper.AppLocationService;
import sahil.clickclean.helper.LocationAddress;
import sahil.clickclean.utilities.Server;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.gson.Gson;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static sahil.clickclean.Views.fragment.SelectServiceFragment.service;

public class AddAddressFragment extends Fragment implements OnMapReadyCallback,View.OnClickListener{

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
    Button btnDatePicker, btnTimePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView mService,numuppper, numbottom, numjacket, numwoollen, numblancketsingle, numblanketdouble, numbedsheetsingle, numbedsheetdouble;

    public AddAddressFragment() {
        // Required empty public constructor
    }
    public void showRateCard() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.rate_card, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(alertLayout);
        builder.setTitle("Our Rate Card");
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("CANCEL", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) view = inflater.inflate(R.layout.fragment_add_address, container, false);
        else return view;
        addressContainer = view.findViewById(R.id.address_container);

        btnDatePicker=(Button)view.findViewById(R.id.btn_date);
        btnDatePicker.setOnClickListener(this);
        mService = view.findViewById(R.id.check_selectedService);

        mService.setText(SelectServiceFragment.service);



        numuppper= view.findViewById(R.id.check_upper);
        numbottom = view.findViewById(R.id.check_bottom_num);
        numwoollen = view.findViewById(R.id.check_woollen);
        numjacket = view.findViewById(R.id.check_jacket);
        numblancketsingle = view.findViewById(R.id.check_blanket_single);
        numblanketdouble = view.findViewById(R.id.check_blanket_double);
        numbedsheetsingle = view.findViewById(R.id.check_bedsheet_single);
        numbedsheetdouble  = view.findViewById(R.id.check_bedsheet_double);


        numuppper.setText(String.valueOf(CreateOrderFragment.upper));
        numbottom.setText(String.valueOf(CreateOrderFragment.bottom));
        numwoollen.setText(String.valueOf(CreateOrderFragment.woollen));
        numjacket.setText(String.valueOf(CreateOrderFragment.jacket));
        numblancketsingle.setText(String.valueOf(CreateOrderFragment.blancket_single));
        numblanketdouble.setText(String.valueOf(CreateOrderFragment.blancket_double));
        numbedsheetdouble.setText(String.valueOf(CreateOrderFragment.bedsheet_double));
        numbedsheetsingle.setText(String.valueOf(CreateOrderFragment.bedsheet_single));
        locationAddress = new LocationAddress();

        requestPermission();

        rateCardView = view.findViewById(R.id.rateCard);

        rateCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRateCard();
            }
        });

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
        Location location = new Location(LocationManager.GPS_PROVIDER);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LocationAddress.getAddressFromLocation(latitude, longitude,
                    getActivity(), new GeocoderHandler());


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getContext(), data);
                addressContainer.setText(place.getAddress());
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
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    @SuppressLint("HandlerLeak")
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            addressContainer.setText(locationAddress);
        }
    }
    class RegisterUser extends AsyncTask<String, String, String> {
        boolean success = false;
        HashMap<String, String> params = new HashMap<>();
        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            params.put("upper", numuppper.getText().toString());
            params.put("bottom", numbottom.getText().toString());
            params.put("woollen", numwoollen.getText().toString());
            params.put("jacket", numjacket.getText().toString());
            params.put("blanket_single",numblancketsingle.getText().toString());
            params.put("blanket_double",numblanketdouble.getText().toString());
            params.put("bedsheet_single",numbedsheetsingle.getText().toString());
            params.put("bedsheet_double",numbedsheetdouble.getText().toString());
            params.put("userid", SharedPreferenceSingleton.getInstance(getContext()).getString("_id","User Not Registered"));

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
            Date date = null;
            try {
                date = sdf.parse(orderPickupDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //            long millis = date.getTime();
            params.put("pickupdate",date.toString());

            progress=new ProgressDialog(getContext());
            progress.setMessage("Registering..");
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.dismiss();
            if (success) {
                Toast.makeText(getContext(), R.string.reg_success, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), YourOrders.class);
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
                result = Server.post(getResources().getString(R.string.register),json);
                success = true;
            } catch (Exception e){
                e.printStackTrace();
            }



            System.out.println("Result:" + result);
            return result;
        }
    }
}
