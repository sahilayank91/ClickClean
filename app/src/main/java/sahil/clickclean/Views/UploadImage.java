package sahil.clickclean.Views;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import sahil.clickclean.R;
import sahil.clickclean.utilities.Server;

public class UploadImage extends AppCompatActivity implements View.OnClickListener{
    //a constant to track the file chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;

    //Buttons
    private Button buttonChoose;
    private Button buttonUpload;
    FirebaseDatabase database;
    //ImageView
    private ImageView imageView;
    private RadioGroup radioGroup,serviceGroup;
    //a Uri object to store file path
    private Uri filePath;
    private StorageReference storageReference;
    private String type,service;
    private EditText offer,code;
    private TextView servicetext;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(UploadImage.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getting views from layout
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
servicetext = findViewById(R.id.servicetext);
servicetext.setVisibility(View.GONE);
        imageView = (ImageView) findViewById(R.id.imageView);

        code = findViewById(R.id.code);
        //attaching listener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        offer = findViewById(R.id.offer);
        offer.setVisibility(View.INVISIBLE);
code.setVisibility(View.GONE);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        serviceGroup = findViewById(R.id.servicegroup);

        serviceGroup.setVisibility(View.GONE);
        serviceGroup.clearCheck();
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    type = rb.getText().toString();
                    Toast.makeText(UploadImage.this, rb.getText(), Toast.LENGTH_SHORT).show();
                    if(rb.getText().equals("Offer")){
                        offer.setVisibility(View.VISIBLE);
                        code.setVisibility(View.VISIBLE);
                        serviceGroup.setVisibility(View.VISIBLE);
                        servicetext.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        serviceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    service = rb.getText().toString();
                    Toast.makeText(UploadImage.this, rb.getText(), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void onClear(View v) {
        /* Clears all selected radio buttons to default */
        radioGroup.clearCheck();
    }

    public void onSubmit(View v) {
        RadioButton rb = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        Toast.makeText(UploadImage.this, rb.getText(), Toast.LENGTH_SHORT).show();
        type= rb.getText().toString();
    }
    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        //if the clicked button is choose
        if (view == buttonChoose) {
            showFileChooser();
        }
        //if the clicked button is upload
        else if (view == buttonUpload) {
               if(type.equals("Donation")){
                   uploadDonationFile();
               }else{
                   uploadOfferFile();
               }


        }
    }
    private void uploadOfferFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final StorageReference riversRef = storageReference.child("images/" + filePath.getLastPathSegment());
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.e("Tuts+", "uri: " + uri.toString());
                                    String download_url = uri.toString();
//                                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("offer");
//                                    String key = myRef.push().getKey();
//
//                                    HashMap<String,String> map  = new HashMap<>();
//                                    map.put("url",download_url);
//                                    map.put("percentage",offer.getText().toString());
//                                    map.put("service",service);
//                                    map.put("code",code.getText().toString());
//                                    myRef.child(key).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//
//                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                            if(task.isSuccessful()) {
//                                                progressDialog.dismiss();
//                                                Toast.makeText(UploadImage.this, "Successfully uploaded", Toast.LENGTH_LONG).show();
//                                                Intent intent = new Intent(UploadImage.this,MainActivity.class);
//                                                startActivity(intent);
//                                                finish();
//
//                                            }else {
//                                                progressDialog.dismiss();
//                                                Toast.makeText(UploadImage.this, "Error happened during the upload process", Toast.LENGTH_LONG).show();
//                                            }
//                                        }
//                                    });


                                    new createOffer(code.getText().toString(),offer.getText().toString(),download_url,service).execute();

                                    //Handle whatever you're going to do with the URL here
                                }
                            });






                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
    private void uploadDonationFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final StorageReference riversRef = storageReference.child("donation_images/" + filePath.getLastPathSegment());
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.e("Tuts+", "uri: " + uri.toString());
                                    String download_url = uri.toString();
                                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("image");
                                    String key = myRef.push().getKey();
                                    myRef.child(key).setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override

                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Toast.makeText(UploadImage.this, "Successfully uploaded", Toast.LENGTH_LONG).show();

                                            }else {
                                                progressDialog.dismiss();
                                                Toast.makeText(UploadImage.this, "Error happened during the upload process", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                    //Handle whatever you're going to do with the URL here
                                }
                            });
                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }







    @SuppressLint("StaticFieldLeak")
    class createOffer extends AsyncTask<String, String, String> {
        boolean success = false;
        HashMap<String, String> params = new HashMap<>();



        createOffer(String code, String percentage, String url,String service){
            params.put("code", code);
            params.put("percentage", percentage);
            params.put("url", url);
            params.put("service",service);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (success) {
                Toast.makeText(getApplicationContext(), "Offer Created Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UploadImage.this, MainActivity.class);
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
                result = Server.post(getResources().getString(R.string.createOffer),json);
                success = true;
            } catch (Exception e){
                e.printStackTrace();
            }



            System.out.println("Result:" + result);
            return result;
        }
    }

}


