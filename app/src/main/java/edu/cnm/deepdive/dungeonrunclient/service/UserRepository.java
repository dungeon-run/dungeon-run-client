package edu.cnm.deepdive.dungeonrunclient.service;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.dungeonrunclient.model.User;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Encapsulates the User entity for use within the client application.
 */
public class UserRepository {

  private final Context context;
  private final DungeonRunProxy webService;
  private final GoogleSignInService signInService;

  /**
   * Sets the context for use of the UserRepository.
   * @param context Sets an instance of the class context.
   */
  public UserRepository(Context context) {
    this.context = context;
    signInService = GoogleSignInService.getInstance();
    webService = DungeonRunProxy.getInstance();
  }

  /**
   * Gets the account information to connect with the user Id.
   * @return
   */
  public GoogleSignInAccount getAccount() {
    return signInService.getAccount();
  }

  /**
   * Gets a single instance for use with the UserProfile associated with the userProfile.
   * @return
   */
  public Single<User> getUserProfile() {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.getProfile(token)
            .subscribeOn(Schedulers.io()));
  }

}
