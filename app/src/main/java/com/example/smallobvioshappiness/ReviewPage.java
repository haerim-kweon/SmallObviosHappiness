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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

public class ReviewPage extends Fragment {


    RecyclerView recyclerView;

    ReviewAdapter adapter;
    ArrayList<Review> reviews;

    ImageButton btn_back;
    private FragmentManager fm;
    private FragmentTransaction ft;
    ProfileTab profileTab;
    RequestQueue queue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.reviewpage, container, false);

        queue = Volley.newRequestQueue(getContext());

        profileTab = new ProfileTab();



        //뒤로 가기
        btn_back = rootView.findViewById(R.id.reviewpage_btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.main_frame, profileTab );
                ft.commit();
            }
        });




        initUI(rootView);
        return rootView;
    }


    public void initUI(ViewGroup rootView){
        reviews = new ArrayList<>();
        add_post();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { //리사이클러뷰 생성

                //리사이클러뷰 생성
                recyclerView = rootView.findViewById(R.id.reviewpage_recyclerview);
                //리사이클러뷰 사이 구분선
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
                //레이아웃매니저 설정
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);


                //어댑터 생성, 아이템 추가
                adapter = new ReviewAdapter(reviews);

                //리사이클러뷰 연결
                recyclerView.setAdapter(adapter);

            }
        },300);




    }


    public void add_post(){
        String url = "http://dev.sbch.shop:9000/app/users/review/";
        JSONObject body = new JSONObject(); //JSON 오브젝트의 body 부분
        SharedPreferences pref = this.getActivity().getSharedPreferences("jwt",0);

        int userid = pref.getInt("userId", 0);
        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url+userid, body,
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
                                reviews.add(new Review(element.getString("nick"), element.getString("content"), element.getString("profileImg")));

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
