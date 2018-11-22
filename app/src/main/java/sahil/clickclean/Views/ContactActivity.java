package sahil.clickclean.Views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import sahil.clickclean.R;

public class ContactActivity extends AppCompatActivity {

    ImageView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact Us");
    email = findViewById(R.id.emailbutton);

    email.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        String[] TO = {"yoursdhobi@gmail.com"};
//        String[] CC = {""};
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//        emailIntent.setData(Uri.parse("mailto:"));
//        emailIntent.setType("text/plain");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Query");
//
//        try {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            finish();
//            Log.i("Finished sending email.", "");
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(ContactActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
        String url = "https://api.whatsapp.com/send?phone="+"9636004841";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
});

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ContactActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
