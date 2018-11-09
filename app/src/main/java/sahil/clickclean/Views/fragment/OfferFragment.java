package sahil.clickclean.Views.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import sahil.clickclean.R;
import sahil.clickclean.adapter.OfferAdapter;
import sahil.clickclean.adapter.OrderAdapter;
import sahil.clickclean.interfaces.RCVItemClickListener;

public class OfferFragment extends Fragment implements RCVItemClickListener{
    View view;
    private ArrayList<String> images = new ArrayList<>();
    private RecyclerView recyclerView;
    private OfferAdapter offerAdapter;

    public OfferFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) view = inflater.inflate(R.layout.fragment_offer, container, false);
        else return view;
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        offerAdapter = new OfferAdapter(getActivity(), images);
        recyclerView.setAdapter(offerAdapter);

        getOffers();




        return view;
    }

    public void getOffers(){

        DatabaseReference refs = FirebaseDatabase.getInstance().getReference("image");
        refs.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
//                Post newPost = dataSnapshot.getValue(Post.class);
                Log.e("sasa",dataSnapshot.toString());
                images.add(dataSnapshot.getValue().toString());
                offerAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                images.remove(dataSnapshot.getValue().toString());
                offerAdapter.notifyDataSetChanged();

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
