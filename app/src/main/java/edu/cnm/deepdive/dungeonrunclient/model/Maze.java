package edu.cnm.deepdive.dungeonrunclient.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Maze.java is for the actual maze generation after the paths have been carved.
 */
public class Maze {

  private final Cell[][] cells;
  private final Random rng;
  private final Cell start;
  private final Cell finish;
  private final Map<Cell, Set<Direction>> arrivals;
  private final Map<Cell, Set<Direction>> departures;
  private Cell current;

  /**
   * For setting up the maze based on size and using the rng to determine the paths.
   * @param size Parameter size is an int based on the difficulty set for the attempts.
   * @param rng Rng carves the random paths through the maze.
   */
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
    current = start;
    arrivals = new HashMap<>();
    departures = new HashMap<>();
  }

  /**
   * Gets all of the cells when called upon in the other classes.
   * @return
   */
  public Cell[][] getCells() {
    return cells;
  }

  /**
   * Gets the size of the maze as set by the user to allow the maze generation to know how
   * to generate the maze.
   * @return
   */
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


  /**
   * Gets the start of the maze which is found by taking the longest paths and comparing it to the
   * end of the longest path.
   * @return
   */
  public Cell getStart() {
    return start;
  }

  /**
   * Finds the end of the path the longest path from the start and determines it as the finish
   * of the path.
   * @return
   */
  public Cell getFinish() {
    return finish;
  }

  public Cell getCurrent() {
    return current;
  }

  public void setCurrent(Cell current) {
    this.current = current;
  }

  public Map<Cell, Set<Direction>> getArrivals() {
    return arrivals;
  }

  public Map<Cell, Set<Direction>> getDepartures() {
    return departures;
  }

  public boolean isSolved() {
    return current.equals(finish);
  }

  public boolean move(Direction direction) {
    return !isSolved() && current
        .getConnectedNeighbor(direction)
        .map((neighbor) -> {
          Set<Direction> departures = this.departures.getOrDefault(current, new HashSet<>());
          departures.add(direction);
          this.departures.putIfAbsent(current, departures);
          Set<Direction> arrivals = this.arrivals.getOrDefault(neighbor, new HashSet<>());
          arrivals.add(direction.opposite());
          this.arrivals.putIfAbsent(neighbor, arrivals);
          current = neighbor;
          return true;
        })
        .orElse(false);
  }
}
