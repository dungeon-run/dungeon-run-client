package edu.cnm.deepdive.dungeonrunclient.service;

import android.content.Context;
import edu.cnm.deepdive.dungeonrunclient.model.Attempt;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class AttemptRepository {

  private final Context context;
  private final DungeonRunProxy webService;
  private final GoogleSignInService signInService;

  public AttemptRepository(Context context) {
    this.context = context;
    webService = DungeonRunProxy.getInstance();
    signInService = GoogleSignInService.getInstance();
  }

  public Single<Attempt> startAttempt(Attempt attempt) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.startAttempt(token, attempt));
  }

  public Single<Boolean> updateAttempt(Attempt attempt) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.markComplete(token, attempt.getId(), attempt.isCompleted()));
  }

  public Single<List<Attempt>> getLeaderboard(int difficulty) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.getLeaderboard(token, difficulty));
  }
}
