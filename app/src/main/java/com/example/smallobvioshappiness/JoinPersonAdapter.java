package com.example.smallobvioshappiness;

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

    //데이터를 레이아웃에 어떻게 넣어줄지
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        JoinPerson person = joinPeople.get(i);

//        itemViewHolder.image.setImageDrawable(chatting.getImage());
        itemViewHolder.name.setText(person.getName());

    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private Button accept, refuse;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.profile_nickname);
            accept = itemView.findViewById(R.id.join_ok);
            refuse = itemView.findViewById(R.id.join_refuse);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "수락", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    void addItem(JoinPerson joinPerson){
        JoinPerson.add(joinPerson);
    }

    void removeItem(int position){
        JoinPerson.remove(position);
    }


}
