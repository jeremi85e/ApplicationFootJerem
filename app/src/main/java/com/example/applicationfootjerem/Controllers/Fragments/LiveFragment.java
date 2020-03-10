package com.example.applicationfootjerem.Controllers.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationfootjerem.Adapters.CalendrierAdapter;
import com.example.applicationfootjerem.Adapters.LiveAdapter;
import com.example.applicationfootjerem.Models.Match;
import com.example.applicationfootjerem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends Fragment{

    private static final String TAG = "CalendrierFragment";
    private ListView listViewLive;
    DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private RequestQueue mRequestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private String urlBase = "http://api.football-data.org/v2";

    public static LiveFragment newInstance() {
        return (new LiveFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_live, container, false);

        listViewLive = (ListView) result.findViewById(R.id.listViewLive);

        getLiveScore();

        return result;
    }

    public void getLiveScore() {
        mRequestQueue = Volley.newRequestQueue(getContext());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlBase + "/matches", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray matches = response.getJSONArray("matches");
                    ArrayList<Match> listeMatchs = new ArrayList<>();

                    for (int idMatch = 0; idMatch < matches.length(); idMatch++) {
                        JSONObject match = matches.getJSONObject(idMatch);
                        listeMatchs.add(new Match(
                            m_ISO8601Local.parse(match.getString("utcDate")),
                            match.getString("status"),
                            match.getJSONObject("competition").getString("name"),
                            match.getJSONObject("competition").getJSONObject("area").getString("name"),
                            match.getJSONObject("homeTeam").getString("name"),
                            !match.getJSONObject("score").getJSONObject("fullTime").isNull("homeTeam") ? match.getJSONObject("score").getJSONObject("fullTime").getString("homeTeam") : null,
                            !match.getJSONObject("score").getJSONObject("fullTime").isNull("awayTeam") ? match.getJSONObject("score").getJSONObject("fullTime").getString("awayTeam") : null,
                            match.getJSONObject("awayTeam").getString("name")
                        ));
                    }
                    LiveAdapter liveAdapter = new LiveAdapter(getContext(), listeMatchs);
                    listViewLive.setAdapter(liveAdapter);
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Erreur : " + e.toString(), Toast.LENGTH_LONG).show();
                    Log.e("MYAPP", "unexpected parse exception", e);
                }  catch (JSONException e){
                    Toast.makeText(getContext(), "Erreur : Could not parse response", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Erreur : Could not parse response");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String string = new String(error.networkResponse.data);
                    JSONObject object = new JSONObject(string);
                    if (object.has("message")) {
                        String message = object.get("message").toString();
                        Toast.makeText(getContext(), "Erreur : " + message, Toast.LENGTH_LONG).show();
                        Log.i(TAG, "Error :" + message);
                    } else {
                        Toast.makeText(getContext(), "Erreur Volley inconnue", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "Error Volley inconnue");
                    }
                } catch (JSONException e){
                    Toast.makeText(getContext(), "Erreur : Could not parse response", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Erreur : Could not parse response");
                } catch (NullPointerException e){
                    Toast.makeText(getContext(), "Erreur : Avez-vous activé Internet ?", Toast.LENGTH_LONG).show();
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
    }
}
