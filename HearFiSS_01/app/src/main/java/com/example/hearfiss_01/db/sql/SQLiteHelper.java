package com.example.hearfiss_01.db.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hearfiss_01.db.entity.Utils.TConst;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLiteHelper extends android.database.sqlite.SQLiteOpenHelper{

    Context m_Context = null;

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.v("SQLiteHelper", "SQLiteHelper() ver : " + version);
        this.m_Context = context;
        dataBaseCheck();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("SQLiteHelper", "onCreate()");

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
        Log.v("SQLiteHelper", "onUpgrade() old : " + oldVersion
                + " new : " + newVersion);

        db.execSQL(" drop table IF EXISTS sentence_word; ");
        db.execSQL(" drop table IF EXISTS audiometry_track; ");
        db.execSQL(" drop table IF EXISTS audiometry_unit; ");
        db.execSQL(" drop table IF EXISTS audiometry_set; ");
        db.execSQL(" drop table IF EXISTS hrtest_unit; ");
        db.execSQL(" drop table IF EXISTS hrtest_set; ");
        db.execSQL(" drop table IF EXISTS account; ");
        db.execSQL(" drop table IF EXISTS sentence_unit; ");

        onCreate(db);

        Log.v("SQLiteHelper", "onUpgrade() - Finish");

    }

    //------------------------------------ DATABASES FOLDERS FILE CHECK -------------------------//
    private void dataBaseCheck(){
        // 해당 폴더(경로 : DATABASES 폴더 하위에 DATABASE_NAME과 같은 데이터베이스가 있는지 체크
        String DB_PATH = "/data/data/" + m_Context.getPackageName() + "/databases/";
        File dbFile = new File(DB_PATH + TConst.DB_FILE);
        if(!dbFile.exists()){
            // 파일이 존재 하지 않을 때 dbCopy 메소드를 실행
            dpCopy();
            // 복사 완료후 Log로 확인
            Log.v("DataBaseHelper","DataBase is Copied");
        }
    }

    //------------------------------------ DATABASES COPY METHOD ---------------------------------//
    private void dpCopy(){
        // 예외 처리로 파일을 복사하자 파일 관련해서 소스 코드 처리할 땐 예외 처리 구문을 활용
        try{
            // 어플, 기기 폴더 (Device File Explorer) 아래 data/data/databases 폴더에 접근
            String DB_PATH = "/data/data/" + m_Context.getPackageName() + "/databases/";
            File folder = new File(DB_PATH);
            // 해당 경로에 폴더가 없으면 폴더 생성
            if(!folder.exists()){
                // 폴더 만들기 메소드
                folder.mkdir();
            }

            // InputStream -> 바이트의 입력 스트림을 나타내는 최상위 클래스
            InputStream inputStream = m_Context.getAssets().open(TConst.DB_FILE);
            // databases위치 -> /data/data/com.example.project명/databases/데이터베이스이름으로 저장하기 위해서 변수 설정
            String out_filename =DB_PATH+TConst.DB_FILE;
            // OutputStream -> 바이트의 출력 스트림을 나타내는 최상위 클래스
            OutputStream outputStream = new FileOutputStream(out_filename);
            // file을 읽고 출력하는데 1024byte로 읽어서 1Kb씩 저장하도록 만들며
            byte[] mBuffer = new byte[1024];
            int mLength;
            // 입력 스트림의 읽은 byte의 값이 0 작으면 : 파일 복사 완료 -> 조건문
            while((mLength = inputStream.read(mBuffer)) > 0){
                // outputStream.write 기능으로 읽어온 byte를 써준다.
                outputStream.write(mBuffer,0,mLength);
            }
            // 스트림 버퍼에 있는 데이터 강제적으로 출력
            outputStream.flush();
            // 사용한 입,출력 스트림 닫아주기
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
