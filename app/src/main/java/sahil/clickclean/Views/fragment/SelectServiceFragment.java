package sahil.clickclean.Views.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;
import sahil.clickclean.Views.LoginActivity;
import sahil.clickclean.Views.MainActivity;
import sahil.clickclean.Views.RegisterActivity;

public class SelectServiceFragment extends Fragment {
    View view;
    private Spinner mSpinner;

    public static String service=null;
    public SelectServiceFragment() {

    }

    CardView steamIroning, washfoldkg, washironkg, washiron, dryclean, premiumlaundry;
    TextView selectedService;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_select_service, container, false);
        else return view;

        List<String> services = Arrays.asList(getResources().getStringArray(R.array.services));
        steamIroning = view.findViewById(R.id.a);
        washfoldkg = view.findViewById(R.id.b);
        dryclean = view.findViewById(R.id.c);
        washironkg = view.findViewById(R.id.d);
        washiron = view.findViewById(R.id.e);
        premiumlaundry = view.findViewById(R.id.f);
        selectedService = view.findViewById(R.id.selected_service);

        steamIroning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                steamIroning.setCardBackgroundColor(Color.GRAY);
                selectedService.setText("Steam Ironing");
                SharedPreferenceSingleton.getInstance(getContext()).put("service", "steamIroning");
                service = "steamIroning";
                washfoldkg.setCardBackgroundColor(Color.WHITE);
                dryclean.setCardBackgroundColor(Color.WHITE);
                washiron.setCardBackgroundColor(Color.WHITE);
                washironkg.setCardBackgroundColor(Color.WHITE);
                premiumlaundry.setCardBackgroundColor(Color.WHITE);

                CreateOrderFragment createOrderFragment = new CreateOrderFragment();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.main_container,createOrderFragment).commit();
            }
        });
        washfoldkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                washfoldkg.setCardBackgroundColor(Color.GRAY);
                selectedService.setText("Wash and Fold (Kg Basis)");
                SharedPreferenceSingleton.getInstance(getContext()).put("service", "washandfold_kg");
                service = "washandfold_kg";
                steamIroning.setCardBackgroundColor(Color.WHITE);
                dryclean.setCardBackgroundColor(Color.WHITE);
                washiron.setCardBackgroundColor(Color.WHITE);
                washironkg.setCardBackgroundColor(Color.WHITE);
                premiumlaundry.setCardBackgroundColor(Color.WHITE);
                CreateOrderFragment createOrderFragment = new CreateOrderFragment();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.main_container,createOrderFragment).commit();
            }
        });
        dryclean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dryclean.setCardBackgroundColor(Color.GRAY);
                selectedService.setText("Dry Cleaning");
                SharedPreferenceSingleton.getInstance(getContext()).put("service", "dryclean");
                service= "dryclean";
                steamIroning.setCardBackgroundColor(Color.WHITE);
                washfoldkg.setCardBackgroundColor(Color.WHITE);
                washiron.setCardBackgroundColor(Color.WHITE);
                washironkg.setCardBackgroundColor(Color.WHITE);
                premiumlaundry.setCardBackgroundColor(Color.WHITE);
                CreateOrderFragment createOrderFragment = new CreateOrderFragment();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.main_container,createOrderFragment).commit();
            }
        });
        washironkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    washironkg.setCardBackgroundColor(Color.GRAY);
                    selectedService.setText("Wash and Iron (Kg Basis)");
                SharedPreferenceSingleton.getInstance(getContext()).put("service", "washandiron_kg");
                service = "washandiron_kg";
                steamIroning.setCardBackgroundColor(Color.WHITE);
                    washfoldkg.setCardBackgroundColor(Color.WHITE);
                    washiron.setCardBackgroundColor(Color.WHITE);
                    dryclean.setCardBackgroundColor(Color.WHITE);
                    premiumlaundry.setCardBackgroundColor(Color.WHITE);
                CreateOrderFragment createOrderFragment = new CreateOrderFragment();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.main_container,createOrderFragment).commit();
            }
        });
        washiron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                washiron.setCardBackgroundColor(Color.GRAY);
                selectedService.setText("Wash and Iron");
                SharedPreferenceSingleton.getInstance(getContext()).put("service", "washandiron");
                service = "washandiron";
                steamIroning.setCardBackgroundColor(Color.WHITE);
                washfoldkg.setCardBackgroundColor(Color.WHITE);
                washironkg.setCardBackgroundColor(Color.WHITE);
                dryclean.setCardBackgroundColor(Color.WHITE);
                premiumlaundry.setCardBackgroundColor(Color.WHITE);
                CreateOrderFragment createOrderFragment = new CreateOrderFragment();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.main_container,createOrderFragment).commit();
            }
        });

        premiumlaundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premiumlaundry.setCardBackgroundColor(Color.GRAY);
                selectedService.setText("Premium Laundry");
                SharedPreferenceSingleton.getInstance(getContext()).put("service", "premiumLaundry");
                service  = "premiumLaundry";
                steamIroning.setCardBackgroundColor(Color.WHITE);
                washfoldkg.setCardBackgroundColor(Color.WHITE);
                washironkg.setCardBackgroundColor(Color.WHITE);
                dryclean.setCardBackgroundColor(Color.WHITE);
                washiron.setCardBackgroundColor(Color.WHITE);
                CreateOrderFragment createOrderFragment = new CreateOrderFragment();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.main_container,createOrderFragment).commit();
            }
        });



        return view;
    }


}
