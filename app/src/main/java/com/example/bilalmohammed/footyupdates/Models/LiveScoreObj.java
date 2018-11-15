package com.example.bilalmohammed.footyupdates.Models;



public class LiveScoreObj {
    public String id, score,home_name,away_name,ht_score,ft_score,status,league_name;

     // stores all the objects that are ging to be used]

    public LiveScoreObj(String id, String score, String home_name, String away_name, String ht_score, String ft_score, String status, String league_name) {
        this.id = id;
        this.score = score;
        this.home_name = home_name;
        this.away_name = away_name;
        this.ht_score = ht_score;
        this.ft_score = ft_score;
        this.status = status;
        this.league_name = league_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public String getAway_name() {
        return away_name;
    }

    public void setAway_name(String away_name) {
        this.away_name = away_name;
    }

    public String getHt_score() {
        return ht_score;
    }

    public void setHt_score(String ht_score) {
        this.ht_score = ht_score;
    }

    public String getFt_score() {
        return ft_score;
    }

    public void setFt_score(String ft_score) {
        this.ft_score = ft_score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }
}
