package doc.student.fitnesstrainerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import doc.student.fitnesstrainerapp.Database.Repository;
import doc.student.fitnesstrainerapp.Entities.CardioTypeEntity;
import doc.student.fitnesstrainerapp.Entities.LoginEntity;
import doc.student.fitnesstrainerapp.Entities.StrengthTypeEntity;
import doc.student.fitnesstrainerapp.R;

public class MainActivity extends AppCompatActivity {

    //To temporarily store user input
    String loginName;
    String password;

    //Temporary placeholders for R.id in activity_main
    EditText editName;
    EditText editPassword;

    List<LoginEntity> allLogins;

    //For the AlarmManager.set() method when creating an alert
    public static int numAlert;

    // Load login screen
    // Create test login
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Repository repository = new Repository(getApplication());

        //Account created for testing
        LoginEntity loginEntity = new LoginEntity(1, "Test", "Test123");
        repository.insert(loginEntity);

        allLogins = repository.getAllLogins();

        editName = findViewById(R.id.editTextLoginName);
        editPassword = findViewById(R.id.editTextTextPassword);

    }

    public void onLogin(View view) {

        //Get input from Login Name and Password fields in Main Activity
        loginName = editName.getText().toString();
        password = editPassword.getText().toString();

        try {
            //Looks inside the login table for a matching username and password
            for (LoginEntity l : allLogins) {

                //Toast pops up if fields are missing
                if (loginName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Login Name is missing", Toast.LENGTH_LONG).show();
                    break;
                }
                if (password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Password is missing", Toast.LENGTH_LONG).show();
                    break;
                }

                //Toast pops up if fields are incorrect
                if ((!l.getName().equals(loginName)) || (!l.getPassword().equals(password))) {
                    Toast.makeText(MainActivity.this, "Incorrect Username or Password.", Toast.LENGTH_LONG).show();
                    break;

                }

                //Scheduled Sessions activity appears if login name and password are found
                    if ((l.getName().equals(loginName)) && (l.getPassword().equals(password))) {
                    Intent intent = new Intent(MainActivity.this, ScheduledSessions.class);
                    startActivity(intent);

                }

            }

        } catch (Exception e) {
            //Prevents the app from crashing
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "OOPs! An unknown error occurred. Please check the login credentials again. If the issue persists, please restart the app. ", Toast.LENGTH_LONG).show();
        }

    }
}