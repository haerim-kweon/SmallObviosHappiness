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

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button login, regist;

    protected void onCreat(Bundle saveInstancestate){
        super.onCreate(saveInstancestate);
        setContentView(R.layout.signin_2);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.button2);
        regist = findViewById(R.id.button3);


        //회원가입버튼 누르면 실행
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });


        //로그인 버튼 누르면 실행
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                Response.Listener<String> response_listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("isSuccess");
                            //로그인 성공
                            if(success==true){
                                String userEmail = jsonObject.getString("userEmail");
                                String userPassword = jsonObject.getString("userPassword");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userEmail", userEmail);
                                intent.putExtra("userPassword", userPassword);

                            }
                            //로그인 실패
                            else {
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT);
                                return;
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                LoginRequest loginRequest = new LoginRequest(userEmail, userPassword, response_listener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }


}
