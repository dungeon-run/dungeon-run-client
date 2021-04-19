package edu.cnm.deepdive.dungeonrunclient.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// TODO Discard unused class at some point.
public class SettingsViewModel extends ViewModel {

  private MutableLiveData<String> mText;

  public SettingsViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is a test");
  }

  public LiveData<String> getText() {
    return mText;
  }
}
