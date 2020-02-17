package com.example.applicationfootjerem.Models;

public class Match {
    private int ecussonDom;
    private String nomDom;
    private String scoreDom;
    private String scoreExt;
    private String nomExt;
    private int ecussonExt;

    public Match(int ecussonDom, String nomDom, String scoreDom, String scoreExt, String nomExt, int ecussonExt) {
        this.ecussonDom = ecussonDom;
        this.nomDom = nomDom;
        this.scoreDom = scoreDom;
        this.scoreExt = scoreExt;
        this.nomExt = nomExt;
        this.ecussonExt = ecussonExt;
    }

    public int getEcussonDom() {
        return ecussonDom;
    }

    public void setEcussonDom(int ecussonDom) {
        this.ecussonDom = ecussonDom;
    }

    public String getNomDom() {
        return nomDom;
    }

    public void setNomDom(String nomDom) {
        this.nomDom = nomDom;
    }

    public String getScoreDom() {
        return scoreDom;
    }

    public void setScoreDom(String scoreDom) {
        this.scoreDom = scoreDom;
    }

    public String getScoreExt() {
        return scoreExt;
    }

    public void setScoreExt(String scoreExt) {
        this.scoreExt = scoreExt;
    }

    public String getNomExt() {
        return nomExt;
    }

    public void setNomExt(String nomExt) {
        this.nomExt = nomExt;
    }

    public int getEcussonExt() {
        return ecussonExt;
    }

    public void setEcussonExt(int ecussonExt) {
        this.ecussonExt = ecussonExt;
    }
}
