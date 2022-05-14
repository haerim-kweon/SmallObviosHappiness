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

public class SearchTab extends Fragment {

    /*
    RecyclerView recyclerView;

    Post_smallAdapter adapter1;
    ArrayList<Post_small> post;
*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.searchtab, container, false);
        initUI(rootView);
        return rootView;
    }


    public void initUI(ViewGroup rootView){

        //레이아웃매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

     /*
        //리사이클러뷰 생성
        recyclerView = rootView.findViewById(R.id.small_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        adapter1 = new Post_smallAdapter(post);
        recyclerView.setAdapter(adapter1);

*/

    }


}
