package doc.student.fitnesstrainerapp.Activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.chrono.ChronoLocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import doc.student.fitnesstrainerapp.Adapters.SessionReceiver;
import doc.student.fitnesstrainerapp.Adapters.WorkoutDetailsAdapter;
import doc.student.fitnesstrainerapp.Database.Repository;
import doc.student.fitnesstrainerapp.Entities.SessionEntity;
import doc.student.fitnesstrainerapp.Entities.WorkoutDetailsEntity;
import doc.student.fitnesstrainerapp.R;

public class SessionDetailsActivity extends AppCompatActivity {

    //For validation check
    private DateTimeFormatter formatter;
    private LocalDate localDate;
//    private LocalDate ldtStart;
    private String endString;
    private String startString;
    private String dateString;
//    private String minuteString;
    private Repository  repository;
    List<SessionEntity> allSessions;
    List<WorkoutDetailsEntity> filteredWorkouts;

    int sessionId;

    //Edit text fields in the session details activity
    EditText editSessTitle;
    EditText editSessTrainer;
    EditText editsessCustomer;
    EditText editsessDate;
    EditText editsessStart;
    EditText editsessEnd;

    //Found session entity
    SessionEntity currentSessionEntity;

    //Recycler View on the session details page
    RecyclerView recyclerView;

    //Track number of workouts
    public static int numWorkouts;

    /**
     * onCreate initializes the layout of the session details activity.
     */
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_session_details);

        //Create a back arrow option for the Main Activity screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Get Session ID if one is selected
        sessionId = getIntent().getIntExtra("sessionID", -1);
        repository = new Repository(getApplication());
        allSessions = repository.getAllSessions();

        //If Session ID is found then SessionEntity is set to it
        for (SessionEntity sessionEntity : allSessions) {
            if (sessionEntity.getSessionID() == sessionId)
                currentSessionEntity = sessionEntity;
        }

        //The session fields are initialized
        editSessTitle = findViewById(R.id.editTextSessionTitle);
        editSessTrainer = findViewById(R.id.editTextTrainerName);
        editsessCustomer = findViewById(R.id.editTextCustomerName);
        editsessDate = findViewById(R.id.editTextDate);
        editsessStart = findViewById(R.id.editTextSessionStartTime);
        editsessEnd = findViewById(R.id.editTextSessionEndTime);

        //If current session has values, the edit for will be filled out with it
        if (currentSessionEntity != null) {
            editSessTitle.setText(currentSessionEntity.getSessionTitle());
        editSessTrainer.setText(currentSessionEntity.getSessionTrainer());
        editsessCustomer.setText(currentSessionEntity.getSessionCustomer());
        editsessDate.setText(currentSessionEntity.getDate());
        editsessStart.setText(currentSessionEntity.getStartTime());
        editsessEnd.setText(currentSessionEntity.getEndTime());
    }

        //initialize recyclerview for workouts
        recyclerView = findViewById(R.id.sessionTypeRecycleView);
        final WorkoutDetailsAdapter adapter = new WorkoutDetailsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        filteredWorkouts = new ArrayList<>();

        //If there are workouts in the repository matching the session ID then it will be loaded
        //In the Recycler View
        for (WorkoutDetailsEntity w:repository.getAllWorkouts()) {
            if (w.getSessID()== sessionId)filteredWorkouts.add(w);
        }

        //For checking if there are any workouts assigned to the session
        numWorkouts = filteredWorkouts.size();
        adapter.setmWorkoutDetails(filteredWorkouts);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.session_refresh:
                refreshSessions();
                return true;

            case R.id.delete_session:

                filteredWorkouts = new ArrayList<>();

                for (WorkoutDetailsEntity w : repository.getAllWorkouts()) {
                    if (w.getSessID() == sessionId) filteredWorkouts.add(w);
                }
                numWorkouts = filteredWorkouts.size();

                if (numWorkouts == 0) {
                    repository.delete(currentSessionEntity);
                    Toast.makeText(getApplicationContext(), "Session ID: [" + currentSessionEntity.getSessionID() + "] was deleted.", Toast.LENGTH_LONG).show();
                    Intent deletedSession = new Intent(SessionDetailsActivity.this, ScheduledSessions.class);
                    startActivity(deletedSession);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to delete. The selected session still has a workout assigned.\n If list is empty, tap [Refresh] via the top right menu.", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.session_alert:
//                String alertDateString = editsessDate.getText().toString();
//                String alertStartString = editsessStart.getText().toString();
//
//                if (alertStartString.isEmpty() || alertDateString.isEmpty()) {
//                    Toast.makeText(SessionDetailsActivity.this, "Missing a Start Date in MM/DD/YY format.\n Save a new date and create the alert again.", Toast.LENGTH_LONG).show();
//                    break;
//                }
                try {
                    if (editSessTitle.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Missing Title", Toast.LENGTH_LONG).show();
                        break;
                    }

                } catch (Exception ignored){

                }

                try {
                    if (editSessTrainer.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Missing Trainer name", Toast.LENGTH_LONG).show();
                        break;
                    }

                } catch (Exception ignored){

                }

                try {
                    if (editsessCustomer.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Missing Customer name", Toast.LENGTH_LONG).show();
                        break;
                    }

                } catch (Exception ignored){

                }

                //Date field
//        String dateString;
                try {
                    if (editsessDate.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Missing Date", Toast.LENGTH_LONG).show();
                        break;
                    }

                    try {
                        dateString = editsessDate.getText().toString();
                        formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MM/dd/yyyy").toFormatter(Locale.ENGLISH);
                        localDate = LocalDate.parse(dateString, formatter);

                    }catch (Exception ignored){
                        Toast.makeText(getApplicationContext(), "Incorrect date format. Make sure it's: 'MM/dd/yyyy'\n" + "Example: 06/01/2022", Toast.LENGTH_LONG).show();
                        break;
                    }

                } catch (Exception ignored){

                }

                //Start field
                try {
                    if (editsessStart.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Missing Start Time", Toast.LENGTH_LONG).show();
                        break;
                    }

                } catch (Exception ignored){

                }

//        String startString;
                LocalDateTime ldtStart;
                try {

                    DateTimeFormatter formatStart;
                    startString = dateString.concat(" " + editsessStart.getText().toString());

                    formatStart = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MM/dd/yyyy HH:mm").toFormatter(Locale.ENGLISH);
                    ldtStart = LocalDateTime.parse(startString, formatStart);

                } catch (Exception ignored){
                    Toast.makeText(getApplicationContext(), "Incorrect Start time format. Make sure it's 'HH:mm'\n" + "Example: 07:00", Toast.LENGTH_LONG).show();
                    break;
                }

                //End field
                try {
                    if (editsessEnd.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Missing End Time", Toast.LENGTH_LONG).show();
                        break;
                    }

                } catch (Exception ignored){

                }

                //Makes sure time is entered in HH:mm format
                LocalDateTime ldtEnd;
                try {
                    DateTimeFormatter formatEnd;
                    endString = dateString.concat(" " + editsessEnd.getText().toString());
                    formatEnd = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MM/dd/yyyy HH:mm").toFormatter(Locale.ENGLISH);
                    ldtEnd = LocalDateTime.parse(endString, formatEnd);

                } catch (Exception ignored){
                    Toast.makeText(getApplicationContext(), "Incorrect End time format. Make sure it's 'HH:mm'\n" + "Example: 07:00", Toast.LENGTH_LONG).show();
                    break;
                }

                //Compares start time end time
                if (ldtEnd.isBefore(ldtStart)){
                    Toast.makeText(getApplicationContext(), "Current 'End Time' is before 'Start Time'. Please make End later than Start.", Toast.LENGTH_LONG).show();
                    break;
                }

                //Creates an alarm date to happen 1 hour before the start time
                LocalDateTime ldtAlarm = ldtStart.minusHours(1);

                //Converts the LocalDateTimeObject to Millis so that the Alarm Manager can use it as a trigger
                Long alertTrigger = ldtAlarm.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

//                Toast.makeText(getApplicationContext(), "Checking if long was converted: " + alertTrigger, Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), "Checking current system time " + System.currentTimeMillis(), Toast.LENGTH_LONG).show();
                //
                Intent timeIntent = new Intent(SessionDetailsActivity.this, SessionReceiver.class);
                timeIntent.putExtra("key", "!ALERT! - Session [" + currentSessionEntity.getSessionTitle() + "] " + "Starts soon at: " + currentSessionEntity.getStartTime());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(SessionDetailsActivity.this, ++MainActivity.numAlert, timeIntent, 0);

                Toast.makeText(getApplicationContext(), "Success - Alert set 1 hour before session starts." , Toast.LENGTH_LONG).show();

                AlarmManager sessionAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                sessionAlarm.set(AlarmManager.RTC_WAKEUP, alertTrigger, pendingIntent);

        }

        return super.onOptionsItemSelected(item);

    }

    public void onSaveSession(View view) {

        try {
            if (editSessTitle.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Missing Title", Toast.LENGTH_LONG).show();
                return;
            }

        } catch (Exception ignored){

        }

        try {
            if (editSessTrainer.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Missing Trainer name", Toast.LENGTH_LONG).show();
                return;
            }

        } catch (Exception ignored){

        }

        try {
            if (editsessCustomer.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Missing Customer name", Toast.LENGTH_LONG).show();
                return;
            }

        } catch (Exception ignored){

        }

        //Date field
//        String dateString;
        try {
            if (editsessDate.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Missing Date", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                dateString = editsessDate.getText().toString();
                formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MM/dd/yyyy").toFormatter(Locale.ENGLISH);
                localDate = LocalDate.parse(dateString, formatter);

            }catch (Exception ignored){
                Toast.makeText(getApplicationContext(), "Incorrect date format. Make sure it's: 'MM/dd/yyyy'\n" + "Example: 06/01/2022", Toast.LENGTH_LONG).show();
                return;
            }

        } catch (Exception ignored){

        }

        //Start field
        try {
            if (editsessStart.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Missing Start Time", Toast.LENGTH_LONG).show();
                return;
            }

        } catch (Exception ignored){

        }

//        String startString;
        LocalDateTime ldtStart;
        try {

            DateTimeFormatter formatStart;
            startString = dateString.concat(" " + editsessStart.getText().toString());

            formatStart = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MM/dd/yyyy HH:mm").toFormatter(Locale.ENGLISH);
            ldtStart = LocalDateTime.parse(startString, formatStart);

        } catch (Exception ignored){
            Toast.makeText(getApplicationContext(), "Incorrect Start time format. Make sure it's 'HH:mm'\n" + "Example: 07:00", Toast.LENGTH_LONG).show();
            return;
        }

        //End field
        try {
            if (editsessEnd.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Missing End Time", Toast.LENGTH_LONG).show();
                return;
            }

        } catch (Exception ignored){

        }

        //Makes sure time is entered in HH:mm format
        LocalDateTime ldtEnd;
        try {
            DateTimeFormatter formatEnd;
            endString = dateString.concat(" " + editsessEnd.getText().toString());
            formatEnd = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MM/dd/yyyy HH:mm").toFormatter(Locale.ENGLISH);
            ldtEnd = LocalDateTime.parse(endString, formatEnd);

        } catch (Exception ignored){
            Toast.makeText(getApplicationContext(), "Incorrect End time format. Make sure it's 'HH:mm'\n" + "Example: 07:00", Toast.LENGTH_LONG).show();
            return;
        }

        //Compares start time end time
        if (ldtEnd.isBefore(ldtStart)){
            Toast.makeText(getApplicationContext(), "Current 'End Time' is before 'Start Time'. Please make End later than Start.", Toast.LENGTH_LONG).show();
             return;
        }

        //If there are no sessions in the list, the new session ID is set to 1
        if (allSessions.size() == 0) {
            sessionId = 1;
            SessionEntity sessionEntity = new SessionEntity(sessionId, editSessTrainer.getText().toString(), editsessCustomer.getText().toString(), editSessTitle.getText().toString(), editsessDate.getText().toString(), editsessStart.getText().toString(), editsessEnd.getText().toString());
            repository.insert(sessionEntity);
        }

        //If no session is selected, and the button on the bottom right is tapped, the session saved
        //is added to the scheduled session screen.
        if (sessionId == -1) {
            sessionId = allSessions.get(allSessions.size()-1).getSessionID();
            SessionEntity sessionEntity = new SessionEntity(++sessionId, editSessTrainer.getText().toString(), editsessCustomer.getText().toString(), editSessTitle.getText().toString(), editsessDate.getText().toString(), editsessStart.getText().toString(), editsessEnd.getText().toString());
            repository.insert(sessionEntity);
        }
        else {
            SessionEntity sessionEntity = new SessionEntity(sessionId, editSessTrainer.getText().toString(), editsessCustomer.getText().toString(), editSessTitle.getText().toString(), editsessDate.getText().toString(), editsessStart.getText().toString(), editsessEnd.getText().toString());
            repository.update(sessionEntity);
        }

//        Toast.makeText(getApplicationContext(), "Workout was added to session. Tap the [Refresh] option via the top-right menu to view updated list", Toast.LENGTH_LONG).show();

        //Opens the session list after an action is made
        Intent intent = new Intent(SessionDetailsActivity.this, ScheduledSessions.class);
        startActivity(intent);


    }

    //Creates options menu in top-right corner
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu, adds items to action bar if there's a session ID
        if (sessionId != -1)
        getMenuInflater().inflate(R.menu.menu_session_details, menu);
        return true;
    }

    //Clicking on the add button on the bottom right corner opens the add workout activity if the current session is saved.
    public void onAddWorkout(View view) {

        //Sets the intent so that when the next activity knows to set that ID
        if (sessionId != -1) {
            Intent intent = new Intent(SessionDetailsActivity.this, WorkoutDetailsActivity.class);
            if (currentSessionEntity != null)
                intent.putExtra("sessID", currentSessionEntity.getSessionID());

            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Please save current Session before adding new Workout.", Toast.LENGTH_LONG).show();
        }
    }

    //Initialize refresh option for Workout recyclerview
    private void refreshSessions(){
        final WorkoutDetailsAdapter adapter = new WorkoutDetailsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<WorkoutDetailsEntity> filteredWorkouts = new ArrayList<>();
        List<WorkoutDetailsEntity> allWorkouts = repository.getAllWorkouts();
        for (WorkoutDetailsEntity w:allWorkouts) {
            if (w.getSessID() == sessionId) filteredWorkouts.add(w);
            adapter.setmWorkoutDetails(filteredWorkouts);
        }
    }
}
