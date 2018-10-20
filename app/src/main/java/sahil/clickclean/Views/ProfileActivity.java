package sahil.clickclean.Views;

import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


import sahil.clickclean.R;

public class ProfileActivity extends AppCompatActivity {


    TextView name, email, address;
    ImageView profilePhoto;


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = findViewById(R.id.user_profile_name);
        email = findViewById(R.id.user_profile_email);
        profilePhoto = findViewById(R.id.user_profile_photo);

        name.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("firstname","Name not Available") +
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("lastname",""));

        email.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("email","Email not Available"));
    }

}
