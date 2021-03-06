package com.example.smallobvioshappiness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
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
import com.android.volley.toolbox.JsonArrayRequest;
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
    ImageButton notice, locationSelect, filter;
    RequestQueue queue;
    TextView location, filter_text;

    String location_selection, location1, location2, filterSelection;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.maintab, container, false);
        notice = rootView.findViewById(R.id.gotonotice);
        queue = Volley.newRequestQueue(getContext());

        location = rootView.findViewById(R.id.maintab_location);
        locationSelect = rootView.findViewById(R.id.location_select);
        filterSelection = "";

        //??????
        filter = rootView.findViewById(R.id.maintab_filter);
        filter_text = rootView.findViewById(R.id.maintab_filter_text);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //????????????
                if(filter.isSelected()){
                    filter.setSelected(false);
                    filter_text.setTextColor(Color.parseColor("#C4C4C4"));
                    filterSelection = "";

                    add_post();
                    initUI(rootView);


                }
                //????????????
                else{
                    filter.setSelected(true);
                    filter_text.setTextColor(Color.parseColor("#4955FD"));
                    filterSelection = "ongoing";

                    add_post();
                    initUI(rootView);
                }
            }
        });

        //?????? ?????? ??????
        SharedPreferences pref = this.getActivity().getSharedPreferences("jwt",0);
        JsonArrayRequest locationRequest = new JsonArrayRequest(Request.Method.GET, "http://dev.sbch.shop:9000/app/posts/location", null,
                new Response.Listener<JSONArray>(){//?????????

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("text1", response.toString());
                            location1 = response.getJSONObject(0).getString("town");
                            location.setText(location1);
                            location_selection = location1;
                            location2 = response.getJSONObject(1).getString("town");
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
                                Log.d("text", "??????"+jo.toString());
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
        queue.add(locationRequest);


        //?????? ??????
        locationSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.location_select, popupMenu.getMenu());

                MenuItem item = popupMenu.getMenu().findItem(R.id.location1);
                item.setTitle(location1);

                MenuItem item1 = popupMenu.getMenu().findItem(R.id.location2);
                item1.setTitle(location2);


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //menuItem.setTitle();
                        //menuItem.setTitle(location1);
                        //menuItem.setTitle(location2);

                        if (menuItem.getItemId() == R.id.location1) {
                            location_selection = location1;
                            location.setText(location1);
                        }
                        else{

                            location_selection = location2;
                            location.setText(location2);
                        }

                        add_post();
                        initUI(rootView);
                        return false;


                    }
                });

                popupMenu.show();

            }
        });


        //????????? ??????
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initUI(rootView);
            }
        },300);
        return rootView;


    }

    public void initUI(View rootView){
        post = new ArrayList<>();
        //post.add(new Post(0,"??????", "????????????", 10000, "deal", 3, 3,"1??????",null));

        add_post();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //?????????????????? ??????
                recyclerView = rootView.findViewById(R.id.post_RecyclerView);
                //?????????????????? ?????? ?????????
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));

                //????????????????????? ??????
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);

                //????????? ??????, ????????? ??????

                adapter = new PostAdapter(post);
                //?????????????????? ??????
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClicklistener(new OnPostItemClickListener(){
                    @Override
                    public void onItemClick(PostAdapter.ItemViewHolder holder, View view, int position){
                        Post item = adapter.getItem(position);
                        //Toast.makeText(getContext(),"?????? : "+item.getTitle()+" ID : " + item.getPostId(), Toast.LENGTH_SHORT).show();

                        int itempostid = item.getPostId();
                        Intent intent = new Intent(getContext(), PostDetailActivity.class);
                        intent.putExtra("postId", itempostid);

                        startActivity(intent);

                    }

                });


            }
        },300);



    }

    public void add_post(){
        post.clear();
        String url = "http://dev.sbch.shop:9000/app/posts?sort="+filterSelection+"&town=";//?????????
        Log.d("text", "add_post URL : " + url + location_selection);
        JSONObject body = new JSONObject(); //JSON ??????????????? body ??????
        SharedPreferences pref = this.getActivity().getSharedPreferences("jwt",0);
        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url+location_selection, body,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("text_adapter", "d");

                            Log.d("text?", response.toString());

                            JSONArray array = response.getJSONArray("result");
                            Log.d("text", "result ?????? : "+String.valueOf(array.length()));

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
                                Log.d("text", "??????"+jo.toString());
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