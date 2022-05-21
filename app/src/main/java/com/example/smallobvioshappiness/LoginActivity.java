package com.example.smallobvioshappiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button login, regist;

    protected void onCreate(Bundle saveInstancestate){
        super.onCreate(saveInstancestate);
        setContentView(R.layout.signin_2);

        email = findViewById(R.id.login_EmailAddress);
        password = findViewById(R.id.login_Password);

        login = findViewById(R.id.btn_login);
        regist = findViewById(R.id.btn_gotoregist);


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
                Log.d("text", "a");
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                Response.Listener<String> response_listener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("text", "b");
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("isSuccess");


                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            //로그인 성공
                            if(success){
                                Log.d("text", "c");
                                String userEmail = jsonArray.getString(0);
                                String userPassword = jsonArray.getString(1);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("email", userEmail);
                                intent.putExtra("password", userPassword);
                                startActivity(intent);

                            }
                            //로그인 실패
                            else {
                                Log.d("text", "d");
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }


                        } catch (JSONException e) {
                            Log.d("text", "e");
                            e.printStackTrace();
                        }

                    }
                };

                Log.d("text", "1");
                LoginRequest loginRequest = new LoginRequest(userEmail, userPassword, response_listener);
                Log.d("text", "2");
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                Log.d("text", "3");
                queue.add(loginRequest);
                Log.d("text", "4");
            }
        });
    }


}
