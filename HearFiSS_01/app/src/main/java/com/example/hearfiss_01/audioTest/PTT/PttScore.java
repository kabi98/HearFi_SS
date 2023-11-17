package com.example.hearfiss_01.audioTest.PTT;

import android.util.Log;

import com.example.hearfiss_01.global.TConst;

import java.util.ArrayList;

public class PttScore {
    String m_TAG = "PttScore";

    final int MIN_DBHL = 0;
    final int MAX_DBHL = 100;
    final int DELTA_DB_UP = 5;
    final int DELTA_DB_DOWN = 10;

    ArrayList<PttUnit> m_alPttUnit = null;
    ArrayList<PttThreshold> m_alThreshold = null;

    int m_iStartDBHL = 0;
    int m_iCurHzId = 0;
    int m_iTurnUp = 0;
    int m_iTurnDown = 0;

    int m_iOutOfBoundary = 0;

    int m_iCurHz = 0;
    int m_iNextDbHL = 0;
    int m_iCurDBHL = 0;
    int m_iPrevDBHL = 0;

    public int get_iCurHzId() {
        return m_iCurHzId;
    }

    public int get_iTurnUp() {
        return m_iTurnUp;
    }

    public int get_iTurnDown() {
        return m_iTurnDown;
    }

    public int get_iCurHz() {
        return m_iCurHz;
    }

    public int get_iNextDbHL() {
        return m_iNextDbHL;
    }

    public int get_iCurDbHL() {
        return m_iCurDBHL;
    }

    public int get_iPrevDbHL() {
        return m_iPrevDBHL;
    }
    public int get_iStartDBHL() {
        return m_iStartDBHL;
    }

    public void set_iStartDBHL(int _iStartDBHL) {
        m_iStartDBHL = _iStartDBHL;
        m_iNextDbHL = m_iStartDBHL;
    }

    public PttScore() {
        m_alPttUnit = new ArrayList<>();
        m_alThreshold = new ArrayList<>();
        clear();
    }

    public ArrayList<PttThreshold> getThresholdList(){
        return m_alThreshold;
    }
    public ArrayList<PttUnit> getUnitList(){

        return m_alPttUnit;
    }

    public int clear(){
        m_alPttUnit.clear();
        m_alThreshold.clear();

        m_iOutOfBoundary = 0;
        m_iTurnUp = 0;
        m_iTurnDown = 0;

        m_iCurDBHL = 0;
        m_iPrevDBHL = 0;
        m_iNextDbHL = 0;
        m_iStartDBHL = 0;

        m_iCurHzId = 0;
        m_iCurHz = TConst.HZ_ORDER[m_iCurHzId];

        return 1;
    }

    public void print() {
        Log.v(m_TAG, "print" );

        if( m_alPttUnit.size() <= 0 )
            return;

        for(PttUnit unitOne : m_alPttUnit) {
            Log.v("PttScore print", unitOne.toString() );
        }
    }

    public int checkHzThreshold() {
        Log.v(m_TAG,
                String.format("checkHzThreshold Start Hz:%d, Thrd:%d", m_iCurHz, m_iCurDBHL ));
        if( isEndPttTest() ) {
            return 0;
        }

        if( isEndHzTest() ) {
            Log.v(m_TAG,
                    String.format("checkHzThreshold Save Hz:%d, Thrd:%d", m_iCurHz, m_iCurDBHL ));
            PttThreshold thresoldOne = new PttThreshold(m_iCurHz, m_iCurDBHL);
            m_alThreshold.add(thresoldOne);
            return 1;
        }
        return 0;
    }

    public int checkHzTestChange(){

        if( isEndHzTest() == false) {
            return 0;
        }
        m_iCurHzId ++;

        if( isEndPttTest() ) {
            return 0;
        }

        m_iOutOfBoundary = 0;
        m_iTurnUp = 0;
        m_iTurnDown = 1;

        m_iCurDBHL = m_iStartDBHL;
        m_iPrevDBHL = 0;
        m_iNextDbHL = m_iStartDBHL;
        m_iCurHz = TConst.HZ_ORDER[m_iCurHzId];

//        Log.v(m_TAG, String.format("changeNextHz Hz:%d, DBHL Prev:%d, Cur:%d, Next:%d, Up:%d, Down:%d, Out:%d",
//                m_iCurHz, m_iPrevDBHL, m_iCurDBHL, m_iNextDbHL, m_iTurnUp, m_iTurnDown, m_iOutOfBoundary));

        return 1;
    }

    public boolean isEndPttTest() {
        if(m_iCurHzId > TConst.HZ_ORDER.length - 1) {
            m_iCurHzId = TConst.HZ_ORDER.length;
            return true;
        } else {
            return false;
        }
    }

    public boolean isEndHzTest() {
//        Log.v(m_TAG, String.format("isEndHzTest TurnDown : %d, OutOfBound : %d",
//                m_iTurnDown, m_iOutOfBoundary ));
        if(m_iTurnDown > 1
                || m_iOutOfBoundary > 0){

            return true;
        } else {
            return false;
        }
    }


    public int addAnswer(PttUnit puAdd){
        Log.v(m_TAG, String.format("addAnswer Start - Hz:%d, DBHL:%d, Hear:%d",
                puAdd.get_Hz(), puAdd.get_DBHL(), puAdd.get_IsHearing() ));
//        Log.v(m_TAG, String.format("addAnswer Start - TurnUp : %d, Down:%d, OutOfBound : %d",
//                m_iTurnUp, m_iTurnDown, m_iOutOfBoundary ));
        if( isEndPttTest() ) {
            return 0;
        }

        m_alPttUnit.add(puAdd);
        m_iPrevDBHL = m_iCurDBHL;
        m_iCurDBHL = puAdd.get_DBHL();
        m_iCurHz = puAdd.get_Hz();

        checkAnswerAndUpDownDbhl(puAdd.get_IsHearing());

//        Log.v(m_TAG, String.format("addAnswer Finish - TurnUp : %d, Down:%d, OutOfBound : %d",
//                m_iTurnUp, m_iTurnDown, m_iOutOfBoundary ));
        return 1;
    }

    private void checkAnswerAndUpDownDbhl(int iIsHearing) {
        if(TConst.HEARING == iIsHearing){
            downDbhlAndCheckTurnDown();
        } else if(TConst.NO_HEARING == iIsHearing) {
            upDbhlAndCheckTurnUp();
        }
    }


    private int downDbhlAndCheckTurnDown() {

        m_iNextDbHL = m_iCurDBHL - DELTA_DB_DOWN;
        if(m_iNextDbHL < MIN_DBHL){
            m_iNextDbHL = MIN_DBHL;
        }

        checkMinBoundary();
        checkTurnDown();
        return 1;
    }

    private void checkMinBoundary() {
        if( MIN_DBHL >= m_iCurDBHL && MIN_DBHL >= m_iNextDbHL ){
            m_iOutOfBoundary = 1;
        }
    }

    private void checkTurnDown() {
        if(  m_iPrevDBHL < m_iCurDBHL && m_iCurDBHL > m_iNextDbHL ) {
            m_iTurnDown++;
        }
    }

    private int upDbhlAndCheckTurnUp() {
        m_iNextDbHL = m_iCurDBHL + DELTA_DB_UP;
        if(m_iNextDbHL > MAX_DBHL){
            m_iNextDbHL = MAX_DBHL;
        }

        checkMaxBoundary();
        checkTurnUp();

        return 1;
    }

    private void checkMaxBoundary() {
        if(m_iCurDBHL >= MAX_DBHL && m_iNextDbHL >= MAX_DBHL ){
            m_iOutOfBoundary = 1;
        }
    }

    private void checkTurnUp() {
        if(m_iNextDbHL > m_iCurDBHL && m_iCurDBHL < m_iPrevDBHL) {
            m_iTurnUp++;
        }
    }

}
