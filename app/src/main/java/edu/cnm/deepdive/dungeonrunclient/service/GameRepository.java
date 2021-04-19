package edu.cnm.deepdive.dungeonrunclient.service;

import android.content.Context;
import edu.cnm.deepdive.dungeonrunclient.model.Maze;
import java.util.Random;

/**
 * The GameRepository encapsulates the needed methods to be gathered from the classes for Maze creation
 * and allows the methods to be called upon when needed.
 */
public class GameRepository {

  private static final int DIFFICULTY_INCREMENT = 4;

  private final Context context;

  /**
   * Sets the context of the game repository for use in the view models.
   * @param context Context is the context of the GameRepository.
   */
  public GameRepository(Context context) {
    this.context = context;
  }

  /**
   * When the maze is generated, the size is based off of the difficulty increment to determine
   * how large the grid shall be for the maze.
   * @param difficulty Difficulty parameter is used to assist with determining the size of the maze.
   * @return returns a new Maze instance for use.
   */
  public Maze createMaze(int difficulty) {
    return new Maze(difficulty * DIFFICULTY_INCREMENT, new Random());
  }
}
