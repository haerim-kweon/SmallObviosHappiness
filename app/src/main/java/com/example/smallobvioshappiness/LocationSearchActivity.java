package com.example.smallobvioshappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
import java.util.List;
import java.util.Map;

public class LocationSearchActivity extends AppCompatActivity {
    RequestQueue queue;
    EditText searchView;
    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private LocationAdpater adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;
    private Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_search);

        queue = Volley.newRequestQueue(getApplicationContext());

        searchView = (EditText) findViewById(R.id.searchView3);
        listView = (ListView) findViewById(R.id.listView);
        btn_search = findViewById(R.id.btn_location_search);

        // 리스트를 생성한다.
        list = new ArrayList<String>();

        // 검색에 사용할 데이터을 미리 저장한다.
        //settingList();
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = searchView.getText().toString();

                String url = "http://dev.sbch.shop:9000/app/users/location/search?dong=";

                JSONObject body = new JSONObject(); //JSON 오브젝트의 body 부분
                SharedPreferences pref = getSharedPreferences("jwt", 0);
                JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url + text, body,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("text", response.toString());
                                    list.clear();
                                    JSONObject result = response.getJSONObject("result");
                                    JSONArray array = result.getJSONArray("addressInfos");
                                    JSONObject element;

                                    for (int i = 0; i < array.length(); i++) {
                                        element = (JSONObject) array.opt(i);
                                        Log.d("text", element.toString());
                                        list.add(element.getString("resion1") + " " +element.getString("resion2")+ " " +element.getString("resion3"));

                                        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
                                        arraylist = new ArrayList<String>();
                                        arraylist.addAll(list);

                                        // 리스트에 연동될 아답터를 생성한다.
                                        adapter = new LocationAdpater(list, LocationSearchActivity.this);

                                        // 리스트뷰에 아답터를 연결한다.
                                        listView.setAdapter(adapter);
                                    }

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
                                        Log.d("text", "결과" + jo.toString());
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
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //String location = list.get(position);

                String[] location_split = list.get(position).split(" ",3);

                Log.d("text", list.get(position));

                String url2 = "http://dev.sbch.shop:9000/app/users/location/choice?";
                String region1 = "region1=" + location_split[0];
                String region2 = "&region2=" + location_split[1];
                String region3 = "&region3=" + location_split[2];

                Log.d("text", url2+region1+region2+region3);

                JSONObject body2 = new JSONObject(); //JSON 오브젝트의 body 부분
                SharedPreferences pref = getSharedPreferences("jwt", 0);
                JsonObjectRequest joRequest2 = new JsonObjectRequest(Request.Method.GET, url2+region1+region2+region3, body2,
                        new Response.Listener<JSONObject>() {

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

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                NetworkResponse rep = error.networkResponse;
                                if (error instanceof ServerError && rep != null) {
                                    try {
                                        String r = new String(rep.data, HttpHeaderParser.parseCharset(rep.headers, "utf-8"));
                                        JSONObject jo = new JSONObject(r);
                                        Log.d("text", "결과" + jo.toString());
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

                queue.add(joRequest2);





            }
        });






    }
}
