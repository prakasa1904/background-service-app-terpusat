package com.terpusat.com.services.page;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.terpusat.com.services.R;

/**
 * Created by prakasa on 03/06/16.
 */
public class SettingFragment extends Fragment {
    private Context parent;
    public SettingFragment(Context parentContext) {
        this.parent = parentContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        return rootView;
    }
}
