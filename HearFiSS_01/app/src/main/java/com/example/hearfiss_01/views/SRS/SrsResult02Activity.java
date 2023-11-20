package com.example.hearfiss_01.views.SRS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatToggleButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.audioTest.WRS.WordScore;
import com.example.hearfiss_01.audioTest.WRS.WordUnit;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.History.HistoryListActivity;
import com.example.hearfiss_01.views.SRT.SrtDesc01Activity;

import java.util.ArrayList;

public class SrsResult02Activity extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    String m_TAG = "SrsResult02Activity";
    Context m_Context;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;
    LinearLayout HomeLayout,SrtLayout,SrsLayout,TestLayout;

    AppCompatToggleButton rightToggleBtn,leftToggleBtn;

    LinearLayout rightLayout,leftLayout;
    AppCompatButton ResultToHomeBtn;
    TextView SrsUserLText, SrsUserRText, SrsRightAnswer, SrsLeftAnswer,
            SrsRightResult, SrsLeftResult,SrsRightWrong,SrsLeftWrong,rightTotalNum,leftTotalNum,
            SrsRightWord,SrsLeftWord,SrsRightWrongWord,SrsLeftWrongWord;

    Account m_Account;
    int m_TgId;
    HrTestGroup m_TestGroup;
    HrTestSet m_TestSetLeft;
    HrTestSet m_TestSetRight;
    ArrayList<WordUnit> m_alRight;
    ArrayList<WordUnit> m_alLeft;

    WordScore m_ScoreLeft = new WordScore();
    WordScore m_ScoreRight = new WordScore();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srs_result02);

        m_Context = SrsResult02Activity.this;

        m_Account = GlobalVar.g_AccLogin;
        m_TgId = GlobalVar.g_TestGroup.getTg_id();
        getSrsResultFromDatabase();

        settingUserText();
        findToggleBtnAndSetListener();

        ResultToHomeBtn = findViewById(R.id.ResultToHomeBtn);
        ResultToHomeBtn.setOnClickListener(this);
        findAndSetHomeBack();
        findAndSetNavigationBar();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivityAndFinish(MenuActivity.class);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ResultToHomeBtn) {
            startActivityAndFinish(MenuActivity.class);

        }
        onClickHomeBack(view);
        onClickNavigationBar(view);

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

    private void onClickNavigationBar(View view) {
        if (view.getId() == R.id.HomeLayout) {
            startActivityAndFinish(MenuActivity.class);

        } else if (view.getId() == R.id.SrtLayout) {
            Intent intent = new Intent(getApplicationContext(), SrtDesc01Activity.class);
            startActivity(intent);

        }else if (view.getId() == R.id.SrsLayout) {
            startActivityAndFinish(SrsDesc01Activity.class);

        }else if (view.getId() == R.id.TestLayout) {
            startActivityAndFinish(HistoryListActivity.class);

        }

    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
            dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

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

    private void getSrsResultFromDatabase() {
    }

    private void findToggleBtnAndSetListener() {
        rightLayout = findViewById(R.id.rightLayout);
        leftLayout = findViewById(R.id.leftLayout);

        rightToggleBtn = findViewById(R.id.rightToggleBtn);
        leftToggleBtn = findViewById(R.id.leftToggleBtn);

        rightToggleBtn.setOnCheckedChangeListener(this);
        leftToggleBtn.setOnCheckedChangeListener(this);
    }

    private void settingUserText() {
        SrsLeftResult = findViewById(R.id.SrsLeftResult);
        SrsRightResult = findViewById(R.id.SrsRightResult);
        SrsLeftAnswer = findViewById(R.id.SrsLeftAnswer);
        SrsRightAnswer = findViewById(R.id.SrsRightAnswer);
        SrsLeftWrong = findViewById(R.id.SrsLeftWrong);
        SrsRightWrong = findViewById(R.id.SrsRightWrong);
        SrsRightWrongWord = findViewById(R.id.SrsRightWrongWord);
        SrsLeftWrongWord = findViewById(R.id.SrsLeftWrongWord);
        SrsRightWord = findViewById(R.id.SrsRightWord);
        SrsLeftWord = findViewById(R.id.SrsLeftWord);

        rightTotalNum = findViewById(R.id.rightTotalNum);
        leftTotalNum = findViewById(R.id.leftTotalNum);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(isChecked){
            compoundButton.setTextColor(getColor(R.color.white));
        }else{
            compoundButton.setTextColor(getColor(R.color.blue));
        }
        checkCorrectWrongDetail();

    }

    private void checkCorrectWrongDetail() {
        if ( leftToggleBtn.isChecked()){
            leftLayout.setVisibility(View.VISIBLE);
        } else {
            leftLayout.setVisibility(View.GONE);
        }

        if ( rightToggleBtn.isChecked()){
            rightLayout.setVisibility(View.VISIBLE);
        } else {
            rightLayout.setVisibility(View.GONE);
        }

    }
}