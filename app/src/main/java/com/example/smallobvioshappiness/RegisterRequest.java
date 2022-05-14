package com.example.smallobvioshappiness;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //url 설정

    final static private String URL = "dev.sbch.shop:9000/app/users";
    private Map<String, String> map;
    private String response;



    public RegisterRequest(String userEmail, String userPassword, String userName, String userNickname, String phone, Response.Listener<String> listener) {
        super(Method.POST,URL, listener, null);

        map = new HashMap<>();
        map.put("userEmail", userEmail);
        map.put("userPassword", userPassword);
        map.put("userName", userName);
        map.put("userNickname", userNickname);
        map.put("phone",phone);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}


