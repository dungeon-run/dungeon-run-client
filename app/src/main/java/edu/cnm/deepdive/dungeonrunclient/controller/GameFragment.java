package edu.cnm.deepdive.dungeonrunclient.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.dungeonrunclient.R;
import edu.cnm.deepdive.dungeonrunclient.databinding.FragmentGameBinding;
import edu.cnm.deepdive.dungeonrunclient.model.Direction;
import edu.cnm.deepdive.dungeonrunclient.viewmodel.GameViewModel;

/**
 * Encapsulates the actual gameplay and displays for the UI.
 */
public class GameFragment extends Fragment {

  private FragmentGameBinding binding;
  private GameViewModel viewModel;

//  public static GameFragment newInstance(String param1, String param2) {
//    GameFragment fragment = new GameFragment();
//    Bundle args = new Bundle();
//    fragment.setArguments(args);
//    return fragment;
//  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentGameBinding.inflate(inflater, container, false);
    binding.moveDown.setOnClickListener((v) -> viewModel.move(Direction.SOUTH));
    binding.moveUp.setOnClickListener((v) -> viewModel.move(Direction.NORTH));
    binding.moveLeft.setOnClickListener((v) -> viewModel.move(Direction.WEST));
    binding.moveRight.setOnClickListener((v) -> viewModel.move(Direction.EAST));
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(this).get(GameViewModel.class);
    viewModel.getMaze().observe(getViewLifecycleOwner(), (maze) -> {
      binding.maze.setMaze(maze);
      binding.maze.invalidate();
    });
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.game_options, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.new_game:
        viewModel.startMaze();
        break;
      case R.id.pause_game:
        //TODO Hide the display and stop the clock.
        break;
      case R.id.resume_game:
        //TODO unhide the display and resume the clock.
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }
}