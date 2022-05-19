package com.example.smallobvioshappiness;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //하단네비게이션바
        //하단네비게이션바 화면 보여주기
        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_main:
                        setFrag(0);
                        break;
                    case R.id.bottom_search:
                        setFrag(1);
                        break;
                    case R.id.bottom_add:
                        setFrag(2);
                        break;
                    case R.id.bottom_chat:
                        setFrag(3);
                        break;
                    case R.id.bottom_profile:
                        setFrag(4);
                        break;
                }
                return true;
            }
        });
        frag1 = new MainTab();
        frag2 = new SearchTab();
        //frag3 = new AddTab();
        frag4 = new ChatTab();
        frag5 = new ProfileTab();
        setFrag(0); //어플 실행 첫화면 = 캘린더탭
        //__하단네비게이션바
    }

    //하단네비게이션바
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;

    private MainTab frag1;
    private SearchTab frag2;
  //  private AddTab frag3;
    private ChatTab frag4;
    private ProfileTab frag5;


    //하단 네비게이션바로 화면전환하는 함수
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2);
                ft.commit();
                break;
            case 2:
                Intent intent = new Intent(MainActivity.this, PostUploadActivity.class);
                startActivity(intent);
                break;
            case 3:
                ft.replace(R.id.main_frame, frag4);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.main_frame, frag5);
                ft.commit();
                break;
        }
    }
}