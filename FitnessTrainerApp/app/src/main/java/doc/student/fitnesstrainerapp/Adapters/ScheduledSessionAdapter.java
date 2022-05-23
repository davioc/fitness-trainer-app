package doc.student.fitnesstrainerapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import doc.student.fitnesstrainerapp.Activities.SessionDetailsActivity;
import doc.student.fitnesstrainerapp.Entities.SessionEntity;
import doc.student.fitnesstrainerapp.R;

public class ScheduledSessionAdapter extends RecyclerView.Adapter<ScheduledSessionAdapter.ScheduledSessionViewHolder> {

    class ScheduledSessionViewHolder extends RecyclerView.ViewHolder {
        private final TextView sessionItemView;

        private ScheduledSessionViewHolder(View itemView) {
            super(itemView);
            sessionItemView = itemView.findViewById(R.id.sessionItemTextView);

            //When item is clicked in the Session List activity, the information is displayed in the
            //UI
            itemView.setOnClickListener(new View.OnClickListener() {

                //Grabs the selected sessions data
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final SessionEntity selectedSession = mSessions.get(position);
                    Intent intent = new Intent(context, SessionDetailsActivity.class);
                    intent.putExtra("sessionID", selectedSession.getSessionID());
                    intent.putExtra("sessionTrainer", selectedSession.getSessionTrainer());
                    intent.putExtra("sessionCustomer", selectedSession.getSessionCustomer());
                    intent.putExtra("sessionTitle", selectedSession.getSessionTitle());
                    intent.putExtra("date", selectedSession.getDate());
                    intent.putExtra("startTime", selectedSession.getStartTime());
                    intent.putExtra("endTime", selectedSession.getEndTime());
                    context.startActivity(intent);

                }
            });

        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<SessionEntity> mSessions;

    public ScheduledSessionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ScheduledSessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.list_session_item, parent, false);

        return new ScheduledSessionViewHolder(itemView);

    }

    //Displays lists of sessions in Scheduled Session Activity

    @Override
    public void onBindViewHolder(ScheduledSessionViewHolder holder, int position) {

        if (mSessions != null) {
            SessionEntity current = mSessions.get(position);
            holder.sessionItemView.setText("Session ID: " + current.getSessionID() + "\n" +
                    "Session Title: " + current.getSessionTitle() + "\n" +
                    "Date: " + current.getDate() + "\n" +
                    "Start Time: " + current.getStartTime() + "\n" +
                    "End Time: " + current.getEndTime());
        } else {
            holder.sessionItemView.setText("No sessions have been created.");
        }

    }

    public void setmSessions(List<SessionEntity> words) {
        mSessions = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSessions != null)
            return mSessions.size();
        else return 0;
    }
}