package com.kanyiakinyidevelopers.goals.ui.fragments.main

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.kanyiakinyidevelopers.goals.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}