package com.jidokhants.mukyojeong;

import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FragmentInsertDiet extends Fragment {
    public static FragmentInsertDiet newInstance() {
        return new FragmentInsertDiet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_calendar_insert_diet, container, false);

        final ArrayList<String> Items = new ArrayList<>();

        TextView tv_meal = view.findViewById(R.id.text_meal_diet);
        final EditText edit_diet = view.findViewById(R.id.edit_insert_diet);
        Button btn_add = view.findViewById(R.id.btn_add_diet);

        RecyclerView recyclerView = view.findViewById(R.id.rv_menu_diet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final CalendarInsertDietAdapter adapter = new CalendarInsertDietAdapter(Items);
        recyclerView.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_item = edit_diet.getText().toString();
                if(str_item.length()==0) {
                    Toast.makeText(getContext(), "식단을 입력해주쇼~!", Toast.LENGTH_SHORT).show();
                    edit_diet.clearFocus();
                }
                else {
                    Items.add(str_item);
                    adapter.notifyDataSetChanged();
                    edit_diet.setText("");
                    edit_diet.clearFocus();
                    Toast.makeText(getApplicationContext(), "'"+str_item +"'"+ " 메뉴가 추가됨!.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}
