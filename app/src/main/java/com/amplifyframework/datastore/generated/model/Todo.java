package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Todo type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Todos")
@Index(name = "byTeam", fields = {"teamID"})
public final class Todo implements Model {
  public static final QueryField ID = field("Todo", "id");
  public static final QueryField TITLE = field("Todo", "title");
  public static final QueryField BODE = field("Todo", "bode");
  public static final QueryField STATE = field("Todo", "state");
  public static final QueryField FILE_KEY = field("Todo", "fileKey");
  public static final QueryField LATITUDE = field("Todo", "latitude");
  public static final QueryField LONGITUDE = field("Todo", "longitude");
  public static final QueryField TEAM = field("Todo", "teamID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String") String bode;
  private final @ModelField(targetType="String") String state;
  private final @ModelField(targetType="String") String fileKey;
  private final @ModelField(targetType="Float") Double latitude;
  private final @ModelField(targetType="Float") Double longitude;
  private final @ModelField(targetType="Team") @BelongsTo(targetName = "teamID", type = Team.class) Team team;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getBode() {
      return bode;
  }
  
  public String getState() {
      return state;
  }
  
  public String getFileKey() {
      return fileKey;
  }
  
  public Double getLatitude() {
      return latitude;
  }
  
  public Double getLongitude() {
      return longitude;
  }
  
  public Team getTeam() {
      return team;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Todo(String id, String title, String bode, String state, String fileKey, Double latitude, Double longitude, Team team) {
    this.id = id;
    this.title = title;
    this.bode = bode;
    this.state = state;
    this.fileKey = fileKey;
    this.latitude = latitude;
    this.longitude = longitude;
    this.team = team;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Todo todo = (Todo) obj;
      return ObjectsCompat.equals(getId(), todo.getId()) &&
              ObjectsCompat.equals(getTitle(), todo.getTitle()) &&
              ObjectsCompat.equals(getBode(), todo.getBode()) &&
              ObjectsCompat.equals(getState(), todo.getState()) &&
              ObjectsCompat.equals(getFileKey(), todo.getFileKey()) &&
              ObjectsCompat.equals(getLatitude(), todo.getLatitude()) &&
              ObjectsCompat.equals(getLongitude(), todo.getLongitude()) &&
              ObjectsCompat.equals(getTeam(), todo.getTeam()) &&
              ObjectsCompat.equals(getCreatedAt(), todo.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), todo.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getBode())
      .append(getState())
      .append(getFileKey())
      .append(getLatitude())
      .append(getLongitude())
      .append(getTeam())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Todo {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("bode=" + String.valueOf(getBode()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("fileKey=" + String.valueOf(getFileKey()) + ", ")
      .append("latitude=" + String.valueOf(getLatitude()) + ", ")
      .append("longitude=" + String.valueOf(getLongitude()) + ", ")
      .append("team=" + String.valueOf(getTeam()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Todo justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Todo(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      bode,
      state,
      fileKey,
      latitude,
      longitude,
      team);
  }
  public interface TitleStep {
    BuildStep title(String title);
  }
  

  public interface BuildStep {
    Todo build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep bode(String bode);
    BuildStep state(String state);
    BuildStep fileKey(String fileKey);
    BuildStep latitude(Double latitude);
    BuildStep longitude(Double longitude);
    BuildStep team(Team team);
  }
  

  public static class Builder implements TitleStep, BuildStep {
    private String id;
    private String title;
    private String bode;
    private String state;
    private String fileKey;
    private Double latitude;
    private Double longitude;
    private Team team;
    @Override
     public Todo build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Todo(
          id,
          title,
          bode,
          state,
          fileKey,
          latitude,
          longitude,
          team);
    }
    
    @Override
     public BuildStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep bode(String bode) {
        this.bode = bode;
        return this;
    }
    
    @Override
     public BuildStep state(String state) {
        this.state = state;
        return this;
    }
    
    @Override
     public BuildStep fileKey(String fileKey) {
        this.fileKey = fileKey;
        return this;
    }
    
    @Override
     public BuildStep latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }
    
    @Override
     public BuildStep longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }
    
    @Override
     public BuildStep team(Team team) {
        this.team = team;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String title, String bode, String state, String fileKey, Double latitude, Double longitude, Team team) {
      super.id(id);
      super.title(title)
        .bode(bode)
        .state(state)
        .fileKey(fileKey)
        .latitude(latitude)
        .longitude(longitude)
        .team(team);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder bode(String bode) {
      return (CopyOfBuilder) super.bode(bode);
    }
    
    @Override
     public CopyOfBuilder state(String state) {
      return (CopyOfBuilder) super.state(state);
    }
    
    @Override
     public CopyOfBuilder fileKey(String fileKey) {
      return (CopyOfBuilder) super.fileKey(fileKey);
    }
    
    @Override
     public CopyOfBuilder latitude(Double latitude) {
      return (CopyOfBuilder) super.latitude(latitude);
    }
    
    @Override
     public CopyOfBuilder longitude(Double longitude) {
      return (CopyOfBuilder) super.longitude(longitude);
    }
    
    @Override
     public CopyOfBuilder team(Team team) {
      return (CopyOfBuilder) super.team(team);
    }
  }
  
}
