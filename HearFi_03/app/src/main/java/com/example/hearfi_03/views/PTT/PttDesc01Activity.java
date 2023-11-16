package com.example.hearfi_03.views.PTT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hearfi_03.R;
import com.example.hearfi_03.views.Common.MenuActivity;
import com.example.hearfi_03.views.History.HistoryListActivity;
import com.example.hearfi_03.views.WRS.WrsDesc01Activity;

public class PttDesc01Activity extends AppCompatActivity implements View.OnClickListener {
    String m_TAG = "PttDesc01Activity";

    AppCompatButton m_AppBtnNext;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;
    LinearLayout HomeLayout,PtaLayout,WrsLayout,TestLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptt_desc01);

        Log.d(m_TAG, "onCreate - Start");

        m_AppBtnNext = findViewById(R.id.imgBtnPttDesc01Next);
        m_AppBtnNext.setOnClickListener(this);

        findAndSetHomeBack();
        findAndSetNavigationBar();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imgBtnPttDesc01Next) {
            Log.d(m_TAG, "onClick - imgBtnPttDesc01Next");
            Intent intent = new Intent(getApplicationContext(), PttDesc02Activity.class);
            startActivity(intent);
        }
        onClickHomeBack(view);
        onClickNavigationBar(view);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivityAndFinish(MenuActivity.class);
    }


    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

    }

    private void findAndSetNavigationBar(){
        HomeLayout = findViewById(R.id.HomeLayout);
        PtaLayout = findViewById(R.id.PtaLayout);
        WrsLayout = findViewById(R.id.WrsLayout);
        TestLayout = findViewById(R.id.TestLayout);

        HomeLayout.setOnClickListener(this);
        PtaLayout.setOnClickListener(this);
        WrsLayout.setOnClickListener(this);
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

//        } else if (view.getId() == R.id.PtaLayout) {
//            Intent intent = new Intent(getApplicationContext(), PttDesc01Activity.class);
//            startActivity(intent);

        }else if (view.getId() == R.id.WrsLayout) {
            startActivityAndFinish(WrsDesc01Activity.class);

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