package com.example.hearfiss_01.views.SRS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatToggleButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.History.HistoryListActivity;
import com.example.hearfiss_01.views.SRT.SrtDesc01Activity;

public class SrsDesc01Activity extends AppCompatActivity implements View.OnClickListener{

    String m_TAG = "SrsDesc01Activity";

    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    AppCompatButton imgBtnSrsDesc01Next;
    LinearLayout HomeLayout,SrtLayout,SrsLayout,TestLayout;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srs_desc01);

        Log.v(m_TAG, "onCreate - start");




        imgBtnSrsDesc01Next = findViewById(R.id.imgBtnSrsDesc01Next);
        imgBtnSrsDesc01Next.setOnClickListener(this);

        findAndSetHomeBack();
        findAndSetNavigationBar();



    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imgBtnSrsDesc01Next) {
            Log.v(m_TAG, "onClick - imgBtnSrsDesc01Next");
            Intent intent = new Intent(getApplicationContext(), SrsDesc02Activity.class);
            startActivity(intent);
        }
        onClickHomeBack(view);
        onClickNavigationBar(view);

    }


    @Override
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
            Log.d(m_TAG, "onClick - imgBtnSrsDesc01Back");
            startActivityAndFinish(MenuActivity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnSrsDesc01Home");
            startActivityAndFinish(MenuActivity.class);

        }
    }

    private void onClickNavigationBar(View view) {
        if (view.getId() == R.id.HomeLayout) {
            startActivityAndFinish(MenuActivity.class);
        }else if (view.getId() == R.id.SrtLayout){
            startActivityAndFinish(SrtDesc01Activity.class);
        }else if (view.getId() == R.id.SrsLayout) {
            startActivityAndFinish(SrsDesc01Activity.class);
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


