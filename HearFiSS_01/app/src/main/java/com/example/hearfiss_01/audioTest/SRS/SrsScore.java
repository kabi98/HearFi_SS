package com.example.hearfiss_01.audioTest.SRS;

import android.util.Log;


import com.example.hearfiss_01.db.DTO.AmTrack;
import com.example.hearfiss_01.db.DTO.HrTestUnit;
import com.example.hearfiss_01.db.DTO.StWord;
import com.example.hearfiss_01.db.dao.SrsDAO;

import java.util.ArrayList;

public class SrsScore {

    String m_TAG = "SrsScore";

    ArrayList<SrsUnit> m_alSrs = null;

    int m_iQuestion, m_iCorrect, m_iScore;
    ArrayList<HrTestUnit> m_allUnit = null;
    String question;
    String answer;
    ArrayList<StWord> m_alWord = null;

    ArrayList<String> u_A;

    ArrayList<String> c_Q;


    AmTrack m_atCur = null;
    SrsDAO srsDAO;


    public ArrayList<SrsUnit> getM_alSrs(){
        return m_alSrs;
    }

    public SrsScore(){
        m_iQuestion = -1;
        m_alSrs = new ArrayList<>();
    }

    @Override
    public String toString(){
        return "SrsScoer{" +
                "m_alSrs=" + m_alSrs +
                ", m_iQuestion=" + m_iQuestion +
                ", m_iCorrect=" + m_iCorrect +
                ", m_iScore=" + m_iScore +
                '}';
    }

    public void setM_alSrs(ArrayList<SrsUnit> m_alSrs){
        this.m_alSrs = m_alSrs;
    }

    public int getM_iQuestion() {
        return m_iQuestion;
    }

    public void setM_iQuestion(int m_iQuestion) {
        this.m_iQuestion = m_iQuestion;
    }

    public int getM_iCorrect() {
        return m_iCorrect;
    }

    public void setM_iCorrect(int m_iCorrect) {
        this.m_iCorrect = m_iCorrect;
    }

    public int getM_iScore() {
        return m_iScore;
    }

    public void setM_iScore(int m_iScore) {
        this.m_iScore = m_iScore;
    }
    public int addSrsUnit(SrsUnit suAdd){
        Log.v(m_TAG, "addSrsUnit");
        if (suAdd == null) {
            Log.v(m_TAG, "addSrsUnit - SrsUnit = null");
            return 0;
        }
        if (suAdd.getTu_Question().equals(suAdd.getTu_Answer())){
            Log.v(m_TAG,"addSrsUnit - Qusetion == Answer");
            suAdd.setTu_IsCorrect(1);
        }else {
            Log.v(m_TAG, "addSrsUnit - Question != Answer");
            suAdd.setTu_IsCorrect(0);
        }
        m_alSrs.add(suAdd);
        Log.v(m_TAG, "addSrsUnit - added success");
        return 1;
    }

}



