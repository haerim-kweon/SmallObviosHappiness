package com.example.smallobvioshappiness;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //url 설정
    final static private String URL = "http://dev.sbch.shop:9000/app/users";
    private Map<String, String> map;
    private String response;



    public RegisterRequest(String userEmail, String userName, String phone, String userPassword, String userPassword2,  String userNickname,  Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("email", userEmail);
        map.put("password", userPassword);
        map.put("password2", userPassword2);
        map.put("name", userName);
        map.put("nick", userNickname);
        map.put("phone",phone);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        return map;
    }

}


