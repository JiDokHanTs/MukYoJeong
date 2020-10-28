package com.jidokhants.mukyojeong;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

public class FragmentCalendarChart extends Fragment {
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

    ScrollView chartScrollView;

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

        edit_from = view.findViewById(R.id.edit_from);
        edit_to = view.findViewById(R.id.edit_to);

        Date time = new Date();
        String today = (new SimpleDateFormat("yyyy.MM.dd")).format(time);
        String before = sdf_string.format(time);
        before = ((Integer.parseInt(before))-6)+"";
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
        TextView tvCal = view.findViewById(R.id.tv_cal);
        TextView tvCab = view.findViewById(R.id.tv_carbo);
        TextView tvPro = view.findViewById(R.id.tv_pro);
        TextView tvFat = view.findViewById(R.id.tv_fat);
        TextView tvMo = view.findViewById(R.id.tv_mo);
        TextView tvVD = view.findViewById(R.id.tv_v_d);
        TextView tvVC = view.findViewById(R.id.tv_v_c);
        TextView tvFiber = view.findViewById(R.id.tv_fiber);
        TextView tvFe = view.findViewById(R.id.tv_fe);
        TextView tvSalt = view.findViewById(R.id.tv_salt);

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

        raw_date_from = edit_from.getText().toString();
        raw_date_to = edit_to.getText().toString();
        try {
            Date from = sdf_string.parse(raw_date_from);
            date_from = String.valueOf(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date to = sdf_string.parse(raw_date_to);
            date_to=String.valueOf(to);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Food weekResult = mukDBHelper.weekRecord(date_from, date_to);

        Log.d(TAG, "칼로리: " + weekResult.getCalorie() + "kcal");
        Log.d(TAG, "탄수회물: " + weekResult.getCarbohydrate() + "g");
        Log.d(TAG, "단백질: " + weekResult.getProtein() + "g");
        Log.d(TAG, "지방: " + weekResult.getFat() + "g");
        Log.d(TAG, "수분: " + weekResult.getMoisture() + "ml");
        radarChart = view.findViewById(R.id.radarChart);

        ArrayList<RadarEntry> values = new ArrayList<>();
        values.add(new RadarEntry((float) (weekResult.getCalorie() / Standard.calorie * 100)));
        values.add(new RadarEntry((float) (weekResult.getCarbohydrate() / Standard.carbohydrate * 100)));
        values.add(new RadarEntry((float) (weekResult.getProtein() / Standard.protein * 100)));
        values.add(new RadarEntry((float) (weekResult.getFat() / Standard.fat * 100)));
        values.add(new RadarEntry((float) (weekResult.getMoisture() / Standard.moisture * 100)));

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
        String state;

        state = "";

        Food analysisFood = new Food(1000000);

        if (weekResult.getCalorie() / Standard.calorie < 0.7) {
            tvCal.setTextColor(Color.RED);
            state = "under";
        } else if (weekResult.getCalorie() / Standard.calorie > 1.2) {
            tvCal.setTextColor(Color.RED);
            state = "over";
        }
        analysisFood.setCalorie(Standard.calorie - weekResult.getCalorie());
        tvCal.setText("칼로리: " + weekResult.getCalorie() + " (" + (int) (weekResult.getCalorie() / Standard.calorie * 100) + "%) " + state);

        state = "";
        if (weekResult.getCarbohydrate() / Standard.carbohydrate < 0.7) {
            tvCab.setTextColor(Color.RED);
            state = "under";
        } else if (weekResult.getCarbohydrate() / Standard.carbohydrate > (double) 70 / 65.0) {
            tvCab.setTextColor(Color.RED);
            state = "over";
        }
        analysisFood.setCarbohydrate(Standard.carbohydrate - weekResult.getCarbohydrate());
        tvCab.setText("탄수화물: " + weekResult.getCarbohydrate() + " (" + (int) (weekResult.getCarbohydrate() / Standard.carbohydrate * 100) + "%) " + state);

        state = "";
        if (weekResult.getProtein() / Standard.protein < 7 / 15.0) {//ㅗㅗ
            tvPro.setTextColor(Color.RED);
            state = "under";
        } else if (weekResult.getProtein() / Standard.protein > 1.2) {
            tvPro.setTextColor(Color.RED);
            state = "over";
        }
        analysisFood.setProtein(Standard.protein - weekResult.getProtein());
        tvPro.setText("단백질: " + weekResult.getProtein() + " (" + (int) (weekResult.getProtein() / Standard.protein * 100) + "%) " + state);

        state = "";
        if (weekResult.getFat() / Standard.fat < 0.7) {
            tvFat.setTextColor(Color.RED);
            state = "under";
        } else if (weekResult.getFat() / Standard.fat > 1.2) {
            tvFat.setTextColor(Color.RED);
            state = "over";
        }
        analysisFood.setFat(Standard.fat - weekResult.getFat());

        tvFat.setText("지방: " + weekResult.getFat() + " (" + (int) (weekResult.getFat() / Standard.fat * 100) + "%) " + state);

        state = "";
        if (weekResult.getMoisture() / Standard.moisture < 0.7) {
            tvMo.setTextColor(Color.RED);
            state = "under";
        } else if (weekResult.getMoisture() / Standard.moisture > 1.2) {
            tvMo.setTextColor(Color.RED);
            state = "over";
        }
        analysisFood.setMoisture(Standard.moisture - weekResult.getMoisture());
        tvMo.setText("수분: " + weekResult.getMoisture() + " (" + (int) (weekResult.getMoisture() / Standard.moisture * 100) + "%) " + state);

        state = "";
        if (weekResult.getVitaminD() / Standard.vitaminD < 0.7) {
            tvVD.setTextColor(Color.RED);
            state = "under";
        } else if (weekResult.getVitaminD() / Standard.vitaminD > 1.2) {
            tvVD.setTextColor(Color.RED);
            state = "over";
        }
        analysisFood.setVitaminD(Standard.vitaminD - weekResult.getVitaminD());
        tvVD.setText("비타민D: " + weekResult.getVitaminD() + " (" + (int) (weekResult.getVitaminD() / Standard.vitaminD) + "%) " + state);

        state = "";
        if (weekResult.getVitaminC() / Standard.vitaminC < 0.7) {
            tvVC.setTextColor(Color.RED);
            state = "under";
        } else if (weekResult.getVitaminC() / Standard.vitaminC > 1.2) {
            tvVC.setTextColor(Color.RED);
            state = "over";
        }
        analysisFood.setVitaminC(Standard.vitaminC - weekResult.getVitaminC());
        tvVC.setText("비타민C: " + weekResult.getVitaminC() + " (" + (int) (weekResult.getVitaminC() / Standard.vitaminC * 100) + "%) " + state);

        state = "";
        if (weekResult.getFiber() / Standard.fiber < 0.7) {
            tvFiber.setTextColor(Color.RED);
            state = "under";
        } else if (weekResult.getFiber() / Standard.fiber > 1.2) {
            tvFiber.setTextColor(Color.RED);
            state = "over";
        }
        analysisFood.setFiber(Standard.fiber - weekResult.getFiber());
        tvFiber.setText("식이섬유: " + weekResult.getFiber() + " (" + (int) (weekResult.getFiber() / Standard.fiber * 100) + "%) " + state);

        state = "";
        if (weekResult.getFe() / Standard.fe < 0.7) {
            tvFe.setTextColor(Color.RED);
            state = "under";
        } else if (weekResult.getFe() / Standard.fe > 1.2) {
            tvFe.setTextColor(Color.RED);
            state = "over";
        }
        analysisFood.setFe(Standard.fe - weekResult.getFe());
        tvFe.setText("철분: " + weekResult.getFe() + " (" + (int) (weekResult.getFe() / Standard.fe * 0.1) + "%) " + state);

        state = "";
        if (weekResult.getSalt() / Standard.salt < 0.7) {
            tvSalt.setTextColor(Color.RED);
            state = "under";
        } else if (weekResult.getSalt() / Standard.salt > 1.2) {
            tvSalt.setTextColor(Color.RED);
            state = "over";
        }
        analysisFood.setSalt(Standard.salt - weekResult.getSalt());
        tvSalt.setText("나트륨: " + weekResult.getSalt() + " (" + (int) (weekResult.getSalt() / Standard.salt * 0.1) + "%) " + state);

        ArrayList<Food> bestMenu = mukDBHelper.getBestMenu(analysisFood);
        return view;
    }

}
