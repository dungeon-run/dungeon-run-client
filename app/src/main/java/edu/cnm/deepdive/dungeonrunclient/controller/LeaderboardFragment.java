package edu.cnm.deepdive.dungeonrunclient.controller;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;


public class LeaderboardFragment extends Fragment implements OnItemSelectedListener {

  private FragmentLeaderboardBinding binding;
  private LeaderboardViewModel viewModel;
  private List<Attempt> attempts;
  private LeaderboardAdapter adapter;
  private int difficulty;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
    binding.difficultySpinner.setOnItemSelectedListener(this);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(LeaderboardViewModel.class);
//    viewModel.getSelectedItem().observe(getViewLifecycleOwner(), new Observer<Integer>() {
//      @Override
//      public void onChanged(Integer integer) {
//        difficulty = integer;
//      }
//    });
//    viewModel.getAttemptsByDifficulty(difficulty);
    Resources res = getResources();
    List<Integer> difficulties = IntStream
        .rangeClosed(res.getInteger(R.integer.min_difficulty), res.getInteger(R.integer.max_difficulty))
        .boxed()
        .collect(Collectors.toList());
      ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getContext(),
          android.R.layout.simple_spinner_item, difficulties);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      binding.difficultySpinner.setAdapter(adapter);
    viewModel.getAttempts().observe(getViewLifecycleOwner(), (attempts) -> {
      if (attempts != null) {
        binding.countsList.setAdapter(new LeaderboardAdapter(getContext(), attempts));
      }
    });
    viewModel.getThrowable().observe(getViewLifecycleOwner(), (throwable) -> {
      if (throwable != null) {
        Snackbar.make(binding.getRoot(), throwable.getMessage(),
            BaseTransientBottomBar.LENGTH_INDEFINITE).show();
      }
    });
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    int difficulty = (Integer) parent.getItemAtPosition(position);
    viewModel.getAttemptsByDifficulty(difficulty);
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {

  }
}