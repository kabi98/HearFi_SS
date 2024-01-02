package com.example.hearfiss_01.views.Common;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

    final int PERMISSION = 1;

    boolean isPermissionRequested = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        m_Context = SplashActivity.this;

        Log.v(m_TAG, "onCreate");

        // 녹음 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION);
            } else {
                moveToNextScreen();
            }
        } else {
            moveToNextScreen();
        }

        copyDatabaseToExtenalDir();

        Log.v(m_TAG, "onCreate-finish");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isPermissionRequested && hasAllPermissions()) {
            moveToNextScreen();
        }
        isPermissionRequested = false;
    }
    private void moveToNextScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.v(m_TAG, "moveToNextScreen - postDelayed run");
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500); // 1.5초 후에 다음 화면으로 이동
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        isPermissionRequested = true;

        if (requestCode == PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                moveToNextScreen();
            } else {
                handlePermissionDenied();
            }
        }
    }

    private void handlePermissionDenied() {
        // 권한 거부 시 알림
        new AlertDialog.Builder(this)
                .setTitle("권한 필요")
                .setMessage("이 앱은 정상적으로 작동하기 위해 녹음 권한이 필요합니다. 설정에서 권한을 허용해주세요.")
                .setPositiveButton("설정으로 이동", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 앱 설정으로 이동
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 앱 종료
                        finish();
                    }
                })
                .create()
                .show();
    }
    private boolean hasAllPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
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