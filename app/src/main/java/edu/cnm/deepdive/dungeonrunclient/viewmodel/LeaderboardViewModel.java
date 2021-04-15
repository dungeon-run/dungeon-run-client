package edu.cnm.deepdive.dungeonrunclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.dungeonrunclient.service.UserRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.UUID;

public class LeaderboardViewModel extends AndroidViewModel implements LifecycleObserver {

  private final UserRepository userRepository;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<UUID> userId;
  private final CompositeDisposable pending;


  public LeaderboardViewModel(@NonNull Application application) {
    super(application);
    userRepository = new UserRepository(application);
    throwable = new MutableLiveData<>();
    userId = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }
}


