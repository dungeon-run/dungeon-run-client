package edu.cnm.deepdive.dungeonrunclient.model;

import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.UUID;

public class Level {

  @Expose
  private UUID id;

  @Expose
  private Date endTime;

  @Expose
  private int timeGiven;

  @Expose
  private int difficulty;

  @Expose
  private boolean completed;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public int getTimeGiven() {
    return timeGiven;
  }

  public void setTimeGiven(int timeGiven) {
    this.timeGiven = timeGiven;
  }

  public int getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}
