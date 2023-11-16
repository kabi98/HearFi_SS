package com.example.hearfi_03.views.PTT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hearfi_03.R;
import com.example.hearfi_03.views.Common.MenuActivity;

public class HlinfoActivity extends AppCompatActivity implements View.OnClickListener {
    String m_TAG = "HlinfoActivity";
    AppCompatButton AppBtnHome;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hlinfo);

        AppBtnHome = findViewById(R.id.infoToHomeBtn);
        AppBtnHome.setOnClickListener(this);

        findAndSetHomeBack();
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivityAndFinish(PttResult02Activity.class);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.infoToHomeBtn) {
            startActivityAndFinish(MenuActivity.class);
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
            startActivityAndFinish(PttResult02Activity.class);

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