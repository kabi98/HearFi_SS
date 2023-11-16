package com.example.hearfi_03.audioTest.PTT;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfi_03.global.GlobalVar;
import com.example.hearfi_03.global.TConst;

import java.util.ArrayList;
import java.util.Collections;

public class PTT {
    String m_TAG = "PTT";
    public static final boolean UNIT_TEST = false;
    Context     m_Context = null;
    int         m_iCurHz = 0;
    int         m_iCurDBHL = 0;
    int         m_iCurSide = 0;
    int         m_iCurProgress = -1;
    PttScore    m_CurScore = null;
    PureTonePlayer m_PTPlayer = null;


    public PttScore get_CurScore() {
        return m_CurScore;
    }

    public int get_iCurHz() {
        return m_iCurHz;
    }

    public int get_iCurDBHL() {
        return m_iCurDBHL;
    }

    public int get_iCurSide() {
        return m_iCurSide;
    }

    public int get_iCurProgress() {
        return m_iCurProgress;
    }

    public PTT(@Nullable Context context) {
        this.m_Context = context;
        startTest();
    }

    public void startTest() {
        Log.v(m_TAG, "startTest");
        m_iCurSide = GlobalVar.g_TestSide;
        m_iCurHz = TConst.HZ_ORDER[0];
        m_iCurProgress = 0;

        m_CurScore = new PttScore();

        if(m_iCurSide == TConst.T_RIGHT){
            m_iCurDBHL = GlobalVar.g_PttRightDBHL;
        } else if(m_iCurSide == TConst.T_LEFT){
            m_iCurDBHL = GlobalVar.g_PttLeftDBHL;
        }
        m_CurScore.set_iStartDBHL(m_iCurDBHL);

        if(UNIT_TEST==false){
            m_PTPlayer = new PureTonePlayer(m_Context);
            m_PTPlayer.setVolumeMax();
            m_PTPlayer.setVolumeSide(m_iCurSide);
            m_PTPlayer.setPhoneAndDevice(GlobalVar.g_PttStrPhone, GlobalVar.g_PttStrDevice);
        }
    }

    public void endTest() {
        Log.v(m_TAG, "endTest");
        if(UNIT_TEST==false) {
            m_PTPlayer.closeAll();
        }

        ArrayList<PttThreshold> CurThresholdList = m_CurScore.getThresholdList();
        Log.v(m_TAG, String.format("endTest Threshold size:%d", CurThresholdList.size()) );

        for(int i=0; i<CurThresholdList.size(); i++){
            Log.v(m_TAG, String.format("current Threshold i:%d, Hz:%d, dB:%d",
                    i, CurThresholdList.get(i).get_Hz(), CurThresholdList.get(i).get_DBHL()));
        }

    }

    public int playCurrent() {
        Log.v(m_TAG, String.format("playCurrent- Side:%d, Hz:%d, dB:%d",
                m_iCurSide, m_iCurHz, m_iCurDBHL));
        if(UNIT_TEST==false) {
            m_PTPlayer.playPureToneFromHzDBHL(m_iCurHz, m_iCurDBHL);
        }

        return 1;
    }

    public int playStop() {
        Log.v(m_TAG, String.format("playStop- Side:%d, Hz:%d, dB:%d",
                m_iCurSide, m_iCurHz, m_iCurDBHL));
        if(UNIT_TEST==false) {
            m_PTPlayer.stopPlayer();
        }

        return 1;
    }

    public boolean isEnd() {
        Log.v(m_TAG, "isEnd");
        if(m_CurScore.isEndPttTest()){
            return true;
        } else {
            return false;
        }
    }

    public int saveAnswer(int iHearing) {
        Log.v(m_TAG, String.format("saveAnswer Start Current Hz:%d, DBHL:%d, Hear:%d",
                m_iCurHz, m_iCurDBHL, iHearing));

        PttUnit unitAdd = new PttUnit(m_iCurHz, m_iCurDBHL, iHearing);
        m_CurScore.addAnswer(unitAdd);
        m_CurScore.checkHzThreshold();
        m_CurScore.checkHzTestChange();

        if(m_CurScore.isEndPttTest()){
            endTest();
        }

        m_iCurHz = m_CurScore.get_iCurHz();
        m_iCurProgress = m_CurScore.get_iCurHzId();
        m_iCurDBHL = m_CurScore.get_iNextDbHL();

        Log.v(m_TAG, String.format("saveAnswer Finish Progress %d, Hz:%d, dB:%d, isHear:%d, prev:%d, cur:%d, next:%d",
                m_iCurProgress, m_iCurHz, m_iCurDBHL, iHearing,
                m_CurScore.get_iPrevDbHL(), m_CurScore.get_iCurDbHL(),
                m_CurScore.get_iNextDbHL()));

        return 0;
    }

    public ArrayList<PttThreshold> getResultList(){
        return m_CurScore.getThresholdList();
    }

    public ArrayList<PttThreshold> getSortedResultList(){
        ArrayList<PttThreshold> alSortedResult = m_CurScore.getThresholdList();
        Collections.sort(alSortedResult);

        return alSortedResult;
    }

}
