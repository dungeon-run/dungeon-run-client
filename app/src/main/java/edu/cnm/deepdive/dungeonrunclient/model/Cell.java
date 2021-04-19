package edu.cnm.deepdive.dungeonrunclient.model;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Sets up the columns for the cells for maze generation.
 */
public class Cell {

  private final EnumSet<Direction> walls = EnumSet.noneOf(Direction.class);

  private final int row;
  private final int column;
  private final int mazeSize;
  private final Maze maze;
  private final Random rng;
  private final int hash;

  private boolean visited;

  /**
   * Cell to be accessed by other classes to generate the mazes.
   * @param row Rows are the different columns the maze will use to create the paths.
   * @param column Columns are the columns to be used for the maze.
   * @param maze Maze is the complete generation of the attempt.
   * @param rng Sets up a random number generator to determine the different paths to create.
   */
  public Cell(int row, int column, Maze maze, Random rng) {
    this(EnumSet.allOf(Direction.class), row, column, maze, rng);
  }

  /**
   * Setting the fields to be used for the maze generation.
   * @param walls The parameter walls is the walls of the maze.
   * @param row Rows to be used to set up how many rows of cells for use in the maze.
   * @param column Columns to be used to set up how many rows of columns for use in the maze.
   * @param maze Maze encapsulates all of the fields to create the maze.
   * @param rng Random number generator to aid in the random generation of paths in the maze.
   */
  public Cell(EnumSet<Direction> walls, int row, int column, Maze maze, Random rng) {
    this.row = row;
    this.column = column;
    this.mazeSize = maze.getSize();
    this.maze = maze;
    this.rng = rng;
    this.walls.addAll(walls);
    hash = Objects.hash(row, column);
  }

  /**
   * Gets the walls of the maze for use with the EnumSet of directions.
   * @return Returns the walls when needed for maze generation.
   */
  public EnumSet<Direction> getWalls() {
    return walls;
  }

  /**
   * Gets all rows needed for generation of the maze.
   * @return Returns the rows needed.
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets all columns needed for generation of the maze.
   * @return Returns the columns needed.
   */
  public int getColumn() {
    return column;
  }

  /**
   * This aids the maze generator to know if it should continue
   * destroying walls in the direction it is headed, or return to destroy walls and create a path in
   * a different direction. This will also allow the user to know if they have previously visited a
   * column already.
   * @return isVisited returns true or false.
   */
  public boolean isVisited() {
    return visited;
  }

  /**
   * Sets boolean true if a cell has been visited already.
   * @param visited Visited effects the path created and also allows the user to know if they have
   *                gone in to a cell previously.
   */
  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  /**
   * The method to create the paths in the maze and be randomly generated.
   * @param unvisitedOnly Will only allow the path creation to go through cells that have
   *                      not been visited.
   * @return Returns the completed map after the paths have been generated.
   */
  public Map<Direction, Cell> getNeighbors(boolean unvisitedOnly) {
   return Stream
       .of(Direction.values())
       .filter((dir) ->
           row + dir.getRowOffset() >= 0
           && row + dir.getRowOffset() < mazeSize
           && column + dir.getColumnOffset() >= 0
           && column + dir.getColumnOffset() < mazeSize
       )
       .collect(Collectors.toMap((dir) -> dir,
           (dir) -> maze.getCells()[row + dir.rowOffset][column + dir.columnOffset]))
       .entrySet()
       .stream()
       .filter((entry) -> !unvisitedOnly || !entry.getValue().visited)
       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * Cells to be used for maze generation. Allows the methods to know which neighbors are
   * connected and to knock down corresponding walls when a wall is knockec down for the path.
   * @return Returns the directions along with the cells.
   */
  public List<Cell> getConnectedNeighbors() {
    return Stream
        .of(Direction.values())
        .filter((dir) -> !walls.contains(dir))
        .map((dir) -> maze.getCells()[row + dir.getRowOffset()][column + dir.getColumnOffset()])
        .collect(Collectors.toList());
  }

  //sets the random paths in the maze

  /**
   * The method to allow the maze to have the randomly generated paths in the maze.
   */
  public void addToMaze() {
    visited = true;
    for (Map<Direction, Cell> neighbors = getNeighbors(true);
        !neighbors.isEmpty();
        neighbors = getNeighbors(true)) {
      List<Map.Entry<Direction, Cell>> neighborEntries = new ArrayList<>(neighbors.entrySet());
      Map.Entry<Direction, Cell> selectedNeighbor
          = neighborEntries.get(rng.nextInt(neighborEntries.size()));
      Direction dir = selectedNeighbor.getKey();
      walls.remove(dir);
      Cell cell = selectedNeighbor.getValue();
      cell.walls.remove(dir.opposite());
      cell.addToMaze();
    }
  }

  /**
   * Directions are enums set to allow the maze generator to know which direction is which.
   */
  public enum Direction {
    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1);

    private final int rowOffset;
    private final int columnOffset;

    /**
     * Directions fields to allow other methods and classes to access them.
     * @param rowOffset Rows are the cells that run vertically along the maze.
     * @param columnOffset Columns are the cells that run horizontally.
     */
    Direction(int rowOffset, int columnOffset) {
      this.rowOffset = rowOffset;
      this.columnOffset = columnOffset;
    }

    /**
     * Gets the rowOffsets when needed for maze generation.
     * @return
     */
    public int getRowOffset() {
      return rowOffset;
    }

    /**
     * Gets the columnOffses when needed in other classes for maze generation.
     * @return
     */
    public int getColumnOffset() {
      return columnOffset;
    }

    /**
     * Sets up the directions to know which is the opposite of each other.
     * @return Returns the values of the opposite directions.
     */
    public Direction opposite() {
      Direction[] values = Direction.values();
      return values[(ordinal() + 2) % values.length];
    }
  }

  @Override
  public int hashCode() {
    return hash;
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    boolean eq;

    if (obj == this) {
      eq = true;
    } else if (obj instanceof Cell) {
      Cell other = (Cell) obj;
      eq = (row == other.row) && (column == other.column);
    } else {
      eq = false;
    }
    return eq;
  }
}
