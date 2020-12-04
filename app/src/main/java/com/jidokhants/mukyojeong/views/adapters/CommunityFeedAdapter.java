package com.jidokhants.mukyojeong.views.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jidokhants.mukyojeong.activities.MainActivity;
import com.jidokhants.mukyojeong.data.MukDBHelper;
import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.fragments.community.FragmentFeed;
import com.jidokhants.mukyojeong.model.Feed;
import com.jidokhants.mukyojeong.model.Post;
import com.jidokhants.mukyojeong.model.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommunityFeedAdapter extends RecyclerView.Adapter<CommunityFeedAdapter.ViewHolder> {

    private ArrayList<Feed> mData = new ArrayList<>();
    Context context;
    FirebaseUser currentUser;
    DatabaseReference mDatabase;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, writer, date, likes, comments;
        ImageView profile, image;
        CheckBox likeCheckBox;
        LinearLayout communityLayout;
        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.commu_item_title);
            writer = itemView.findViewById(R.id.commu_item_writer);
            date = itemView.findViewById(R.id.commu_item_date);
            image = itemView.findViewById(R.id.commu_item_img);
            likes = itemView.findViewById(R.id.commu_item_like_cnt);
            likeCheckBox = itemView.findViewById(R.id.commu_item_like_ckb);
            comments = itemView.findViewById(R.id.commu_item_comments_cnt);
            communityLayout = itemView.findViewById(R.id.commu_item_layout);
        }
    }

    CommunityFeedAdapter() {
    }

    ;

    public CommunityFeedAdapter(ArrayList<Feed> list) {
        mData = list;
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

        final Post curPost = mData.get(position).getPost();
        if (curPost!= null){
            holder.communityLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentFeed fragmentFeed = FragmentFeed.newInstance(mData.get(position).getKey());
                    ((MainActivity)context).replaceCommunityPostFragment(fragmentFeed);
                }
            });
            holder.title.setText("제목: " + curPost.getTitle());
            holder.writer.setText(curPost.getWriter().getEmail());
            holder.date.setText(curPost.getDate());
            if (curPost.getImagePath()!=null){
                Glide.with(context).load(curPost.getImagePath()).into(holder.image);
            }
            if (curPost.getLikes()!= null && curPost.getLikes().size() != 0){
                holder.likes.setText("좋아요 "+curPost.getLikes().size()+"개");
            }else{
                holder.likes.setText("좋아요 "+"0"+"개");
            }
            if (curPost.getComments()!= null && curPost.getComments().size() != 0){
                holder.comments.setText("댓글 "+curPost.getComments().size()+"개");
            }else{
                holder.comments.setText("댓글 "+"0개");
            }
            // 좋아요 버튼 세팅
            holder.likeCheckBox.setButtonDrawable(R.drawable.like_button_status);
            if (curPost.getLikes()!=null && curPost.getLikes().contains(currentUser.getEmail())){
                holder.likeCheckBox.setChecked(true);
            }
            else if (curPost.getLikes() == null){
                curPost.setLikes(new ArrayList<String>());
            }
            Log.d("CURRENT USER", currentUser.getEmail());

            holder.likeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                ArrayList<String> tempLikes = new ArrayList<>();
                @Override
                public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                    tempLikes.clear();
                    mDatabase.child("posts").child(mData.get(position).getKey()).child("likes").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot item : snapshot.getChildren()){
                                String temp = item.getValue(String.class);
                                tempLikes.add(temp);
                            }
                            if (isChecked && !tempLikes.contains(currentUser.getEmail())){
                                tempLikes.add(currentUser.getEmail());
                                Map<String, Object> map = new HashMap<>();
                                curPost.setLikes(tempLikes);
                                map.put("posts/"+mData.get(position).getKey()+"/likes", curPost.getLikes());
                                mDatabase.updateChildren(map);
                            }else{
                                if (!isChecked && tempLikes.contains(currentUser.getEmail())){
                                    tempLikes.remove(currentUser.getEmail());
                                    Map<String, Object> map = new HashMap<>();
                                    curPost.setLikes(tempLikes);
                                    map.put("posts/"+mData.get(position).getKey()+"/likes", curPost.getLikes());
                                    mDatabase.updateChildren(map);
                                }
                            }
                            holder.likes.setText("좋아요 "+curPost.getLikes().size()+"개");

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
        }

//        Record curRecord = mData.get(position);
//        holder.textView.setText(curRecord.getFood().getName().toString()+" ("+curRecord.getFood().getServingSize()+ curRecord.getFood().getUnit()+")");
//        holder.editCount.setText(curRecord.getAmountRatio()+"");


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
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
    public void updateItemList (ArrayList<Feed> posts){
        mData = posts;
        notifyDataSetChanged();
    }
}
