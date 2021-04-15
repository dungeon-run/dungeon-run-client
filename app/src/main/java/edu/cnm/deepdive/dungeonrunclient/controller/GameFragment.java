package edu.cnm.deepdive.dungeonrunclient.controller;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.cnm.deepdive.dungeonrunclient.R;
import edu.cnm.deepdive.dungeonrunclient.model.Maze;
import edu.cnm.deepdive.dungeonrunclient.service.GameRepository;
import org.jetbrains.annotations.NotNull;

public class GameFragment extends Fragment {



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
    return inflater.inflate(R.layout.fragment_game, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //TODO Attach to ViewModel
    Maze maze = new GameRepository(getContext()).createMaze(1); //FIXME This should go in the ViewModel
  }
}