package sahil.clickclean.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import sahil.clickclean.R;
import sahil.clickclean.Views.fragment.CreateDonationFragment;

public class DonateActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    private CreateDonationFragment createDonationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Donate Clothes");

        fragmentManager = getSupportFragmentManager();
        if (createDonationFragment == null)
            createDonationFragment = new CreateDonationFragment();
        replaceFragment(createDonationFragment);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(DonateActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        ft.replace(R.id.service_main_container, fragment)
                .commit();
//        ft.commitAllowingStateLoss();
    }


}
