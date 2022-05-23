package doc.student.fitnesstrainerapp.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import doc.student.fitnesstrainerapp.Database.Repository;
import doc.student.fitnesstrainerapp.Entities.CardioTypeEntity;
import doc.student.fitnesstrainerapp.Entities.StrengthTypeEntity;
import doc.student.fitnesstrainerapp.Entities.WorkoutDetailsEntity;
import doc.student.fitnesstrainerapp.R;

public class WorkoutItemActivity extends AppCompatActivity {

    Repository repository;
    int workoutId;
    int cardioId;
    int strengthId;
    static int cardioOrStrength;

    String cardioName;
    String repType;
    String repItem;
    String type;

    TextView editViewLocOrSet;
    TextView editWeightOrIntensity;

    TextView editHintName;
    TextView editHintSets;
    TextView editHintWeights;

    EditText editTextName;
    EditText editTextRepType;
    EditText editTextRepItem;
//    EditText editTextType;



    //Tracks cardio item
    CardioTypeEntity selectedCardio;

    //Initializes list of cardio items
    List<CardioTypeEntity> allCardioWorkouts;

    StrengthTypeEntity selectedStrength;
//    WorkoutDetailsEntity foundWorkout;
//    List<WorkoutDetailsEntity> allWorkouts;
    List<StrengthTypeEntity> allStrengthWorkouts;

    /**
     * Switches are used in this class to differentiate strength and cardio items.
     * @param savedInstanceState
     */
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_workout_item);

        // Creates a back arrow option to the previous screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Create repository
        repository = new Repository(getApplication());

        //Creates a list of cardio workouts
        allCardioWorkouts = repository.getAllCardioWorkouts();
        //Creates a list of strength workouts
        allStrengthWorkouts = repository.getAllStrengthWorkouts();

        try {
            //Get's cardio ID if one was selected
            cardioId = getIntent().getIntExtra("cardioID", -1);
        } catch (Exception e ){
            //no cardio id found
        }

        try {
            strengthId = getIntent().getIntExtra("strengthID", -1);
        } catch (Exception e){
            //no strength id found
        }

        //Get's workout ID from previous activity
        workoutId = getIntent().getIntExtra("workoutID", -1);

        //Initialize fields
        editTextName = findViewById(R.id.editTextWorkoutName);
        editTextRepItem = findViewById(R.id.editTextIntensityOrWeight);
        editTextRepType = findViewById(R.id.editTextLocationOrSet);

        switch (cardioOrStrength){
            case 1:
            //Find a cardio workout
            for (CardioTypeEntity c: allCardioWorkouts){
                if (c.getCardioID() == cardioId)
                    selectedCardio = c;
            }

            if (cardioId != -1){
                editTextName.setText(selectedCardio.getName());
                editTextRepItem.setText(selectedCardio.getRepetitionItem());
                editTextRepType.setText(selectedCardio.getRepetitionType());
             }


                break;
            case 2:

                editTextName.setHint("Bench Press, Bicep Curls");
                editTextRepType.setHint("3 Sets of 10 Repetitions");
                editTextRepItem.setHint("100 lbs, 45 kg");


                editViewLocOrSet = findViewById(R.id.textViewWorkoutLocationOrSets);
                editWeightOrIntensity = findViewById(R.id.textViewIntensityOrWeight);

                editViewLocOrSet.setText("Sets:");
                editWeightOrIntensity.setText("Weight (lbs or kg):");

//                Toast.makeText(getApplicationContext(), "Inside strength case 2", Toast.LENGTH_LONG).show();
                try {
                    for (StrengthTypeEntity s : allStrengthWorkouts) {
                        if (s.getStrengthID() == strengthId)
                            selectedStrength = s;
                    }
                } catch (Exception e){
//                    Toast.makeText(getApplicationContext(), "No lists in the ", Toast.LENGTH_LONG).show();
                }

                if (strengthId != -1){
                    editTextName.setText(selectedStrength.getName());
                    editTextRepItem.setText(selectedStrength.getRepetitionItem());
                    editTextRepType.setText(selectedStrength.getRepetitionType());
                }

        }


    }

    public boolean onCreateOptionsMenu(Menu menu){

        switch (cardioOrStrength){
            case 1:
                if(cardioId != -1)
                    getMenuInflater().inflate(R.menu.menu_workout_item, menu);
                break;

            case 2:
                if(strengthId != -1)
                    getMenuInflater().inflate(R.menu.menu_workout_item, menu);

        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.delete_cardio:
                switch (cardioOrStrength){
                    case 1:
                        if (selectedCardio == null){
                            Toast.makeText(getApplicationContext(), "Cardio already deleted.\n Go back and tap [Refresh Workout Items] via the top right menu to see updated list.", Toast.LENGTH_LONG).show();
                            break;
                        }

                        repository.delete(selectedCardio);
                        Toast.makeText(getApplicationContext(), "Cardio item deleted\nTap [Refresh Workout Items] via the top right corner menu to view new list.", Toast.LENGTH_LONG).show();
                        this.finish();
                        return true;

                    case 2:
                        if (selectedStrength == null){
                            Toast.makeText(getApplicationContext(), "Strength already deleted.\n Go back and tap [Refresh Workout Items] via the top right menu to see updated list.", Toast.LENGTH_LONG).show();
                            break;
                        }

                        repository.delete(selectedStrength);
                        Toast.makeText(getApplicationContext(), "Strength item deleted\nTap [Refresh Workout Items] via the top right corner menu to view new list.", Toast.LENGTH_LONG).show();
                        this.finish();
                        return true;
                }

        }
        return super.onOptionsItemSelected(item);
    }

    //TODO add validation checks to make sure input is entered
    public void onSaveWorkoutItem(View view) {

        switch (cardioOrStrength){
            case 1:

                if (editTextName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a Workout Name", Toast.LENGTH_LONG).show();
                    break;
                }

                if (editTextRepType.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter a Location", Toast.LENGTH_LONG).show();
                    break;
                }

                if (editTextRepItem.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Intensity for the workout", Toast.LENGTH_LONG).show();
                    break;
                }

                // Checks if there are any cardio items. If there are none, ID is set to 1 to initialize new table
                try {
                    if (allCardioWorkouts.size() == 0) {
                        cardioId = 0;
                        CardioTypeEntity c = new CardioTypeEntity(editTextName.getText().toString(),editTextRepType.getText().toString(), editTextRepItem.getText().toString(), cardioId, workoutId);
                        repository.insert(c);
                    }

                    //If no cardio ID is found, a new one is created and it's added to the last of the list
                    if (cardioId == -1) {
                        cardioId = 0;
                        CardioTypeEntity c = new CardioTypeEntity(editTextName.getText().toString(),editTextRepType.getText().toString(), editTextRepItem.getText().toString(), cardioId, workoutId);
                        repository.insert(c);

                    } else {
                        CardioTypeEntity c = new CardioTypeEntity(editTextName.getText().toString(),editTextRepType.getText().toString(), editTextRepItem.getText().toString(), cardioId, workoutId);
                        repository.update(c);
                    }
                    this.finish();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Save not working", Toast.LENGTH_LONG).show();

                }
                break;
            case 2:
                // Checks if there are any cardio items. If there are none, ID is set to 1 to initialize new table

                if (editTextName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a Workout Name", Toast.LENGTH_LONG).show();
                    break;
                }

                if (editTextRepType.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter a Set description", Toast.LENGTH_LONG).show();
                    break;
                }

                if (editTextRepItem.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter Weight", Toast.LENGTH_LONG).show();
                    break;
                }


                try {
                    if (allStrengthWorkouts.size() == 0) {
                        strengthId = 0;
                        StrengthTypeEntity s = new StrengthTypeEntity(editTextName.getText().toString(),editTextRepType.getText().toString(), editTextRepItem.getText().toString(), strengthId, workoutId);
                        repository.insert(s);
                    }

                    //If no cardio ID is found, a new one is created and it's added to the last of the list
                    if (strengthId == -1) {
                        strengthId = 0;
                        StrengthTypeEntity s = new StrengthTypeEntity(editTextName.getText().toString(),editTextRepType.getText().toString(), editTextRepItem.getText().toString(), strengthId, workoutId);
                        repository.insert(s);

                    } else {
                        StrengthTypeEntity s = new StrengthTypeEntity(editTextName.getText().toString(),editTextRepType.getText().toString(), editTextRepItem.getText().toString(), strengthId, workoutId);
                        repository.update(s);
                    }
                    this.finish();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Save not working", Toast.LENGTH_LONG).show();

                }
        }
    }

    /**
     * Makes sure cardio interface is loaded and saved to.
     */
    public static void addCardioSwitch() {
        cardioOrStrength = 1;
    }

    /**
     * Makes sure strength interface is loaded and saved to.
     */
    public static void addStrengthSwitch() {
        cardioOrStrength = 2;
    }

}
