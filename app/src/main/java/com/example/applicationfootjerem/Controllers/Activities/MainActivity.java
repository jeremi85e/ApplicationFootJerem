package com.example.applicationfootjerem.Controllers.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.applicationfootjerem.Adapters.OngletAdapter;
import com.example.applicationfootjerem.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private Button btnRequest;
    private ListView listViewResultats;
    private Spinner spinnerJournees;
    private Spinner spinnerChampionnats;

    private RequestQueue mRequestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private String urlBase = "http://api.football-data.org/v2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureViewPagerAndTabs();

        /*btnRequest = (Button) findViewById(R.id.buttonRequest);
        listViewResultats = (ListView) findViewById(R.id.listViewResultats);
        spinnerJournees = (Spinner) findViewById(R.id.spinnerJournees);
        spinnerChampionnats = (Spinner) findViewById(R.id.spinnerChampionnats);

        remplissageSpinnerChampionnats();

        Integer[] items = new Integer[38];
        for (int i = 0; i < 38; i++) {
            items[i] = i+1;
        }
        ArrayAdapter<Integer> adapterJournees = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerJournees.setAdapter(adapterJournees);

        btnRequest.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v){
                  sendAndRequestResponse();
              }
        });*/
    }

    //Fais la liaison avec les onglets et le ViewPager
    private void configureViewPagerAndTabs(){
        // 1 - Get ViewPager from layout
        ViewPager pager = (ViewPager)findViewById(R.id.mainViewPager);
        // 2 - Set Adapter OngletAdapter and glue it together
        pager.setAdapter(new OngletAdapter(getSupportFragmentManager()) {
        });

        // 1 - Get TabLayout from layout
        TabLayout tabs = (TabLayout)findViewById(R.id.activityTabLayout);
        // 2 - Assembler les onglets et le ViewPager
        tabs.setupWithViewPager(pager);
        // 3 - Pour que les onglets aient tous la même taille
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }
/*    private void sendAndRequestResponse() {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

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
                    MatchAdapter matchAdapter = new MatchAdapter(MainActivity.this, listeMatchs);
                    listViewResultats.setAdapter(matchAdapter);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Erreur : " + e.toString(), Toast.LENGTH_LONG).show();
                    Log.e("MYAPP", "unexpected JSON exception", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Erreur : " + error.toString(), Toast.LENGTH_LONG).show();
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

    public void remplissageSpinnerChampionnats() {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

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
                    ArrayAdapter<String> adapterCompetitions = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, listeCompetitions);
                    spinnerChampionnats.setAdapter(adapterCompetitions);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Erreur : " + e.toString(), Toast.LENGTH_LONG).show();
                    Log.e("MYAPP", "unexpected JSON exception", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Erreur : " + error.toString(), Toast.LENGTH_LONG).show();
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

    public int getEcusson(String nomEquipe) {
        if (nomEquipe.contains("Amiens")){
            return R.drawable.amiens;
        }
        if (nomEquipe.contains("Angers")){
            return R.drawable.angers;
        }
        if (nomEquipe.contains("Bordeaux")){
            return R.drawable.bordeaux;
        }
        if (nomEquipe.contains("Brest")){
            return R.drawable.brest;
        }
        if (nomEquipe.contains("Dijon")){
            return R.drawable.dijon;
        }
        if (nomEquipe.contains("Lille")){
            return R.drawable.lille;
        }
        if (nomEquipe.contains("Lyon")){
            return R.drawable.lyon;
        }
        if (nomEquipe.contains("Marseille")){
            return R.drawable.marseille;
        }
        if (nomEquipe.contains("Metz")){
            return R.drawable.metz;
        }
        if (nomEquipe.contains("Monaco")){
            return R.drawable.monaco;
        }
        if (nomEquipe.contains("Montpellier")){
            return R.drawable.montpellier;
        }
        if (nomEquipe.contains("Nantes")){
            return R.drawable.nantes;
        }
        if (nomEquipe.contains("Nice")){
            return R.drawable.nice;
        }
        if (nomEquipe.contains("Nîmes")){
            return R.drawable.nimes;
        }
        if (nomEquipe.contains("Paris")){
            return R.drawable.paris;
        }
        if (nomEquipe.contains("Reims")){
            return R.drawable.reims;
        }
        if (nomEquipe.contains("Stade Rennais")){
            return R.drawable.rennes;
        }
        if (nomEquipe.contains("Saint-Étienne")){
            return R.drawable.saint_etienne;
        }
        if (nomEquipe.contains("Strasbourg")){
            return R.drawable.strasbourg;
        }
        if (nomEquipe.contains("Toulouse")){
            return R.drawable.toulouse;
        }
        return 0;
    }*/
}
