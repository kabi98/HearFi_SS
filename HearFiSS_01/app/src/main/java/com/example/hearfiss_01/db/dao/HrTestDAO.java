package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.PttTestDevice;
import com.example.hearfiss_01.global.TConst;

public class HrTestDAO {

    String m_TAG = "HrTestDAO";

    Context m_Context;

    boolean m_isMyDatabase = false;
    SQLiteHelper m_helper;
    SQLiteDatabase m_database;

    public HrTestDAO(SQLiteHelper m_helper) {

        m_isMyDatabase = false;
        this.m_helper = m_helper;
    }

    public HrTestDAO(@Nullable Context _context) {

        m_isMyDatabase = true;
        m_Context = _context;
        m_helper = new SQLiteHelper(m_Context,  TConst.DB_FILE, null, TConst.DB_VER);
    }

    public void releaseAndClose() {
        Log.v(m_TAG,
                String.format("releaseAndClose"));
        try {
            if( m_isMyDatabase ) {
                m_database.close();
                m_helper.close();
            }

        } catch (Exception e) {
            Log.v(m_TAG, "releaseAndClose Exception " + e);
        }
    }


    public HrTestGroup insertAndSelectTestGroup(HrTestGroup tgIns) {
        Log.v(m_TAG, " insertAndSelectTestGroup ");
        try {
            tryInsertTestGroup(tgIns);
            return selectTestGroup(tgIns);

        } catch (Exception e) {
            Log.v(m_TAG, "insertAndSelectTestGroup Exception " + e);
            return null;
        }
    }

    private void tryInsertTestGroup(HrTestGroup tgIns){
        m_database = m_helper.getWritableDatabase();
        String strSQL =
                " INSERT INTO hrtest_group (tg_date, tg_type, tg_result, acc_id)  "
                        + " VALUES (?, ?, ?, ?) ";
        Object[] params = {tgIns.getTg_Date(), tgIns.getTg_type(),
                tgIns.getTg_result(), tgIns.getAcc_id()};

        m_database.execSQL(strSQL, params);
    }

    public HrTestGroup selectTestGroup(HrTestGroup tgInput) {
        Log.v(m_TAG,
                String.format("selectTestGroup Date %s, Type %s, Result %s, Id %d",
                        tgInput.getTg_Date(), tgInput.getTg_type(), tgInput.getTg_result(), tgInput.getAcc_id()));

        try {
            return trySelectTestGroup(tgInput);
        } catch (Exception e) {
            Log.v(m_TAG, "selectTestGroup Exception " + e);
            return null;
        }
    }

    private HrTestGroup trySelectTestGroup(HrTestGroup tgInput){
        m_database = m_helper.getReadableDatabase();

        String strSQL = "  SELECT tg_id, tg_date, tg_type, tg_result, acc_id "
                + " FROM hrtest_group WHERE tg_date= ? and tg_type = ? and acc_id = ?; ";
        String[] params = { tgInput.getTg_Date(), tgInput.getTg_type(),
                Integer.toString(tgInput.getAcc_id())};
        Cursor cursor = m_database.rawQuery(strSQL, params);

        Log.v(m_TAG,
                String.format("selectTestGroup Result = %d", cursor.getCount()));
        if (cursor.getCount() > 0) {
            cursor.moveToNext();

            int     tg_id      = cursor.getInt(0);
            String  tg_date    = cursor.getString(1);
            String  tg_type    = cursor.getString(2);
            String  tg_result  = cursor.getString(3);
            int     acc_id     = cursor.getInt(4);

            HrTestGroup tgOne = new HrTestGroup(tg_id, tg_date, tg_type, tg_result, acc_id);

            Log.v(m_TAG, tgOne.toString());
            cursor.close();

            return tgOne;
        } else {
            return null;
        }
    }

    public void insertTestSet(HrTestSet tsInsert) {
        Log.v(m_TAG, " insertTestSet ");
        try {
            tryInsertTestSet(tsInsert);
        } catch (Exception e) {
            Log.v(m_TAG, "insertTestSet Exception " + e);
        }
    }

    public void tryInsertTestSet(HrTestSet tsInsert) {
        Log.v(m_TAG, " tryInsertTestSet ");
        m_database = m_helper.getWritableDatabase();

        String strSQL = " INSERT INTO hrtest_set (tg_id, ts_side, ts_result, ts_comment)  "
                + " VALUES (?, ?, ?, ?) ";
        Object[] params = {tsInsert.getTg_id(), tsInsert.getTs_side(),
                tsInsert.getTs_Result(), tsInsert.getTs_Comment()};

        m_database.execSQL(strSQL, params);
    }

    public HrTestSet selectTestSet(HrTestSet tsInput) {
        try {
            return trySelectTestSet(tsInput);
        } catch (Exception e) {
            Log.v(m_TAG, "selectTestSet Exception " + e);
            return null;
        }
    }

    public HrTestSet trySelectTestSet(HrTestSet tsInput) {
        Log.v(m_TAG,
                String.format("trySelectTestSet tg_id %d, ts_side %s ",
                        tsInput.getTg_id(), tsInput.getTs_side()));

        m_database = m_helper.getReadableDatabase();

        String strSQL = "  SELECT ts_id, tg_id, ts_side, ts_result, ts_comment "
                + " FROM hrtest_set WHERE tg_id= ? and ts_side = ?; ";
        String[] params = { Integer.toString(tsInput.getTg_id()), tsInput.getTs_side() };
        Cursor cursor = m_database.rawQuery(strSQL, params);

        Log.v(m_TAG,
                String.format("trySelectTestSet Result = %d", cursor.getCount()));
        if (cursor.getCount() > 0) {
            cursor.moveToNext();

            int     ts_id      = cursor.getInt(0);
            int     tg_id      = cursor.getInt(1);
            String  tg_side    = cursor.getString(2);
            String  tg_result  = cursor.getString(3);
            String  tg_comment = cursor.getString(4);

            HrTestSet tsOne = new HrTestSet(ts_id, tg_id, tg_side, tg_result, tg_comment);
            Log.v(m_TAG, tsOne.toString());
            cursor.close();

            return tsOne;
        } else {
            return null;
        }
    }

    public HrTestSet selectTestSetFromTestGroup(int iTgId, String strSide) {
        Log.v(m_TAG,
                String.format("selectTestSetFromTestGroup Id %d", iTgId));

        try {
            return trySelectTestSetFromTestGroup(iTgId, strSide);
        } catch (Exception e) {
            Log.v(m_TAG, "selectTestSetFromTestGroup Exception " + e);
            return null;
        }
    }

    private HrTestSet trySelectTestSetFromTestGroup(int iTgId, String strSide){
        m_database = m_helper.getReadableDatabase();

        String strSQL = "  SELECT ts_id, tg_id, ts_side, ts_result, ts_comment "
                + " FROM hrtest_set WHERE tg_id = ? and ts_side = ?; ";
        String[] params = { Integer.toString(iTgId), strSide };
        Cursor cursor = m_database.rawQuery(strSQL, params);

        Log.v(m_TAG,
                String.format("trySelectTestSetFromTestGroup Result = %d", cursor.getCount()));
        if (cursor.getCount() > 0) {
            cursor.moveToNext();

            int     ts_id      = cursor.getInt(0);
            int     tg_id      = cursor.getInt(1);
            String  tg_side    = cursor.getString(2);
            String  tg_result  = cursor.getString(3);
            String  tg_comment = cursor.getString(4);

            HrTestSet tsOne = new HrTestSet(ts_id, tg_id, tg_side, tg_result, tg_comment);
            Log.v(m_TAG, tsOne.toString());
            cursor.close();

            return tsOne;
        } else {
            return null;
        }

    }

    public HrTestGroup selectTestGroupFromTgId(int iTgId) {
        Log.v(m_TAG,
                String.format("selectTestGroupFromTgId Id %d", iTgId));

        try {
            return trySelectTestGroupFromTgId(iTgId);
        } catch (Exception e) {
            Log.v(m_TAG, "selectTestGroupFromTgId Exception " + e);
            return null;
        }
    }

    private HrTestGroup trySelectTestGroupFromTgId(int iTgId){
        m_database = m_helper.getReadableDatabase();

        String strSQL = "  SELECT tg_id, tg_Date, tg_type, tg_result, acc_id"
                + " FROM hrtest_group WHERE tg_id = ? ; ";
        String[] params = { Integer.toString(iTgId)};
        Cursor cursor = m_database.rawQuery(strSQL, params);

        Log.v(m_TAG,
                String.format("trySelectTestGroupFromTgId Result = %d", cursor.getCount()));
        if (cursor.getCount() > 0) {
            cursor.moveToNext();

            int     tg_id      = cursor.getInt(0);
            String  tg_date    = cursor.getString(1);
            String  tg_type    = cursor.getString(2);
            String  tg_result  = cursor.getString(3);
            int     acc_id     = cursor.getInt(4);

            HrTestGroup tgOne = new HrTestGroup(tg_id, tg_date, tg_type, tg_result, acc_id);

            Log.v(m_TAG, tgOne.toString());
            cursor.close();

            return tgOne;
        } else {
            return null;
        }
    }

    public void insertPttTestDevice(PttTestDevice tdInsert) {
        Log.v(m_TAG, " insertPttTestDevice ");
        try {
            tryInsertPttTestDevice(tdInsert);
        } catch (Exception e) {
            Log.v(m_TAG, "insertPttTestDevice Exception " + e);
        }
    }

    private void tryInsertPttTestDevice(PttTestDevice tdInsert) {
        Log.v(m_TAG, " tryInsertTestSet ");
        m_database = m_helper.getWritableDatabase();

        String strSQL = " INSERT INTO ptt_test_device (tg_id, td_phone, td_device)  "
                + " VALUES (?, ?, ?); ";
        Object[] params = {tdInsert.getTgId(), tdInsert.getTdPhone(), tdInsert.getTdDevice()};

        m_database.execSQL(strSQL, params);
    }

    public PttTestDevice selectPttTestDeviceFromTgId(int iTgId) {
        try {
            return trySelectPttTestDeviceFromTgId(iTgId);
        } catch (Exception e) {
            Log.v(m_TAG, "selectPttTestDevice Exception " + e);
            return null;
        }
    }

    public PttTestDevice trySelectPttTestDeviceFromTgId(int iTgId) {
        Log.v(m_TAG,
                String.format("trySelectPttTestDeviceFromTgId tg_id %d ", iTgId) );

        m_database = m_helper.getReadableDatabase();

        String strSQL = "  SELECT td_id, tg_id, td_phone, td_device "
                + " FROM ptt_test_device WHERE tg_id= ?; ";
        String[] params = { Integer.toString(iTgId) };
        Cursor cursor = m_database.rawQuery(strSQL, params);

        Log.v(m_TAG,
                String.format("trySelectPttTestDeviceFromTgId Result = %d", cursor.getCount()));
        if (cursor.getCount() > 0) {
            cursor.moveToNext();

            int     td_id      = cursor.getInt(0);
            int     tg_id      = cursor.getInt(1);
            String  td_phone    = cursor.getString(2);
            String  td_device  = cursor.getString(3);

            PttTestDevice tdOne = new PttTestDevice(td_id, tg_id, td_phone, td_device);
            Log.v(m_TAG, tdOne.toString());
            cursor.close();

            return tdOne;
        } else {
            return null;
        }
    }




}
