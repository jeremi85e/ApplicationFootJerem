package com.example.applicationfootjerem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.applicationfootjerem.Models.Match;
import com.example.applicationfootjerem.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class MatchAdapter extends ArrayAdapter<Match> {

    private Context mContext;
    private List<Match> matchsListe;
    private DateFormat ddMMyyyyHHmm = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    //tweets est la liste des models à afficher
    public MatchAdapter(Context context, List<Match> listeMatchs) {
        super(context, 0, listeMatchs);
        mContext = context;
        matchsListe = listeMatchs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.match_calendrier_item,parent, false);
        }

        TextView nomEquipeDom = (TextView) convertView.findViewById(R.id.nomEquipeDom);
        TextView scoreEquipeDom = (TextView) convertView.findViewById(R.id.scoreEquipeDom);
        TextView textViewHoraireMatch = (TextView) convertView.findViewById(R.id.textViewHoraireMatch);
        TextView scoreEquipeExt = (TextView) convertView.findViewById(R.id.scoreEquipeExt);
        TextView nomEquipeExt = (TextView) convertView.findViewById(R.id.nomEquipeExt);

        //getItem(position) va récupérer l'item [position] de la List<Match> listeMatchs
        Match match = matchsListe.get(position);

        //il ne reste plus qu'à remplir notre vue
        nomEquipeDom.setText(match.getNomDom());
        if (match.getScoreDom() != null){
            scoreEquipeDom.setText(match.getScoreDom());
            scoreEquipeExt.setText(match.getScoreExt());
        } else {
            textViewHoraireMatch.setText(this.ddMMyyyyHHmm.format(match.getDateMatch()));
        }
        nomEquipeExt.setText(match.getNomExt());

        return convertView;
    }
}
