package com.example.applicationfootjerem.Views;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationfootjerem.Models.Match;
import com.example.applicationfootjerem.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchCalendrierViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.nomEquipeDom) TextView nomEquipeDom;
    @BindView(R.id.scoreEquipeDom) TextView scoreEquipeDom;
    @BindView(R.id.scoreEquipeExt) TextView scoreEquipeExt;
    @BindView(R.id.nomEquipeExt) TextView nomEquipeExt;

    public MatchCalendrierViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void remplissageCalendrier(Match match){
        this.nomEquipeDom.setText(match.getNomDom());
        this.scoreEquipeDom.setText(match.getScoreDom());
        this.scoreEquipeExt.setText(match.getScoreExt());
        this.nomEquipeExt.setText(match.getNomExt());
    }
}