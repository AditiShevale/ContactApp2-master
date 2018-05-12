package com.example.aditi.contactapp2.SharedPref;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.aditi.contactapp2.R;

public class PreferenceFragment extends PreferenceFragmentCompat  {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.xml);
    }
}
