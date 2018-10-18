package sahil.clickclean.Views.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class SelectServiceFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    View view;
    private Spinner mSpinner;
    public SelectServiceFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) view = inflater.inflate(R.layout.fragment_select_service, container, false);
        else return view;
        List<String> services = Arrays.asList(getResources().getStringArray(R.array.services));
        Spinner spinner = view.findViewById(R.id.service_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        Toast.makeText(getContext(),"item selected: "+ item,Toast.LENGTH_LONG).show();



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
