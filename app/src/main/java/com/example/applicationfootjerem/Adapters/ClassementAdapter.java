package com.example.applicationfootjerem.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.applicationfootjerem.Models.EquipeClassement;
import com.example.applicationfootjerem.Models.Match;
import com.example.applicationfootjerem.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClassementAdapter extends ArrayAdapter<EquipeClassement> {

    private Context mContext;
    private List<EquipeClassement> equipeClassementListe;

    public ClassementAdapter(@NonNull Context context, ArrayList<EquipeClassement> list) {
        super(context, 0 , list);
        mContext = context;
        equipeClassementListe = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.equipe_classement_item,parent,false);

        EquipeClassement currentEquipe = equipeClassementListe.get(position);

        TextView positionEquipe = (TextView) listItem.findViewById(R.id.positionEquipeClassement);
        positionEquipe.setText(currentEquipe.getPosition());

        ImageView ecussonEquipe = (ImageView) listItem.findViewById(R.id.ecussonEquipeClassement);
        Picasso.get().load(currentEquipe.getEcusson()).into(ecussonEquipe);

        TextView nomEquipe = (TextView) listItem.findViewById(R.id.nomEquipeClassement);
        nomEquipe.setText(currentEquipe.getNom());

        TextView pointsEquipe = (TextView) listItem.findViewById(R.id.pointsEquipeClassement);
        pointsEquipe.setText(currentEquipe.getPoints());

        return listItem;
    }
}