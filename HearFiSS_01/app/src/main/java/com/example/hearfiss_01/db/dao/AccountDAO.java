package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.global.TConst;

public class AccountDAO {
    String m_TAG = "AccountDAO";

    Context m_Context;

    SQLiteHelper m_helper;
    SQLiteDatabase m_database;

    public AccountDAO(@Nullable Context _context) {
        m_Context = _context;
        m_helper = new SQLiteHelper(m_Context,  TConst.DB_FILE, null, TConst.DB_VER);
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

    public Account selectLogin(String strPhoneId, String strPwd) {
        Log.v(m_TAG,
                String.format("selectLogin strPhone %s, Pass %s", strPhoneId, strPwd));

        try {
            return trySelectLogin(strPhoneId, strPwd);

        } catch (Exception e) {
            Log.v(m_TAG, "selectLogin Exception " + e);
            return null;
        }
    }

    public Account trySelectLogin(String strPhoneId, String strPwd) {
        m_database = m_helper.getReadableDatabase();

        String strSQL = "  SELECT acc_id, acc_phone_id, acc_phone, acc_pwd, acc_name, acc_gender, acc_birth "
                + " FROM account WHERE acc_phone_id= ? and acc_pwd = ?; ";
        String[] params = {strPhoneId, strPwd};
        Cursor cursor = m_database.rawQuery(strSQL, params);

        Log.v(m_TAG,
                String.format("trySelectLogin Result = %d", cursor.getCount()));
        if (cursor.getCount() > 0) {
            cursor.moveToNext();

            int id = cursor.getInt(0);
            String phone_id = cursor.getString(1);
            String phone = cursor.getString(2);
            String pwd = cursor.getString(3);
            String name = cursor.getString(4);
            String gender = cursor.getString(5);
            String birth = cursor.getString(6);

            Account accOne = new Account(id, phone_id, phone, pwd, name, gender, birth);

            Log.v(m_TAG, accOne.toString());
            cursor.close();

            return accOne;
        } else {
            return null;
        }
    }

    public int insertAccount(Account acInsert) {
        Log.v(m_TAG,
                String.format("insertAccount acInsert : %s ", acInsert.toString()));

        try {
            m_database = m_helper.getWritableDatabase();

            String strSQL = " INSERT INTO account (acc_phone_id, acc_phone, acc_pwd, acc_name, acc_gender, acc_birth)  "
                    + " VALUES (?, ?, ?, ?, ?, ?) ";
            Object[] params = {acInsert.getAcc_phone_id(), acInsert.getAcc_phone(),
                    acInsert.getAcc_pwd(), acInsert.getAcc_name(),
                    acInsert.getAcc_gender(), acInsert.getAcc_birth()};

            m_database.execSQL(strSQL, params);

        } catch (Exception e) {
            Log.v(m_TAG, "insertAccount Exception " + e);
            return -1;
        }

        return 1;
    }

}