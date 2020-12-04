package com.jidokhants.mukyojeong.fragments.community;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.activities.MainActivity;
import com.jidokhants.mukyojeong.data.MukDBHelper;
import com.jidokhants.mukyojeong.model.Food;
import com.jidokhants.mukyojeong.model.Ingredient;
import com.jidokhants.mukyojeong.views.adapters.CalendarInsertDietAdapter;
import com.jidokhants.mukyojeong.views.adapters.IngdAutoCompleteAdapter;
import com.jidokhants.mukyojeong.views.adapters.IngdListAdapter;

import java.util.ArrayList;

public class FragmentIngredientDialog extends DialogFragment implements View.OnClickListener {
    private Context context;
    Dialog dialog;
    Button okButton, cancelButton;
    AutoCompleteTextView ingdAutoComplete;
    RecyclerView ingdRecyclerview;

    MukDBHelper mukDBHelper;
    ArrayList<Food> foods;
    ArrayList<Ingredient> ingredients;

    IngdListAdapter ingdListAdapter;
    LinearLayout buttonLayout;

    interface IngredientDialogListener{
        void submitted(ArrayList<Ingredient> ingredients);
    }
    public static FragmentIngredientDialog newInstance(ArrayList<Ingredient> newIngredients) {
        FragmentIngredientDialog fragmentIngredientDialog = new FragmentIngredientDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList("ingredients", newIngredients);
        fragmentIngredientDialog.setArguments(args);
        return fragmentIngredientDialog;
    }

    public FragmentIngredientDialog() {

    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_width_size);
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_height_size);
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Bundle args = getArguments();
            ingredients = args.<Ingredient>getParcelableArrayList("ingredients");
            if (ingredients.size() > 0) {
                Log.d("제발", ingredients.get(0).getFood().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.ingredient_dialog, null);

        ingdAutoComplete = view.findViewById(R.id.ingd_auto);
        okButton = view.findViewById(R.id.ingd_btn_ok);
        cancelButton = view.findViewById(R.id.ingd_btn_cancel);
        ingdRecyclerview = view.findViewById(R.id.ingd_recyclerview);
        buttonLayout = view.findViewById(R.id.btn_layout);

        if (ingredients.size() > 0) {
            Log.d("제발", ingredients.get(0).getFood().getName());
        }
        ingdRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingdListAdapter = new IngdListAdapter(ingredients);
        ingdRecyclerview.setAdapter(ingdListAdapter);

        mukDBHelper = MukDBHelper.getInstance(getActivity());
        foods = mukDBHelper.getIngredients();

        IngdAutoCompleteAdapter autoCompleteTestAdapter = new IngdAutoCompleteAdapter(getActivity(), foods);
        ingdAutoComplete.setAdapter(autoCompleteTestAdapter);
        ingdAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("INGREDIENT: ", ((Food) parent.getItemAtPosition(position)).getName());
                ingdListAdapter.addItem(new Ingredient((Food) parent.getItemAtPosition(position)));
                ingdAutoComplete.setText("");
            }
        });

        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    public ArrayList<Ingredient> getIngredientFromDialog(){
        return ingredients;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ingd_btn_ok:
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("ingredients", ingredients);
                getTargetFragment().onActivityResult(7777, Activity.RESULT_OK, intent);
//                ((FragmentWrite)getTargetFragment()).setIngredients(ingredients);
//                ((MainActivity)getActivity()).ingredientsFrom(ingredients);
//                ingdListAdapter.resetList();
                getDialog().dismiss();
                break;
            case R.id.ingd_btn_cancel:
//                ingdListAdapter.resetList();
                getDialog().dismiss();
                break;
        }
    }
}
