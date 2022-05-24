package com.example.smallobvioshappiness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainTab extends Fragment {

    RecyclerView recyclerView;
    PostAdapter adapter;
    ArrayList<Post> post;
    ImageButton notice;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.maintab, container, false);
        Log.d("text", "a");
        notice = rootView.findViewById(R.id.gotonotice);
        Log.d("text", "b");
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("text", "c");
                Intent intent = new Intent(getContext(), NoticeActivity.class);
                Log.d("text", "d");
                startActivity(intent);
            }
        });

        initUI(rootView);
        return rootView;
    }

    public void initUI(View rootView){
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

        //리사이클러뷰 사이 구분선
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));

        //레이아웃매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //어댑터 생성, 아이템 추가
        adapter = new PostAdapter(post);

        //리사이클러뷰 연결
        recyclerView.setAdapter(adapter);

    }
}
