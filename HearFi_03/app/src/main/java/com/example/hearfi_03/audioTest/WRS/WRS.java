package com.example.hearfi_03.audioTest.WRS;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.hearfi_03.db.DTO.AmTrack;
import com.example.hearfi_03.db.dao.RandomTrack;
import com.example.hearfi_03.global.GlobalVar;
import com.example.hearfi_03.global.TConst;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class WRS {
    String m_TAG = "WRS";
    MediaPlayer m_Player = null;
    Context m_Context = null;
    RandomTrack m_randTrack = null;
    String m_Type = "";
    String m_PackageName = "";
    int m_iCount = -1;
    AmTrack m_atCur = null;

    WordScore m_CurScore = null;

    int m_tsLimit = 10;

    int userVolume = 0;

    int             m_iVolumeSide = 0;

    public void setUserVolume(int userVolume) {
        this.userVolume = userVolume;
    }

    public ArrayList<WordUnit> get_WordUnitList() {
        return m_CurScore.getM_alWord();
    }

    public void setM_tsLimit(int m_tsLimit) {
        this.m_tsLimit = m_tsLimit;
    }
    //------------------------------------CONSTRUCTOR---------------------------------------------//
    public WRS(@Nullable Context context){
        this.m_Context = context;
        this.m_PackageName = m_Context.getPackageName();

        startTest();
    }

    public void startTest() {
        m_randTrack = new RandomTrack(m_Context);
        m_iCount = -1;
        m_iVolumeSide = GlobalVar.g_TestSide;

        m_CurScore = new WordScore();
    }

    public void endTest() {
        Log.v(m_TAG,
                String.format("endTest"));
        stopPlayer();

        m_randTrack.releaseAndClose();
    }

    public int stopPlayer(){
        Log.v(m_TAG, "stopPlayer");
        try {
            if (m_Player != null) {
                m_Player.stop();
                m_Player.release();
                m_Player = null;
            }
            return 1;
        } catch (Exception e) {
            Log.v(m_TAG, "stopPlayer Exception " + e);
            return 0;
        }
    }

    //------------------------------------GETTER M_TYPE METHOD------------------------------------//
    public String getM_Type(){return m_Type;}
    //------------------------------------SETTER M_TYPE METHOD------------------------------------//
    public void setM_Type(String m_Type){
        this.m_Type = m_Type;
        m_randTrack.setM_Type(m_Type);
    }
    //------------------------------------GETTER RANDOM BAG VALUE---------------------------------//
    public AmTrack getNext(){
        Log.v(m_TAG, "getNext : m_icount Pre = " + m_iCount);
        m_randTrack.printTrackContent();
        m_iCount ++;
        return m_randTrack.getNext();
    }
    //------------------------------------SAVE USER ANSWER ---------------------------------------//
    public int getCurrentProgress() {
        return (int) (((float) m_CurScore.getM_alWord().size() / (float) m_tsLimit) * 100);
    }

    public int SaveAnswer(String strAnswer) {
        if(m_atCur == null){
            return 0;
        } else {
            WordUnit unitAdd = new WordUnit(m_atCur.getAt_content(), strAnswer, -1);
            m_CurScore.addWordUnit(unitAdd);
            Log.v(m_TAG,
                    String.format("SaveAnswer : Side : %d , cnt : %d - Q: %s , A : %s C : %d",
                            GlobalVar.g_TestSide, m_CurScore.getM_alWord().size(),
                            unitAdd.get_Question(), unitAdd.get_Answer(), unitAdd.get_Correct()));
            return m_CurScore.getM_alWord().size();
        }

    }

    //------------------------------------GET CURRENT VALUE --------------------------------------//
    public int playCurrent() {
        Log.v("SRT", "playCurrent : ");

        if(m_atCur == null) {
            // 트랙의 정보가 없는 경우는 새로 배치를 해야한다.
            return playNext();
        }
        else {
            // 트랙의 정보가 있으면 플레이
            return playTrack();
        }
    }
    //------------------------------------RANDOM TRACK SHUFFLE -----------------------------------//
    public int playNext() {
        Log.v(m_TAG, "playNext : ");
        // AmTrack class -> getNext Method(트랙이 비었을 경우 새로 리스트 업)
        m_atCur = getNext();
        return playTrack();
    }
    //------------------------------------RANDOM TRACK ID PARSER & PLAY --------------------------//
    private int playTrack(){
        int idTrack = m_Context.getResources().getIdentifier(m_atCur.getAt_file_name(), "raw", m_PackageName);

        if (m_Player != null) {
            m_Player.stop();
            m_Player.release();
            m_Player = null;
        }

        m_Player = MediaPlayer.create(m_Context, idTrack);
        setVolumeSideCheck();
        m_Player.start();
        return m_iCount;

    }
    //------------------------------------TEST END CONFIRMATION METHOD----------------------------//
    public boolean isEnd(){
        if(m_iCount == (m_tsLimit-1)){
            return true;
        }else{
            return false;
        }
    }


    private void setVolumeSideCheck(){
        Log.v(m_TAG, String.format("setVolumeSideCheck - side:%d", m_iVolumeSide));
        if(m_iVolumeSide == TConst.T_RIGHT){
            m_Player.setVolume(0.0f, 1.0f); // Right Only

        } else if(m_iVolumeSide == TConst.T_LEFT) {
            m_Player.setVolume(1.0f, 0.0f); // Left Only
        }

    }


}
