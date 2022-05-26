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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegistActivity extends AppCompatActivity {

    private TextView passwordCheck;
    private EditText email, password, password2, name, nickname, phone;
    private Button registButton;
    private ImageButton btn_Back, btn_agreeAll, btn_agree2, btn_agree3, btn_agree4;
    RequestQueue queue;

    protected void onCreate(Bundle saveInstancestate){
        super.onCreate(saveInstancestate);
        queue = Volley.newRequestQueue(this);

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

                //입력된 정보 가져오기
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userPassword2 = password2.getText().toString();
                String userName = name.getText().toString();
                String userNickname = nickname.getText().toString();
                String userPhone = phone.getText().toString();
                Map<String, String>  params = new HashMap<>();

                // params.put("Content-type", "application/json;charset=utf-8");
                params.put("email", userEmail);
                params.put("name", userName);
                params.put("password",userPassword);
                params.put("password2",userPassword2);
                params.put( "phone", userPhone);
                params.put("nick",userNickname);

                JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.POST,
                        "http://dev.sbch.shop:9000/app/users", new JSONObject(params),
                        new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("text", "결과"+ response.toString());
                                boolean success = false;
                                try {
                                    success = response.getBoolean("isSuccess");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(success){
                                    Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                }
                                //회원가입 실패
                                else {
                                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        },

                        new Response.ErrorListener(){

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                NetworkResponse rep = error.networkResponse;
                                if(error instanceof ServerError && rep != null){
                                    try{
                                        String r = new String(rep.data, HttpHeaderParser.parseCharset(rep.headers, "utf-8"));
                                        JSONObject jo = new JSONObject(r);
                                        Log.d("text", "결과"+jo.toString());
                                    }catch(UnsupportedEncodingException | JSONException e1){
                                        e1.printStackTrace();
                                    }
                                }
                                Log.d("text", "ERROR: "+error.getMessage());
                            }
                        }
                        )
                {

                   @Override
                    public Map<String, String> getHeaders() throws AuthFailureError{
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json;charset=utf-8");

                        return headers;
                    }

                };
                queue.add(joRequest);

            }
        });
    }


}