package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hearfiss_01.audioTest.SRT.SrtThreshold;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.global.TConst;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class SrtDAO {

    String m_TAG = "SrtDAO";

    String m_strTestType;

    Context m_Context;

    SQLiteHelper m_helper;

    SQLiteDatabase m_database;

    String m_strPhone;

    String m_strDevice;

    String m_strGroupResult;

    Account m_Account;

    HrTestGroup m_TestGroup;

    HrTestSet m_TestSetLeft;

    HrTestSet m_TestSetRight;

    ArrayList<SrtThreshold> m_alRightThreshold;

    ArrayList<SrtThreshold> m_alLeftThreshold;

    public SrtDAO(@Nullable Context _context){

        m_Context = _context;
        m_helper = new SQLiteHelper(m_Context, TConst.DB_FILE, null, TConst.DB_VER);

        m_strPhone = TConst.DEFAULT_PHONE;
        m_strDevice = TConst.DEFAULT_EARPHONE;

        m_Account = new Account();
        m_alRightThreshold = new ArrayList<>();
        m_alLeftThreshold = new ArrayList<>();

        m_strTestType = TConst.STR_SRT_TYPE;
    }

    public void releaseAndClose(){
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

    public void setResultThreshold(ArrayList<SrtThreshold> _alLeftThreshold,
                                   ArrayList<SrtThreshold> _alRightThreshold) {

        this.m_alLeftThreshold = _alLeftThreshold;
        this.m_alRightThreshold = _alRightThreshold;
    }

    public ArrayList<SrtThreshold> getRightThresholdList() {
        return m_alRightThreshold;
    }

    public ArrayList<SrtThreshold> getLeftThresholdList() {
        return m_alLeftThreshold;
    }

    public void setStrPhone(String _strPhone) {
        this.m_strPhone = _strPhone;
    }

    public void setStrDevice(String _strDevice) {
        this.m_strDevice = _strDevice;
    }

}
