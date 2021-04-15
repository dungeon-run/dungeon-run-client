package edu.cnm.deepdive.dungeonrunclient.model;

import java.util.Random;

public class Maze {

  private final Cell[][] cells;
  private final Random rng;

  public Maze(int size, Random rng) {
    cells = new Cell[size][size];
    this.rng = rng;
    for (int rowIndex = 0; rowIndex < size; rowIndex++) {
      for (int columnIndex = 0; columnIndex < size; columnIndex++) {
        cells[rowIndex][columnIndex] = new Cell(rowIndex, columnIndex, this, this.rng);
      }
    }
    cells[0][0].addToMaze();
  }

  public Cell[][] getCells() {
    return cells;
  }

  public int getSize() {
    return cells.length;
  }
}
