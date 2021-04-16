package edu.cnm.deepdive.dungeonrunclient.controller;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.cnm.deepdive.dungeonrunclient.R;
import edu.cnm.deepdive.dungeonrunclient.databinding.FragmentGameBinding;
import edu.cnm.deepdive.dungeonrunclient.model.Maze;
import edu.cnm.deepdive.dungeonrunclient.service.GameRepository;
import org.jetbrains.annotations.NotNull;

public class GameFragment extends Fragment {

private FragmentGameBinding binding;

  public static GameFragment newInstance(String param1, String param2) {
    GameFragment fragment = new GameFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentGameBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //TODO Attach to ViewModel
    Maze maze = new GameRepository(getContext()).createMaze(3); //FIXME This should go in the ViewModel
    binding.maze.setMaze(maze);
    binding.maze.invalidate();
  }
}