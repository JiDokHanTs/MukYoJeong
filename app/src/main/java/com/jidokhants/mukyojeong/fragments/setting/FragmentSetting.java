package com.jidokhants.mukyojeong.fragments.setting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.activities.AfterActivity;
import com.jidokhants.mukyojeong.activities.MainActivity;

public class FragmentSetting extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).showSearchMenu(false);
        ((MainActivity)getActivity()).showWriteMenu(false);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);
        Button button = (Button) view.findViewById(R.id.btn_setting);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AfterActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                Log.d("로그아웃 회원 탈퇴 버튼!!!!!!!!", "click");
            }
        });


        return view;
    }
}
