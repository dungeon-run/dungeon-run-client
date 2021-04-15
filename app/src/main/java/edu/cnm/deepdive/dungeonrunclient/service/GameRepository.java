package edu.cnm.deepdive.dungeonrunclient.service;

import android.content.Context;
import edu.cnm.deepdive.dungeonrunclient.model.Maze;
import java.util.Random;

public class GameRepository {

  private static final int DIFFICULTY_INCREMENT = 4;

  private final Context context;

  public GameRepository(Context context) {
    this.context = context;
  }

  public Maze createMaze(int difficulty) {
    return new Maze(difficulty * DIFFICULTY_INCREMENT, new Random());
  }
}
