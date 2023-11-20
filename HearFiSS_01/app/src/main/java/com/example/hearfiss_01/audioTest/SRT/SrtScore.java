package com.example.hearfiss_01.audioTest.SRT;

import android.util.Log;

import java.util.ArrayList;

public class SrtScore {
    ArrayList<SrtUnit> m_alSrtUnit = null;
    int m_iQuestion, m_iCorrect, m_iScore, m_iPassTrsd;
    int m_iCntTurnUp, m_iPrevDb, m_iCurDb;
    boolean m_isEnd;

    public SrtScore()
    {
        m_alSrtUnit = new ArrayList<>();
        m_iQuestion = 0;
        m_iCorrect = 0;
        m_iScore = 0;
        m_iPassTrsd = 1000;

        m_iCurDb = 0;
        m_iPrevDb = 0;
        m_iCntTurnUp = 0;

        m_isEnd = false;
    }

    public int getM_iCurDb() {
        return m_iCurDb;
    }

    public int getM_iPrevDb() {
        return m_iPrevDb;
    }

    public boolean getM_isEnd() {
        return m_isEnd;
    }

    public int getM_iPassTrsd() {
        return m_iPassTrsd;
    }


    public int getM_iQuestion() {
        return m_iQuestion;
    }

    public int getM_iCorrect() {
        return m_iCorrect;
    }

    public int getM_iScore() {
        return m_iScore;
    }

    public ArrayList<SrtUnit> getM_alSrtUnit(){
        return m_alSrtUnit;
    }

    public int addTestUnit(SrtUnit tuAdd){
        m_alSrtUnit.add(tuAdd);
        print();
        return 1;
    }

    public void print(){
        Log.v("SrtScore", "print" );

        if( m_alSrtUnit.size() <= 0 )
            return;

        for(SrtUnit unitOne : m_alSrtUnit) {
            Log.v("SrtScore print", unitOne.toString() );
        }
    }


    public int countRecentDbHL(SrtUnit unitAnswer){
        Log.v("SrtScore",
                String.format("countRecentDbHL Q:%s, A:%s, C:%d, cur:%d, next:%d",
                        unitAnswer.get_Question(), unitAnswer.get_Answer(),
                        unitAnswer.get_Correct(), unitAnswer.get_CurDb(),
                        unitAnswer.get_NextDb()) );
        m_iQuestion = 0;
        m_iCorrect = 0;

        if(unitAnswer == null)
            return -1;

        m_iQuestion++;
        if (unitAnswer.get_Question().equals(unitAnswer.get_Answer())) {
            unitAnswer.set_Correct(1);
            m_iCorrect++;
        }
        else{
            unitAnswer.set_Correct(0);
        }

        if(m_alSrtUnit.size() <= 0) {
            return 0;
        }

        int iAnswerDb = unitAnswer.get_CurDb();
        for(int i = m_alSrtUnit.size()-1; i >= 0; i--){
            if(m_alSrtUnit.get(i).get_CurDb() != iAnswerDb){
                break;
            }
            m_iQuestion ++;
            if (m_alSrtUnit.get(i).get_Correct() == 1)
                m_iCorrect ++;
        }

        return 0;
    }

    public int addAnswer(SrtUnit unitAnswer){
        if(unitAnswer == null)
            return -1;

        Log.v("SrtScore - addAnswer",
                String.format("Q:%s, A:%s, C:%d, cur:%d, next:%d",
                        unitAnswer.get_Question(), unitAnswer.get_Answer(),
                        unitAnswer.get_Correct(), unitAnswer.get_CurDb(),
                        unitAnswer.get_NextDb()) );

        m_alSrtUnit.add(unitAnswer);
        return 0;
    }

    public int calcNextDbNSaveAnswer(SrtUnit unitAnswer) {
//        Log.v("SrtScore", " calcNextDbNSaveAnswer ");
        if(countRecentDbHL(unitAnswer) == -1) {
            Log.v("SrtScore", "calcNextDbNSaveAnswer unitAnswer is NULL ");
            return -1;
        }

        int iCurDb = unitAnswer.get_CurDb();
        if(m_iCurDb != iCurDb) {
            m_iPrevDb = m_iCurDb;
            m_iCurDb = iCurDb;
        }

        if(m_iQuestion < 2){
            Log.v("SrtScore", "calcNextDbNSaveAnswer m_iQuestion < 2 ");

            unitAnswer.set_NextDb(iCurDb);
            addAnswer(unitAnswer);
            return 1;
        }

        m_iScore = 0;
        int iNextDb = iCurDb;

        m_iScore = (int)(((float)m_iCorrect/(float)m_iQuestion) * 100);
        if(m_iScore > 50){
            iNextDb = iCurDb - 10;
            m_iPassTrsd = iCurDb;
        } else if (m_iScore < 50) {
            iNextDb = iCurDb + 5;
            if(m_iPrevDb > iCurDb) {
                m_iCntTurnUp++;
            }
        } else {
            // Test Continue;
        }

        if(iNextDb < 0) {
            iNextDb = 0;
            m_isEnd = true;
        } else if (iNextDb > 100){
            iNextDb = 100;
            m_isEnd = true;
        }

        if(m_iScore > 50 && m_iCntTurnUp > 0){
            m_isEnd = true;
        }

        Log.v("SrtScore",
                String.format("calcNextDbNSaveAnswer Threshold:%d, end:%b", m_iPassTrsd, m_isEnd) );
        unitAnswer.set_NextDb(iNextDb);
        addAnswer(unitAnswer);

        return 1;
    }

    public int clear(){
        m_alSrtUnit.clear();
        m_iQuestion = 0;
        m_iCorrect = 0;        m_iScore = 0;
        m_iPassTrsd = 1000;

        m_iCurDb = 0;
        m_iPrevDb = 0;
        m_iCntTurnUp = 0;

        m_isEnd = false;
        return 1;
    }
}


