package sahil.clickclean.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.service.autofill.UserData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


import sahil.clickclean.R;
import sahil.clickclean.model.User;
import sahil.clickclean.utilities.Server;

public class RegisterActivity extends AppCompatActivity {

    EditText mFirstname, mLastname, mEmail, mPhone,mAddress,mPassword;
    String firstname,lastname, password,useremail, userphone, useraddress;
    Button submit;

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

                User user = new User();

                user.setFirstname(firstname);
                user.setAddress(useraddress);
                user.setEmail(useremail);
                user.setLastname(lastname);
                user.setPhone(userphone);
                new RegisterUser().execute();
            }
        });


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
            progress=new ProgressDialog(RegisterActivity.this);
            progress.setMessage("Registering..");
//            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
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
