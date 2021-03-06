package com.example.applicationfootjerem.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.applicationfootjerem.Models.Match;
import com.example.applicationfootjerem.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class LiveAdapter extends ArrayAdapter<Match> {

    private Context mContext;
    private List<Match> matchsListe;
    private DateFormat dateFormatHHmm = new SimpleDateFormat("HH:mm");

    //tweets est la liste des models à afficher
    public LiveAdapter(Context context, List<Match> listeMatchs) {
        super(context, 0, listeMatchs);
        mContext = context;
        matchsListe = listeMatchs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_match_live,parent, false);
        }

        TextView nomCompetitionLiveItem = (TextView) convertView.findViewById(R.id.textViewCompetitionLiveItem);
        TextView nomEquipeDom = (TextView) convertView.findViewById(R.id.nomEquipeDomLiveItem);
        TextView scoreMatch = (TextView) convertView.findViewById(R.id.scoreMatchLiveItem);
        TextView nomEquipeExt = (TextView) convertView.findViewById(R.id.nomEquipeExtLiveItem);

        Match match = matchsListe.get(position);

        if (position != 0 && match.getCompetition().equals(matchsListe.get(position - 1).getCompetition())){
            nomCompetitionLiveItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
        } else {
            nomCompetitionLiveItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            nomCompetitionLiveItem.setText(match.getPays() + " - " + match.getCompetition());
        }
        nomEquipeDom.setText(match.getNomDom());
        nomEquipeExt.setText(match.getNomExt());
        scoreMatch.setTextSize(16);

        if (match.getStatut().equals("POSTPONED")){
            scoreMatch.setTypeface(Typeface.DEFAULT_BOLD);
            scoreMatch.setText("REP");
        } else {
            scoreMatch.setTypeface(Typeface.DEFAULT);
            if (match.getScoreDom() != null){
                scoreMatch.setText(match.getScoreDom()+ " - " + match.getScoreExt());
                if (match.getStatut().equals("IN_PLAY") || match.getStatut().equals("PAUSED")){
                    scoreMatch.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                } else {
                    scoreMatch.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNoir));
                }
            } else {
                scoreMatch.setTextSize(12);
                scoreMatch.setText(this.dateFormatHHmm.format(match.getDateMatch()));
                scoreMatch.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNoir));
            }
        }

        return convertView;
    }
}
