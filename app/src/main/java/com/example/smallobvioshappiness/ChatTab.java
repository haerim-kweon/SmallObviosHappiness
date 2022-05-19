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

import java.util.ArrayList;

public class ChatTab extends Fragment {

    RecyclerView recyclerView;
    ChatAdapter adapter;
    ArrayList<Chat> chat;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.chattab, container, false);

        initUI(rootView);
        return rootView;
    }

    public void initUI(ViewGroup rootView) {
        chat = new ArrayList<>();
        chat.add(new Chat("눈송이", "안녕하세요"));
        chat.add(new Chat("눈송이", "안녕하세요"));
        chat.add(new Chat("눈송이", "안녕하세요"));
        chat.add(new Chat("눈송이", "안녕하세요"));
        chat.add(new Chat("눈송이", "안녕하세요"));
        chat.add(new Chat("눈송이", "안녕하세요"));
        chat.add(new Chat("눈송이", "안녕하세요"));
        chat.add(new Chat("눈송이", "안녕하세요"));
        chat.add(new Chat("눈송이", "안녕하세요"));
        chat.add(new Chat("눈송이", "안녕하세요"));

        //리사이클러뷰 생성
        recyclerView = rootView.findViewById(R.id.chat_recyclerview);

        //리사이클러뷰 사이 구분선
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));

        //레이아웃매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //어댑터 생성, 아이템 추가
        adapter = new ChatAdapter(chat);

        //리사이클러뷰 연결
        recyclerView.setAdapter(adapter);
    }
}
