package com.example.hearfiss_01.views.PTT;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.audioTest.PTT.PureTonePlayer;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.MenuActivity;

public class PttPreTestActivity extends AppCompatActivity implements View.OnClickListener {
    String m_TAG = "PttPreTestActivity";

    AppCompatButton m_BtnStart;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;
    ImageButton m_ImgBtnSoundDown, m_ImgBtnSoundUp;
    PureTonePlayer m_PTPlayer;
    String m_packname;
    int m_iCurHz, m_iCurDBHL;

    TextView m_TextViewSideCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptt_pre_test);
        m_packname = getPackageName();

        findBtnAndSetClickListener();
        findAndSetHomeBack();
        setTextColor();
        //----------------------------------------------------------------------------------------//
        m_TextViewSideCheck = findViewById(R.id.sideCheckText);
        if(GlobalVar.g_TestSide == TConst.T_RIGHT){
            m_TextViewSideCheck.setText("오른쪽 듣기");
        }else{
            m_TextViewSideCheck.setText("왼쪽 듣기");
        }

        m_PTPlayer = new PureTonePlayer(this);
        m_PTPlayer.setVolumeMax();
        m_PTPlayer.setVolumeSide(GlobalVar.g_TestSide);
        m_PTPlayer.setPhoneAndDevice(GlobalVar.g_PttStrPhone, GlobalVar.g_PttStrDevice);

//            GlobalVar.g_PttTypeEarOrHead = TConst.HEADSET;


        m_iCurHz = TConst.PTT_PRE_HZ;
        m_iCurDBHL = TConst.PTT_PRE_DBHL;
        m_PTPlayer.playPureToneFromHzDBHL(m_iCurHz, m_iCurDBHL);
        m_BtnStart.setOnClickListener(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        m_PTPlayer.closeAll();

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
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Log.v(m_TAG, "onKeyUp - KEYCODE_VOLUME_DOWN");
                return true;

            case KeyEvent.KEYCODE_VOLUME_UP:
                Log.v(m_TAG, "onKeyUp - KEYCODE_VOLUME_UP");
                return true;

        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivityAndFinish(PttDesc02Activity.class);
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.appBtnPttPreTestStart) {
            Log.v(m_TAG, "onClick - appBtnPttPreTestStart");
            closePlayerAndSaveStartDBHL();

            startActivityAndFinish(PttStartActivity.class);

        }

        onClickHomeBack(view);
        onClickSoundUpDownButton(view);

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
            m_PTPlayer.closeAll();

            startActivityAndFinish(PttDesc02Activity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnHome");
            m_PTPlayer.closeAll();
            startActivityAndFinish(MenuActivity.class);

        }

    }

    private void onClickSoundUpDownButton(View view){
        if(view.getId() == R.id.soundDown){
            Log.v(m_TAG, "onClick - soundDown");
            m_iCurDBHL -= 5;
            checkAndPlayPureTone();
//            setStartBtnClickableAfterSoundUpDown();

        } else if(view.getId() == R.id.soundUp){
            Log.v(m_TAG, "onClick - soundUp");
            m_iCurDBHL += 5;
            checkAndPlayPureTone();
//            setStartBtnClickableAfterSoundUpDown();
        }
    }

    private void checkAndPlayPureTone() {

        if(m_iCurDBHL < TConst.MIN_DBHL) {
            m_iCurDBHL = TConst.MIN_DBHL;
        } else if(m_iCurDBHL > TConst.MAX_DBHL){
            m_iCurDBHL = TConst.MAX_DBHL;
        }

        Log.v(m_TAG, String.format("checkAndplayPureTone - Hz:%d, DB:%d", m_iCurHz, m_iCurDBHL));
        m_PTPlayer.playPureToneFromHzDBHL(m_iCurHz, m_iCurDBHL);
    }

    private void closePlayerAndSaveStartDBHL() {

        m_PTPlayer.closeAll();
        if(GlobalVar.g_TestSide == TConst.T_RIGHT){
            GlobalVar.g_PttRightDBHL = m_iCurDBHL;

        } else if(GlobalVar.g_TestSide == TConst.T_LEFT){
            GlobalVar.g_PttLeftDBHL = m_iCurDBHL;
        }
        Log.v(m_TAG,
                String.format("closePlayerAndSaveStartDBHL - side:%d, Left:%d, Right:%d",
                GlobalVar.g_TestSide, GlobalVar.g_PttLeftDBHL, GlobalVar.g_PttRightDBHL));
    }

    private void setStartBtnClickableAfterSoundUpDown(){
        m_BtnStart.setBackgroundResource(getResources().getIdentifier("blue_button","drawable", m_packname));
        m_BtnStart.setTextColor(getColor(R.color.white));

    }

    private void findBtnAndSetClickListener(){
        m_BtnStart = findViewById(R.id.appBtnPttPreTestStart);
        m_ImgBtnSoundDown = findViewById(R.id.soundDown);
        m_ImgBtnSoundDown.setOnClickListener(this);

        m_ImgBtnSoundUp = findViewById(R.id.soundUp);
        m_ImgBtnSoundUp.setOnClickListener(this);
    }

    private void setTextColor()
    {
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