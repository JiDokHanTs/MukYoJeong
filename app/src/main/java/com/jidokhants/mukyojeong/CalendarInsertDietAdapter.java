package com.jidokhants.mukyojeong;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jidokhants.mukyojeong.model.Food;
import com.jidokhants.mukyojeong.model.Record;

import java.util.ArrayList;

public class CalendarInsertDietAdapter extends RecyclerView.Adapter<CalendarInsertDietAdapter.ViewHolder> {

    private ArrayList<Record> mData = null;
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

    CalendarInsertDietAdapter() {};
    CalendarInsertDietAdapter(ArrayList<Record> list) {
        mData = list;
    }

    @Override
    public CalendarInsertDietAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.diet_recyclerview_item, parent, false);
        CalendarInsertDietAdapter.ViewHolder vh = new CalendarInsertDietAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final CalendarInsertDietAdapter.ViewHolder holder, final int position) {
        mukDBHelper = MukDBHelper.getInstance(context);

        Record curRecord = mData.get(position);
        holder.textView.setText(curRecord.getFood().getName().toString());
        holder.editCount.setText(curRecord.getAmountRatio()+"");

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Double.parseDouble(holder.editCount.getText().toString())) == 0.0){
                    Toast.makeText(context, "0 이하로 입력할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Record record =mData.get(position);
                    Double cnt = record.getAmountRatio() - 0.5 ;
                    //holder.editCount.setText(cnt+"");
                    record.setAmountRatio(cnt);
                    notifyDataSetChanged();
                    mukDBHelper.updateRecord(record);
                    Log.d("숫자 minus", "position : " + position + " record name : " + record.getFood().getName() + " ratio : " + record.getAmountRatio());
                }
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Record record =mData.get(position);
                Double cnt = record.getAmountRatio() + 0.5;
                record.setAmountRatio(cnt);
                notifyDataSetChanged();
                //holder.editCount.setText(cnt+"");
                mukDBHelper.updateRecord(record);
                Log.d("숫자 plus", "position : " + position + " record name : " + record.getFood().getName() + " ratio : " + record.getAmountRatio());
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"'"+ mData.get(position).getFood().getName() + "'" +"이(가) 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                mukDBHelper.deleteRocord(mData.get(position));
                removeItem(position);
            }
        });

//        holder.editCount.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(holder.editCount.getText().length()>0) {
//                    Record record = mData.get(position);
//                    Double cnt = record.getAmountRatio();
//                    record.setAmountRatio(cnt);
//                    holder.editCount.setText(cnt+"");
//                    //notifyDataSetChanged();
//                    mukDBHelper.updateRecord(record);
//                    Log.d("ratio key", "record name : " + record.getFood().getName() + " ratio : " + record.getAmountRatio());
//                }
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItem(Record data) {
        mData.add(data);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    public void updateItemList (ArrayList<Record> records){
        mData = records;
        notifyDataSetChanged();
    }
}
