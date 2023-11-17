package com.example.hearfiss_01.views.Common;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.global.TConst;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {
    String m_TAG = "SplashActivity";
    Context m_Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        m_Context = SplashActivity.this;

        Log.v(m_TAG, "onCreate");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.v(m_TAG, "postDelayed-run");
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },1500); // 1.5 sec Delay

        copyDatabaseToExtenalDir();

        Log.v(m_TAG, "onCreate-finish");
    }

    String getStringFromNowTime(){
        DateFormat sdfDateTime = new SimpleDateFormat("yyyyMMdd-HHmmss");
        return sdfDateTime.format(Calendar.getInstance().getTime());
    }

    void copyDatabaseToExtenalDir() {
        Log.v(m_TAG, "copyDatabaseToExtenalDir-start");

        try {

            String DB_PATH = "/data/data/" + m_Context.getPackageName() + "/databases/";
            File fileInputDB = new File(DB_PATH + TConst.DB_FILE);

            String android_id = Settings.Secure.getString(m_Context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            String fileName = getStringFromNowTime() + "-" + TConst.COPY_FILE;
//          File fileOutputCopy = new File(getExternalFilesDir(null), TConst.DB_FILE);
            File downloadDir = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
            File fileOutputCopy = new File(downloadDir, fileName);

            InputStream isDB = new FileInputStream(fileInputDB);
            OutputStream osCopy = new FileOutputStream(fileOutputCopy);

            int iLen;
            byte[] byteBuf = new byte[isDB.available()];
            while((iLen = isDB.read(byteBuf)) != -1) {
                osCopy.write(byteBuf, 0, iLen);
            }

            osCopy.flush();
            osCopy.close();
            isDB.close();
            Log.v(m_TAG, "copyDatabaseToExtenalDir-finish");

        } catch (IOException e) {
            Log.w(m_TAG, "copyDatabaseToExtenalDir Exception" + e);
        }
    }



}