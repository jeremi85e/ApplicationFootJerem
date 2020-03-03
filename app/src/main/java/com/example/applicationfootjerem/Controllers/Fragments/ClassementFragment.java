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
import com.example.applicationfootjerem.Adapters.ClassementAdapter;
import com.example.applicationfootjerem.Controllers.Activities.MainActivity;
import com.example.applicationfootjerem.Models.EquipeClassement;
import com.example.applicationfootjerem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

public class ClassementFragment extends Fragment {

    private static final String TAG = MainActivity.class.getName();
    private Button boutonClassement;
    private ListView listeViewClassement;
    private RequestQueue mRequestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private Spinner spinnerClassement;
    private String urlBase = "http://api.football-data.org/v2";


    public ClassementFragment() {
        // Required empty public constructor
    }

    public static ClassementFragment newInstance() {

        return(new ClassementFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_classement, container, false);

        boutonClassement = (Button) result.findViewById(R.id.boutonClassement);
        listeViewClassement = (ListView) result.findViewById(R.id.listViewClassement);
        spinnerClassement = (Spinner) result.findViewById(R.id.spinnerClassement);

        remplissageSpinnerClassement();

        boutonClassement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                appelAPIClassement();
            }
        });

        return result;
    }

    public void remplissageSpinnerClassement() {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(getContext());

        //String Request initialized
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlBase + "/competitions/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray competitions = response.getJSONArray("competitions");
                    ArrayList<String> listeCompetitions = new ArrayList<>();

                    for (int nbCompet = 0; nbCompet < competitions.length(); nbCompet++) {
                        JSONObject competition = competitions.getJSONObject(nbCompet);
                        if (competition.getString("plan").equals("TIER_ONE")){
                            listeCompetitions.add(competition.getString("name") + " (" +  competition.getJSONObject("area").getString("name") + ") - " + competition.getString("code"));
                        }
                    }
                    ArrayAdapter<String> adapterCompetitions = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listeCompetitions);
                    spinnerClassement.setAdapter(adapterCompetitions);

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

    private void appelAPIClassement() {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(getContext());

        String codeCompet = spinnerClassement.getSelectedItem().toString().split("-")[1].trim();

        //String Request initialized
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlBase + "/competitions/" + codeCompet + "/standings", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray classement = response.getJSONArray("standings").getJSONObject(0).getJSONArray("table");
                    ArrayList<EquipeClassement> listeEquipesClassement = new ArrayList<>();

                    for (int idEquipeClassement = 0; idEquipeClassement < classement.length(); idEquipeClassement++) {
                        JSONObject equipeClassement = classement.getJSONObject(idEquipeClassement);
                        listeEquipesClassement.add(
                                new EquipeClassement(
                                        equipeClassement.getString("position"),
                                        equipeClassement.getJSONObject("team").getString("crestUrl"),
                                        equipeClassement.getJSONObject("team").getString("name"),
                                        equipeClassement.getString("points")
                                )
                        );
                    }
                    ClassementAdapter classementAdapter = new ClassementAdapter(getContext(), listeEquipesClassement);
                    listeViewClassement.setAdapter(classementAdapter);

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
