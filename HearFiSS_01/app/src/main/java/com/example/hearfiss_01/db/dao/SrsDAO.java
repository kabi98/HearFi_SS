package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.audioTest.SRS.SentScore;
import com.example.hearfiss_01.audioTest.SRS.SentUnit;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.AmTrack;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.StWord;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SrsDAO {
    String m_TAG = "SrsDAO";
    SQLiteDatabase m_database;
    SQLiteHelper m_helper;

    Context m_Context;
    String m_strTestType;

    String m_strGroupResult;

    Account m_Account;

  //  ArrayList <SentUnit> m_alLeft;

    //ArrayList <SentUnit> m_alRight;



    HrTestGroup m_TestGroup;

    HrTestSet m_TestSetLeft;

    HrTestSet m_TestSetRight;

    SentScore m_SentScoreLeft;

    SentScore m_SentScoreRight;
/*
    public ArrayList<SentUnit> getRightUnitList(){
        return m_alRight;
    }

    public ArrayList<SentUnit> getLeftUnitList(){
        return m_alLeft;
    }


 */

    public void setResultList(SentScore scoreLeft, SentScore scoreRight) {
        this.m_SentScoreLeft = scoreLeft;
        this.m_SentScoreRight = scoreRight;
    }

    public HrTestGroup getTestGroup(){
        return m_TestGroup;

    }

    public Account getAccount(){
        return m_Account;
    }


    public SrsDAO(@Nullable Context _context) {
        m_Context = _context;
        m_helper = new SQLiteHelper(m_Context, TConst.DB_FILE, null, TConst.DB_VER);

        m_Account = new Account();

        //m_alLeft = new ArrayList<>();

        //m_alRight = new ArrayList<>();

        m_SentScoreLeft = new SentScore();

        m_SentScoreRight = new SentScore();

        m_strTestType = TConst.STR_SRS_TYPE;

    }

    public void releaseAndClose(){
        Log.v(m_TAG,String.format("releaseAndClose"));
        try {
            m_database.close();
            m_helper.close();
        }catch (Exception e){
            Log.v(m_TAG, "releaseAndClose Exception " + e);
        }
    }

    public ArrayList<AmTrack> selectTrackFromType(String _strType) {
        Log.v(m_TAG,
                String.format("selectTrackFromType Type %s", _strType));

        ArrayList<AmTrack> alTrack = new ArrayList<>();
        try {
            m_database = m_helper.getReadableDatabase();

            String strSQL = "  SELECT at_id, at_file_name, at_file_ext, at_type, at_content FROM audiometry_track "
                    + " WHERE at_type = ? ; ";
            String[] params = {_strType};
            Cursor cursor = m_database.rawQuery(strSQL, params);

            Log.v(m_TAG,
                    String.format("selectTrackFromType Result = %d", cursor.getCount()));
            if (cursor.getCount() <= 0)
                return null;

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String ext = cursor.getString(2);
                String type = cursor.getString(3);
                String content = cursor.getString(4);

                AmTrack atOne = new AmTrack(id, name, ext, type, content);
                alTrack.add(atOne);

                Log.v(m_TAG,
                        String.format("  selectTrackFromType \n name %s, ext %s, type %s, content %s ",
                                name, ext, type, content));
            }
            cursor.close();
            return alTrack;

        } catch (Exception e) {
            Log.v(m_TAG, "selectTrackFromType Exception " + e);
            return null;
        }
    }

    public ArrayList<StWord> selectWordListFromId(int _atId) {
        Log.v(m_TAG,
                String.format("selectWordFromId AtId %d", _atId));
        ArrayList<StWord> wordList = new ArrayList<>();
        try {
            m_database = m_helper.getReadableDatabase();

            String strSQL = "  SELECT sw_id, sw_word, at_id, sw_idx FROM sentence_word"
                    + " WHERE at_id = ? ; ";
            String[] params = {Integer.toString(_atId)};
            Cursor cursor = m_database.rawQuery(strSQL, params);

            Log.v(m_TAG,
                    String.format("selectWordFromId Result = %d", cursor.getCount()));

            if (cursor.getCount() <= 0)
                return new ArrayList<>();

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                int sw_id = cursor.getInt(0);
                String word = cursor.getString(1);
                int at_id = cursor.getInt(2);
                int sw_idx = cursor.getInt(3);

                StWord wordOne = new StWord(sw_id, word, at_id , sw_idx);
                wordList.add(wordOne);

                Log.v(m_TAG, wordOne.getSw_word());
            }
            cursor.close();
            return wordList;

        } catch (Exception e) {
            Log.v(m_TAG, "selectWordFromId Exception " + e);
            return null;
        }

    }

    public void setAccount(Account _Account){
        this.m_Account = _Account;
    }


    public void saveTestResults(){
        Log.v(m_TAG, "saveTestResults");
        calculateTestSetAndGroupResult();
        insertAndSelectTestGroup();
        insertAndSelectTestSet();
        insertSrsTestUnitList();

    }

    private void calculateTestSetAndGroupResult() {
        Log.v(m_TAG, "calculateTestSetAndGroupResult");
        m_TestSetLeft = calculateSrsAndGetTestSet(TConst.T_LEFT);
        m_TestSetRight = calculateSrsAndGetTestSet(TConst.T_RIGHT);
        m_strGroupResult = "임시";

        
    }


    public HrTestSet calculateSrsAndGetTestSet(int iTestSide) {
        Log.v(m_TAG,"calculateSrsAndGetTestSet");
        String strTestSide = "";
        SentScore scoreCur;

        if (TConst.T_LEFT == iTestSide){
            strTestSide = TConst.STR_LEFT_SIDE;
            scoreCur = GlobalVar.g_SentScoreLeft;



        } else {
            strTestSide = TConst.STR_RIGHT_SIDE;
            scoreCur = GlobalVar.g_SentScoreRight;


        }

        Log.v(m_TAG, String.format("calculateSrsAndGetTestSet WordScore : %d, SentenceScore : %d ",
                scoreCur.get_iWordScore(), scoreCur.get_iSentScore()));
        String strResult = String.format("단어 기준 정답률 : %d%%", scoreCur.get_iWordScore());
        String strComment = String.format("문장 기준 정답률 : %d%%", scoreCur.get_iSentScore());


        return new HrTestSet(0,0,strTestSide, strResult, strComment);
    }




    public void insertAndSelectTestGroup() {
        Log.v(m_TAG,"insertAndSelectTestGroup");
        Date dtNow = new Date();
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdFormatter.format(dtNow);

        HrTestGroup tgIns = new HrTestGroup(0, strDate, m_strTestType, m_strGroupResult, m_Account.getAcc_id());
        HrTestDAO hrTestDAO = new HrTestDAO(m_helper);
        m_TestGroup = hrTestDAO.insertAndSelectTestGroup(tgIns);

    }


    public void insertAndSelectTestSet() {
        Log.v(m_TAG, "insertAndSelectTestSet");
        HrTestDAO hrTestDAO = new HrTestDAO(m_helper);

        // String strResult = String.format("단어 기준 정답률 : %d%%" , GlobalVar.);
        String strLeftResult = String.format("단어 기준 정답률 : %d%%" , m_SentScoreLeft.get_iWordScore());
        String strLeftResultComment = String.format("문장 기준 정답률 : %d%%" , m_SentScoreLeft.get_iSentScore());
        m_TestSetLeft = new HrTestSet(0, 0, TConst.STR_LEFT_SIDE, strLeftResult, strLeftResultComment);

        String strRightResult = String.format("단어 기준 정답률 : %d%%" , m_SentScoreRight.get_iWordScore());
        String strRightResultComment = String.format("문장 기준 정답률 : %d%%" , m_SentScoreRight.get_iSentScore());

        m_TestSetRight = new HrTestSet(0, 0, TConst.STR_RIGHT_SIDE,strRightResult, strRightResultComment);

        Log.v(m_TAG, "*********** insertAndSelectTestSet right ********** " + m_TestSetRight.toString());
        Log.v(m_TAG, "*********** insertAndSelectTestSet left ********** " + m_TestSetLeft.toString());
        m_TestSetLeft.setTg_id(m_TestGroup.getTg_id());
        hrTestDAO.insertTestSet(m_TestSetLeft);
        m_TestSetLeft = hrTestDAO.selectTestSet(m_TestSetLeft);
        m_TestSetRight.setTg_id(m_TestGroup.getTg_id());
        hrTestDAO.insertTestSet(m_TestSetRight);
        m_TestSetRight = hrTestDAO.selectTestSet(m_TestSetRight);

    }
    public void insertSrsTestUnitList(){
        Log.v(m_TAG,"**insertSrsTestUnitList**");
        try {
            tryInsertSrsTestUnitList();
        }catch (Exception e){
            Log.v(m_TAG, "insertSrsTestUnitList Exception " + e);
        }
    }

    private void tryInsertSrsTestUnitList() {
        Log.v(m_TAG, "tryInsertSrsTestUnitList");
        insertTestUnitList(m_TestSetLeft.getTs_id(), m_SentScoreLeft.get_alSentence());
        insertTestUnitList(m_TestSetRight.getTs_id(), m_SentScoreRight.get_alSentence());
    }

    private void insertTestUnitList(int iTsId, ArrayList<SentUnit> alSentUnit){
        Log.v(m_TAG, "*****insertTestUnitList*****");
        m_database = m_helper.getWritableDatabase();
        for (SentUnit unitOne : alSentUnit){
            Log.v(m_TAG,String.format("insertTestUnitList SentUnit Q : %s, A : %s, C : %d",
                    unitOne.get_Question(), unitOne.get_Answer(), unitOne.get_Correct()));
            String strSQL = " INSERT INTO hrtest_unit (ts_id, tu_question, tu_answer, tu_iscorrect) "
                    + " VALUES (?, ?, ?, ?); ";
            Object[] params = { iTsId, unitOne.get_Question(), unitOne.get_Answer(), unitOne.get_Correct() };

            m_database.execSQL(strSQL, params);
        }
    }
    /*
    public void loadSrsResultFromTestGroupId(int iTgId){

    }

     */

}
