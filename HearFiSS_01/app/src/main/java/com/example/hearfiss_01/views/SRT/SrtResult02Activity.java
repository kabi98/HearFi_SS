package com.example.hearfiss_01.views.SRT;

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
import com.example.hearfiss_01.audioTest.PTT.PttThreshold;
import com.example.hearfiss_01.audioTest.WRS.WordScore;
import com.example.hearfiss_01.audioTest.WRS.WordUnit;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.History.HistoryListActivity;
import com.example.hearfiss_01.views.PTT.HlinfoActivity;
import com.example.hearfiss_01.views.PTT.PttResult02Activity;
import com.example.hearfiss_01.views.SRS.SrsDesc01Activity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class SrtResult02Activity extends AppCompatActivity  implements View.OnClickListener, CompoundButton.OnCheckedChangeListener  {

    String m_TAG = "SrtResult02Activity";
    Context m_Context;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;
    LinearLayout HomeLayout,SrtLayout,SrsLayout,TestLayout;

    AppCompatToggleButton rightToggleBtn,leftToggleBtn;

    LinearLayout rightLayout,leftLayout;
    AppCompatButton ResultToHomeBtn;
    TextView SrtUserLText, SrtUserRText, SrtRightAnswer, SrtLeftAnswer,
            SrtRightResult, SrtLeftResult,SrtRightWrong,SrtLeftWrong,rightTotalNum,leftTotalNum,
            SrtRightWord,SrtLeftWord,SrtRightWrongWord,SrtLeftWrongWord;

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
        setContentView(R.layout.activity_srt_result02);

        m_Context = SrtResult02Activity.this;

        m_Account = GlobalVar.g_AccLogin;
        m_TgId = GlobalVar.g_TestGroup.getTg_id();
        getSrtResultFromDatabase();

        settingUserText();
        findToggleBtnAndSetListener();

        ResultToHomeBtn = findViewById(R.id.ResultToHomeBtn);
        ResultToHomeBtn.setOnClickListener(this);
        findAndSetHomeBack();
        findAndSetNavigationBar();

        for(int i=0; i< GlobalVar.g_alSrtRight.size(); i++){
            Log.v(m_TAG,
                    String.format(" SRT RESULT Right : %d, %s ",
                            i , GlobalVar.g_alSrtRight.get(i).toString() ) );
        }

        for(int i=0; i< GlobalVar.g_alSrtLeft.size(); i++){
            Log.v(m_TAG,
                    String.format(" SRT RESULT Left : %d, %s ",
                            i , GlobalVar.g_alSrtLeft.get(i).toString() ) );
        }

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

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
//            startActivityAndFinish(MenuActivity.class);
            dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
            dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnHome");
            startActivityAndFinish(MenuActivity.class);

        }
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

    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }

    private void getSrtResultFromDatabase() {
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
        SrtLeftResult = findViewById(R.id.SrtLeftResult);
        SrtRightResult = findViewById(R.id.SrtRightResult);
        SrtLeftAnswer = findViewById(R.id.SrtLeftAnswer);
        SrtRightAnswer = findViewById(R.id.SrtRightAnswer);
        SrtLeftWrong = findViewById(R.id.SrtLeftWrong);
        SrtRightWrong = findViewById(R.id.SrtRightWrong);
        SrtRightWrongWord = findViewById(R.id.SrtRightWrongWord);
        SrtLeftWrongWord = findViewById(R.id.SrtLeftWrongWord);
        SrtRightWord = findViewById(R.id.SrtRightWord);
        SrtLeftWord = findViewById(R.id.SrtLeftWord);

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
