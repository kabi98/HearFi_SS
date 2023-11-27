package com.example.hearfiss_01.views.SRT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.PTT.PttDesc01Activity;
import com.example.hearfiss_01.views.PTT.PttTestActivity;

public class SrtStartActivity extends AppCompatActivity
        implements View.OnClickListener {

    String m_TAG = "SrtStartActivity";

    TextView m_TextSrtInfo;
    AppCompatButton m_AppBtnStart;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_start);

        changeTextColorFromStartToEnd(R.id.srtInfoText, "#0181F8", 5, 14);

        m_AppBtnStart = findViewById(R.id.srtStartBtn);
        m_AppBtnStart.setOnClickListener(this);

        if(GlobalVar.g_TestSide== TConst.T_RIGHT){
            m_AppBtnStart.setText("오른쪽 테스트 시작하기");
        }else{
            m_AppBtnStart.setText("왼쪽 테스트 시작하기");
        }

        findAndSetHomeBack();
    }

    @Override
    public void onBackPressed() {
        startActivityAndFinish(SrtDesc01Activity.class);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.srtStartBtn) {
            Log.d(m_TAG, "onClick - srtStartBtn");
            startActivityAndFinish(SrtTestActivity.class);

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
            startActivityAndFinish(SrtDesc01Activity.class);

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

    private void changeTextColorFromStartToEnd(int idRes, String strColor, int iStart, int iEnd) {
        TextView tvText = findViewById(idRes);
        String strText = tvText.getText().toString();
        SpannableStringBuilder ssbText = new SpannableStringBuilder(strText);
        ssbText.setSpan(new ForegroundColorSpan(Color.parseColor(strColor)),iStart,iEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvText.setText(ssbText);
    }

}