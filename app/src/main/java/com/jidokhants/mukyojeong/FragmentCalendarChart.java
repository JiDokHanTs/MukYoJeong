package com.jidokhants.mukyojeong;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.jidokhants.mukyojeong.model.Food;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.github.mikephil.charting.charts.RadarChart;

public class FragmentCalendarChart extends Fragment {
    public static final String TAG = "CAL_CHART";
    private MukDBHelper mukDBHelper;

    RadarChart radarChart;
    TextView textView;

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
        Log.d(TAG, "칼로리: " + weekResult.getCalorie() + "kcal");
        Log.d(TAG, "탄수회물: " + weekResult.getCarbohydrate() + "g");
        Log.d(TAG, "단백질: " + weekResult.getProtein() + "g");
        Log.d(TAG, "지방: " + weekResult.getFat() + "g");
        Log.d(TAG, "수분: " + weekResult.getMoisture() + "ml");
        radarChart = view.findViewById(R.id.radarChart);

        ArrayList<RadarEntry> values = new ArrayList<>();
        values.add(new RadarEntry((float)(weekResult.getCalorie()/Standard.calorie*100)));
        values.add(new RadarEntry((float)(weekResult.getCarbohydrate()/Standard.carbohydrate*100)));
        values.add(new RadarEntry((float)(weekResult.getProtein()/Standard.protein*100)));
        values.add(new RadarEntry((float)(weekResult.getFat()/Standard.fat*100)));
        values.add(new RadarEntry((float)(weekResult.getMoisture()/Standard.moisture*100)));

        RadarDataSet dataSet = new RadarDataSet(values, null);
        dataSet.setDrawFilled(true);

        RadarData radarData = new RadarData();
        radarData.addDataSet(dataSet);
        radarData.setValueTextSize(8f);
        String[] labels = {"칼로리", "탄수화물", "단백질", "지방", "수분"};
        XAxis xAxis = radarChart.getXAxis();
        YAxis yAxis = radarChart.getYAxis();
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(120f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        radarChart.setData(radarData);

        return view;
    }
}
