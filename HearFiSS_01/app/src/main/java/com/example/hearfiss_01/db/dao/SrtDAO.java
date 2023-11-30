package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        m_strTestType = TConst.STR_SRT_TYPE;
    }

    public void releaseAndClose() {
        Log.v(m_TAG,
                String.format("releaseAndClose"));
        try {

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
        insertTestUnitList();
    }

    private void caculateTestSetAndGroupResult() {
//        m_TestSetLeft = caculatePtaAndGetTestSet(TConst.T_LEFT);
//        m_TestSetRight = caculatePtaAndGetTestSet(TConst.T_RIGHT);
//
//        int iLeftPTA = Integer.parseInt(m_TestSetLeft.getTs_Result());
//        int iRightPTA = Integer.parseInt(m_TestSetRight.getTs_Result());
//
//        PtaCalculator calcPTA = new PtaCalculator();
//        if(iLeftPTA > iRightPTA){
//            m_strGroupResult = calcPTA.getHearingLossStr(iLeftPTA);
//        } else {
//            m_strGroupResult = calcPTA.getHearingLossStr(iRightPTA);
//        }
        m_strGroupResult = "잘모르니 그냥 가자";
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

        m_TestSetLeft = new HrTestSet(0, 0, TConst.STR_LEFT_SIDE, "100 dB 비슷한 무언가", "심도난청 비슷한 무언가");
        m_TestSetRight = new HrTestSet(0, 0, TConst.STR_RIGHT_SIDE, "30 dB 비슷한 무언가", "경도난청 비슷한 무언가");

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

    private void insertTestUnitList() {
        try {
            tryInsertSrtTestUnitList(m_TestSetRight.getTs_id(), m_alRight);
            tryInsertSrtTestUnitList(m_TestSetLeft.getTs_id(), m_alLeft);
        } catch (Exception e) {
            Log.v(m_TAG, "insertTestUnitList Exception " + e);
        }
    }

    private void tryInsertSrtTestUnitList(int iTsId, ArrayList<SrtUnit> alSrtUnit) {
        Log.v(m_TAG, "*********** tryInsertSrtTestUnitList ********** ");
        int i = 0;
        for (SrtUnit unitOne : alSrtUnit) {
            Log.v(m_TAG, i++ + unitOne.toString());
        }

        m_database = m_helper.getWritableDatabase();
        for (SrtUnit unitOne : alSrtUnit) {
            Log.v(m_TAG, String.format("insertTestUnitList SrtUnit Q:%s, A:%s, C:%d",
                    unitOne.get_Question(), unitOne.get_Answer(), unitOne.get_Correct()));

            String strSQL = " INSERT INTO hrtest_unit (ts_id, tu_question, tu_answer, tu_iscorrect) "
                    + " VALUES (?, ?, ?, ?); ";
            Object[] params = {iTsId, unitOne.get_Question(), unitOne.get_Answer(), unitOne.get_Correct()};

            m_database.execSQL(strSQL, params);
        }
    }


    public void loadSrtResultsFromTestGroup(HrTestGroup tgInput) {
        HrTestDAO hrTestDAO = new HrTestDAO(m_helper);
        m_TestGroup = hrTestDAO.selectTestGroup(tgInput);

        selectBothSideTestSet();
        getBothSideSrtUnitList();
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

        ArrayList<SrtUnit> unitList = new ArrayList<>();
        m_database = m_helper.getReadableDatabase();

        try {
            m_database = m_helper.getReadableDatabase();

            String strSQL = " SELECT tu_id, ts_id, tu_question, tu_answer, tu_iscorrect "
                    + " FROM hrtest_unit WHERE ts_id = ?; ";
            String[] params = {Integer.toString(_tsId)};
            Cursor cursor = m_database.rawQuery(strSQL, params);

            Log.v(m_TAG,
                    String.format("tryGetSrtUnitListFromTestSet Result = %d", cursor.getCount()));
            if (cursor.getCount() <= 0)
                return new ArrayList<>();

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                int tu_id = cursor.getInt(0);
                int ts_id = cursor.getInt(1);
                String tu_question = cursor.getString(2);
                String tu_answer = cursor.getString(3);
                int tu_iscorrect = cursor.getInt(4);


                SrtUnit unitOne = new SrtUnit(tu_question, tu_answer, tu_iscorrect, 0, 0);
                unitList.add(unitOne);

                Log.v("SQLiteControl", unitOne.toString());
            }
            cursor.close();
            return unitList;

        } catch (Exception e) {
            Log.v("SQLiteControl", "selectTestUnitFromTsId Exception " + e);
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
