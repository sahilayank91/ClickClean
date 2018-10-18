package sahil.clickclean.Views.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import sahil.clickclean.R;

public class CheckoutFragment extends Fragment{

    View view;
    public CheckoutFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) view = inflater.inflate(R.layout.fragment_checkout, container, false);
        else return view;


        return view;
    }
}
