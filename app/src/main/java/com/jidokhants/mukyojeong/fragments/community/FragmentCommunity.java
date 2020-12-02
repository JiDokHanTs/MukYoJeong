package com.jidokhants.mukyojeong.fragments.community;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.activities.MainActivity;
import com.jidokhants.mukyojeong.model.Post;
import com.jidokhants.mukyojeong.views.adapters.CommunityFeedAdapter;

import java.util.ArrayList;

public class FragmentCommunity extends Fragment implements View.OnClickListener {
    RecyclerView commuRecyclerView;

    CommunityFeedAdapter communityFeedAdapter;
    ArrayList<Post> posts;

    DatabaseReference mDatabase;

    int oldestPostId = -1;
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).showSearchMenu(true);
        ((MainActivity)getActivity()).showWriteMenu(true);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community, container, false);

        commuRecyclerView = view.findViewById(R.id.commu_recyclerview);
        commuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        posts = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("posts").limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()){
                    posts.add(0, item.getValue(Post.class));
                }
                Log.d("POST CALL", ""+posts.size());
                communityFeedAdapter = new CommunityFeedAdapter(posts);
                commuRecyclerView.setAdapter(communityFeedAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
    }
}