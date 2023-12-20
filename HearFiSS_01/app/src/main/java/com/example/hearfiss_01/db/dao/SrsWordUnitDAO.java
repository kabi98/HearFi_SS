package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.db.DTO.SrsWordUnit;
import com.example.hearfiss_01.global.TConst;

public class SrsWordUnitDAO {

    String m_TAG = "SrsWordUnitDAO";

    Context m_Context;

    SQLiteHelper m_helper;

    SQLiteDatabase m_database;

    public SrsWordUnitDAO(SQLiteHelper m_helper){
        this.m_helper = m_helper;
    }

    public SrsWordUnitDAO(@Nullable Context _context){
        m_Context = _context;
        m_helper = new SQLiteHelper(m_Context, TConst.DB_FILE, null, TConst.DB_VER);
    }

    public void releaseAndClose(){
        Log.v(m_TAG, "releaseAndClose");

        try {
            m_database.close();
            m_helper.close();
        }catch (Exception e){
            Log.v(m_TAG, "releaseAndClose Exception : "+e);
        }
    }

    public void insertSrsWordUnit(SrsWordUnit swInsert){
        Log.v(m_TAG,"insertSrsWordUnit");
        try {
            tryInsertSrsWordUnit(swInsert);
        }catch (Exception e){
            Log.v(m_TAG, "insertSrsWordUnit Exception "+e);
        }
    }

    private void tryInsertSrsWordUnit(SrsWordUnit swIns) {
        m_database = m_helper.getWritableDatabase();
        String strSQL =
                " INSERT INTO srs_word_unit (tu_id, su_question, su_answer, su_iscorrect, su_idx)  "
                        + " VALUES (?, ?, ?, ?, ?) ";
        Object[] params = {swIns.getTu_id(), swIns.getSu_question(),
                swIns.getSu_question(), swIns.getSu_answer(), swIns.getSu_iscorrect(), swIns.getSu_idx()};

        m_database.execSQL(strSQL, params);
    }

    public SrsWordUnit selectSrsWordUnit(SrsWordUnit swInput){
        try {
            return trySelectSrsWordUnit(swInput);
        }catch (Exception e){
            Log.v(m_TAG,"selectSrsWordUnit Exception" +e);
            return null;
        }
    }
    private SrsWordUnit trySelectSrsWordUnit(SrsWordUnit swInput) {
        m_database = m_helper.getReadableDatabase();

        String strSQL = "  SELECT su_id, tu_id, su_question, su_answer, su_iscorrect, su_idx "
                + " FROM srs_word_unit WHERE tu_id= ? and su_question = ?; ";
        String[] params = {Integer.toString(swInput.getTu_id()),swInput.getSu_question()};
        Cursor cursor = m_database.rawQuery(strSQL, params);

        Log.v(m_TAG,
                String.format("selectSrsWordUnit Result = %d", cursor.getCount()));
        if (cursor.getCount() > 0) {
            cursor.moveToNext();

            int     su_id      = cursor.getInt(0);
            int     tu_id      = cursor.getInt(1);
            String  su_question    = cursor.getString(2);
            String  su_answer    = cursor.getString(3);
            int     su_iscorrect  = cursor.getInt(4);
            int     su_idx     = cursor.getInt(5);

            SrsWordUnit swOne = new SrsWordUnit(su_id, tu_id, su_question, su_answer, su_iscorrect, su_idx);

            Log.v(m_TAG, swOne.toString());
            cursor.close();

            return swOne;
        } else {
            return null;
        }
    }

    public SrsWordUnit selectSrsWordUnitFromTestUnit(int iTuId, String suQuestion){
        Log.v(m_TAG,
                String.format("selectSrsWordUnitFromTestUnit ID : %d", iTuId));

        try {
            return trySelectSrsWordUnitFromTestUnit(iTuId, suQuestion);
        }catch (Exception e){
            Log.v(m_TAG, "selectSrsWordUnitFromTestUnit Exception : "+e);
            return null;
        }
    }

    private SrsWordUnit trySelectSrsWordUnitFromTestUnit(int iTuId, String suQuestion) {
        m_database = m_helper.getReadableDatabase();

        String strSQL = "  SELECT su_id, tu_id, su_question, su_answer, su_iscorrect, su_idx "
                + " FROM srs_word_unit WHERE tu_id = ? and su_question = ?; ";
        String[] params = { Integer.toString(iTuId), suQuestion };
        Cursor cursor = m_database.rawQuery(strSQL, params);

        Log.v(m_TAG,
                String.format("trySelectSrsWordUnitFromTestUnit Result = %d", cursor.getCount()));
        if (cursor.getCount() > 0) {
            cursor.moveToNext();

            int     su_id      = cursor.getInt(0);
            int     tu_id      = cursor.getInt(1);
            String  su_question    = cursor.getString(2);
            String  su_answer  = cursor.getString(3);
            int     su_iscorrect = cursor.getInt(4);
            int     su_idx      = cursor.getInt(5);

            SrsWordUnit swOne = new SrsWordUnit(su_id, tu_id, su_question, su_answer, su_iscorrect, su_idx);
            Log.v(m_TAG, swOne.toString());
            cursor.close();

            return swOne;
        } else {
            return null;
        }
    }


}
