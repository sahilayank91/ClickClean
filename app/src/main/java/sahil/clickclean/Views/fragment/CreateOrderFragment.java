package sahil.clickclean.Views.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.INotificationSideChannel;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import sahil.clickclean.R;
import sahil.clickclean.Views.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class CreateOrderFragment extends Fragment implements View.OnClickListener{
    View view;
    private Button checkout;
    public static int upper = 0, bottom = 0, woollen = 0, jacket = 0, blancket_single = 0, blancket_double = 0, bedsheet_single = 0, bedsheet_double = 0;
    private ImageButton upper_min,upper_add,bottom_min,bottom_add,woollen_min,woollen_add,jacket_min, jacket_add,blancket_single_min,blancket_single_add,
            blancket_double_min,blancket_double_add,bedsheet_single_min,bedsheet_single_add,bedsheet_double_min,bedsheet_double_add;
    private Button setAddress;
    public TextView mupper, mbottom, mwoollen, mjacket, mblanketsingle, mblanketdouble, mbedsheetsingle, mbedsheetdouble;
    public CreateOrderFragment(){
        //necessary default constructor
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
        checkout = view.findViewById(R.id.checkoutbutton);
        upper_min = view.findViewById(R.id.min_upper);
        upper_add = view.findViewById(R.id.add_upper);
        mupper = view.findViewById(R.id.uppers);

        bottom_min = view.findViewById(R.id.min_bottom);
        bottom_add = view.findViewById(R.id.add_bottom);
        mbottom = view.findViewById(R.id.bottom);

        woollen_min = view.findViewById(R.id.min_woollen);
        woollen_add = view.findViewById(R.id.add_woollen);
        mwoollen = view.findViewById(R.id.woollen);

        jacket_min = view.findViewById(R.id.min_jacket);
        jacket_add = view.findViewById(R.id.add_jacket);
        mjacket = view.findViewById(R.id.jacket);

        blancket_single_min = view.findViewById(R.id.min_blanket_single);
        blancket_single_add = view.findViewById(R.id.add_blanket_single);
        mblanketsingle = view.findViewById(R.id.blanket_single);

        blancket_double_min = view.findViewById(R.id.min_blanket_double);
        blancket_double_add = view.findViewById(R.id.add_blanket_double);
        mblanketdouble = view.findViewById(R.id.blanket_double);

        bedsheet_single_min = view.findViewById(R.id.min_bedsheet_single);
        bedsheet_single_add = view.findViewById(R.id.add_bedsheet_single);
        mbedsheetsingle = view.findViewById(R.id.bedsheet_single);

        bedsheet_double_min = view.findViewById(R.id.min_bedsheet_double);
        bedsheet_double_add = view.findViewById(R.id.add_bedsheet_double);
        mbedsheetdouble = view.findViewById(R.id.bedsheet_double);



        upper_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upper!=0)
                upper--;
                mupper.setText(String.valueOf(upper));
            }
        });


        upper_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upper++;
                mupper.setText(String.valueOf(upper));
            }
        });

        bottom_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottom!=0)
                bottom--;
                mbottom.setText(String.valueOf(bottom));
            }
        });

        bottom_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom++;
                mbottom.setText(String.valueOf(bottom));
            }
        });

        woollen_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(woollen!=0)
                woollen--;
                mwoollen.setText(String.valueOf(woollen));
            }
        });

        woollen_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                woollen++;
                mwoollen.setText(String.valueOf(woollen));
            }
        });

        jacket_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(jacket!=0)
                jacket--;
                mjacket.setText(String.valueOf(jacket));
            }
        });

        jacket_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jacket++;
                mjacket.setText(String.valueOf(jacket));
            }
        });

        blancket_single_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blancket_single!=0)
                blancket_single--;
                mblanketsingle.setText(String.valueOf(blancket_single));
            }
        });
        blancket_single_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blancket_single++;
                mblanketsingle.setText(String.valueOf(blancket_single));
            }
        });

        blancket_double_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blancket_double!=0)
                blancket_double--;
                mblanketdouble.setText(String.valueOf(blancket_double));
            }
        });
        blancket_double_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blancket_double++;
                mblanketdouble.setText(String.valueOf(blancket_double));
            }
        });

        bedsheet_single_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bedsheet_single!=0)
                bedsheet_single--;
                mbedsheetsingle.setText(String.valueOf(bedsheet_single));
            }
        });
        bedsheet_single_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bedsheet_single++;
                mbedsheetsingle.setText(String.valueOf(bedsheet_single));
            }
        });

        bedsheet_double_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bedsheet_double!=0)
                bedsheet_double--;
                mbedsheetdouble.setText(String.valueOf(bedsheet_double));
            }
        });

        bedsheet_double_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bedsheet_double++;
                mbedsheetdouble.setText(String.valueOf(bedsheet_double));
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CreateOrderFragment.upper  + CreateOrderFragment.bottom + CreateOrderFragment.woollen + CreateOrderFragment.blancket_single + CreateOrderFragment.blancket_double + CreateOrderFragment.bedsheet_single+ CreateOrderFragment.bedsheet_double + CreateOrderFragment.jacket ==0 ){
                    Toast.makeText(getContext(),"Please select some of the clothes",Toast.LENGTH_LONG).show();
                }else{
                    AddAddressFragment addAddressFragment = new AddAddressFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.main_container,addAddressFragment).commit();

                }

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
