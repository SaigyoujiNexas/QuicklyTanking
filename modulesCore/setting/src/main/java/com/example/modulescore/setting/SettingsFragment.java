package com.example.modulescore.setting;

import android.os.Bundle;
import android.widget.Button;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        var bt = new Button(getContext());
    }
}