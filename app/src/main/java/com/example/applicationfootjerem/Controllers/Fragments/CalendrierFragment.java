package com.example.applicationfootjerem.Controllers.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationfootjerem.Adapters.CalendrierAdapter;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendrierFragment extends Fragment{

    private static final String TAG = "CalendrierFragment";
    private ImageButton journeePrecedente;
    private ImageButton journeeSuivante;
    private ArrayList<String> listeJournees;
    private ListView listViewResultats;
    private Spinner spinnerJournees;
    private int journeeActuelle;
    DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private RequestQueue mRequestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private String urlBase = "http://api.football-data.org/v2";

    public static CalendrierFragment newInstance(Competition competition) {
        CalendrierFragment calendrierFragment = new CalendrierFragment();

        Bundle bundle = new Bundle();
        bundle.putString("codeCompetition", competition.getCode());
        bundle.putStringArrayList("listeJournees", competition.getListeJournees());
        bundle.putInt("currentMatchDay", competition.getJourneeActuelle());
        calendrierFragment.setArguments(bundle);

        return calendrierFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_calendrier, container, false);

        listViewResultats = (ListView) result.findViewById(R.id.calendrierListeView);
        spinnerJournees = (Spinner) result.findViewById(R.id.spinnerJournees);
        journeePrecedente = (ImageButton) result.findViewById(R.id.calendrierJourneePrecedente);
        journeeSuivante = (ImageButton) result.findViewById(R.id.calendrierJourneeSuivante);

        journeeActuelle = getArguments().getInt("currentMatchDay");
        listeJournees = getArguments().getStringArrayList("listeJournees");

        String[] items = new String[listeJournees.size()];
        for (int i = 0; i < listeJournees.size(); i++) {
            items[i] = getJourneeSpinner(listeJournees.get(i));
        }
        ArrayAdapter<String> adapterJournees = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerJournees.setAdapter(adapterJournees);
        spinnerJournees.setSelection(journeeActuelle - 1);


        getCalendrier(journeeActuelle);

        journeePrecedente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                spinnerJournees.setSelection(journeeActuelle - 1);
            }
        });

        journeeSuivante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                spinnerJournees.setSelection(journeeActuelle + 1);
            }
        });

        spinnerJournees.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                journeeActuelle = position;
                getCalendrier(journeeActuelle);
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        return result;
    }

    private void getCalendrier(int journee) {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(getContext());

        String matchUrl = "";
        if(listeJournees.get(journee).equals("ROUND_OF_16") || listeJournees.get(journee).equals("QUARTER_FINALS") || listeJournees.get(journee).equals("SEMI_FINALS") || listeJournees.get(journee).equals("3RD_PLACE") || listeJournees.get(journee).equals("FINAL")){
            matchUrl = "stage";
        } else {
            matchUrl = "matchday";
        }

        //String Request initialized
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlBase + "/competitions/" + getArguments().getString("codeCompetition") + "/matches?" + matchUrl + "=" + listeJournees.get(journee), null, new Response.Listener<JSONObject>() {
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
                                response.getJSONObject("competition").getString("name"),
                                response.getJSONObject("competition").getJSONObject("area").getString("name"),
                                match.getJSONObject("homeTeam").getString("name"),
                                !match.getJSONObject("score").getJSONObject("fullTime").isNull("homeTeam") ? match.getJSONObject("score").getJSONObject("fullTime").getString("homeTeam") : null,
                                !match.getJSONObject("score").getJSONObject("fullTime").isNull("awayTeam") ? match.getJSONObject("score").getJSONObject("fullTime").getString("awayTeam") : null,
                                match.getJSONObject("awayTeam").getString("name")
                        ));
                    }
                    Collections.sort(listeMatchs);
                    CalendrierAdapter calendrierAdapter = new CalendrierAdapter(getContext(), listeMatchs);
                    listViewResultats.setAdapter(calendrierAdapter);

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

    private String getJourneeSpinner(String code){
        switch (code){
            case "ROUND_OF_16" :
                return "Huitièmes de Finale";
            case "QUARTER_FINALS" :
                return "Quarts de Finale";
            case "SEMI_FINALS" :
                return "Demie Finale";
            case "3RD_PLACE" :
                return "Match pour la 3ème place";
            case "FINAL" :
                return "Finale";
            default:
                return "Journée " + code;
        }
    }

}
