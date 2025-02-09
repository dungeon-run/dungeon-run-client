package edu.cnm.deepdive.dungeonrunclient.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import edu.cnm.deepdive.dungeonrunclient.R;
import edu.cnm.deepdive.dungeonrunclient.model.Cell;
import edu.cnm.deepdive.dungeonrunclient.model.Direction;
import edu.cnm.deepdive.dungeonrunclient.model.Maze;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * MazeView creates the maze to display in the UI.
 */
public class MazeView extends View {

  private Maze maze;
  private Paint paint;
  private Context context;

  @ColorInt
  private int wallColor;
  @ColorInt
  private int pathColor;
  @ColorInt
  private int currentColor;
  @ColorInt
  private int endColor;
  private float wallStrokeWidth = 5;

  {
    setWillNotDraw(false);
    paint = new Paint();
  }

  /**
   * Sets the context for this MazeView.java.
   *
   * @param context Creates an instance of the MazeView class for context.
   */
  public MazeView(Context context) {
    super(context);
  }

  /**
   * For setting up the context and getting the attributes for use in the display generation.
   *
   * @param context Instance of context for use in the method.
   * @param attrs   Instance of AttributeSet for use in the method.
   */
  public MazeView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setUpColors(context, attrs);
  }

  /**
   * For setting up the display of the maze generation.
   *
   * @param context      Instance of context for use in the method.
   * @param attrs        Instance of AttributeSet for use in the method.
   * @param defStyleAttr Instance of the style for use in the maze display.
   */
  public MazeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setUpColors(context, attrs);
  }

  /**
   * To set up the mazeView when called up.
   *
   * @param context      Instance of context for use in the method.
   * @param attrs        Instance of AttributeSet for use in the method.
   * @param defStyleAttr Instance of the style for use in the maze display.
   * @param defStyleRes  Instance of the defStyleRes to create the look of the maze.
   */
  public MazeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    setUpColors(context, attrs);
  }

  private void setUpColors(Context context, AttributeSet attrs) {
    TypedArray arr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MazeView, 0, 0);
    try {
      wallColor = arr.getColor(R.styleable.MazeView_wallColor, Color.BLACK);
      pathColor = arr.getColor(R.styleable.MazeView_pathColor, Color.argb(255, 208, 208, 208));
      endColor = arr.getColor(R.styleable.MazeView_endColor, Color.argb(255, 0, 128, 0));
      currentColor = arr.getColor(R.styleable.MazeView_currentColor, Color.BLUE);
    } finally {
      arr.recycle();
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (maze != null) {
      drawWalls(canvas);
      drawPath(canvas);
      drawMarkers(canvas);
    }
  }

  private void drawWalls(Canvas canvas) {
    int leftPadding = getPaddingLeft();
    int rightPadding = getPaddingRight();
    int topPadding = getPaddingTop();
    int bottomPadding = getPaddingBottom();
    int height = getHeight() - topPadding - bottomPadding;
    int width = getWidth() - leftPadding - rightPadding;
    paint.setColor(wallColor);
    paint.setStyle(Style.STROKE);
    paint.setStrokeWidth(wallStrokeWidth);
    canvas.drawRect(leftPadding, topPadding, leftPadding + width, topPadding + height, paint);
    Cell[][] cells = maze.getCells();
    float cellWidth = (float) width / cells[0].length;
    float cellHeight = (float) height / cells.length;
    for (int rowIndex = 0; rowIndex < cells.length; rowIndex++) {
      for (int columnIndex = 0; columnIndex < cells[rowIndex].length; columnIndex++) {
        Cell cell = cells[rowIndex][columnIndex];
        float top = rowIndex * cellHeight;
        float bottom = (rowIndex + 1) * cellHeight;
        float left = columnIndex * cellWidth;
        float right = (columnIndex + 1) * cellWidth;
        EnumSet<Direction> walls = cell.getWalls();
        for (Direction d : walls) {
          float startX = 0;
          float startY = 0;
          float endX = 0;
          float endY = 0;
          switch (d) {
            case NORTH:
              startX = left + leftPadding;
              startY = endY = top + topPadding;
              endX = right + leftPadding;
              break;
            case EAST:
              startX = endX = right + leftPadding;
              startY = top + topPadding;
              endY = bottom + topPadding;
              break;
            case SOUTH:
              startY = endY = bottom + topPadding;
              startX = left + leftPadding;
              endX = right + leftPadding;
              break;
            case WEST:
              startX = endX = left + leftPadding;
              startY = top + topPadding;
              endY = bottom + topPadding;
              break;
          }
          canvas.drawLine(startX, startY, endX, endY, paint);
        }
      }
    }
  }

  private void drawPath(Canvas canvas) {
    int leftPadding = getPaddingLeft();
    int rightPadding = getPaddingRight();
    int topPadding = getPaddingTop();
    int bottomPadding = getPaddingBottom();
    int height = getHeight() - topPadding - bottomPadding;
    int width = getWidth() - leftPadding - rightPadding;
    Cell[][] cells = maze.getCells();
    float cellWidth = (float) width / cells[0].length;
    float cellHeight = (float) height / cells.length;
    paint.setColor(pathColor);
    paint.setStyle(Style.STROKE);
    paint.setStrokeCap(Cap.ROUND);
    paint.setStrokeWidth(Math.min(cellWidth, cellHeight) * 0.25f);
    Map<Cell, Set<Direction>> links = maze.getArrivals();
    maze
        .getDepartures()
        .entrySet()
        .forEach((entry) -> {
          Cell cell = entry.getKey();
          Set<Direction> directions = links.getOrDefault(cell, new HashSet<>());
          directions.addAll(entry.getValue());
          float top = cell.getRow() * cellHeight + topPadding;
          float bottom = (cell.getRow() + 1) * cellHeight + topPadding;
          float left = cell.getColumn() * cellWidth + leftPadding;
          float right = (cell.getColumn() + 1) * cellWidth + leftPadding;
          float centerX = (left + right) / 2;
          float centerY = (top + bottom) / 2;
          for (Direction direction : directions) {
            float startX = centerX;
            float startY = centerY;
            switch (direction) {
              case NORTH:
                startY = top;
                break;
              case EAST:
                startX = right;
                break;
              case SOUTH:
                startY = bottom;
                break;
              case WEST:
                startX = left;
                break;
            }
            canvas.drawLine(startX, startY, centerX, centerY, paint);
          }
        });
  }

  private void drawMarkers(Canvas canvas) {
    int leftPadding = getPaddingLeft();
    int rightPadding = getPaddingRight();
    int topPadding = getPaddingTop();
    int bottomPadding = getPaddingBottom();
    int height = getHeight() - topPadding - bottomPadding;
    int width = getWidth() - leftPadding - rightPadding;
    Cell[][] cells = maze.getCells();
    float cellWidth = (float) width / cells[0].length;
    float cellHeight = (float) height / cells.length;
    paint.setStyle(Style.FILL);
    paint.setColor(endColor);
    canvas.drawOval(
        leftPadding + maze.getFinish().getColumn() * cellWidth + cellWidth * 0.25f,
        topPadding + maze.getFinish().getRow() * cellHeight + cellHeight * 0.25f,
        leftPadding + maze.getFinish().getColumn() * cellWidth + cellWidth * 0.75f,
        topPadding + maze.getFinish().getRow() * cellHeight + cellHeight * 0.75f,
        paint
    );
    paint.setColor(endColor);
    canvas.drawRect(
        leftPadding + maze.getStart().getColumn() * cellWidth + cellWidth * 0.15f,
        topPadding + maze.getStart().getRow() * cellHeight + cellHeight * 0.15f,
        leftPadding + maze.getStart().getColumn() * cellWidth + cellWidth * 0.85f,
        topPadding + maze.getStart().getRow() * cellHeight + cellHeight * 0.85f,
        paint
    );
    paint.setColor(currentColor);
    canvas.drawOval(
        leftPadding + maze.getCurrent().getColumn() * cellWidth + cellWidth * 0.25f,
        topPadding + maze.getCurrent().getRow() * cellHeight + cellHeight * 0.25f,
        leftPadding + maze.getCurrent().getColumn() * cellWidth + cellWidth * 0.75f,
        topPadding + maze.getCurrent().getRow() * cellHeight + cellHeight * 0.75f,
        paint
    );

  }

  /**
   * Sets the maze when called upon in other classes.
   *
   * @param maze
   */
  public void setMaze(Maze maze) {
    this.maze = maze;
  }

  /**
   * Gets the wall color to set for the UI when maze is displayed.
   *
   * @return
   */
  public int getWallColor() {
    return wallColor;
  }

  /**
   * Returns the wall color for use when called upon.
   *
   * @param wallColor
   */
  public void setWallColor(int wallColor) {
    this.wallColor = wallColor;
    invalidate();
  }

  /**
   * Gets the path color which is where the user has already been through the maze.
   *
   * @return
   */
  public int getPathColor() {
    return pathColor;
  }

  /**
   * Sets the path color for use when user is going through the maze.
   *
   * @param pathColor
   */
  public void setPathColor(int pathColor) {
    this.pathColor = pathColor;
    invalidate();
  }

  /**
   * Get current color is to display where the user currently is.
   *
   * @return
   */
  public int getCurrentColor() {
    return currentColor;
  }

  /**
   * Sets the color where the user currently is.
   *
   * @param currentColor
   */
  public void setCurrentColor(int currentColor) {
    this.currentColor = currentColor;
    invalidate();
  }

  /**
   * Get end color is to show where the end of the maze is.
   *
   * @return
   */
  public int getEndColor() {
    return endColor;
  }

  /**
   * Sets the color at the location of the end of the maze.
   *
   * @param endColor
   */
  public void setEndColor(int endColor) {
    this.endColor = endColor;
    invalidate();
  }

  /**
   * Gets the strode width of the maze walls.
   *
   * @return
   */
  public float getWallStrokeWidth() {
    return wallStrokeWidth;
  }

  /**
   * Sets the stroke with to display in the UI for the walls of the maze.
   *
   * @param wallStrokeWidth
   */
  public void setWallStrokeWidth(float wallStrokeWidth) {
    this.wallStrokeWidth = wallStrokeWidth;
  }

}
