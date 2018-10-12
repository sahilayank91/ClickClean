package sahil.clickclean.Views;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.w3c.dom.Text;

import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewFlipper mViewFlipper;
    private Context mContext;
    private float initialX;
    private ImageView navImageView;
private TextView navEmailView;
private TextView navNameView;
    @Override
    protected void onStart() {
        super.onStart();

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent touchevent) {
//        switch (touchevent.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                initialX = touchevent.getX();
//                break;
//            case MotionEvent.ACTION_UP:
//                float finalX = touchevent.getX();
//                if (initialX > finalX) {
//                    if (mViewFlipper.getDisplayedChild() == 1)
//                        break;
//
//                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.in_from_left));
//                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.out_from_left));
//
//                    mViewFlipper.showNext();
//                } else {
//                    if (mViewFlipper.getDisplayedChild() == 0)
//                        break;
//
//                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.in_from_right));
//                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.out_from_right));
//
//                    mViewFlipper.showPrevious();
//                }
//                break;
//        }
//        return false;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;
//        mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View header = navigationView.getHeaderView(0);
        navImageView= (ImageView) header.findViewById(R.id.header_profile);
        navEmailView = (TextView) header.findViewById(R.id.nav_header_email);
        navNameView = (TextView) header.findViewById(R.id.nav_header_name);

        navEmailView.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("email",""));
        navNameView.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("firstname","") +
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("lastname",""));
//
//        mViewFlipper.setAutoStart(true);
//        mViewFlipper.setFlipInterval(1000);
//        mViewFlipper.startFlipping();
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
            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_order) {

        } else if (id == R.id.nav_schedule_pickup) {

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

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
