package com.terpusat.com.services.page;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.terpusat.com.services.R;
import com.terpusat.com.services.database.SqlliteDriver;
/**
 * Created by prakasa on 03/06/16.
 */
public class HomeFragment extends Fragment {
    private TextView switchStatus;
    private Switch serviceSwitch;
    SqlliteDriver mydb;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        serviceSwitch = (Switch) rootView.findViewById(R.id.service_switch);
        switchStatus = (TextView) rootView.findViewById(R.id.text_status);

        serviceSwitch.setChecked(true);
        serviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    switchStatus.setText("Switch is currently ON");
                }else{
                    switchStatus.setText("Switch is currently OFF");
                }
            }
        });
        return rootView;
    }
}
