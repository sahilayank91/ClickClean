package sahil.clickclean.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;
import sahil.clickclean.Views.fragment.AddAddressFragment;
import sahil.clickclean.Views.fragment.CheckoutFragment;
import sahil.clickclean.Views.fragment.CreateOrderFragment;
import sahil.clickclean.Views.fragment.SelectServiceFragment;
import sahil.clickclean.helper.AppLocationService;

import static sahil.clickclean.Views.fragment.CreateOrderFragment.upper;
import static sahil.clickclean.Views.fragment.CreateOrderFragment.woollen;
import static sahil.clickclean.Views.fragment.SelectServiceFragment.service;

public class SchedulePickup extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private TextView mTextMessage;

    private SelectServiceFragment selectServiceFragment;
    private CreateOrderFragment createOrderFragment;
    private AddAddressFragment addAddressFragment;
    FragmentManager fragmentManager;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(SchedulePickup.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        service = null;
        CreateOrderFragment.upper = 0;
        CreateOrderFragment.bottom = 0;
        CreateOrderFragment.jacket = 0;
        CreateOrderFragment.woollen =0;
        CreateOrderFragment.blancket_single = 0;
        CreateOrderFragment.blancket_double = 0;
        CreateOrderFragment.bedsheet_single = 0;
        CreateOrderFragment.bedsheet_double = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        service = null;
        CreateOrderFragment.upper = 0;
        CreateOrderFragment.bottom = 0;
        CreateOrderFragment.jacket = 0;
        CreateOrderFragment.woollen =0;
        CreateOrderFragment.blancket_single = 0;
        CreateOrderFragment.blancket_double = 0;
        CreateOrderFragment.bedsheet_single = 0;
        CreateOrderFragment.bedsheet_double = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        service = null;
        CreateOrderFragment.upper = 0;
        CreateOrderFragment.bottom = 0;
        CreateOrderFragment.jacket = 0;
        CreateOrderFragment.woollen =0;
        CreateOrderFragment.blancket_single = 0;
        CreateOrderFragment.blancket_double = 0;
        CreateOrderFragment.bedsheet_single = 0;
        CreateOrderFragment.bedsheet_double = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_pickup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);

        mTextMessage = (TextView) findViewById(R.id.message);

        setUpBottomBar();

        fragmentManager = getSupportFragmentManager();

        if (selectServiceFragment == null)
            selectServiceFragment = new SelectServiceFragment();
        replaceFragment(selectServiceFragment);
        getSupportActionBar().setTitle("Select Service");

        mEditor = mSharedPreferences.edit();
    }

    private void setUpBottomBar(){
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

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
                        if(service==null){
                            Toast.makeText(SchedulePickup.this,"Please Select a Service First",Toast.LENGTH_LONG).show();
                        }else{
                            getSupportActionBar().setTitle(R.string.create_order);
                            replaceFragment(createOrderFragment);
                        }
                        break;
                    case R.id.navigation_add_address:
                        if (addAddressFragment == null)
                            addAddressFragment = new AddAddressFragment();

                        if(service==null){
                            Toast.makeText(SchedulePickup.this,"Please Select a Service First",Toast.LENGTH_LONG).show();
                        }else if (CreateOrderFragment.upper  + CreateOrderFragment.bottom + CreateOrderFragment.woollen + CreateOrderFragment.blancket_single + CreateOrderFragment.blancket_double + CreateOrderFragment.bedsheet_single+ CreateOrderFragment.bedsheet_double + CreateOrderFragment.jacket ==0 ){
                            Toast.makeText(SchedulePickup.this,"Please select some of the clothes",Toast.LENGTH_LONG).show();
                        }else{
                            getSupportActionBar().setTitle(R.string.title_add_address);
                            replaceFragment(addAddressFragment);
                        }
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
            if (tag.equals(fragmentManager.getBackStackEntryAt(x).getName())) {
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
