package com.example.applicationfootjerem.Adapters;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationfootjerem.Models.Match;
import com.example.applicationfootjerem.R;
import com.example.applicationfootjerem.Views.MatchCalendrierViewHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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