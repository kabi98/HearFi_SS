package com.example.hearfiss_01.entity.HearingTest;

import android.util.Log;

public class PttTestUnit {

    int     pu_id; // PRIMARY KEY
    int     ps_id; // 외래키 (세트 번호)
    String  pu_date;
    String  pu_hertz;
    int     pu_decibel;
    int     acc_id;

    public PttTestUnit() {
    }

    public PttTestUnit(int pu_id, int ps_id, String pu_date, String pu_hertz, int pu_decibel) {
        this.pu_id = pu_id;
        this.ps_id = ps_id;
        this.pu_date = pu_date;
        this.pu_hertz = pu_hertz;
        this.pu_decibel = pu_decibel;
    }

    public PttTestUnit(int ps_id, String pu_date, String pu_hertz, int pu_decibel) {
        this.ps_id = ps_id;
        this.pu_date = pu_date;
        this.pu_hertz = pu_hertz;
        this.pu_decibel = pu_decibel;
    }

    public int getPu_id() {
        return pu_id;
    }

    public void setPu_id(int pu_id) {
        this.pu_id = pu_id;
    }

    public int getPs_id() {
        return ps_id;
    }

    public void setPs_id(int ps_id) {
        this.ps_id = ps_id;
    }

    public String getPu_date() {
        return pu_date;
    }

    public void setPu_date(String pu_date) {
        this.pu_date = pu_date;
    }

    public String getPu_hertz() {
        return pu_hertz;
    }

    public void setPu_hertz(String pu_hertz) {
        this.pu_hertz = pu_hertz;
    }

    public int getPu_decibel() {
        return pu_decibel;
    }

    public void setPu_decibel(int pu_decibel) {
        this.pu_decibel = pu_decibel;
    }

    public void print() {
        Log.v("HrTestUnit - print",
                String.format("D: %s, H: %s, B: %d",
                        pu_date, pu_hertz, pu_decibel) );
    }

    @Override
    public String toString() {
        return "HrTestUnit{" +
                "id=" + pu_id +
                ", ps_id=" + ps_id +
                ", D='" + pu_date + '\'' +
                ", H='" + pu_hertz + '\'' +
                ", B=" + pu_decibel +
                '}';
    }

}