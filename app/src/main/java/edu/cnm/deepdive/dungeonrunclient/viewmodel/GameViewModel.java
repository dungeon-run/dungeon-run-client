package edu.cnm.deepdive.dungeonrunclient.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.dungeonrunclient.R;
import edu.cnm.deepdive.dungeonrunclient.model.Attempt;
import edu.cnm.deepdive.dungeonrunclient.model.Direction;
import edu.cnm.deepdive.dungeonrunclient.model.Maze;
import edu.cnm.deepdive.dungeonrunclient.service.AttemptRepository;
import edu.cnm.deepdive.dungeonrunclient.service.GameRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.Date;

/**
 * Allows the viewModel for the game to be set up on the game screen.
 */
public class GameViewModel extends AndroidViewModel implements LifecycleObserver {

  private final GameRepository gameRepository;
  private final AttemptRepository attemptRepository;
  private final MutableLiveData<Maze> maze;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final Resources resources;
  private final SharedPreferences preferences;

  private Attempt attempt;

  public GameViewModel(@NonNull Application application) {
    super(application);
    gameRepository = new GameRepository(application);
    attemptRepository = new AttemptRepository(application);
    maze = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    resources = application.getResources();
    preferences = PreferenceManager.getDefaultSharedPreferences(application);
//    startMaze();
  }

  public void startMaze() {
    int difficulty = preferences.getInt(
        resources.getString(R.string.difficulty_key),
        resources.getInteger(R.integer.default_difficulty));
    Maze maze = gameRepository.createMaze(difficulty);
    attempt = new Attempt();
    attempt.setStartTime(new Date());
    attempt.setDifficulty(difficulty);
    pending.add(
        attemptRepository
        .startAttempt(attempt)
        .subscribe(
            (receivedAttempt) -> {
              attempt = receivedAttempt;
              this.maze.postValue(maze);
            },
            this::logThrowable
        )
    );
  }

  private void logThrowable(Throwable throwable) {
    Log.e(getClass().getName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

  public void move(Direction direction) {
    Maze maze = this.maze.getValue();
    if (!maze.isSolved()) {
      if (maze.move(direction)) {
        this.maze.setValue(maze);
        if (maze.isSolved()) {
          //TODO send completed attempt to the server. Similar to lines 55-62
        }
      }
    }
  }

  public LiveData<Maze> getMaze() {
    return maze;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }
}
