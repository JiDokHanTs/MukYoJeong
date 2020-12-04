package com.jidokhants.mukyojeong.views.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.data.MukDBHelper;
import com.jidokhants.mukyojeong.model.Food;
import com.jidokhants.mukyojeong.model.Ingredient;
import com.jidokhants.mukyojeong.model.Record;

import java.util.ArrayList;

public class IngdListAdapter extends RecyclerView.Adapter<IngdListAdapter.ViewHolder> {

    private ArrayList<Ingredient> ingredients = null;
    Context context;

    private MukDBHelper mukDBHelper;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, minus, plus;
        EditText editCount;
        ImageButton delete;

        ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_diet_rv);
            minus = itemView.findViewById(R.id.btn_minus_diet);
            plus = itemView.findViewById(R.id.btn_plus_diet);
            editCount = itemView.findViewById(R.id.edit_count_diet);
            delete = itemView.findViewById(R.id.btn_delete_diet);
        }
    }

    IngdListAdapter() {
    }

    ;

    public IngdListAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public IngdListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.diet_recyclerview_item, parent, false);
        IngdListAdapter.ViewHolder vh = new IngdListAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final IngdListAdapter.ViewHolder holder, final int position) {
        mukDBHelper = MukDBHelper.getInstance(context);

        Food currentFood = ingredients.get(position).getFood();
        holder.textView.setText(currentFood.getName().toString() + " (" + currentFood.getServingSize() + currentFood.getUnit() + ")");
        holder.editCount.setText(ingredients.get(position).getRatio()+"");

        holder.editCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    Double cnt = (Double.parseDouble(holder.editCount.getText().toString()));
                    if(cnt <0.0){
                        Toast.makeText(context, "0 이하로 입력할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        holder.editCount.setText(0.0+"");
                    }else{
                        ingredients.get(position).setRatio(cnt);
                        Log.d("INGREDIENT ADAPTER", "changed cnt: "+ cnt);

                    }
                }
                else{
                    ingredients.get(position).setRatio(0.0);
                }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.editCount.getText().toString().equals("") ||
                        (Double.parseDouble(holder.editCount.getText().toString())) -0.5 <= 0.0) {
                    Toast.makeText(context, "0 이하로 입력할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    ingredients.get(position).setRatio(0.0);
                    holder.editCount.setText(0.0+"");
                } else {
                    Double cnt = (Double.parseDouble(holder.editCount.getText().toString())) - 0.5;
//                    ingredients.get(position).setRatio(cnt);
                    holder.editCount.setText(cnt + "");
                }
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double cnt = (Double.parseDouble(holder.editCount.getText().toString())) + 0.5;
//                ingredients.get(position).setRatio(cnt);
                holder.editCount.setText(cnt + "");
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "'" + ingredients.get(position).getFood().getName() + "'" + "이(가) 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                removeItem(position);
            }
        });


    }


    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void addItem(Ingredient data) {
        ingredients.add(data);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        ingredients.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void updateItemList(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }
    public void resetList(){
        ingredients.clear();
        notifyDataSetChanged();
    }
    public ArrayList<Ingredient> getAllIngredients(){
        return ingredients;
    }
}
