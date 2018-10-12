package sahil.clickclean.Views;

import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;


import sahil.clickclean.R;

public class ProfileActivity extends Activity {


    TextView name, email, address;
    ImageView profilePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.user_profile_name);
        email = findViewById(R.id.user_profile_email);
//        address = findViewById()
        profilePhoto = findViewById(R.id.user_profile_photo);

        name.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("firstname","Name not Available") +
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("lastname",""));

        email.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("email","Email not Available"));
    }

}
