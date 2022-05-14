package com.example.smallobvioshappiness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainTab extends Fragment {

    RecyclerView recyclerView;
    PostAdapter adapter;
    ArrayList<Post> post;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.maintab, container, false);

        initUI(rootView);
        return rootView;
    }

    public void initUI(ViewGroup rootView){
        post = new ArrayList<>();
        post.add(new Post("인테리어 사진 액자 공구", "잡화", "1분전", "21,000원"));
        post.add(new Post("인테리어 사진 액자 공구", "잡화", "1분전", "21,000원"));
        post.add(new Post("인테리어 사진 액자 공구", "잡화", "1분전", "21,000원"));
        post.add(new Post("인테리어 사진 액자 공구", "잡화", "1분전", "21,000원"));
        post.add(new Post("인테리어 사진 액자 공구", "잡화", "1분전", "21,000원"));
        post.add(new Post("인테리어 사진 액자 공구", "잡화", "1분전", "21,000원"));
        post.add(new Post("인테리어 사진 액자 공구", "잡화", "1분전", "21,000원"));
        post.add(new Post("인테리어 사진 액자 공구", "잡화", "1분전", "21,000원"));
        post.add(new Post("인테리어 사진 액자 공구", "잡화", "1분전", "21,000원"));
        post.add(new Post("인테리어 사진 액자 공구", "잡화", "1분전", "21,000원"));
        post.add(new Post("인테리어 사진 액자 공구", "잡화", "1분전", "21,000원"));
        post.add(new Post("인테리어 사진 액자 공구", "잡화", "1분전", "21,000원"));

        //리사이클러뷰 생성
        recyclerView = rootView.findViewById(R.id.post_RecyclerView);

        //레이아웃매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //어댑터 생성, 아이템 추가
        adapter = new PostAdapter(post);

        //리사이클러뷰 연결
        recyclerView.setAdapter(adapter);

    }
}
