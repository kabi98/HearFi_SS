package com.example.hearfiss_01.audioTest.SRT;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.db.DTO.AmTrack;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.HrTestUnit;
import com.example.hearfiss_01.db.dao.RandomTrack;
import com.example.hearfiss_01.db.sql.SQLiteControl;
import com.example.hearfiss_01.db.sql.SQLiteHelper;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class SRT {

    AudioManager m_AudioMan= null;

    MediaPlayer m_Player = null;

    String m_PkgName = "";

    RandomTrack m_randTrack = null;

    String m_Type = "";

    int m_iCount = -1;

    AmTrack m_atCur = null;


    int userVolume = 0;

    int m_iThreshold = 0;

    int m_iCurDbHL = 0;

    SrtScore m_SrtScore = null;
    String m_TAG = "SRT";
    Context     m_Context = null;


    public void setUserVolume(int userVolume) {
        this.userVolume = userVolume;
    }


    public SRT(@Nullable Context context) {
        this.m_Context = context;

        m_PkgName = m_Context.getPackageName();

        m_randTrack = new RandomTrack(m_Context);

        m_SrtScore = new SrtScore();

        m_iCurDbHL = 0;

        m_AudioMan = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public String getM_Type() {
        return m_Type;
    }

    public void setM_Type(String m_Type) {
        Log.v("SRT", "setM_Type : " + m_Type );
        this.m_Type = m_Type;
        m_randTrack.setM_Type(m_Type);
    }

    public AmTrack getNext(){
        Log.v("SRT", "getNext : m_iCount Pre = " + m_iCount );
        m_randTrack.printTrackContent();
        m_iCount ++;
        return m_randTrack.getNext();
    }

    public void startTest(){
        Log.v("SRT", "startTest");
        setM_Type("bwl_a1");

        m_iCurDbHL = getDBHLFromVolume();
        m_SrtScore.clear();
    }

    public void endTest(){
        Log.v(m_TAG, "**** endTest ******");

        m_iThreshold = m_SrtScore.getM_iPassTrsd();

        String strResult
                = String.format("어음역치 = %d dbHL", m_iThreshold);
        String strComment
                = String.format("Count: %d, UnitSize:%d, Cur:%d, Prev:%d", m_iCount, m_SrtScore.getM_alSrtUnit().size(), m_SrtScore.getM_iCurDb(), m_SrtScore.getM_iPrevDb());
/*
        Log.v(m_TAG,
                String.format(" SRT RESULT SIDE *** %d ",
                        GlobalVar.g_TestSide ) );

        if(GlobalVar.g_TestSide == TConst.T_RIGHT) {

            GlobalVar.g_RightResult = Integer.toString(m_iThreshold) + "dB HL";
            GlobalVar.g_alSrtRight = m_SrtScore.getM_alSrtUnit();

            for(int i=0; i< GlobalVar.g_alSrtRight.size(); i++){
                Log.v(m_TAG,
                        String.format(" SRT RESULT Right : %d, %s ",
                                i , GlobalVar.g_alSrtRight.get(i).toString() ) );
            }


        } else if(GlobalVar.g_TestSide == TConst.T_LEFT) {
            GlobalVar.g_leftResult = Integer.toString(m_iThreshold) + "dB HL";
            GlobalVar.g_alSrtLeft = m_SrtScore.getM_alSrtUnit();

            for(int i=0; i< GlobalVar.g_alSrtLeft.size(); i++){
                Log.v(m_TAG,
                        String.format(" SRT RESULT Left : %d, %s ",
                                i , GlobalVar.g_alSrtLeft.get(i).toString() ) );
            }

        }
*/
    }

    public boolean isEnd(){
        return m_SrtScore.getM_isEnd();
    }

    public int saveAnswer(String strAnswer) {
        if(m_atCur == null)
            return 0;

        String Question = m_atCur.getAt_content();
        String Answer = strAnswer.trim();
        int    dBHL = m_iCurDbHL;

        SrtUnit suTemp = new SrtUnit(Question, Answer, -1, dBHL, 0);
        m_SrtScore.calcNextDbNSaveAnswer(suTemp);

        int IsCorrect = suTemp.get_Correct();
        m_iCurDbHL = suTemp.get_NextDb();

        Log.v("SRT SaveAnswer",
                String.format("cnt:%d, dB:%d, Q:%s, A:%s, C:%d Next:%d, Pass:%d, End:%b",
                        m_iCount, dBHL, Question, Answer, IsCorrect,
                        m_iCurDbHL, m_SrtScore.getM_iPassTrsd(), isEnd()) );

        if(isEnd()){
            endTest();
        }

        return 1;
    }

    public int playCurrent() {
        Log.v("SRT", "playCurrent : ");
        if(m_atCur == null) {
            return playNext();
        }
        else {
            return playTrack();
        }
    }

    private int playTrack() {
        Log.v("SRT", "play sound");
        int idTrack = m_Context.getResources().getIdentifier(m_atCur.getAt_file_name(), "raw", m_PkgName);

        if (m_Player != null) {
            m_Player.stop();
            m_Player.release();
        }
        m_Player = MediaPlayer.create(m_Context, idTrack);
        m_Player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        setVolumeFromDBHL(m_iCurDbHL);
        m_Player.start();
        return m_iCount;
    }

    private int setVolumeFromDBHL(int iDBHL) {
        Log.v("SRT setVolume", String.format("Vol = %d ", iDBHL) );
        if(m_Player == null){
            return 0;
        }

        if(GlobalVar.g_TestSide == TConst.T_RIGHT){
            Log.v("SRT test side", "side : " + GlobalVar.g_TestSide);
            m_Player.setVolume(0.0f, 1.0f); // Right Only
        } else {
            m_Player.setVolume(1.0f, 0.0f); // Left Only
        }

        int CurVol = m_AudioMan.getStreamVolume(AudioManager.STREAM_MUSIC);
        int MaxVol = m_AudioMan.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int MinVol = m_AudioMan.getStreamMinVolume(AudioManager.STREAM_MUSIC);
        int CalcVol = (int)(iDBHL * ((float)(MaxVol - MinVol) / 100.));

        Log.v("SRT setVolumeFromDBHL", String.format("Set = %d , Cur : %d, Max : %d, Min : %d, Calc : %d ",
                iDBHL, CurVol, MaxVol, MinVol, CalcVol) );

        if(CalcVol > MaxVol){
            CalcVol = MaxVol;
        }
        if(CalcVol <= MinVol){
            CalcVol = MinVol;
        }

        m_AudioMan.setStreamVolume(AudioManager.STREAM_MUSIC, CalcVol,0);
        return CalcVol;
    }

    public int playNext() {
        Log.v("SRT", "playNext : ");
        m_atCur = getNext();
        return playTrack();
    }


    private int getDBHLFromVolume() {
        int CurVol = m_AudioMan.getStreamVolume(AudioManager.STREAM_MUSIC);
        int MaxVol = m_AudioMan.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int MinVol = m_AudioMan.getStreamMinVolume(AudioManager.STREAM_MUSIC);
        int CalcDBHL = (int)(CurVol * ( 100. / (float)(MaxVol - MinVol) ));

        CalcDBHL = (CalcDBHL / 5) * 5;

        Log.v("SRT setVolumeFromDBHL", String.format("Cur : %d, Max : %d, Min : %d, Calc : %d ",
                CurVol, MaxVol, MinVol, CalcDBHL) );

        return CalcDBHL;
    }

    public ArrayList<SrtUnit> getScoreList(){
        return m_SrtScore.getM_alSrtUnit();
    }
}

