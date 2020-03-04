package com.example.applicationfootjerem.Models;

import java.util.Date;

public class Match {
    private Date dateMatch;
    private String nomDom;
    private String scoreDom;
    private String scoreExt;
    private String nomExt;

    public Match(Date dateMatch, String nomDom, String scoreDom, String scoreExt, String nomExt) {
        this.dateMatch = dateMatch;
        this.nomDom = nomDom;
        this.scoreDom = scoreDom;
        this.scoreExt = scoreExt;
        this.nomExt = nomExt;
    }

    public Date getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(Date dateMatch) {
        this.dateMatch = dateMatch;
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
}
