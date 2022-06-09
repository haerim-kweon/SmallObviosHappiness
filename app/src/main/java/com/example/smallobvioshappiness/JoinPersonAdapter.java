package com.example.smallobvioshappiness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class JoinPersonAdapter extends RecyclerView.Adapter<JoinPersonAdapter.ItemViewHolder> {
    private ArrayList<JoinPerson> joinPeople;
    public JoinPersonAdapter(ArrayList<JoinPerson> joinPeople) {
        this.joinPeople = joinPeople;
    }
    private OnJoinPersonClickListener listener;
    RequestQueue queue;

    @Override
    public int getItemCount() {
        return joinPeople.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.join_person, viewGroup,false);
        return new ItemViewHolder(view);
    }

    //아이템클릭
    public void setOnItemClicklistener(OnJoinPersonClickListener listener){
        this.listener = listener;
    }

    public void onItemClick(JoinPersonAdapter.ItemViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    //데이터를 레이아웃에 어떻게 넣어줄지
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        JoinPerson person = joinPeople.get(i);
        itemViewHolder.name.setText(person.getName());
        int postId = 1;
        int userId = person.getUserId();
        itemViewHolder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queue = Volley.newRequestQueue(view.getContext());
                String url = "http://dev.sbch.shop:9000/app/posts/joinApply?postId="+postId+"&userId="+userId;
                JSONObject body = new JSONObject(); //JSON 오브젝트의 body 부분

                JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url, body,
                        new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getBoolean("isSuccess")){
                                        Toast.makeText(view.getContext(), response.getString("result"), Toast.LENGTH_SHORT).show();
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
                        return headers;
                    }

                };

                queue.add(joRequest);

            }
        });

        itemViewHolder.refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queue = Volley.newRequestQueue(view.getContext());
                String url = "http://dev.sbch.shop:9000/app/posts/joinRefuse?postId="+postId+"&userId="+userId;
                JSONObject body = new JSONObject(); //JSON 오브젝트의 body 부분

                JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url, body,
                        new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getBoolean("isSuccess")){
                                        Toast.makeText(view.getContext(), response.getString("result"), Toast.LENGTH_SHORT).show();
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
                        return headers;
                    }

                };

                queue.add(joRequest);

            }
        });
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private Button accept, refuse;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.profile_nickname);
            accept = itemView.findViewById(R.id.join_ok);
            refuse = itemView.findViewById(R.id.join_refuse);

        }
    }


    void addItem(JoinPerson joinPerson){
        JoinPerson.add(joinPerson);
    }

    void removeItem(int position){
        JoinPerson.remove(position);
    }

    public JoinPerson getItem(int position){
        return joinPeople.get(position);
    }

}
