package edu.cnm.deepdive.dungeonrunclient.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.dungeonrunclient.BuildConfig;
import edu.cnm.deepdive.dungeonrunclient.model.Attempt;
import edu.cnm.deepdive.dungeonrunclient.model.User;
import io.reactivex.Single;
import java.util.List;
import java.util.UUID;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This interface proxy will connect with the server and receive the incoming information for use
 * on the client side.
 */
public interface DungeonRunProxy {

  /**
   * Gets the instance of the user information coming in from the server side for the user.
   * @param bearerToken BearerToken allows the user to stay connected with the service side.
   * @return
   */
  @GET("users/me")
  Single<User> getProfile(@Header("Authorization") String bearerToken);

  /**
   * Will post the corresponding information along with attempts for use on the client side database.
   * @param bearerToken Needs to have the bearerToken to keep a current connection with the server.
   * @param attempt Attempts is an instance from the Attempts.class to gather all needed columns.
   * @return
   */
  @POST("attempts")
  Single<Attempt> startAttempt(@Header("Authorization") String bearerToken, @Body Attempt attempt);

  /**
   * Saves the information for the database when the attempt is compleated.
   * @param bearerToken Needs to have the bearerToken to keep a current connection with the server.
   * @param id Parameter Id is used to ensure the information associated with the user is shown.
   * @param complete Sets boolean true when the attempt is successfully completed.
   * @return Returns the information for use in the database upon completion.
   */
  @PUT("attempts/{id}/completed")
  Single<Boolean> markComplete(@Header("Authorization") String bearerToken,
      @Path("id") UUID id, @Body boolean complete);

  /**
   * Gets the list of attempts from the database when called upon.
   * @param bearerToken Needs to have the bearerToken to keep a current connection with the server.
   * @param difficulty Attempts gathered from the database are tied to the level of difficulty the
   *                   attempt was tried or completed on.
   * @return Returns the information for the leaderboard.
   */
  @GET("attempts")
  Single<List<Attempt>> getLeaderboard(@Header("Authorization") String bearerToken,
      @Query("difficulty") int difficulty);

  /**
   * Gets an instance of the proxy when called upon.
   * @return Returns an instance of the proxy.
   */
  static DungeonRunProxy getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * Subclass in the proxy to hold instances of the proxy class.
   */
  class InstanceHolder {

    private static final DungeonRunProxy INSTANCE;

    static {
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .create();
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(BuildConfig.DEBUG ? Level.BODY : Level.NONE);
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .client(client)
          .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(DungeonRunProxy.class);
    }

  }
}
