package edu.cnm.deepdive.dungeonrunclient.model;

import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.UUID;

/**
 * Gathers the information for level Attempts from the server side to use in the client side.
 */
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

  /**
   * Extracts the User class for use in the database.
   * @return
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the User for use in other classes.
   * @param user
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets the id for the user to use in other classes when needed.
   * @return
   */
  public UUID getId() {
    return id;
  }

  /**
   * Sets the the id for use when needed in other classes.
   * @param id
   */
  public void setId(UUID id) {
    this.id = id;
  }

  /**
   * Gets the timestamp of the Date when the attempt is started.
   * @return
   */
  public Date getStartTime() {
    return startTime;
  }

  /**
   * Sets the Date for the startTime when the attempt is started.
   * @param startTime
   */
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  /**
   * Gets the endTime Date from when the level was completed.
   * @return
   */
  public Date getEndTime() {
    return endTime;
  }

  /**
   * Sets the endTime date stamp when the level is completed.
   * @param endTime
   */
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  /**
   * Gets the timeGiven that was set for the associated difficulty level.
   * @return
   */
  public int getTimeGiven() {
    return timeGiven;
  }

  /**
   * Sets the timeGiven to be associated to the difficulty of the level.
   * @param timeGiven
   */
  public void setTimeGiven(int timeGiven) {
    this.timeGiven = timeGiven;
  }

  /**
   * Gets the difficulty set for the attempt.
   * @return
   */
  public int getDifficulty() {
    return difficulty;
  }

  /**
   * Sets the difficulty for the attempt. Difficulty is set by the user from the settings.
   * @param difficulty
   */
  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }

  /**
   * Returns true when the attempt is completed.
   * @return
   */
  public boolean isCompleted() {
    return completed;
  }

  /**
   * Sets the completed boolean for use in the leaderboard when completed.
   * @param completed
   */
  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  /**
   * Gets the time elapsed from the the attempt when the attempt is completed.
   * @return
   */
  public long getTimeElapsed() {
    return timeElapsed;
  }

  /**
   * Sets the time elapsed when the attempt is completed.
   * @param timeElapsed
   */
  public void setTimeElapsed(long timeElapsed) {
    this.timeElapsed = timeElapsed;
  }
}
