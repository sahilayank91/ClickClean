package sahil.clickclean.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.FileUriExposedException;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.iid.FirebaseInstanceId;

import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;
import sahil.clickclean.Views.fragment.AddAddressFragment;
import sahil.clickclean.Views.fragment.CategoryFragment;
import sahil.clickclean.Views.fragment.CreateOrderFragment;
import sahil.clickclean.Views.fragment.HomeFragment;
import sahil.clickclean.Views.fragment.OfferFragment;
import sahil.clickclean.Views.fragment.SelectServiceFragment;
import sahil.clickclean.model.Feedback;
import sahil.clickclean.utilities.BottomNavigationBehaviour;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView navEmailView;
    private TextView navNameView;
    private TextView navPhoneView;
    private ImageView navImageView;
    NestedScrollView nestedScrollView;
    private HomeFragment homeFragment;
    private OfferFragment offerFragment;
    FragmentManager fragmentManager;
    private SelectServiceFragment selectServiceFragment;
    AHBottomNavigation bottomNavigation;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nestedScrollView = findViewById(R.id.scrollView);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.main_navigation);


        // attaching bottom sheet behaviour - hide / show on scroll

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
        navImageView = (ImageView) header.findViewById(R.id.personView);
        String name;
        String role = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("role", "Customer");
        String firstname = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("firstname", "User Not Registered");
        String lastname = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("lastname", "User Not Registered");
        name = firstname + " " + lastname;
        navEmailView.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("email", "User Not Registered"));
        navNameView.setText(name);
        navPhoneView.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("phone", "Phone not registered"));

        String gender  = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("gender", "Male");

//        if(gender.equals("Male")){
//            navImageView.setImageDrawable(getResources().getDrawable(R.drawable.male));
//        }else{
//            navImageView.setImageDrawable(getResources().getDrawable(R.drawable.woman));
//
//        }
        Menu nav_Menu = navigationView.getMenu();
        if(role.equals("Customer")){
//            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_register_washerman).setVisible(false);
            nav_Menu.findItem(R.id.nav_orders).setVisible(false);
            nav_Menu.findItem(R.id.nav_add_image).setVisible(false);
        }

        if(role.equals("Washerman")){
            nav_Menu.findItem(R.id.nav_register_washerman).setVisible(true);
            nav_Menu.findItem(R.id.nav_orders).setVisible(true);
            nav_Menu.findItem(R.id.nav_add_image).setVisible(false);

        }
        if(role.equals("Admin")){
            nav_Menu.findItem(R.id.nav_register_washerman).setVisible(true);
            nav_Menu.findItem(R.id.nav_orders).setVisible(false);
            nav_Menu.findItem(R.id.nav_add_image).setVisible(true);
        }

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
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#0336FF"));
        // Change colors

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.home, R.drawable.ic_home_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.categories, R.drawable.ic_map_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.offers, R.drawable.ic_card_giftcard_black_24dp, R.color.colorPrimary);


        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.setColored(true);
        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setAccentColor(Color.parseColor("#FF0000"));
        bottomNavigation.setInactiveColor(Color.parseColor("#ffffff"));
        bottomNavigation.setForceTint(true);
        bottomNavigation.setCurrentItem(0);


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position){
                    case 0:
                        homeFragment = new HomeFragment();
                        getSupportActionBar().setTitle("Home");
                        replaceFragment(homeFragment);
                        break;
                    case 1:  selectServiceFragment = new SelectServiceFragment();
                        getSupportActionBar().setTitle("Categories")    ;
                        replaceFragment(selectServiceFragment);
                        break;
                    case 2: offerFragment = new OfferFragment();
                        getSupportActionBar().setTitle("Offers");
                        replaceFragment(offerFragment);
                        break;
                }

                return true;
            }
        });

//        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()) {
//                    case R.id.navigation_home:
//                            homeFragment = new HomeFragment();
//                        getSupportActionBar().setTitle("Home");
//                        replaceFragment(homeFragment);
//                        break;
//                    case R.id.navigation_categories:
//                            selectServiceFragment = new SelectServiceFragment();
//                            getSupportActionBar().setTitle("Categories")    ;
//                            replaceFragment(selectServiceFragment);
//                        break;
//                    case R.id.navigation_offers:
//                            offerFragment = new OfferFragment();
//                            getSupportActionBar().setTitle("Offers");
//                            replaceFragment(offerFragment);
//                        break;
//                }
//                return true;
//            }
//        });
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
            getApplicationContext().getSharedPreferences(SharedPreferenceSingleton.SETTINGS_NAME, MODE_PRIVATE).edit().clear().apply();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
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

            selectServiceFragment = new SelectServiceFragment();
            replaceFragment(selectServiceFragment);
        }else if (id==R.id.nav_donate_clothes){
            Intent intent = new Intent(MainActivity.this,DonateActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_logout) {
            getApplicationContext().getSharedPreferences(SharedPreferenceSingleton.SETTINGS_NAME, MODE_PRIVATE).edit().clear().apply();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else if (id == R.id.nav_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "ClickClean");
            String message = "\nDownload YoursDhobi \n https://play.google.com/store/apps/details?id=sahil.clickclean\n\n";
            i.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(i, "Choose Sharing Method"));
        } else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
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
        } else if(id == R.id.nav_contactus){
            Intent intent = new Intent(MainActivity.this,ContactActivity.class);
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
