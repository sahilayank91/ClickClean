package sahil.clickclean.Views.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import sahil.clickclean.R;

public class SelectServiceFragment extends Fragment {
    View view;
    private Spinner mSpinner;

    public SelectServiceFragment() {

    }

    CardView steamIroning, washfoldkg, washironkg, washiron, dryclean, premiumlaundry;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_select_service, container, false);
        else return view;

        List<String> services = Arrays.asList(getResources().getStringArray(R.array.services));
//        Spinner spinner = view.findViewById(R.id.service_spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, services);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spinner.setAdapter(adapter);
        steamIroning = view.findViewById(R.id.a);
        washfoldkg = view.findViewById(R.id.b);
        dryclean = view.findViewById(R.id.c);
        washironkg = view.findViewById(R.id.d);
        washiron = view.findViewById(R.id.e);
        premiumlaundry = view.findViewById(R.id.f);
//        steamIroning.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                steamIroning.setCardBackgroundColor(Color.GRAY);
//            }
//        });
//        washfoldkg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                washfoldkg.setCardBackgroundColor(Color.GRAY);
//            }
//        });
//


        return view;
    }


}
