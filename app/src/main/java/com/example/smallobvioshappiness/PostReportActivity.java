package com.example.smallobvioshappiness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

//게시글 신고하기 - 33번
public class PostReportActivity extends AppCompatActivity {
    private Button postReportButton; //신고하기 버튼
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtab);
        queue = Volley.newRequestQueue(this);


        postReportButton = findViewById(R.id.btn_kakao); //여기 수정해야함..진짜 신고하기 버튼으로

        SharedPreferences pref = getSharedPreferences("jwt",0);

        postReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //신고할 postid 받아야됨
                int postId=1;


                JSONObject jsonObject = new JSONObject(); //head오브젝트와 body오브젝트를 담을 JSON오브젝트
                JSONObject headers = new JSONObject(); //JSON 오브젝트의 head 부분

                try {
                    //head 생성
                    headers.put("Content-Type", "application/json;charset=utf-8");
                    headers.put("X-ACCESS-TOKEN", pref.getString("jwt","") );


                    //jsonObject.put("head", headers); //head 오브젝트 추가


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url="http://dev.sbch.shop:9000/app/posts/report/"+postId;
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
                                    Toast.makeText(getApplicationContext(), "신고 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PostReportActivity.this, MainActivity.class);
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
