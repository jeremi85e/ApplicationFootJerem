package com.example.applicationfootjerem.Models;

import android.net.Uri;

import java.net.URL;

public class EquipeClassement {
    private String position;
    private String ecusson;
    private String nom;
    private String points;

    public EquipeClassement(String position, String ecusson, String nom, String points) {
        this.position = position;
        this.ecusson = ecusson;
        this.nom = nom;
        this.points = points;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEcusson() {
        return ecusson;
    }

    public void setEcusson(String ecusson) {
        this.ecusson = ecusson;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
