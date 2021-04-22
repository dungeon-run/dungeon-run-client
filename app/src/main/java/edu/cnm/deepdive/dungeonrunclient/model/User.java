package edu.cnm.deepdive.dungeonrunclient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.UUID;

/**
 * User information to be associated with each user that access the application.
 */
public class User {

  @Expose
  private UUID id;

  @Expose
  private Date created;

  @Expose
  private Date connected;

  @Expose
  @SerializedName("name")
  private String displayName;

  /**
   * Gets the UUID for use of the id associated with the user.
   * @return
   */
  public UUID getId() {
    return id;
  }

  /**
   * Sets the ID for use for the user.
   * @param id
   */
  public void setId(UUID id) {
    this.id = id;
  }

  /**
   * Gets the date of when the first instance of the user was created.
   * @return
   */
  public Date getCreated() {
    return created;
  }

  /**
   * Sets the date of the first instance of when the user was created.
   * @param created
   */
  public void setCreated(Date created) {
    this.created = created;
  }

  /**
   * Updates the time stamp for each time the user reconnects to the application.
   * @return
   */
  public Date getConnected() {
    return connected;
  }

  /**
   * Updates the time stamp and sets it each time the user reconnects to the application.
   * @param connected
   */
  public void setConnected(Date connected) {
    this.connected = connected;
  }

  /**
   * Gets the display name from Google sign in to use for user.
   * @return
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Sets the display name from Google sign in to display in the application for user.
   * @param displayName
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
}
