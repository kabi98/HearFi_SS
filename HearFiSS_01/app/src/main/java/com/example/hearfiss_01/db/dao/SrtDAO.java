package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hearfiss_01.audioTest.SRT.SrtScore;
import com.example.hearfiss_01.audioTest.SRT.SrtUnit;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.HrTestUnit;
import com.example.hearfiss_01.global.TConst;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SrtDAO {

    String m_TAG = "SrtDAO";

    String m_strTestType;

    Context m_Context;

    SQLiteHelper m_helper;
    SQLiteDatabase m_database;

    String m_strGroupResult;
    Account m_Account;
    HrTestGroup m_TestGroup;

    HrTestSet m_TestSetLeft;
    HrTestSet m_TestSetRight;

    ArrayList<SrtUnit> m_alLeft, m_alRight;

    public HrTestGroup getTestGroup() {
        return m_TestGroup;
    }

    public void setM_TestGroup(HrTestGroup _TestGroup) {
        this.m_TestGroup = _TestGroup;
    }

    public Account getAccount() {
        return m_Account;
    }

    public void setAccount(Account _Account) {
        this.m_Account = _Account;
    }

    public ArrayList<SrtUnit> get_alLeftList() {
        return m_alLeft;
    }

    public ArrayList<SrtUnit> get_alRightList() {
        return m_alRight;
    }

    public void setResultUnitList(ArrayList<SrtUnit> alLeft, ArrayList<SrtUnit> alRight) {
        this.m_alLeft = alLeft;
        this.m_alRight = alRight;

    }

    public SrtDAO(@Nullable Context _context) {

        m_Context = _context;
        m_helper = new SQLiteHelper(m_Context, TConst.DB_FILE, null, TConst.DB_VER);

        m_Account = new Account();
        m_alRight = new ArrayList<>();
        m_alLeft = new ArrayList<>();

        m_strTestType = TConst.STR_SRT_TYPE;
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

    public HrTestSet getTestSetLeft() {
        return m_TestSetLeft;
    }

    public HrTestSet getTestSetRight() {
        return m_TestSetRight;
    }


    public void saveResult() {
        Log.v(m_TAG, "savePttResult");
        caculateTestSetAndGroupResult();
        insertAndSelectTestGroup();
        insertAndSelectTestSet();
        InsertSrtTestUnitList();
    }

    private void caculateTestSetAndGroupResult() {

        Log.v(m_TAG,"calculateTestSetAndGroupResult");
        m_TestSetLeft = calculateSrtAndGetTestSet(TConst.T_LEFT);
        m_TestSetRight = calculateSrtAndGetTestSet(TConst.T_RIGHT);

        Log.v(m_TAG, "calculateTestSetAndGroupResult : " + m_TestSetLeft.toString());
        Log.v(m_TAG, "calculateTestSetAndGroupResult : " + m_TestSetRight.toString());

        // int iLeftSRT = Integer.parseInt(m_TestSetLeft.getTs_Result());
        //int iRightSRT = Integer.parseInt(m_TestSetRight.getTs_Result());

        m_strGroupResult = "잘모르니 그냥 가자";
    }

    private HrTestSet calculateSrtAndGetTestSet(int iTestSide) {
        Log.v(m_TAG, "cacluateSrtAndGetTestSet");
        String strTestSide = "";
        ArrayList<SrtUnit> alSrtUnit;

        if (TConst.T_LEFT == iTestSide){
            strTestSide = TConst.STR_LEFT_SIDE;
            alSrtUnit = m_alLeft;
        }else {
            strTestSide = TConst.STR_RIGHT_SIDE;
            alSrtUnit = m_alRight;
        }
        SrtScore curScore = new SrtScore();
        curScore.setM_alSrtUnit(alSrtUnit);

        Log.v(m_TAG, String.format(" **** calculateSrtAndGetTestSet **** PassThrd : %d, CurDB : %d",
                curScore.getM_iPassTrsd(), curScore.getM_iCurDb()));

        String strResult = String.format("%d", curScore.getM_iPassTrsd());
        String strComment = String.format("%d dB HL", curScore.getM_iPassTrsd());
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

      //  m_TestSetLeft = new HrTestSet(0, 0, TConst.STR_LEFT_SIDE, "100 dB 비슷한 무언가", "심도난청 비슷한 무언가");
      //  m_TestSetRight = new HrTestSet(0, 0, TConst.STR_RIGHT_SIDE, "30 dB 비슷한 무언가", "경도난청 비슷한 무언가");

        Log.v(m_TAG, "*********** insertAndSelectTestSet right ********** " + m_TestSetRight.toString());
        Log.v(m_TAG, "*********** insertAndSelectTestSet left ********** " + m_TestSetLeft.toString());
        m_TestSetLeft.setTg_id(m_TestGroup.getTg_id());
        hrTestDAO.insertTestSet(m_TestSetLeft);
        m_TestSetLeft = hrTestDAO.selectTestSet(m_TestSetLeft);

        m_TestSetRight.setTg_id(m_TestGroup.getTg_id());
        hrTestDAO.insertTestSet(m_TestSetRight);
        m_TestSetRight = hrTestDAO.selectTestSet(m_TestSetRight);

        Log.v(m_TAG, "*********** insertAndSelectTestSet right ********** " + m_TestSetRight.toString());
        Log.v(m_TAG, "*********** insertAndSelectTestSet left ********** " + m_TestSetLeft.toString());
    }


    public void InsertSrtTestUnitList(){
        try {
            tryInsertSrtTestUnitList();
        }catch (Exception e){
            Log.v(m_TAG, "InsertSrtTestUnitList Exception : " + e);
        }
    }

    public void tryInsertSrtTestUnitList(){
        Log.v(m_TAG, "tryInsertSrtTestUnitList");

        Log.v(m_TAG, "tryInsertSrtTestUnitList Right : " + m_alRight.size());
        insertTestUnitList(m_TestSetRight.getTs_id(), m_alRight);

        Log.v(m_TAG, "tryInsertSrtTestUnitList Left : " + m_alLeft.size());
        insertTestUnitList(m_TestSetLeft.getTs_id(), m_alLeft);

        Log.v(m_TAG, "tryInsertSrtTestUnitList completed");
    }

    private boolean isDuplicateSrtUnit(int tsId, String question, String answer){
        m_database = m_helper.getReadableDatabase();
        String query = "SELECT COUNT (*) FROM hrtest_unit WHERE ts_id = ? AND tu_question = ? AND tu_answer = ?";
        Cursor cursor = m_database.rawQuery(query, new String[]{String.valueOf(tsId), question, answer});
        if (cursor != null && cursor.moveToFirst()){
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }
        return false;
    }
    private void insertTestUnitList(int iTsId, ArrayList<SrtUnit> alSrtUnit){
        m_database = m_helper.getWritableDatabase();
        for (SrtUnit unitOne : alSrtUnit){
            if (!isDuplicateSrtUnit(iTsId, unitOne.get_Question(), unitOne.get_Answer())){
                // hrtestunit table
                String strSQLHrTestUnit =  " INSERT INTO hrtest_unit (ts_id, tu_question, tu_answer, tu_iscorrect) "
                        + "VALUES (?,?,?,?); ";
                Object[] paramsHrTestUnit = { iTsId, unitOne.get_Question(), unitOne.get_Answer(), unitOne.get_Correct()};

                m_database.execSQL(strSQLHrTestUnit, paramsHrTestUnit);
                Log.v(m_TAG, String.format("inserted TestUnitList HrTestUnit Q: %s, A: %s, C: %d ",
                        unitOne.get_Question(), unitOne.get_Answer(), unitOne.get_Correct()));

                // srtunit table
                String strSQLSrtUnit = "INSERT INTO srt_unit (ts_id, tu_dBHL) VALUES (?, ?);";
                Object[] paramsSrtUnit = {iTsId, unitOne.get_CurDb()};
                m_database.execSQL(strSQLSrtUnit, paramsSrtUnit);
                Log.v(m_TAG, String.format("inserted TestUnitList SrtUnit dB : %d ", unitOne.get_CurDb()));
            }

        }
    }


    public void loadSrtResultsFromTestGroupId(int iTgId) {
        Log.v(m_TAG, " loadSrtResultFromTestGroupId" + iTgId);

        HrTestDAO hrTestDAO = new HrTestDAO(m_helper);
        m_TestGroup = hrTestDAO.selectTestGroupFromTgId(iTgId);

        if (m_TestGroup == null) {
            Log.v(m_TAG, "loadSrtResultFromTestGroupId = null");
        } else {
            Log.v(m_TAG, "loadSrtResultsFromTestGroupId - TestGroup 로드 완료: " + m_TestGroup.toString());
        }

        selectBothSideTestSet();
        if (m_TestSetLeft == null) {
            Log.v(m_TAG, "loadSrtResultsFromTestGroupId - 왼쪽 TestSet이 null임.");
        } else {
            Log.v(m_TAG, "loadSrtResultsFromTestGroupId - 왼쪽 TestSet 로드 완료: " + m_TestSetLeft.toString());
        }
        if (m_TestSetRight == null) {
            Log.v(m_TAG, "loadSrtResultsFromTestGroupId - 오른쪽 TestSet이 null임.");
        } else {
            Log.v(m_TAG, "loadSrtResultsFromTestGroupId - 오른쪽 TestSet 로드 완료: " + m_TestSetRight.toString());
        }
        getBothSideSrtUnitList();
        if (m_alLeft == null) {
            Log.v(m_TAG, "loadSrtResultsFromTestGroupId - 왼쪽 SrtUnit 리스트가 null임.");
        } else {
            Log.v(m_TAG, "loadSrtResultsFromTestGroupId - 왼쪽 SrtUnit 리스트 로드 완료, 크기: " + m_alLeft.size());
        }
        if (m_alRight == null) {
            Log.v(m_TAG, "loadSrtResultsFromTestGroupId - 오른쪽 SrtUnit 리스트가 null임.");
        } else {
            Log.v(m_TAG, "loadSrtResultsFromTestGroupId - 오른쪽 SrtUnit 리스트 로드 완료, 크기: " + m_alRight.size());
        }

        Log.v(m_TAG, "loadSrtResultsFromTestGroupId - 완료");

    }


    private void getBothSideSrtUnitList() {
        Log.v(m_TAG, String.format("getBothSideUnitList"));
        try {
            m_alLeft = tryGetUnitListFromTestSet(m_TestSetLeft.getTs_id());
            m_alRight = tryGetUnitListFromTestSet(m_TestSetRight.getTs_id());
        } catch (Exception e) {
            Log.v(m_TAG, "getBothSideUnitList Exception" + e);
        }

    }


    private ArrayList<SrtUnit> tryGetUnitListFromTestSet(int _tsId) {
        Log.v(m_TAG, "tryGetUnitListFromTestSet");
        ArrayList<SrtUnit> unitList = new ArrayList<>();

        try {
            m_database = m_helper.getReadableDatabase();
/*
            String strSQL = "SELECT h.tu_id, h.ts_id, h.tu_question, h.tu_answer, h.tu_iscorrect, s.tu_dBHL " +
                    "FROM hrtest_unit h JOIN srt_unit s ON h.ts_id = s.ts_id " +
                    "WHERE h.ts_id = ?;";
*/

            String strSQL = " SELECT h.tu_id, h.ts_id, h.tu_question, h.tu_answer, h.tu_iscorrect, s.tu_dBHL "
                    + " FROM hrtest_unit h, srt_unit s "
                    + " WHERE h.tu_id = s.tu_id "
                    + " AND h.ts_id = ?; ";

            String[] params = {Integer.toString(_tsId)};
            Cursor cursor = m_database.rawQuery(strSQL, params);

            Log.v(m_TAG,
                    String.format("tryGetSrtUnitListFromTestSet Result = %d", cursor.getCount()));
            if (cursor.getCount() <= 0) {
                Log.v(m_TAG, "tryGetUnitListFromTestSet - No Data Found");
                return new ArrayList<>();
            }

            for (int i = 0; i <cursor.getCount(); i++){
                cursor.moveToNext();
                String tu_question = cursor.getString(2);
                String tu_answer = cursor.getString(3);
                int tu_iscorrect = cursor.getInt(4);
                int tu_dBHL = cursor.getInt(5);

                SrtUnit unitOne = new SrtUnit(tu_question, tu_answer, tu_iscorrect, tu_dBHL, 0);
                unitList.add(unitOne);

                Log.v(m_TAG, "tryGetUnitListFromTestSet : " + unitOne);

            }
            cursor.close();
            return unitList;
        } catch (Exception e) {
            Log.v(m_TAG, "selectTestUnitFromTsId Exception " + e);
            return null;
        }
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
