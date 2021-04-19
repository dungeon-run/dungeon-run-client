package edu.cnm.deepdive.dungeonrunclient.controller;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import edu.cnm.deepdive.dungeonrunclient.R;

/**
 * Allows the users to access the settings menu. Settings menu has an option to allow the user to
 * sign out of the application. And will also allow the user to change the difficulty of the
 * mazes when starting a game.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.preferences, rootKey);
  }

}