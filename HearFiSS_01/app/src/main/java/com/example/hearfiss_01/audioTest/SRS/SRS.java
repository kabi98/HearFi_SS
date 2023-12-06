package com.example.hearfiss_01.audioTest.SRS;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.db.dao.SrsDAO;
import com.example.hearfiss_01.db.sql.SQLiteControl;
import com.example.hearfiss_01.db.dao.SQLiteHelper;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestUnit;
import com.example.hearfiss_01.db.DTO.StWord;
import com.example.hearfiss_01.db.DTO.AmTrack;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.db.dao.RandomTrack;
import com.example.hearfiss_01.global.TConst;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SRS {
    //------------------------------------FIELD AND GLOBAL VARIABLE -----------------------------//
    String m_TAG = "SRS";
    MediaPlayer m_Player = null;
    Context m_context = null;
    RandomTrack m_randTrack = null;
    String m_Type = "";
    String m_PkgName = "";
    int m_iCount = -1;
    AmTrack m_atCur = null;
    SrsScore m_CurScore = null;
    int m_tsLimit = 10;
    int userVolume = 0;
    int m_iVolumeSide = 0;

    int tWord;

    int cWord;

    int cSentence;

    ArrayList<String> u_A;
    ArrayList<String> c_Q;
    HrTestGroup m_testGroup = null;

    ArrayList<HrTestUnit> unitList = new ArrayList<HrTestUnit>();
    SrsDAO srsDAO ;

    public void setUserVolume(int userVolume){
        this.userVolume = userVolume;
    }

    public void setM_tsLimit(int m_tsLimit){
        this.m_tsLimit = m_tsLimit;
    }
    public SRS(@Nullable Context context){
        this.m_context = context;
        this.m_PkgName = m_context.getPackageName();
        startTest();
    }
    public void startTest() {
        m_randTrack = new RandomTrack(m_context);
        m_iCount = -1;
        m_iVolumeSide = GlobalVar.g_TestSide;

        m_CurScore = new SrsScore();

    }

    public void endTest(){
        Log.v(m_TAG, String.format("endTest"));
        stopPlayer();

        m_randTrack.releaseAndClose();
    }

    public int stopPlayer(){
        Log.v(m_TAG, "stopPlayer");
        try {
            if (m_Player != null){
                m_Player.stop();
                m_Player.release();
                m_Player = null;
            }
            return 1;
        }catch (Exception e){
            Log.v(m_TAG, "stopPlayer Exception " + e);
            return 0;
        }
    }
    //------------------------------------GETTER M_TYPE METHOD-----------------------------------//
    public String getM_Type() {
        return m_Type;
    }
    //------------------------------------SETTER M_TYPE METHOD-----------------------------------//

    public void setM_Type(String m_Type) {
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
    //------------------------------------GET CURRENT VALUE --------------------------------------//

    public int getCurrentProgress(){
        Log.v(m_TAG, "getCurrentProgress");
      //  return (int)(((float)m_CurScore.getM_alWord().size() / (float) m_tsLimit)*100);
        return 0;
    }

    public int SaveAnswer(String strAnswer) {
        Log.v(m_TAG, "SaveAnswer");
        if (m_atCur == null) {
            Log.v(m_TAG, "SaveAnswer - m_atCur is null");
            return 0;
        } else {
            // 1. Stword -> SrsUnit으로 변경
            SrsUnit unitAdd = new SrsUnit(m_atCur.getAt_content(), strAnswer, -1);
            Log.v(m_TAG, "SaveAnswer : " + m_atCur.getAt_content() + ", Answer : "+ strAnswer);

            m_CurScore.addSrsUnit(unitAdd);
            Log.v(m_TAG, "SaveAnswer - Srsunit added ");

            return m_CurScore.getM_alSrs().size();
        }

    }
            /*
            String question = m_atCur.getAt_content();
            String answer = strAnswer.trim();


            // 음원에 대한 at_id로 포함된 단어 검색
            ArrayList<StWord> alword = srsDAO.selectWordFromId(m_atCur.getAt_id());
            u_A = m_CurScore.sameSize(strAnswer);

            // TODO : 23.08.18  =  검사 알고리즘 정리
            // 단어 갯수 확인 변수
            tWord += alword.size();
            Log.v(m_TAG ,"word count : " +tWord);
            int temp = 0;
            int sWcnt= alword.size();
            for (int i=0; i<alword.size(); i++){
                Log.v(m_TAG,"sentence include Word : " + sWcnt);
                int idx = alword.get(i).getSw_idx();
                Log.v(m_TAG,"sentence include Word : " + sWcnt);
                HrTestUnit unit = new HrTestUnit();
               // unit.setTu_dBHL(GlobalVar.g_srsUserVolume);
               // unit.setTu_atId(m_atCur.getAt_id());
                unit.setTu_Question(alword.get(i).getSw_word());
                Log.v("SaveAnswer", "u_A size : " + u_A.size());
                if(u_A.get(idx).contains(alword.get(i).getSw_word())){
                    // 사용자 입력 배열에 인덱스 값이 출력한 단어 인덱스와 같을 때
                    Log.v("포함된 단어 : " , alword.get(i).getSw_word() +"|"+ sWcnt);
                    unit.setTu_Answer(u_A.get(idx));
                    unit.setTu_IsCorrect(1);
                    sWcnt -= 1;
                    cWord +=1;
                    Log.v(m_TAG,"남은 단어 갯수  : " + sWcnt);
                    Log.v(m_TAG,"맞은 단어 갯수  : " + cWord);
                }else{
                    Log.v("미포함 단어 : " , alword.get(i).getSw_word() +"|"+ sWcnt);
                    unit.setTu_Answer(u_A.get(i));
                    unit.setTu_IsCorrect(0);
                }

                if(sWcnt==0) {
                    cSentence += 1;
                    Log.v("SaveAnswer", "맞은 문장 : " + cSentence);
                }
                unitList.add(unit);
                Log.v("unit check", "value : " + unit.toString());
            }

      */


    public int playCurrent() {
        Log.v("SRT", "playCurrent : ");

        if(m_atCur == null) {
            return playNext();
        }
        else {
            return playTrack();
        }
    }
    //------------------------------------RANDOM TRACK SHUFFLE -----------------------------------//
    public int playNext() {
        Log.v(m_TAG, "playNext : ");
        m_atCur = getNext();
        return playTrack();
    }
    //------------------------------------RANDOM TRACK ID PARSER & PLAY -------------------------//
    private int playTrack(){
        int idTrack = m_context.getResources().getIdentifier(m_atCur.getAt_file_name(), "raw",m_PkgName);
        if (m_Player != null) {
            m_Player.stop();
            m_Player.release();
            m_Player = null;
        }
        m_Player = MediaPlayer.create(m_context, idTrack);
        setVolumeSideCheck();
        m_Player.start();
        return m_iCount;
    }


    public boolean isEnd(){
        Log.v(m_TAG, "isEnd : " + m_iCount);
        if(m_iCount == 9){
            return true;
        }else{
            return false;
        }
    }

    private void setVolumeSideCheck() {
        Log.v(m_TAG, String.format("setVolumeSideChecj - side : %d", m_iVolumeSide));
        if (m_iVolumeSide == TConst.T_RIGHT){
            m_Player.setVolume(0.0f, 1.0f);
        }else if (m_iVolumeSide == TConst.T_LEFT){
            m_Player.setVolume(1.0f, 0.0f);
        }
    }

}



