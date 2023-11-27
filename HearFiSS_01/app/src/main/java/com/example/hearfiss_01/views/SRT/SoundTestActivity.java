package com.example.hearfiss_01.views.SRT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.db.DTO.AmTrack;
import com.example.hearfiss_01.db.DTO.HrTestSet;

public class SoundTestActivity extends AppCompatActivity implements View.OnClickListener {
    Button upBtn, downBtn, startBtn;
    MediaPlayer m_Player = null;

    AudioManager m_Manager;
    Context m_Context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_test);

        m_Player = MediaPlayer.create(this, R.raw.ks_bwl_a1_001);

        m_Manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (! m_Player.isPlaying()){
                    m_Player.setLooping(true);
                    m_Player.start();
                }
            }
        });

        upBtn = findViewById(R.id.upBtn);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (m_Manager != null){
                   int cv = m_Manager.getStreamVolume(AudioManager.STREAM_MUSIC);
                   int maxVol = m_Manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                   if (cv < maxVol){
                       cv ++;
                       m_Manager.setStreamVolume(AudioManager.STREAM_MUSIC, cv, 0);
                   }
               }
            }
        });

        downBtn = findViewById(R.id.downBtn);
        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (m_Manager != null){
                    int cv = m_Manager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    if (cv>0){
                        cv --;
                        m_Manager.setStreamVolume(AudioManager.STREAM_MUSIC, cv, 0);
                    }
                }
            }
        });

        m_Context = SoundTestActivity.this;


    }


    @Override
    public void onClick(View view) {

    }
}