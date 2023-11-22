package com.example.hearfiss_01.audioTest.SRS;

import com.example.hearfiss_01.db.entity.HearingTest.HrTestUnit;
import com.example.hearfiss_01.db.entity.HearingTest.StWord;

import java.util.ArrayList;

public class SrsScore {

    ArrayList<HrTestUnit> m_allUnit = null;
    String question;
    String answer;
    ArrayList<StWord> m_alWord = null;

    public SrsScore(){
        m_allUnit = new ArrayList<HrTestUnit>();
        question = "";
        answer = "";
        m_alWord = new ArrayList<StWord>();
    }

    public ArrayList<HrTestUnit> getM_allUnit() {
        return m_allUnit;
    }

    public void setM_allUnit(ArrayList<HrTestUnit> m_allUnit) {
        this.m_allUnit = m_allUnit;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<StWord> getM_alWord() {
        return m_alWord;
    }

    public void setM_alWord(ArrayList<StWord> m_alWord) {
        this.m_alWord = m_alWord;
    }
}
