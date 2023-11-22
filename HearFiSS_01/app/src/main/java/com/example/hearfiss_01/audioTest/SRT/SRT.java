package com.example.hearfiss_01.audioTest.SRT;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.Nullable;

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
import java.util.Date;

public class SRT {

    SQLiteControl m_SqlCon = null;

    SQLiteHelper m_SqlHlp = null;

    AudioManager m_AudioMan= null;

    MediaPlayer m_Player = null;

    String m_PkgName = "";

    RandomTrack m_randTrack = null;

    String m_Type = "";

    int m_iCount = -1;

    AmTrack m_atCur = null;

    HrTestGroup m_TestGroup = null;

    HrTestSet m_TestSet = null;

    int m_iThreshold = 0;

    int m_iCurDbHL = 0;

    SrtScore m_SrtScore = null;
    String m_TAG = "SRT";
    Context     m_Context = null;
//    String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음"};



    public SRT(@Nullable Context context) {
        this.m_Context = context;

        m_PkgName = m_Context.getPackageName();

        m_randTrack = new RandomTrack(m_Context);

        m_SqlHlp = new SQLiteHelper(m_Context,  TConst.DB_FILE, null, TConst.DB_VER);

        m_SqlCon = new SQLiteControl(m_SqlHlp);

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
        m_TestSet = new HrTestSet();
        setM_Type("bwl_a1");

        m_iCurDbHL = getDBHLFromVolume();
/*
        Date dtNow = new Date();
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdFormatter.format(dtNow);

        HrTestGroup tgIns = new HrTestGroup(0, strDate, GlobalVar.g_MenuType," ", GlobalVar.g_AccLogin.getAcc_id());
        if(GlobalVar.g_MenuSide.equals("RIGHT")) {
            m_SqlCon.insertTestGroup(tgIns);
            m_TestGroup = m_SqlCon.selectTestGroup(tgIns);
            m_TestGroup = GlobalVar.g_TestGroup;
        }


        HrTestSet tsInsert = new HrTestSet(0, m_TestGroup.getTg_id(), GlobalVar.g_MenuSide, "", "");
        m_SqlCon.insertTestSet(tsInsert);

        m_TestSet = m_SqlCon.selectTestSet(tsInsert);
*/
        m_SrtScore.clear();
    }

    public void endTest(){
        Log.v("SRT", "endTest");
        m_iThreshold = m_SrtScore.getM_iPassTrsd();

        String strResult
                = String.format("어음역치 = %d dbHL", m_iThreshold);
        String strComment
                = String.format("Count: %d, UnitSize:%d, Cur:%d, Prev:%d", m_iCount, m_SrtScore.getM_alSrtUnit().size(), m_SrtScore.getM_iCurDb(), m_SrtScore.getM_iPrevDb());

        m_TestSet.setTs_Result(strResult);
        m_TestSet.setTs_Comment(strComment);
        m_SqlCon.updateTestSetFromTsid(m_TestSet);

        if(GlobalVar.g_MenuSide.equals("RIGHT")) {
            GlobalVar.g_RightResult = Integer.toString(m_iThreshold) + "dB HL";
        } else {
            GlobalVar.g_leftResult = Integer.toString(m_iThreshold) + "dB HL";
        }

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

        HrTestUnit tuInsert = new HrTestUnit(m_TestSet.getTs_id(), Question, Answer, IsCorrect);
        m_SqlCon.insertTestUnit(tuInsert);

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

    void printTestUnits() {
        Log.v("SRT printTestUnits", "num - question - answer - is_correct " );
        ArrayList<HrTestUnit> alTestUnit = m_SqlCon.selectTestUnitFromTsId(m_TestSet.getTs_id());

        for(HrTestUnit hrOne : alTestUnit) {
            Log.v("SRT printTestUnits", hrOne.toString() );
        }
    }

    public int adjustVolume(int delta){
        if(m_Player == null || !m_Player.isPlaying()){
            return 0;
        }
        int CurVol = m_AudioMan.getStreamVolume(AudioManager.STREAM_MUSIC);
        int MaxVol = m_AudioMan.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int MinVol = m_AudioMan.getStreamMinVolume(AudioManager.STREAM_MUSIC);
        int NewVol = CurVol + delta;

        if(NewVol > MaxVol){
            NewVol = MaxVol;
        }
        if(NewVol <= MinVol){
            NewVol = MinVol;
        }

        //Log.v(aName,"current V : " + nVolume);
        m_AudioMan.setStreamVolume(AudioManager.STREAM_MUSIC, NewVol,0);
        return NewVol;
    }

    private int setVolumeFromDBHL(int iDBHL) {
        Log.v("SRT setVolume", String.format("Vol = %d ", iDBHL) );
        if(m_Player == null){
            return 0;
        }

        if(GlobalVar.g_MenuSide.equals("RIGHT")){
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

}

