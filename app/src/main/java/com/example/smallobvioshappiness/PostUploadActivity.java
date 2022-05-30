package com.example.smallobvioshappiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class PostUploadActivity extends AppCompatActivity {



    private EditText title, product, price, explain, num;
    private ImageButton btn_back;
    private Button location1, location2, btn_add;
    private TextView category, period, time;

    int location1_id, location2_id;

    RequestQueue queue;

    Calendar myCalendar = Calendar.getInstance();


    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "yyyy년 MM월 dd일";    // 출력형식
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView et_date = findViewById(R.id.add_Period);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtab);
        queue = Volley.newRequestQueue(this);

        btn_back = findViewById(R.id.imageButton14);
        btn_add = findViewById(R.id.add_post);
        location1 = findViewById(R.id.location1);
        location2 = findViewById(R.id.location2);

        title = findViewById(R.id.editTextTitle);
        price = findViewById(R.id.editTextTextPrice);
        explain = findViewById(R.id.editTextTextExplain);
        product = findViewById(R.id.productName);
        num = findViewById(R.id.editTextTextPersonNumber);

        category = findViewById(R.id.add_Category);
        period = findViewById(R.id.add_Period);
        time = findViewById(R.id.add_Time);

        location1 = findViewById(R.id.location1);
        location2 = findViewById(R.id.location2);

        SharedPreferences pref = getSharedPreferences("jwt",0);

//

        JsonArrayRequest locationRequest = new JsonArrayRequest(Request.Method.GET, "http://dev.sbch.shop:9000/app/posts/location", null,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("text1", response.toString());
                            String location1_town = response.getJSONObject(0).getString("town");
                            location1_id = response.getJSONObject(0).getInt("locationId");
                            String location2_town = response.getJSONObject(1).getString("town");
                            location2_id = response.getJSONObject(1).getInt("locationId");

                            location1.setText(location1_town);
                            location2.setText(location2_town);
                            //JSONObject result = response.getJSONObject(0);
                            Log.d("text", "3");

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


        queue.add(locationRequest);


        //

        location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(location1.isSelected()){
                    location1.setSelected(false);
                }
                else{
                    location1.setSelected(true);
                    if(location2.isSelected()){
                        location2.setSelected(false);
                    }
                }
            }
        });

        location2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(location2.isSelected()){
                    location2.setSelected(false);
                }
                else{
                    location2.setSelected(true);
                    if(location1.isSelected()){
                        location1.setSelected(false);
                    }
                }
            }
        });


        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(PostUploadActivity.this, view);
                getMenuInflater().inflate(R.menu.category, popupMenu.getMenu());


                popupMenu.show();
            }
        });

        period.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(PostUploadActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView et_time = findViewById(R.id.add_Time);
                et_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(PostUploadActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                String state = "AM";
                                // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                                if (selectedHour > 12) {
                                    selectedHour -= 12;
                                    state = "PM";
                                }
                                // 출력할 형식 지정
                                et_time.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                            }
                        }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();
                    }
                                           }
                );
            }
        });



                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //입력된 정보 가져오기
                String posttitle = title.getText().toString();
                String postproduct = product.getText().toString();
                String postexplain = explain.getText().toString();

                int postcategoty=1;
                int postprice = Integer.parseInt(price.getText().toString());
                int postnum = Integer.parseInt(num.getText().toString());
                int postlocation = 0;

                Log.d("text", "지역1 선택"+ location1.isSelected() + "지역2 선택" + location2.isSelected());
                if(location1.isSelected()){
                    postlocation = location1_id;
                }
                else if(location2.isSelected()){
                    postlocation = location2_id;
                }
                else{
                    Toast.makeText(getApplicationContext(), "지역을 선택해주세요", Toast.LENGTH_SHORT ).show();
                }


                String timestamp = "2022-05-30T21:00:00";


                JSONObject body = new JSONObject(); //JSON 오브젝트의 body 부분

                try {
                    body.put("title", posttitle);
                    body.put("categoryId", postcategoty);
                    body.put("productName", postproduct);
                    body.put("price", postprice);
                    body.put("locationId", postlocation);
                    body.put("date",timestamp);
                    body.put("num", postnum);
                    body.put("content",postexplain);

                    //jsonObject.put("body", body); //body 오브젝트 추가

                } catch (JSONException e) {
                    e.printStackTrace();
                } Log.e("json", "생성한 json : " + body.toString()); //log로 JSON오브젝트가 잘생성되었는지 확인
                Log.d("text", timestamp.toString());


                JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.POST,
                        "http://dev.sbch.shop:9000/app/posts/save", body,
                        new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("text", "결과"+ response.toString());
                                boolean success = false;
                                try {
                                    success = response.getBoolean("isSuccess");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(success){
                                    Toast.makeText(getApplicationContext(), "업로드 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PostUploadActivity.this, MainActivity.class);
                                    startActivity(intent);

                                }
                                //업로드 실패
                                else {
                                    try {
                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    return;
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

                //Log.d("text", jsonObject.toString());
                queue.add(joRequest);



            }
        });



            }


        }