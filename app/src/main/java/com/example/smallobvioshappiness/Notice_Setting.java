package com.example.smallobvioshappiness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Notice_Setting extends Fragment {


    ImageButton btn_back;
    private FragmentManager fm;
    private FragmentTransaction ft;
    ProfileTab profileTab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.notice_setting, container, false);
        btn_back = rootView.findViewById(R.id.notice_setting_btn_back);
        profileTab = new ProfileTab();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.main_frame,profileTab );
                ft.commit();
            }
        });



        return rootView;



    }




}
