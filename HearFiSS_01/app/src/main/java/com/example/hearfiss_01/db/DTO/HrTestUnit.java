package com.example.hearfiss_01.db.DTO;

import android.util.Log;

public class HrTestUnit {

    int     tu_id; // PRIMARY KEY
    int     ts_id; // 외래키 (세트 번호)
    String  tu_Question;
    String  tu_Answer;
    int     tu_IsCorrect;

    public HrTestUnit() {
    }

    public HrTestUnit(int tu_id, int ts_id, String tu_Question, String tu_Answer, int tu_IsCorrect) {
        this.tu_id = tu_id;
        this.ts_id = ts_id;
        this.tu_Question = tu_Question;
        this.tu_Answer = tu_Answer;
        this.tu_IsCorrect = tu_IsCorrect;
    }

    public HrTestUnit(int ts_id, String tu_Question, String tu_Answer, int tu_IsCorrect) {
        this.ts_id = ts_id;
        this.tu_Question = tu_Question;
        this.tu_Answer = tu_Answer;
        this.tu_IsCorrect = tu_IsCorrect;
    }

    public int getTu_id() {
        return tu_id;
    }

    public void setTu_id(int tu_id) {
        this.tu_id = tu_id;
    }

    public int getTs_id() {
        return ts_id;
    }

    public void setTs_id(int ts_id) {
        this.ts_id = ts_id;
    }

    public String getTu_Question() {
        return tu_Question;
    }

    public void setTu_Question(String tu_Question) {
        this.tu_Question = tu_Question;
    }

    public String getTu_Answer() {
        return tu_Answer;
    }

    public void setTu_Answer(String tu_Answer) {
        this.tu_Answer = tu_Answer;
    }

    public int getTu_IsCorrect() {
        return tu_IsCorrect;
    }

    public void setTu_IsCorrect(int tu_IsCorrect) {
        this.tu_IsCorrect = tu_IsCorrect;
    }


    public void print() {
        Log.v("HrTestUnit - print",
                String.format("Q: %s, A: %s, C: %d",
                        tu_Question, tu_Answer, tu_IsCorrect) );
    }

    @Override
    public String toString() {
        return "HrTestUnit{" +
                "id=" + tu_id +
                ", ts_id=" + ts_id +
                ", Q='" + tu_Question + '\'' +
                ", A='" + tu_Answer + '\'' +
                ", C=" + tu_IsCorrect +
                '}';
    }



}
