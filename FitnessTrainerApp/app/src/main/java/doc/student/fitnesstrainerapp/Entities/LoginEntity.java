package doc.student.fitnesstrainerapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Login_table")
public class LoginEntity {
    @PrimaryKey(autoGenerate = true)
    private int loginID;
    private String name;
    private String password;


    public LoginEntity(int loginID, String name, String password) {
        this.loginID = loginID;
        this.name = name;
        this.password = password;
    }

    public int getLoginID() {
        return loginID;
    }

    public void setLoginID(int loginID) {
        this.loginID = loginID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
