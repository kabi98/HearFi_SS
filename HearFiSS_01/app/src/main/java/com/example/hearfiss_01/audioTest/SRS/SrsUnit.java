package com.example.hearfiss_01.audioTest.SRS;

public class SrsUnit {
    int sw_id;

    String sw_word;

    int at_id;

    int sw_idx;

    public SrsUnit() {
    }

    public SrsUnit(int sw_id, String sw_word, int at_id, int sw_idx) {
        this.sw_id = sw_id;
        this.sw_word = sw_word;
        this.at_id = at_id;
        this.sw_idx = sw_idx;
    }

    public int getSw_id() {
        return sw_id;
    }

    public void setSw_id(int sw_id) {
        this.sw_id = sw_id;
    }

    public String getSw_word() {
        return sw_word;
    }

    public void setSw_word(String sw_word) {
        this.sw_word = sw_word;
    }

    public int getAt_id() {
        return at_id;
    }

    public void setAt_id(int at_id) {
        this.at_id = at_id;
    }

    public int getSw_idx() {
        return sw_idx;
    }

    public void setSw_idx(int sw_idx) {
        this.sw_idx = sw_idx;
    }
}
