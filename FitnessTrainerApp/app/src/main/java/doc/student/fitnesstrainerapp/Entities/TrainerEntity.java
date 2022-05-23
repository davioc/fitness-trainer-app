package doc.student.fitnesstrainerapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trainer_table")
public class TrainerEntity extends PersonEntity {
    @PrimaryKey(autoGenerate = true)
    private int trainerID;
    private String position;

    public TrainerEntity(int trainerID, String name, String memberStartDate, String position) {
        super(name, memberStartDate);
        this.trainerID = trainerID;
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }
}
