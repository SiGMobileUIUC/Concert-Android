package com.example.concert_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                Log.d(TAG, "button clicked");
                String inputUsername = ((EditText)findViewById(R.id.username)).getText().toString();
                String inputPassword = ((EditText)findViewById(R.id.password)).getText().toString();
                callvolley(inputUsername, inputPassword);
                //startAPICall();
            }
        });
    }

    void startAPICall() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "https://concert.acm.illinois.edu/login";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", ((EditText)findViewById(R.id.username)).getText().toString());
            jsonBody.put("password", ((EditText)findViewById(R.id.password)).getText().toString());
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            })
            ;
            requestQueue.add(stringRequest);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void callvolley(final String username, final String password){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://concert.acm.illinois.edu/login"; // <----enter your post url here
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e("VOLLEY", error.toString());
            }
        }) {
            protected Map<String, String> getInputParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", username);
                MyData.put("password", password);
                return MyData;
            }
        };


        MyRequestQueue.add(MyStringRequest);
    }
}
