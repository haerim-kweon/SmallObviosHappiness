package com.example.smallobvioshappiness;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class NoticeActivity extends AppCompatActivity {

    Toolbar toolbar;

    News news;
    Notice_ChatTab chat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticepage);

        news = new News();
        chat = new Notice_ChatTab();

        getSupportFragmentManager().beginTransaction().replace(R.id.chat_container, news).commit();

        TabLayout tabs = findViewById(R.id.notice_tabs);
        tabs.addTab(tabs.newTab().setText("소식"));
        tabs.addTab(tabs.newTab().setText("채팅"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("NoticeActivity", "선택된 탭 : " + position);

                Fragment selected = null;
                if(position == 0){
                    selected = news;
                } else{
                    selected = chat;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.chat_container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}