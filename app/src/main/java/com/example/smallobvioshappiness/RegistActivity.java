package com.example.smallobvioshappiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistActivity extends AppCompatActivity {


    private EditText email, password, password2, name, nickname, phone;
    private Button registButton;

    protected void onCreat(Bundle saveInstancestate){
        super.onCreate(saveInstancestate);
        setContentView(R.layout.activity_regist);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        password2 = findViewById(R.id.editTextTextPassword2);
        name = findViewById(R.id.editTextTextPersonName);
        nickname = findViewById(R.id.editTextTextPersonNickname);
        phone = findViewById(R.id.editTextTextPhoneNumber);

        registButton = findViewById(R.id.button4);
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userPassword2 = password2.getText().toString();
                String userName = name.getText().toString();
                String userNickname = nickname.getText().toString();
                String userPhone = phone.getText().toString();

                Response.Listener<String> response_listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("isSuccess");
                            //회원가입 성공
                            if(success==true){

                                Intent intent = new Intent(RegistActivity.this, Signin_4.class);
                            }
                            //회원가입 실패
                            else {
                                Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT);
                                return;
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(userEmail, userPassword, userName, userNickname, userPhone, response_listener);
                RequestQueue queue = Volley.newRequestQueue(RegistActivity.this);


            }
        });
    }


}