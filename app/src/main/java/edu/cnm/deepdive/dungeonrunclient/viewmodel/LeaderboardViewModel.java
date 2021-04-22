package edu.cnm.deepdive.dungeonrunclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.dungeonrunclient.model.Attempt;
import edu.cnm.deepdive.dungeonrunclient.service.AttemptRepository;
import edu.cnm.deepdive.dungeonrunclient.service.UserRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import java.util.UUID;

/**
 * For the leaderboard to be set up and viewed in the application.
 */
public class LeaderboardViewModel extends AndroidViewModel implements LifecycleObserver {

  private final UserRepository userRepository;
  private final AttemptRepository attemptRepository;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<UUID> userId;
  private final MutableLiveData<Integer> difficulty;
  private final CompositeDisposable pending;
  private final MutableLiveData<List<Attempt>> attempts;
  private final MutableLiveData<Integer> selectedItem;

  /**
   * Sets the fields of the leaderboard to be used for the viewmodels to be displayed when called.
   *
   * @param application creates an Instance of application.
   */
  public LeaderboardViewModel(@NonNull Application application) {
    super(application);
    userRepository = new UserRepository(application);
    attemptRepository = new AttemptRepository(application);
    throwable = new MutableLiveData<>();
    userId = new MutableLiveData<>();
    pending = new CompositeDisposable();
    attempts = new MutableLiveData<>();
    difficulty = new MutableLiveData<>();
    selectedItem = new MutableLiveData<>();
  }

  /**
   * Gets the live data from the list of Attempts in the server side to use.
   *
   * @return
   */
  public LiveData<List<Attempt>> getAttempts() {
    return attempts;
  }

  /**
   * Gets throwables to use when the current instance of data is not needed.
   *
   * @return
   */
  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  /**
   * Gets any attempts and sorts them by difficulty of level associated with.
   *
   * @param difficulty An instance of Difficulty in an int.
   */
  public void getAttemptsByDifficulty(int difficulty) {
    throwable.postValue(null);
    pending.add(
        attemptRepository.getLeaderboard(difficulty)
            .subscribe(
                attempts::postValue,
                throwable::postValue
            )
    );
  }
}


