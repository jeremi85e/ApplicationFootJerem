package com.example.applicationfootjerem.Models;

public class Competition implements Comparable<Competition>{
    private String nom;
    private String pays;
    private String code;
    private int journeeActuelle;

    public Competition(String nom, String pays, String code, int journeeActuelle) {
        this.nom = nom;
        this.pays = pays;
        this.code = code;
        this.journeeActuelle = journeeActuelle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getJourneeActuelle() {
        return journeeActuelle;
    }

    public void setJourneeActuelle(int journeeActuelle) {
        this.journeeActuelle = journeeActuelle;
    }

    @Override
    public int compareTo(Competition c) {
        return this.getPays().compareTo(c.getPays());
    }
}
