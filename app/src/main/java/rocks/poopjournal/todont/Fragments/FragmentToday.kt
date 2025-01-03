package rocks.poopjournal.todont.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import rocks.poopjournal.todont.Helper;
import rocks.poopjournal.todont.R;

public class FragmentToday extends Fragment {
    Button avoided,done,habits;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_today2, container, false);
        Helper.isTodaySelected=true;
        avoided=view.findViewById(R.id.avoided);
        done=view.findViewById(R.id.done);
        habits=view.findViewById(R.id.habits);
        if(Helper.SelectedButtonOfTodayTab==0){
            habits.setBackgroundResource(R.drawable.continuebutton2);
            avoided.setBackgroundResource(R.drawable.continuebuttontrans);
            done.setBackgroundResource(R.drawable.continuebuttontrans);
            FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.containerTodayFragment,new HabitsFragment());
            ft.commit();
        }
        else if(Helper.SelectedButtonOfTodayTab==1){
            habits.setBackgroundResource(R.drawable.continuebuttontrans);
            avoided.setBackgroundResource(R.drawable.continuebutton2);
            done.setBackgroundResource(R.drawable.continuebuttontrans);
            FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.containerTodayFragment,new AvoidedFragment());
            ft.commit();
        }
        else if(Helper.SelectedButtonOfTodayTab==2){
            habits.setBackgroundResource(R.drawable.continuebuttontrans);
            avoided.setBackgroundResource(R.drawable.continuebuttontrans);
            done.setBackgroundResource(R.drawable.continuebutton2);
            FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.containerTodayFragment,new DoneFragment());
            ft.commit();
        }


        habits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habits.setBackgroundResource(R.drawable.continuebutton2);
                avoided.setBackgroundResource(R.drawable.continuebuttontrans);
                done.setBackgroundResource(R.drawable.continuebuttontrans);
                FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.containerTodayFragment,new HabitsFragment());
                ft.commit();


            }
        });
        avoided.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habits.setBackgroundResource(R.drawable.continuebuttontrans);
                avoided.setBackgroundResource(R.drawable.continuebutton2);
                done.setBackgroundResource(R.drawable.continuebuttontrans);
                FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.containerTodayFragment,new AvoidedFragment());
                ft.commit();


            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habits.setBackgroundResource(R.drawable.continuebuttontrans);
                avoided.setBackgroundResource(R.drawable.continuebuttontrans);
                done.setBackgroundResource(R.drawable.continuebutton2);
                FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.containerTodayFragment,new DoneFragment());
                ft.commit();

            }
        });
        return view;
    }



}