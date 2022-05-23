package doc.student.fitnesstrainerapp.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import doc.student.fitnesstrainerapp.Adapters.ScheduledSessionAdapter;
import doc.student.fitnesstrainerapp.Database.Repository;
import doc.student.fitnesstrainerapp.Entities.SessionEntity;
import doc.student.fitnesstrainerapp.R;

public class ScheduledSessions extends AppCompatActivity {

    private Repository repository;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //Opens the scheduled sessions activity
    setContentView(R.layout.activity_scheduled_sessions);

        //Create a back arrow option for the Main Activity screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    repository = new Repository(getApplication());
        List<SessionEntity> allSessions = repository.getAllSessions();

        //View list of scheduled sessions
        try {
            RecyclerView recyclerView = findViewById(R.id.scheduledSessionsRecycler);
            final ScheduledSessionAdapter sessionAdapter = new ScheduledSessionAdapter(this);
            recyclerView.setAdapter(sessionAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            sessionAdapter.setmSessions(allSessions);
        }catch (Exception e) {
            Toast.makeText(ScheduledSessions.this, "There are no sessions scheduled. ", Toast.LENGTH_LONG).show();

        }

    }

    //Creates options menu in top-right corner
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_scheduled, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.view_reports:
                Intent intent = new Intent(ScheduledSessions.this, ReportsActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void onAddSession(View view) {
        Intent intent = new Intent(ScheduledSessions.this, SessionDetailsActivity.class);
        startActivity(intent);
    }

}
