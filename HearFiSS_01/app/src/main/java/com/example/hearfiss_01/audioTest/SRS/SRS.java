package com.example.hearfiss_01.audioTest.SRS;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.db.DTO.AmTrack;
import com.example.hearfiss_01.db.DTO.StWord;
import com.example.hearfiss_01.db.dao.RandomTrack;
import com.example.hearfiss_01.db.dao.SrsDAO;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;

import java.util.ArrayList;


public class SRS {
    //------------------------------------FIELD AND GLOBAL VARIABLE -----------------------------//
    String m_TAG = "SRS";
    Context m_context = null;
    String m_PkgName = "";

    int m_tsLimit = 0;
    int userVolume = 0;

    int m_iCount = -1;
    int m_iVolumeSide = 0;

    String m_Type = "";

    AmTrack m_atCur = null;

    MediaPlayer m_Player = null;

    RandomTrack m_randTrack = null;

    SentScore m_CurScore = null;


    public void setUserVolume(int userVolume){
        this.userVolume = userVolume;
    }

    public void setM_tsLimit(int m_tsLimit){
        this.m_tsLimit = m_tsLimit;
    }
    public int getCount(){
        return m_iCount;
    }


    public String getM_Type() {
        return m_Type;
    }
    //------------------------------------SETTER M_TYPE METHOD-----------------------------------//

    public void setM_Type(String m_Type) {
        this.m_Type = m_Type;
        m_randTrack.setM_Type(m_Type);
    }

    public SentScore getScore(){
        return m_CurScore;
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

        m_CurScore =  new SentScore();

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

    public int playCurrent() {
        Log.v(m_TAG, String.format("playCurrent m_iCount = %d, Limit %d ", m_iCount, m_tsLimit));

        if(m_atCur == null) {
            Log.v(m_TAG, String.format("playCurrent null atCur m_iCount = %d, Limit %d ", m_iCount, m_tsLimit));
            return playNext();
        }
        else {
            Log.v(m_TAG, String.format("playCurrent m_iCount = %d, Limit %d ", m_iCount, m_tsLimit));
            return playTrack();
        }
    }
    //------------------------------------RANDOM TRACK SHUFFLE -----------------------------------//
    public int playNext() {
        Log.v(m_TAG, "playNext : ");
        m_atCur = getNext();
        return playTrack();
    }

    public AmTrack getNext(){
        Log.v(m_TAG, "getNext : m_icount Pre = " + m_iCount);
        m_randTrack.printTrackContent();
        m_iCount ++;
        return m_randTrack.getNext();
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
        Log.v(m_TAG, "current sentence : " + m_atCur.getAt_content());
        return m_iCount;
    }
    private void setVolumeSideCheck() {
        Log.v(m_TAG, String.format("setVolumeSideChecj - side : %d", m_iVolumeSide));
        if (m_iVolumeSide == TConst.T_RIGHT){
            m_Player.setVolume(0.0f, 1.0f);
        }else if (m_iVolumeSide == TConst.T_LEFT){
            m_Player.setVolume(1.0f, 0.0f);
        }
    }

    public boolean isEnd() {
        Log.v(m_TAG, String.format("isEnd : m_iCount = %d, m_tsLimit = %d", m_iCount, m_tsLimit));
        if(m_iCount >=  m_tsLimit){
            Log.v(m_TAG, String.format("isEnd : m_iCount = %d, m_tsLimit = %d return true", m_iCount, m_tsLimit));
            return true;
        }else{
            Log.v(m_TAG, String.format("isEnd : m_iCount = %d, m_tsLimit = %d return false", m_iCount, m_tsLimit));
            return false;
        }
    }


    public int SaveAnswer(String strAnswer) {
        Log.v(m_TAG, "*********SaveAnswer***********");
        Log.v(m_TAG, "Question : " + m_atCur.getAt_content());
        Log.v(m_TAG, "Answer : " + strAnswer);

        if(m_atCur == null){
            return 0;
        } else {
            SrsDAO srsDAO = new SrsDAO(m_context);

            ArrayList<StWord> wordList = srsDAO.selectWordListFromId(m_atCur.getAt_id());

            SentUnit unitAdd = new SentUnit();

            for(int i=0; i<wordList.size(); i++){
                Log.v( m_TAG,
                        String.format("word : %s, idx : %d",
                        wordList.get(i).getSw_word(), wordList.get(i).getSw_idx()) );
                unitAdd.addWordNIdx(wordList.get(i).getSw_word(), wordList.get(i).getSw_idx());
            }

            String strTrimAnswer = strAnswer.trim();
            unitAdd.saveQnA(m_atCur.getAt_content(), strTrimAnswer);
            Log.v(m_TAG, "unitAdd : " + unitAdd.toString());

            srsDAO.releaseAndClose();
            m_CurScore.addSentUnit(unitAdd);
//            m_CurScore.printSentence();

        }

        return 0;

    }

    public int scoring(){
        Log.v(m_TAG, "****scoring****");
        m_CurScore.printSentence();
        m_CurScore.scoring();

        Log.v( m_TAG,
                String.format("scoring : word_score %d, q :%d, c:%d, sent_score %d, q :%d, c:%d,",
                        m_CurScore.get_iWordScore(), m_CurScore.get_iWordQuest(), m_CurScore.get_iWordCorrect(),
                        m_CurScore.get_iSentScore(), m_CurScore.get_iSentQuest(), m_CurScore.get_iSentCorrect()   ) );

//        assertEquals(8, scoreTemp.get_iWordCorrect());
//        assertEquals(10, scoreTemp.get_iWordQuest());
//        assertEquals(80, scoreTemp.get_iWordScore());
//
//        assertEquals(1, scoreTemp.get_iSentCorrect());
//        assertEquals(3, scoreTemp.get_iSentQuest());
//        assertEquals(33, scoreTemp.get_iSentScore());



//        // 단어 기준
//        int wResult = (int)(((float)(cWord) / (float)(tWord)*100));
//        int sResult = (int)(((float) (cSentence) / (float) (10)*100));
//        int iTestSide = 0;
//
//        Log.v(m_TAG, "단어 정답 개수 : " + cWord + "개");
//        Log.v(m_TAG, "단어 기준 : " + wResult + "%");
//
//        Log.v(m_TAG, "문장 정답 개수 : " + cSentence + "개");
//        Log.v(m_TAG, "문장 기준 : " + sResult + "%");
//        HrTestSet hrtestSet = insertSet(wResult,sResult, iTestSide);
//        if(hrtestSet != null){
//            insertUnit(unitList, hrtestSet);
//        }
//        if(GlobalVar.g_TestSide == TConst.T_RIGHT){
//            GlobalVar.g_RightResult = Integer.toString(sResult) +"%";
//        }else{
//            GlobalVar.g_leftResult = Integer.toString(sResult) +"%";
//        }
//        return sResult;

        return 0;
    }

/*
    public ArrayList<SentUnit> get_SentUnitList(){
        return m_CurScore.get_alSentence();
    }

 */

    public ArrayList<SentUnit> getScoreList(){

        return m_CurScore.get_alSentence();
    }



}



