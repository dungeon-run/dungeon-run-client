package edu.cnm.deepdive.dungeonrunclient.model;

import android.util.Log;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Maze {

  private final Cell[][] cells;
  private final Random rng;
  private final Cell start;
  private final Cell finish;

  public Maze(int size, Random rng) {
    cells = new Cell[size][size];
    this.rng = rng;
    for (int rowIndex = 0; rowIndex < size; rowIndex++) {
      for (int columnIndex = 0; columnIndex < size; columnIndex++) {
        cells[rowIndex][columnIndex] = new Cell(rowIndex, columnIndex, this, this.rng);
      }
    }
    cells[0][0].addToMaze();
    start = getFarthestCell(cells[0][0]);
    finish = getFarthestCell(start);
    Log.d(getClass().getName(), String.format("Start = %d, %d", start.getRow(), start.getColumn()));
    Log.d(getClass().getName(), String.format("Finish = %d, %d", finish.getRow(), finish.getColumn()));
  }

  public Cell[][] getCells() {
    return cells;
  }

  public int getSize() {
    return cells.length;
  }

  private Cell getFarthestCell(Cell cell) {
    Cell farthestCell = null;
    Set<Cell> boundary = new HashSet<>();
    Set<Cell> flooded = new HashSet<>();
    boundary.add(cell);
    while (!boundary.isEmpty()) {
      Set<Cell> nextBoundary = new HashSet<>();
      for (Cell boundaryCell : boundary) {
        List<Cell> neighbors = boundaryCell.getConnectedNeighbors()
            .stream()
            .filter((neighbor) -> !flooded.contains(neighbor) && !boundary.contains(neighbor))
            .collect(Collectors.toList());
        nextBoundary.addAll(neighbors);
      }
      if (nextBoundary.isEmpty()) {
        farthestCell = boundary
            .stream()
            .findAny()
            .orElseThrow(IllegalStateException::new);
        break;
      } else {
        flooded.addAll(boundary);
        boundary.clear();
        boundary.addAll(nextBoundary);
      }
    }
    return farthestCell;
  }

  public Cell getStart() {
    return start;
  }

  public Cell getFinish() {
    return finish;
  }
}
