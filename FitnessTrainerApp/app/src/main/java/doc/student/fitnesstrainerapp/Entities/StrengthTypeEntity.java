package doc.student.fitnesstrainerapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "strength_table")
public class StrengthTypeEntity extends WorkoutEntity {
    @PrimaryKey(autoGenerate = true)
    private int strengthID;
    private int workoutID;

    public StrengthTypeEntity(String name, String repetitionType, String repetitionItem, int strengthID, int workoutID) {
        super(name, repetitionType, repetitionItem);
        this.strengthID = strengthID;
        this.workoutID = workoutID;
    }

    public int getStrengthID() {
        return strengthID;
    }

    public void setStrengthID(int strengthID) {
        this.strengthID = strengthID;
    }

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

}
