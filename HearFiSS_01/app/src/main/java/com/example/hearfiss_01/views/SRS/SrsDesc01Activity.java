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

public class SrsDesc01Activity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    String m_TAG = "SrsDesc01Activity";

    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    AppCompatToggleButton srs10,srs20,srs25;

    AppCompatButton imgBtnSrsDesc01Next;
    LinearLayout HomeLayout,SrtLayout,SrsLayout,TestLayout;

    int [] m_ToggleBtnId = {R.id.srs10, R.id.srs20, R.id.srs25};

    String m_packname = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srs_desc01);

        m_packname = getPackageName();

        srs10 = findViewById(R.id.srs10);
        srs20 = findViewById(R.id.srs20);
        srs25 = findViewById(R.id.srs25);


        imgBtnSrsDesc01Next = findViewById(R.id.imgBtnSrsDesc01Next);
        imgBtnSrsDesc01Next.setOnClickListener(this);

        findAndSetHomeBack();
        findAndSetNavigationBar();

        srs10.setOnCheckedChangeListener(this);
        srs20.setOnCheckedChangeListener(this);
        srs25.setOnCheckedChangeListener(this);



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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgBtnSrsDesc01Next) {
            GlobalVar.g_TestSide = TConst.T_RIGHT;
            GlobalVar.g_srsNumber = getSrsNum();

            startActivityAndFinish(SrsDesc02Activity.class);
        }

        onClickHomeBack(view);
        onClickNavigationBar(view);

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

        }else if (view.getId() == R.id.PtaLayout) {
            startActivityAndFinish(SrtDesc01Activity.class);

        }else if (view.getId() == R.id.TestLayout) {
            startActivityAndFinish(HistoryListActivity.class);

        }
    }

    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }

    public void Clickable(){
        imgBtnSrsDesc01Next.setClickable(true);
        imgBtnSrsDesc01Next.setOnClickListener(this);
        imgBtnSrsDesc01Next.setBackgroundResource(getResources().getIdentifier("blue_button","drawable", m_packname));
    }

    public void unClickable(){
        imgBtnSrsDesc01Next.setBackgroundResource(getResources().getIdentifier("gray_button","drawable", m_packname));
        imgBtnSrsDesc01Next.setClickable(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            compoundButton.setTextColor(getColor(R.color.white));
            checkOtherBtnToggle(compoundButton.getId());
        } else {
            compoundButton.setTextColor(getColor(R.color.gray));
        }
        checkAndNextBtnClickable();

    }


    private void checkOtherBtnToggle(int idCheckedBtn) {
        AppCompatToggleButton findBtn;
        for(int i=0; i<m_ToggleBtnId.length; i++){
            if(m_ToggleBtnId[i] == idCheckedBtn)
                continue;

            findBtn = findViewById(m_ToggleBtnId[i]);
            if(findBtn.isChecked())
                findBtn.performClick();
        }

    }

    private void checkAndNextBtnClickable() {
        if((srs10.isChecked()) | (srs20.isChecked()) | (srs25.isChecked())){
            Clickable();
        }else{
            unClickable();
        }
    }


    private int getSrsNum() {
        if(srs25.isChecked()) {
            return 25;
        } else if(srs20.isChecked()) {
            return 20;
        } else {
            return 10;
        }
    }
    }


