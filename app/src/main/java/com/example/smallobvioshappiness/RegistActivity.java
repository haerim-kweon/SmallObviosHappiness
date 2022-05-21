package com.example.smallobvioshappiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistActivity extends AppCompatActivity {


    MemberDTO dto;
    private TextView passwordCheck;
    private EditText email, password, password2, name, nickname, phone;
    private Button registButton;
    private ImageButton btn_Back, btn_agreeAll, btn_agree2, btn_agree3, btn_agree4;

    protected void onCreate(Bundle saveInstancestate){
        super.onCreate(saveInstancestate);
        setContentView(R.layout.signin_3);

        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextPassword);
        password2 = findViewById(R.id.editTextPassword2);
        name = findViewById(R.id.editTextPersonName);
        nickname = findViewById(R.id.editTextPersonNickname);
        phone = findViewById(R.id.editTextPhoneNumber);


        passwordCheck = findViewById(R.id.textView8);
        btn_agreeAll = findViewById(R.id.imageButton2);
        btn_agree2 = findViewById(R.id.imageButton3);
        btn_agree3 = findViewById(R.id.imageButton4);
        btn_agree4 = findViewById(R.id.imageButton5);

        btn_Back = findViewById(R.id.imageButton);


        dto=new MemberDTO();




        //뒤로가기 버튼 클릭시 액티비티 종료
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_agreeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        registButton = findViewById(R.id.btn_regist);
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("text", email.getText().toString());

                //입력된 정보 가져오기
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userPassword2 = password2.getText().toString();
                String userName = name.getText().toString();
                String userNickname = nickname.getText().toString();
                String userPhone = phone.getText().toString();


                /*
                //비밀번호 확인
                if(userPassword.equals(userPassword2) == false && userPassword2.isEmpty()==false){
                    passwordCheck.setVisibility(View.VISIBLE);
                }
                else if(userPassword.equals(userPassword2)) {
                    passwordCheck.setText("사용할 수 있는 비밀번호입니다");
                }
*/


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("text2", "aaa");
                            // 스트링을 json오브젝트 형태로 전송
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("isSuccess");


                            //회원가입 성공
                            if(success){
                                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistActivity.this, Signin_4.class);

                            }
                            //회원가입 실패
                            else {
                                Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //Volley 라이브러리, 서버통신
                RegisterRequest registerRequest = new RegisterRequest(userEmail,  userName, userPhone, userPassword, userPassword2,  userNickname,  responseListener);

                Log.d("text", "aaa");
                RequestQueue queue = Volley.newRequestQueue(RegistActivity.this);

                Log.d("text", "ccc");

                queue.add(registerRequest);

                Log.d("text", "eee");


            }
        });
    }


}