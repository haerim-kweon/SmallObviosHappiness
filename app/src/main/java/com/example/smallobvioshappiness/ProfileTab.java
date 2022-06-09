package com.example.smallobvioshappiness;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class ProfileTab extends Fragment {
    private View view;

    Mypage_frag1 frag1;
    Mypage_frag2 frag2;
    Mypage_frag3 frag3;

    Notice_Setting notice_setting;
    ReviewPage reviewpage;

    TextView nick, score, alarm, location_setting, location_certify, review, logout ;
    Button modify;
    RequestQueue queue;
    int userid;
    private FragmentManager fm;
    private FragmentTransaction ft;
    ImageView profileImg;
    String imgUrl;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profiletab, container, false);

        notice_setting = new Notice_Setting();
        reviewpage = new ReviewPage();

        profileImg = view.findViewById(R.id.myprofileImg);

        frag1 = new Mypage_frag1();
        frag2 = new Mypage_frag2();
        frag3 = new Mypage_frag3();//신림동

        getFragmentManager().beginTransaction().replace(R.id.mypage_container, frag1).commit();

        TabLayout tabs = view.findViewById(R.id.mypage_tabs);
        tabs.addTab(tabs.newTab().setText("참여내역"));
        tabs.addTab(tabs.newTab().setText("주최한내역"));
        tabs.addTab(tabs.newTab().setText("찜한내역"));


        nick = view.findViewById(R.id.profile_nick);
        score = view.findViewById(R.id.profile_score);
        modify = view.findViewById(R.id.profile_modify);

        alarm = view.findViewById(R.id.alarm_setting);
        location_setting = view.findViewById(R.id.location_setting);
        location_certify = view.findViewById(R.id.certify_setting);
        review = view.findViewById(R.id.myreview);
        logout = view.findViewById(R.id.logout);

        //알람 설정 이동
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.main_frame, notice_setting);
                ft.commit();
            }
        });

        //리뷰 이동
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.main_frame, reviewpage);
                ft.commit();
            }
        });

        //로그아웃
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getActivity().finish();
                startActivity(intent);


            }
        });

        queue = Volley.newRequestQueue(getContext());
        String url2 = "http://dev.sbch.shop:9000/app/users/profile/0";

        JSONObject body2 = new JSONObject(); //JSON 오브젝트의 body 부분
        SharedPreferences pref =  this.getActivity().getSharedPreferences("jwt",0);
        Log.d("text", "jwt : " + pref.getString("jwt",""));
        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url2, body2,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("text", response.toString());
                            JSONObject result = response.getJSONObject("result");
                            nick.setText(result.getString("nick"));
                            score.setText("신뢰도 " +String.valueOf(result.getDouble("credibilityScore")) + " / 10.0");

                            imgUrl = result.getString("profileImg");
                            Glide.with(getContext()).load(imgUrl).placeholder(R.drawable.ic_defalt_profile)
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


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){
                    selected = frag1;
                } else if(position == 1){
                    selected = frag2;
                } else{
                    selected = frag3;
                }

                getFragmentManager().beginTransaction().replace(R.id.mypage_container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        location_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LocationSearchActivity.class);
                startActivity(intent);
            }
        });



        return view;

    }

}
