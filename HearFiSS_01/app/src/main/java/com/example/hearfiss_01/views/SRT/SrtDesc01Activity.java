package com.example.hearfiss_01.views.SRT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.History.HistoryListActivity;
import com.example.hearfiss_01.views.PTT.PttDesc02Activity;
import com.example.hearfiss_01.views.WRS.WrsDesc01Activity;

public class SrtDesc01Activity extends AppCompatActivity
        implements View.OnClickListener {

    String m_TAG = "SrtDesc01Activity";
    AppCompatButton m_AppBtnNext;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;
    LinearLayout HomeLayout,SrtLayout,SrsLayout,TestLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_desc01);
        Log.d(m_TAG, "onCreate - Start");

        m_AppBtnNext = findViewById(R.id.imgBtnSrtDesc01Next);
        m_AppBtnNext.setOnClickListener(this);

        findAndSetHomeBack();
        findAndSetNavigationBar();


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgBtnSrtDesc01Next) {
            Log.d(m_TAG, "onClick - imgBtnStrDesc01Next");
            Intent intent = new Intent(getApplicationContext(), SrtDesc02Activity.class);
            startActivity(intent);
        }
        onClickHomeBack(view);
        onClickNavigationBar(view);

    }

    public void onBackPressed() {
        startActivityAndFinish(MenuActivity.class);
    }



    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);
    }

    private void findAndSetNavigationBar() {
        HomeLayout = findViewById(R.id.HomeLayout);
        SrtLayout = findViewById(R.id.SrtLayout);
        SrsLayout = findViewById(R.id.SrsLayout);
        TestLayout = findViewById(R.id.TestLayout);

        HomeLayout.setOnClickListener(this);
        SrtLayout.setOnClickListener(this);
        SrsLayout.setOnClickListener(this);
        TestLayout.setOnClickListener(this);
    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(MenuActivity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnHome");
            startActivityAndFinish(MenuActivity.class);
        }
    }

    private void onClickNavigationBar(View view) {
        if (view.getId() == R.id.HomeLayout) {
            startActivityAndFinish(MenuActivity.class);
        } else if (view.getId() == R.id.SrtLayout) {
            startActivityAndFinish(SrtDesc01Activity.class);
        } else if (view.getId() == R.id.SrsLayout) {
//            startActivityAndFinish(WrsDesc01Activity.class);

        }else if (view.getId() == R.id.TestLayout) {
            startActivityAndFinish(HistoryListActivity.class);

        }
    }


    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }
}
