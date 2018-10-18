package sahil.clickclean.Views;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.service.autofill.UserData;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import sahil.clickclean.model.User;
import sahil.clickclean.utilities.Server;

public class RegisterActivity extends AppCompatActivity {

    EditText mFirstname, mLastname, mEmail, mPhone,mAddress,mPassword,mUserFlat;
    String firstname,lastname, password,useremail, userphone, useraddress,userflataddress;
    Button submit;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private final static int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirstname = findViewById(R.id.firstname);
        mLastname = findViewById(R.id.lastname);
        mEmail = findViewById(R.id.useremail);
        mPhone = findViewById(R.id.userphone);
        mAddress=findViewById(R.id.useraddress);
        mPassword = findViewById(R.id.userpassword);
        mUserFlat = findViewById(R.id.userflatAddress);

        submit = findViewById(R.id.register);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname = mFirstname.getText().toString();
                lastname = mLastname.getText().toString();
                useremail = mEmail.getText().toString();
                userphone = mPhone.getText().toString();
                password = mPassword.getText().toString();
                useraddress = mAddress.getText().toString();
                userflataddress = mUserFlat.getText().toString();

                User user = new User();

                user.setFirstname(firstname);
                user.setAddress(useraddress);
                user.setEmail(useremail);
                user.setLastname(lastname);
                user.setPhone(userphone);
                new RegisterUser().execute();
            }
        });

                mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermission();

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(RegisterActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

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
                Place place = PlacePicker.getPlace(RegisterActivity.this, data);
                mAddress.setText(place.getAddress());
                if (place.getAttributions() == null) {
                } else {
                }
            }
        }
    }


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
            progress=new ProgressDialog(RegisterActivity.this);
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
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
