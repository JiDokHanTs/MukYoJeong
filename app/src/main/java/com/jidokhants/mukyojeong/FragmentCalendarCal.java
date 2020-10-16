package com.jidokhants.mukyojeong;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.ArrayList;

public class FragmentCalendarCal extends Fragment {

    public static FragmentCalendarCal newInstance() {
        FragmentCalendarCal fragment = new FragmentCalendarCal();

        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_cal, container, false);

        final CollapsibleCalendar collapsibleCalendar = view.findViewById(R.id.collapsibleCalendar2);
        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();
                Log.i(getClass().getName(), "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());
            }

            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onWeekChange(int i) {

            }
        });

        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i<5;i++) {
            list.add(String.format("식단 %d번 흠냐륑", i));
        }

        RecyclerView rv_breakfast = view.findViewById(R.id.rv_cal_breakfast);
        RecyclerView rv_lunch = view.findViewById(R.id.rv_cal_lunch);
        RecyclerView rv_dinner = view.findViewById(R.id.rv_cal_dinner);
        RecyclerView rv_snack = view.findViewById(R.id.rv_cal_snack);

        rv_breakfast.setLayoutManager(new LinearLayoutManager(getContext()));

        CalendarTextAdapter adapter = new CalendarTextAdapter(list);
        rv_breakfast.setAdapter(adapter);

        return view;
    }
}
