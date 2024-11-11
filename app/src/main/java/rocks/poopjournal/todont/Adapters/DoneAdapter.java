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
import rocks.poopjournal.todont.Fragments.DoneFragment;
import rocks.poopjournal.todont.Helper;
import rocks.poopjournal.todont.R;


public class DoneAdapter extends RecyclerView.Adapter<DoneAdapter.RecyclerViewHolder> {
    private ArrayList<String> donotTask=new ArrayList<>();
    private ArrayList<String> donotCatagory=new ArrayList<>();
    DoneFragment ft;
    Context con;
    Date c = Calendar.getInstance().getTime();
    String[] catagories;
    String catagoryselected;
    Db_Controller db;
    final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    
    
    String formattedDate = df.format(c);

    public DoneAdapter(Context con, DoneFragment ft, Db_Controller db, ArrayList<String> donotTask, ArrayList<String> donotCatagory) {
        this.donotTask = donotTask;
        this.donotCatagory = donotCatagory;
        this.con = con;
        this.ft=ft;
        this.db=db;

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
        db.show_done_data();
        String dTask=donotTask.get(position);
        String date= Helper.donedata.get(position)[1];
        String time= Helper.donedata.get(position)[4];
//        holder.times.setText(time);
        holder.times.setText(time+ "-Times");

        holder.task.setText(dTask);
        holder.dateoftask.setText(date);

        //ft.setDataInList();

    }

    @Override
    public int getItemCount() {
        return donotTask.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        Button rbtn;
        TextView task;
        TextView dateoftask,times;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            rbtn =  itemView.findViewById(R.id.addToAvoided);
            task = itemView.findViewById(R.id.task);
            times = itemView.findViewById(R.id.times);
            dateoftask = itemView.findViewById(R.id.dateoftask);
            rbtn.setBackgroundResource(R.drawable.ic_done);
        }
    }
}
