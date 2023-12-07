package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.audioTest.SRS.SrsUnit;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.AmTrack;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.StWord;
import com.example.hearfiss_01.global.TConst;

import java.util.ArrayList;

public class SrsDAO {
    SQLiteDatabase m_database;
    SQLiteHelper m_helper;

    Context m_Context;
    HrTestGroup m_TestGroup;

    HrTestSet m_TestSetLeft, m_TestSetRight;
    Account m_Account;
    String m_TAG = "SrsDAO";

    String m_strGroupResult;
    String m_strTestType;
    public HrTestGroup getTestGroup(){
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


    public SrsDAO(@Nullable Context _context) {
        m_Context = _context;
        m_helper = new SQLiteHelper(m_Context, TConst.DB_FILE, null, TConst.DB_VER);

        m_Account = new Account();

        m_strTestType = TConst.STR_SRS_TYPE;
    }

    public void releaseAndClose(){
        Log.v(m_TAG,String.format("releaseAndClose"));
        try {
            m_database.close();
            m_helper.close();
        }catch (Exception e){
            Log.v(m_TAG, "releaseAndClose Exception " + e);
        }
    }

    public HrTestSet getTestSetLeft(){
        return m_TestSetLeft;
    }

    public HrTestSet getTestSetRight(){
        return m_TestSetRight;
    }

    public void saveResult(){
        Log.v(m_TAG, "saveSrsResult");

    }
    public ArrayList<AmTrack> selectTrackFromType(String _strType) {
        Log.v(m_TAG,
                String.format("selectTrackFromType Type %s", _strType));

        ArrayList<AmTrack> alTrack = new ArrayList<>();
        try {
            m_database = m_helper.getReadableDatabase();

            String strSQL = "  SELECT at_id, at_file_name, at_file_ext, at_type, at_content FROM audiometry_track "
                    + " WHERE at_type = ? ; ";
            String[] params = {_strType};
            Cursor cursor = m_database.rawQuery(strSQL, params);

            Log.v(m_TAG,
                    String.format("selectTrackFromType Result = %d", cursor.getCount()));
            if (cursor.getCount() <= 0)
                return null;

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String ext = cursor.getString(2);
                String type = cursor.getString(3);
                String content = cursor.getString(4);

                AmTrack atOne = new AmTrack(id, name, ext, type, content);
                alTrack.add(atOne);

                Log.v(m_TAG,
                        String.format("  selectTrackFromType \n name %s, ext %s, type %s, content %s ",
                                name, ext, type, content));
            }
            cursor.close();
            return alTrack;

        } catch (Exception e) {
            Log.v(m_TAG, "selectTrackFromType Exception " + e);
            return null;
        }
    }

    public ArrayList<StWord> selectWordFromId(int _atId) {
        Log.v(m_TAG,
                String.format("selectWordFromId AtId %d", _atId));

        ArrayList<StWord> alWord = new ArrayList<>();
        try {
            m_database = m_helper.getReadableDatabase();

            String strSQL = "  SELECT sw_id, sw_word, at_id, sw_idx FROM sentence_word"
                    + " WHERE at_id = ? ; ";
            String[] params = {Integer.toString(_atId)};
            Cursor cursor = m_database.rawQuery(strSQL, params);

            Log.v(m_TAG,
                    String.format("selectWordFromId Result = %d", cursor.getCount()));
            if (cursor.getCount() <= 0){
                Log.v(m_TAG, "selectWordFromId - No results found for _atId : " + _atId);
                return null;
            }

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                int sw_id = cursor.getInt(0);
                String word = cursor.getString(1);

                int at_id = cursor.getInt(2);
                int sw_idx = cursor.getInt(3);

                StWord wordOne = new StWord(sw_id, word, at_id , sw_idx);

                alWord.add(wordOne);

                Log.v(m_TAG,
                        String.format("  selectWordFromId \n sw_id %d, word %s, at_id %d sw_idx %d ",
                                sw_id, word, at_id, sw_idx));
            }
            cursor.close();
            return alWord;

        } catch (Exception e) {
            Log.v(m_TAG, "selectWordFromId Exception " + e);
            return null;
        }
    }
    public int insertTestUnit(SrsUnit tuInsert) {
        Log.v(m_TAG, " insertTestUnit ");
        try {
//            INSERT INTO hrtest_unit (ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES (1, '편지', '편지', 1, 60);
            m_database = m_helper.getWritableDatabase();
            String strSQL = " INSERT INTO srs_unit (ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) "
                    + " VALUES (?, ?, ?, ?, ?, ?); ";
            Object[] params = { tuInsert.getTs_id(), tuInsert.getTu_Question(), tuInsert.getTu_Answer(),
                    tuInsert.getTu_IsCorrect(), tuInsert.getTu_dBHL(), tuInsert.getTu_atId() };

            m_database.execSQL(strSQL, params);
        } catch (Exception e) {
            Log.v(m_TAG, "insertTestUnit Exception " + e);
            return -1;
        }

        return 1;
    }

    public int insertTestGroup(HrTestGroup tgInsert) {
        Log.v(m_TAG, " insertTestGroup ");
        try {
            m_database = m_helper.getWritableDatabase();
            String strSQL = " INSERT INTO hrtest_group (tg_date, tg_type, tg_result, acc_id)  "
                    + " VALUES (?, ?, ?, ?) ";
            Object[] params = {tgInsert.getTg_Date(), tgInsert.getTg_type(),tgInsert.getTg_result(), tgInsert.getAcc_id()};

            m_database.execSQL(strSQL, params);
        } catch (Exception e) {
            Log.v(m_TAG, "insertTestGroup Exception " + e);
            return -1;
        }

        return 1;
    }

    public HrTestGroup selectTestGroup(HrTestGroup tgInput) {
        Log.v(m_TAG,
                String.format("selectTestGroup Date %s, Type %s Result %s Id %d",
                        tgInput.getTg_Date(), tgInput.getTg_type(), tgInput.getTg_result(), tgInput.getAcc_id()));

        try {
            m_database = m_helper.getReadableDatabase();

            String strSQL = "  SELECT tg_id, tg_date, tg_type, acc_id "
                    + " FROM hrtest_group WHERE tg_date= ? and tg_type = ? and tg_result = ? and acc_id = ?; ";
            String[] params = { tgInput.getTg_Date(), tgInput.getTg_type(), tgInput.getTg_result(),
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

        } catch (Exception e) {
            Log.v("SQLiteControl", "selectTestGroup Exception " + e);
            return null;
        }
    }

    public int insertTestSet(HrTestSet tsInsert) {
        Log.v(m_TAG, " insertTestSet ");
        try {
            m_database = m_helper.getWritableDatabase();

            String strSQL = " INSERT INTO hrtest_set (tg_id, ts_side, ts_result, ts_comment)  "
                    + " VALUES (?, ?, ?, ?) ";
            Object[] params = {tsInsert.getTg_id(), tsInsert.getTs_side(),
                    tsInsert.getTs_Result(), tsInsert.getTs_Comment()};

            m_database.execSQL(strSQL, params);
        } catch (Exception e) {
            Log.v(m_TAG, "insertTestSet Exception " + e);
            return -1;
        }

        return 1;
    }

    public HrTestSet selectTestSet(HrTestSet tsInput) {
        Log.v(m_TAG,
                String.format("selectTestSet tg_id %d, ts_side %s ",
                        tsInput.getTg_id(), tsInput.getTs_side()));

        try {
            m_database = m_helper.getReadableDatabase();

            String strSQL = "  SELECT ts_id, tg_id, ts_side, ts_result, ts_comment "
                    + " FROM hrtest_set WHERE tg_id= ? and ts_side = ?; ";
            String[] params = { Integer.toString(tsInput.getTg_id()), tsInput.getTs_side() };
            Cursor cursor = m_database.rawQuery(strSQL, params);

            Log.v(m_TAG,
                    String.format("selectTestSet Result = %d", cursor.getCount()));
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

        } catch (Exception e) {
            Log.v(m_TAG, "selectTestSet Exception " + e);
            return null;
        }
    }
}
