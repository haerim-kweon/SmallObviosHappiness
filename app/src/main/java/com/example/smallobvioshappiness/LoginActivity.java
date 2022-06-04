package com.example.smallobvioshappiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button login, regist;

    RequestQueue queue;

    protected void onCreate(Bundle saveInstancestate){
        super.onCreate(saveInstancestate);
        queue = Volley.newRequestQueue(this);

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
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                Map<String, String>  params = new HashMap<>();

                params.put("email", userEmail);
                params.put("password",userPassword);


                JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.POST,
                        "http://dev.sbch.shop:9000/app/users/logIn", new JSONObject(params),
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
                                    try {
                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                        JSONObject result = response.getJSONObject("result");
                                        String jwt = result.getString("jwt");
                                        int userid = result.getInt("userIdx");
                                        SharedPreferences pref = getSharedPreferences("jwt",0);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("jwt", jwt );
                                        editor.putInt("userId", userid);
                                        editor.commit();
                                        Log.d("text", pref.getString("jwt",""));
                                        Log.d("text", String.valueOf(pref.getInt("userId", 0)));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);

                                }
                                //회원가입 실패
                                else {
                                    try {
                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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
                //

            }
        });
    }


}