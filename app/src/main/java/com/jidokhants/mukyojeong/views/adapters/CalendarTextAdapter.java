package com.jidokhants.mukyojeong.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.model.Record;

import java.util.ArrayList;

public class CalendarTextAdapter extends RecyclerView.Adapter<CalendarTextAdapter.ViewHolder> {

    private ArrayList<Record> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_rv);
        }
    }

    public CalendarTextAdapter(ArrayList<Record> list) {
        mData = list;
    }

    @Override
    public CalendarTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.cal_recyclerview_item, parent, false);
        CalendarTextAdapter.ViewHolder vh = new CalendarTextAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final CalendarTextAdapter.ViewHolder holder, final int position) {
        Record record = mData.get(position);
        holder.textView.setText(record.getFood().getName() +"\t" +(record.getAmountRatio()*record.getFood().getCalorie())+"kcal");


    }
    public void updateItemList(ArrayList<Record> records){
        mData = records;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
}

