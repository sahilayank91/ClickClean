package sahil.clickclean.Views;

import android.content.DialogInterface;
import android.content.Intent;
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
import sahil.clickclean.R;
import sahil.clickclean.Views.fragment.AddAddressFragment;
import sahil.clickclean.Views.fragment.CheckoutFragment;
import sahil.clickclean.Views.fragment.CreateOrderFragment;
import sahil.clickclean.Views.fragment.SelectServiceFragment;
import sahil.clickclean.helper.AppLocationService;

public class SchedulePickup extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private TextView mTextMessage;

    private SelectServiceFragment selectServiceFragment;
    private CreateOrderFragment createOrderFragment;
    private AddAddressFragment addAddressFragment;
    FragmentManager fragmentManager;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_pickup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextMessage = (TextView) findViewById(R.id.message);

        setUpBottomBar();

        fragmentManager = getSupportFragmentManager();

        if (selectServiceFragment == null)
            selectServiceFragment = new SelectServiceFragment();
        replaceFragment(selectServiceFragment);
        getSupportActionBar().setTitle("Select Service");

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
