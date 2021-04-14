package edu.cnm.deepdive.dungeonrunclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import edu.cnm.deepdive.dungeonrunclient.controller.GameFragment;

public class GameViewModel extends AndroidViewModel implements LifecycleObserver {

  public GameViewModel(@NonNull Application application) {
    super(application);
  }
}
