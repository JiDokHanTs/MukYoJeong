package com.jidokhants.mukyojeong;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jidokhants.mukyojeong.model.Food;
import com.jidokhants.mukyojeong.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteAdapter extends ArrayAdapter<FoodItem> {

    private List<FoodItem> foodListFull;

    public AutoCompleteAdapter(@NonNull Context context,  @NonNull ArrayList<FoodItem> foodList) {
        super(context, 0, foodList);
        this.foodListFull = new ArrayList<>(foodList);
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return foodFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cal_autocomplete_item, parent, false
            );

            TextView foodName = convertView.findViewById(R.id.auto_food_name);
            TextView foodFrom = convertView.findViewById(R.id.auto_food_from);

            FoodItem foodItem = getItem(position);

            foodName.setText(foodItem.getName());
            if (!foodItem.getCommercial().equals("품목대표"))
                foodFrom.setText(foodItem.getFrom());
            else
                foodFrom.setText("");
        }
        return convertView;
    }

    private Filter foodFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<FoodItem> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(foodListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                suggestions.clear();
                for (FoodItem item : foodListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {
                Log.d("customAdapter","called, Hi");
                clear();
                addAll((List)results.values);
                notifyDataSetChanged();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((FoodItem) resultValue).getName();
        }
    };
}
