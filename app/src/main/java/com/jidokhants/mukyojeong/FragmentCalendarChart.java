package com.jidokhants.mukyojeong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jidokhants.mukyojeong.model.Food;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.mikephil.charting.charts.RadarChart;

public class FragmentCalendarChart extends Fragment {
    public static final String TAG = "CAL_CHART";
    private MukDBHelper mukDBHelper;

    RadarChart radarChart;

    public static FragmentCalendarChart newInstance() {
        FragmentCalendarChart fragment = new FragmentCalendarChart();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_chart, container, false);

        mukDBHelper = MukDBHelper.getInstance(getContext());

        Date time = new Date();
        String today = (new SimpleDateFormat("yyyyMMdd")).format(time);
        Food weekResult = mukDBHelper.weekRecord(today);
        Log.d(TAG, "칼로리: "+ weekResult.getCalorie()+"kcal");
        Log.d(TAG, "탄수회물: "+ weekResult.getCarbohydrate()+"g");
        Log.d(TAG, "단백질: "+ weekResult.getProtein()+"g");
        Log.d(TAG, "지방: "+ weekResult.getFat()+"g");
        Log.d(TAG, "수분: "+ weekResult.getMoisture()+"g");
        radarChart = view.findViewById(R.id.radarChart);



        return view;
    }
}
