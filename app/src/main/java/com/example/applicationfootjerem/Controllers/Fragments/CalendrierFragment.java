package com.example.applicationfootjerem.Controllers.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationfootjerem.Adapters.MatchAdapter;
import com.example.applicationfootjerem.Controllers.Activities.MainActivity;
import com.example.applicationfootjerem.Models.Match;
import com.example.applicationfootjerem.R;
import com.example.applicationfootjerem.Utils.NetworkAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendrierFragment extends Fragment implements NetworkAsyncTask.Listeners {

    @BindView(R.id.calendrierRecyclerView) RecyclerView recyclerViewCalendrier;
    private List<Match> listeMatchs;
    private MatchAdapter matchAdapter;

    public CalendrierFragment() {
        // Required empty public constructor
    }

    public static CalendrierFragment newInstance() {
        return(new CalendrierFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 3 - Get layout of CalendrierFragment
        View view = inflater.inflate(R.layout.fragment_calendrier, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        return view;
    }

    private void configureRecyclerView(){
        // 3.1 - Reset list
        this.listeMatchs = new ArrayList<>();
        // 3.2 - Create adapter passing the list of users
        this.matchAdapter = new MatchAdapter(this.listeMatchs);

        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.recyclerViewCalendrier.setAdapter(this.matchAdapter);
        // 3.4 - Set layout manager to position the items
        this.recyclerViewCalendrier.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerViewCalendrier.setHasFixedSize(true);
    }

    // -----------------
    // ACTIONS
    // -----------------

    @OnClick(R.id.boutonCalendrier)
    public void submit(View view) {
        this.executeHttpRequest();
    }

    // ------------------
    //  HTTP REQUEST
    // ------------------

    private void executeHttpRequest(){
        new NetworkAsyncTask(this).execute("http://api.football-data.org/v2/competitions/FL1/matches?matchday=25");
    }

    @Override
    public void onPreExecute() {
        this.updateUIWhenStartingHTTPRequest();
    }

    @Override
    public void doInBackground() { }

    @Override
    public void onPostExecute(String json) {
        this.updateUIWhenStopingHTTPRequest(json);
    }

    // ------------------
    //  UPDATE UI
    // ------------------

    //Méthode exécutée lors du début de l'appel API
    private void updateUIWhenStartingHTTPRequest(){
    }

    //Méthode exécutée lors de la fin de l'appel API
    private void updateUIWhenStopingHTTPRequest(String response){
        try {
            JSONArray matches = new JSONObject(response).getJSONArray("matches");
            ArrayList<Match> listeMatchs = new ArrayList<>();

            for (int idMatch = 0; idMatch < matches.length(); idMatch++) {
                JSONObject match = matches.getJSONObject(idMatch);
                listeMatchs.add(
                        new Match(
                                //getEcusson(match.getJSONObject("homeTeam").getString("name")),
                                0,
                                match.getJSONObject("homeTeam").getString("name"),
                                match.getJSONObject("score").getJSONObject("fullTime").getString("homeTeam"),
                                match.getJSONObject("score").getJSONObject("fullTime").getString("awayTeam"),
                                match.getJSONObject("awayTeam").getString("name"),
                                //getEcusson(match.getJSONObject("awayTeam").getString("name"))
                                0
                        )
                );
            }
            MatchAdapter matchAdapter = new MatchAdapter(listeMatchs);
            this.recyclerViewCalendrier.setAdapter(matchAdapter);

        } catch (JSONException e) {
            Toast.makeText(getContext(), "Erreur : " + e.toString(), Toast.LENGTH_LONG).show();
            Log.e("MYAPP", "unexpected JSON exception", e);
        }


    }

    /*
    private static final String TAG = MainActivity.class.getName();
    private Button boutonCalendrier;
    private ListView listViewResultats;
    private Spinner spinnerJournees;
    private Spinner spinnerChampionnats;

    private RequestQueue mRequestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private String urlBase = "http://api.football-data.org/v2";

    // 2 - Method that will create a new instance of CalendrierFragment.
    public static CalendrierFragment newInstance() {
        return(new CalendrierFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 3 - Get layout of CalendrierFragment
        View result = inflater.inflate(R.layout.fragment_calendrier, container, false);

        boutonCalendrier = (Button) result.findViewById(R.id.boutonCalendrier);
        listViewResultats = (ListView) result.findViewById(R.id.listViewResultats);
        spinnerJournees = (Spinner) result.findViewById(R.id.spinnerJournees);
        spinnerChampionnats = (Spinner) result.findViewById(R.id.spinnerChampionnats);

        remplissageSpinnerChampionnats();

        Integer[] items = new Integer[38];
        for (int i = 0; i < 38; i++) {
            items[i] = i+1;
        }
        ArrayAdapter<Integer> adapterJournees = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerJournees.setAdapter(adapterJournees);

        boutonCalendrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                appelAPICalendrier();
            }
        });

        return result;
    }

    public void remplissageSpinnerChampionnats() {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(getContext());

        //String Request initialized
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlBase + "/competitions/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray competitions = response.getJSONArray("competitions");
                    ArrayList<String> listeCompetitions = new ArrayList<>();
                    int nbChampionnatsAutorises = 0;

                    for (int nbCompet = 0; nbCompet < competitions.length(); nbCompet++) {
                        JSONObject competition = competitions.getJSONObject(nbCompet);
                        if (competition.getString("plan").equals("TIER_ONE")){
                            listeCompetitions.add(competition.getString("name") + " (" +  competition.getJSONObject("area").getString("name") + ") - " + competition.getString("code"));
                            nbChampionnatsAutorises++;
                        }
                    }
                    ArrayAdapter<String> adapterCompetitions = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listeCompetitions);
                    spinnerChampionnats.setAdapter(adapterCompetitions);

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

    private void appelAPICalendrier() {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(getContext());

        String codeCompet = spinnerChampionnats.getSelectedItem().toString().split("-")[1].trim();

        //String Request initialized
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlBase + "/competitions/" + codeCompet + "/matches?matchday=" + spinnerJournees.getSelectedItem().toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray matches = response.getJSONArray("matches");
                    ArrayList<Match> listeMatchs = new ArrayList<>();

                    for (int idMatch = 0; idMatch < matches.length(); idMatch++) {
                        JSONObject match = matches.getJSONObject(idMatch);
                        listeMatchs.add(
                                new Match(
                                        //getEcusson(match.getJSONObject("homeTeam").getString("name")),
                                        0,
                                        match.getJSONObject("homeTeam").getString("name"),
                                        match.getJSONObject("score").getJSONObject("fullTime").getString("homeTeam"),
                                        match.getJSONObject("score").getJSONObject("fullTime").getString("awayTeam"),
                                        match.getJSONObject("awayTeam").getString("name"),
                                        //getEcusson(match.getJSONObject("awayTeam").getString("name"))
                                        0
                                )
                        );
                    }
                    MatchAdapter matchAdapter = new MatchAdapter(getContext(), listeMatchs);
                    listViewResultats.setAdapter(matchAdapter);

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
    */
}
