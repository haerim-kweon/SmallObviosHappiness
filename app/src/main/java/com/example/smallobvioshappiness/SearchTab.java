package com.example.smallobvioshappiness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class SearchTab extends Fragment {


    RecyclerView recyclerView;
    Post_Small_Adapter adapter;
    PostAdapter postAdapter;
    ArrayList<Post> post;
    RequestQueue queue;
    ImageButton btn_search;
    EditText editText;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.searchtab, container, false);
        queue = Volley.newRequestQueue(getContext());
        editText = rootView.findViewById(R.id.searchView3);
        textView = rootView.findViewById(R.id.searchtab_textview);

        btn_search = rootView.findViewById(R.id.imageButton21);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post.clear();

                add_search_post(editText.getText().toString());
                textView.setText("'" + editText.getText().toString() + "' 에 대한 검색결과");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //레이아웃매니저 설정
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

                        //리사이클러뷰 생성
                        recyclerView = rootView.findViewById(R.id.search_recyclerview1);
                        recyclerView.setLayoutManager(layoutManager);
                        postAdapter = new PostAdapter(post);


                        //리사이클러뷰 연결
                        recyclerView.setAdapter(postAdapter);

                        postAdapter.setOnItemClicklistener(new OnPostItemClickListener(){
                            @Override
                            public void onItemClick(PostAdapter.ItemViewHolder holder, View view, int position){
                                Post item = postAdapter.getItem(position);

                                int itempostid = item.getPostId();
                                Intent intent = new Intent(getContext(), PostDetailActivity.class);
                                intent.putExtra("postId", itempostid);

                                startActivity(intent);

                            }
                        });

                    }
                },1000);




            }
        });

        initUI(rootView);
        return rootView;


    }


    public void initUI(ViewGroup rootView){

        post = new ArrayList<>();
        //post.add(new Post(0,"제목", "카테고리", 10000, "deal", 3, 3,"1분전",null));

        add_post();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //레이아웃매니저 설정
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(RecyclerView.HORIZONTAL);


                //리사이클러뷰 생성
                recyclerView = rootView.findViewById(R.id.search_recyclerview1);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new Post_Small_Adapter(post);


                //리사이클러뷰 연결
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClicklistener(new OnSmallPostItemClickListener(){
                    @Override
                    public void onItemClick(Post_Small_Adapter.ItemViewHolder holder, View view, int position){
                        Post item = adapter.getItem(position);

                        int itempostid = item.getPostId();
                        Intent intent = new Intent(getContext(), PostDetailActivity.class);
                        intent.putExtra("postId", itempostid);

                        startActivity(intent);

                    }
                });

            }
        },1000);


    }


    public void add_post(){
        String url = "http://dev.sbch.shop:9000/app/posts/";
        JSONObject body = new JSONObject(); //JSON 오브젝트의 body 부분
        SharedPreferences pref = this.getActivity().getSharedPreferences("jwt",0);
        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url+15+"/recommend", body,
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
                                post.add(new Post(element.getInt("postId"), element.getString("title"), "", element.getInt("price"), "", 0, 0, "", element.getString("imgUrl")));

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



    public void add_search_post(String word){
        String url = "http://dev.sbch.shop:9000/app/posts/search?word=";
        Log.d("text", url + word);
        JSONObject body = new JSONObject(); //JSON 오브젝트의 body 부분
        SharedPreferences pref = this.getActivity().getSharedPreferences("jwt",0);
        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url+word, body,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("text_adapter", "d");

                            Log.d("text", response.toString());

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

        //키워드 등록 자동
        String url2 = "http://dev.sbch.shop:9000/app/posts/keyword?word=";
        Log.d("text", url2 + word);
        JSONObject body2 = new JSONObject(); //JSON 오브젝트의 body 부분
        JsonObjectRequest joRequest2 = new JsonObjectRequest(Request.Method.POST, url2+word, body2,
                new Response.Listener<JSONObject>(){//권선동

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("text", "키워드 등록 : "  + response.toString());
                            if(response.getBoolean("isSuccess")){
                                Toast.makeText(getContext(),"'"+ word + "' 키워드가 등록됐습니다", Toast.LENGTH_SHORT).show();
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

        queue.add(joRequest2);

    }




}
