package com.example.smallobvioshappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView, recommend_recyclerView;
    Post_Small_Adapter post_small_adapter;
    ArrayList<Post> post;

    CommentAdapter adapter;
    ArrayList<Comment> comments;

    String imageUrl, user_imageUrl;
    RequestQueue queue;
    ImageButton btn_back, btn_interest, menu, btn_add_comment;
    Button btn_participation;
    TextView userNick, userLocation, category, createdAt, title, price, participant, deadline, contents;
    String transactionStatus;
    int post_userId;
    ConstraintLayout profile;
    int postId;
    EditText edittext_comment;
    ImageView imageView, user_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("text", "PostDetailActivity_start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", 0);

        btn_back = findViewById(R.id.imageButton14);
        btn_interest = findViewById(R.id.imageButton17);
        btn_participation = findViewById(R.id.btn_participate);
        menu = findViewById(R.id.imageButton19);

        userNick = findViewById(R.id.textView20);
        userLocation = findViewById(R.id.postuser_location);
        createdAt = findViewById(R.id.post_createdAt);

        category = findViewById(R.id.post_category);
        title = findViewById(R.id.post_title);
        price = findViewById(R.id.post_price);
        contents = findViewById(R.id.post_contents);
        participant = findViewById(R.id.post_participant);
        deadline = findViewById(R.id.post_deadline);

        profile = findViewById(R.id.post_detail_profile);
        edittext_comment = findViewById(R.id.edittext_comment);
        btn_add_comment = findViewById(R.id.btn_add_comment);

        imageView = findViewById(R.id.post_detail_imageView);
        user_image = findViewById(R.id.imageView2);
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://dev.sbch.shop:9000/app/posts/";

        JSONObject body = new JSONObject(); //JSON ??????????????? body ??????
        SharedPreferences pref = getSharedPreferences("jwt",0);
        int userId = pref.getInt("userId", 0);

        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url+postId, body,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject result = response.getJSONObject("result");
                            title.setText(result.getString("title"));
                            category.setText(result.getString("category"));
                            createdAt.setText(result.getString("createdAt"));
                            StringBuffer string_price = new StringBuffer(String.valueOf(result.getInt("price")));
                            if(string_price.length()>3){
                                string_price.insert(string_price.length()-3, ",");
                            }

                            price.setText(string_price+" ???");

                            userNick.setText(result.getString("nick"));
                            userLocation.setText(result.getString("town"));
                            contents.setText(result.getString("content"));
                            deadline.setText(result.getString("date"));
                            participant.setText(String.valueOf(result.getString("joinNum")+" / "+result.getString("num"))+"???");
                            //transactionStatus = result.getString("transactionStatus");
                            post_userId = result.getInt("userId");

                            //????????? ?????????
                            imageUrl = result.getJSONArray("imgUrls").getString(0);
                            user_imageUrl = result.getString("profileImg");

                            Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
                            Glide.with(getApplicationContext()).load(user_imageUrl).circleCrop().into(user_image);




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

        //Log.d("text", "body : " + body.toString());
        queue.add(joRequest);


        //?????? ?????? ??????
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //????????? ????????? ??????
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PostDetailActivity.this, OtherProfileActivity.class);
                intent.putExtra("postUserId", post_userId);
                Log.d("text", "postUserId : " + String.valueOf(post_userId));
                startActivity(intent);
            }
        });

        //bottom_sheet
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("text", "userid : " + String.valueOf(userId) + "/ post_userId : " + String.valueOf(post_userId));
                //??? ???????????? ??????
                if(userId == post_userId){
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PostDetailActivity.this, R.style.BottomSheetDialogTheme);
                    View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                            R.layout.bottom_sheet, (LinearLayout)findViewById(R.id.bottom_sheet1)
                    );

                    TextView post_modify, status_modify, post_remove, join_people;
                    post_modify = bottomSheetView.findViewById(R.id.post_modify);
                    status_modify = bottomSheetView.findViewById(R.id.status_modify);
                    post_remove = bottomSheetView.findViewById(R.id.post_remove);
                    join_people = bottomSheetView.findViewById(R.id.join_people1);

                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();//?????????

                    //?????? ?????? ????????????
                    status_modify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("text", "abc");
                            PopupMenu popupMenu = new PopupMenu(PostDetailActivity.this, view);
                            getMenuInflater().inflate(R.menu.translation_status_menu, popupMenu.getMenu());

                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    if (menuItem.getItemId() == R.id.status_ongoing) {
                                        transactionStatus = "open";
                                        transactionStatus_modify(postId);
                                    } else if (menuItem.getItemId() == R.id.status_translation) {
                                        transactionStatus = "deal";
                                        transactionStatus_modify(postId);
                                    } else {
                                        transactionStatus = "complete";
                                        transactionStatus_modify(postId);
                                    }


                                    return false;



                                }
                            });

                            popupMenu.show();


                        }

                    });

                    //?????? ????????? _ ????????? ?????? ??????
                    join_people.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent2 = new Intent(PostDetailActivity.this, JoinPeopleActivity.class);
                            intent2.putExtra("postId", postId);
                            Log.d("text", "postId" + String.valueOf(postId));
                            startActivity(intent2);
                        }
                    });

                    //????????? ????????????
                    post_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String url2 = "http://dev.sbch.shop:9000/app/posts/";

                            Log.d("text", url2+postId);
                            JSONObject body2 = new JSONObject(); //JSON ??????????????? body ??????
                            //SharedPreferences pref = getSharedPreferences("jwt",0);
                            JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.DELETE, url2+postId, body2,
                                    new Response.Listener<JSONObject>(){

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                Log.d("text", response.toString());
                                                JSONObject result = response.getJSONObject("result");


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
                    });

                }

                //?????? ?????? ???????????? ??????
                else {

                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PostDetailActivity.this, R.style.BottomSheetDialogTheme);
                    View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                            R.layout.bottom_sheet2, (LinearLayout)findViewById(R.id.bottom_sheet2)

                    );

                    TextView post_complain, join_people2;
                    post_complain = bottomSheetView.findViewById(R.id.post_complain);
                    join_people2 = bottomSheetView.findViewById(R.id.join_people2);

                    //????????? ????????????
                    post_complain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url2 = "http://dev.sbch.shop:9000/app/posts/report/";

                            Log.d("text", url2+postId);
                            JSONObject body2 = new JSONObject(); //JSON ??????????????? body ??????
                            //SharedPreferences pref = getSharedPreferences("jwt",0);
                            JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url2+post_userId, body2,
                                    new Response.Listener<JSONObject>(){

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                Log.d("text", response.toString());
                                                JSONObject result = response.getJSONObject("result");
                                                if(response.getBoolean("isSuccess")){
                                                    Toast.makeText(getApplicationContext(), "?????? ???????????? ??????????????????", Toast.LENGTH_SHORT).show();
                                                    finish();
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
                    });
                    //?????? ????????? _ ????????????
                    join_people2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url2 = "http://dev.sbch.shop:9000/app/users/block/";

                            Log.d("text", url2+post_userId);
                            JSONObject body2 = new JSONObject(); //JSON ??????????????? body ??????
                            //SharedPreferences pref = getSharedPreferences("jwt",0);
                            JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url2+post_userId, body2,
                                    new Response.Listener<JSONObject>(){

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                Log.d("text", response.toString());
                                                JSONObject result = response.getJSONObject("result");
                                                if(result.getBoolean("isSuccess")){
                                                    Toast.makeText(getApplicationContext(), "???????????? ??????????????????", Toast.LENGTH_LONG).show();
                                                    finish();
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
                    });


                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();
                }

            }
        });

        //??? ?????????
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
                JSONObject body2 = new JSONObject(); //JSON ??????????????? body ??????
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
                                            Toast.makeText(getApplicationContext(), "????????? ??????", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(result.getInt("status")==1){
                                            Toast.makeText(getApplicationContext(), "????????? ??????", Toast.LENGTH_SHORT).show();
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

                Log.d("text", "body2 : " + body2.toString());
                queue.add(joRequest2);



            }
        });

        //????????????
        btn_participation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url3 = "http://dev.sbch.shop:9000/app/posts/join/";
                //Log.d("text", url3+postId+"/translate?status="+transactionStatus);

                JSONObject body3 = new JSONObject(); //JSON ??????????????? body ??????
                //SharedPreferences pref = getSharedPreferences("jwt",0);
                JsonObjectRequest joRequest2 = new JsonObjectRequest(Request.Method.GET, url3+postId, body3,
                        new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("text", response.toString());
                                    //JSONObject result = response.getJSONObject("result");
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

                Log.d("text", "body3 : " + body3.toString());
                queue.add(joRequest2);

            }
        });

        //?????? ????????????
        btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url3 = "http://dev.sbch.shop:9000/app/posts/comment";
                Log.d("text", "btn_add_comment");

                String content = edittext_comment.getText().toString();

                JSONObject body3 = new JSONObject(); //JSON ??????????????? body ??????

                try {
                    body3.put("postId", postId);
                    body3.put("parentId", 1);
                    body3.put("content", content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //SharedPreferences pref = getSharedPreferences("jwt",0);
                JsonObjectRequest joRequest2 = new JsonObjectRequest(Request.Method.POST, url3, body3,
                        new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("text", response.toString());
                                    //JSONObject result = response.getJSONObject("result");

                                    //Toast.makeText(getApplicationContext(), response.getString("result"), Toast.LENGTH_SHORT).show();

                                    if(response.getBoolean("isSuccess")){
                                        Toast.makeText(getApplicationContext(), "????????? ??????????????????", Toast.LENGTH_SHORT).show();
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

                Log.d("text", "body3 : " + body3.toString());
                queue.add(joRequest2);
                //adapter.notifyItemInserted(0);


            }
        });


        //?????? ??????
        comments = new ArrayList<>();
        add_comment();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { //?????????????????? ??????

                Log.d("text", "?????? ??????");
                //?????????????????? ??????
                recyclerView = findViewById(R.id.comment_recyclerview);
                //????????????????????? ??????
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                //????????? ??????, ????????? ??????
                adapter = new CommentAdapter(comments);
                //?????????????????? ??????
                recyclerView.setAdapter(adapter);

            }
        },500);


        post = new ArrayList<>();
        //post.add(new Post(0,"??????", "????????????", 10000, "deal", 3, 3,"1??????",null));


        add_recommend_post();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //????????????????????? ??????
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(RecyclerView.HORIZONTAL);


                //?????????????????? ??????
                recyclerView = findViewById(R.id.post_detail_recyclerview);
                recyclerView.setLayoutManager(layoutManager);
                post_small_adapter = new Post_Small_Adapter(post);


                //?????????????????? ??????
                recyclerView.setAdapter(post_small_adapter);

                post_small_adapter.setOnItemClicklistener(new OnSmallPostItemClickListener(){
                    @Override
                    public void onItemClick(Post_Small_Adapter.ItemViewHolder holder, View view, int position){
                        Post item = post_small_adapter.getItem(position);

                        int itempostid = item.getPostId();
                        Intent intent = new Intent(getApplicationContext(), PostDetailActivity.class);
                        intent.putExtra("postId", itempostid);

                        startActivity(intent);

                    }
                });

            }
        },500);


    }

    public void add_comment(){
        Log.d("text", "?????? ?????? add_comment() start ");
        String url = "http://dev.sbch.shop:9000/app/posts/comment/";
        JSONObject body = new JSONObject(); //JSON ??????????????? body ??????
        SharedPreferences pref = this.getSharedPreferences("jwt",0);

        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url+postId, body,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray array = response.getJSONArray("result");
                            Log.d("text", "result ?????? : "+String.valueOf(array.length()));

                            JSONArray jsonArray = response.optJSONArray("result");

                            JSONObject element;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                element = (JSONObject) jsonArray.opt(i);
                                Log.d("text?", element.toString());
                                comments.add(new Comment(element.getString("nick"), element.getString("comment")));

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
    public void transactionStatus_modify(int postId){

        String url = "http://dev.sbch.shop:9000/app/posts/" + postId + "/translate?status=" + transactionStatus;

        Log.d("Text", "transactionStatus_modify : " + url);
        JSONObject body = new JSONObject(); //JSON ??????????????? body ??????
        SharedPreferences pref = getSharedPreferences("jwt", 0);


        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url, body,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject result = response.getJSONObject("result");
                            Log.d("text", result.toString());
                            Toast.makeText(getApplicationContext(), "?????? ?????? : " + result.getString("transactionStatus"), Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse rep = error.networkResponse;
                        if (error instanceof ServerError && rep != null) {
                            try {
                                String r = new String(rep.data, HttpHeaderParser.parseCharset(rep.headers, "utf-8"));
                                JSONObject jo = new JSONObject(r);
                                Log.d("text", "??????" + jo.toString());
                            } catch (UnsupportedEncodingException | JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                        Log.d("text", "ERROR: " + error.getMessage());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("X-ACCESS-TOKEN", pref.getString("jwt", ""));
                return headers;
            }

        };
        queue.add(joRequest);
    }




    //?????? ??????????????????


    public void add_recommend_post(){
        String url = "http://dev.sbch.shop:9000/app/posts/";
        JSONObject body = new JSONObject(); //JSON ??????????????? body ??????
        SharedPreferences pref = this.getSharedPreferences("jwt",0);
        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url+postId+"/recommend", body,
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


