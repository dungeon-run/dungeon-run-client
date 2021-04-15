package edu.cnm.deepdive.dungeonrunclient.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import edu.cnm.deepdive.dungeonrunclient.model.Cell;
import edu.cnm.deepdive.dungeonrunclient.model.Cell.Direction;
import edu.cnm.deepdive.dungeonrunclient.model.Maze;
import java.util.EnumSet;

public class MazeView extends View {

  private Maze maze;
  private Paint paint;

  {
    setWillNotDraw(false);
    paint = new Paint();
    paint.setColor(Color.BLACK); // FIXME Take it from a resource. Make it more pretty
    paint.setStrokeWidth(2); //FIXME Make dependat on puzzle diffucly and view size
  }

  public MazeView(Context context) {
    super(context);
  }

  public MazeView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public MazeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public MazeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onDraw(Canvas canvas) {
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

  public void setMaze(Maze maze) {
    this.maze = maze;
  }
}
