package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.HrTestUnit;
import com.example.hearfiss_01.db.DTO.StWord;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        insertAndSelectTestGroup();
        insertAndSelectTestSet();
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


        Log.v(m_TAG, "*********** insertAndSelectTestSet right ********** " + m_TestSetRight.toString());
        Log.v(m_TAG, "*********** insertAndSelectTestSet left ********** " + m_TestSetLeft.toString());
        m_TestSetLeft.setTg_id(m_TestGroup.getTg_id());
        hrTestDAO.insertTestSet(m_TestSetLeft);
        m_TestSetLeft = hrTestDAO.selectTestSet(m_TestSetLeft);

        m_TestSetRight.setTg_id(m_TestGroup.getTg_id());
        hrTestDAO.insertTestSet(m_TestSetRight);
        m_TestSetRight = hrTestDAO.selectTestSet(m_TestSetRight);

        Log.v(m_TAG, "*********** insertAndSelectTestSet right ********** " + m_TestSetRight.toString());
        Log.v(m_TAG, "*********** insertAndSelectTestSet left ********** " + m_TestSetLeft.toString());
    }



    public ArrayList<StWord> selectWordFromId(int _atId) {
        Log.v(m_TAG,
                String.format("selectWordFromId AtId %d", _atId));

        ArrayList<StWord> alWord = new ArrayList<>();

        try {
            m_database = m_helper.getReadableDatabase();

            String strSQL = "  SELECT sw_id, sw_word, at_id, sw_idx FROM sentence_word   "
                    + " WHERE at_id = ? ; ";
            String[] params = {Integer.toString(_atId)};
            Cursor cursor = m_database.rawQuery(strSQL, params);

            Log.v(m_TAG,
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
