package edu.cnm.deepdive.dungeonrunclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.dungeonrunclient.service.UserRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.UUID;

/**
 * For the leaderboard to be set up and viewed in the application.
 */
public class LeaderboardViewModel extends AndroidViewModel implements LifecycleObserver {

  private final UserRepository userRepository;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<UUID> userId;
  private final CompositeDisposable pending;

  /**
   * Sets the fields of the leaderboard to be used for the viewmodels to be displayed when called.
   * @param application creates an Instance of application.
   */
  public LeaderboardViewModel(@NonNull Application application) {
    super(application);
    userRepository = new UserRepository(application);
    throwable = new MutableLiveData<>();
    userId = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }
}


