package edu.cnm.deepdive.dungeonrunclient.model;

import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.UUID;

public class Attempt {

  @Expose
  private User user;

  @Expose
  private UUID id;

  @Expose
  private Date startTime;

  @Expose
  private Date endTime;

  @Expose
  private int timeGiven;

  @Expose
  private int difficulty;

  @Expose
  private boolean completed;

  @Expose
  private long timeElapsed;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
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

  public long getTimeElapsed() {
    return timeElapsed;
  }

  public void setTimeElapsed(long timeElapsed) {
    this.timeElapsed = timeElapsed;
  }
}
