package com.example.smallobvioshappiness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class News extends Fragment {
    RecyclerView recyclerView;
    NewsAdapter adapter;
    ArrayList<Chat> news;
    RequestQueue queue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.news, container, false);
        queue = Volley.newRequestQueue(getContext());

        initUI(rootView);
        return rootView;
    }

    public void initUI(ViewGroup rootView) {
        news = new ArrayList<>();
        news.add(new Chat("참여한 공구가 완료됐어요!", "자체 제작 향초"));
        news.add(new Chat("참여한 공구가 완료됐어요!", "BHC 치킨"));
        news.add(new Chat("게시물이 수정됐어요!", "일자형 선반"));

        //리사이클러뷰 생성
        recyclerView = rootView.findViewById(R.id.news_recyclerview);

        //리사이클러뷰 사이 구분선
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));

        //레이아웃매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //어댑터 생성, 아이템 추가
        adapter = new NewsAdapter(news);

        //리사이클러뷰 연결
        recyclerView.setAdapter(adapter);
    }
}
