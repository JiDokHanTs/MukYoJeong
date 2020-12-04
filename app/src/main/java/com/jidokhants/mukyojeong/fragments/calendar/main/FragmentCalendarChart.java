package com.jidokhants.mukyojeong.fragments.calendar.main;

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
import android.widget.ImageView;
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
import com.jidokhants.mukyojeong.data.MukDBHelper;
import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.etc.Standard;
import com.jidokhants.mukyojeong.model.Food;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    TextView tvNeedNutrient;
    TextView[] menu_name = new TextView[3];
    TextView[] menu_info = new TextView[3];

    ScrollView chartScrollView;

    Button analysisButton;

    ArrayList<Food> recommendFoodResult;
    TextView tvRecommends;

    String lackNutrient = "";

    ImageView bestFoodImage;

    final String[] icon_meat = {"육류구이",
            "육류튀김",
            "육류국.탕",
            "육류볶음",
            "육류찌개.전골",
            "육류전",
            "육류조림",
            "육류찜",
            "육류",
            "치킨류",
            "난류"
    };
    final String[] icon_vege_fruit = {
            "채소류구이",
            "채소류국.탕",
            "나물.숙채류",
            "나물.채소류무침",
            "기타 생채.무침류",
            "장아찌.절임류",
            "채소류전",
            "부침류",
            "채소류찌개.전골",
            "채소류튀김",
            "채소류찜",
            "만두류",
            "기타 면 및 만두류",
            "샐러드",
            "곡류 및 그 제품",
            "감자 및 전분류",
            "채소류",
            "버섯류",
            "과실류",
            "곡류 및 서류",
            "김치",
            "채소류볶음",
            "채소류조림"
    };
    final String[] icon_rice = {
            "김밥(초밥)류",
            "기타 밥류",
            "덮밥류",
            "비빔밥류",
            "볶음밥류",
            "쌀밥.잡곡밥류",
            "죽류",
            "떡볶이류",
            "스프류",
            "떡류"
    };
    final String[] icon_seafood = {
            "어패류구이",
            "어패류국.탕",
            "회류",
            "어패류찜",
            "어패류무침",
            "어패류볶음",
            "어패류전",
            "어패류조림",
            "어패류찌개.전골",
            "어패류튀김",
            "젓갈류",
            "어패류 및 기타 수산물",
            "해조류",
            "어류",
            "수산가공품",
            "패류",
            "갑각류",
    };
    final String[] icon_noodle = {
            "중식면류",
            "라면류",
            "국수류",
            "스파게티류"
    };
    final String[] icon_bread = {
            "앙금빵류",
            "크림빵류",
            "피자류",
            "샌드위치류",
            "튀김빵류(도넛, 꽈배기 등)",
            "케이크류",
            "페이스트리류",
            "기타 빵류",
            "식빵류",
            "버거류"
    };
    final String[] icon_snack = {
            "한과류",
            "빙수류",
            "견과류 및 종실류"
    };
    final String[] icon_milk = {
            "우유 및 유제품류",
            "유지류",
            "차류",
            "음료류",
            "주류",
            "기타 음료 및 차류"
    };
    final String[] icon_food = {
            "타 국 및 탕류",
            "기타 볶음류",
            "기타",
            "적류",
            "포류",
            "냉국류",
            "기타 전.적 및 부침류",
            "기타 조림류",
            "기타 찜류",
            "기타 튀김류",
            "두족류",
            "당류",
            "두류",
            "조미료류",
            "조리가공품류"
    };

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

        tvNeedNutrient = view.findViewById(R.id.tv_need_nutrient);

        bestFoodImage = view.findViewById(R.id.img_bestmenu);
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

        int minColumnIndex = 1111;
        int maxColumnIndex = -1;

        int minValue = 99999;
        int maxValue = -1;

        double rangeMax = 0;
        int tempValue = 0;

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
                tvCal.setTextColor(Color.RED);
            } else if (analysisResult.getCalorie() < Standard.calorie * 1.2) {
                stateIndex = 1;
            } else {
                stateIndex = 2;
                tvCal.setTextColor(Color.RED);
            }
            tempValue = (int) (analysisResult.getCalorie() / Standard.calorie * 100);
            if (tempValue < minValue) {
                minValue = tempValue;
                minColumnIndex = 0;
                rangeMax = Standard.calorie * 1.2 - analysisResult.getCalorie();
                lackNutrient = "칼로리";
            } else if (tempValue > maxValue) {
                maxValue = tempValue;
                maxColumnIndex = 0;
            }
            tvCal.setText("칼로리: " + Math.round(analysisResult.getCalorie()) + " kcal (" + tempValue + "%) " + states[stateIndex]);

            if (analysisResult.getCarbohydrate() < Standard.carbohydrate * 0.55) {
                stateIndex = 0;
                tvCab.setTextColor(Color.RED);
            } else if (analysisResult.getCarbohydrate() < Standard.carbohydrate * 1.2) {
                stateIndex = 1;
            } else {
                stateIndex = 2;
                tvCab.setTextColor(Color.RED);
            }
            tempValue = (int) (analysisResult.getCarbohydrate() / Standard.carbohydrate * 100);
            if (tempValue < minValue) {
                minValue = tempValue;
                minColumnIndex = 1;
                rangeMax = Standard.carbohydrate * 1.2 - analysisResult.getCarbohydrate();
                lackNutrient = "탄수화물";
            } else if (tempValue > maxValue) {
                maxValue = tempValue;
                maxColumnIndex = 1;
            }
            tvCab.setText("탄수화물: " + Math.round(analysisResult.getCarbohydrate()) + " g (" + tempValue + "%) " + states[stateIndex]);

            if (analysisResult.getProtein() < Standard.protein * 0.7) {
                stateIndex = 0;
                tvPro.setTextColor(Color.RED);
            } else if (analysisResult.getProtein() < Standard.protein * 1.2) {
                stateIndex = 1;
            } else {
                stateIndex = 2;
                tvPro.setTextColor(Color.RED);
            }
            tempValue = (int) (analysisResult.getProtein() / Standard.protein * 100);
            if (tempValue < minValue) {
                minValue = tempValue;
                minColumnIndex = 2;
                rangeMax = Standard.protein * 1.2 - analysisResult.getProtein();
                lackNutrient = "단백질";
            } else if (tempValue > maxValue) {
                maxValue = tempValue;
                maxColumnIndex = 2;
            }
            tvPro.setText("단백질: " + Math.round(analysisResult.getProtein()) + " g (" + tempValue + "%) " + states[stateIndex]);

            if (analysisResult.getFat() < Standard.fat * 0.7) {
                stateIndex = 0;
                tvFat.setTextColor(Color.RED);
            } else if (analysisResult.getFat() < Standard.fat * 1.2) {
                stateIndex = 1;
            } else {
                stateIndex = 2;
                tvFat.setTextColor(Color.RED);
            }
            tempValue = (int) (analysisResult.getFat() / Standard.fat * 100);
            if (tempValue < minValue) {
                minValue = tempValue;
                minColumnIndex = 3;
                rangeMax = Standard.fat * 1.2 - analysisResult.getFat();
                lackNutrient = "지방";
            } else if (tempValue > maxValue) {
                maxValue = tempValue;
                maxColumnIndex = 3;
            }
            tvFat.setText("지방: " + Math.round(analysisResult.getFat()) + " g (" + tempValue + "%) " + states[stateIndex]);

            if (analysisResult.getMoisture() < Standard.moisture * 0.7) {
                stateIndex = 0;
                tvMo.setTextColor(Color.RED);
            } else if (analysisResult.getMoisture() < Standard.moisture * 1.2) {
                stateIndex = 1;
            } else {
                stateIndex = 2;
                tvMo.setTextColor(Color.RED);
            }
//            tempValue = (int) (analysisResult.getMoisture() / Standard.moisture * 100);
//            if (tempValue < minValue) {
//                minValue = tempValue;
//                minColumnIndex = 4;
//                rangeMax = Standard.moisture * 1.2 - analysisResult.getMoisture();
//                lackNutrient = "수분";
//            } else if (tempValue > maxValue) {
//                maxValue = tempValue;
//                maxColumnIndex = 4;
//            }
            tvMo.setText("수분: " + Math.round(analysisResult.getMoisture()) + " ml (" + tempValue + "%) " + states[stateIndex]);

            if (analysisResult.getVitaminK() < Standard.vitaminD * 0.7) {
                stateIndex = 0;
                tvVD.setTextColor(Color.RED);
            } else if (analysisResult.getVitaminK() < Standard.vitaminD * 1.2) {
                stateIndex = 1;
            } else {
                stateIndex = 2;
                tvVD.setTextColor(Color.RED);
            }
            tempValue = (int) (analysisResult.getVitaminK() / Standard.vitaminD * 100);
            if (tempValue < minValue) {
                minValue = tempValue;
                minColumnIndex = 5;
                rangeMax = Standard.vitaminD * 1.2 - analysisResult.getVitaminK();
                lackNutrient = "비타민K";
            } else if (tempValue > maxValue) {
                maxValue = tempValue;
                maxColumnIndex = 5;
            }
            tvVD.setText("비타민K: " + Math.round(analysisResult.getVitaminK()) + " ㎍ (" + tempValue + "%) " + states[stateIndex]);

            if (analysisResult.getVitaminC() < Standard.vitaminC * 0.7) {
                stateIndex = 0;
                tvVC.setTextColor(Color.RED);
            } else if (analysisResult.getVitaminC() < Standard.vitaminC * 1.2) {
                stateIndex = 1;

            } else {
                stateIndex = 2;
                tvVC.setTextColor(Color.RED);
            }
            tempValue = (int) (analysisResult.getVitaminC() / Standard.vitaminC * 100);
            if (tempValue < minValue) {
                minValue = tempValue;
                minColumnIndex = 6;
                rangeMax = Standard.vitaminC * 1.2 - analysisResult.getVitaminC();
                lackNutrient = "비타민C";

            } else if (tempValue > maxValue) {
                maxValue = tempValue;
                maxColumnIndex = 6;
            }
            tvVC.setText("비타민C: " + Math.round(analysisResult.getVitaminC()) + " ㎍ (" + tempValue + "%) " + states[stateIndex]);

            if (analysisResult.getFiber() < Standard.fiber * 0.7) {
                stateIndex = 0;
                tvFiber.setTextColor(Color.RED);
            } else if (analysisResult.getFiber() < Standard.fiber * 1.2) {
                stateIndex = 1;
            } else {
                stateIndex = 2;
                tvFiber.setTextColor(Color.RED);
            }
            tempValue = (int) (analysisResult.getFiber() / Standard.fiber * 100);
            if (tempValue < minValue) {
                minValue = tempValue;
                minColumnIndex = 7;
                rangeMax = Standard.fiber * 1.2 - analysisResult.getFiber();
                lackNutrient = "식이섬유";
            } else if (tempValue > maxValue) {
                maxValue = tempValue;
                maxColumnIndex = 7;
            }
            tvFiber.setText("식이섬유: " + Math.round(analysisResult.getFiber()) + " g (" + tempValue + "%) " + states[stateIndex]);

            if (analysisResult.getFe() < Standard.fe * 0.7) {
                stateIndex = 0;
                tvFe.setTextColor(Color.RED);
            } else if (analysisResult.getFe() < Standard.fe * 1.2) {
                stateIndex = 1;
            } else {
                stateIndex = 2;
                tvFe.setTextColor(Color.RED);
            }
            tempValue = (int) (analysisResult.getFe() / Standard.fe * 100);
            if (tempValue < minValue) {
                minValue = tempValue;
                minColumnIndex = 8;
                rangeMax = Standard.fe * 1.2 - analysisResult.getFe();
                lackNutrient = "철분";
            } else if (tempValue > maxValue) {
                maxValue = tempValue;
                maxColumnIndex = 8;
            }
            tvFe.setText("철분: " + Math.round(analysisResult.getFe()) + " ㎎ (" + tempValue + "%) " + states[stateIndex]);

            if (analysisResult.getSalt() < Standard.salt * 0.7) {
                stateIndex = 0;

                tvSalt.setTextColor(Color.RED);
            } else if (analysisResult.getSalt() < Standard.salt * 1.2) {
                stateIndex = 1;
            } else {
                stateIndex = 2;
                tvSalt.setTextColor(Color.RED);
            }
            tempValue = (int) (analysisResult.getSalt() / Standard.salt * 100);
            if (tempValue < minValue) {
                minValue = tempValue;
                minColumnIndex = 9;
                rangeMax = Standard.salt * 1.2 - analysisResult.getSalt();
                lackNutrient = "나트륨";
            } else if (tempValue > maxValue) {
                maxValue = tempValue;
                maxColumnIndex = 9;
            }
            tvSalt.setText("나트륨: " + Math.round(analysisResult.getSalt()) + " g (" + tempValue + "%) " + states[stateIndex]);

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
        recommendFoods(minColumnIndex, maxColumnIndex, rangeMax);
    }

    public void recommendFoods(int minIndex, int maxIndex, double rangeMax) {
        recommendFoodResult = mukDBHelper.getRecommendFood(minIndex, maxIndex, rangeMax);

        double nutrient = 0.0;
        String unit = "";

        String temp = "";
        if (recommendFoodResult.size() != 0) {
            tvNeedNutrient.setText(lackNutrient + "이(가) 추가 섭취 필요");
            if (recommendFoodResult.size() > 0) {
                String subCategory = recommendFoodResult.get(0).getSubCategory();
                int imageResource = -1;

                if (Arrays.asList(icon_meat).contains(subCategory)) {
                    imageResource = R.drawable.icon_meat;
                } else if (Arrays.asList(icon_vege_fruit).contains(subCategory)) {
                    imageResource = R.drawable.icon_vege_fruit;
                } else if (Arrays.asList(icon_rice).contains(subCategory)) {
                    imageResource = R.drawable.icon_rice;
                } else if (Arrays.asList(icon_seafood).contains(subCategory)) {
                    imageResource = R.drawable.icon_seafood;
                } else if (Arrays.asList(icon_noodle).contains(subCategory)) {
                    imageResource = R.drawable.icon_noodle;
                } else if (Arrays.asList(icon_bread).contains(subCategory)) {
                    imageResource = R.drawable.icon_bread;
                } else if (Arrays.asList(icon_snack).contains(subCategory)) {
                    imageResource = R.drawable.icon_snack;
                } else if (Arrays.asList(icon_milk).contains(subCategory)) {
                    imageResource = R.drawable.icon_milk;
                } else {
                    imageResource = R.drawable.icon_food;
                }
                bestFoodImage.setImageResource(imageResource);
            } else {
                bestFoodImage.setImageResource(R.drawable.icon_food);
            }
            for (int i = 0; i < recommendFoodResult.size(); i++) {
                switch (minIndex) {
                    case 0:
                        nutrient = recommendFoodResult.get(i).getCalorie();
                        unit = "kcal";
                        break;
                    case 1:
                        nutrient = recommendFoodResult.get(i).getCarbohydrate();
                        unit = "g";
                        break;
                    case 2:
                        nutrient = recommendFoodResult.get(i).getProtein();
                        unit = "g";
                        break;
                    case 3:
                        nutrient = recommendFoodResult.get(i).getFat();
                        unit = "g";
                        break;
                    case 4:
                        nutrient = recommendFoodResult.get(i).getMoisture();
                        unit = "ml";
                        break;
                    case 5:
                        nutrient = recommendFoodResult.get(i).getVitaminK();
                        unit = "㎍";
                        break;
                    case 6:
                        nutrient = recommendFoodResult.get(i).getVitaminC();
                        unit = "㎍";
                        break;
                    case 7:
                        nutrient = recommendFoodResult.get(i).getFiber();
                        unit = "g";
                        break;
                    case 8:
                        nutrient = recommendFoodResult.get(i).getFe();
                        unit = "mg";
                        break;
                    case 9:
                        nutrient = recommendFoodResult.get(i).getSalt();
                        unit = "g";
                        break;
                }

                temp = recommendFoodResult.get(i).getName();
                menu_name[i].setText(temp);
                menu_name[i].setVisibility(View.VISIBLE);
                menu_info[i].setText(nutrient + unit);
                menu_info[i].setVisibility(View.VISIBLE);
                //temp+=food.getName() + "\n";
            }
        } else {
            temp = "추천하는 음식이 없습니다.";
            menu_name[0].setText(temp);
            menu_name[0].setVisibility(View.VISIBLE);
        }
//        tvRecommends.setText(temp);

    }
}
