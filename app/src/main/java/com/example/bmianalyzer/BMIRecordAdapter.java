package com.example.bmianalyzer;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class BMIRecordAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<BMIRecord> records ;
    private Context context ;
    private User user ;

    public BMIRecordAdapter(User user, Context context) {
        this.user=user;
     if (user!=null)  records = user.getRecords();
        this.context = context;

    }

    public ArrayList<BMIRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<BMIRecord> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        return new BMIRecordHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            BMIRecord bmiRecord = records.get(position);
            BMIRecordHolder bmiRecordHolder = (BMIRecordHolder)holder ;
            bmiRecordHolder.date.setText(bmiRecord.getDate());
            bmiRecordHolder.length.setText(String.format("%s", bmiRecord.getLength()));
            bmiRecordHolder.weight.setText(String.format("%s", bmiRecord.getWeight()));
            int status= BMIRecord.toStringStatus(bmiRecord.getStatus(user.agePercentage()));
            bmiRecordHolder.status.setText(context.getString(status));

    }

    @Override
    public int getItemCount() {
        return records.size();
    }



    static class BMIRecordHolder extends RecyclerView.ViewHolder{

        TextView date , length , weight , status ;
        public BMIRecordHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.record_date);
            length = itemView.findViewById(R.id.record_length);
            weight = itemView.findViewById(R.id.record_weight);
            status = itemView.findViewById(R.id.record_status);

        }
    }
}
