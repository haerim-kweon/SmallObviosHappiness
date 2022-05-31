package com.example.smallobvioshappiness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//키워드 등록, 키워드 조회  - 30, 34
public class keywordActivity extends AppCompatActivity {
    private EditText keyword; //등록 키워드
    private List<String> keywords; //조회한 키워드들
    private Button btn_keywordAdd,btn_keywordGet; //키워드 등록 버튼, 조회 버튼
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtab);
        queue = Volley.newRequestQueue(this);


        keyword = findViewById(R.id.editTextEmailAddress); //여기 수정해야함..진짜 등록할 키워드로

        btn_keywordAdd=findViewById(R.id.btn_kakao); //여기 수정해야함.. 진짜 키워드 등록 하는 버튼으로
        btn_keywordGet=findViewById(R.id.btn_kakao); //여기 수정해야함.. 진짜 키워드 조회 하는 버튼으로

        SharedPreferences pref = getSharedPreferences("jwt",0);

        //키워드 등록
        btn_keywordAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userkeyword = keyword.getText().toString();


                JSONObject jsonObject = new JSONObject(); //head오브젝트와 body오브젝트를 담을 JSON오브젝트
                JSONObject headers = new JSONObject(); //JSON 오브젝트의 head 부분

                try {
                    //head 생성
                    headers.put("Content-Type", "application/json;charset=utf-8");
                    headers.put("X-ACCESS-TOKEN", pref.getString("jwt","") );


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url="http://dev.sbch.shop:9000/app/posts/keyword?word="+userkeyword;
                JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET,
                        url,jsonObject, //jsonobject말고 뭐 넣어야할지 모르겠음
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
                                    Toast.makeText(getApplicationContext(), "키워드 등록 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(keywordActivity.this, MainActivity.class);
                                    startActivity(intent);

                                }
                                //업로드 실패
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
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json;charset=utf-8");
                        headers.put("X-ACCESS-TOKEN", pref.getString("jwt","") );

                        return headers;
                    }

                };

                Log.d("text", jsonObject.toString());
                queue.add(joRequest);

            }
        });

        //키워드 조회
        btn_keywordGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int userId=1; //신뢰도 평가할 유저 id..어디서 받아와야함

                //Map<String, String> params = new HashMap<>();

                JSONObject jsonObject = new JSONObject(); //head오브젝트와 body오브젝트를 담을 JSON오브젝트
                JSONObject headers = new JSONObject(); //JSON 오브젝트의 head 부분


                try {
                    //head 생성
                    headers.put("Content-Type", "application/json;charset=utf-8");
                    headers.put("X-ACCESS-TOKEN", pref.getString("jwt","") );


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.POST,
                        "http://dev.sbch.shop:9000/app/users/keyword",jsonObject, //jsonobject말고 뭐 넣어야할지 모르겠음
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
                                    Toast.makeText(getApplicationContext(), "키워드 조회 성공", Toast.LENGTH_SHORT).show();

                                    //성공하면 키워드 받기?
                                    JSONArray jsonArray = response.optJSONArray("result");

                                    JSONObject element;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        element = (JSONObject) jsonArray.opt(i);
                                        try {
                                            keywords.add(element.getString("keyword"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    Intent intent = new Intent(keywordActivity.this, MainActivity.class);
                                    startActivity(intent);

                                }
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
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json;charset=utf-8");
                        headers.put("X-ACCESS-TOKEN", pref.getString("jwt","") );

                        return headers;
                    }

                };

                Log.d("text", jsonObject.toString());
                queue.add(joRequest);



            }
        });

    }
}
