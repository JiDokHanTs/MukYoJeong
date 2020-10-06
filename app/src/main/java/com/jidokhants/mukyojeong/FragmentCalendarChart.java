package com.jidokhants.mukyojeong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCalendarChart extends Fragment {

    public static FragmentCalendarChart newInstance() {
        FragmentCalendarChart fragment = new FragmentCalendarChart();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_chart, container, false);
        return view;
    }
}
