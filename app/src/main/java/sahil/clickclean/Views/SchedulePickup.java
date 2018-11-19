package sahil.clickclean.Views;

import android.content.Intent;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import sahil.clickclean.R;

import sahil.clickclean.Views.fragment.CreateOrderFragment;


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
        String percentage = getIntent().getStringExtra("percentage");
        String code = getIntent().getStringExtra("code");
        String offerid = getIntent().getStringExtra("offerid");

        Bundle b = new Bundle();
        b.putString("type",type);
        b.putString("service",service);
        b.putString("percentage",percentage);
        if(code!=null){
            b.putString("code",code);
        }
        if(offerid!=null){
            b.putString("offerid",offerid);
        }
        if (createOrderFragment == null)
            createOrderFragment = new CreateOrderFragment();
        createOrderFragment.setArguments(b);
        getSupportActionBar().setTitle("Create Order");
        replaceFragment(createOrderFragment);


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
