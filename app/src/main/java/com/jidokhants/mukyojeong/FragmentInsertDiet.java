package com.jidokhants.mukyojeong;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jidokhants.mukyojeong.model.Food;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FragmentInsertDiet extends Fragment implements View.OnClickListener {
    Button[] toggleButtons = new Button[6];
    int toggleButtonClickedId;

    private MukDBHelper mukDBHelper;
    ArrayList<Food> foodList;

    Food selectedFood;
    public static FragmentInsertDiet newInstance() {
        return new FragmentInsertDiet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calendar_insert_diet, container, false);

        final ArrayList<String> Items = new ArrayList<>();

        TextView tv_meal = view.findViewById(R.id.text_meal_diet);

        final AutoCompleteTextView autoCompleteInsertText = view.findViewById(R.id.auto_complete_insert_diet);
        Button btn_add = view.findViewById(R.id.btn_add_diet);

        RecyclerView recyclerView = view.findViewById(R.id.rv_menu_diet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        toggleButtons[0] = view.findViewById(R.id.toggleButton1);
        toggleButtons[1] = view.findViewById(R.id.toggleButton2);
        toggleButtons[2] = view.findViewById(R.id.toggleButton3);
        toggleButtons[3] = view.findViewById(R.id.toggleButton4);
        toggleButtons[4] = view.findViewById(R.id.toggleButton5);
        toggleButtons[5] = view.findViewById(R.id.toggleButton6);

        final CalendarInsertDietAdapter adapter = new CalendarInsertDietAdapter(Items);
        recyclerView.setAdapter(adapter);

        mukDBHelper = MukDBHelper.getInstance(getContext());
        foodList = mukDBHelper.getAllFood();
//        final String[] foodNameList = new String[foodList.size()];

//        for (int i = 0; i < foodList.size();i++){
//            if (!foodList.get(i).getCommercial().equals("품목대표")){
//                foodNameList[i] = foodList.get(i).getName()+"("+foodList.get(i).getFrom()+")";
//            }else{
//                foodNameList[i] = foodList.get(i).getName();
//            }
//        }
        final AutoCompleteAdapter autoCompleteTestAdapter = new AutoCompleteAdapter(getContext(), foodList);
        autoCompleteInsertText.setAdapter(autoCompleteTestAdapter);
        autoCompleteInsertText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFood = autoCompleteTestAdapter.getItem(position);
                Log.d("selectedFoodInfo", selectedFood.getName()+": "+selectedFood.getCalorie()+"kcal");
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_item = autoCompleteInsertText.getText().toString();
                if (str_item.length() == 0) {
                    Toast.makeText(getContext(), "식단을 입력해주쇼~!", Toast.LENGTH_SHORT).show();
                    autoCompleteInsertText.clearFocus();
                } else {
                    Items.add(str_item);
                    adapter.notifyDataSetChanged();
                    autoCompleteInsertText.setText("");
                    autoCompleteInsertText.clearFocus();
                    Toast.makeText(getApplicationContext(), "'" + str_item + "'" + " 메뉴가 추가됨!.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        toggleButtons[0].setSelected(true);
        toggleButtonClickedId = toggleButtons[0].getId();

        for (int i = 0; i < toggleButtons.length; i++) {
            toggleButtons[i].setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
