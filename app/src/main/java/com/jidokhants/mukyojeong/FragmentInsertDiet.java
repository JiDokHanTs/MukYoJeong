package com.jidokhants.mukyojeong;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentInsertDiet extends Fragment implements View.OnClickListener{
    Button[] toggleButtons = new Button[6];
    int toggleButtonClickedId;

    public static FragmentInsertDiet newInstance() {
        return new FragmentInsertDiet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_insert_diet, container, false);

        toggleButtons[0] = view.findViewById(R.id.toggleButton1);
        toggleButtons[1] = view.findViewById(R.id.toggleButton2);
        toggleButtons[2] = view.findViewById(R.id.toggleButton3);
        toggleButtons[3] = view.findViewById(R.id.toggleButton4);
        toggleButtons[4] = view.findViewById(R.id.toggleButton5);
        toggleButtons[5] = view.findViewById(R.id.toggleButton6);

        toggleButtons[0].setSelected(true);
        toggleButtonClickedId = toggleButtons[0].getId();

        for (int i = 0; i < toggleButtons.length; i++) {
            toggleButtons[i].setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                if (v.getId() != toggleButtonClickedId || v.isSelected()) {
                    for (int i = 0; i < toggleButtons.length; i++) {
                        if (toggleButtonClickedId == toggleButtons[i].getId()) {
                            toggleButtons[i].setSelected(false);
                            break;
                        }
                    }
                }
                toggleButtonClickedId = v.getId();
                v.setSelected(true);
        }
    }
}
