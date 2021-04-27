package edu.cnm.deepdive.dungeonrunclient.service;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import edu.cnm.deepdive.dungeonrunclient.BuildConfig;
import io.reactivex.Single;

/**
 * Encapsulates the Google sign in services needed to allow a user to sign in with their
 * google account.
 */
public class GoogleSignInService {

  private static final String BEARER_TOKEN_FORMAT = "Bearer %s";
  private static Application context;
  private final GoogleSignInClient client;
  private GoogleSignInAccount account;

  private GoogleSignInService() {
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()
        .requestId()
        .requestProfile()
        .requestIdToken(BuildConfig.CLIENT_ID)
        .build();
    client = GoogleSignIn.getClient(context, options);
  }

  /**
   * Sets the context for this Google instance.
   * @param context Context needed for the application parameter.
   */
  public static void setContext(Application context) {
    GoogleSignInService.context = context;
  }

  /**
   * Gets the instance of Google Sign in for users to be able to be attached to their individual id.
   * @return
   */
  public static GoogleSignInService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * Gets the account info to be used when the user is logged in to their Google account.
   * @return
   */
  public GoogleSignInAccount getAccount() {
    return account;
  }

  /**
   * A single Google sign in for user to log in.
   * @return Returns the logged in status with Google account, otherwise if log in is not successful
   * user will not be logged in to their account.
   */
  public Single<GoogleSignInAccount> refresh() {
    return Single.create((emitter) ->
        client.silentSignIn()
            .addOnSuccessListener(this::setAccount)
            .addOnSuccessListener(emitter::onSuccess)
            .addOnFailureListener(emitter::onError)
    );
  }

  /**
   * Refreshes the bearerToken to ensure the user is able to remain connected to the server when needed.
   * @return Returns the refreshed and updated bearerToken.
   */
  public Single<String> refreshBearerToken() {
    return refresh()
        .map((account) -> String.format(BEARER_TOKEN_FORMAT, account.getIdToken()));
  }

  /**
   * For allowing the user to sign in to their Google account.
   * @param activity Google sign in account activity for use to log in.
   * @param requestCode Gets a request code from Google to allow the user to prove they are
   *                    the correct user logging in.
   */
  public void startSignIn(Activity activity, int requestCode) {
    account = null;
    Intent intent = client.getSignInIntent();
    activity.startActivityForResult(intent, requestCode);
  }

  /**
   * Completes the Google sign in and allows user to proceed in to the app when log in is successful.
   * @param data data is an instance of the Intent to ensure the user to be logged in correctly.
   * @return Returns the task for logging in upon completion of logging in.
   */
  public Task<GoogleSignInAccount> completeSignIn(Intent data) {
    Task<GoogleSignInAccount> task = null;

    try {
      task = GoogleSignIn.getSignedInAccountFromIntent(data);
      setAccount(task.getResult(ApiException.class));
    } catch (ApiException e) {
// exception will be passed automatically to onFailureListener
    }
    return task;
  }

  /**
   * Allows user to signOut of their Google sign in if they choose to.
   * @return User is signed out when successfully completed.
   */
  public Task<Void> signOut() {
    return client.signOut()
        .addOnCompleteListener((ignore) -> setAccount(null));
  }

  /**
   * Sets the account information with the bearerToken to ensure a valid user connection.
   * @param account account is an an instance of the GoogleSign in to know which account
   *                user is using.
   */
  public void setAccount(GoogleSignInAccount account) {
    this.account = account;
//    if (account != null) {
//      Log.d(getClass().getSimpleName() + " Bearer Token", account.getIdToken());
//    }
  }

  private static class InstanceHolder {

    private static final GoogleSignInService INSTANCE = new GoogleSignInService();
  }
}
