package edu.cnm.deepdive.dungeonrunclient.model;

import java.util.EnumSet;

public class Cell {

  private EnumSet<Direction> walls;

  // TODO Define methods that knock down walls
  // TODO Define fields for row and column of cell
  // TODO Define a boolean flag indicating if a cell has been visited previously.

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
