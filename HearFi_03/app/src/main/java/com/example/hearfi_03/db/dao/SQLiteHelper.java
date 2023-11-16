package com.example.hearfi_03.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfi_03.global.TConst;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLiteHelper extends android.database.sqlite.SQLiteOpenHelper{

    Context m_Context = null;
    String m_TAG = "SQLiteHelper";

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.v(m_TAG, "SQLiteHelper() ver : " + version);
        this.m_Context = context;

        checkDatabaseAndCopy();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(m_TAG, "onCreate()");

        String sql_Temp = "";


        sql_Temp = " CREATE TABLE IF NOT EXISTS account ( "
                + " acc_id INTEGER PRIMARY KEY  AUTOINCREMENT "
                + " , acc_email TEXT "
                + " , acc_name TEXT "
                + " , acc_pwd TEXT "
                + " , acc_gender TEXT "
                + " , acc_birth DATETIME "
                + " ); ";

        db.execSQL(sql_Temp);
        sql_Temp = " CREATE TABLE IF NOT EXISTS hrtest_set ( "
                + " ts_id INTEGER PRIMARY KEY  AUTOINCREMENT "
                + " , ts_date DATETIME  DEFAULT (datetime(\'now\', \'localtime\')) "
                + " , ts_type TEXT "
                + " , ts_side TEXT "
                + " , acc_id INTEGER "
                + " , ts_result TEXT "
                + " , ts_comment TEXT "
                + " , FOREIGN KEY(acc_id) REFERENCES account(acc_id) "
                + " ); ";

        db.execSQL(sql_Temp);

        sql_Temp = " CREATE TABLE IF NOT EXISTS hrtest_unit ( "
                + " tu_id INTEGER PRIMARY KEY  AUTOINCREMENT "
                + " , ts_id INTEGER "
                + " , tu_question TEXT "
                + " , tu_answer TEXT "
                + " , tu_iscorrect INTEGER "
                + " , tu_dbHL INTEGER "
                + " , FOREIGN KEY(ts_id) REFERENCES hrtest_set(ts_id) "
                + " ); ";

        db.execSQL(sql_Temp);

        sql_Temp = " CREATE TABLE IF NOT EXISTS audiometry_track ( "
                + " at_id INTEGER PRIMARY KEY  AUTOINCREMENT "
                + " , at_file_name TEXT "
                + " , at_file_ext TEXT "
                + " , at_type TEXT "
                + " , at_content TEXT "
                + " ); ";

        db.execSQL(sql_Temp);

        sql_Temp = " CREATE TABLE IF NOT EXISTS sentence_unit ( "
                + " su_id INTEGER PRIMARY KEY  AUTOINCREMENT "
                + " , su_sentence TEXT "
                + " , at_id INTEGER "
                + " , FOREIGN KEY(at_id) REFERENCES audometry_track(at_id) "
                + " ); ";

        db.execSQL(sql_Temp);

        sql_Temp = " CREATE TABLE IF NOT EXISTS sentence_word ( "
                + " sw_id INTEGER PRIMARY KEY  AUTOINCREMENT "
                + " , sw_word_no INTEGER "
                + " , sw_word TEXT "
                + " , su_id INTEGER "
                + " , FOREIGN KEY(su_id) REFERENCES sentence_unit(su_id) "
                + " ); ";

        db.execSQL(sql_Temp);

        Log.v("SQLiteHelper", "onCreate() - Finish");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(m_TAG, "onUpgrade() old : " + oldVersion
                + " new : " + newVersion);

        db.execSQL(" drop table IF EXISTS ptt_test_unit; ");
        db.execSQL(" drop table IF EXISTS pt_track; ");
        db.execSQL(" drop table IF EXISTS sentence_word; ");
        db.execSQL(" drop table IF EXISTS audiometry_unit; ");
        db.execSQL(" drop table IF EXISTS audiometry_set; ");

        db.execSQL(" drop table IF EXISTS hrtest_unit; ");
        db.execSQL(" drop table IF EXISTS hrtest_set; ");
        db.execSQL(" drop table IF EXISTS hrtest_group; ");

        db.execSQL(" drop table IF EXISTS sentence_unit; ");
        db.execSQL(" drop table IF EXISTS audiometry_track; ");
        db.execSQL(" drop table IF EXISTS account; ");

        onCreate(db);

        Log.v("SQLiteHelper", "onUpgrade() - Finish");

    }

    //------------------------------------ DATABASES FOLDERS FILE CHECK -------------------------//
    private void checkDatabaseAndCopy(){
        // 해당 폴더(경로 : DATABASES 폴더 하위에 DATABASE_NAME과 같은 데이터베이스가 있는지 체크
        String DB_PATH = "/data/data/" + m_Context.getPackageName() + "/databases/";
        File dbFile = new File(DB_PATH + TConst.DB_FILE);
        if(!dbFile.exists()){
            // 파일이 존재 하지 않을 때 dbCopy 메소드를 실행
            copyDatabaseFileFromAssets();
            // 복사 완료후 Log로 확인
            Log.v(m_TAG,"DataBase is Copied");
        }
    }

    //------------------------------------ DATABASES COPY METHOD ---------------------------------//
    private void copyDatabaseFileFromAssets(){

        try{
            String DB_PATH = "/data/data/" + m_Context.getPackageName() + "/databases/";
            File folder = new File(DB_PATH);
            if(!folder.exists()){
                folder.mkdir();
            }

            InputStream inputStream = m_Context.getAssets().open(TConst.DB_FILE);
            String out_filename =DB_PATH+TConst.DB_FILE;
            OutputStream outputStream = new FileOutputStream(out_filename);

            int iLen;
            byte[] byteBuf = new byte[inputStream.available()];
            while((iLen = inputStream.read(byteBuf)) != -1) {
                outputStream.write(byteBuf, 0, iLen);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            outputStream = null;
            inputStream = null;

        } catch (IOException e) {
            Log.v(m_TAG,"copyDatabaseFileFromAssets Exception : " + e.toString());
            throw new RuntimeException(e);

        }
    }

/*
    private void copyDatabaseFileFromAssets(){

        try{
            String DB_PATH = "/data/data/" + m_Context.getPackageName() + "/databases/";
            File folder = new File(DB_PATH);
            if(!folder.exists()){
                folder.mkdir();
            }

            InputStream inputStream = m_Context.getAssets().open(TConst.DB_FILE);
            String out_filename =DB_PATH+TConst.DB_FILE;
            OutputStream outputStream = new FileOutputStream(out_filename);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while((mLength = inputStream.read(mBuffer)) > 0){
                outputStream.write(mBuffer,0, mLength);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            Log.v(m_TAG,"copyDatabaseFileFromAssets Exception : " + e.toString());
            throw new RuntimeException(e);

        }
    }
*/

}
