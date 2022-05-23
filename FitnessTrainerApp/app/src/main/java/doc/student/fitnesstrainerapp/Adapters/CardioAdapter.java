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
import doc.student.fitnesstrainerapp.R;

public class CardioAdapter extends RecyclerView.Adapter<CardioAdapter.CardioItemViewHolder> {

    class CardioItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView cardioItemView;

        private CardioItemViewHolder(View itemView){
            super(itemView);
            cardioItemView = itemView.findViewById(R.id.workoutItemTextView);
            itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                final CardioTypeEntity selectedCardio = cardioTypeEntitylist.get(position);
                Intent intent = new Intent(context, WorkoutItemActivity.class);
                intent.putExtra("cardioID", selectedCardio.getCardioID());
                intent.putExtra("workoutID", selectedCardio.getWorkoutID());
                intent.putExtra("name", selectedCardio.getName());
                intent.putExtra("repetitionType", selectedCardio.getRepetitionType());
                intent.putExtra("repetitionItem", selectedCardio.getRepetitionItem());
                context.startActivity(intent);
            }
        });
    }
}

    private final LayoutInflater mInflater;
    private final Context context;
    private List<CardioTypeEntity> cardioTypeEntitylist;

    public CardioAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CardioItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_workout_item, parent, false);
        return new CardioAdapter.CardioItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardioItemViewHolder holder, int position) {

        try {

            if (cardioTypeEntitylist != null){
                CardioTypeEntity current = cardioTypeEntitylist.get(position);
                holder.cardioItemView.setText(current.getName() + " - " + current.getRepetitionItem());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCardioTypeEntitylist(List<CardioTypeEntity> words) {
        cardioTypeEntitylist = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (cardioTypeEntitylist != null)
            return cardioTypeEntitylist.size();
        else return 0;
    }

}