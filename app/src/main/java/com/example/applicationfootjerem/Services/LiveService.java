package com.example.applicationfootjerem.Services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class LiveService {

    private String urlBase = "http://api.football-data.org/v2";

    private RequestQueue mRequestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private Context context;

    //responses
    private JSONObject responseGetMatchsLive;

    public LiveService(Context context){
        this.context = context;
    }

    public JSONObject getMatchsLive(){
        mRequestQueue = Volley.newRequestQueue(context);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlBase + "/matches", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                responseGetMatchsLive = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseGetMatchsLive = null;
                try {
                    String string = new String(error.networkResponse.data);
                    JSONObject object = new JSONObject(string);
                    if (object.has("message")) {
                        String message = object.get("message").toString();
                        Toast.makeText(context, "Erreur : " + message, Toast.LENGTH_LONG).show();
                        Log.i(TAG, "Error :" + message);
                    } else {
                        Toast.makeText(context, "Erreur Volley inconnue", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "Error Volley inconnue");
                    }
                } catch (JSONException e){
                    Toast.makeText(context, "Erreur : Could not parse response", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Erreur : Could not parse response");
                } catch (NullPointerException e){
                    Toast.makeText(context, "Erreur : Avez-vous activé Internet ?", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Erreur : Avez-vous activé Internet ?");
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Auth-Token", "df3bab244ac64d57971819531df71b8a");
                return params;
            }
        };
        mRequestQueue.add(jsonObjectRequest);
        return responseGetMatchsLive;
    }
}
