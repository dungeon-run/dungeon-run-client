package edu.cnm.deepdive.dungeonrunclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;

/**
 * Allows the viewModel for the game to be set up on the game screen.
 */
public class GameViewModel extends AndroidViewModel implements LifecycleObserver {

  public GameViewModel(@NonNull Application application) {
    super(application);
  }
}
