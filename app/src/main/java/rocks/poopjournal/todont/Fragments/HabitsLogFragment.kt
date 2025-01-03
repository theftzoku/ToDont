package rocks.poopjournal.todont.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import rocks.poopjournal.todont.Adapters.HabitsLogAdapter;
import rocks.poopjournal.todont.Db_Controller;
import rocks.poopjournal.todont.Helper;
import rocks.poopjournal.todont.R;


public class HabitsLogFragment extends Fragment {
    RecyclerView rv;
    ArrayList<String> gettingtasks = new ArrayList<>();
    ArrayList<String> gettingcatagory = new ArrayList<>();
    Db_Controller db;
    HabitsLogAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_log_habits, container, false);
        rv = view.findViewById(R.id.rv);
        db = new Db_Controller(getActivity(), "", null, 2);
        db.show_labels();
        db.show_habits_data();
        setDataInList();
        return view;

    }
    public void setDataInList() {
        for (int i = 0; i < Helper.habitsdata.size(); i++) {
            gettingtasks.add(Helper.habitsdata.get(i)[2]);
            gettingcatagory.add(Helper.habitsdata.get(i)[4]);
        }
       rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d("catttt",""+gettingcatagory);
        adapter= new HabitsLogAdapter(getActivity(), HabitsLogFragment.this,db,gettingtasks,gettingcatagory);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}