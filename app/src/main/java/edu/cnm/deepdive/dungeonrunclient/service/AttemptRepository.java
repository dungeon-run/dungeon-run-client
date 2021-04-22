package edu.cnm.deepdive.dungeonrunclient.service;

import android.content.Context;
import edu.cnm.deepdive.dungeonrunclient.model.Attempt;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * Encapsulates the information for the viewmodels to display information needed for use in the
 * applications.
 */
public class AttemptRepository {

  private final Context context;
  private final DungeonRunProxy webService;
  private final GoogleSignInService signInService;

  /**
   * Sets the fields for the repository for use in the class.
   * @param context Sets the context for this repository and fields to be used.
   */
  public AttemptRepository(Context context) {
    this.context = context;
    webService = DungeonRunProxy.getInstance();
    signInService = GoogleSignInService.getInstance();
  }

  /**
   * Gets the attempt and creates a new attempt to start.
   * @param attempt Creates an instance of the class Attempt.
   * @return Returns the signInService and keeps the bearerToken refreshed for access to the server.
   */
  public Single<Attempt> startAttempt(Attempt attempt) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.startAttempt(token, attempt));
  }

  /**
   * Updates the Attempts when the attempt is completed and boolean becomes true.
   * @param attempt Creates an instance of the class Attempt.
   * @return Returns the signInService and keeps the bearerToken refreshed for access to the server.
   */
  public Single<Attempt> updateAttempt(Attempt attempt) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.updateAttempt(token, attempt.getId(), attempt));
  }

  /**
   * A single list of attempts to display for the leaderboard viewmodel.
   * @param difficulty The list of attempts will display based on which difficulty level is chosen.
   * @return Returns the signInService and keeps the bearerToken refreshed for access to the server.
   */
  public Single<List<Attempt>> getLeaderboard(int difficulty) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.getLeaderboard(token, difficulty));
  }

}
