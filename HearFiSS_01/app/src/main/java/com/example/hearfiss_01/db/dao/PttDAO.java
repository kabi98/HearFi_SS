package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.audioTest.PTT.PtaCalculator;
import com.example.hearfiss_01.audioTest.PTT.PttThreshold;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.PttTestDevice;
import com.example.hearfiss_01.db.DTO.PureToneTrack;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PttDAO {
    String m_TAG = "PttDAO";

    String m_strTestType;

    Context m_Context;

    SQLiteHelper m_helper;
    SQLiteDatabase m_database;

    String m_strPhone;
    String m_strDevice;

    String m_strGroupResult;
    Account m_Account;
    HrTestGroup m_TestGroup;

    PttTestDevice m_TestDevice;
    HrTestSet m_TestSetLeft;
    HrTestSet m_TestSetRight;
    ArrayList<PttThreshold> m_alRightThreshold;
    ArrayList<PttThreshold> m_alLeftThreshold;

    public PttDAO(@Nullable Context _context) {

        m_Context = _context;
        m_helper = new SQLiteHelper(m_Context,  TConst.DB_FILE, null, TConst.DB_VER);

        m_strPhone = TConst.DEFAULT_PHONE;
        m_strDevice = TConst.DEFAULT_EARPHONE;

        m_Account = new Account();
        m_alRightThreshold = new ArrayList<>();
        m_alLeftThreshold = new ArrayList<>();

        m_strTestType = TConst.STR_PTT_TYPE;

    }

    public void releaseAndClose() {
        Log.v(m_TAG,
                String.format("releaseAndClose"));
        try {
            m_alRightThreshold = new ArrayList<>();
            m_alLeftThreshold = new ArrayList<>();

            m_database.close();
            m_helper.close();
        } catch (Exception e) {
            Log.v(m_TAG, "releaseAndClose Exception " + e);
        }
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

    public void setTestGroup(HrTestGroup _TestGroup) {
        this.m_TestGroup = _TestGroup;
    }

    public void setAccount(Account _Account) {
        this.m_Account = _Account;
    }

    public void setResultThreshold(ArrayList<PttThreshold> _alLeftThreshold,
                                   ArrayList<PttThreshold> _alRightThreshold) {

        this.m_alLeftThreshold = _alLeftThreshold;
        this.m_alRightThreshold = _alRightThreshold;
    }

    public ArrayList<PttThreshold> getRightThresholdList() {
        return m_alRightThreshold;
    }

    public ArrayList<PttThreshold> getLeftThresholdList() {
        return m_alLeftThreshold;
    }

    public void setStrPhone(String _strPhone) {
        this.m_strPhone = _strPhone;
    }

    public void setStrDevice(String _strDevice) {
        this.m_strDevice = _strDevice;
    }

    public PureToneTrack selectTrackFromHzDBHL(int iHz, int iDBHL){
        Log.v(m_TAG,
                String.format("selectTrackFromHzDBHL Hz %d, dBHL %d", iHz, iDBHL));
        try {
            return try_SelectTrackFromHzDBHL(iHz, iDBHL, m_strPhone, m_strDevice);

        } catch (Exception e) {
            Log.v(m_TAG, "selectTrackFromHzDBHL Exception " + e);
            return null;
        }

    }

    public PureToneTrack selectTrackFromHzDBHL(int iHz, int iDBHL, String strPhone, String strDevice){
        Log.v(m_TAG,
                String.format("selectTrackFromHzDBHL Hz:%d, dBHL:%d, phone:%s, device:%s",
                        iHz, iDBHL, strPhone, strDevice));
        try {
            return try_SelectTrackFromHzDBHL(iHz, iDBHL, strPhone, strDevice);
        } catch (Exception e) {
            Log.v(m_TAG, "selectTrackFromHzDBHL Exception " + e);
            return null;
        }

    }

    private PureToneTrack try_SelectTrackFromHzDBHL(int iHz, int iDBHL, String strPhone, String strDevice){
        m_database = m_helper.getReadableDatabase();

        String strSQL =
                " SELECT pt_id, pt_file_name, pt_file_ext, frequency, dBHL, phone, device "
                        + " FROM pt_track WHERE frequency= ? and dBHL = ? and phone= ? and device= ?; ";
        String[] params = {Integer.toString(iHz), Integer.toString(iDBHL), strPhone, strDevice};
        Cursor cursor = m_database.rawQuery(strSQL, params);

        Log.v(m_TAG,
                String.format("selectLogin Result = %d", cursor.getCount()));
        if (cursor.getCount() > 0) {
            cursor.moveToNext();

            int     pt_id           = cursor.getInt(0);
            String  pt_file_name    = cursor.getString(1);
            String  pt_file_ext     = cursor.getString(2);
            int     frequency       = cursor.getInt(3);
            int     dBHL            = cursor.getInt(4);
            String  phone           = cursor.getString(5);
            String  device          = cursor.getString(6);

            PureToneTrack ptTrackOne = new PureToneTrack(pt_id, pt_file_name, pt_file_ext,
                    frequency, dBHL, phone, device);

            Log.v(m_TAG, ptTrackOne.toString());
            cursor.close();

            return ptTrackOne;
        } else {
            return null;
        }
    }

    public void savePttResult(){
        Log.v(m_TAG, "savePttResult");
        caculateTestSetAndGroupResult();
        insertAndSelectTestGroup();
        insertAndSelectTestSet();
        InsertPttTestUnitList();
        insertAndSelectTestDevice();
    }

    private void caculateTestSetAndGroupResult() {
        m_TestSetLeft = caculatePtaAndGetTestSet(TConst.T_LEFT);
        m_TestSetRight = caculatePtaAndGetTestSet(TConst.T_RIGHT);

        int iLeftPTA = Integer.parseInt(m_TestSetLeft.getTs_Result());
        int iRightPTA = Integer.parseInt(m_TestSetRight.getTs_Result());

        PtaCalculator calcPTA = new PtaCalculator();
        if(iLeftPTA > iRightPTA){
            m_strGroupResult = calcPTA.getHearingLossStr(iLeftPTA);
        } else {
            m_strGroupResult = calcPTA.getHearingLossStr(iRightPTA);
        }
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

    private HrTestSet caculatePtaAndGetTestSet(int iTestSide) {
        Log.v(m_TAG, " caculatePtaAndInsertTestSet ");
        String strTestSide = "";
        PtaCalculator calcPTA = new PtaCalculator();
        ArrayList<PttThreshold> alPttThreshold;

        if(TConst.T_LEFT == iTestSide){
            strTestSide = TConst.STR_LEFT_SIDE;
            calcPTA.setThresholdList(m_alLeftThreshold);
        } else {
            strTestSide = TConst.STR_RIGHT_SIDE;
            calcPTA.setThresholdList(m_alRightThreshold);
        }


        int iPTA = calcPTA.calculatePTA_W3FA();
//        int iPTA = calcPTA.calculatePTA_6FA();
        String strHearingLoss = calcPTA.getHearingLossStr(iPTA);

        String strResult = String.format("%d", iPTA);
        String strComment = String.format("%02d dB HL (%s)", iPTA, strHearingLoss);

        return new HrTestSet(0, 0, strTestSide, strResult, strComment);
    }


    public void insertAndSelectTestDevice() {
        Log.v(m_TAG, " insertAndSelectTestDevice ");

        HrTestDAO hrTestDAO = new HrTestDAO(m_helper);

        m_TestDevice = new PttTestDevice();
        m_TestDevice.setTgId(m_TestGroup.getTg_id());
        m_TestDevice.setTdPhone(GlobalVar.g_PttStrPhone);
        m_TestDevice.setTdDevice(GlobalVar.g_PttStrDevice);

        hrTestDAO.insertPttTestDevice (m_TestDevice);
        m_TestDevice = hrTestDAO.selectPttTestDeviceFromTgId(m_TestGroup.getTg_id());

        Log.v(m_TAG, String.format(" insertAndSelectTestDevice %s", m_TestDevice.toString()));

    }

    public void InsertPttTestUnitList() {
        try{
            tryInsertPttTestUnitList();
        } catch (Exception e) {
            Log.v(m_TAG, "InsertPttTestUnitList Exception " + e);
        }
    }

    public void tryInsertPttTestUnitList() {
        insertTestUnitList(m_TestSetLeft.getTs_id(), m_alLeftThreshold);
        insertTestUnitList(m_TestSetRight.getTs_id(), m_alRightThreshold);
    }

    private void insertTestUnitList(int iTsId, ArrayList<PttThreshold> alPttThreshold){
        m_database = m_helper.getWritableDatabase();
        for(PttThreshold thresholdOne : alPttThreshold){
            Log.v(m_TAG, String.format("insertTestUnitList Threshold Hz:%d, dBHL:%d",
                    thresholdOne.get_Hz(), thresholdOne.get_DBHL()) );
            String strSQL = " INSERT INTO ptt_test_unit (ts_id, tu_hz, tu_dBHL)  "
                    + " VALUES (?, ?, ?) ";
            Object[] params = { iTsId, thresholdOne.get_Hz(), thresholdOne.get_DBHL()};

            m_database.execSQL(strSQL, params);
        }
    }

    public void loadPttResultsFromTestGroup(HrTestGroup tgInput) {

        HrTestDAO hrTestDAO = new HrTestDAO(m_helper);
        m_TestGroup =  hrTestDAO.selectTestGroup(tgInput);

        selectBothSideTestSet();
        getBothSideThresholdList();
    }

    public void loadPttResultsFromTestGroupId(int iTgId) {

        HrTestDAO hrTestDAO = new HrTestDAO(m_helper);
        m_TestGroup =  hrTestDAO.selectTestGroupFromTgId(iTgId);

        selectBothSideTestSet();
        getBothSideThresholdList();
    }

    private void selectBothSideTestSet() {
        HrTestDAO hrTestDAO = new HrTestDAO(m_helper);
        m_TestSetLeft = hrTestDAO.selectTestSetFromTestGroup(m_TestGroup.getTg_id(), TConst.STR_LEFT_SIDE);
        m_TestSetRight = hrTestDAO.selectTestSetFromTestGroup(m_TestGroup.getTg_id(), TConst.STR_RIGHT_SIDE);
        Log.v(m_TAG,
                String.format("selectBothSideTestSet tg_id %d, ts_id_left %s, ts_id_right %s ",
                        m_TestGroup.getTg_id(), m_TestSetLeft.getTs_id(), m_TestSetRight.getTs_id()));
    }

    private void getBothSideThresholdList(){
        try {
            m_alLeftThreshold = tryGetThresholdListFromTestSet(m_TestSetLeft.getTs_id());
            m_alRightThreshold = tryGetThresholdListFromTestSet(m_TestSetRight.getTs_id());
        } catch (Exception e) {
            Log.v(m_TAG, " selectAllTestSetFromTestGroup Exception " + e);
        }
    }

    private ArrayList<PttThreshold> tryGetThresholdListFromTestSet(int _tsId){

        ArrayList<PttThreshold> thresholdList = new ArrayList<>();
        m_database = m_helper.getReadableDatabase();

        String strSQL = " SELECT tu_id, ts_id, tu_hz, tu_dbHL "
                + " FROM ptt_test_unit WHERE ts_id = ?; ";
        String[] params = {Integer.toString(_tsId)};
        Cursor cursor = m_database.rawQuery(strSQL, params);

        Log.v(m_TAG,
                String.format("tryGetThresholdListFromTestSet Result = %d", cursor.getCount()));
        if (cursor.getCount() <= 0)
            return new ArrayList<>();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
//            int tu_id = cursor.getInt(0);
//            int ts_id = cursor.getInt(1);
            int tu_hz = cursor.getInt(2);
            int tu_dbHL = cursor.getInt(3);

            PttThreshold tuOne = new PttThreshold(tu_hz, tu_dbHL);
            thresholdList.add(tuOne);

            Log.v(m_TAG, tuOne.toString());
        }
        cursor.close();
        return thresholdList;
    }


}

