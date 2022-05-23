package doc.student.fitnesstrainerapp.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import doc.student.fitnesstrainerapp.Adapters.CardioAdapter;
import doc.student.fitnesstrainerapp.Adapters.ReportsAdapter;
import doc.student.fitnesstrainerapp.Adapters.ScheduledSessionAdapter;
import doc.student.fitnesstrainerapp.Adapters.WorkoutDetailsAdapter;
import doc.student.fitnesstrainerapp.Database.Repository;
import doc.student.fitnesstrainerapp.Entities.CardioTypeEntity;
import doc.student.fitnesstrainerapp.Entities.SessionEntity;
import doc.student.fitnesstrainerapp.Entities.WorkoutDetailsEntity;
import doc.student.fitnesstrainerapp.R;


public class ReportsActivity extends AppCompatActivity {

    TextView editTitle;
    TextView editCustomer;
    TextView editTrainer;
    TextView editDate;
    TextView editSearch;

    String searchString;

    RadioButton monthButton;
    RadioButton weekButton;

    SessionEntity foundSession;

    TableLayout reportTbl;
    TableRow reportRow;

    private Repository repository;
    private RecyclerView recyclerView;

    List<SessionEntity> reportSessionList;

    private DateTimeFormatter formatter;
    private LocalDateTime nowTime;
    private LocalDateTime timePlusSeven;
    private LocalDateTime timePlusThirty;
    private LocalDate localDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reports);

        //Create a back arrow option for the Main Activity screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        repository = new Repository(getApplication());
        List<SessionEntity> allSessions = repository.getAllSessions();
        recyclerView = findViewById(R.id.recyclerview_reports);

        //Format string to local date time
//        formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MM/dd/yyyy").toFormatter(Locale.ENGLISH);
        nowTime = LocalDateTime.now();
        timePlusSeven = LocalDateTime.now().plusDays(7);
        timePlusThirty = LocalDateTime.now().plusDays(30);


        reportSessionList = new ArrayList<>();

        //View list of scheduled sessions
        try {
//            RecyclerView recyclerView = findViewById(R.id.recyclerview_reports);
            final ReportsAdapter reportsAdapter = new ReportsAdapter(this);
            recyclerView.setAdapter(reportsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            reportsAdapter.setmSessions(allSessions);
        } catch (Exception e) {
            Toast.makeText(ReportsActivity.this, "There are no sessions scheduled. ", Toast.LENGTH_LONG).show();
        }

        //Initialize Search field
        editSearch = findViewById(R.id.editTextSearch);
    }

    /**
     * Enables the back arrow to take the user back to the main activity.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //For the going back to main activity
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * checks if radio buttons are clicked. Formats and parses string into date-time format
     *
     * @param view
     */
    public void onWeekOrMonthClicked(View view) {

        //Checks if radio button week or month are enabled.
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonWeek:
                if (checked) {

                    try {
                        reportSessionList.clear();
                        for (SessionEntity s : repository.getAllSessions()) {
                            String dateString = s.getDate();
                            localDate = LocalDate.parse(dateString, formatter);

                            if (localDate.isAfter(ChronoLocalDate.from(nowTime)) && localDate.isBefore(ChronoLocalDate.from(timePlusSeven))) {
                                reportSessionList.add(s);
                            }
                        }
                        if (reportSessionList.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "No sessions found within the next 7 days.", Toast.LENGTH_LONG).show();
                            final ReportsAdapter adapter = new ReportsAdapter(this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            adapter.setmSessions(reportSessionList);
                        }
                        if (!reportSessionList.isEmpty()) {
                            final ReportsAdapter adapter = new ReportsAdapter(this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            adapter.setmSessions(reportSessionList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
            case R.id.radioButtonMonth:
                if (checked)

                    try {
                        reportSessionList.clear();
                        for (SessionEntity s : repository.getAllSessions()) {
                            String dateString = s.getDate();
                            localDate = LocalDate.parse(dateString, formatter);

                            if (localDate.isAfter(ChronoLocalDate.from(nowTime)) && localDate.isBefore(ChronoLocalDate.from(timePlusThirty))) {
                                reportSessionList.add(s);
                            }
                        }
                        if (reportSessionList.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "No sessions found within the next 30 days.", Toast.LENGTH_LONG).show();
                            final ReportsAdapter adapter = new ReportsAdapter(this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            adapter.setmSessions(reportSessionList);
                        }
                        if (!reportSessionList.isEmpty()) {
                            final ReportsAdapter adapter = new ReportsAdapter(this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            adapter.setmSessions(reportSessionList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                break;

        }

    }

    /**
     * The Find button is clicked. Looks for characters that partially match what's in sessions repository.
     * @param view
     */
    public void onFindClick(View view) {

        if (editSearch.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Input missing in 'Search' field.", Toast.LENGTH_LONG).show();

            repository = new Repository(getApplication());
            List<SessionEntity> allSessions = repository.getAllSessions();
            final ReportsAdapter reportsAdapter = new ReportsAdapter(this);
            recyclerView.setAdapter(reportsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            reportsAdapter.setmSessions(allSessions);

            return;
        }

        try {
            searchString = editSearch.getText().toString();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Nothing found", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            reportSessionList.clear();
            for (SessionEntity s : repository.getAllSessions()) {

                if (s.getSessionTitle().contains(searchString) || s.getSessionCustomer().contains(searchString)) {
                    reportSessionList.add(s);
                }
            }
            if (reportSessionList.isEmpty()) {
                Toast.makeText(getApplicationContext(), "No sessions found", Toast.LENGTH_LONG).show();
                reportSessionList.clear();
                final ReportsAdapter adapter = new ReportsAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setmSessions(reportSessionList);
                return;
            }
//
            final ReportsAdapter adapter = new ReportsAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setmSessions(reportSessionList);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
