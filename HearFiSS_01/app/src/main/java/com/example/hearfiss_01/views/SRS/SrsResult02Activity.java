package com.example.hearfiss_01.views.SRS;

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
import com.example.hearfiss_01.audioTest.SRS.SentScore;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.HrTestUnit;
import com.example.hearfiss_01.db.DTO.SrsWordUnit;
import com.example.hearfiss_01.db.dao.SrsDAO;
import com.example.hearfiss_01.db.dao.SrsWordUnitDAO;
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

    SentScore m_SentLeft;
    SentScore m_SentRight;

    SentScore m_ScoreLeft = new SentScore();
    SentScore m_ScoreRight = new SentScore();

    SrsWordUnitDAO m_srsWordUnitDAO = new SrsWordUnitDAO();

    ArrayList<HrTestUnit> rightUnits = new ArrayList<>();
    ArrayList<HrTestUnit> leftUnits = new ArrayList<>();

    ArrayList<SrsWordUnit> correctUnit = new ArrayList<>();
    ArrayList<SrsWordUnit> wrongUnit = new ArrayList<>();

    ArrayList<SrsWordUnit> rightWordList = new ArrayList<>();
    ArrayList<SrsWordUnit> leftWordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srs_result02);

        m_Context = SrsResult02Activity.this;

        m_srsWordUnitDAO = new SrsWordUnitDAO(m_Context);
        m_Account = GlobalVar.g_AccLogin;
        m_TgId = GlobalVar.g_TestGroup.getTg_id();
//----------------------------------------------------------------------------------------------------//
        getSrsResultFromDatabase();

        settingUserText();
        findToggleBtnAndSetListener();

        Log.d(m_TAG, "onCreate - ResultToHomeBtn");
        ResultToHomeBtn = findViewById(R.id.ResultToHomeBtn);

        Log.d(m_TAG, "onCreate - setOnClickListener");
        ResultToHomeBtn.setOnClickListener(this);

        Log.d(m_TAG, "onCreate - findAndSetHomeBack");
        findAndSetHomeBack();

        Log.d(m_TAG, "onCreate - findAndSetNavigationBar");
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
            Intent intent = new Intent(getApplicationContext(), SrsDesc01Activity.class);
            startActivity(intent);

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
        Log.v(m_TAG, "getSrsResultFromDatabase");
        SrsDAO srsDAO = new SrsDAO(m_Context);

        srsDAO.setAccount(m_Account);
        srsDAO.loadSrsResultFromTestGroupId(m_TgId);

        m_TestGroup = srsDAO.getTestGroup();
        Log.v(m_TAG, String.format("getSrsResultFromDatabase TestGroup id:%d, %s",
                m_TestGroup.getTg_id(), m_TestGroup.toString() ) );

        m_TestSetLeft = srsDAO.getTestSetLeft();
        m_TestSetRight = srsDAO.getTestSetRight();
        Log.v(m_TAG, String.format("TestSet LEFT %s",
                m_TestSetLeft.toString() ) );
        Log.v(m_TAG, String.format("TestSet RIGHT %s",
                m_TestSetRight.toString() ) );


        m_ScoreLeft.setAlSentence(srsDAO.getLeftSrsUnitList());
        m_ScoreLeft.scoring();

        m_ScoreRight.setAlSentence(srsDAO.getRightSrsUnitList());
        m_ScoreRight.scoring();

        Log.v(m_TAG, "getSrsResultFromDatabase List left : " + m_ScoreLeft.toString());
        Log.v(m_TAG, "getSrsResultFromDatabase List right : " + m_ScoreRight.toString());
        Log.v(m_TAG, "getSrsResultFromDatabase after scoring left : " +m_ScoreLeft);

        Log.v(m_TAG,"getSrsResultFromDatabase after scoring left : " +m_ScoreRight);

        Log.v(m_TAG,"get result Right Score : " + m_ScoreRight.toString());

        for(int i=0; i< m_ScoreRight.get_alSentence().size(); i++){
            Log.v(m_TAG,
                    String.format(" SRS RESULT Right : %d, %s ",
                            i ,  m_ScoreRight.get_alSentence().get(i).toString()) );
        }

        Log.v(m_TAG,"get result Left Score : " + m_ScoreLeft.toString());

        for(int i=0; i< m_ScoreLeft.get_alSentence().size(); i++){
            Log.v(m_TAG,
                    String.format(" SRS RESULT Left : %d, %s ",
                            i , m_ScoreLeft.get_alSentence().get(i).toString()) );
        }

////// SrsWordUnit 관련 작업

        Log.v(m_TAG,String.format(" ### LeftTsId = %d, RightTsId = %d", m_TestSetLeft.getTs_id(), m_TestSetRight.getTs_id()));

        rightUnits = srsDAO.selectUnitListFromTsId(m_TestSetRight.getTs_id());
        leftUnits = srsDAO.selectUnitListFromTsId(m_TestSetLeft.getTs_id());

        for(int i=0; i<rightUnits.size(); i++){
            ArrayList<SrsWordUnit> selectWordList = m_srsWordUnitDAO.selectWordListFromTuId(rightUnits.get(i).getTu_id());
            Log.v(m_TAG,String.format(" ### rightUnits %d : tu_id = %d", i, rightUnits.get(i).getTu_id()));
            rightWordList.addAll(selectWordList);
        }

        for(int i=0; i<leftUnits.size(); i++){
            ArrayList<SrsWordUnit> selectWordList = m_srsWordUnitDAO.selectWordListFromTuId(leftUnits.get(i).getTu_id());
            Log.v(m_TAG,String.format(" ### leftUnits %d : tu_id = %d", i, leftUnits.get(i).getTu_id()));
            leftWordList.addAll(selectWordList);
        }

//----------------------------------------------------------------------------------------------------//
/*
        Log.v(m_TAG, String.format("*** getSrsResultFromDatabase Word List Size Left:%d, Right : %d",  leftWordList.size(), rightWordList.size() ));
        int iRightAll = 0, iRightCorrect = 0, iRightWrong = 0;
        iRightAll = rightWordList.size();
        for(SrsWordUnit wordUnit : rightWordList) {
            if(wordUnit.getSu_iscorrect() == 1){
                iRightCorrect++;
            } else {
                iRightWrong++;
            }
            Log.v(m_TAG, String.format("*** getSrsResultFromDatabase WordUnit Right Q:%s A:%s C:%d ",
                    wordUnit.getSu_question(), wordUnit.getSu_answer(), wordUnit.getSu_iscorrect()));
        }
        Log.v(m_TAG, String.format("*** getSrsResultFromDatabase WordUnit Rigth All %d: C:%d W:%d ",
                iRightAll, iRightCorrect, iRightWrong));


        int iLeftAll = 0, iLeftCorrect = 0, iLeftWrong = 0;
        iLeftAll = leftWordList.size();
        for(SrsWordUnit wordUnit : leftWordList) {
            if(wordUnit.getSu_iscorrect() == 1){
                iLeftCorrect++;
            } else {
                iLeftWrong++;
            }
            Log.v(m_TAG, String.format("*** getSrsResultFromDatabase WordUnit Left Q:%s A:%s C:%d ",
                    wordUnit.getSu_question(), wordUnit.getSu_answer(), wordUnit.getSu_iscorrect()));
        }
        Log.v(m_TAG, String.format("*** getSrsResultFromDatabase WordUnit Left All %d: C:%d W:%d ",
                iLeftAll, iLeftCorrect, iLeftWrong));
*/

        srsDAO.releaseAndClose();
    }

    private String displayCorrectAnswers(SentScore score){
        return score.getCorrectStringList();
    }





    private String displayWrongAnswers(SentScore score){
        return score.getWrongStringList();
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


        SrsRightResult.setText(m_TestSetRight.getTs_Result()+"\n"+m_TestSetRight.getTs_Comment());
        SrsLeftResult.setText(m_TestSetLeft.getTs_Result()+"\n"+m_TestSetLeft.getTs_Comment());


        int iLeftAll = 0, iLeftCorrect = 0, iLeftWrong = 0;
        iLeftAll = leftWordList.size();
        for(SrsWordUnit wordUnit : leftWordList) {
            if(wordUnit.getSu_iscorrect() == 1){
                iLeftCorrect++;
            } else {
                iLeftWrong++;
            }
            Log.v(m_TAG, String.format("*** getSrsResultFromDatabase WordUnit Left Q:%s A:%s C:%d ",
                    wordUnit.getSu_question(), wordUnit.getSu_answer(), wordUnit.getSu_iscorrect()));
        }
        Log.v(m_TAG, String.format("*** getSrsResultFromDatabase WordUnit Left All %d: C:%d W:%d ",
                iLeftAll, iLeftCorrect, iLeftWrong));

        int iLeftScore = (int)(((float)iLeftCorrect/(float)iLeftAll) * 100);


        String ResultLeft = String.format(
                "문장 기준 점수 : %d %% (%d개/%d개)" +
                        "\n단어 기준 점수 : %d %% (%d개/%d개)"
                , m_ScoreLeft.get_iSentScore()
                , m_ScoreLeft.get_iSentCorrect()
                , m_ScoreLeft.get_iSentQuest()
                , iLeftScore
                , iLeftCorrect
                , iLeftAll);
        SrsLeftResult.setText(ResultLeft);

        int iRightAll = 0, iRightCorrect = 0, iRightWrong = 0;
        iRightAll = rightWordList.size();
        for(SrsWordUnit wordUnit : rightWordList) {
            if(wordUnit.getSu_iscorrect() == 1){
                iRightCorrect++;
            } else {
                iRightWrong++;
            }
            Log.v(m_TAG, String.format("*** getSrsResultFromDatabase WordUnit Right Q:%s A:%s C:%d ",
                    wordUnit.getSu_question(), wordUnit.getSu_answer(), wordUnit.getSu_iscorrect()));
        }
        Log.v(m_TAG, String.format("*** getSrsResultFromDatabase WordUnit Rigth All %d: C:%d W:%d ",
                iRightAll, iRightCorrect, iRightWrong));

        int iRightScore = (int)(((float)iRightCorrect /(float)iRightAll) * 100);

        String ResultRight = String.format(
                "문장 기준 점수 : %d %% (%d개/%d개)" +
                        "\n단어 기준 점수 : %d %% (%d개/%d개)"
                , m_ScoreRight.get_iSentScore()
                , m_ScoreRight.get_iSentCorrect()
                , m_ScoreRight.get_iSentQuest()
                , iRightScore
                , iRightCorrect
                , iRightAll);

        SrsRightResult.setText(ResultRight);

        String l_answerNum = String.format(
                "문장 : %d\n단어 : %d", m_ScoreLeft.get_iSentCorrect(), iLeftCorrect
        );
        String r_answerNum = String.format(
                "문장 : %d\n단어 : %d", m_ScoreRight.get_iSentCorrect(), iRightCorrect
        );
        SrsLeftAnswer.setText(l_answerNum);
        SrsRightAnswer.setText(r_answerNum);

        String l_wrongNum = String.format(
                "문장 : %d\n단어 : %d", m_ScoreLeft.get_iSentQuest() - m_ScoreLeft.get_iSentCorrect(), iLeftWrong);

        String r_wrongNum = String.format(
                "문장 : %d\n단어 : %d", m_ScoreRight.get_iSentQuest() - m_ScoreRight.get_iSentCorrect(), iRightWrong);
        SrsLeftWrong.setText(l_wrongNum);
        SrsRightWrong.setText(r_wrongNum);

        String totalRight = String.format("문장 : %d\n단어 : %d", m_ScoreRight.get_iSentQuest(),iRightAll);
        String totalLeft = String.format("문장 : %d\n단어 : %d", m_ScoreLeft.get_iSentQuest(),iLeftAll);
        rightTotalNum.setText(totalRight);
        leftTotalNum.setText(totalLeft);

        String l_answer = displayCorrectAnswers(m_ScoreLeft);
        SrsLeftWord.setText(l_answer);
        String l_wrong = displayWrongAnswers(m_ScoreLeft);
        SrsLeftWrongWord.setText(l_wrong);

        String r_answer = displayCorrectAnswers(m_ScoreRight);
        SrsRightWord.setText(r_answer);
        String r_wrong = displayWrongAnswers(m_ScoreRight);
        SrsRightWrongWord.setText(r_wrong);
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