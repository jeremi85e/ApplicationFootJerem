package com.example.applicationfootjerem.Models;

import java.util.Date;

public class Match implements Comparable<Match> {
    private Date dateMatch;
    private String statut;;
    private String pays;
    private String competition;
    private String nomDom;
    private String scoreDom;
    private String scoreExt;
    private String nomExt;

    public Match(Date dateMatch, String statut, String pays, String competition, String nomDom, String scoreDom, String scoreExt, String nomExt) {
        this.dateMatch = dateMatch;
        this.statut = statut;
        this.pays = pays;
        this.competition = competition;
        this.nomDom = nomDom;
        this.scoreDom = scoreDom;
        this.scoreExt = scoreExt;
        this.nomExt = nomExt;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
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

    @Override
    public int compareTo(Match m) {
        if (this.getPays().compareTo(m.getPays()) == 0) {
            if (this.getCompetition().compareTo(m.getCompetition()) == 0) {
                if (this.getDateMatch().compareTo(m.getDateMatch()) == 0) {
                    return this.getNomDom().compareTo(m.getNomDom());
                }
                return this.getDateMatch().compareTo(m.getDateMatch());
            }
            return this.getCompetition().compareTo(m.getCompetition());
        }
        return this.getPays().compareTo(m.getPays());
    }
}
