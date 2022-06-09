package com.example.smallobvioshappiness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mypage_frag2 extends Fragment {
    RecyclerView recyclerView;
    Post_List_Adapter adapter;
    ArrayList<Post_List> post_lists;
    RequestQueue queue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.mypage_frag2, container, false);

        Log.d("text", "Mypage_frag2 Start");
        queue = Volley.newRequestQueue(getContext());

        initUI(rootView);
        return rootView;
    }

    public void initUI(ViewGroup rootView) {
        post_lists = new ArrayList<>();
        add_post();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { //리사이클러뷰 생성
                recyclerView = rootView.findViewById(R.id.mypage_frag2_recyclerview);


                //리사이클러뷰 사이 구분선
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
                //레이아웃매니저 설정
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);

                //어댑터 생성, 아이템 추가
                adapter = new Post_List_Adapter(post_lists);
                adapter.setOnItemClicklistener(new OnPostListItemClickListener(){
                    @Override
                    public void onItemClick(Post_List_Adapter.ItemViewHolder holder, View view, int position){
                        Post_List item = adapter.getItem(position);

                        int itempostid = item.getId();
                        Intent intent = new Intent(getContext(), PostDetailActivity.class);
                        intent.putExtra("postId", itempostid);

                        startActivity(intent);

                    }

                });
                //리사이클러뷰 연결
                recyclerView.setAdapter(adapter);


            }
        },300);
    }

    public void add_post(){
        String url = "http://dev.sbch.shop:9000/app/users/host/";
        JSONObject body = new JSONObject(); //JSON 오브젝트의 body 부분
        SharedPreferences pref = this.getActivity().getSharedPreferences("jwt",0);
        int userId = pref.getInt("userId",0);
        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url+userId, body,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray array = response.getJSONArray("result");
                            Log.d("text", "result 길이 : "+String.valueOf(array.length()));

                            JSONArray jsonArray = response.optJSONArray("result");

                            JSONObject element;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                element = (JSONObject) jsonArray.opt(i);
                                Log.d("text?", element.toString());
                                post_lists.add(new Post_List(element.getInt("postId"),element.getString("title"), element.getString("category"),element.getString("imgUrl"), element.getInt("price")));

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
