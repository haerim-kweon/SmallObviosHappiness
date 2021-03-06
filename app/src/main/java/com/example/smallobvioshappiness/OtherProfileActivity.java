package com.example.smallobvioshappiness;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class OtherProfileActivity extends AppCompatActivity {

    OtherProfile_frag1 frag1;
    OtherProfile_frag2 frag2;
    Notice_ChatTab chat;
    RequestQueue queue;
    TextView nick1, nick2, location;
    ImageButton btn_back;
    ImageView profileImg;
    String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theother_profile);
        Intent intent = getIntent();
        int post_userid = intent.getIntExtra("postUserId",0);
        Bundle bundle = new Bundle();
        bundle.putInt("postUserId", post_userid);

        frag1 = new OtherProfile_frag1();
        frag2 = new OtherProfile_frag2();

        profileImg = findViewById(R.id.otherprofileimg);

        nick1 = findViewById(R.id.otherprofile_nick1);
        nick2 = findViewById(R.id.otherprofile_nick2);
        location = findViewById(R.id.otherprofile_location);
        btn_back = findViewById(R.id.otherprofile_btn_back);
        frag1.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.otherprofile_container, frag1).commit();

        TabLayout tabs = findViewById(R.id.otherprofile_tabs);
        tabs.addTab(tabs.newTab().setText("????????????"));
        tabs.addTab(tabs.newTab().setText("???????????????"));

        //?????? ?????? ??????
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        queue = Volley.newRequestQueue(getApplicationContext());
        String url2 = "http://dev.sbch.shop:9000/app/users/profile/";

        Log.d("text", url2+post_userid);
        JSONObject body2 = new JSONObject(); //JSON ??????????????? body ??????
        SharedPreferences pref = getSharedPreferences("jwt",0);
        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url2+post_userid, body2,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("text", response.toString());
                            JSONObject result = response.getJSONObject("result");
                            nick1.setText(result.getString("nick")+"??? ?????????");
                            nick2.setText(result.getString("nick"));
                            location.setText("????????? " +String.valueOf(result.getDouble("credibilityScore")) + " / 10.0");

                            imgUrl = result.getString("profileImg");
                            Glide.with(getApplicationContext()).load(imgUrl).placeholder(R.drawable.ic_defalt_profile)
                                    .error(R.drawable.ic_defalt_profile).into(profileImg);

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




        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){
                    selected = frag1;
                    frag1.setArguments(bundle);
                } else{
                    selected = frag2;
                    frag2.setArguments(bundle);
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.otherprofile_container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }

}