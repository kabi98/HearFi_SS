package com.example.hearfiss_01.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.db.DTO.AmTrack;
import com.example.hearfiss_01.db.DTO.StWord;
import com.example.hearfiss_01.global.TConst;

import java.util.ArrayList;

public class SrsDAO {
    String m_TAG = "SrsDAO";
    SQLiteDatabase m_database;
    SQLiteHelper m_helper;

    Context m_Context;
    String m_strTestType;
    public SrsDAO(@Nullable Context _context) {
        m_Context = _context;
        m_helper = new SQLiteHelper(m_Context, TConst.DB_FILE, null, TConst.DB_VER);
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

    public ArrayList<StWord> selectWordListFromId(int _atId) {
        Log.v(m_TAG,
                String.format("selectWordFromId AtId %d", _atId));
        ArrayList<StWord> wordList = new ArrayList<>();
        try {
            m_database = m_helper.getReadableDatabase();

            String strSQL = "  SELECT sw_id, sw_word, at_id, sw_idx FROM sentence_word"
                    + " WHERE at_id = ? ; ";
            String[] params = {Integer.toString(_atId)};
            Cursor cursor = m_database.rawQuery(strSQL, params);

            Log.v(m_TAG,
                    String.format("selectWordFromId Result = %d", cursor.getCount()));

            if (cursor.getCount() <= 0)
                return new ArrayList<>();

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                int sw_id = cursor.getInt(0);
                String word = cursor.getString(1);
                int at_id = cursor.getInt(2);
                int sw_idx = cursor.getInt(3);

                StWord wordOne = new StWord(sw_id, word, at_id , sw_idx);
                wordList.add(wordOne);

                Log.v(m_TAG, wordOne.getSw_word());
            }
            cursor.close();
            return wordList;

        } catch (Exception e) {
            Log.v(m_TAG, "selectWordFromId Exception " + e);
            return null;
        }

    }
}
