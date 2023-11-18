package com.example.hearfiss_01.views.SRT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.views.Common.ImageProgress;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.PTT.PttDesc01Activity;
import com.example.hearfiss_01.views.PTT.PttResult02Activity;

public class SrtResult01Activity extends AppCompatActivity implements View.OnClickListener {

    String m_TAG = "PttResult01Activity";

    AppCompatButton m_AppBtnSrtResult;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_result01);

        m_AppBtnSrtResult = findViewById(R.id.srtresultBtn);
        m_AppBtnSrtResult.setOnClickListener(this);

        findAndSetHomeBack();
    }

    @Override
    public void onBackPressed() {
        startActivityAndFinish(SrtDesc01Activity.class);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.srtresultBtn) {
//            startActivityAndFinish(SrtResult02Activity.class);

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