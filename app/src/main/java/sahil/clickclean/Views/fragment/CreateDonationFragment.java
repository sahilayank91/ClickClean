package sahil.clickclean.Views.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import sahil.clickclean.R;
import sahil.clickclean.adapter.ClothListAdapter;
import sahil.clickclean.adapter.DonationAdapter;
import sahil.clickclean.model.RateCard;

public class CreateDonationFragment extends Fragment implements View.OnClickListener{
    View view;
    private ArrayList<RateCard> listCloth = new ArrayList<>();
    public static HashMap<String,String> order = new HashMap<>();
    public static Integer total  = 0;
    DonationAdapter adapter;
    public CreateDonationFragment(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SelectServiceFragment selectServiceFragment = new SelectServiceFragment();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.main_container,selectServiceFragment).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) view = inflater.inflate(R.layout.fragment_add_order, container, false);
        else return view;


        assert getArguments() != null;
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new DonationAdapter(getContext(), listCloth);
        recyclerView.setAdapter(adapter);

        getRateDetails();
        Button checkout = view.findViewById(R.id.checkoutbutton);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total<=0){
                    Toast.makeText(getContext(),"Please select some of the clothes",Toast.LENGTH_LONG).show();
                }else{
                    Gson gson = new Gson();
                    String json = gson.toJson(order);
                    Bundle bundle = new Bundle();
                    bundle.putString("order",json);
                    bundle.putString("total",String.valueOf(total));
                    order.clear();
                    CheckoutDonationFragment checkoutDonationFragment = new CheckoutDonationFragment();
                    checkoutDonationFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.service_main_container,checkoutDonationFragment).commit();

                }

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {

    }
    public void getRateDetails(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clothes");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
//                Post newPost = dataSnapshot.getValue(Post.class);
                Log.e("fasfdsfsfsa",dataSnapshot.toString());
                RateCard cloth = new RateCard();
                cloth.setCloth(dataSnapshot.getKey());
                if(dataSnapshot.hasChild("Wash and Iron")){
                    cloth.setWashandiron(dataSnapshot.child("Wash and Iron").getValue().toString());

                }
                if(dataSnapshot.hasChild("Wash")){
                    cloth.setWash(dataSnapshot.child("Wash").getValue().toString());
                }
                if(dataSnapshot.hasChild("Iron")){
                    cloth.setIron(dataSnapshot.child("Iron").getValue().toString());
                }
                if(dataSnapshot.hasChild("icon")){
                    cloth.setIcon(dataSnapshot.child("icon").getValue().toString());
                }

                listCloth.add(cloth);
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


}
