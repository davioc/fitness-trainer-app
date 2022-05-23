package doc.student.fitnesstrainerapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import doc.student.fitnesstrainerapp.Entities.SessionEntity;
import doc.student.fitnesstrainerapp.R;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ReportsViewHolder> {

    class ReportsViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewCustomer;
        private final TextView textViewTrainer;
        private final TextView textViewDate;


        private ReportsViewHolder(View itemView){
            super(itemView);

            //Initialize list item in
            textViewTitle = itemView.findViewById(R.id.reports_view_title);
            textViewCustomer = itemView.findViewById(R.id.reports_view_customer);
            textViewTrainer = itemView.findViewById(R.id.reports_view_trainer);
            textViewDate = itemView.findViewById(R.id.reports_view_Date);


        }

    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<SessionEntity> mSessions;

    public ReportsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ReportsAdapter.ReportsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.list_reports, parent, false);

        return new ReportsAdapter.ReportsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ReportsAdapter.ReportsViewHolder holder, int position) {

        if (mSessions != null) {
            String startString;
            SessionEntity current = mSessions.get(position);
            holder.textViewTitle.setText(current.getSessionTitle());
            holder.textViewCustomer.setText(current.getSessionCustomer());
            holder.textViewTrainer.setText(current.getSessionTrainer());

            //Combines date and start time
            startString = current.getDate().concat(" " + current.getStartTime());

            holder.textViewDate.setText(startString);
//            holder.textViewDate.setText(current.getDate());
        } else {
            holder.textViewTitle.setText("No sessions have been created.");
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
