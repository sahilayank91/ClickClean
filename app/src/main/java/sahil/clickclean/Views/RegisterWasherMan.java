package sahil.clickclean.Views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;

import java.util.HashMap;

import sahil.clickclean.R;
import sahil.clickclean.utilities.Server;

public class RegisterWasherMan extends AppCompatActivity {

    EditText mFirstname, mLastname, mEmail, mPhone,mSecondaryPhone,mAddress,mPassword,mUserFlat,mUserPincode,mUserCity;;
    String firstname,lastname, password,useremail,secondaryPhone, userphone, useraddress,userflataddress,usercity,userpincode;
    TextView placeNameText;
    TextView placeAddressText;
    WebView attributionText;
    Button registerWasherman;
    public double longitude,latitude;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private final static int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_washer_man);

        requestPermission();

        mFirstname = findViewById(R.id.firstname);
        mLastname = findViewById(R.id.lastname);
        mEmail = findViewById(R.id.email);
        mPhone = findViewById(R.id.phone);
        mAddress=findViewById(R.id.address);
        mPassword = findViewById(R.id.password);
        mUserFlat = findViewById(R.id.flataddress);
        mSecondaryPhone = findViewById(R.id.secondary_phone);
        mUserCity = findViewById(R.id.city);
        mUserPincode = findViewById(R.id.pincode);
        registerWasherman= findViewById(R.id.register_washerman);
        attributionText = (WebView) findViewById(R.id.wvAttribution);
        mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(RegisterWasherMan.this,"Wait till we access the Map",Toast.LENGTH_LONG).show();
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(RegisterWasherMan.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        registerWasherman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname = mFirstname.getText().toString();
                lastname = mLastname.getText().toString();
                useremail = mEmail.getText().toString();
                userphone = mPhone.getText().toString();
                password = mPassword.getText().toString();
                useraddress = mAddress.getText().toString();
                userflataddress = mUserFlat.getText().toString();
                secondaryPhone = mSecondaryPhone.getText().toString();
                usercity = mUserCity.getText().toString();
                userpincode = mUserPincode.getText().toString();
                new RegisterUser().execute();
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
                Place place = PlacePicker.getPlace(RegisterWasherMan.this, data);
                mAddress.setText(place.getAddress());
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                if (place.getAttributions() == null) {
                    attributionText.loadData("no attribution", "text/html; charset=utf-8", "UFT-8");
                } else {
                    attributionText.loadData(place.getAttributions().toString(), "text/html; charset=utf-8", "UFT-8");
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    class RegisterUser extends AsyncTask<String, String, String> {
        boolean success = false;
        HashMap<String, String> params = new HashMap<>();
        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            params.put("firstname", firstname);
            params.put("lastname", lastname);
            params.put("address", useraddress);
            params.put("password", password);
            params.put("phone",userphone);
            params.put("email",useremail);
            params.put("userflataddress",userflataddress);
            params.put("secondary_phone",secondaryPhone);
            params.put("pincode",userpincode);
            params.put("city",usercity);
            params.put("latitude",String.valueOf(latitude));
            params.put("longitude",String.valueOf(longitude));
            params.put("role","Washerman");
            progress=new ProgressDialog(RegisterWasherMan.this);
            progress.setMessage("Registering..");
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.dismiss();
            if (success) {
                Toast.makeText(getApplicationContext(), R.string.reg_success, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterWasherMan.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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
                System.out.println(json);
                result = Server.post(getResources().getString(R.string.register),json);
                success = true;
            } catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("Result:" + result);
            return result;
        }
    }
}