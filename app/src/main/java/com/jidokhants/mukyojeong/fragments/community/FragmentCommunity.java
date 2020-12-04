package com.jidokhants.mukyojeong.fragments.community;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.jidokhants.mukyojeong.model.Feed;
import com.jidokhants.mukyojeong.model.Post;
import com.jidokhants.mukyojeong.views.adapters.CommunityFeedAdapter;

import java.util.ArrayList;

public class FragmentCommunity extends Fragment implements View.OnClickListener {
    RecyclerView commuRecyclerView;

    CommunityFeedAdapter communityFeedAdapter;
    ArrayList<Feed> feeds = new ArrayList<>();
    ArrayList<Feed> feedTemp = new ArrayList<>();
    ArrayList<String> oldPost= new ArrayList<>();
    DatabaseReference mDatabase;

    String oldPostId;
    String query;
    ProgressDialog pd;

    public static FragmentCommunity newInstance(String query) {
        FragmentCommunity fragment = new FragmentCommunity();
        Bundle args = new Bundle();
        args.putString("query", query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).showSearchMenu(true);
        ((MainActivity)getActivity()).showWriteMenu(true);
        ((MainActivity)getActivity()).setActionbarshow();

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
        commuRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        communityFeedAdapter = new CommunityFeedAdapter(feeds);
        commuRecyclerView.setAdapter(communityFeedAdapter);

        if (pd == null){
            pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
        }
        if (getArguments()!=null && getArguments().getString("query")!= null){
            query = getArguments().getString("query");
        }
        mDatabase.child("posts").limitToLast(5).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()){
                    Post temp = item.getValue(Post.class);
                    String key = item.getKey();
                    Feed newFeed = new Feed(temp, key);
                    if (query == null || newFeed.getPost().getTitle().contains(query)){
                        feeds.add(0, newFeed);
                        feedTemp.add(0, newFeed);
                        oldPost.add(item.getKey());
                    }
                }
                if (oldPost.size() > 0){
                    oldPostId = oldPost.get(0);
                }
                communityFeedAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "예상치 못한 오류가 발생했습니다. 다시 실행해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        commuRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!commuRecyclerView.canScrollVertically(1)) {
                    if (feedTemp.size() == 5){
//                        pd.setMessage("레시피 불러오는 중");
//                        pd.show();
                    }

                    mDatabase.child("posts").orderByKey().endAt(oldPostId).limitToLast(5).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            feedTemp.clear();
                            oldPost.clear();
                            for (DataSnapshot item : snapshot.getChildren()){
                                Post temp = item.getValue(Post.class);
                                String key = item.getKey();
                                Feed newFeed = new Feed(temp, key);
                                if (query == null ||  newFeed.getPost().getTitle().contains(query)){
                                    feedTemp.add(0,newFeed);
                                    oldPost.add(item.getKey());
                                }
                            }
                            if (feedTemp.size()>1){
                                feedTemp.remove(0);
                                feeds.addAll(feedTemp);
                                oldPostId = oldPost.get(0);
                            }
                            communityFeedAdapter.notifyDataSetChanged();

//                            pd.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
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
        switch (v.getId()){
        }
    }
}