package sahil.clickclean.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.iid.FirebaseInstanceId;

import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;
import sahil.clickclean.Views.fragment.AddAddressFragment;
import sahil.clickclean.Views.fragment.CategoryFragment;
import sahil.clickclean.Views.fragment.CreateOrderFragment;
import sahil.clickclean.Views.fragment.HomeFragment;
import sahil.clickclean.Views.fragment.OfferFragment;
import sahil.clickclean.Views.fragment.SelectServiceFragment;

import static sahil.clickclean.Views.fragment.SelectServiceFragment.service;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView navEmailView;
    private TextView navNameView;
    private TextView navPhoneView;
    private Button buttonschedulepickup;

    private HomeFragment homeFragment;
    private OfferFragment offerFragment;
    private CategoryFragment categoryFragment;
    FragmentManager fragmentManager;

    private float startX;
    private ViewFlipper vf;
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View header = navigationView.getHeaderView(0);
        navEmailView = (TextView) header.findViewById(R.id.nav_header_email);
        navNameView = (TextView) header.findViewById(R.id.nav_header_name);
        navPhoneView = (TextView) header.findViewById(R.id.nav_header_phone);
        String name;
        String role = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("role", "Customer");
        String firstname = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("firstname", "User Not Registered");
        String lastname = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("lastname", "User Not Registered");
        name = firstname + " " + lastname;
        navEmailView.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("email", "User Not Registered"));
        navNameView.setText(name);
        navPhoneView.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("phone", "Phone not registered"));


//        if(role.equals("Customer")){
//            Menu nav_Menu = navigationView.getMenu();
//            nav_Menu.findItem(R.id.nav_register_washerman).setVisible(false);
//            nav_Menu.findItem(R.id.nav_orders).setVisible(false);
//        }
        fragmentManager = getSupportFragmentManager();
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        replaceFragment(homeFragment);
        getSupportActionBar().setTitle("Home");

        setUpBottomBar();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    private void setUpBottomBar(){
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.main_navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                            homeFragment = new HomeFragment();
                        getSupportActionBar().setTitle("Home");
                        replaceFragment(homeFragment);
                        break;
                    case R.id.navigation_categories:
                            categoryFragment = new CategoryFragment();
                            getSupportActionBar().setTitle("Categories")    ;
                            replaceFragment(categoryFragment);
                        break;
                    case R.id.navigation_offers:
                            offerFragment = new OfferFragment();
                            getSupportActionBar().setTitle("Offers");
                            replaceFragment(offerFragment);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_order) {
            Intent intent = new Intent(MainActivity.this, YourOrders.class);
            startActivity(intent);
        } else if (id == R.id.nav_schedule_pickup) {
            Intent intent = new Intent(MainActivity.this, SchedulePickup.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            getApplicationContext().getSharedPreferences(SharedPreferenceSingleton.SETTINGS_NAME, MODE_PRIVATE).edit().clear().apply();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else if (id == R.id.nav_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "ClickClean");
            String message = "\nDownload ClickClean *Your app link* \n\n";
            i.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(i, "Choose Sharing Method"));
        } else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(MainActivity.this, RegisterWasherMan.class);
            startActivity(intent);
        } else if (id == R.id.nav_register_washerman) {
            Intent intent = new Intent(MainActivity.this, RegisterWasherMan.class);
            startActivity(intent);
        } else if (id == R.id.nav_orders) {
            Intent intent = new Intent(MainActivity.this, PickupActivity.class);
            startActivity(intent);
        } else if (id==R.id.nav_rate_card){
            Intent intent = new Intent(MainActivity.this,RateCardActivity.class);
            startActivity(intent);
        } else if(id==R.id.nav_terms){
            Intent intent = new Intent(MainActivity.this,TermsAndCondition.class);
            startActivity(intent);
        } else if(id==R.id.nav_add_image){
            Intent intent = new Intent(MainActivity.this,UploadImage.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        ft.replace(R.id.main_container, fragment)
                .commit();
//        ft.commitAllowingStateLoss();
    }


}
