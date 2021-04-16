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

public class Cell {

  private final EnumSet<Direction> walls = EnumSet.noneOf(Direction.class);

  private final int row;
  private final int column;
  private final int mazeSize;
  private final Maze maze;
  private final Random rng;
  private final int hash;

  private boolean visited;

  public Cell(int row, int column, Maze maze, Random rng) {
    this(EnumSet.allOf(Direction.class), row, column, maze, rng);
  }

  public Cell(EnumSet<Direction> walls, int row, int column, Maze maze, Random rng) {
    this.row = row;
    this.column = column;
    this.mazeSize = maze.getSize();
    this.maze = maze;
    this.rng = rng;
    this.walls.addAll(walls);
    hash = Objects.hash(row, column);
  }

  public EnumSet<Direction> getWalls() {
    return walls;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public boolean isVisited() {
    return visited;
  }

  public void setVisited(boolean visited) {
    this.visited = visited;
  }

//contains the maze
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

  public List<Cell> getConnectedNeighbors() {
    return Stream
        .of(Direction.values())
        .filter((dir) -> !walls.contains(dir))
        .map((dir) -> maze.getCells()[row + dir.getRowOffset()][column + dir.getColumnOffset()])
        .collect(Collectors.toList());
  }

  //sets the random paths in the maze
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

  public enum Direction {
    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1);

    private final int rowOffset;
    private final int columnOffset;

    Direction(int rowOffset, int columnOffset) {
      this.rowOffset = rowOffset;
      this.columnOffset = columnOffset;
    }

    public int getRowOffset() {
      return rowOffset;
    }

    public int getColumnOffset() {
      return columnOffset;
    }

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
