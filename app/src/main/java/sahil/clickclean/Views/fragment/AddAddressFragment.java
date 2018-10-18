package sahil.clickclean.Views.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import sahil.clickclean.R;
import sahil.clickclean.Views.SchedulePickup;
import sahil.clickclean.helper.AppLocationService;
import sahil.clickclean.helper.LocationAddress;

public class AddAddressFragment extends Fragment implements OnMapReadyCallback,LocationListener{

    View view;
    private static final int MY_LOCATION_REQUEST_CODE = 1;

    MapView mMapView;
    GoogleMap mGoogleMap;
    private LocationManager mLocationManager;
    private String provider;

    private TextView rateCardView;
    AppLocationService appLocationService;
    public EditText addressContainer;
    LocationManager locationManager;
    Double latitude,longitude;

    public LocationAddress locationAddress;

    public AddAddressFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) view = inflater.inflate(R.layout.fragment_add_address, container, false);
        else return view;

        locationAddress = new LocationAddress();
        getUserLocation();
        rateCardView = view.findViewById(R.id.rateCard);
        rateCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRateCard();
            }
        });
        return view;
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
        addressContainer = view.findViewById(R.id.address_container);
        appLocationService = new AppLocationService(
                getContext());
        Location location = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);



        //you can hard-code the lat & long if you have issues with getting it
        //remove the below if-condition and use the following couple of lines
        //double latitude = 37.422005;
        //double longitude = -122.084095

        if (location != null) {
             latitude = location.getLatitude();
             longitude = location.getLongitude();


            LocationAddress.getAddressFromLocation(latitude, longitude,
                    getActivity(), new GeocoderHandler());
        } else {
            showSettingsAlert();
        }

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
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getContext());
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getContext().startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    public void showRateCard() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.rate_card, null);
        final String[] items = {"Apple", "Banana", "Orange", "Grapes"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(alertLayout);
        builder.setTitle("Our Rate Card");

        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("CANCEL", null);
//        builder.setNeutralButton("NEUTRAL", null);
//        builder.setPositiveButtonIcon(getResources().getDrawable(android.R.drawable.ic_menu_call, getTheme()));

        AlertDialog alertDialog = builder.create();

        alertDialog.show();

//        Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
//        button.setBackgroundColor(Color.BLACK);
//        button.setPadding(0, 0, 20, 0);
//        button.setTextColor(Color.WHITE);
    }



    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {


                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.app_name)
                        .setMessage("Please enable GPS")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity() ,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_LOCATION_REQUEST_CODE);
                            }
                        })
                        .create()
                        .show();

            }
            else{
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_LOCATION_REQUEST_CODE);
            }

        }
        else{
            mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = mLocationManager.getBestProvider(criteria, false);
            if(provider!=null&&!provider.equals("null"))   mLocationManager.requestLocationUpdates(provider, 0, 0, this);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_LOCATION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // attendance_location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                        Criteria criteria = new Criteria();
                        String provider = mLocationManager.getBestProvider(criteria, false);

                        if(provider!=null&&!provider.equals("null"))   mLocationManager.requestLocationUpdates(provider, 0, 0, this);

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mGoogleMap.clear();
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title("My Home").snippet("Pick my clothes from here"));


        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(location.getLatitude(),location.getLongitude())).zoom(16).bearing(0).tilt(45).build();

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        LocationAddress.getAddressFromLocation(location.getLatitude(),location.getLongitude(), getContext(), new GeocoderHandler());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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
}
