package com.example.hearfi_03.views.WRS;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hearfi_03.R;
import com.example.hearfi_03.audioTest.WRS.WRS;
import com.example.hearfi_03.audioTest.WRS.WordUnit;
import com.example.hearfi_03.db.dao.WrsDAO;
import com.example.hearfi_03.global.GlobalVar;
import com.example.hearfi_03.global.TConst;
import com.example.hearfi_03.views.Common.MenuActivity;

import java.util.ArrayList;

public class WrsTestActivity extends AppCompatActivity
        implements View.OnClickListener, TextView.OnEditorActionListener {
    String m_TAG = "WrsTestActivity";
    Context m_Context;

    AppCompatButton m_AppBtnNext, m_AppBtnReplay;
    TextView m_TextViewTestSide;
    EditText m_EditWRS;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    ProgressBar m_ProgressBar;
    WRS m_WRS = null;
    int m_iLimit = 0;
    boolean m_isActChanging;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrs_test);
        m_Context = WrsTestActivity.this;

        m_iLimit = GlobalVar.g_wrsNumber;

        m_AppBtnReplay = findViewById(R.id.wrsReplayBtn);
        m_AppBtnReplay.setOnClickListener(this);
//        m_AppBtnReplay.setVisibility(View.INVISIBLE);
        m_AppBtnReplay.setEnabled(false);

        m_AppBtnNext = findViewById(R.id.wrsnextBtn);
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
//        super.onBackPressed();
        startActivityAndFinish(WrsDesc01Activity.class);
    }


    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

    }

    private void initAct(){
        if(TConst.T_RIGHT == GlobalVar.g_TestSide) {
            GlobalVar.g_alWrsRight.clear();
            GlobalVar.g_alWrsLeft.clear();
        }

        m_isActChanging = false;
        m_WRS = new WRS(m_Context);
        m_WRS.setM_tsLimit(GlobalVar.g_wrsNumber);
        m_WRS.setUserVolume(GlobalVar.g_wrsUserVolume);
        m_WRS.setM_Type("mwl_a1");
        m_WRS.playCurrent();

    }

    private void finalAct(){
        m_WRS.endTest();
        m_AppBtnNext.setClickable(false);
        m_AppBtnReplay.setClickable(false);

        // 키보드 제어
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(m_EditWRS.getWindowToken(), 0);
        // 채점기능
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.wrsnextBtn) {
            Log.v(m_TAG, "onClick" + R.id.wrsnextBtn);
            ClickedWrsNextBtn();

        } else if(view.getId() == R.id.wrsReplayBtn){
            Log.v(m_TAG, "onClick - wrsReplayBtn");
            m_WRS.playCurrent();

        }

        onClickHomeBack(view);
    }

    public void ClickedWrsNextBtn(){
        Log.v(m_TAG, "NextBtnClick - 단어 인지도 테스트");
        if(m_isActChanging) {
            return;
        }

        String strAnswer = m_EditWRS.getText().toString();
        m_EditWRS.setText("");

        m_WRS.SaveAnswer(strAnswer);
        int curPercent = m_WRS.getCurrentProgress();
        m_ProgressBar.setProgress(curPercent);
        Log.v(m_TAG, "NextBtnClick - progressbar value : " + curPercent);

        checkTestEndAndNextPlay();

    }

    private void checkTestEndAndNextPlay() {
        if(m_WRS.isEnd()){
            m_ProgressBar.setProgress(100);
            m_AppBtnNext.setClickable(false);
            m_AppBtnReplay.setClickable(false);

            saveResultAndChangeAct();

        } else {
            m_WRS.playNext();
        }

    }

    private void saveResultAndChangeAct() {
        m_isActChanging = true;
        if(TConst.T_RIGHT == GlobalVar.g_TestSide){
            saveWrsResultToGlobalVar();
            m_WRS.endTest();

        } else if(TConst.T_LEFT == GlobalVar.g_TestSide){
            saveWrsResultToGlobalVar();
            if(m_WRS.isEnd()){
                saveWrsResultToDatabase();
            }
            m_WRS.endTest();
        }

        showChangeSideMessage();
        checkSideAndStartActivity();
    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(WrsDesc01Activity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(MenuActivity.class);

        }

    }

    private void saveWrsResultToDatabase() {
        Log.v(m_TAG, String.format("saveWrsResultToDatabase") );

        printBothResult();

        WrsDAO wrsDAO = new WrsDAO(m_Context);
        wrsDAO.setAccount(GlobalVar.g_AccLogin);
        wrsDAO.setResultList(GlobalVar.g_alWrsLeft, GlobalVar.g_alWrsRight);

        wrsDAO.saveTestResults();
        GlobalVar.g_TestGroup = wrsDAO.getTestGroup();

        wrsDAO.releaseAndClose();
    }

    private void printBothResult() {
        ArrayList<WordUnit> alWrsResult;
        alWrsResult = GlobalVar.g_alWrsLeft;
        for (WordUnit resultOne : alWrsResult) {
            Log.v(m_TAG, String.format("result LEFT Q:%s, A:%s, C:%d",
                    resultOne.get_Question(), resultOne.get_Answer(), resultOne.get_Correct()));
        }

        alWrsResult = GlobalVar.g_alWrsRight;
        for (WordUnit resultOne : alWrsResult) {
            Log.v(m_TAG, String.format("result RIGHT Q:%s, A:%s, C:%d",
                    resultOne.get_Question(), resultOne.get_Answer(), resultOne.get_Correct()));
        }
    }

    private void saveWrsResultToGlobalVar(){
        Log.v(m_TAG, String.format("saveWrsResultToGlobalVar") );

        ArrayList<WordUnit> alWrsResult = m_WRS.get_WordUnitList();

        for(WordUnit resultOne : alWrsResult){
            Log.v(m_TAG, String.format("result Side:%d Q:%s, A:%s, C:%d",
                    GlobalVar.g_TestSide, resultOne.get_Question(), resultOne.get_Answer(), resultOne.get_Correct()) );        }

        if(TConst.T_RIGHT == GlobalVar.g_TestSide){
            GlobalVar.g_alWrsRight = alWrsResult;

        } else if(TConst.T_LEFT == GlobalVar.g_TestSide){
            GlobalVar.g_alWrsLeft = alWrsResult;

        }
    }

    private void setSideTextAndProgressBar() {
        //----------------------------------SIDE TEXT SETTING-------------------------------------//
        m_TextViewTestSide = findViewById(R.id.wrsTestSideTitle);
        if(GlobalVar.g_TestSide == TConst.T_LEFT){
            m_TextViewTestSide.setText("왼쪽 귀 테스트 중입니다.");
        } else {
            m_TextViewTestSide.setText("오른쪽 귀 테스트 중입니다.");
        }
        //----------------------------------PROGRESS BAR SETTING----------------------------------//
        m_ProgressBar = findViewById(R.id.progress_bar);
        m_ProgressBar.setIndeterminate(false);
        m_ProgressBar.setProgress(0);

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
            ClickedWrsNextBtn();
            return true;
        }
        return false;
    }


    private void setupAnswerEditAndShowSoftKeyboard() {
        // 키보드 자동완성 제거
        m_EditWRS = findViewById(R.id.wrs_Edit);
        m_EditWRS.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        m_EditWRS.setFocusableInTouchMode(true);
        m_EditWRS.setOnEditorActionListener(this);

        InputMethodManager imm = (InputMethodManager) getSystemService((Context.INPUT_METHOD_SERVICE));
        m_EditWRS.requestFocus();
        imm.showSoftInput(m_EditWRS,0);
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
    public void checkSideAndStartActivity() {
        Handler handler = new Handler();
        handler.postDelayed(new RunCheckAndStartActivity(), 2000);
    }

    public class RunCheckAndStartActivity implements Runnable {

        @Override
        public void run() {

            if(GlobalVar.g_TestSide == TConst.T_RIGHT) {
                GlobalVar.g_TestSide = TConst.T_LEFT;
                startActivityAndFinish(WrsPreTestActivity.class);

            } else {
                startActivityAndFinish(WrsResult01Activity.class);

            }

        }
    }

    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }


}