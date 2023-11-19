package com.example.hearfiss_01.views.SRS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
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

public class SrsPreTestActivity extends AppCompatActivity implements View.OnClickListener {
    String m_TAG = "WrsPreTestActivity";
    Context m_Context;

    AppCompatButton startBtn;
    ImageButton soundDown,soundUp;
    TextView sideCheckText;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    AudioManager m_AudioMan= null;
    MediaPlayer m_Player = null;
    String m_packname;
    int m_iCurVolume = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srs_pre_test);

        m_packname = getPackageName();
        m_Context = SrsPreTestActivity .this;
        Log.v(m_TAG, "onCreate");

        startBtn = findViewById(R.id.imgBtnSrsPreTestStart);
        startBtn.setOnClickListener(this);

        soundDown = findViewById(R.id.soundDown);
        soundDown.setOnClickListener(this);

        soundUp = findViewById(R.id.soundUp);
        soundUp.setOnClickListener(this);

        setTextColor();

        sideCheckText = findViewById(R.id.sideCheckText);

        if(GlobalVar.g_TestSide == TConst.T_RIGHT){
            sideCheckText.setText("오른쪽 듣기");
        }else{
            sideCheckText.setText("왼쪽 듣기");
        }
        findAndSetHomeBack();

    }

    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

    }

    private void setTextColor() {
        changeTextColorFromStartToEnd(R.id.preTestText, "#0181F8", 4, 9);
        changeTextColorFromStartToEnd(R.id.preTestText1, "#0181F8", 5, 15);
        changeTextColorFromStartToEnd(R.id.preTestText2, "#1DB85E", 5, 18);
        changeTextColorFromStartToEnd(R.id.preTestText3, "#FF0000", 5, 18);
    }

    private void changeTextColorFromStartToEnd(int idRes, String strColor, int iStart, int iEnd) {
        TextView tvText = findViewById(idRes);
        String strText = tvText.getText().toString();
        SpannableStringBuilder ssbText = new SpannableStringBuilder(strText);
        ssbText.setSpan(new ForegroundColorSpan(Color.parseColor(strColor)),iStart,iEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvText.setText(ssbText);
    }

    public void onBackPressed() {
        startActivityAndFinish(SrsDesc02Activity.class);
    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgBtnSrsPreTestStart) {
//        stopPlayer();

//        GlobalVar.g_wrsUserVolume = m_iCurVolume;

        startActivityAndFinish(SrsStartActivity.class);
    }

//        if(view.getId() == R.id.soundDown){
//            checkbtnClickable();
//            m_iCurVolume = adjustVolume(-1);
//        }else if(view.getId() == R.id.soundUp){
//            checkbtnClickable();
//            m_iCurVolume = adjustVolume(+1);
//        }
        onClickHomeBack(view);

    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(SrsDesc01Activity.class);

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

    private void checkbtnClickable(){

        startBtn.setClickable(true);
        startBtn.setBackgroundResource(getResources().getIdentifier("blue_button","drawable", m_packname));
        startBtn.setTextColor(getColor(R.color.white));
        startBtn.setOnClickListener(this);
    }

}