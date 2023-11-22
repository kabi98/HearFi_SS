package com.example.hearfiss_01.db.entity.HearingTest;

import android.util.Log;

public class PttTestSet {
    int     ps_id;
    String  ps_Date;
    String  ps_pta;
    int  acc_id;
    String  ps_Result;

    public PttTestSet() {
    }

    public PttTestSet(String ps_Date, String ps_pta, int acc_id, String ps_Result) {
        this.ps_Date = ps_Date;
        this.ps_pta = ps_pta;
        this.acc_id = acc_id;
        this.ps_Result = ps_Result;
    }

    public PttTestSet(int ps_id, String ps_Date, String ps_pta, int acc_id, String ps_Result) {
        this.ps_id = ps_id;
        this.ps_Date = ps_Date;
        this.ps_pta = ps_pta;
        this.acc_id = acc_id;
        this.ps_Result = ps_Result;
    }

    public int getPs_id() {
        return ps_id;
    }

    public void setPs_id(int ps_id) {
        this.ps_id = ps_id;
    }

    public String getPs_Date() {
        return ps_Date;
    }

    public void setPs_Date(String ts_Date) {
        this.ps_Date = ps_Date;
    }

    public String getPs_pta() {
        return ps_pta;
    }

    public void setPs_pta(String ps_pta) {
        this.ps_pta = ps_pta;
    }

    public String getPs_Result() {
        return ps_Result;
    }

    public void setPs_Result(String ps_Result) {
        this.ps_Result = ps_Result;
    }

    public int getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(int acc_id) {
        this.acc_id = acc_id;
    }


    public void print() {
        Log.v("HrTestSet - print",
                String.format(" %d, %s, %s, %s, %d, %s, %s ",
                        ps_id, ps_Date, ps_pta, acc_id, ps_Result));
    }

    @Override
    public String toString() {
        return "PtTestSet{" +
                "ps_id=" + ps_id +
                ", ps_Date=" + ps_Date +
                ", ps_pta='" + ps_pta + '\'' +
                ", acc_id='" + acc_id + '\'' +
                ", ps_Result='" + ps_Result + '\'' +
                '}';
    }
}