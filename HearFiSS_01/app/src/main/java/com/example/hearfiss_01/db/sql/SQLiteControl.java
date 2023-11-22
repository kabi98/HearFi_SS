package com.example.hearfiss_01.db.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.AmTrack;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.HrTestUnit;
import com.example.hearfiss_01.db.DTO.StWord;

import java.util.ArrayList;

public class SQLiteControl {

    SQLiteHelper helper;
    SQLiteDatabase sqlite;

    public SQLiteControl(SQLiteHelper _helper) {
        this.helper = _helper;
    }

    public void db_close() {
        sqlite.close();
        helper.close();
    }

    public Account selectLogin(String strEmail, String strPwd) {
        Log.v("SQLiteControl",
                String.format("selectLogin Email %s, Pass %s", strEmail, strPwd));

//        try {
//            sqlite = helper.getReadableDatabase();
//
//            String strSQL = "  SELECT acc_id, acc_email, acc_name, acc_pwd, acc_gender, acc_birth "
//                    + " FROM account WHERE acc_email= ? and acc_pwd = ?; ";
//            String[] params = {strEmail, strPwd};
//            Cursor cursor = sqlite.rawQuery(strSQL, params);
//
//            Log.v("SQLiteControl",
//                    String.format("selectLogin Result = %d", cursor.getCount()));
//            if (cursor.getCount() > 0) {
//                cursor.moveToNext();
//
//                int     id      = cursor.getInt(0);
//                String  email   = cursor.getString(1);
//                String  name    = cursor.getString(2);
//                String  pwd     = cursor.getString(3);
//                String  gender  = cursor.getString(4);
//                String  birth   = cursor.getString(5);
//
//                Account accOne = new Account(id, email, name, pwd, gender, birth);
//
//                Log.v("SQLiteControl", accOne.toString());
//                cursor.close();
//
//                return accOne;
//            } else {
//                return null;
//            }
//
//        } catch (Exception e) {
//            Log.v("SQLiteControl", "selectLogin Exception " + e);
//            return null;
//        }
        return  null;
    }

    public ArrayList<AmTrack> selectTrackFromType(String _strType) {
        Log.v("SQLiteControl",
                String.format("selectTrackFromType Type %s", _strType));

        ArrayList<AmTrack> alTrack = new ArrayList<>();
        try {
            sqlite = helper.getReadableDatabase();

            String strSQL = "  SELECT at_id, at_file_name, at_file_ext, at_type, at_content FROM audiometry_track "
                    + " WHERE at_type = ? ; ";
            String[] params = {_strType};
            Cursor cursor = sqlite.rawQuery(strSQL, params);

            Log.v("SQLiteControl",
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

                Log.v("SQLiteControl",
                        String.format("  selectTrackFromType \n name %s, ext %s, type %s, content %s ",
                                name, ext, type, content));
            }
            cursor.close();
            return alTrack;

        } catch (Exception e) {
            Log.v("SQLiteControl", "selectTrackFromType Exception " + e);
            return null;
        }
    }

    public int insertAccount(Account acInsert) {
//        Log.v("SQLiteControl",
//                String.format("insertAccount email:%s, pwd:%s, name:%s, birth:%s",
//                        acInsert.getAcc_email(), acInsert.getAcc_pwd(), acInsert.getAcc_name(),
//                        acInsert.getAcc_birth() ));

//        try {
//            sqlite = helper.getWritableDatabase();
//
//            String strSQL = " INSERT INTO account (acc_email, acc_name, acc_pwd, acc_gender, acc_birth)  "
//                    + " VALUES (?, ?, ?, ?, ?) ";
//            Object[] params = { acInsert.getAcc_email(), acInsert.getAcc_name(), acInsert.getAcc_pwd(),
//                                acInsert.getAcc_gender(), acInsert.getAcc_birth()};
//
//
//            sqlite.execSQL(strSQL, params);
//        } catch (Exception e) {
//            Log.v("SQLiteControl", "insertAccount Exception " + e);
//            return -1;
//        }

        return 1;
    }

    public Account selectAccount(Account acInput) {
//        Log.v("SQLiteControl",
//                String.format("selectAccount email:%s, name:%s, id:%d",
//                        acInput.getAcc_email(), acInput.getAcc_name(), acInput.getAcc_id() ));
//
//        Account acSelect = null;
//        try {
//            sqlite = helper.getReadableDatabase();
//
//            String strSQL = " SELECT acc_id, acc_email, acc_name, acc_pwd, acc_gender, acc_birth "
//                    + " FROM account "
//                    + " WHERE acc_email = ? ";
//            String[] params = { acInput.getAcc_email() };
//            Cursor cursor = sqlite.rawQuery(strSQL, params);
//
//            if (cursor.getCount() <= 0) {
//                return null;
//            }
//
//            cursor.moveToNext();
//            int acc_id          = cursor.getInt(0);
//            String acc_email    = cursor.getString(1);
//            String acc_name     = cursor.getString(2);
//            String acc_pwd      = cursor.getString(3);
//            String acc_gender   = cursor.getString(4);
//            String acc_birth    = cursor.getString(5);
//            cursor.close();
//
//            acSelect = new Account(acc_id, acc_email, acc_name, acc_pwd, acc_gender, acc_birth);
//
//        } catch (Exception e) {
//            Log.v("SQLiteControl", "insertAccount Exception " + e);
//            return null;
//        }
          return null;
//        return acSelect;
    }

    public ArrayList<StWord> selectWordFromId(int _atId) {
        Log.v("SQLiteControl",
                String.format("selectWordFromId AtId %d", _atId));

        ArrayList<StWord> alWord = new ArrayList<>();
        try {
            sqlite = helper.getReadableDatabase();

            String strSQL = "  SELECT sw_id, sw_word, at_id, sw_idx FROM sentence_word   "
                    + " WHERE at_id = ? ; ";
            String[] params = {Integer.toString(_atId)};
            Cursor cursor = sqlite.rawQuery(strSQL, params);

            Log.v("SQLiteControl",
                    String.format("selectWordFromId Result = %d", cursor.getCount()));
            if (cursor.getCount() <= 0)
                return null;

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                int sw_id = cursor.getInt(0);
                String word = cursor.getString(1);

                int at_id = cursor.getInt(2);
                int sw_idx = cursor.getInt(3);

                StWord wordOne = new StWord(sw_id, word, at_id , sw_idx);

                alWord.add(wordOne);

                Log.v("SQLiteControl",
                        String.format("  selectWordFromId \n sw_id %d, word %s, at_id %d sw_idx %d ",
                                sw_id, word, at_id, sw_idx));
            }
            cursor.close();
            return alWord;

        } catch (Exception e) {
            Log.v("SQLiteControl", "selectWordFromId Exception " + e);
            return null;
        }
    }

    public ArrayList<HrTestSet> selectTestSetFromTgId(int Tg_id){
        ArrayList<HrTestSet> HrTestSet = new ArrayList<>();
        Log.v("SQLiteControl",
                String.format("selectTestSetFromLeftSide Id : %d", Tg_id));
        try{
            sqlite = helper.getReadableDatabase();
            String strSQL = "SELECT * FROM hrtest_set where tg_id = ? ;";
            String[] params = {Integer.toString(Tg_id)};
            Cursor cursor = sqlite.rawQuery(strSQL,params);
            Log.v("SQLiteControl",
                    String.format("selectTestGroup Result = %d", cursor.getCount()));
            if (cursor.getCount() <= 0) {
                return null;
            }
            for(int i=0; i<cursor.getCount(); i++){
                cursor.moveToNext();
                int ts_id = cursor.getInt(0);
                int tg_id = cursor.getInt(1);
                String ts_side = cursor.getString(2);
                String ts_result = cursor.getString(3);
                String ts_comment = cursor.getString(4);
                HrTestSet tsOne = new HrTestSet(ts_id,tg_id,ts_side,ts_result,ts_comment);
                HrTestSet.add(tsOne);
            }
            cursor.close();

            return HrTestSet;

        } catch (Exception e) {
            Log.v("SQLiteControl", "selectTestGroup Exception " + e);
            return null;
        }
    }
    public ArrayList<HrTestGroup> selectTestGroupFromId(int acc_id){
        ArrayList<HrTestGroup> HrTestGroup = new ArrayList<>();
        Log.v("SQLiteControl",
                String.format("selectTestGroupFromID Id : %d", acc_id));
        try{
            sqlite = helper.getReadableDatabase();
            String strSQL = "  SELECT tg_id, tg_date, tg_type, acc_id "
                    + " FROM hrtest_group WHERE acc_id = ? " + " ORDER BY tg_id desc  ;";
            String[] params = {Integer.toString(acc_id)};
            Cursor cursor = sqlite.rawQuery(strSQL,params);
            Log.v("SQLiteControl",
                    String.format("selectTestGroup Result = %d", cursor.getCount()));
            if (cursor.getCount() <= 0)
                return null;
            for(int i=0; i<cursor.getCount(); i++) {

                cursor.moveToNext();
                int tg_id = cursor.getInt(0);
                String tg_date = cursor.getString(1);
                String tg_type = cursor.getString(2);
                int tgAcc_id = cursor.getInt(3);
                HrTestGroup tgOne = new HrTestGroup(tg_id, tg_date, tg_type, " ", tgAcc_id);
                Log.v("SQLiteControl", tgOne.toString());
                HrTestGroup.add(tgOne);

            }
            cursor.close();

            return HrTestGroup;
        } catch (Exception e) {
            Log.v("SQLiteControl", "selectTestGroup Exception " + e);
            return null;
        }

    }
    public HrTestGroup selectTestGroup(HrTestGroup tgInput) {
        Log.v("SQLiteControl",
                String.format("selectTestGroup Date %s, Type %s Id %d",
                        tgInput.getTg_Date(), tgInput.getTg_type(), tgInput.getAcc_id()));

        try {
            sqlite = helper.getReadableDatabase();

            String strSQL = "  SELECT tg_id, tg_date, tg_type, tg_result, acc_id "
                    + " FROM hrtest_group WHERE tg_date= ? and tg_type = ? and acc_id = ?; ";
            String[] params = { tgInput.getTg_Date(), tgInput.getTg_type(),
                    Integer.toString(tgInput.getAcc_id())};
            Cursor cursor = sqlite.rawQuery(strSQL, params);

            Log.v("SQLiteControl",
                    String.format("selectTestGroup Result = %d", cursor.getCount()));
            if (cursor.getCount() > 0) {
                cursor.moveToNext();

                int     tg_id      = cursor.getInt(0);
                String  tg_date    = cursor.getString(1);
                String  tg_type    = cursor.getString(2);
                String  tg_result  = cursor.getString(3);
                int     acc_id     = cursor.getInt(4);

                HrTestGroup tgOne = new HrTestGroup(tg_id, tg_date, tg_type, tg_result, acc_id);

                Log.v("SQLiteControl", tgOne.toString());
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

    public int insertTestGroup(HrTestGroup tgInsert) {
        Log.v("SQLiteControl", " insertTestGroup ");
        try {
            sqlite = helper.getWritableDatabase();
            String strSQL = " INSERT INTO hrtest_group (tg_date, tg_type, acc_id)  "
                    + " VALUES (?, ?, ?) ";
            Object[] params = {tgInsert.getTg_Date(), tgInsert.getTg_type(), tgInsert.getAcc_id()};

            sqlite.execSQL(strSQL, params);
        } catch (Exception e) {
            Log.v("SQLiteControl", "insertTestGroup Exception " + e);
            return -1;
        }

        return 1;
    }

    public HrTestSet selectTestSet(HrTestSet tsInput) {
        Log.v("SQLiteControl",
                String.format("selectTestSet tg_id %d, ts_side %s ",
                        tsInput.getTg_id(), tsInput.getTs_side()));

        try {
            sqlite = helper.getReadableDatabase();

            String strSQL = "  SELECT ts_id, tg_id, ts_side, ts_result, ts_comment "
                    + " FROM hrtest_set WHERE tg_id= ? and ts_side = ?; ";
            String[] params = { Integer.toString(tsInput.getTg_id()), tsInput.getTs_side() };
            Cursor cursor = sqlite.rawQuery(strSQL, params);

            Log.v("SQLiteControl",
                    String.format("selectTestSet Result = %d", cursor.getCount()));
            if (cursor.getCount() > 0) {
                cursor.moveToNext();

                int     ts_id      = cursor.getInt(0);
                int     tg_id      = cursor.getInt(1);
                String  tg_side    = cursor.getString(2);
                String  tg_result  = cursor.getString(3);
                String  tg_comment = cursor.getString(4);

                HrTestSet tsOne = new HrTestSet(ts_id, tg_id, tg_side, tg_result, tg_comment);
                Log.v("SQLiteControl", tsOne.toString());
                cursor.close();

                return tsOne;
            } else {
                return null;
            }

        } catch (Exception e) {
            Log.v("SQLiteControl", "selectTestSet Exception " + e);
            return null;
        }
    }



    public int insertTestSet(HrTestSet tsInsert) {
        Log.v("SQLiteControl", " insertTestSet ");
        try {
            sqlite = helper.getWritableDatabase();

            String strSQL = " INSERT INTO hrtest_set (tg_id, ts_side, ts_result, ts_comment)  "
                    + " VALUES (?, ?, ?, ?) ";
            Object[] params = {tsInsert.getTg_id(), tsInsert.getTs_side(),
                    tsInsert.getTs_Result(), tsInsert.getTs_Comment()};

            sqlite.execSQL(strSQL, params);
        } catch (Exception e) {
            Log.v("SQLiteControl", "insertTestSet Exception " + e);
            return -1;
        }

        return 1;
    }


    public int updateTestSetFromTsid(HrTestSet tsInsert) {
        Log.v("SQLiteControl", " updateTestSetFromTsid ");
        try {
            sqlite = helper.getWritableDatabase();
            String strSQL = " UPDATE hrtest_set "
                    + " SET  ts_result = ?, ts_comment = ? "
                    + " WHERE ts_id = ? ; ";
            Object[] params = { tsInsert.getTs_Result(), tsInsert.getTs_Comment(),
                    Integer.toString(tsInsert.getTs_id()) };

            sqlite.execSQL(strSQL, params);
        } catch (Exception e) {
            Log.v("SQLiteControl", "updateTestSetFromTsid Exception " + e);
            return -1;
        }

        return 1;

    }


    public int insertTestUnit(HrTestUnit tuInsert) {
        Log.v("SQLiteControl", " insertTestUnit ");
        try {
//            INSERT INTO hrtest_unit (ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES (1, '편지', '편지', 1, 60);
            sqlite = helper.getWritableDatabase();
            String strSQL = " INSERT INTO hrtest_unit (ts_id, tu_question, tu_answer, tu_iscorrect) "
                    + " VALUES (?, ?, ?, ?); ";
            Object[] params = { tuInsert.getTs_id(), tuInsert.getTu_Question(), tuInsert.getTu_Answer(),
                    tuInsert.getTu_IsCorrect() };

            sqlite.execSQL(strSQL, params);
        } catch (Exception e) {
            Log.v("SQLiteControl", "insertTestUnit Exception " + e);
            return -1;
        }

        return 1;
    }

    public ArrayList<HrTestUnit> selectTestUnitFromTsId(int _tsId) {
        Log.v("SQLiteControl",
                String.format("selectTestUnitFromTsId AtId %d", _tsId));

        ArrayList<HrTestUnit> alTestUnit = new ArrayList<>();
        try {
            sqlite = helper.getReadableDatabase();

            String strSQL = " SELECT tu_id, ts_id, tu_question, tu_answer, tu_iscorrect "
                    + " FROM hrtest_unit WHERE ts_id = ?; ";
            String[] params = {Integer.toString(_tsId)};
            Cursor cursor = sqlite.rawQuery(strSQL, params);

            Log.v("SQLiteControl",
                    String.format("selectTestUnitFromTsId Result = %d", cursor.getCount()));
            if (cursor.getCount() <= 0)
                return null;

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                int tu_id = cursor.getInt(0);
                int ts_id = cursor.getInt(1);
                String tu_question = cursor.getString(2);
                String tu_answer = cursor.getString(3);
                int tu_iscorrect = cursor.getInt(4);


                HrTestUnit tuOne = new HrTestUnit(tu_id, ts_id, tu_question, tu_answer, tu_iscorrect);
                alTestUnit.add(tuOne);

                Log.v("SQLiteControl", tuOne.toString());
            }
            cursor.close();
            return alTestUnit;

        } catch (Exception e) {
            Log.v("SQLiteControl", "selectTestUnitFromTsId Exception " + e);
            return null;
        }
    }

    public ArrayList<HrTestUnit> selectTestUnitFromTsId_2(int _tsId) {
        Log.v("SQLiteControl",
                String.format("selectTestUnitFromTsId AtId %d", _tsId));

        ArrayList<HrTestUnit> alTestUnit = new ArrayList<>();
        try {
            sqlite = helper.getReadableDatabase();

            String strSQL = " SELECT tu_id, ts_id, tu_question, tu_answer, tu_iscorrect "
                    + " FROM hrtest_unit WHERE ts_id = ? ORDER BY tu_iscorrect; ";
            String[] params = {Integer.toString(_tsId)};
            Cursor cursor = sqlite.rawQuery(strSQL, params);

            Log.v("SQLiteControl",
                    String.format("selectTestUnitFromTsId Result = %d", cursor.getCount()));
            if (cursor.getCount() <= 0)
                return null;

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                int tu_id = cursor.getInt(0);
                int ts_id = cursor.getInt(1);
                String tu_question = cursor.getString(2);
                String tu_answer = cursor.getString(3);
                int tu_iscorrect = cursor.getInt(4);

                HrTestUnit tuOne = new HrTestUnit(tu_id, ts_id, tu_question, tu_answer, tu_iscorrect);
                alTestUnit.add(tuOne);

                Log.v("SQLiteControl", tuOne.toString());
            }
            cursor.close();
            return alTestUnit;

        } catch (Exception e) {
            Log.v("SQLiteControl", "selectTestUnitFromTsId Exception " + e);
            return null;
        }
    }


}