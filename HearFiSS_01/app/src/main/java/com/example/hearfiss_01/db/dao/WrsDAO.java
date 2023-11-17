package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.audioTest.WRS.WordScore;
import com.example.hearfiss_01.audioTest.WRS.WordUnit;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.global.TConst;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WrsDAO {
    String m_TAG = "WrsDAO";

    Context m_Context;

    String m_strTestType;
    SQLiteHelper m_helper;
    SQLiteDatabase m_database;

    Account m_Account;
    HrTestGroup m_TestGroup;
    HrTestSet m_TestSetLeft;
    HrTestSet m_TestSetRight;
    ArrayList<WordUnit> m_alRight;
    ArrayList<WordUnit> m_alLeft;
    String m_strGroupResult;

    public WrsDAO(@Nullable Context _context) {
        m_Context = _context;
        m_helper = new SQLiteHelper(m_Context,  TConst.DB_FILE, null, TConst.DB_VER);

        m_Account = new Account();
        m_alRight = new ArrayList<>();
        m_alLeft = new ArrayList<>();

        m_strTestType = TConst.STR_WRS_TYPE;
    }

    public void releaseAndClose() {
        Log.v(m_TAG,
                String.format("releaseAndClose"));
        try {
            m_alRight = new ArrayList<>();
            m_alLeft = new ArrayList<>();

            m_database.close();
            m_helper.close();
        } catch (Exception e) {
            Log.v(m_TAG, "releaseAndClose Exception " + e);
        }
    }

    public void setAccount(Account _Account) {
        this.m_Account = _Account;
    }

    public HrTestGroup getTestGroup() {
        return m_TestGroup;
    }

    public HrTestSet getTestSetLeft() {
        return m_TestSetLeft;
    }

    public HrTestSet getTestSetRight() {
        return m_TestSetRight;
    }

    public ArrayList<WordUnit> getRightUnitList() {
        return m_alRight;
    }

    public ArrayList<WordUnit> getLeftUnitList() {
        return m_alLeft;
    }

    public void setResultList(ArrayList<WordUnit> _alLeft, ArrayList<WordUnit> _alRight ) {
        this.m_alLeft = _alLeft;
        this.m_alRight = _alRight;

    }

    public void saveTestResults() {
        Log.v(m_TAG, "saveTestResults");
        caculateTestSetAndGroupResult();
        insertAndSelectTestGroup();
        insertAndSelectTestSet();
        InsertWrsTestUnitList();

    }

    private void caculateTestSetAndGroupResult() {
        Log.v(m_TAG, " caculateTestSetAndGroupResult ");
        m_TestSetLeft = caculateWrsAndGetTestSet(TConst.T_LEFT);
        m_TestSetRight = caculateWrsAndGetTestSet(TConst.T_RIGHT);

        int iLeftScore = Integer.parseInt(m_TestSetLeft.getTs_Result());
        int iRightScore = Integer.parseInt(m_TestSetRight.getTs_Result());

        WordScore scoreTemp = new WordScore();
        if(iLeftScore > iRightScore){
            m_strGroupResult = scoreTemp.getGradeFromScore(iRightScore);
        } else {
            m_strGroupResult = scoreTemp.getGradeFromScore(iLeftScore);
        }
    }

    public HrTestSet caculateWrsAndGetTestSet(int iTestSide) {
        Log.v(m_TAG, " caculateWrsAndGetTestSet ");
        String strTestSide = "";
        ArrayList<WordUnit> alWordUnit;

        if(TConst.T_LEFT == iTestSide){
            strTestSide = TConst.STR_LEFT_SIDE;
            alWordUnit = m_alLeft;
        } else {
            strTestSide = TConst.STR_RIGHT_SIDE;
            alWordUnit = m_alRight;
        }

        WordScore curScore = new WordScore();
        curScore.setM_alWord(alWordUnit);
        curScore.scoringWordList();
        String strGrade = curScore.getGradeFromScore();

        String strResult = String.format( "%d", curScore.getM_iScore() );
        String strComment = String.format( "%d %% (%s)", curScore.getM_iScore(), strGrade );
        return new HrTestSet(0, 0, strTestSide, strResult, strComment);
    }

    public void insertAndSelectTestGroup() {
        Log.v(m_TAG, " insertAndSelectTestGroup ");
        Date dtNow = new Date();
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdFormatter.format(dtNow);

        HrTestGroup tgIns = new HrTestGroup(0, strDate, m_strTestType, m_strGroupResult, m_Account.getAcc_id());
        HrTestDAO hrTestDAO = new HrTestDAO(m_helper);
        m_TestGroup = hrTestDAO.insertAndSelectTestGroup(tgIns);

    }

    public void insertAndSelectTestSet() {
        Log.v(m_TAG, " insertAndSelectTestSet ");

        HrTestDAO hrTestDAO = new HrTestDAO(m_helper);

        m_TestSetLeft.setTg_id(m_TestGroup.getTg_id());
        hrTestDAO.insertTestSet(m_TestSetLeft);
        m_TestSetLeft = hrTestDAO.selectTestSet(m_TestSetLeft);

        m_TestSetRight.setTg_id(m_TestGroup.getTg_id());
        hrTestDAO.insertTestSet(m_TestSetRight);
        m_TestSetRight = hrTestDAO.selectTestSet(m_TestSetRight);

    }

    public void InsertWrsTestUnitList() {
        try{
            tryInsertWrsTestUnitList();
        } catch (Exception e) {
            Log.v(m_TAG, "InsertPttTestUnitList Exception " + e);
        }
    }

    public void tryInsertWrsTestUnitList() {
        insertTestUnitList(m_TestSetLeft.getTs_id(), m_alLeft);
        insertTestUnitList(m_TestSetRight.getTs_id(), m_alRight);
    }

    private void insertTestUnitList(int iTsId, ArrayList<WordUnit> alWordUnit){
        m_database = m_helper.getWritableDatabase();
        for(WordUnit unitOne : alWordUnit){
            Log.v(m_TAG, String.format("insertTestUnitList WordUnit Q:%s, A:%s, C:%d",
                    unitOne.get_Question(), unitOne.get_Answer(), unitOne.get_Correct()) );

            String strSQL = " INSERT INTO hrtest_unit (ts_id, tu_question, tu_answer, tu_iscorrect) "
                    + " VALUES (?, ?, ?, ?); ";
            Object[] params = { iTsId, unitOne.get_Question(), unitOne.get_Answer(), unitOne.get_Correct() };

            m_database.execSQL(strSQL, params);
        }
    }

    public void loadWrsResultFromTestGroupId(int iTgId) {
        HrTestDAO hrTestDAO = new HrTestDAO(m_helper);
        m_TestGroup =  hrTestDAO.selectTestGroupFromTgId(iTgId);
        selectBothSideTestSet();
        getBothSideUnitList();
    }

    public void getBothSideUnitList(){
        Log.v(m_TAG,
                String.format("getBothSideUnitList "));

        try {
            m_alLeft = tryGetUnitListFromTestSet(m_TestSetLeft.getTs_id());
            m_alRight = tryGetUnitListFromTestSet(m_TestSetRight.getTs_id());

        } catch (Exception e) {
            Log.v(m_TAG, "getBothSideUnitList Exception " + e);
        }
    }

    private ArrayList<WordUnit> tryGetUnitListFromTestSet(int _tsId){

        ArrayList<WordUnit> unitList = new ArrayList<>();
        m_database = m_helper.getReadableDatabase();

        String strSQL = " SELECT tu_id, tu_question, tu_answer, tu_iscorrect "
                + " FROM hrtest_unit WHERE ts_id = ?; ";
        String[] params = {Integer.toString(_tsId)};
        Cursor cursor = m_database.rawQuery(strSQL, params);

        Log.v(m_TAG,
                String.format("tryGetThresholdListFromTestSet Result = %d", cursor.getCount()));
        if (cursor.getCount() <= 0)
            return new ArrayList<>();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            int tu_id = cursor.getInt(0);
            String tu_question = cursor.getString(1);
            String tu_answer = cursor.getString(2);
            int tu_correct = cursor.getInt(3);

            WordUnit unitOne = new WordUnit(tu_question, tu_answer, tu_correct);
            unitList.add(unitOne);

            Log.v(m_TAG, unitOne.toString());
        }
        cursor.close();
        return unitList;
    }


    private void selectBothSideTestSet() {
        try {
            HrTestDAO hrTestDAO = new HrTestDAO(m_helper);

            m_TestSetLeft = hrTestDAO.selectTestSetFromTestGroup(m_TestGroup.getTg_id(), TConst.STR_LEFT_SIDE);
            m_TestSetRight = hrTestDAO.selectTestSetFromTestGroup(m_TestGroup.getTg_id(), TConst.STR_RIGHT_SIDE);
            Log.v(m_TAG,
                    String.format("selectBothSideTestSet tg_id %d, ts_id_left %s, ts_id_right %s ",
                            m_TestGroup.getTg_id(), m_TestSetLeft.getTs_id(), m_TestSetRight.getTs_id()));
        } catch (Exception e) {
            Log.v(m_TAG, " selectBothSideTestSet Exception " + e);
        }
    }


}
