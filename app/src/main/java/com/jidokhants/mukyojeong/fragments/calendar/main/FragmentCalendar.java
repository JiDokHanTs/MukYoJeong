package com.jidokhants.mukyojeong.fragments.calendar.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.activities.MainActivity;

public class FragmentCalendar extends Fragment {

    FragmentPagerAdapter adapterViewPager;


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).showSearchMenu(false);
        ((MainActivity)getActivity()).showWriteMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);


        ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        return view;
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FragmentCalendarCal.newInstance();
                case 1:
                    return FragmentCalendarChart.newInstance();
                default:
                    return null;
            }
        }
    }
}

