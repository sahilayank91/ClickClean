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
import sahil.clickclean.model.RateCard;

public class CreateOrderFragment extends Fragment implements View.OnClickListener{
    View view;
    private RecyclerView recyclerView;
    private ArrayList<RateCard> listCloth = new ArrayList<>();
    private Button checkout;
    private ClothListAdapter adapter;
    public static int upper = 0, bottom = 0, woollen = 0, jacket = 0, blancket_single = 0, blancket_double = 0, bedsheet_single = 0, bedsheet_double = 0;

    public static HashMap<String,String> order = new HashMap<>();
    public static Double total  = 0.0;
    public CreateOrderFragment(){

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
        final String service = getArguments().getString("service");
        final String type = getArguments().getString("type");
        final String percentage = getArguments().getString("percentage");
        final String offerid = getArguments().getString("offerid");
        final String code = getArguments().getString("code");
        Log.e("type",type);
        Log.e("service",service);

        Log.e("percentage",percentage);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ClothListAdapter(getContext(), listCloth, service, type);
        recyclerView.setAdapter(adapter);

        assert service != null;
        if(service.equals("Dryclean")){
            getDryClean();
        }else{
            getRateDetails();
        }

        checkout = view.findViewById(R.id.checkoutbutton);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total==0){
                    Toast.makeText(getContext(),"Please select some of the clothes",Toast.LENGTH_LONG).show();
                }else{
                    Log.e("total:",String.valueOf(total));
                    Gson gson = new Gson();
                    String json = gson.toJson(order);
                    Log.e("order:",json);

                    assert service != null;


                    if(type.equals("Express")){
                        total = total*2;
                    }
                    Log.e("total after pre",String.valueOf(total));


                    Double d = Double.parseDouble(percentage);

                    total = total - (d/100)*total;



                    Bundle bundle = new Bundle();
                    bundle.putString("total",String.valueOf(total.intValue()+ 10));
                    bundle.putString("order",json);
                    bundle.putString("service",service);
                    bundle.putString("type",type);

                    if(code!=null){
                        bundle.putString("code",code);
                    }

                    if(offerid!=null){
                        bundle.putString("offerid",offerid);
                    }


                    if(d>0){
                        bundle.putString("offer","Yes");
                    }else{
                        bundle.putString("offer","No");
                    }
                    total = 0.0;
                    order.clear();
                    AddAddressFragment addAddressFragment = new AddAddressFragment();
                    addAddressFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.service_main_container,addAddressFragment).commit();

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

    public void getDryClean(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("drycleaning");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
                RateCard rateCard = new RateCard();
                rateCard.setCloth(dataSnapshot.getKey());
                rateCard.setDryclean(dataSnapshot.getValue().toString());
                listCloth.add(rateCard);
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
