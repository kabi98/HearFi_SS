package com.example.hearfiss_01.views.SRT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.audioTest.SRT.SRT;
import com.example.hearfiss_01.audioTest.SRT.SrtScore;
import com.example.hearfiss_01.audioTest.SRT.SrtUnit;
import com.example.hearfiss_01.db.DTO.HrTestUnit;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.MenuActivity;

import java.util.ArrayList;

public class SrtTestActivity extends AppCompatActivity
        implements View.OnClickListener, TextView.OnEditorActionListener {

    String m_TAG = "SrtTestActivity";
    Context m_Context;

    AppCompatButton m_AppBtnNext, m_AppBtnAnswerVoice, sttFinishBtn;

    LinearLayout STTView;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    ProgressBar m_ProgressBar = null;

    EditText m_EditSRT;

    Intent stt_intent;

    final int PERMISSION = 1;

    TextView m_TextViewTestSide, sttTextView1, sttTextView2;

    String user_Answer = "";

    ArrayList<HrTestUnit> user_lists= new ArrayList<HrTestUnit>();

    ArrayList<SrtUnit> m_alSrtUnit = null;

    SrtScore m_SrtScore = null;

    int m_iLimit = 0;

    boolean m_isActChanging;

    SRT m_SRT = null;



    InputMethodManager imm = null;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_test);
        m_Context = SrtTestActivity.this;

        m_iLimit = GlobalVar.g_srtNumber;
        //--------------------------------TEST DETAIL CONMENT TEXTVIEW ---------------------------//
        m_TextViewTestSide = findViewById(R.id.srtTestSideTitle);
        if(GlobalVar.g_TestSide == TConst.T_LEFT){
            m_TextViewTestSide.setText("왼쪽 귀 테스트 중입니다.");
        } else {
            m_TextViewTestSide.setText("오른쪽 귀 테스트 중입니다.");
        }

        m_AppBtnAnswerVoice = findViewById(R.id.srtVoiceAnswerBtn);
        m_AppBtnAnswerVoice.setOnClickListener(this);

        m_AppBtnNext = findViewById(R.id.srtnextBtn);
        m_AppBtnNext.setOnClickListener(this);

        setSideTextAndProgressBar();

        findAndSetHomeBack();

        setupAnswerEditAndShowSoftKeyboard();

        initAct();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finalAct();

    }
    @Override
    public void onBackPressed() {
        startActivityAndFinish(SrtDesc01Activity.class);
    }

    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

    }

    private void initAct() {
        if(TConst.T_RIGHT == GlobalVar.g_TestSide) {
            GlobalVar.g_alSrtRight.clear();
            GlobalVar.g_alSrtLeft.clear();
        }

        m_isActChanging = false;
        m_SRT = new SRT(m_Context);
        m_SRT.setM_tsLimit(GlobalVar.g_srtNumber);
        m_SRT.setUserVolume(GlobalVar.g_srtUserVolume);
        m_SRT.setM_Type("mwl_a1");
        m_SRT.playCurrent();
    }

    private void finalAct() {
        m_SRT.endTest();
        m_AppBtnNext.setClickable(false);
        m_AppBtnAnswerVoice.setClickable(false);

        // 키보드 제어
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(m_EditSRT.getWindowToken(), 0);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.srtnextBtn) {
            Log.v(m_TAG, "onClick - nextBtnClick");
            ClickedSrtNextBtn();

        } /*else if(view.getId() == R.id.srtVoiceAnswerBtn){
            Log.v(m_TAG, "onClick - srtVoiceAnswerBtn");
            // 음성 인식 기능 활성화
        }*/

        onClickHomeBack(view);

    }
    private void ClickedSrtNextBtn() {
        Log.v(m_TAG, "onClick - sound play");
        initAct();
        m_AppBtnNext.setText("다음 문제");
        if(m_isActChanging) {
            return;
        }

        String strAnswer = m_EditSRT.getText().toString();
        m_EditSRT.setText("");

        // m_SRT.saveAnswer(strAnswer);
        // int curPercent = m_SRT.getCurrentProgress();
        // m_ProgressBar.setProgress(curPercent);

        checkTestEndAndNextPlay();
    }

    private void checkTestEndAndNextPlay() {
        if(m_SRT.isEnd()){
            // m_ProgressBar.setProgress(100);
            m_AppBtnNext.setClickable(false);
            m_AppBtnAnswerVoice.setClickable(false);

            saveResultAndChangeAct();

        } else {
            m_SRT.playNext();
        }

    }
    private void saveResultAndChangeAct() {
        m_isActChanging = true;
        if(TConst.T_RIGHT == GlobalVar.g_TestSide){
            saveSrtResultToGlobalVar();
            m_SRT.endTest();

        } else if(TConst.T_LEFT == GlobalVar.g_TestSide){
            saveSrtResultToGlobalVar();
            if(m_SRT.isEnd()){
                saveSrtResultToDatabase();
            }
            m_SRT.endTest();
        }


        showChangeSideMessage();
        checkSideAndStartActivity();

    }


    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(SrtDesc01Activity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(MenuActivity.class);

        }

    }

    private void saveSrtResultToDatabase() {
        // not yet
    }

    private void printBothResult() {
       /*
        ArrayList<SrtUnit> alSrtResult;
        alSrtResult = GlobalVar.g_alSrtLeft;
        for (SrtUnit resultOne : alSrtResult) {
            Log.v(m_TAG, String.format("result LEFT Q:%s, A:%s, C:%d",
                    resultOne.get_Question(), resultOne.get_Answer(), resultOne.get_Correct()));
        }

        alSrtResult = GlobalVar.g_alSrtRight;
        for (SrtUnit resultOne : alSrtResult) {
            Log.v(m_TAG, String.format("result RIGHT Q:%s, A:%s, C:%d",
                    resultOne.get_Question(), resultOne.get_Answer(), resultOne.get_Correct()));
        }*/
    }

    private void saveSrtResultToGlobalVar() {
        // not yet
        Log.v(m_TAG, String.format("saveSrtResultToGlobalVar") );

        ArrayList<SrtUnit> alSrtResult = m_SrtScore.getM_alSrtUnit();

        for(SrtUnit resultOne : alSrtResult){
            Log.v(m_TAG, String.format("result Side:%d Q:%s, A:%s, C:%d",
                    GlobalVar.g_TestSide, resultOne.get_Question(), resultOne.get_Answer(), resultOne.get_Correct()) );        }

        if(TConst.T_RIGHT == GlobalVar.g_TestSide){
            GlobalVar.g_alSrtRight = alSrtResult;

        } else if(TConst.T_LEFT == GlobalVar.g_TestSide){
            GlobalVar.g_alSrtLeft = alSrtResult;

        }
    }

    private void setSideTextAndProgressBar() {
        //----------------------------------SIDE TEXT SETTING-------------------------------------//
        m_TextViewTestSide = findViewById(R.id.srtTestSideTitle);
        if(GlobalVar.g_TestSide == TConst.T_LEFT){
            m_TextViewTestSide.setText("왼쪽 귀 테스트 중입니다.");
        } else {
            m_TextViewTestSide.setText("오른쪽 귀 테스트 중입니다.");
        }
        //----------------------------------PROGRESS BAR SETTING----------------------------------//
       /*
        m_ProgressBar = findViewById(R.id.progress_bar);
        m_ProgressBar.setIndeterminate(false);
        m_ProgressBar.setProgress(0);
*/
    }

    private boolean isEnterKeyUp(int keyCode, KeyEvent keyEvent){
        return( (keyCode == keyEvent.KEYCODE_ENTER)
                && (keyEvent.getAction() == KeyEvent.ACTION_UP) );
    }
    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        Log.v(m_TAG, String.format("onEditorAction v:%d, i:%d", textView.getId(), actionId) );
        if(EditorInfo.IME_ACTION_DONE == actionId){
            Log.v(m_TAG, String.format("onEditorAction v:%d, Action Done id = %d", textView.getId(), actionId) );
            ClickedSrtNextBtn();
            return true;
        }
        return false;
    }

    private void setupAnswerEditAndShowSoftKeyboard() {
        m_EditSRT = findViewById(R.id.srt_Edit);
        m_EditSRT.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        m_EditSRT.setFocusableInTouchMode(true);
        m_EditSRT.setOnEditorActionListener(this);

        InputMethodManager imm = (InputMethodManager) getSystemService((Context.INPUT_METHOD_SERVICE));
        m_EditSRT.requestFocus();
        imm.showSoftInput(m_EditSRT,0);
    }



    public void showChangeSideMessage(){
        if(GlobalVar.g_TestSide == TConst.T_RIGHT) {
            String info = "오른쪽 테스트 종료되었습니다.\n" +
                    "왼쪽 테스트 진행하겠습니다.\n";
            Toast toast = Toast.makeText(m_Context,info,Toast.LENGTH_SHORT);
            toast.show();
        }else{
            String info = "왼쪽 테스트 종료되었습니다.\n" +
                    "결과 화면으로 넘어가겠습니다.\n";
            Toast toast = Toast.makeText(m_Context,info,Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void checkSideAndStartActivity() {
        Handler handler = new Handler();
        handler.postDelayed(new RunCheckAndStartActivity(), 2000);
    }


    public class RunCheckAndStartActivity implements Runnable {

        @Override
        public void run() {

            if(GlobalVar.g_TestSide == TConst.T_RIGHT) {
                GlobalVar.g_TestSide = TConst.T_LEFT;
                startActivityAndFinish(SrtPreTestActivity.class);

            } else {
                startActivityAndFinish(SrtResult01Activity.class);

            }

        }
    }



    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }


}
