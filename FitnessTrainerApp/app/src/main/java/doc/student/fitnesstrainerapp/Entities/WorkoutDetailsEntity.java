package doc.student.fitnesstrainerapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_details_table")
public class WorkoutDetailsEntity {
    @PrimaryKey(autoGenerate = true)
    private int workoutID;
    private String workoutType;
    private String fitnessLevel;
    private String duration;
    private String notes;

    private int sessID;


    public WorkoutDetailsEntity(int workoutID, String fitnessLevel, String duration, String notes, String workoutType, int sessID) {
        this.workoutID = workoutID;
        this.fitnessLevel = fitnessLevel;
        this.duration = duration;
        this.notes = notes;
        this.workoutType = workoutType;
        this.sessID = sessID;
    }


    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

    public String getFitnessLevel() {
        return fitnessLevel;
    }

    public void setFitnessLevel(String fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public int getSessID() {
        return sessID;
    }

    public void setSessID(int sessID) {
        this.sessID = sessID;
    }

}
