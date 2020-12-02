package com.jidokhants.mukyojeong.fragments.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jidokhants.mukyojeong.R;

public class FragmentFeed extends Fragment {
    public static FragmentFeed newInstance() {
        FragmentFeed fragment = new FragmentFeed();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cmnt_feed, container, false);

        int CMNT_SIGN = 0;
        if(getArguments()!= null){
            CMNT_SIGN = getArguments().getInt("CMNT_SIGN");
        }
        TextView feedText = view.findViewById(R.id.feed_text);
        feedText.setText(CMNT_SIGN + "번 feed 입니다.");
        return view;
    }
    
}
