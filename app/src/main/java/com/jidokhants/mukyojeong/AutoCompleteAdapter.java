package com.jidokhants.mukyojeong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jidokhants.mukyojeong.model.Food;

import java.util.ArrayList;

public class AutoCompleteAdapter extends ArrayAdapter<Food> {
    private ArrayList<Food> foodList;

    public AutoCompleteAdapter(@NonNull Context context, @NonNull ArrayList<Food> foodList){
        super(context, 0, foodList);
        this.foodList = new ArrayList<>(foodList);
    }

    @Nullable
    @Override
    public Food getItem(int position) {
        return foodList.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return foodFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cal_autocomplete_item, parent, false
            );

            TextView foodName = convertView.findViewById(R.id.auto_food_name);
            TextView foodFrom = convertView.findViewById(R.id.auto_food_from);

            Food foodItem = getItem(position);

            if(foodItem != null){
                foodName.setText(foodItem.getName());
                if(!foodItem.getCommercial().equals("품목대표"))
                    foodFrom.setText(foodItem.getFrom());
                else
                    foodFrom.setText("");
            }
        }
        return convertView;
    }

    private Filter foodFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<Food> suggestions = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                suggestions.addAll(foodList);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Food item : foodList){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        suggestions.add(item);
                    }
                }
            }
            results.values= suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Food)resultValue).getName();
        }
    };
}
