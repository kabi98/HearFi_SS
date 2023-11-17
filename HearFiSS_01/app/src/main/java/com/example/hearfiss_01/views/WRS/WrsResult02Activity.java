package com.example.hearfiss_01.views.WRS;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatToggleButton;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.audioTest.WRS.WordScore;
import com.example.hearfiss_01.audioTest.WRS.WordUnit;
import com.example.hearfiss_01.db.dao.WrsDAO;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.History.HistoryListActivity;
import com.example.hearfiss_01.views.PTT.PttDesc01Activity;

import java.util.ArrayList;

public class WrsResult02Activity extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    String m_TAG = "WrsResult02Activity";
    Context m_Context;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;
    LinearLayout HomeLayout,PtaLayout,WrsLayout,TestLayout;

    AppCompatToggleButton rightToggleBtn,leftToggleBtn;

    LinearLayout rightLayout,leftLayout;
    AppCompatButton ResultToHomeBtn;
    TextView WrsUserLText, WrsUserRText, WrsRightAnswer, WrsLeftAnswer,
            WrsRightResult, WrsLeftResult,WrsRightWrong,WrsLeftWrong,rightTotalNum,leftTotalNum,
            WrsRightWord,WrsLeftWord,WrsRightWrongWord,WrsLeftWrongWord;

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
        setContentView(R.layout.activity_wrs_result02);

        m_Context = WrsResult02Activity.this;

        m_Account = GlobalVar.g_AccLogin;
        m_TgId = GlobalVar.g_TestGroup.getTg_id();
        getWrsResultFromDatabase();

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
//        startActivityAndFinish(MenuActivity.class);
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

        } else if (view.getId() == R.id.PtaLayout) {
            Intent intent = new Intent(getApplicationContext(), PttDesc01Activity.class);
            startActivity(intent);

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



    private void getWrsResultFromDatabase(){
        Log.v(m_TAG, String.format("getWrsResultFromDatabase") );
        WrsDAO wrsDAO = new WrsDAO(m_Context);

        wrsDAO.setAccount(m_Account);
        wrsDAO.loadWrsResultFromTestGroupId(m_TgId);

        m_TestGroup = wrsDAO.getTestGroup();

        m_TestSetLeft = wrsDAO.getTestSetLeft();
        m_TestSetRight = wrsDAO.getTestSetRight();

        m_alLeft = wrsDAO.getLeftUnitList();
        m_alRight = wrsDAO.getRightUnitList();

        m_ScoreLeft.setM_alWord(m_alLeft);
        m_ScoreLeft.scoringWordList();

        m_ScoreRight.setM_alWord(m_alRight);
        m_ScoreRight.scoringWordList();

        wrsDAO.releaseAndClose();

        Log.v(m_TAG, String.format("TestGroup id:%d, %s",
                m_TestGroup.getTg_id(), m_TestGroup.toString() ) );

        Log.v(m_TAG, String.format("TestSet LEFT %s",
                m_TestSetLeft.toString() ) );

        Log.v(m_TAG, String.format("TestSet RIGHT %s",
                m_TestSetRight.toString() ) );

        Log.v(m_TAG, String.format("**** LEFT Unit List Size = %d *****", m_alLeft.size() ));
        for(WordUnit unitOne: m_alLeft) {
            unitOne.print();
        }

        Log.v(m_TAG, String.format("**** RIGHT Unit List Size = %d *****", m_alRight.size()));
        for(WordUnit unitOne: m_alRight) {
            unitOne.print();
        }

    }
    private String getCorrectStringList(ArrayList<WordUnit> _alUnitList){
        String strCorrect = "";
        for(WordUnit unit: _alUnitList){
            if(1 == unit.get_Correct()){
                strCorrect += unit.get_Question()+" ";
            }
        }
        return strCorrect;
    }

    private String getWrongStringList(ArrayList<WordUnit> _alUnitList){
        String strWrong = "";
        for(WordUnit unit: _alUnitList){
            if(1 != unit.get_Correct()){
                strWrong += unit.get_Question()+" ";
            }
        }
        return strWrong;
    }

    private void findToggleBtnAndSetListener() {
        rightLayout = findViewById(R.id.rightLayout);
        leftLayout = findViewById(R.id.leftLayout);

        rightToggleBtn = findViewById(R.id.rightToggleBtn);
        leftToggleBtn = findViewById(R.id.leftToggleBtn);

        rightToggleBtn.setOnCheckedChangeListener(this);
        leftToggleBtn.setOnCheckedChangeListener(this);
    }

    private void settingUserText(){
//        WrsUserLText = findViewById(R.id.WrsUserLText);
//        WrsUserRText = findViewById(R.id.WrsUserRText);
        WrsLeftResult = findViewById(R.id.WrsLeftResult);
        WrsRightResult = findViewById(R.id.WrsRightResult);
        WrsLeftAnswer = findViewById(R.id.WrsLeftAnswer);
        WrsRightAnswer = findViewById(R.id.WrsRightAnswer);
        WrsLeftWrong = findViewById(R.id.WrsLeftWrong);
        WrsRightWrong = findViewById(R.id.WrsRightWrong);
        WrsRightWrongWord = findViewById(R.id.WrsRightWrongWord);
        WrsLeftWrongWord = findViewById(R.id.WrsLeftWrongWord);
        WrsRightWord = findViewById(R.id.WrsRightWord);
        WrsLeftWord = findViewById(R.id.WrsLeftWord);

        rightTotalNum = findViewById(R.id.rightTotalNum);
        leftTotalNum = findViewById(R.id.leftTotalNum);


        String ResultLeft = String.format(
                " %s %n%d %% (%d개/%d개)"
                , m_ScoreLeft.getGradeFromScore()
                , m_ScoreLeft.getM_iScore()
                , m_ScoreLeft.getM_iCorrect()
                , m_ScoreLeft.getM_iQuestion() );

        String ResultRight = String.format(
                " %s %n%d %% (%d개/%d개)"
                , m_ScoreRight.getGradeFromScore()
                , m_ScoreRight.getM_iScore()
                , m_ScoreRight.getM_iCorrect()
                , m_ScoreRight.getM_iQuestion() );

        WrsLeftResult.setText(ResultLeft);
        WrsRightResult.setText(ResultRight);

        String l_answerNum = Integer.toString(m_ScoreLeft.getM_iCorrect());
        String r_answerNum = Integer.toString(m_ScoreRight.getM_iCorrect());
        WrsLeftAnswer.setText(l_answerNum);
        WrsRightAnswer.setText(r_answerNum);

        String l_wrongNum = Integer.toString(m_ScoreLeft.getM_iQuestion() - m_ScoreLeft.getM_iCorrect());
        String r_wrongNum = Integer.toString(m_ScoreRight.getM_iQuestion() - m_ScoreRight.getM_iCorrect());

        rightTotalNum.setText(Integer.toString(m_ScoreRight.getM_iQuestion()));
        leftTotalNum.setText(Integer.toString(m_ScoreLeft.getM_iQuestion()));
        WrsLeftWrong.setText(l_wrongNum);
        WrsRightWrong.setText(r_wrongNum);

        String l_answer= getCorrectStringList(m_alLeft);
        String l_wrong = getWrongStringList(m_alLeft);
        String r_answer= getCorrectStringList(m_alRight);
        String r_wrong = getWrongStringList(m_alRight);

        WrsLeftWord.setText(l_answer);
        WrsLeftWrongWord.setText(l_wrong);
        WrsRightWord.setText(r_answer);
        WrsRightWrongWord.setText(r_wrong);
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

    private void checkCorrectWrongDetail(){
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