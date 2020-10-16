package com.jidokhants.mukyojeong;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jidokhants.mukyojeong.model.Food;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FragmentCalendar2 extends Fragment {
    private MukDBHelper mukDBHelper;
    ArrayList<Food> foodList;

    public static FragmentCalendar2 newInstance() {
        FragmentCalendar2 fragment = new FragmentCalendar2();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calendar2, container, false);

        Button callFoodListButton = view.findViewById(R.id.food_list_call_button);
        mukDBHelper = MukDBHelper.getInstance(getContext());
        callFoodListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodList = mukDBHelper.getAllFood();
                String temp = "";
                for(int i = 0 ; i< 10; i++){
                    temp += foodList.get(i).getName()+", "+ foodList.get(i).getCalorie()+"\n";
                }
                TextView tvFoodList = view.findViewById(R.id.food_list_tv);
                tvFoodList.setText(temp);
            }
        });
        final CollapsibleCalendar collapsibleCalendar = view.findViewById(R.id.cal_view);
        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener(){

            @Override
            public void onDaySelect() {
                Log.d("onDaySelect", "called, " + collapsibleCalendar.getSelectedDay().toString());
            }

            @Override
            public void onWeekChange(int i) {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onItemClick(View view) {
                Log.d("onItemClick", "clicked, "+ view.toString());
            }



            public void onDayChanged() {

            }

            @Override
            public void onDataUpdate() {

            }


            public void onClickListener() {

            }
        });
        return view;
    }

}
