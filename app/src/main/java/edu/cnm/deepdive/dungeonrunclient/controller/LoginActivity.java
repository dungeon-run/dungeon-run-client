package edu.cnm.deepdive.dungeonrunclient.controller;

import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.dungeonrunclient.databinding.ActivityLoginBinding;
import edu.cnm.deepdive.dungeonrunclient.service.GoogleSignInService;

/**
 * Activity for logging in to Google sign in services.
 * Will allow the user to sign in to their google account for tracking attempts completed.
 */
public class LoginActivity extends AppCompatActivity {

    private static final int LOGIN_REQUEST_CODE = 1000;
    private GoogleSignInService service;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = GoogleSignInService.getInstance();
        service.refresh()
            .subscribe(
                this::switchToMain,
                (throwable) -> {
                    binding = ActivityLoginBinding.inflate(getLayoutInflater());
                    binding.signIn.setOnClickListener((v)
                        -> service.startSignIn(this, LOGIN_REQUEST_CODE));
                    setContentView(binding.getRoot());
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == LOGIN_REQUEST_CODE) {
        service.completeSignIn(data)
            .addOnSuccessListener(this::switchToMain)
            .addOnFailureListener((throwable) ->
                Toast.makeText(this, "Unable to sign in with the provided credentials",
                    Toast.LENGTH_LONG).show());
      } else {
        super.onActivityResult(requestCode,resultCode,data);
      }
    }

    private void switchToMain(GoogleSignInAccount account) {
      Intent intent = new Intent(this, MainActivity.class)
          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    }
}