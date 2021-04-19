package edu.cnm.deepdive.dungeonrunclient.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import edu.cnm.deepdive.dungeonrunclient.model.Cell;
import edu.cnm.deepdive.dungeonrunclient.model.Cell.Direction;
import edu.cnm.deepdive.dungeonrunclient.model.Maze;
import java.util.EnumSet;

/**
 * MazeView creates the maze to display in the UI.
 */
public class MazeView extends View {

  private Maze maze;
  private Paint paint;

  {
    setWillNotDraw(false);
    paint = new Paint();
    paint.setColor(Color.BLACK); // FIXME Take it from a resource. Make it more pretty
//    paint.setStrokeWidth(2); //FIXME Make dependant on puzzle diffucly and view size
    paint.setStyle(Style.STROKE);
  }

  /**
   * Sets the context for this MazeView.java.
   * @param context Creates an instance of the MazeView class for context.
   */
  public MazeView(Context context) {
    super(context);
  }

  /**
   * For setting up the context and getting the attributes for use in the display generation.
   * @param context Instance of context for use in the method.
   * @param attrs Instance of AttributeSet for use in the method.
   */
  public MazeView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * For setting up the display of the maze generation.
   * @param context Instance of context for use in the method.
   * @param attrs Instance of AttributeSet for use in the method.
   * @param defStyleAttr Instance of the style for use in the maze display.
   */
  public MazeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  /**
   * To set up the mazeView when called up.
   * @param context Instance of context for use in the method.
   * @param attrs Instance of AttributeSet for use in the method.
   * @param defStyleAttr Instance of the style for use in the maze display.
   * @param defStyleRes Instance of the defStyleRes to create the look of the maze.
   */
  public MazeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    Cell[][] cells = maze.getCells();
    float cellWidth = (float) getWidth() / cells[0].length;
    float cellHeight = (float) getHeight() / cells.length;
    for (int rowIndex = 0; rowIndex < cells.length; rowIndex++) {
      for (int columnIndex = 0; columnIndex < cells[rowIndex].length; columnIndex++) {
        Cell cell = cells[rowIndex][columnIndex];
        EnumSet<Direction> walls = cell.getWalls();
        float top = rowIndex * cellHeight;
        float bottom = (rowIndex + 1) * cellHeight;
        float left = columnIndex * cellWidth;
        float right = (columnIndex + 1) * cellWidth;
        for (Direction d : walls) {
          float startX = 0;
          float startY = 0;
          float endX = 0;
          float endY = 0;
          switch (d) {
            case NORTH:
              startX = left;
              startY = endY = top;
              endX = right;
              break;
            case EAST:
              startX = endX = right;
              startY = top;
              endY = bottom;
              break;
            case SOUTH:
              startY = endY = bottom;
              startX = left;
              endX = right;
              break;
            case WEST:
              startX = endX = left;
              startY = top;
              endY = bottom;
              break;
          }
          canvas.drawLine(startX, startY, endX, endY, paint);
        }
      }
    }
  }

  /**
   * Sets the maze when called upon in other classes.
   * @param maze
   */
  public void setMaze(Maze maze) {
    this.maze = maze;
  }
}
