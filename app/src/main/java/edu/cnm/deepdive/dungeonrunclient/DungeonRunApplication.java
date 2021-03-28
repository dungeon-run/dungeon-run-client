package edu.cnm.deepdive.dungeonrunclient;

import android.app.Application;
import edu.cnm.deepdive.dungeonrunclient.service.GoogleSignInService;

public class DungeonRunApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setContext(this);
  }

}
