package doc.student.fitnesstrainerapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "session_table")
public class SessionEntity {
    @PrimaryKey(autoGenerate = true)
    private int sessionID;
    private String sessionTrainer;
    private String sessionCustomer;
    private String sessionTitle;
    private String date;
    private String startTime;
    private String endTime;

    public SessionEntity(int sessionID, String sessionTrainer, String sessionCustomer, String sessionTitle, String date, String startTime, String endTime) {
        this.sessionID = sessionID;
        this.sessionTrainer = sessionTrainer;
        this.sessionCustomer = sessionCustomer;
        this.sessionTitle = sessionTitle;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSessionTrainer() {
        return sessionTrainer;
    }

    public void setSessionTrainer(String sessionTrainer) {
        this.sessionTrainer = sessionTrainer;
    }

    public String getSessionCustomer() {
        return sessionCustomer;
    }

    public void setSessionCustomer(String sessionCustomer) {
        this.sessionCustomer = sessionCustomer;
    }
}
