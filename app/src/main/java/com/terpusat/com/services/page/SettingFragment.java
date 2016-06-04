package com.terpusat.com.services.page;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.terpusat.com.services.R;
import com.terpusat.com.services.database.SqlliteDriver;

/**
 * Created by prakasa on 03/06/16.
 */
public class SettingFragment extends Fragment {
    private Context parent;
    private TextView namaEdit;
    private TextView timeEdit;
    private TextView urlEdit;
    private TextView statusEdit;
    private Button btnSave;

    public SettingFragment(Context parentContext) {
        this.parent = parentContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        /*
        * Used OwN Libraries
        * SQLLite
        * */
        SqlliteDriver mydb = new SqlliteDriver(parent);

        namaEdit = (TextView) rootView.findViewById(R.id.editTextName);
        timeEdit = (TextView) rootView.findViewById(R.id.editTextTime);
        urlEdit = (TextView) rootView.findViewById(R.id.editTextUrl);
        statusEdit = (TextView) rootView.findViewById(R.id.editTextStatus);
        btnSave = (Button) rootView.findViewById(R.id.buttonSave);

        Cursor rest = mydb.getDataByName();
        rest.moveToFirst();

        String idUpdate = rest.getString(rest.getColumnIndex(SqlliteDriver.PENGATURAN_COLUMN_ID));
        String namaUpdate = rest.getString(rest.getColumnIndex(SqlliteDriver.PENGATURAN_COLUMN_NAMA));
        String timeUpdate = rest.getString(rest.getColumnIndex(SqlliteDriver.PENGATURAN_COLUMN_TIME));
        String urlUpdate = rest.getString(rest.getColumnIndex(SqlliteDriver.PENGATURAN_COLUMN_URL));
        String statusUpdate = rest.getString(rest.getColumnIndex(SqlliteDriver.PENGATURAN_COLUMN_STATUS));

        if (!rest.isClosed())
            rest.close();

        namaEdit.setText(namaUpdate);
        namaEdit.setFocusable(false);
        namaEdit.setClickable(false);

        timeEdit.setText(timeUpdate);
        urlEdit.setText(urlUpdate);
        statusEdit.setText(statusUpdate);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment fragment = new HomeFragment(parent);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            }
        });

        return rootView;
    }
}
