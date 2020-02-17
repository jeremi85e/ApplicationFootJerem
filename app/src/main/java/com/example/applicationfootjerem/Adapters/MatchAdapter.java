package com.example.applicationfootjerem.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationfootjerem.Models.Match;
import com.example.applicationfootjerem.R;
import com.example.applicationfootjerem.Views.MatchCalendrierViewHolder;

import java.util.List;


public class MatchAdapter extends RecyclerView.Adapter<MatchCalendrierViewHolder> {

    // FOR DATA
    private List<Match> listeMatchs;

    // CONSTRUCTOR
    public MatchAdapter(List<Match> listeMatchs) {
        this.listeMatchs = listeMatchs;
    }

    @Override
    public MatchCalendrierViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.liste_resultats_item, parent, false);

        
        return new MatchCalendrierViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A GITHUBUSER
    @Override
    public void onBindViewHolder(MatchCalendrierViewHolder viewHolder, int position) {
        viewHolder.remplissageCalendrier(this.listeMatchs.get(position));
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.listeMatchs.size();
    }
}