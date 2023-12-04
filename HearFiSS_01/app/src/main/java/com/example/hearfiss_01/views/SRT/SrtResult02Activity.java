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
import com.example.hearfiss_01.audioTest.SRT.SrtScore;
import com.example.hearfiss_01.audioTest.SRT.SrtUnit;
import com.example.hearfiss_01.audioTest.WRS.WordScore;
import com.example.hearfiss_01.audioTest.WRS.WordUnit;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.HrTestUnit;
import com.example.hearfiss_01.db.dao.PttDAO;
import com.example.hearfiss_01.db.dao.SrtDAO;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.History.HistoryListActivity;
import com.example.hearfiss_01.views.PTT.HlinfoActivity;
import com.example.hearfiss_01.views.PTT.PttResult02Activity;
import com.example.hearfiss_01.views.SRS.SrsDesc01Activity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import kotlin.Unit;

public class SrtResult02Activity extends AppCompatActivity  implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    String m_TAG = "SrtResult02Activity";
    Context m_Context;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;
    LinearLayout HomeLayout, SrtLayout, SrsLayout, TestLayout;

    AppCompatToggleButton rightToggleBtn, leftToggleBtn;

    LinearLayout rightLayout, leftLayout;
    AppCompatButton ResultToHomeBtn;
    TextView SrtUserLText, SrtUserRText, SrtRightAnswer, SrtLeftAnswer,
            SrtRightResult, SrtLeftResult, SrtRightWrong, SrtLeftWrong, rightTotalNum, leftTotalNum,
            SrtRightWord, SrtLeftWord, SrtRightWrongWord, SrtLeftWrongWord;

    Account m_Account;
    int m_TgId;
    HrTestGroup m_TestGroup;
    HrTestSet m_TestSetLeft;
    HrTestSet m_TestSetRight;
    ArrayList<SrtUnit> m_alRight;
    ArrayList<SrtUnit> m_alLeft;

    SrtScore m_ScoreLeft = new SrtScore();

    SrtScore m_ScoreRight = new SrtScore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_result02);

        Log.d(m_TAG,"SrtLeftResult initialized : " + (SrtLeftResult != null));
        Log.d(m_TAG,"SrtRightResult initialized : " + (SrtRightResult != null));

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

        } else if (view.getId() == R.id.SrsLayout) {
            startActivityAndFinish(SrsDesc01Activity.class);

        } else if (view.getId() == R.id.TestLayout) {
            startActivityAndFinish(HistoryListActivity.class);

        }

    }

    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }

    private void getSrtResultFromDatabase() {


        SrtDAO srtDAO = new SrtDAO(m_Context);

        srtDAO.setAccount(m_Account);
        srtDAO.loadSrtResultsFromTestGroupId(m_TgId);


        Log.d(m_TAG, "getSrtResultFromDatabase Account " + m_Account.toString());
        Log.d(m_TAG, "getSrtResultFromDatabase TGID " + m_TgId);

        m_TestGroup = srtDAO.getTestGroup();

        m_TestSetLeft = srtDAO.getTestSetLeft();
        m_TestSetRight = srtDAO.getTestSetRight();

        m_alRight = srtDAO.get_alRightList();
        m_alLeft = srtDAO.get_alLeftList();

        m_ScoreLeft.setM_alSrtUnit(m_alLeft);
        m_ScoreRight.setM_alSrtUnit(m_alRight);


        srtDAO.releaseAndClose();

        Log.v(m_TAG, String.format("Srt result : m_ScoreLeft " + m_ScoreLeft.getM_iPassTrsd() ));
        Log.v(m_TAG, String.format("Srt result : m_ScoreRight " + m_ScoreRight.getM_iPassTrsd() ));


        Log.v(m_TAG, String.format("TestGroup id:%d, %s",
                m_TestGroup.getTg_id(), m_TestGroup.toString() ) );

        Log.v(m_TAG, String.format("TestSet LEFT %s", m_TestSetLeft.toString()));

        Log.v(m_TAG, String.format("TesSet Right %s", m_TestSetRight.toString()));

        for(int i=0; i< m_alRight.size(); i++){
            Log.v(m_TAG,
                    String.format(" SRT RESULT Right : %d, %s ",
                            i , m_alRight.get(i).toString() ) );
        }

        for(int i=0; i< m_alLeft.size(); i++){
            Log.v(m_TAG,
                    String.format(" SRT RESULT Left : %d, %s ",
                            i , m_alLeft.get(i).toString() ) );
        }
    }

    private String getCorrectStringList(ArrayList<SrtUnit> _alUnitList){
        Log.v(m_TAG, "getCorrectStringList - size : " +  _alUnitList.size());
        String strCorrect = "";
        for (SrtUnit unit : _alUnitList){
            Log.v(m_TAG, "processing CorrectSrtUnit : " + unit.toString());

            if (1 == unit.get_Correct()){
                strCorrect += unit.get_Question() + " ";
                Log.v(m_TAG, " Discover CorrectSrtUnit : " + unit.get_Question());
            }
        }
        Log.v(m_TAG, " getCorrectStringList - end : "+ strCorrect);
        return strCorrect;
    }

    private String getWrongStringList(ArrayList<SrtUnit> _alUnitList){
        Log.v(m_TAG, "getWrongStringList - size : " + _alUnitList.size());
        String strWrong = " ";
        for (SrtUnit unit : _alUnitList){
            Log.v(m_TAG, "processing WrongSrtUnit : " + unit.toString());

            if (1 != unit.get_Correct()){
                strWrong += unit.get_Question() + " ";
                Log.v(m_TAG,"Discover WrongSrtUnit : "+ unit.get_Question());
            }
        }
        Log.v(m_TAG, "getWrongStringList - end : " + strWrong);
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

        String ResultLeft = String.format(
                " %d%%"
                , m_ScoreLeft.getM_iPassTrsd());
        Log.d(m_TAG,"Result Left : " + ResultLeft);


        String ResultRight = String.format(
                " %d%%"
                , m_ScoreRight.getM_iPassTrsd());
        Log.d(m_TAG,"Result Right : " + SrtRightResult);


        SrtLeftResult.setText(ResultLeft);
        SrtRightResult.setText(ResultRight);



        String l_answerNum = Integer.toString(m_ScoreLeft.getM_iCorrect());
        String r_answerNum = Integer.toString(m_ScoreRight.getM_iCorrect());
        SrtLeftAnswer.setText(l_answerNum);
        SrtRightAnswer.setText(r_answerNum);
        Log.d(m_TAG,"Left Answer : " + l_answerNum);
        Log.d(m_TAG,"Right Answer : " + r_answerNum);



        String l_wrongNum = Integer.toString(m_ScoreLeft.getM_iQuestion() - m_ScoreLeft.getM_iCorrect());
        String r_wrongNum = Integer.toString(m_ScoreRight.getM_iQuestion() - m_ScoreRight.getM_iCorrect());

//        rightTotalNum.setText(Integer.toString(m_ScoreRight.getM_iQuestion()));
//        leftTotalNum.setText(Integer.toString(m_ScoreLeft.getM_iQuestion()));
        SrtLeftWrong.setText(l_wrongNum);
        SrtRightWrong.setText(r_wrongNum);

        String l_answer= getCorrectStringList(m_alLeft);
        String l_wrong = getWrongStringList(m_alLeft);
        String r_answer= getCorrectStringList(m_alRight);
        String r_wrong = getWrongStringList(m_alRight);

        SrtLeftWord.setText(l_answer);
        SrtLeftWrongWord.setText(l_wrong);
        SrtRightWord.setText(r_answer);
        SrtRightWrongWord.setText(r_wrong);

        Log.d(m_TAG, "Final Left Set Text : " + SrtLeftWord.getText().toString());
        Log.d(m_TAG, "Final Right Set Text : " + SrtRightWord.getText().toString());

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
