package sahil.clickclean.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import sahil.clickclean.R;
import sahil.clickclean.Views.fragment.AddAddressFragment;
import sahil.clickclean.Views.fragment.CheckoutFragment;
import sahil.clickclean.Views.fragment.CreateOrderFragment;
import sahil.clickclean.Views.fragment.SelectServiceFragment;
import sahil.clickclean.helper.AppLocationService;
import sahil.clickclean.helper.LocationAddress;

public class SchedulePickup extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private TextView mTextMessage;

    AppLocationService appLocationService;
    private Button mAddPickupAddress;
    private String selectedService;
    private SelectServiceFragment selectServiceFragment;
    private CreateOrderFragment createOrderFragment;
    private AddAddressFragment addAddressFragment;
    FragmentManager fragmentManager;
    private EditText addressContainer;
    private CheckoutFragment checkoutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_pickup);

        mTextMessage = (TextView) findViewById(R.id.message);

        addressContainer = findViewById(R.id.address_container);


        setUpBottomBar();

        fragmentManager = getSupportFragmentManager();

        if (selectServiceFragment == null)
            selectServiceFragment = new SelectServiceFragment();
        replaceFragment(selectServiceFragment);
        getSupportActionBar().setTitle("Select Service");

//
//        mAddPickupAddress = (Button) findViewById(R.id.addPickupAddress);
//        mAddPickupAddress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//
//                Location location = appLocationService
//                        .getLocation(LocationManager.GPS_PROVIDER);
//
//                //you can hard-code the lat & long if you have issues with getting it
//                //remove the below if-condition and use the following couple of lines
//                //double latitude = 37.422005;
//                //double longitude = -122.084095
//
//                if (location != null) {
//                    double latitude = location.getLatitude();
//                    double longitude = location.getLongitude();
//                    LocationAddress locationAddress = new LocationAddress();
//                    locationAddress.getAddressFromLocation(latitude, longitude,
//                            getApplicationContext(), new GeocoderHandler());
//                } else {
//                    showSettingsAlert();
//                }
//
//            }
//        });


    }

    private void setUpBottomBar(){
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_select_service:
                        if (selectServiceFragment == null)
                            selectServiceFragment = new SelectServiceFragment();
                        getSupportActionBar().setTitle(R.string.title_select_service);
                        replaceFragment(selectServiceFragment);
                        break;
                    case R.id.navigation_create_order:
                        if (createOrderFragment == null)
                            createOrderFragment = new CreateOrderFragment();
                        getSupportActionBar().setTitle(R.string.create_order);
                        replaceFragment(createOrderFragment);
                        break;
                    case R.id.navigation_add_address:
                        if (addAddressFragment == null)
                            addAddressFragment = new AddAddressFragment();
                        getSupportActionBar().setTitle(R.string.title_add_address);
                        replaceFragment(addAddressFragment);
                        break;

                }
                return true;
            }
        });
    }
    private void replaceFragment(Fragment new_fragment, String tag) {

        if (isTagInBackStack(tag)) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragmentManager.findFragmentByTag(tag));
            transaction.commit();
        } else {
            addFragment(new_fragment, tag);
        }

    }
    public boolean isTagInBackStack(String tag) {
        int x;
        boolean toReturn = false;
        int backStackCount = fragmentManager.getBackStackEntryCount();
        System.out.println("backstack" + backStackCount);

        for (x = 0; x < backStackCount; x++) {
            if (tag == fragmentManager.getBackStackEntryAt(x).getName()) {
                toReturn = true;
            }
        }

        return toReturn;
    }

    private void addFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
    private void addFragment(Fragment fragmentToAdd) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.main_container, fragmentToAdd)
                .commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                SchedulePickup.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        SchedulePickup.this.startActivity(intent);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


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
