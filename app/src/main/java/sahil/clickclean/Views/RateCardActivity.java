package sahil.clickclean.Views;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import sahil.clickclean.R;

public class RateCardActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RateCardActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(RateCardActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_card);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
