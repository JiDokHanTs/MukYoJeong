package com.jidokhants.mukyojeong.fragments.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.activities.MainActivity;

public class FragmentNotifications extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).showSearchMenu(false);
        ((MainActivity)getActivity()).showWriteMenu(false);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications,container,false);
    }
}
