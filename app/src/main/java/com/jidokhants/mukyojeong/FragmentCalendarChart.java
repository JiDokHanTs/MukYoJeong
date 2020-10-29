package com.jidokhants.mukyojeong;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.jidokhants.mukyojeong.model.Food;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.github.mikephil.charting.charts.RadarChart;

public class FragmentCalendarChart extends Fragment implements View.OnClickListener {
    public static final String TAG = "CAL_CHART";
    private MukDBHelper mukDBHelper;

    Calendar myCalendar = Calendar.getInstance();

    RadarChart radarChart;
    LinearLayout menuLayout;
    ConstraintLayout chartLayout;

    TextView tvCal, tvCab, tvPro, tvFat, tvMo, tvVD, tvVC, tvFiber, tvFe, tvSalt;
    EditText edit_from, edit_to;
    String raw_date_from, raw_date_to, date_from, date_to;
    SimpleDateFormat sdf_string = new SimpleDateFormat("yyyyMMdd");

    TextView[] menu_name = new TextView[3];
    TextView[] menu_info = new TextView[3];

    ScrollView chartScrollView;

    Button analysisButton;

    ArrayList<Food> recommendFoodResult;
    TextView tvRecommends;
    DatePickerDialog.OnDateSetListener myDatePicker1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONDAY, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }
    };

    DatePickerDialog.OnDateSetListener myDatePicker2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONDAY, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        }
    };

    private void updateLabel1() {
        String myFormat = "yyyy.MM.dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        edit_from.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "yyyy.MM.dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        edit_to.setText(sdf.format(myCalendar.getTime()));
    }

    public static FragmentCalendarChart newInstance() {
        FragmentCalendarChart fragment = new FragmentCalendarChart();

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_chart, container, false);
        Log.d(TAG, "onCreateView Called");
        edit_from = view.findViewById(R.id.edit_from);
        edit_to = view.findViewById(R.id.edit_to);

        Date time = new Date();
        String today = (new SimpleDateFormat("yyyy.MM.dd")).format(time);
        String before = sdf_string.format(time);
        before = ((Integer.parseInt(before)) - 6) + "";

        Date date_before = null;
        try {
            date_before = sdf_string.parse(before);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        before = (new SimpleDateFormat("yyyy.MM.dd")).format(date_before);
        edit_from.setText(before);
        edit_to.setText(today);

        mukDBHelper = MukDBHelper.getInstance(getContext());

        tvCal = view.findViewById(R.id.tv_cal);
        tvCab = view.findViewById(R.id.tv_carbo);
        tvPro = view.findViewById(R.id.tv_pro);
        tvFat = view.findViewById(R.id.tv_fat);
        tvMo = view.findViewById(R.id.tv_mo);
        tvVD = view.findViewById(R.id.tv_v_d);
        tvVC = view.findViewById(R.id.tv_v_c);
        tvFiber = view.findViewById(R.id.tv_fiber);
        tvFe = view.findViewById(R.id.tv_fe);
        tvSalt = view.findViewById(R.id.tv_salt);

//        tvRecommends = view.findViewById(R.id.tv_recommend_foods);

        analysisButton = view.findViewById(R.id.btn_chk_analysis);
        analysisButton.setOnClickListener(this);

        edit_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), myDatePicker1,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edit_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), myDatePicker2,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        chartScrollView = view.findViewById(R.id.chart_scrollview);
        chartScrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        chartScrollView.fullScroll(View.FOCUS_DOWN);
                        break;
                    case MotionEvent.ACTION_UP:
                        chartScrollView.fullScroll(View.FOCUS_UP);
                        break;
                }
                return false;
            }
        });

        radarChart = view.findViewById(R.id.radarChart);

        ArrayList<RadarEntry> values = new ArrayList<>();
        values.add(new RadarEntry(0));
        values.add(new RadarEntry(0));
        values.add(new RadarEntry(0));
        values.add(new RadarEntry(0));
        values.add(new RadarEntry(0));

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

        menu_name[0] = view.findViewById(R.id.menu_name1);
        menu_name[1] = view.findViewById(R.id.menu_name2);
        menu_name[2] = view.findViewById(R.id.menu_name3);
        menu_info[0] = view.findViewById(R.id.menu_info1);
        menu_info[1] = view.findViewById(R.id.menu_info2);
        menu_info[2] = view.findViewById(R.id.menu_info3);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chk_analysis:
                analysisNutrient();
        }
    }

    public void analysisNutrient() {

        raw_date_from = edit_from.getText().toString();
        raw_date_to = edit_to.getText().toString();
        date_from = raw_date_from.replaceAll("[.]", "");
        date_to = raw_date_to.replaceAll("[.]", "");

        Food analysisResult = mukDBHelper.weekRecord(date_from, date_to);

        Food recommendStart = new Food(-1);
        Food recommendEnd = new Food(-2);

        String[] states = new String[]{"under", "", "over"};
        int stateIndex;

        if (!Double.isNaN(analysisResult.getCalorie())) {

            tvCal.setTextColor(Color.BLACK);
            tvCab.setTextColor(Color.BLACK);
            tvPro.setTextColor(Color.BLACK);
            tvFat.setTextColor(Color.BLACK);
            tvMo.setTextColor(Color.BLACK);
            tvVD.setTextColor(Color.BLACK);
            tvVC.setTextColor(Color.BLACK);
            tvFiber.setTextColor(Color.BLACK);
            tvFe.setTextColor(Color.BLACK);
            tvSalt.setTextColor(Color.BLACK);

            if (analysisResult.getCalorie() < Standard.calorie * 0.7) {
                stateIndex = 0;
                recommendStart.setCalorie(Standard.calorie * 0.7 - analysisResult.getCalorie());
                recommendEnd.setCalorie(Standard.calorie * 1.2 - analysisResult.getCalorie());
                tvCal.setTextColor(Color.RED);
            } else if (analysisResult.getCalorie() < Standard.calorie * 1.2) {
                stateIndex = 1;
                recommendStart.setCalorie(0);
                recommendEnd.setCalorie(Standard.calorie * 1.2 - analysisResult.getCalorie());
            } else {
                stateIndex = 2;
                recommendStart.setCalorie(0);
                recommendEnd.setCalorie(0);
                tvCal.setTextColor(Color.RED);
            }
            tvCal.setText("칼로리: " + Math.round(analysisResult.getCalorie()) + " kcal (" + (int) (analysisResult.getCalorie() / Standard.calorie * 100) + "%) " + states[stateIndex]);

            if (analysisResult.getCarbohydrate() < Standard.carbohydrate * 0.55) {
                stateIndex = 0;
                recommendStart.setCarbohydrate(Standard.carbohydrate * 0.55 - analysisResult.getCarbohydrate());
                recommendEnd.setCarbohydrate(Standard.carbohydrate * 1.2 - analysisResult.getCarbohydrate());
                tvCab.setTextColor(Color.RED);
            } else if (analysisResult.getCarbohydrate() < Standard.carbohydrate * 1.2) {
                stateIndex = 1;
                recommendStart.setCarbohydrate(0);
                recommendEnd.setCarbohydrate(Standard.carbohydrate * 1.2 - analysisResult.getCarbohydrate());
            } else {
                stateIndex = 2;
                recommendStart.setCarbohydrate(0);
                recommendEnd.setCarbohydrate(0);
                tvCab.setTextColor(Color.RED);
            }
            tvCab.setText("탄수회물: " + Math.round(analysisResult.getCarbohydrate()) + " g (" + (int) (analysisResult.getCarbohydrate() / Standard.carbohydrate * 100) + "%) " + states[stateIndex]);

            if (analysisResult.getProtein() < Standard.protein * 0.7) {
                stateIndex = 0;
                recommendStart.setProtein(Standard.protein * 0.7 - analysisResult.getProtein());
                recommendEnd.setProtein(Standard.protein * 1.2 - analysisResult.getProtein());
                tvPro.setTextColor(Color.RED);
            } else if (analysisResult.getProtein() < Standard.protein * 1.2) {
                stateIndex = 1;
                recommendStart.setProtein(0);
                recommendEnd.setProtein(Standard.protein * 1.2 - analysisResult.getProtein());
            } else {
                stateIndex = 2;
                recommendStart.setProtein(0);
                recommendEnd.setProtein(0);
                tvPro.setTextColor(Color.RED);
            }
            tvPro.setText("단백질: " + Math.round(analysisResult.getProtein()) + " g (" + (int) (analysisResult.getProtein() / Standard.protein * 100) + "%) " + states[stateIndex]);

            if (analysisResult.getFat() < Standard.fat * 0.7) {
                stateIndex = 0;
                recommendStart.setFat(Standard.fat * 0.7 - analysisResult.getFat());
                recommendEnd.setFat(Standard.fat * 1.2 - analysisResult.getFat());
                tvFat.setTextColor(Color.RED);
            } else if (analysisResult.getFat() < Standard.fat * 1.2) {
                stateIndex = 1;
                recommendStart.setFat(0);
                recommendEnd.setFat(Standard.fat * 1.2 - analysisResult.getFat());
            } else {
                stateIndex = 2;
                recommendStart.setFat(0);
                recommendEnd.setFat(0);
                tvFat.setTextColor(Color.RED);
            }
            tvFat.setText("지방: " + Math.round(analysisResult.getFat()) + " g (" + (int) (analysisResult.getFat() / Standard.fat * 100) + "%) " + states[stateIndex]);

            if (analysisResult.getMoisture() < Standard.moisture * 0.7) {
                stateIndex = 0;
                recommendStart.setMoisture(Standard.moisture * 0.7 - analysisResult.getMoisture());
                recommendEnd.setMoisture(Standard.moisture * 1.2 - analysisResult.getMoisture());
                tvMo.setTextColor(Color.RED);
            } else if (analysisResult.getMoisture() < Standard.moisture * 1.2) {
                stateIndex = 1;
                recommendStart.setMoisture(0);
                recommendEnd.setMoisture(Standard.moisture * 1.2 - analysisResult.getMoisture());
            } else {
                stateIndex = 2;
                recommendStart.setMoisture(0);
                recommendEnd.setMoisture(0);
                tvMo.setTextColor(Color.RED);
            }
            tvMo.setText("수분: " + Math.round(analysisResult.getMoisture())+ " ml (" + (int) (analysisResult.getMoisture() / Standard.moisture * 100) + "%) " + states[stateIndex]);

            if (analysisResult.getVitaminD() < Standard.vitaminD * 0.7) {
                stateIndex = 0;
                recommendStart.setMoisture(Standard.vitaminD * 0.7 - analysisResult.getVitaminD());
                recommendEnd.setMoisture(Standard.vitaminD * 1.2 - analysisResult.getVitaminD());
                tvVD.setTextColor(Color.RED);
            } else if (analysisResult.getVitaminD() < Standard.vitaminD * 1.2) {
                stateIndex = 1;
                recommendStart.setMoisture(0);
                recommendEnd.setMoisture(Standard.vitaminD * 1.2 - analysisResult.getVitaminD());
            } else {
                stateIndex = 2;
                recommendStart.setMoisture(0);
                recommendEnd.setMoisture(0);
                tvVD.setTextColor(Color.RED);
            }
            tvVD.setText("비타민D: " + Math.round(analysisResult.getVitaminD()) + " mmg (" + (int) (analysisResult.getVitaminD() / Standard.vitaminD * 100) + "%) " + states[stateIndex]);

            if (analysisResult.getVitaminC() < Standard.vitaminC * 0.7) {
                stateIndex = 0;
                recommendStart.setVitaminC(Standard.vitaminC * 0.7 - analysisResult.getVitaminC());
                recommendEnd.setVitaminC(Standard.vitaminC * 1.2 - analysisResult.getVitaminC());
                tvVC.setTextColor(Color.RED);
            } else if (analysisResult.getVitaminC() < Standard.vitaminC * 1.2) {
                stateIndex = 1;
                recommendStart.setVitaminC(0);
                recommendEnd.setVitaminC(Standard.vitaminC * 1.2 - analysisResult.getVitaminC());
            } else {
                stateIndex = 2;
                recommendStart.setVitaminC(0);
                recommendEnd.setVitaminC(0);
                tvVC.setTextColor(Color.RED);
            }
            tvVC.setText("비타민C: " + Math.round(analysisResult.getVitaminC()) + " mmg (" + (int) (analysisResult.getVitaminC() / Standard.vitaminC * 100) + "%) " + states[stateIndex]);

            if (analysisResult.getFiber() < Standard.fiber * 0.7) {
                stateIndex = 0;
                recommendStart.setFiber(Standard.fiber * 0.7 - analysisResult.getFiber());
                recommendEnd.setFiber(Standard.fiber * 1.2 - analysisResult.getFiber());
                tvFiber.setTextColor(Color.RED);
            } else if (analysisResult.getFiber() < Standard.fiber * 1.2) {
                stateIndex = 1;
                recommendStart.setFiber(0);
                recommendEnd.setFiber(Standard.fiber * 1.2 - analysisResult.getFiber());
            } else {
                stateIndex = 2;
                recommendStart.setFiber(0);
                recommendEnd.setFiber(0);
                tvFiber.setTextColor(Color.RED);
            }
            tvFiber.setText("식이섬유: " + Math.round(analysisResult.getFiber()) + " g (" + (int) (analysisResult.getFiber() / Standard.fiber * 100) + "%) " + states[stateIndex]);

            if (analysisResult.getFe() < Standard.fe * 0.7) {
                stateIndex = 0;
                recommendStart.setFe(Standard.fe * 0.7 - analysisResult.getFe());
                recommendEnd.setFe(Standard.fe * 1.2 - analysisResult.getFe());
                tvFe.setTextColor(Color.RED);
            } else if (analysisResult.getFe() < Standard.fe * 1.2) {
                stateIndex = 1;
                recommendStart.setFe(0);
                recommendEnd.setFe(Standard.fe * 1.2 - analysisResult.getFe());
            } else {
                stateIndex = 2;
                recommendStart.setFe(0);
                recommendEnd.setFe(0);
                tvFe.setTextColor(Color.RED);
            }
            tvFe.setText("철분: " + Math.round(analysisResult.getFe()) + " mmg (" + (int) (analysisResult.getFe() / Standard.fe * 100) + "%) " + states[stateIndex]);

            if (analysisResult.getSalt() < Standard.salt * 0.7) {
                stateIndex = 0;
                recommendStart.setSalt(Standard.salt * 0.7 - analysisResult.getSalt());
                recommendEnd.setSalt(Standard.salt * 1.2 - analysisResult.getSalt());
                tvSalt.setTextColor(Color.RED);
            } else if (analysisResult.getSalt() < Standard.salt * 1.2) {
                stateIndex = 1;
                recommendStart.setSalt(0);
                recommendEnd.setSalt(Standard.salt * 1.2 - analysisResult.getSalt());
            } else {
                stateIndex = 2;
                recommendStart.setSalt(0);
                recommendEnd.setSalt(0);
                tvSalt.setTextColor(Color.RED);
            }
            tvSalt.setText("나트륨: " + Math.round(analysisResult.getSalt()) + " g (" + (int) (analysisResult.getSalt() / Standard.salt * 100) + "%) " + states[stateIndex]);

            radarChart.clear();
            radarChart.invalidate();

            ArrayList<RadarEntry> values = new ArrayList<>();

            values.add(new RadarEntry((float) (analysisResult.getCalorie() / Standard.calorie * 100)));
            values.add(new RadarEntry((float) (analysisResult.getCarbohydrate() / Standard.carbohydrate * 100)));
            values.add(new RadarEntry((float) (analysisResult.getProtein() / Standard.protein * 100)));
            values.add(new RadarEntry((float) (analysisResult.getFat() / Standard.fat * 100)));
            values.add(new RadarEntry((float) (analysisResult.getMoisture() / Standard.moisture * 100)));

            RadarDataSet dataSet = new RadarDataSet(values, null);
            dataSet.setDrawFilled(true);

            RadarData radarData = new RadarData();
            radarData.addDataSet(dataSet);
            radarData.setValueTextSize(8f);
            XAxis xAxis = radarChart.getXAxis();
            YAxis yAxis = radarChart.getYAxis();
            yAxis.setAxisMinimum(0);
            yAxis.setAxisMaximum(120f);
            radarChart.setData(radarData);
            radarData.notifyDataChanged();

        } else {
            Toast.makeText(getActivity(), "날짜를 다시 선택해주세요.", Toast.LENGTH_SHORT).show();
        }

        recommendFoods(recommendStart, recommendEnd);
    }
    public void recommendFoods(Food start, Food end){
        recommendFoodResult = mukDBHelper.getRecommendFood(start, end);
        String temp = "";
        if (recommendFoodResult.size()!=0){
            for (int i =0;i<recommendFoodResult.size();i++){
                temp = recommendFoodResult.get(i).getName();
                menu_name[i].setText(temp);
                menu_name[i].setVisibility(View.VISIBLE);

                //temp+=food.getName() + "\n";
            }
        }else {
            temp= "추천하는 음식이 없습니다.";
            menu_name[0].setText(temp);
            menu_name[0].setVisibility(View.VISIBLE);
        }
//        tvRecommends.setText(temp);

    }
}
