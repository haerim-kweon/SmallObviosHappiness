package com.example.smallobvioshappiness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import com.android.volley.toolbox.Volley;

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
        queue = Volley.newRequestQueue(getContext());


        //알림탭 이동
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        initUI(rootView);
        return rootView;
    }

    public void initUI(View rootView){


        post = new ArrayList<>();
        //post.add(new Post(0,"제목", "카테고리", 10000, "deal", 3, 3,"1분전",null));

        add_post();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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

                adapter.setOnItemClicklistener(new OnPostItemClickListener(){
                    @Override
                    public void onItemClick(PostAdapter.ItemViewHolder holder, View view, int position){
                        Post item = adapter.getItem(position);
                        Toast.makeText(getContext(),"제목 : "+item.getTitle()+" ID : " + item.getPostId(), Toast.LENGTH_SHORT).show();

                        int itempostid = item.getPostId();
                        Intent intent = new Intent(getContext(), PostDetailActivity.class);
                        intent.putExtra("postId", itempostid);

                        startActivity(intent);

                    }

                });



//내가 실행하고 싶은 코드


            }
        },1000);



    }

    public void add_post(){
        String url = "http://dev.sbch.shop:9000/app/posts?sort=''&town=권선동";
        JSONObject body = new JSONObject(); //JSON 오브젝트의 body 부분
        SharedPreferences pref = this.getActivity().getSharedPreferences("jwt",0);
        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url, body,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("text_adapter", "d");

                            Log.d("text?", response.toString());

                            JSONArray array = response.getJSONArray("result");
                            Log.d("text", "result 길이 : "+String.valueOf(array.length()));

                            JSONArray jsonArray = response.optJSONArray("result");

                            JSONObject element;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                element = (JSONObject) jsonArray.opt(i);
                                Log.d("text?", element.toString());
                                post.add(new Post(element.getInt("postId"), element.getString("title"), element.getString("category"), element.getInt("price"), element.getString("transactionStatus"), element.getInt("interestStatus"), element.getInt("interestNum"), element.getString("createdAt"), element.getString("imgUrl")));

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