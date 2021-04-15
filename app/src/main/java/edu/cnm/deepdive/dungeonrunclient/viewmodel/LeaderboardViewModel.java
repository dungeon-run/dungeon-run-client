package edu.cnm.deepdive.dungeonrunclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import edu.cnm.deepdive.dungeonrunclient.model.Level;
import edu.cnm.deepdive.dungeonrunclient.service.UserRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class LeaderboardViewModel extends AndroidViewModel implements LifecycleObserver {

  private final UserRepository userRepository;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<UUID> userId;
//  private final LiveData<List<Level>> levels;
//  private final CompositeDisposable pending;


  public LeaderboardViewModel(@NonNull Application application) {
    super(application);
    userRepository = new UserRepository(application);
    throwable = new MutableLiveData<>();
    userId = new MutableLiveData<>();
  }
}


