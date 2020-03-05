package com.example.applicationfootjerem.Controllers.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationfootjerem.Adapters.CompetitionAdapter;
import com.example.applicationfootjerem.Controllers.Activities.MainActivity;
import com.example.applicationfootjerem.Models.Competition;
import com.example.applicationfootjerem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CompetitionsFragment extends Fragment {
    private ListView listViewCompetitions;
    private static final String TAG = MainActivity.class.getName();
    private RequestQueue mRequestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private String urlBase = "http://api.football-data.org/v2";

    // 2 - Method that will create a new instance of CalendrierFragment.
    public static CompetitionsFragment newInstance() {
        return(new CompetitionsFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 3 - Get layout of CalendrierFragment
        View result = inflater.inflate(R.layout.fragment_competitions, container, false);

        listViewCompetitions = (ListView) result.findViewById(R.id.listViewCompetitons);

        mRequestQueue = Volley.newRequestQueue(getContext());

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlBase + "/competitions/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray competitions = response.getJSONArray("competitions");
                    ArrayList<Competition> listeCompetitions = new ArrayList<>();

                    for (int nbCompet = 0; nbCompet < competitions.length(); nbCompet++) {
                        JSONObject competitionJson = competitions.getJSONObject(nbCompet);
                        if (competitionJson.getString("plan").equals("TIER_ONE")){
                            Competition competition = new Competition(
                                    competitionJson.getString("name"),
                                    competitionJson.getJSONObject("area").getString("name"),
                                    competitionJson.getString("code"),
                                    competitionJson.getJSONObject("currentSeason").getInt("currentMatchday")
                            );
                            listeCompetitions.add(competition);
                        }
                    }
                    Collections.sort(listeCompetitions);
                    CompetitionAdapter competitionAdapter = new CompetitionAdapter(getContext(), listeCompetitions);
                    listViewCompetitions.setAdapter(competitionAdapter);

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Erreur : " + e.toString(), Toast.LENGTH_LONG).show();
                    Log.e("MYAPP", "unexpected JSON exception", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Erreur : " + error.toString(), Toast.LENGTH_LONG).show();
                Log.i(TAG, "Error :" + error.toString());

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

        return result;
    }
}
