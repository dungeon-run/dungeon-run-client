package edu.cnm.deepdive.dungeonrunclient.controller;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import edu.cnm.deepdive.dungeonrunclient.R;
import edu.cnm.deepdive.dungeonrunclient.adapter.LeaderboardAdapter;
import edu.cnm.deepdive.dungeonrunclient.databinding.FragmentLeaderboardBinding;
import edu.cnm.deepdive.dungeonrunclient.model.Attempt;
import edu.cnm.deepdive.dungeonrunclient.service.AttemptRepository;
import edu.cnm.deepdive.dungeonrunclient.viewmodel.LeaderboardViewModel;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public class LeaderboardFragment extends Fragment {

  private FragmentLeaderboardBinding binding;
  private LeaderboardViewModel viewModel;
  private List<Attempt> attempts;
  private LeaderboardAdapter adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
//    int difficulty = ((Attempt) binding.difficultySpinner.getSelectedItem()).getDifficulty();
//    binding.difficultySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//      @Override
//      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Attempt attempt = (Attempt) parent.getItemAtPosition(position);
//        viewModel.setAttempts(attempt.getDifficulty());
//      }
//      @Override
//      public void onNothingSelected(AdapterView<?> parent) {
//      }
//    });

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(LeaderboardViewModel.class);
    viewModel.getAttemptsByDifficulty(1);
    viewModel.getAttempts().observe(getViewLifecycleOwner(), (attempts) -> {
      if (attempts != null){
       binding.countsList.setAdapter(new LeaderboardAdapter(getContext(), attempts));
      }
    });
    viewModel.getThrowable().observe(getViewLifecycleOwner(), (throwable) -> {
      if (throwable != null) {
        Snackbar.make(binding.getRoot(), throwable.getMessage(), BaseTransientBottomBar.LENGTH_INDEFINITE).show();
      }
    });
  }
}