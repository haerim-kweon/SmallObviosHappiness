package com.example.smallobvioshappiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


public class PostUploadActivity extends AppCompatActivity {



    private EditText title, price, explain;
    private ImageButton btn_back, btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtab);

        btn_back = findViewById(R.id.imageButton14);
        btn_add = findViewById(R.id.imageButton4);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }
}