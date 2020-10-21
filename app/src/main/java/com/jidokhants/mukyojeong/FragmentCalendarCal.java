package com.jidokhants.mukyojeong;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentCalendarCal extends Fragment {
    TextView selectedDate;
    public static FragmentCalendarCal newInstance() {
        FragmentCalendarCal fragment = new FragmentCalendarCal();

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_cal, container, false);

        selectedDate = view.findViewById(R.id.cal_today_date);

        Date currentTime = Calendar.getInstance().getTime();
        selectedDate.setText(new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(currentTime));

        final CollapsibleCalendar collapsibleCalendar = view.findViewById(R.id.collapsibleCalendar2);
        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {


            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();
                Log.i(getClass().getName(), "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());
                selectedDate.setText(day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());
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

            public void onDayChanged() {

            }


            public void onClickListener() {

            }
        });

        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        for(int i = 0;i<10;i++) {
            list1.add(String.format("아침 식단 %d번 흠냐륑", i));
            list2.add(String.format("점심 식단 %d번 흠냐륑", i));
        }

        RecyclerView rv_breakfast = view.findViewById(R.id.rv_cal_breakfast);
        RecyclerView rv_lunch = view.findViewById(R.id.rv_cal_lunch);
        RecyclerView rv_dinner = view.findViewById(R.id.rv_cal_dinner);
        RecyclerView rv_snack = view.findViewById(R.id.rv_cal_snack);

        rv_breakfast.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_lunch.setLayoutManager(new LinearLayoutManager(getContext()));

        CalendarTextAdapter adapter1 = new CalendarTextAdapter(list1);
        CalendarTextAdapter adapter2 = new CalendarTextAdapter(list2);

        rv_breakfast.setAdapter(adapter1);
        rv_lunch.setAdapter(adapter2);
        ImageButton btn_add = view.findViewById(R.id.btn_add_cal);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceCalendarFragment(FragmentInsertDiet.newInstance());
            }
        });

        return view;
    }
}
