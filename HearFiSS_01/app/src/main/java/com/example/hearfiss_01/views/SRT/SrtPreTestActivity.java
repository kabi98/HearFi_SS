package com.example.hearfiss_01.views.SRT;

import static com.example.hearfiss_01.global.ShareUtil.changeTextColorFromStartToEnd;

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
import com.example.hearfiss_01.audioTest.PTT.PureTonePlayer;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.MainActivity;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.PTT.PttDesc02Activity;
import com.example.hearfiss_01.views.PTT.PttStartActivity;
import com.example.hearfiss_01.views.PTT.PttTestActivity;

public class SrtPreTestActivity  extends AppCompatActivity
        implements View.OnClickListener {

    String m_TAG = "SrtPreTestActivity";

    AppCompatButton m_BtnStart;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    ImageButton m_ImgBtnSoundDown, m_ImgBtnSoundUp;

//    PureTonePlayer m_PTPlayer;

    String m_packname;

//    int m_iCurHz, m_iCurDBHL;

    TextView m_TextViewSideCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_pre_test);

        m_packname = getPackageName();

        findBtnAndSetClickListener();
        findAndSetHomeBack();
        setTextColor();

        m_TextViewSideCheck = findViewById(R.id.sideCheckText);
        if(GlobalVar.g_TestSide == TConst.T_RIGHT){
            m_TextViewSideCheck.setText("오른쪽 듣기");
        }else{
            m_TextViewSideCheck.setText("왼쪽 듣기");
        }
        m_BtnStart.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        startActivityAndFinish(SrtDesc02Activity.class);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.appBtnSrtPreTestStart) {
            Log.d(m_TAG, "onClick - pttStartBtn");
            startActivityAndFinish(SrtStartActivity.class);

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
//            m_PTPlayer.closeAll();

            startActivityAndFinish(SrtDesc02Activity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnHome");
//            m_PTPlayer.closeAll();
            startActivityAndFinish(MenuActivity.class);

        }
    }


    private void findBtnAndSetClickListener() {
        m_BtnStart = findViewById(R.id.appBtnSrtPreTestStart);
        m_ImgBtnSoundDown = findViewById(R.id.soundDown);
        m_ImgBtnSoundDown.setOnClickListener(this);

        m_ImgBtnSoundUp = findViewById(R.id.soundUp);
        m_ImgBtnSoundUp.setOnClickListener(this);
    }

    private void setTextColor() {
        changeTextColorFromStartToEnd(R.id.preTestText, "#0181F8", 4, 9);
        changeTextColorFromStartToEnd(R.id.preTestText1, "#0181F8", 5, 15);
        changeTextColorFromStartToEnd(R.id.preTestText2, "#1DB85E", 5, 18);
        changeTextColorFromStartToEnd(R.id.preTestText3, "#FF0000", 5, 18);
    }

    private void changeTextColorFromStartToEnd(int idRes, String strColor, int iStart, int iEnd){
        TextView tvText = findViewById(idRes);
        String strText = tvText.getText().toString();
        SpannableStringBuilder ssbText = new SpannableStringBuilder(strText);
        ssbText.setSpan(new ForegroundColorSpan(Color.parseColor(strColor)),iStart,iEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvText.setText(ssbText);
    }




    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }

}