package edu.cnm.deepdive.dungeonrunclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.dungeonrunclient.service.UserRepository;
import io.reactivex.disposables.CompositeDisposable;

/**
 * The viewmodel to set up the main view of the UI for the application when launched.
 */
public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

  private final UserRepository userRepository;
  private final MutableLiveData<GoogleSignInAccount> account;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  /**
   * Holds the instances of the fields to be called upon when needed.
   * @param application An instance of application created from Application.class.
   */
  public MainViewModel(
      @NonNull Application application) {
    super (application);
    userRepository = new UserRepository(application);
    account = new MutableLiveData<>(userRepository.getAccount());
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }

  /**
   * Gets the throwable from when needed.
   * @return
   */
  public MutableLiveData<Throwable> getThrowable() {
    return throwable;
  }

}
