package com.example.smallobvioshappiness;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class ProfileTab extends Fragment {
    private View view;

    News news;
    Notice_ChatTab chat;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profiletab, container, false);


        news = new News();
        chat = new Notice_ChatTab();

        getFragmentManager().beginTransaction().replace(R.id.chat_container, news).commit();

        TabLayout tabs = view.findViewById(R.id.mypage_tabs);
        tabs.addTab(tabs.newTab().setText("참여내역"));
        tabs.addTab(tabs.newTab().setText("주최한내역"));
        tabs.addTab(tabs.newTab().setText("찜한내역"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("NoticeActivity", "선택된 탭 : " + position);

                Fragment selected = null;
                if(position == 0){
                    selected = news;
                } else if(position == 1){
                    selected = chat;
                } else{
                    //selected
                }

                getFragmentManager().beginTransaction().replace(R.id.mypage_container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





        return view;

    }

}
