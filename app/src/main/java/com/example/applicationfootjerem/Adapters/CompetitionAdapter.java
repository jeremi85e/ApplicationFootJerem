package com.example.applicationfootjerem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.applicationfootjerem.Models.Competition;
import com.example.applicationfootjerem.R;

import java.util.List;


public class CompetitionAdapter extends ArrayAdapter<Competition> {

    private Context mContext;
    private List<Competition> competitionsListe;

    public CompetitionAdapter(Context context, List<Competition> listeCompetitions) {
        super(context, 0, listeCompetitions);
        mContext = context;
        competitionsListe = listeCompetitions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.competition_listview_item,parent, false);
        }
        TextView textViewCompetitionSpinnerItem = (TextView) convertView.findViewById(R.id.textViewCompetitionListViewItem);
        textViewCompetitionSpinnerItem.setText(competitionsListe.get(position).getPays() + " - " + competitionsListe.get(position).getNom());

        return convertView;
    }
}
