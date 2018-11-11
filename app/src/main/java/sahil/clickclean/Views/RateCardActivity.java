package sahil.clickclean.Views;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import sahil.clickclean.R;
import sahil.clickclean.adapter.OrderAdapter;
import sahil.clickclean.adapter.RateCardAdapter;
import sahil.clickclean.interfaces.RCVItemClickListener;
import sahil.clickclean.model.RateCard;

public class RateCardActivity extends AppCompatActivity implements RCVItemClickListener{
    private RecyclerView recyclerView;
    private RateCardAdapter adapter;
    ArrayList<RateCard> listRateCard = new ArrayList<>();
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
        getSupportActionBar().setTitle("Rate Card");
        recyclerView = (RecyclerView) findViewById(R.id.rate_card_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RateCardAdapter(this, listRateCard);
        recyclerView.setAdapter(adapter);
        adapter.setRcvItemClickListener(this);
        getRateDetails();
    }

    public void getRateDetails(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clothes");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
                RateCard rateCard = new RateCard();
                rateCard.setCloth(dataSnapshot.getKey());
                if(dataSnapshot.hasChild("Wash and Iron")){
                    rateCard.setWashandiron(dataSnapshot.child("Wash and Iron").getValue().toString());

                }
                if(dataSnapshot.hasChild("Wash")){
                    rateCard.setWash(dataSnapshot.child("Wash").getValue().toString());
                }

                if(dataSnapshot.hasChild("Iron")){
                    rateCard.setIron(dataSnapshot.child("Iron").getValue().toString());
                }
                listRateCard.add(rateCard);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
