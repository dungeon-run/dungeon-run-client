package edu.cnm.deepdive.dungeonrunclient.model;

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
   *
   * @param rowOffset    Rows are the cells that run vertically along the maze.
   * @param columnOffset Columns are the cells that run horizontally.
   */
  Direction(int rowOffset, int columnOffset) {
    this.rowOffset = rowOffset;
    this.columnOffset = columnOffset;
  }

  /**
   * Gets the rowOffsets when needed for maze generation.
   *
   * @return
   */
  public int getRowOffset() {
    return rowOffset;
  }

  /**
   * Gets the columnOffses when needed in other classes for maze generation.
   *
   * @return
   */
  public int getColumnOffset() {
    return columnOffset;
  }

  /**
   * Sets up the directions to know which is the opposite of each other.
   *
   * @return Returns the values of the opposite directions.
   */
  public Direction opposite() {
    Direction[] values = Direction.values();
    return values[(ordinal() + 2) % values.length];
  }
}
