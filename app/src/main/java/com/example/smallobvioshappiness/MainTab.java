package com.example.smallobvioshappiness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainTab extends Fragment {

    RecyclerView recyclerView;
    PostAdapter adapter;
    ArrayList<Post> post;
    ImageButton notice;
    RequestQueue queue;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.maintab, container, false);
        notice = rootView.findViewById(R.id.gotonotice);


        //알림탭 이동
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        //initUI(rootView);
        return rootView;
    }

    public void initUI(View rootView){


        post = new ArrayList<>();
        Map<String, String>  params = new HashMap<>();


        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET,
                "http://dev.sbch.shop:9000/app/posts?sort=' '", new JSONObject(params),
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray result = response.getJSONArray("result");
                            //제목, 카테고리, 가격, 찜여부, 찜개수, 시간
//                            post.add(result.getString(2), result.getString(3), result.getInt(4), result.getInt(6), result.getInt(7), result.getString(8));


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

                return headers;
            }

        };
        queue.add(joRequest);






        //리사이클러뷰 생성
        recyclerView = rootView.findViewById(R.id.post_RecyclerView);

        //리사이클러뷰 사이 구분선
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));

        //레이아웃매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //어댑터 생성, 아이템 추가
        adapter = new PostAdapter(post);

        //리사이클러뷰 연결
        recyclerView.setAdapter(adapter);

    }
}
