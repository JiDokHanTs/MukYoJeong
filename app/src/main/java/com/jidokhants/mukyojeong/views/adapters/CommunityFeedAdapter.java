package com.jidokhants.mukyojeong.views.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.jidokhants.mukyojeong.data.MukDBHelper;
import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.model.Post;
import com.jidokhants.mukyojeong.model.Record;

import java.util.ArrayList;

public class CommunityFeedAdapter extends RecyclerView.Adapter<CommunityFeedAdapter.ViewHolder> {

    private ArrayList<Post> mData = null;
    Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, writer, date, likes, comments;
        ImageView profile, image;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.commu_item_title);
            writer = itemView.findViewById(R.id.commu_item_writer);
            date = itemView.findViewById(R.id.commu_item_date);
            likes = itemView.findViewById(R.id.commu_item_like_cnt);
            comments = itemView.findViewById(R.id.commu_item_comments_cnt);
        }
    }

    CommunityFeedAdapter() {};
    public CommunityFeedAdapter(ArrayList<Post> list) {
        mData = list;
    }

    @Override
    public CommunityFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.community_item, parent, false);
        CommunityFeedAdapter.ViewHolder vh = new CommunityFeedAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final CommunityFeedAdapter.ViewHolder holder, final int position) {

        Post curPost = mData.get(position);
        holder.title.setText("제목: "+curPost.getTitle().toString());
        holder.writer.setText(curPost.getWriter().toString());
        holder.date.setText(curPost.getDate());
        holder.image.setImageURI(Uri.parse(curPost.getImagePath()));
        holder.likes.setText(curPost.getLikes().size());
        holder.comments.setText(curPost.getComments().size());

//        Record curRecord = mData.get(position);
//        holder.textView.setText(curRecord.getFood().getName().toString()+" ("+curRecord.getFood().getServingSize()+ curRecord.getFood().getUnit()+")");
//        holder.editCount.setText(curRecord.getAmountRatio()+"");


    }

    @Override
    public int getItemCount() {
        return 0;
    }


//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }
//
//    public void addItem(Record data) {
//        mData.add(data);
//        notifyDataSetChanged();
//    }
//
//    public void removeItem(int position) {
//        mData.remove(position);
//        notifyItemRemoved(position);
//        notifyDataSetChanged();
//    }
//    public void updateItemList (ArrayList<Record> records){
//        mData = records;
//        notifyDataSetChanged();
//    }
}
