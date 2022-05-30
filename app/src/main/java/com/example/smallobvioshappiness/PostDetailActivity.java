package com.example.smallobvioshappiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class PostDetailActivity extends AppCompatActivity {

    RequestQueue queue;
    ImageButton btn_back, btn_interest;
    Button btn_participation;
    TextView userNick, userLocation, category, createdAt, title, price, participant, deadline, contents;
    String transactionStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("text", "PostDetailActivity_start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        Intent intent = getIntent();
        int postId = intent.getIntExtra("postId", 0);

        btn_back = findViewById(R.id.imageButton14);
        btn_interest = findViewById(R.id.imageButton17);
        btn_participation = findViewById(R.id.btn_participate);

        userNick = findViewById(R.id.textView20);
        userLocation = findViewById(R.id.postuser_location);
        createdAt = findViewById(R.id.post_createdAt);

        category = findViewById(R.id.post_category);
        title = findViewById(R.id.post_title);
        price = findViewById(R.id.post_price);
        contents = findViewById(R.id.post_contents);
        participant = findViewById(R.id.post_participant);
        deadline = findViewById(R.id.post_deadline);


        queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://dev.sbch.shop:9000/app/posts/";

        JSONObject body = new JSONObject(); //JSON 오브젝트의 body 부분
        SharedPreferences pref = getSharedPreferences("jwt",0);
        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url+postId, body,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject result = response.getJSONObject("result");
                            title.setText(result.getString("title"));
                            category.setText(result.getString("category"));
                            createdAt.setText(result.getString("createdAt"));
                            price.setText(String.valueOf(result.getInt("price"))+" 원");
                            userNick.setText(result.getString("nick"));
                            userLocation.setText(result.getString("town"));
                            contents.setText(result.getString("content"));
                            deadline.setText(result.getString("date"));
                            participant.setText(String.valueOf(result.getString("num"))+"명");
                            transactionStatus = result.getString("transactionStatus");
                            if(result.getInt("interestStatus")==1){
                                btn_interest.setSelected(true);
                            }
                            else{
                                btn_interest.setSelected(false);
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

        Log.d("text", "body : " + body.toString());
        queue.add(joRequest);


        //찜 누르기
        btn_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_interest.isSelected()){
                    btn_interest.setSelected(false);
                }
                else{
                    btn_interest.setSelected(true);
                }

                String url2 = "http://dev.sbch.shop:9000/app/posts/interest/";

                Log.d("text", url2+postId);
                JSONObject body2 = new JSONObject(); //JSON 오브젝트의 body 부분
                //SharedPreferences pref = getSharedPreferences("jwt",0);
                JsonObjectRequest joRequest2 = new JsonObjectRequest(Request.Method.GET, url2+postId, body2,
                        new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("text", response.toString());
                                    JSONObject result = response.getJSONObject("result");
                                    if(response.getBoolean("isSuccess")){
                                        if(result.getInt("status")==0){
                                            Toast.makeText(getApplicationContext(), "찜하기 취소", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(result.getInt("status")==1){
                                            Toast.makeText(getApplicationContext(), "찜하기 성공", Toast.LENGTH_SHORT).show();
                                        }
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

                Log.d("text", "body2 : " + body2.toString());
                queue.add(joRequest2);



            }
        });

        //참여하기
        btn_participation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url3 = "http://dev.sbch.shop:9000/app/posts/join/";
                //Log.d("text", url3+postId+"/translate?status="+transactionStatus);

                JSONObject body3 = new JSONObject(); //JSON 오브젝트의 body 부분
                //SharedPreferences pref = getSharedPreferences("jwt",0);
                JsonObjectRequest joRequest2 = new JsonObjectRequest(Request.Method.GET, url3+postId, body3,
                        new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("text", response.toString());
                                    JSONObject result = response.getJSONObject("result");
                                    Toast.makeText(getApplicationContext(), response.getString("result"), Toast.LENGTH_SHORT).show();

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

                Log.d("text", "body3 : " + body3.toString());
                queue.add(joRequest2);

            }
        });
    }
}