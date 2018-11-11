package sahil.clickclean.Views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;
import sahil.clickclean.model.User;
import sahil.clickclean.model.UserData;
import sahil.clickclean.utilities.Server;

public class ProfileActivity extends AppCompatActivity {
    EditText firstname,lastname;
    TextView phone,email;
    EditText city,flat;
    Double latitude,longitude;
    Button address,save;
    private ProgressDialog progress;

    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private final static int PLACE_PICKER_REQUEST = 1;
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
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        flat = findViewById(R.id.flataddress);
        city = findViewById(R.id.city);
        address  = findViewById(R.id.locality);
        save  = findViewById(R.id.save);
        String fname = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("firstname", "User Not Registered");
        String lname = SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("lastname", "User Not Registered");

        firstname.setText(fname);
        lastname.setText(lname);


        email.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("email", "Email Not Registered"));
        address.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("address", "Address Not Registered"));
        phone.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("phone", "Phone Not Registered"));
        flat.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("flataddress", "Flat Address Not Registered"));
        city.setText(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("city", "User city Not Registered"));


        latitude = Double.parseDouble(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("latitude", "Latitude not available"));
        longitude = Double.parseDouble(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("longitude", "Longitude not available"));

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermission();

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(ProfileActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateUser().execute();
            }
        });
    }



    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(ProfileActivity.this, data);
                address.setText(place.getAddress());
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;

            }
        }
    }



    @SuppressLint("StaticFieldLeak")
    class UpdateUser extends AsyncTask<String, String, String> {
        boolean success = false;
        HashMap<String, String> params = new HashMap<>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(ProfileActivity.this);
            progress.setMessage("Updating your Details");
            progress.show();
            params.put("_id",SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("_id", "User Not Registered"));
            params.put("firstname", firstname.getText().toString());
            params.put("lastname", lastname.getText().toString());
            params.put("address", address.getText().toString());
            params.put("flataddress",flat.getText().toString());
            params.put("city",city.getText().toString());
            params.put("latitude",String.valueOf(latitude));
            params.put("longitude",String.valueOf(longitude));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.dismiss();
            if (true) {
//                Toast.makeText(getApplicationContext(), R.string.update_success, Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();

                new GetUser(SharedPreferenceSingleton.getInstance(getApplicationContext()).getString("_id", "User Not Registered")).execute();

            } else {
                Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                Gson gson = new Gson();
                String json = gson.toJson(params);
                result = Server.post(getResources().getString(R.string.updateUser),json);
                Log.e("result",result);
                success = true;
            } catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }

    @SuppressLint("StaticFieldLeak")
    class GetUser extends AsyncTask<String, String, String> {

        private ProgressDialog progress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(ProfileActivity.this);
            progress.setMessage("Updating local Database...");
//            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();

        }

        private final String mId;
        HashMap<String,String> map = new HashMap<>();
        private Boolean success = false;
        GetUser(String id) {
            mId = id;
            map.put("_id",mId);
        }

        @Override
        protected String doInBackground(String... strings) {
            // TODO: attempt authentication against a network service.
            String result="";
            try {
                Gson gson = new Gson();
                String json = gson.toJson(map);
                result = Server.post(getResources().getString(R.string.loginById),json);
                success = true;
                UserData.getInstance(getApplicationContext()).initUserData(new User(new JSONObject(result)), getApplicationContext());
                return result;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
            // TODO: register the new account here.

        }

        @Override
        protected void onPostExecute(String s) {
            progress.dismiss();

            super.onPostExecute(s);
            if (success) {
                Toast.makeText(getApplicationContext(), R.string.update_success, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                //REMOVE THIS AS TESTING IS OVER
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
                Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
            }
        }



    }








}
