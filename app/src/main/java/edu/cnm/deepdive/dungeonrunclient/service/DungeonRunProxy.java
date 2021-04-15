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

public interface DungeonRunProxy {
  
  @GET("users/me")
  Single<User> getProfile(@Header("Authorization") String bearerToken);

  @POST("attempts")
  Single<Attempt> startAttempt(@Header("Authorization") String bearerToken, @Body Attempt attempt);

  @PUT("attempts/{id}/completed")
  Single<Boolean> markComplete(@Header("Authorization") String bearerToken,
      @Path("id") UUID id, @Body boolean complete);

  @GET("attempts")
  Single<List<Attempt>> getLeaderboard(@Header("Authorization") String bearerToken,
      @Query("difficulty") int difficulty);

  static DungeonRunProxy getInstance() {
    return InstanceHolder.INSTANCE;
  }

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
