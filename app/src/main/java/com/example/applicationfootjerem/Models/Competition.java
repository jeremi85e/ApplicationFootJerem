package com.example.applicationfootjerem.Models;

import java.util.ArrayList;

public class Competition implements Comparable<Competition>{
    private String nom;
    private String pays;
    private String code;
    private int journeeActuelle;
    private ArrayList<String> listeJournees;

    public Competition(String nom, String pays, String code, int journeeActuelle) {
        this.nom = nom;
        this.pays = pays;
        this.code = code;
        this.journeeActuelle = journeeActuelle;
        this.listeJournees = new ArrayList<String>();
        setListeJournees();
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

    public ArrayList<String> getListeJournees() {
        return listeJournees;
    }

    public void setListeJournees(){
        switch (this.getCode()){
            case "CL" :
                for (int i=0 ; i<6 ; i++){
                    this.listeJournees.add(i,"" + (i + 1));
                }
                this.listeJournees.add(6,"ROUND_OF_16");
                this.listeJournees.add(7,"QUARTER_FINALS");
                this.listeJournees.add(8,"SEMI_FINALS");
                this.listeJournees.add(9,"FINAL");
                break;
            case "EC" :
            case "WC" :
                for (int i=0 ; i<3 ; i++){
                    this.listeJournees.add(i,"" + (i + 1));
                }
                this.listeJournees.add(3,"ROUND_OF_16");
                this.listeJournees.add(4,"QUARTER_FINALS");
                this.listeJournees.add(5,"SEMI_FINALS");
                this.listeJournees.add(6,"3RD_PLACE");
                this.listeJournees.add(7,"FINAL");
                break;
            case "ELC" :
                for (int i=0 ; i<46 ; i++){
                    this.listeJournees.add(i,(i + 1) + "");
                }
                break;
            case "BL1" :
            case "DED" :
            case "PPL" :
                for (int i=0 ; i<34 ; i++){
                    this.listeJournees.add(i,(i + 1) + "");
                }
                break;
            default :
                for (int i=0 ; i<38 ; i++){
                    this.listeJournees.add(i,(i + 1) + "");
                }
                break;
        }
    }

    @Override
    public int compareTo(Competition c) {
        return this.getPays().compareTo(c.getPays());
    }
}
