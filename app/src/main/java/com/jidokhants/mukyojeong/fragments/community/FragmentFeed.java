package com.jidokhants.mukyojeong.fragments.community;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.etc.Standard;
import com.jidokhants.mukyojeong.model.Feed;
import com.jidokhants.mukyojeong.model.Ingredient;
import com.jidokhants.mukyojeong.model.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentFeed extends Fragment {
    Feed feed = new Feed();
    TextView title, writer, date, ingredients, recipe, kcal, carbo, protein, fat, moisture, likes, cnt;
    DatabaseReference mDatabase;
    ImageView image;
    CheckBox checkBox;
    RadarChart radarChart;
    FirebaseUser currentUser;

    public static FragmentFeed newInstance(String key) {
        FragmentFeed fragment = new FragmentFeed();
        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cmnt_feed, container, false);

        title = view.findViewById(R.id.commu_post_title);
        writer = view.findViewById(R.id.commu_post_writer);
        date = view.findViewById(R.id.commu_post_date);
        ingredients = view.findViewById(R.id.commu_post_ingredients);
        recipe = view.findViewById(R.id.commu_post_recipe_tv);
        image = view.findViewById(R.id.commu_post_img);
        kcal = view.findViewById(R.id.commu_post_kcal);
        carbo = view.findViewById(R.id.commu_post_carbo);
        protein = view.findViewById(R.id.commu_post_protein);
        fat = view.findViewById(R.id.commu_post_fat);
        moisture = view.findViewById(R.id.commu_post_vitaK);
        checkBox = view.findViewById(R.id.commu_post_like_ckb);
        likes = view.findViewById(R.id.commu_post_like_cnt);
        cnt = view.findViewById(R.id.commu_post_comments_cnt);
        radarChart = view.findViewById(R.id.post_radarchart);

        ArrayList<RadarEntry> values = new ArrayList<>();
        values.add(new RadarEntry(0));
        values.add(new RadarEntry(0));
        values.add(new RadarEntry(0));
        values.add(new RadarEntry(0));
        values.add(new RadarEntry(0));

        RadarDataSet dataSet = new RadarDataSet(values, null);
        dataSet.setDrawFilled(true);

        RadarData radarData = new RadarData();
        radarData.addDataSet(dataSet);
        radarData.setValueTextSize(8f);
        String[] labels = {"칼로리", "탄수화물", "단백질", "지방", "수분"};
        XAxis xAxis = radarChart.getXAxis();
//        YAxis yAxis = radarChart.getYAxis();
//        yAxis.setAxisMinimum(0);
//        yAxis.setAxisMaximum(120f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        radarChart.setData(radarData);

        checkBox.setButtonDrawable(R.drawable.like_button_status);
        if (getArguments() != null) {
            feed.setKey(getArguments().getString("key"));
            Log.d("FEED LOG", feed.getKey());
            mDatabase.child("posts").child(feed.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    feed.setPost(snapshot.getValue(Post.class));
                    Post post = feed.getPost();
                    title.setText(post.getTitle());
                    writer.setText(post.getWriter().getEmail());
                    date.setText(post.getDate());
                    String temp = "";
                    String strIng = "";
                    for (Ingredient item : post.getIngredients()) {
                        temp += item.getFood().getName() + "(" + item.getFood().getServingSize() * item.getRatio() + item.getFood().getUnit() + "), ";
                    }
                    if (temp.length() > 0) {
                        strIng = temp.substring(0, temp.length() - 2);
                    }
                    ingredients.setText(strIng);
                    Glide.with(getActivity()).load(post.getImagePath()).into(image);
                    recipe.setText(post.getContent());
                    kcal.setText("총 열량: " + post.getResultFood().getCalorie() + "kcal");
                    carbo.setText("탄수화물: " + post.getResultFood().getCarbohydrate() + "g");
                    protein.setText("단백질: " + post.getResultFood().getProtein() + "g");
                    fat.setText("지방" + post.getResultFood().getFat() + "g");
                    moisture.setText("수분: " + post.getResultFood().getMoisture() + "ml");
                    if (post.getLikes() != null) {
                        likes.setText("좋아요 " + post.getLikes().size() + "개");
                    } else {
                        likes.setText("좋아요 " + 0 + "개");
                    }
                    if (post.getLikes() != null && post.getLikes().contains(currentUser.getEmail())) {
                        checkBox.setChecked(true);
                    } else {
                        checkBox.setChecked(false);
                    }
                    if (post.getComments() != null) {
                        cnt.setText("댓글 " + post.getComments().size() + "개");
                    } else {
                        cnt.setText("댓글 " + 0 + "개");
                    }
                    radarChart.clear();
                    radarChart.invalidate();

                    ArrayList<RadarEntry> values = new ArrayList<>();

                    values.add(new RadarEntry((float) (post.getResultFood().getCalorie() / Standard.calorie * 100)));
                    values.add(new RadarEntry((float) (post.getResultFood().getCarbohydrate() / Standard.carbohydrate * 100)));
                    values.add(new RadarEntry((float) (post.getResultFood().getProtein() / Standard.protein * 100)));
                    values.add(new RadarEntry((float) (post.getResultFood().getFat() / Standard.fat * 100)));
                    values.add(new RadarEntry((float) (post.getResultFood().getMoisture() / Standard.moisture * 100)));

                    RadarDataSet dataSet = new RadarDataSet(values, null);
                    dataSet.setDrawFilled(true);

                    RadarData radarData = new RadarData();
                    radarData.addDataSet(dataSet);
                    radarData.setValueTextSize(8f);
//                    XAxis xAxis = radarChart.getXAxis();
//                    YAxis yAxis = radarChart.getYAxis();
//                    yAxis.setAxisMinimum(0);
//                    yAxis.setAxisMaximum(120f);
                    radarChart.setData(radarData);
                    radarData.notifyDataChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            ArrayList<String> tempLikes = new ArrayList<>();

            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                tempLikes.clear();
                mDatabase.child("posts").child(feed.getKey()).child("likes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item : snapshot.getChildren()) {
                            String temp = item.getValue(String.class);
                            tempLikes.add(temp);
                        }
                        if (isChecked && !tempLikes.contains(currentUser.getEmail())) {
                            tempLikes.add(currentUser.getEmail());
                            Map<String, Object> map = new HashMap<>();
                            feed.getPost().setLikes(tempLikes);
                            map.put("posts/" + feed.getKey() + "/likes", feed.getPost().getLikes());
                            mDatabase.updateChildren(map);
                        } else {
                            if (!isChecked && tempLikes.contains(currentUser.getEmail())) {
                                tempLikes.remove(currentUser.getEmail());
                                Map<String, Object> map = new HashMap<>();
                                feed.getPost().setLikes(tempLikes);
                                map.put("posts/" + feed.getKey() + "/likes", feed.getPost().getLikes());
                                mDatabase.updateChildren(map);
                            }
                        }
                        likes.setText("좋아요 " + feed.getPost().getLikes().size() + "개");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return view;
    }

}
