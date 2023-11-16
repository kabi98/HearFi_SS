package com.example.hearfi_03.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfi_03.db.DTO.AmTrack;
import com.example.hearfi_03.global.RandomBag;
import com.example.hearfi_03.global.TConst;

import java.util.ArrayList;

public class RandomTrack {
    String m_TAG = "RandomTrack";

    Context m_Context;
    SQLiteDatabase m_database;
    SQLiteHelper m_helper = null;
    ArrayList<AmTrack> m_alTrack = null;
    String m_Type = "";

    public RandomTrack(@Nullable Context context) {
        Log.v(m_TAG, "RandomTrack()");

        m_Context = context;

        m_helper = new SQLiteHelper(m_Context,  TConst.DB_FILE, null, TConst.DB_VER);
        m_alTrack = new ArrayList<>();
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

    public String getM_Type() {
        Log.v(m_TAG, "getM_Type : " + m_Type);
        return m_Type;
    }

    public void setM_Type(String m_Type) {
        Log.v(m_TAG, "setM_Type : " + m_Type);

        this.m_Type = m_Type;
        filling();
    }

    public void filling(){

        ArrayList<AmTrack> alTemp = selectTrackFromType(m_Type);

        RandomBag rbTemp = new RandomBag();
        rbTemp.setiLimit(alTemp.size());

        m_alTrack.clear();

        for(int i=0; i < alTemp.size(); i++) {
            m_alTrack.add(alTemp.get(rbTemp.getNext()));
        }

        printTrackContent();
    }

    public AmTrack getNext(){
        if(m_alTrack.size() <= 0)
            filling();
        return m_alTrack.remove(0);
    }

    public int size(){
        return m_alTrack.size();
    }

    public void printTrackContent() {
        Log.v(m_TAG, "printTrackContent : ");
        StringBuilder sbTemp = new StringBuilder();
        for(int i=0; i<m_alTrack.size(); i++){
            sbTemp.append( String.format(" %s,", m_alTrack.get(i).getAt_content()) ) ;
        }

        Log.v(m_TAG, sbTemp.toString());
        Log.v(m_TAG, "printTrackContent() Finish -----------------------------------------");
    }
    public ArrayList<AmTrack> selectTrackFromType(String _strType) {
        Log.v(m_TAG,
                String.format("selectTrackFromType Type %s", _strType));

        try {
            return trySelectTrackFromType(_strType);

        } catch (Exception e) {
            Log.v(m_TAG, "selectTrackFromType Exception " + e);
            return null;

        }
    }

    public ArrayList<AmTrack> trySelectTrackFromType(String _strType) {
        ArrayList<AmTrack> alTrack = new ArrayList<>();
        m_database = m_helper.getReadableDatabase();

        String strSQL = "  SELECT at_id, at_file_name, at_file_ext, at_type, at_content "
                + " FROM audiometry_track "
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
    }


}
