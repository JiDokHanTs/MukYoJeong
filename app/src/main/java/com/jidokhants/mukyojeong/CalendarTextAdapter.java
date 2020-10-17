package com.jidokhants.mukyojeong;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.xml.XMLConstants;

public class CalendarTextAdapter extends RecyclerView.Adapter<CalendarTextAdapter.ViewHolder> {

    private ArrayList<String> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_rv);
           }
    }

    CalendarTextAdapter(ArrayList<String> list) {
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
        String text = mData.get(position);
        holder.textView.setText(text);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

