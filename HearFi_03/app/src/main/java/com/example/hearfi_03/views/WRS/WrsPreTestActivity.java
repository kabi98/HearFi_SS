package com.example.hearfi_03.views.WRS;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hearfi_03.R;
import com.example.hearfi_03.global.GlobalVar;
import com.example.hearfi_03.global.TConst;
import com.example.hearfi_03.views.Common.MenuActivity;

public class WrsPreTestActivity extends AppCompatActivity implements View.OnClickListener {
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
        setContentView(R.layout.activity_wrs_pre_test);
        m_packname = getPackageName();
        m_Context = WrsPreTestActivity .this;
        Log.v(m_TAG, "onCreate");

        startBtn = findViewById(R.id.imgBtnWrsPreTestStart);
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

        m_iCurVolume = 0;
        m_AudioMan = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        m_AudioMan.setStreamVolume(AudioManager.STREAM_MUSIC, 7,0);

        int idTrack = getResources().getIdentifier("pre","raw", m_packname);
        stopPlayer();
        m_Player = MediaPlayer.create(m_Context,idTrack);

        setVolumeFromDBHL(50);
        m_Player.setLooping(true);
        playStart(m_Player);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopPlayer();
    }

    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

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

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivityAndFinish(WrsDesc02Activity.class);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imgBtnWrsPreTestStart) {
            stopPlayer();

            GlobalVar.g_wrsUserVolume = m_iCurVolume;

            startActivityAndFinish(WrsStartActivity.class);
        }

        if(view.getId() == R.id.soundDown){
//            checkbtnClickable();
            m_iCurVolume = adjustVolume(-1);
        }else if(view.getId() == R.id.soundUp){
//            checkbtnClickable();
            m_iCurVolume = adjustVolume(+1);
        }
        onClickHomeBack(view);
    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(WrsDesc01Activity.class);

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


    private int setVolumeFromDBHL(int iDBHL){
        Log.v(m_TAG, String.format("setVolumeFromDBHL Vol = %d ", iDBHL) );
        if(m_Player == null){
            return 0;
        }

        if(GlobalVar.g_TestSide == TConst.T_RIGHT){
            m_Player.setVolume(0.0f, 1.0f); // Right Only
        } else {
            m_Player.setVolume(1.0f, 0.0f); // Left Only
        }

        int iCalcVol = calculteVolumeFromDBHL(iDBHL);
        int iCheckVol = getVolumeAfterMinMaxCheck(iCalcVol);

        m_AudioMan.setStreamVolume(AudioManager.STREAM_MUSIC, iCheckVol,0);
        return iCheckVol;
    }
    public int adjustVolume(int delta){
        if(m_Player == null || !m_Player.isPlaying()){
            return 0;
        }

        int CurVol = m_AudioMan.getStreamVolume(AudioManager.STREAM_MUSIC);
        int NewVol = getVolumeAfterMinMaxCheck(CurVol + delta);

        //Log.v(aName,"current V : " + nVolume);
        m_AudioMan.setStreamVolume(AudioManager.STREAM_MUSIC, NewVol,0);
        return NewVol;
    }

    private int calculteVolumeFromDBHL(int iDBHL) {
        int MaxVol = m_AudioMan.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int MinVol = m_AudioMan.getStreamMinVolume(AudioManager.STREAM_MUSIC);
        int CalcVol = (int)(iDBHL * ((float)(MaxVol - MinVol) / 100.));

        Log.v(m_TAG, String.format(" setVolumeFromDBHL DBHL = %d , Max : %d, Min : %d, Calc : %d ",
                iDBHL, MaxVol, MinVol, CalcVol) );
        return CalcVol;
    }

    private int getVolumeAfterMinMaxCheck(int iVol){
        int MaxVol = m_AudioMan.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int MinVol = m_AudioMan.getStreamMinVolume(AudioManager.STREAM_MUSIC);

        if(iVol > MaxVol){
            iVol = MaxVol;
        }
        if(iVol < MinVol){
            iVol = MinVol;
        }

        return iVol;
    }

    private void checkbtnClickable(){

        startBtn.setClickable(true);
        startBtn.setBackgroundResource(getResources().getIdentifier("blue_button","drawable", m_packname));
        startBtn.setTextColor(getColor(R.color.white));
        startBtn.setOnClickListener(this);
    }

    public void playStart(MediaPlayer m_player){
        float lVolume = GlobalVar.g_TestSide == TConst.T_LEFT?1.0f:0.0f;
        float rVolume = GlobalVar.g_TestSide == TConst.T_RIGHT?1.0f:0.0f;
        if (m_player != null){
            m_player.setVolume(lVolume,rVolume);
            m_player.start();
        }
    }


}