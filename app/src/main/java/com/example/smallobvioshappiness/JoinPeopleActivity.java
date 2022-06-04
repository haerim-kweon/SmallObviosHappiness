package com.example.smallobvioshappiness;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JoinPeopleActivity extends AppCompatActivity {

    RequestQueue queue;

    RecyclerView recyclerView;
    JoinPersonAdapter adapter;
    ArrayList<JoinPerson> person;
    ImageButton btn_joinpeople_back;
    String onlyjoin = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("text", "JoinPeoPleActivity_Start");
        //Log.d("text", String.valueOf(postId));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.joinpeople);

        Intent intent = getIntent();
        int postId = intent.getIntExtra("postId", 0);

        //뒤로가기 버튼
        btn_joinpeople_back = findViewById(R.id.btn_joinpeople_back);
        btn_joinpeople_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        person = new ArrayList<>();
        add_person(postId);

        //리사이클러뷰 생성
        recyclerView = findViewById(R.id.join_people_recyclerview);

        //리사이클러뷰 사이 구분선
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));

        //레이아웃매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        //어댑터 생성, 아이템 추가
        adapter = new JoinPersonAdapter(person);

        //리사이클러뷰 연결
        recyclerView.setAdapter(adapter);

    }


    public void add_person(int postId){

        queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://dev.sbch.shop:9000/app/posts/joinlist/";
        JSONObject body = new JSONObject(); //JSON 오브젝트의 body 부분

        SharedPreferences pref = getSharedPreferences("jwt",0);
        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url+postId+"?onlyJoin="+onlyjoin, body,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("text", "adapter");
                            Log.d("text", response.toString());
                            JSONArray array = response.getJSONArray("result");
                            Log.d("text", "result 길이 : "+String.valueOf(array.length()));

                            JSONArray jsonArray = response.optJSONArray("result");
                            JSONObject element;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                element = (JSONObject) jsonArray.opt(i);
                                person.add(new JoinPerson(element.getString("nick"), element.getInt("userId")));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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

        queue.add(joRequest);




    }


}