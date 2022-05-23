package doc.student.fitnesstrainerapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import doc.student.fitnesstrainerapp.Activities.ScheduledSessions;
import doc.student.fitnesstrainerapp.Activities.SessionDetailsActivity;
import doc.student.fitnesstrainerapp.Activities.WorkoutDetailsActivity;
import doc.student.fitnesstrainerapp.Entities.WorkoutDetailsEntity;
import doc.student.fitnesstrainerapp.R;

public class WorkoutDetailsAdapter extends RecyclerView.Adapter<WorkoutDetailsAdapter.WorkoutDetailsViewHolder> {

    class WorkoutDetailsViewHolder extends RecyclerView.ViewHolder {
        private final TextView workoutDetailsItemView;

        private WorkoutDetailsViewHolder(View itemView) {
            super(itemView);
            workoutDetailsItemView = itemView.findViewById(R.id.workoutDetailsItemTextView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final WorkoutDetailsEntity selectedWorkoutDetails = mWorkoutDetails.get(position);
                    Intent intent = new Intent(context, WorkoutDetailsActivity.class);
                    intent.putExtra("workoutID", selectedWorkoutDetails.getWorkoutID());
                    intent.putExtra("workoutType", selectedWorkoutDetails.getWorkoutType());
                    intent.putExtra("sessID", selectedWorkoutDetails.getSessID());
                    intent.putExtra("fitnessLevel", selectedWorkoutDetails.getFitnessLevel());
                    intent.putExtra("duration", selectedWorkoutDetails.getDuration());
                    intent.putExtra("notes", selectedWorkoutDetails.getNotes());
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<WorkoutDetailsEntity> mWorkoutDetails;

    public WorkoutDetailsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public WorkoutDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_workout_details_item, parent, false);
        return new WorkoutDetailsAdapter.WorkoutDetailsViewHolder(itemView);

    }

    //Displays lists of sessions in Scheduled Session Activity

    @Override
    public void onBindViewHolder(WorkoutDetailsViewHolder holder, int position) {

        try {
            if (mWorkoutDetails != null) {
                WorkoutDetailsEntity current = mWorkoutDetails.get(position);
                holder.workoutDetailsItemView.setText(current.getWorkoutType() + " - " + current.getDuration() + " minutes");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void setmWorkoutDetails(List<WorkoutDetailsEntity> words) {
        mWorkoutDetails = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWorkoutDetails != null)
            return mWorkoutDetails.size();
        else return 0;
    }


}
