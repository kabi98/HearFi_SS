package com.example.hearfiss_01.views.SRT;

import static com.example.hearfiss_01.global.ShareUtil.changeTextColorFromStartToEnd;

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
import android.view.KeyEvent;
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
import com.example.hearfiss_01.views.WRS.WrsPreTestActivity;

public class SrtPreTestActivity  extends AppCompatActivity
        implements View.OnClickListener {

    String m_TAG = "SrtPreTestActivity";
    Context m_Context;

    AppCompatButton m_BtnStart;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    ImageButton m_ImgBtnSoundDown, m_ImgBtnSoundUp;


    String m_packname;

   int m_iCurHz, m_iCurDBHL;

    TextView m_TextViewSideCheck;

    AudioManager m_AudioMan= null;
    MediaPlayer m_Player = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_pre_test);
        m_Context = SrtPreTestActivity.this;

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

        int idTrack = getResources().getIdentifier("pre","raw", m_packname);
        stopPlayer();
        m_Player = MediaPlayer.create(m_Context,idTrack);
        playStart(m_Player);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopPlayer();
    }


    @Override
    public void onBackPressed() {
        startActivityAndFinish(SrtDesc02Activity.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Log.d(m_TAG, "onKeyDown - KEYCODE_VOLUME_DOWN");
                return true;

            case KeyEvent.KEYCODE_VOLUME_UP:
                Log.d(m_TAG, "onKeyDown - KEYCODE_VOLUME_UP");
                return true;

        }
        return super.onKeyDown(keyCode, event);
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

    private void onClickSoundUpDownButton(View view){
        if(view.getId() == R.id.soundDown){
            Log.v(m_TAG, "onClick - soundDown");
            m_iCurDBHL -= 5;
           //  checkAndPlayPureTone();
            setStartBtnClickableAfterSoundUpDown();

        } else if(view.getId() == R.id.soundUp){
            Log.v(m_TAG, "onClick - soundUp");
            m_iCurDBHL += 5;
       //     checkAndPlayPureTone();
            setStartBtnClickableAfterSoundUpDown();
        }
    }

    private void setStartBtnClickableAfterSoundUpDown() {
        m_BtnStart.setBackgroundResource(getResources().getIdentifier("blue_button","drawable", m_packname));
        m_BtnStart.setTextColor(getColor(R.color.white));
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


    public void playStart(MediaPlayer m_player){
        float lVolume = GlobalVar.g_TestSide == TConst.T_LEFT?1.0f:0.0f;
        float rVolume = GlobalVar.g_TestSide == TConst.T_RIGHT?1.0f:0.0f;
        if (m_player != null){
            m_player.setVolume(lVolume,rVolume);
            m_player.start();
        }
    }

    public int stopPlayer(){
        Log.v(m_TAG, "stopPlayer");
        try {
            if (m_Player != null) {
                m_Player.stop();
                m_Player.release();
                m_Player = null;
            }
            return 1;
        } catch (Exception e) {
            Log.v(m_TAG, "stopPlayer Exception " + e);
            return 0;
        }
    }


}