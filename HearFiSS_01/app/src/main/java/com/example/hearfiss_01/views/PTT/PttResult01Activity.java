package com.example.hearfiss_01.views.PTT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.views.Common.ImageProgress;
import com.example.hearfiss_01.views.Common.MenuActivity;

public class PttResult01Activity extends AppCompatActivity implements View.OnClickListener {

    String m_TAG = "PttResult01Activity";

    AppCompatButton m_AppBtnPttResult;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;
    ImageProgress m_ProgressPTT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptt_result01);

        m_ProgressPTT = findViewById(R.id.ImageProgress);
        m_ProgressPTT.setProgress(7);

        m_AppBtnPttResult = findViewById(R.id.pttresultBtn);
        m_AppBtnPttResult.setOnClickListener(this);

        findAndSetHomeBack();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivityAndFinish(PttDesc01Activity.class);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pttresultBtn) {
            startActivityAndFinish(PttResult02Activity.class);

        }

        onClickHomeBack(view);

    }

    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(PttDesc01Activity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnHome");
            startActivityAndFinish(MenuActivity.class);

        }

    }

    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }



}