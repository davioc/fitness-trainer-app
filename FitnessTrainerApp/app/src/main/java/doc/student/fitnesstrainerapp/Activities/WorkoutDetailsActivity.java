package doc.student.fitnesstrainerapp.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import doc.student.fitnesstrainerapp.Adapters.CardioAdapter;
import doc.student.fitnesstrainerapp.Adapters.StrengthAdapter;
import doc.student.fitnesstrainerapp.Database.Repository;
import doc.student.fitnesstrainerapp.Entities.CardioTypeEntity;
import doc.student.fitnesstrainerapp.Entities.StrengthTypeEntity;
import doc.student.fitnesstrainerapp.Entities.WorkoutDetailsEntity;
import doc.student.fitnesstrainerapp.R;

public class WorkoutDetailsActivity extends AppCompatActivity {
    Repository repository;
    public static int numCardio;
    public static int numStrength;
    int workoutId;
    int sessionID;
    String workoutType;
    String fitnessLevel;
    String duration;
    String notes;

    //Edit text fields in workout details activity
    EditText editWorkoutType;
    EditText editFitnessLevel;
    EditText editDuration;
    EditText editNotes;

    List<WorkoutDetailsEntity> allWorkouts;
    WorkoutDetailsEntity currentWorkout;
    RecyclerView recyclerView;

    RadioGroup carOrStr;
    RadioButton cardioButton;
    RadioButton strengthButton;

//    WorkoutItemActivity wIA;
//    String typePlaceHolder;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_workout_details);

        // Creates a back arrow option for the previous screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Radiobutton stuff
//        carOrStr = (RadioGroup)findViewById(R.id.car_or_str_radio_group);
//        cardioButton = findViewById(R.id.cardioButton);
//        strengthButton = findViewById(R.id.strengthButton);


        //Create repository and
        repository = new Repository(getApplication());
        allWorkouts = repository.getAllWorkouts();
        workoutId = getIntent().getIntExtra("workoutID", -1);

        //If workoutID is found then the value of w is set to the currentWorkoutEntity
        for (WorkoutDetailsEntity w:allWorkouts) {
            if (w.getWorkoutID() == workoutId)
                currentWorkout = w;
        }

        workoutType = getIntent().getStringExtra("workoutType");
        fitnessLevel = getIntent().getStringExtra("fitnessLevel");
        duration = getIntent().getStringExtra("duration");
        notes = getIntent().getStringExtra("notes");
        sessionID = getIntent().getIntExtra("sessID", -1);

        //Edit workout fields are initialized
        editWorkoutType = findViewById(R.id.editTextWorkoutType);
        editFitnessLevel = findViewById(R.id.editTextFitnessLevel);
        editDuration = findViewById(R.id.editTextDuration);
        editNotes = findViewById(R.id.editTextNotes);

        //Editable fields are set with information if workout id is found
        if (workoutId != -1) {
            editWorkoutType.setText(workoutType);
            editFitnessLevel.setText(fitnessLevel);
            editDuration.setText(duration);
            editNotes.setText(notes);
        }

        //Initializes the recycler view for cardio workouts
                recyclerView = findViewById(R.id.workoutTypeRecylerView);

        //Looks if type is to cardio
        if (editWorkoutType.getText().toString().equals("Cardio")) {
            final CardioAdapter adapter = new CardioAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            List<CardioTypeEntity> filteredCardio = new ArrayList<>();
//            Toast.makeText(getApplicationContext(), "I found: " + editWorkoutType.getText().toString(), Toast.LENGTH_LONG).show();

            //If there are cardio workouts in the repository, and their workout ids match the previous
            // then they'll be added to the recyclerview
            for (CardioTypeEntity c : repository.getAllCardioWorkouts()) {
                if (c.getWorkoutID() == workoutId) filteredCardio.add(c);
            }
            //Sets the size of the recyclerView list
            numCardio = filteredCardio.size();
            adapter.setCardioTypeEntitylist(filteredCardio);

            //let workout item activity know to load cardio layout
            WorkoutItemActivity.addCardioSwitch();
            return;
        }


        //Looks if type is set to Strength
        if (editWorkoutType.getText().toString().equals("Strength")) {
            final StrengthAdapter adapter = new StrengthAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            List<StrengthTypeEntity> filteredStrength = new ArrayList<>();

            for (StrengthTypeEntity s : repository.getAllStrengthWorkouts()) {
                if (s.getWorkoutID() == workoutId) filteredStrength.add(s);
            }

            numStrength = filteredStrength.size();
            adapter.setStrengthTypeEntityList(filteredStrength);

            //let workout item activity know to load strength layout
            WorkoutItemActivity.addStrengthSwitch();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu){
        //inflate menu on the top-right corner if workout id is found
        if (workoutId != -1)
            getMenuInflater().inflate(R.menu.menu_workout_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return  true;
            case R.id.refresh_workout_items:
                refreshWorkoutItems();
                return true;

            case R.id.delete_workout:
                if (currentWorkout == null){
                    Toast.makeText(getApplicationContext(), "Workout already deleted.\n Go back and tap [Refresh] via the top right menu to update the list.", Toast.LENGTH_LONG).show();
                    break;
                }
                repository.delete(currentWorkout);
                //Delete associated cardio ids
                for (CardioTypeEntity c:repository.getAllCardioWorkouts()) {
                    if (c.getWorkoutID() == workoutId)
                        repository.delete(c);
                }

                for (StrengthTypeEntity s:repository.getAllStrengthWorkouts()) {
                    if (s.getWorkoutID() == workoutId)
                        repository.delete(s);
                }


                Toast.makeText(getApplicationContext(), "Workout type [" + currentWorkout.getWorkoutType() + "] was successfully deleted along with any associated cardio/strength items \n Tap [Refresh] via top-right menu option to view updated list.", Toast.LENGTH_LONG).show();
                this.finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveWorkoutDetails(View view) {

        //Checks is Cardio or Strength are entered into the type field
        if (!editWorkoutType.getText().toString().equals("Cardio")){
            if (!editWorkoutType.getText().toString().equals("Strength")) {
                Toast.makeText(getApplicationContext(), "Please enter 'Cardio' or 'Strength' into Workout Type field", Toast.LENGTH_LONG).show();
                return;
            }
        }

        if (editFitnessLevel.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a Fitness Level", Toast.LENGTH_LONG).show();
            return;
        }

        if (editDuration.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a Duration", Toast.LENGTH_LONG).show();
            return;
        }

        if (editNotes.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter Customer Notes", Toast.LENGTH_LONG).show();
            return;
        }

        // Checks if there are any workouts. If there are none, ID is set to 1 to initialize new table
        if (allWorkouts.size() == 0) {
            workoutId = 1;
            WorkoutDetailsEntity w = new WorkoutDetailsEntity(workoutId, editFitnessLevel.getText().toString(), editDuration.getText().toString(), editNotes.getText().toString(), editWorkoutType.getText().toString(), sessionID);
            repository.insert(w);
//            this.finish();
        }

        //If no workout ID is found, a new one is created and it's added to the last of the list
        if (workoutId == -1) {
            workoutId = 0;
            WorkoutDetailsEntity w = new WorkoutDetailsEntity(workoutId,editFitnessLevel.getText().toString(), editDuration.getText().toString(), editNotes.getText().toString(), editWorkoutType.getText().toString(), sessionID);
            repository.insert(w);
        } else {
            WorkoutDetailsEntity w = new WorkoutDetailsEntity(workoutId, editFitnessLevel.getText().toString(), editDuration.getText().toString(), editNotes.getText().toString(), editWorkoutType.getText().toString(), sessionID);
            repository.update(w);
//            this.finish();
        }
        this.finish();
    }

    //Clicking on the add button on the bottom right corner opens the add workout activity
    public void onAddWorkoutItem(View view) {

        //Creates a temporary workout ID value for the item activity to reference
        if (workoutId != -1){
            Intent intent = new Intent(WorkoutDetailsActivity.this, WorkoutItemActivity.class);
            if (currentWorkout != null)
                intent.putExtra("workoutID", currentWorkout.getWorkoutID());

            startActivity(intent);
        }         else {
            Toast.makeText(getApplicationContext(), "Please save current Workout details before adding/modifying items.", Toast.LENGTH_LONG).show();
        }

    }

    private void refreshWorkoutItems() {

        if (editWorkoutType.getText().toString().equals("Cardio")) {
            //Refresh cardio list
            final CardioAdapter adapter = new CardioAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<CardioTypeEntity> filteredCardios = new ArrayList<>();
            List<CardioTypeEntity> allCardios = repository.getAllCardioWorkouts();
            for (CardioTypeEntity c : allCardios) {
                if (c.getWorkoutID() == workoutId) filteredCardios.add(c);
                adapter.setCardioTypeEntitylist(filteredCardios);
            }
            return;
        }
        if (editWorkoutType.getText().toString().equals("Strength")){
            //Refresh strength list
            final StrengthAdapter adapter = new StrengthAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<StrengthTypeEntity> filteredStrengths = new ArrayList<>();
            List<StrengthTypeEntity> allStrengths = repository.getAllStrengthWorkouts();
            for (StrengthTypeEntity s : allStrengths) {
                if (s.getWorkoutID() == workoutId) filteredStrengths.add(s);
                adapter.setStrengthTypeEntityList(filteredStrengths);
            }
        }
    }

//    public void onStrBtn(View view) {
//        Toast.makeText(getApplicationContext(), strengthButton.getText() + " was clicked.", Toast.LENGTH_LONG).show();
//    }
//
//    public void onCardioBtn(View view) {
//        Toast.makeText(getApplicationContext(), cardioButton.getText() + " was clicked.", Toast.LENGTH_LONG).show();
//    }
}
