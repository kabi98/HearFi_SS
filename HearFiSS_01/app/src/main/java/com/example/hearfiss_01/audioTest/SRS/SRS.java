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

    SrsScore m_CurScore = null;

    String m_Type = "";
    String m_PkgName = "";

    int userVolume = 0;

    int m_iCount = -1;

    int m_tsLimit = 10;

    int m_iVolumeSide = 0;
    HrTestSet testSet = null;
    ArrayList<HrTestUnit> unitList = new ArrayList<HrTestUnit>();
    HrTestSet hrTestSet = null;
    AmTrack m_atCur = null;
    SQLiteControl sqlcon;
    SQLiteHelper helper;
    int sum;
    int result = 0;
    int tWord;
    int cSentence;
    int cWord;
    ArrayList<String> u_A;
    ArrayList<String> c_Q;
    HrTestGroup m_testGroup = null;
    SrsDAO srsDAO ;


    public void setM_tsLimit(int m_tsLimit){
        this.m_tsLimit = m_tsLimit;
    }
    public void setUserVolume(int userVolume){
        this.userVolume = userVolume;
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


    //------------------------------------CONSTRUCTOR--------------------------------------------//
    public SRS(@Nullable Context context){
        this.m_context = context;
        this.m_PkgName = m_context.getPackageName();
        /*
        helper = new SQLiteHelper(m_context,  TConst.DB_FILE, null, TConst.DB_VER);
        //sqlcon = new SQLiteControl(helper );
        m_randTrack = new RandomTrack(context);
        srsDAO = new SrsDAO(helper);
         */

        startTest();
    }

    public void startTest() {
        m_randTrack = new RandomTrack(m_context);
        m_iCount = -1;
        m_iVolumeSide = GlobalVar.g_TestSide;

        m_CurScore = new SrsScore();

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
        return (int)(((float)m_CurScore.getM_alWord().size() / (float) m_tsLimit)*100);
    }

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

    public boolean isEnd(){
        Log.v(m_TAG, "isEnd : " + m_iCount);
        if(m_iCount == 9){
            //unitList.clear();
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

    public ArrayList<String> sameSize(String strAnswer){
        ArrayList<StWord> alword = sqlcon.selectWordFromId(m_atCur.getAt_id());
        String question = m_atCur.getAt_content();
        // AmTrack - question 배열
        c_Q = split_Answer(question);
        String answer = strAnswer.trim();
        int iLen = -1;
        while(iLen != answer.length()){
            iLen = answer.length();
            answer = answer.replace("  ", " ");
        }
        // 사용자 - Answer 배열
        u_A = split_Answer(answer);
        Log.v(m_TAG,"createList : " + c_Q.size());
        Log.v(m_TAG,"createList : " + u_A.size());
        if(u_A.size() < c_Q.size()){
            int gap = c_Q.size()-u_A.size();
            for(int i=0; i<gap; i++){
                u_A.add("");
            }
            Log.v(m_TAG,"createList : " + u_A.size());
            Log.v(m_TAG,"createList : " + u_A.toString());
        }
        return u_A;
    }

    public int SaveAnswer(String strAnswer) {
        /*
        if (m_atCur == null) {
            return 0;
        } else {
            String question = m_atCur.getAt_content();
            String answer = strAnswer.trim();

            // 음원에 대한 at_id로 포함된 단어 검색
            ArrayList<StWord> alword = srsDAO.selectWordFromId(m_atCur.getAt_id());
            u_A = sameSize(strAnswer);

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
                unit.setTu_dBHL(GlobalVar.g_srsUserVolume);
                unit.setTu_atId(m_atCur.getAt_id());
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
            Log.v("SaveAnswer", "size : " + unitList.size());
            return (m_iCount+1);
        }

         */
        // 임시 0 값 입력
        return 0;
    }
    /*

    //------------------------------------SCORING METHOD-----------------------------------------//
    public int scoring(){
        // 단어 기준
        int wResult = (int)(((float)(cWord) / (float)(tWord)*100));
        int sResult = (int)(((float) (cSentence) / (float) (10)*100));

        Log.v("SaveAnswer", "단어 정답 개수 : " + cWord + "개");
        Log.v("SaveAnswer", "단어 기준 : " + wResult + "%");

        Log.v("SaveAnswer", "문장 정답 개수 : " + cSentence + "개");
        Log.v("SaveAnswer", "문장 기준 : " + sResult + "%");
        HrTestSet hrtestSet = insertSet(wResult,sResult);
        if(hrtestSet != null){
            insertUnit(unitList, hrtestSet);
        }
        if(GlobalVar.g_MenuSide.equals("RIGHT")){
            GlobalVar.g_RightResult = Integer.toString(sResult) +"%";
        }else{
            GlobalVar.g_leftResult = Integer.toString(sResult) +"%";
        }
        return sResult;
    }
    //------------------------------------USER ANSWER SPLIT METHOD-------------------------------//
   */
    public ArrayList<String> split_Answer(String answer) {
        String[] u_temp = answer.split(" ");
        ArrayList<String> u_A = new ArrayList<>(Arrays.asList(u_temp));
        // 사용자 응답 (스페이스 공백 기준 나누기)
        for (int i = 0; i < u_A.size(); i++) {
            if (!u_A.get(i).isEmpty()) {
                Log.v(m_TAG, "array shape[" + i + "] : " + u_A.get(i));
            }
        }
        return u_A;
    }
    /*
    public HrTestGroup dataGroupInsert() {
        Date dtNow = new Date();
        // ex) 2023-08-10 16:22:22 포멧팅
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 문자형으로 날짜 포맷팅
        String strDate = sdFormatter.format(dtNow);
        HrTestGroup hrTestGroup = new HrTestGroup(0, strDate, GlobalVar.g_MenuType, GlobalVar.g_AccLogin.getAcc_id());
        sqlcon.insertTestGroup(hrTestGroup);
        m_testGroup = sqlcon.selectTestGroup(hrTestGroup);
        GlobalVar.g_TestGroup = m_testGroup;
        Log.v(aName, "insertData : " + m_testGroup.toString());
        return m_testGroup;
    }
    public HrTestSet insertSet(int wResult, int sResult){

        if(GlobalVar.g_MenuSide.equals("RIGHT")){
            m_testGroup = dataGroupInsert();
        }
        hrTestSet = new HrTestSet();

        // HrTestSet 수정 필요
        //hrTestSet.setTs_Date(strDate); // 테스트 날짜
        //hrTestSet.setTs_type(GlobalVar.g_MenuType); //테스트 타입

        hrTestSet.setTg_id(GlobalVar.g_TestGroup.getTg_id());
        hrTestSet.setTs_side(GlobalVar.g_MenuSide); // 테스트 방향
        hrTestSet.setTs_Result("단어 기준 : " +Integer.toString(wResult) +"%"); // 단어 기준 점수
        hrTestSet.setTs_Comment("문장 기준 : " +Integer.toString(sResult) +"%"); // 문장 기준 점수
        sqlcon.insertTestSet(hrTestSet);
        HrTestSet m_testset = sqlcon.selectTestSet(hrTestSet);
        Log.v(aName,"insertData : " + m_testset.toString());
        return m_testset;
    }
    public void insertUnit(ArrayList<HrTestUnit> unitList, HrTestSet srsTestSet){

        int row = 0;
        for(int i=0; i<unitList.size(); i++){
            unitList.get(i).setTs_id(srsTestSet.getTs_id());
            int insert = sqlcon.insertTestUnit(unitList.get(i));
            row += insert;

            Log.v(aName, "insertData" + unitList.get(i).toString());
        }
        Log.v("insertUnit", "cnt : " + row);
    }



 */
}



