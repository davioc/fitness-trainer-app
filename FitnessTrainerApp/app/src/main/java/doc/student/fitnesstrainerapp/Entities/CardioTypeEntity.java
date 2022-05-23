package doc.student.fitnesstrainerapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cardio_table")
public class CardioTypeEntity extends WorkoutEntity {

    @PrimaryKey(autoGenerate = true)
    private int cardioID;
    private int workoutID;

    public CardioTypeEntity(String name, String repetitionType, String repetitionItem, int cardioID, int workoutID) {
        super(name, repetitionType, repetitionItem);
        this.cardioID = cardioID;
        this.workoutID = workoutID;
    }

    public int getCardioID() {
        return cardioID;
    }

    public void setCardioID(int cardioID) {
        this.cardioID = cardioID;
    }

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

}
