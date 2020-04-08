package com.example.applicationfootjerem.Controllers.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.applicationfootjerem.Adapters.LiveAdapter;
import com.example.applicationfootjerem.Models.Match;
import com.example.applicationfootjerem.R;
import com.example.applicationfootjerem.Services.LiveService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class LiveFragment extends Fragment{

    private static final String TAG = "CalendrierFragment";
    private ListView listViewLive;
    private TextView textViewPasDeLive;
    DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static LiveFragment newInstance() {
        return (new LiveFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_live, container, false);

        listViewLive = (ListView) result.findViewById(R.id.listViewLive);
        textViewPasDeLive = (TextView) result.findViewById(R.id.textViewPasDeLive);

        getLiveScore();

        return result;
    }

    public void getLiveScore() {
        LiveService liveService = new LiveService(getContext());
        try {
            JSONArray matches = liveService.getMatchsLive().getJSONArray("matches");
            ArrayList<Match> listeMatchs = new ArrayList<>();

            if (matches.length() == 0){
                textViewPasDeLive.setText("Il n'y a aucun match aujourd'hui...");
                textViewPasDeLive.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            } else {
                textViewPasDeLive.setHeight(0);
                for (int idMatch = 0; idMatch < matches.length(); idMatch++) {
                    JSONObject match = matches.getJSONObject(idMatch);
                    listeMatchs.add(new Match(
                        m_ISO8601Local.parse(match.getString("utcDate")),
                        match.getString("status"),
                        match.getJSONObject("competition").getJSONObject("area").getString("name"),
                        match.getJSONObject("competition").getString("name"),
                        match.getJSONObject("homeTeam").getString("name"),
                        !match.getJSONObject("score").getJSONObject("fullTime").isNull("homeTeam") ? match.getJSONObject("score").getJSONObject("fullTime").getString("homeTeam") : null,
                        !match.getJSONObject("score").getJSONObject("fullTime").isNull("awayTeam") ? match.getJSONObject("score").getJSONObject("fullTime").getString("awayTeam") : null,
                        match.getJSONObject("awayTeam").getString("name")
                    ));
                }
                Collections.sort(listeMatchs);
                LiveAdapter liveAdapter = new LiveAdapter(getContext(), listeMatchs);
                listViewLive.setAdapter(liveAdapter);
            }
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Erreur : " + e.toString(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "unexpected parse exception", e);
        }  catch (JSONException e){
            Toast.makeText(getContext(), "Erreur : Could not parse response", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Erreur : Could not parse response");
        }
    }
}
