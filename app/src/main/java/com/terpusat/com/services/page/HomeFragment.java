package com.terpusat.com.services.page;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.terpusat.com.services.R;
import com.terpusat.com.services.background.MainServices;
import com.terpusat.com.services.database.SqlliteDriver;
import com.terpusat.com.services.monitor.GPSTracker;
/**
 * Created by prakasa on 03/06/16.
 */
public class HomeFragment extends Fragment {
    private Context parent;
    private TextView gpsStatus;
    private Switch serviceSwitch;
    private TextView switchStatus;

    public HomeFragment(Context parentContext) {
        this.parent = parentContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        /*
        * Used OwN Libraries
        * GPS
        * SQLLite
        * */
        final GPSTracker gps = new GPSTracker(parent);
        final SqlliteDriver mydb = new SqlliteDriver(parent);
        final Intent services = new Intent(parent, MainServices.class);

        serviceSwitch = (Switch) rootView.findViewById(R.id.service_switch);
        switchStatus = (TextView) rootView.findViewById(R.id.text_status);

        gpsStatus = (TextView) rootView.findViewById(R.id.latlong);

        Cursor rest = mydb.getDataByName();
        rest.moveToFirst();
        final String idUpdate = rest.getString(rest.getColumnIndex(SqlliteDriver.PENGATURAN_COLUMN_ID));
        final String statusUpdate = rest.getString(rest.getColumnIndex(SqlliteDriver.PENGATURAN_COLUMN_STATUS));
        if( statusUpdate.matches("1") )
            serviceSwitch.setChecked(true);

        if( serviceSwitch.isChecked() ) {
            parent.startService(services);
            if(gps.canGetLocation()){
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                gpsStatus.setText("Longitude : " + longitude + " Latitude : " + latitude);
                switchStatus.setText("Switch is currently ON");
            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }
        }

        serviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    parent.startService(services);

                    if(gps.canGetLocation()){
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        gpsStatus.setText("Longitude : " + longitude + " Latitude : " + latitude);
                        switchStatus.setText("Switch is currently ON");
                    }else{
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }
                }else{
                    mydb.updateConfigStatus(Integer.parseInt(idUpdate), Integer.parseInt(statusUpdate));
                    parent.stopService(services); // BG Service Stop
                    gps.stopUsingGPS();
                    gpsStatus.setText("Service Inactive");
                    switchStatus.setText("Switch is currently OFF");
                }
            }
        });
        return rootView;
    }
}
