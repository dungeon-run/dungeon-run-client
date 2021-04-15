package edu.cnm.deepdive.dungeonrunclient.model;

import java.util.EnumSet;
import java.util.List;

public class Cell {

  private final EnumSet<Direction> walls = EnumSet.noneOf(Direction.class);

  private final int row;
  private final int column;
  private final int mazeSize;
  private final Maze maze;

  private boolean visited;

  public Cell(int row, int column, Maze maze) {
    this(EnumSet.allOf(Direction.class), row, column, maze);
  }

  public Cell(EnumSet<Direction> walls, int row, int column, Maze maze) {
    this.row = row;
    this.column = column;
    this.mazeSize = maze.getSize();
    this.maze = maze;
    this.walls.addAll(walls);
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

  public List<Cell> neighbors(boolean unvisitedOnly) {
   return null;
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

}
