package doc.student.fitnesstrainerapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import doc.student.fitnesstrainerapp.Activities.WorkoutItemActivity;
import doc.student.fitnesstrainerapp.Entities.CardioTypeEntity;
import doc.student.fitnesstrainerapp.Entities.StrengthTypeEntity;
import doc.student.fitnesstrainerapp.R;

public class StrengthAdapter extends RecyclerView.Adapter<StrengthAdapter.StrengthItemViewHolder> {

    class StrengthItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView strengthItemView;

        private StrengthItemViewHolder(View itemView){
            super(itemView);
            strengthItemView = itemView.findViewById(R.id.workoutItemTextView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final StrengthTypeEntity selectedStrength = strengthTypeEntitylist.get(position);
                    Intent intent = new Intent(context, WorkoutItemActivity.class);
                    intent.putExtra("strengthID", selectedStrength.getStrengthID());
                    intent.putExtra("workoutID", selectedStrength.getWorkoutID());
                    intent.putExtra("name", selectedStrength.getName());
                    intent.putExtra("repetitionType", selectedStrength.getRepetitionType());
                    intent.putExtra("repetitionItem", selectedStrength.getRepetitionItem());
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<StrengthTypeEntity> strengthTypeEntitylist;

    public StrengthAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public StrengthAdapter.StrengthItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_workout_item, parent, false);
        return new StrengthAdapter.StrengthItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StrengthAdapter.StrengthItemViewHolder holder, int position) {

        try {

            if (strengthTypeEntitylist != null){
                StrengthTypeEntity current = strengthTypeEntitylist.get(position);
                holder.strengthItemView.setText(current.getName() + " - " + current.getRepetitionItem());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStrengthTypeEntityList(List<StrengthTypeEntity> words) {
        strengthTypeEntitylist = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (strengthTypeEntitylist != null)
            return strengthTypeEntitylist.size();
        else return 0;
    }


}
