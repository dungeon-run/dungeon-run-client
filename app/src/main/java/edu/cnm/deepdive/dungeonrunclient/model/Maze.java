package edu.cnm.deepdive.dungeonrunclient.model;

public class Maze {

  private final Cell[][] cells;

  public Maze(int size) {
    cells = new Cell[size][size];
    for (int rowIndex = 0; rowIndex < size; rowIndex++) {
      for (int columnIndex = 0; columnIndex < size; columnIndex++) {
        cells[rowIndex][columnIndex] = new Cell(rowIndex, columnIndex, this);
      }
    }
  }

  public int getSize() {
    return cells.length;
  }
}
