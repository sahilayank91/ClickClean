package sahil.clickclean.Views;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;

public class ProfileActivity extends AppCompatActivity {


    TextView name, email, address;
    ImageView profilePhoto;


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = findViewById(R.id.user_profile_name);
        email = findViewById(R.id.user_profile_email);
address = findViewById(R.id.address_container);
        String username;
        String firstname = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("firstname", "User Not Registered");
        String lastname = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("lastname", "User Not Registered");
        username = firstname + " " + lastname;
        name.setText(username);

        email.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("email", "User Not Registered"));
        address.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("address", "User Not Registered"));



    }

}
