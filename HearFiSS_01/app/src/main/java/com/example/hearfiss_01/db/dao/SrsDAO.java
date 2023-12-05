package com.example.hearfiss_01.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.HrTestUnit;
import com.example.hearfiss_01.db.DTO.StWord;
import com.example.hearfiss_01.global.GlobalVar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SrsDAO {
    SQLiteDatabase sqlite;
    SQLiteHelper helper;

    public SrsDAO(SQLiteHelper helper) {

    }

    public ArrayList<StWord> selectWordFromId(int _atId) {
        Log.v("SQLiteControl",
                String.format("selectWordFromId AtId %d", _atId));

        ArrayList<StWord> alWord = new ArrayList<>();
        /*
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

                StWord wordOne = new StWord(sw_id, word, at_id, sw_idx);

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
         */
        // temp return value
        return null;
    }

    public HrTestGroup dataGroupInsert() {
        /*
        Date dtNow = new Date();
        // ex) 2023-08-10 16:22:22 포멧팅
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 문자형으로 날짜 포맷팅
        String strDate = sdFormatter.format(dtNow);
        HrTestGroup hrTestGroup = new HrTestGroup(0, strDate, GlobalVar.g_MenuType, GlobalVar.g_AccLogin.getAcc_id());
        sqlcon.insertTestGroup(hrTestGroup);
        m_testGroup = sqlcon.selectTestGroup(hrTestGroup);
        GlobalVar.g_TestGroup = m_testGroup;
        Log.v(aName, "insertData : " + m_testGroup.toString());
        return m_testGroup;
    }
         */
        // 임시 리턴값
        return dataGroupInsert();
    }

    public HrTestSet insertSet(int wResult, int sResult) {
/*
        if(GlobalVar.g_MenuSide.equals("RIGHT")){
            m_testGroup = dataGroupInsert();
        }
        hrTestSet = new HrTestSet();

        // HrTestSet 수정 필요
        //hrTestSet.setTs_Date(strDate); // 테스트 날짜
        //hrTestSet.setTs_type(GlobalVar.g_MenuType); //테스트 타입

        hrTestSet.setTg_id(GlobalVar.g_TestGroup.getTg_id());
        hrTestSet.setTs_side(GlobalVar.g_MenuSide); // 테스트 방향
        hrTestSet.setTs_Result("단어 기준 : " +Integer.toString(wResult) +"%"); // 단어 기준 점수
        hrTestSet.setTs_Comment("문장 기준 : " +Integer.toString(sResult) +"%"); // 문장 기준 점수
        sqlcon.insertTestSet(hrTestSet);
        HrTestSet m_testset = sqlcon.selectTestSet(hrTestSet);
        Log.v(aName,"insertData : " + m_testset.toString());
        return m_testset;
    }

 */
        // 임시 리턴값
        return null;
    }

    public void insertUnit(ArrayList<HrTestUnit> unitList, HrTestSet srsTestSet){
/*
        int row = 0;
        for(int i=0; i<unitList.size(); i++){
            unitList.get(i).setTs_id(srsTestSet.getTs_id());
            int insert = sqlcon.insertTestUnit(unitList.get(i));
            row += insert;

            Log.v(aName, "insertData" + unitList.get(i).toString());
        }
        Log.v("insertUnit", "cnt : " + row);
    }
    */
    }
}
