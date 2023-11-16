package com.example.hearfi_03.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfi_03.db.DTO.HrTestGroup;
import com.example.hearfi_03.db.DTO.HrTestSet;
import com.example.hearfi_03.global.GlobalVar;
import com.example.hearfi_03.global.TConst;

import java.util.ArrayList;

public class MyTest {
    String m_TAG = "MyTest";

    SQLiteDatabase m_database;
    SQLiteHelper m_helper;
    Context m_Context = null;
    String m_PackageName = "";
    ArrayList<HrTestGroup> groups = new ArrayList<>();
    ArrayList<HrTestSet> sets = new ArrayList<>();
    public MyTest(@Nullable Context context){
        this.m_Context = context;
        this.m_PackageName = m_Context.getPackageName();

        m_helper = new SQLiteHelper(m_Context,  TConst.DB_FILE, null, TConst.DB_VER);
    }

    public void closeDatabase(){
        Log.v(m_TAG,
                String.format("closeDatabase"));
        try {
            m_database.close();
            m_helper.close();
        } catch (Exception e) {
            Log.v(m_TAG, "closeDatabase Exception " + e);
        }

    }

    public ArrayList<HrTestGroup> searchHrTestGroup(){
        Log.v(m_TAG, "USER : "+GlobalVar.g_AccLogin.getAcc_id());
        int acc_id = GlobalVar.g_AccLogin.getAcc_id();
        groups = selectTestGroupFromId(acc_id);
        if(groups != null){
            return  groups;
        }else{
            return null;
        }
    }

    public ArrayList<HrTestSet> searchHrTestSet(int _Tg_id){
        sets = selectTestSetFromTgId(_Tg_id);
        if(sets!=null){
            return sets;
        }else{
            return null;
        }

    }

    public ArrayList<HrTestSet> selectTestSetFromTgId(int Tg_id) {
        ArrayList<HrTestSet> HrTestSet = new ArrayList<>();
        Log.v(m_TAG,
                String.format("selectTestSetFromLeftSide Id : %d", Tg_id));
        try {
            m_database = m_helper.getReadableDatabase();
            String strSQL = "SELECT * FROM hrtest_set where tg_id = ? ;";
            String[] params = {Integer.toString(Tg_id)};
            Cursor cursor = m_database.rawQuery(strSQL, params);
            Log.v(m_TAG,
                    String.format("selectTestGroup Result = %d", cursor.getCount()));
            if (cursor.getCount() <= 0) {
                return null;
            }
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                int ts_id = cursor.getInt(0);
                int tg_id = cursor.getInt(1);
                String ts_side = cursor.getString(2);
                String ts_result = cursor.getString(3);
                String ts_comment = cursor.getString(4);
                HrTestSet tsOne = new HrTestSet(ts_id, tg_id, ts_side, ts_result, ts_comment);
                HrTestSet.add(tsOne);
            }
            cursor.close();

            return HrTestSet;

        } catch (Exception e) {
            Log.v(m_TAG, "selectTestGroup Exception " + e);
            return null;
        }
    }

    public ArrayList<HrTestGroup> selectTestGroupFromId(int acc_id) {
        ArrayList<HrTestGroup> HrTestGroup = new ArrayList<>();
        Log.v(m_TAG,
                String.format("selectTestGroupFromID Id : %d", acc_id));
        try {
            m_database = m_helper.getReadableDatabase();
            String strSQL = "  SELECT tg_id, tg_date, tg_type, tg_result, acc_id "
                    + " FROM hrtest_group WHERE acc_id = ? "
                    + " ORDER BY tg_id desc  ;";
            String[] params = {Integer.toString(acc_id)};
            Cursor cursor = m_database.rawQuery(strSQL, params);
            Log.v(m_TAG,
                    String.format("selectTestGroup Result = %d", cursor.getCount()));
            if (cursor.getCount() <= 0)
                return null;
            for (int i = 0; i < cursor.getCount(); i++) {

                cursor.moveToNext();
                int tg_id = cursor.getInt(0);
                String tg_date = cursor.getString(1);
                String tg_type = cursor.getString(2);
                String tg_result = cursor.getString(3);
                int tgAcc_id = cursor.getInt(4);
                HrTestGroup tgOne = new HrTestGroup(tg_id, tg_date, tg_type, tg_result, tgAcc_id);
                Log.v(m_TAG, tgOne.toString());
                HrTestGroup.add(tgOne);

            }
            cursor.close();

            return HrTestGroup;
        } catch (Exception e) {
            Log.v(m_TAG, "selectTestGroup Exception " + e);
            return null;
        }

    }

}
