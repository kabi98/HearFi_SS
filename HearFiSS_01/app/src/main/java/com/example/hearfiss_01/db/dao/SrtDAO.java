package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hearfiss_01.audioTest.PTT.PtaCalculator;
import com.example.hearfiss_01.audioTest.PTT.PttThreshold;
import com.example.hearfiss_01.audioTest.SRT.SrtUnit;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.PttTestDevice;
import com.example.hearfiss_01.global.GlobalVar;
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

    public void setResultUnitList(ArrayList<SrtUnit> alLeft, ArrayList<SrtUnit> alRight) {
        this.m_alLeft = alLeft;
        this.m_alRight = alRight;

    }

    public SrtDAO(@Nullable Context _context){

        m_Context = _context;
        m_helper = new SQLiteHelper(m_Context, TConst.DB_FILE, null, TConst.DB_VER);

        m_Account = new Account();
        m_strTestType = TConst.STR_SRT_TYPE;
    }

    public void releaseAndClose(){
        Log.v(m_TAG,
                String.format("releaseAndClose"));
        try {

            m_database.close();
            m_helper.close();
        } catch (Exception e) {
            Log.v(m_TAG, "releaseAndClose Exception " + e);
        }
    }

    public void saveResult(){
        Log.v(m_TAG, "savePttResult");
        caculateTestSetAndGroupResult();
        insertAndSelectTestGroup();
        insertAndSelectTestSet();
//        InsertPttTestUnitList();
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
    }

}
