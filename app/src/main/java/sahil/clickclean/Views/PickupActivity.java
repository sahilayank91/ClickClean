package sahil.clickclean.Views;


import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;
import sahil.clickclean.Views.fragment.CompletedFragment;
import sahil.clickclean.Views.fragment.TodayFragment;
import sahil.clickclean.Views.fragment.UpcomingFragment;


/**
 * Provides UI for the main screen.
 */
public class PickupActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);
        // Adding Toolbar to Main screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Orders");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

//         Create Navigation drawer and inlfate layout
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//
//
//
//    View header = navigationView.getHeaderView(0);
//        TextView navEmailView = (TextView) header.findViewById(R.id.nav_header_email);
//        TextView navNameView = (TextView) header.findViewById(R.id.nav_header_name);
//        TextView navPhoneView = (TextView) header.findViewById(R.id.nav_header_phone);
//        String name;
//        String role= SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("role", "Customer");
//        String firstname = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("firstname", "User Not Registered");
//        String lastname = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("lastname", "User Not Registered");
//        name = firstname + " " + lastname;
//        navEmailView.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("email", "User Not Registered"));
//        navNameView.setText(name);
//        navPhoneView.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("phone", "Phone not registered"));
//
//
//
//        if(role.equals("Customer")){
//            Menu nav_Menu = navigationView.getMenu();
//            nav_Menu.findItem(R.id.nav_register_washerman).setVisible(false);
//            nav_Menu.findItem(R.id.nav_orders).setVisible(false);
//        }
//
//
//        // Adding menu icon to Toolbar
//        ActionBar supportActionBar = getSupportActionBar();
//        if (supportActionBar != null) {
//            VectorDrawableCompat indicator
//                    = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu_black_24dp, getTheme());
//            indicator.setTint(ResourcesCompat.getColor(getResources(), R.color.bg_screen1,getTheme()));
//            supportActionBar.setHomeAsUpIndicator(indicator);
//            supportActionBar.setDisplayHomeAsUpEnabled(true);
//        }
//        // Set behavior of Navigation drawer
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    // This method will trigger on item Click of navigation menu
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        // Set item in checked state
//
//                        int id = menuItem.getItemId();
//                        if (id == R.id.nav_profile) {
//                            // Handle the camera action
//                            Intent intent = new Intent(PickupActivity.this,ProfileActivity.class);
//                            startActivity(intent);
//                        } else if (id == R.id.nav_order) {
////            Intent intent  = new Intent(PickupActivity.this,PickupActivity.class);
////            startActivity(intent);
//                        } else if (id == R.id.nav_schedule_pickup) {
//                            Intent intent = new Intent(PickupActivity.this,SchedulePickup.class);
//                            startActivity(intent);
//                        } else if (id == R.id.nav_logout) {
//                            getApplicationContext().getSharedPreferences(SharedPreferenceSingleton.SETTINGS_NAME, MODE_PRIVATE).edit().clear().apply();
//                            startActivity(new Intent(PickupActivity.this, LoginActivity.class));
//                            finish();
//
//                        } else if (id == R.id.nav_share) {
//                            Intent i = new Intent(Intent.ACTION_SEND);
//                            i.setType("text/plain");
//                            i.putExtra(Intent.EXTRA_SUBJECT, "ClickClean");
//                            String message = "\nDownload ClickClean *Your app link* \n\n";
//                            i.putExtra(Intent.EXTRA_TEXT, message);
//                            startActivity(Intent.createChooser(i, "Choose Sharing Method"));
//
//
//                        } else if (id == R.id.nav_feedback) {
//
//                            Intent intent = new Intent(PickupActivity.this,RegisterWasherMan.class);
//                            startActivity(intent);
//                        }
//                        menuItem.setChecked(true);
//
//                        // TODO: handle navigation
//
//                        // Closing drawer on item click
//                        mDrawerLayout.closeDrawers();
//                        return true;
//                    }
//                });
        // Adding Floating Action Button to bottom right of main view

    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new TodayFragment(), "Today");
        adapter.addFragment(new UpcomingFragment(), "Upcoming");
        adapter.addFragment(new CompletedFragment(), "Completed");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PickupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}