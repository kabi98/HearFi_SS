package com.example.hearfiss_01.views.SRT;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hearfiss_01.R;

public class SoundTestActivity extends AppCompatActivity implements View.OnClickListener {
    Button upBtn, downBtn;
    MediaPlayer m_Player = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_test);

        upBtn = findViewById(R.id.upBtn);
        upBtn.setOnClickListener(this);

        downBtn = findViewById(R.id.downBtn);
        downBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

    }
}