package sahil.clickclean.Views.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;

public class SelectServiceFragment extends Fragment {
    View view;
    private Spinner mSpinner;
    public static String service=null;
    public SelectServiceFragment() {

    }

    CardView steamIroning, washfoldkg, washironkg, washiron, dryclean, premiumlaundry;
    TextView selectedService;
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
            }
        });



        return view;
    }


}
