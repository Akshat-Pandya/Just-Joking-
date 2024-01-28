package com.example.just_joking;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonApiRequest {
    private Context context;
    private RequestQueue queue;

    public JsonApiRequest(Context context) {
        this.context = context;
        this.queue= Volley.newRequestQueue(context);
    }
    public void makeRequest(String requesturl,final JsonCallback callback){

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, requesturl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response!=null)
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.getMessage());
            }
        });
        queue.add(request);

    }
    public interface JsonCallback{
        void onSuccess(JSONObject response) throws JSONException;
        void onFailure(String error);
    }
}
