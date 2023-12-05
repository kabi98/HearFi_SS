package com.example.hearfiss_01.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hearfiss_01.db.DTO.StWord;

import java.util.ArrayList;

public class SrsDAO {
    SQLiteDatabase sqlite;
    SQLiteHelper helper;

    public SrsDAO(SQLiteHelper helper) {

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
}
