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
import sahil.clickclean.Views.SchedulePickup;

public class SelectServiceFragment extends Fragment {
    View view;
    public static String service=null;
    public SelectServiceFragment() {

    }

    CardView normal_steam,normal_wash_and_fold,normal_wash_and_iron;
    CardView express_steam,express_wash_and_fold,express_wash_and_iron;
    CardView dryclean;
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


        normal_steam = view.findViewById(R.id.normal_steam);
        normal_wash_and_fold = view.findViewById(R.id.normal_wash_and_fold);
        normal_wash_and_iron = view.findViewById(R.id.normal_wash_and_iron);
        express_steam = view.findViewById(R.id.express_steam);
        express_wash_and_fold = view.findViewById(R.id.express_wash_and_fold);
        express_wash_and_iron = view.findViewById(R.id.express_wash_and_iron);
        dryclean = view.findViewById(R.id.dryclean);

        final Intent intent = new Intent(getContext(), SchedulePickup.class);
        normal_steam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Steam Ironing");
                intent.putExtra("type","normal");
                startActivity(intent);
            }
        });

        normal_wash_and_fold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Wash and Fold");
                intent.putExtra("type","normal");
                startActivity(intent);
            }
        });

        normal_wash_and_iron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Wash and Iron");
                intent.putExtra("type","normal");
                startActivity(intent);

            }
        });


        express_steam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Steam Ironing");
                intent.putExtra("type","express");
                startActivity(intent);

            }
        });

        express_wash_and_iron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Wash and Iron");
                intent.putExtra("type","express");
                startActivity(intent);

            }
        });

        express_wash_and_fold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Wash and Fold");
                intent.putExtra("type","express");
                startActivity(intent);

            }
        });
        dryclean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Dry Clean");
                intent.putExtra("type","Normal");
                startActivity(intent);
            }

        });


        return view;
    }


}
