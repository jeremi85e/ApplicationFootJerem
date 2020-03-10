package com.example.applicationfootjerem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.applicationfootjerem.Models.Match;
import com.example.applicationfootjerem.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class CalendrierAdapter extends ArrayAdapter<Match> {

    private Context mContext;
    private List<Match> matchsListe;
    private DateFormat dateFormatddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat dateFormatHHmm = new SimpleDateFormat("HH:mm");

    //tweets est la liste des models à afficher
    public CalendrierAdapter(Context context, List<Match> listeMatchs) {
        super(context, 0, listeMatchs);
        mContext = context;
        matchsListe = listeMatchs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_match_calendrier,parent, false);
        }

        TextView nomEquipeDom = (TextView) convertView.findViewById(R.id.nomEquipeDom);
        TextView scoreMatch = (TextView) convertView.findViewById(R.id.scoreMatch);
        TextView nomEquipeExt = (TextView) convertView.findViewById(R.id.nomEquipeExt);

        Match match = matchsListe.get(position);

        nomEquipeDom.setText(match.getNomDom());
        if (match.getScoreDom() != null){
            scoreMatch.setText(match.getScoreDom()+ " - " + match.getScoreExt());
            if (match.getStatut() == "IN_PLAY"){ //A REVERIFIER SI CA MARCHE !!! 
                scoreMatch.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            }
        } else {
            scoreMatch.setTextSize(12);
            scoreMatch.setText(this.dateFormatddMMyyyy.format(match.getDateMatch()) + "\n" + this.dateFormatHHmm.format(match.getDateMatch()));
        }
        nomEquipeExt.setText(match.getNomExt());

        return convertView;
    }
}
