package com.jidokhants.mukyojeong;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentCommunity extends Fragment implements View.OnClickListener {

    EditText searchText;
    Button btnToday, btnWeek, btnNow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_community,container,false);

        btnToday = view.findViewById(R.id.cmnt_btn_today);
        btnWeek = view.findViewById(R.id.cmnt_btn_week);
        btnNow = view.findViewById(R.id.cmnt_btn_now);

        btnToday.setOnClickListener(this);
        btnWeek.setOnClickListener(this);
        btnNow.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View searchBoxView = view.findViewById(R.id.community_search_box_item_ayout);

        ImageButton searchButton = searchBoxView.findViewById(R.id.search_button);
        searchText = searchBoxView.findViewById(R.id.search_edit_text);
        Log.d("onViewCreated", "succeeded");

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("searchButton", "clicked");

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.0.18:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                final RemoteService remoteService = retrofit.create(RemoteService.class);
                Call<person> call = remoteService.getName();

                call.enqueue(new Callback<person>() {
                    @Override
                    public void onResponse(Call<person> call, Response<person> response) {
                        String temp;

                        try {
                            person person = response.body();
                            temp = person.getName();
                            Log.d("requestGet", temp.toString());
                            searchText.setText(temp.toString());
                        }catch (Exception e){
                            Log.e("onResponse", "error");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<person> call, Throwable t) {
                        t.printStackTrace();
                        Log.d("onFailure", "called, " + t.toString());
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.cmnt_btn_today:
                ((MainActivity)getActivity()).replaceCommunityFragment(1, FragmentCalendar2.newInstance());
                break;
            case R.id.cmnt_btn_week:
                ((MainActivity)getActivity()).replaceCommunityFragment(2, FragmentFeed.newInstance());
                break;
            case R.id.cmnt_btn_now:
                ((MainActivity)getActivity()).replaceCommunityFragment(3, FragmentFeed.newInstance());
                break;
        }
    }
}
