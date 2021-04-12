package edu.cnm.deepdive.dungeonrunclient.controller;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import edu.cnm.deepdive.dungeonrunclient.R;
import edu.cnm.deepdive.dungeonrunclient.service.GoogleSignInService;
import edu.cnm.deepdive.dungeonrunclient.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

  private MainViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setUpViewModel();
  }
//  setContentView(R.layout.activity_main);
//  BottomNavigationView navView = findViewById(R.id.nav_view);
//  appBarConfiguration = new AppBarConfiguration.Builder(
//  R.id.play_navigation, R.id.settings_navigation, R.id.leaderboard_navigation)
//      .build();

  private void setUpViewModel() {
    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    getLifecycle().addObserver(viewModel);
    viewModel.getThrowable().observe(this,(throwable) -> {
      if(throwable != null) {
        Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.main_options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.sign_out:
        logout();
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
  }

  private void logout() {
    GoogleSignInService.getInstance().signOut()
        .addOnCompleteListener((ignored) -> {
          Intent intent = new Intent(this, LoginActivity.class)
              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        });
  }
}