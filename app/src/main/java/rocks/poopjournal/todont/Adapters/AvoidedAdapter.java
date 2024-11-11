package rocks.poopjournal.todont.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import rocks.poopjournal.todont.Db_Controller;
import rocks.poopjournal.todont.Fragments.AvoidedFragment;
import rocks.poopjournal.todont.Helper;
import rocks.poopjournal.todont.R;


public class AvoidedAdapter extends RecyclerView.Adapter<AvoidedAdapter.RecyclerViewHolder> {
    private ArrayList<String> donotTask = new ArrayList<>();
    private ArrayList<String> donotCatagory = new ArrayList<>();
    private final ArrayList<String> updateddonotCatagory = new ArrayList<>();
    AvoidedFragment ft;
    Context con;
    Date c = Calendar.getInstance().getTime();
    String[] catagories;
    String catagoryselected = "";
    Db_Controller db;
    final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String formattedDate = df.format(c);

    public AvoidedAdapter(Context con, AvoidedFragment ft, Db_Controller db, ArrayList<String> donotTask, ArrayList<String> donotCatagory) {
        this.donotTask = donotTask;
        this.donotCatagory = donotCatagory;
        this.con = con;
        this.ft = ft;
        this.db = db;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recyclerview_layout, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        db.show_avoided_data();
        String dTask = donotTask.get(position);
        String date = Helper.avoidedData.get(position)[1];
        String tim = Helper.avoidedData.get(position)[4];
        holder.tim.setText(tim+ "-Times");
        holder.task.setText(dTask);
        holder.dateoftask.setText(date);


    }

    @Override
    public int getItemCount() {
        return donotTask.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        Button rbtn;
        TextView task,tim;
        TextView dateoftask;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            rbtn = itemView.findViewById(R.id.addToAvoided);
            task = itemView.findViewById(R.id.task);
            tim = itemView.findViewById(R.id.times);
            dateoftask = itemView.findViewById(R.id.dateoftask);
            rbtn.setBackgroundResource(R.drawable.ic_avoided);

        }

    }
}
