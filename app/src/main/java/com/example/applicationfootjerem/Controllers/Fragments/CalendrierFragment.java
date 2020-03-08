package com.example.applicationfootjerem.Controllers.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationfootjerem.Adapters.MatchAdapter;
import com.example.applicationfootjerem.Controllers.Activities.MainActivity;
import com.example.applicationfootjerem.Models.Competition;
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
public class CalendrierFragment extends Fragment{

    private static final String TAG = MainActivity.class.getName();
    private Button boutonCalendrier;
    private ListView listViewResultats;
    private Spinner spinnerJournees;
    private Spinner spinnerChampionnats;
    DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private RequestQueue mRequestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private String urlBase = "http://api.football-data.org/v2";

    public static CalendrierFragment newInstance(Competition competition) {
        CalendrierFragment calendrierFragment = new CalendrierFragment();

        Bundle bundle = new Bundle();
        bundle.putString("codeCompetition", competition.getCode());
        bundle.putInt("currentMatchDay", competition.getJourneeActuelle());
        calendrierFragment.setArguments(bundle);

        return calendrierFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_calendrier, container, false);

        boutonCalendrier = (Button) result.findViewById(R.id.boutonCalendrier);
        listViewResultats = (ListView) result.findViewById(R.id.calendrierListeView);
        spinnerJournees = (Spinner) result.findViewById(R.id.spinnerJournees);

        Integer[] items = new Integer[38];
        for (int i = 0; i < 38; i++) {
            items[i] = i+1;
        }
        ArrayAdapter<Integer> adapterJournees = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerJournees.setAdapter(adapterJournees);
        spinnerJournees.setSelection(getArguments().getInt("currentMatchDay") - 1);

        boutonCalendrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                getCalendrier();
            }
        });

        return result;
    }

    private void getCalendrier() {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(getContext());

        //String Request initialized
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlBase + "/competitions/" + getArguments().getString("codeCompetition") + "/matches?matchday=" + spinnerJournees.getSelectedItem().toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray matches = response.getJSONArray("matches");
                    ArrayList<Match> listeMatchs = new ArrayList<>();

                    for (int idMatch = 0; idMatch < matches.length(); idMatch++) {
                        JSONObject match = matches.getJSONObject(idMatch);
                        listeMatchs.add(
                                new Match(
                                        m_ISO8601Local.parse(match.getString("utcDate")),
                                        match.getString("status"),
                                        match.getJSONObject("homeTeam").getString("name"),
                                        !match.getJSONObject("score").getJSONObject("fullTime").isNull("homeTeam") ? match.getJSONObject("score").getJSONObject("fullTime").getString("homeTeam") : null,
                                        !match.getJSONObject("score").getJSONObject("fullTime").isNull("awayTeam") ? match.getJSONObject("score").getJSONObject("fullTime").getString("awayTeam") : null,
                                        match.getJSONObject("awayTeam").getString("name")
                                )
                        );
                    }
                    MatchAdapter matchAdapter = new MatchAdapter(getContext(), listeMatchs);
                    listViewResultats.setAdapter(matchAdapter);

                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Erreur : " + e.toString(), Toast.LENGTH_LONG).show();
                    Log.e("MYAPP", "unexpected parse exception", e);
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
    }

}
