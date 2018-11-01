package sahil.clickclean.Views;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import sahil.clickclean.R;

public class RateCardActivity extends Activity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RateCardActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_card);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
