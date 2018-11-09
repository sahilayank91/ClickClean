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
    private CreateOrderFragment createOrderFragment;
    FragmentManager fragmentManager;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(SchedulePickup.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SchedulePickup.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_pickup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fragmentManager = getSupportFragmentManager();



        String type = getIntent().getStringExtra("type");
        String service = getIntent().getStringExtra("service");
        Bundle b = new Bundle();
        b.putString("type",type);
        b.putString("service",service);
        if (createOrderFragment == null)
            createOrderFragment = new CreateOrderFragment();
        createOrderFragment.setArguments(b);
        replaceFragment(createOrderFragment);
        getSupportActionBar().setTitle("Create Order");

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
       FragmentTransaction ft = fragmentManager.beginTransaction();
       ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
       ft.replace(R.id.service_main_container, fragment)
                .commit();
//        ft.commitAllowingStateLoss();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
