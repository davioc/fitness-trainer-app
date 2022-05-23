package doc.student.fitnesstrainerapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customer_table")
public class CustomerEntity extends PersonEntity{
    @PrimaryKey(autoGenerate = true)
    private int customerID;
    private String workoutFocus;
    private String fitnessLevel;
    private String goals;

    public CustomerEntity(int customerID, String name, String memberStartDate, String workoutFocus, String fitnessLevel, String goals) {
        super(name, memberStartDate);
        this.customerID = customerID;
        this.workoutFocus = workoutFocus;
        this.fitnessLevel = fitnessLevel;
        this.goals = goals;
    }

    public String getWorkoutFocus() {
        return workoutFocus;
    }

    public void setWorkoutFocus(String workoutFocus) {
        this.workoutFocus = workoutFocus;
    }

    public String getFitnessLevel() {
        return fitnessLevel;
    }

    public void setFitnessLevel(String fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
