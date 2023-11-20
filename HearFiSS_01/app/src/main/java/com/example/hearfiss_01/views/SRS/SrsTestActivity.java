package com.example.hearfiss_01.views.SRS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.MenuActivity;

public class SrsTestActivity extends AppCompatActivity
        implements View.OnClickListener, TextView.OnEditorActionListener{

    String m_TAG = "SrsTestActivity";
    Context m_Context;

    AppCompatButton m_AppBtnNext, m_AppBtnReplay;
    TextView m_TextViewTestSide;
    EditText m_EditSRS;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    ProgressBar m_ProgressBar;
//    SRS m_SRS = null;
    int m_iLimit = 0;
    boolean m_isActChanging;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srs_test);

        m_Context = SrsTestActivity.this;

        m_iLimit = GlobalVar.g_srsNumber;

        m_AppBtnReplay = findViewById(R.id.srsReplayBtn);
        m_AppBtnReplay.setOnClickListener(this);
        m_AppBtnReplay.setEnabled(false);

        m_AppBtnNext = findViewById(R.id.srsnextBtn);
        m_AppBtnNext.setOnClickListener(this);

//        setSideTextAndProgressBar();

        findAndSetHomeBack();

        setupAnswerEditAndShowSoftKeyboard();

        initAct();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finalAct();

    }

    public void onBackPressed() {
        startActivityAndFinish(SrsDesc01Activity.class);
    }

    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);
    }

    private void initAct() {

    }

    private void finalAct() {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.srsnextBtn) {
            Log.v(m_TAG, "onClick" + R.id.srsnextBtn);
            ClickedSrsNextBtn();

        } else if(view.getId() == R.id.srsReplayBtn){
            Log.v(m_TAG, "onClick - srsReplayBtn");
//            m_SRS.playCurrent();

        }

        onClickHomeBack(view);
    }

    private void ClickedSrsNextBtn() {
        Log.v(m_TAG, "NextBtnClick - 문장 인지도 테스트");
        if(m_isActChanging) {
            return;
        }

        String srsAnswer = m_EditSRS.getText().toString();
        m_EditSRS.setText("");

//        m_SRS.SaveAnswer(strAnswer);
//        int curPercent = m_SRS.getCurrentProgress();
//        m_ProgressBar.setProgress(curPercent);
//        Log.v(m_TAG, "NextBtnClick - progressbar value : " + curPercent);

        checkTestEndAndNextPlay();
    }

    private void checkTestEndAndNextPlay() {
//        if(m_SRS.isEnd()){
//            m_ProgressBar.setProgress(100);
//            m_AppBtnNext.setClickable(false);
//            m_AppBtnReplay.setClickable(false);

            saveResultAndChangeAct();

//        } else {
//            m_SRS.playNext();
//        }

    }

    private void saveResultAndChangeAct() {
        m_isActChanging = true;
//        if(TConst.T_RIGHT == GlobalVar.g_TestSide){
//            saveSrsResultToGlobalVar();
//            m_SRS.endTest();
//
//        } else if(TConst.T_LEFT == GlobalVar.g_TestSide){
//            saveSrsResultToGlobalVar();
//            if(m_SRS.isEnd()){
//                saveSrsResultToDatabase();
//            }
//            m_SRS.endTest();
//        }

        showChangeSideMessage();

        checkSideAndStartActivity();
    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(SrsDesc01Activity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(MenuActivity.class);

        }
    }

//    private void setSideTextAndProgressBar() {
//        //----------------------------------SIDE TEXT SETTING-------------------------------------//
//        m_TextViewTestSide = findViewById(R.id.srsTestSideTitle);
//        if(GlobalVar.g_TestSide == TConst.T_LEFT){
//            m_TextViewTestSide.setText("왼쪽 귀 테스트 중입니다.");
//        } else {
//            m_TextViewTestSide.setText("오른쪽 귀 테스트 중입니다.");
//        }
//        //----------------------------------PROGRESS BAR SETTING----------------------------------//
//        m_ProgressBar = findViewById(R.id.progress_bar);
//        m_ProgressBar.setIndeterminate(false);
//        m_ProgressBar.setProgress(0);
//
//    }

    private boolean isEnterKeyUp(int keyCode, KeyEvent keyEvent){
        return( (keyCode == keyEvent.KEYCODE_ENTER)
                && (keyEvent.getAction() == KeyEvent.ACTION_UP) );
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        Log.v(m_TAG, String.format("onEditorAction v:%d, i:%d", textView.getId(), actionId) );
        if(EditorInfo.IME_ACTION_DONE == actionId){
            Log.v(m_TAG, String.format("onEditorAction v:%d, Action Done id = %d", textView.getId(), actionId) );
            ClickedSrsNextBtn();
            return true;
        }
        return false;
    }

    private void setupAnswerEditAndShowSoftKeyboard() {
        m_EditSRS = findViewById(R.id.srs_Edit);
        m_EditSRS.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        m_EditSRS.setFocusableInTouchMode(true);
        m_EditSRS.setOnEditorActionListener(this);

        InputMethodManager imm = (InputMethodManager) getSystemService((Context.INPUT_METHOD_SERVICE));
        m_EditSRS.requestFocus();
        imm.showSoftInput(m_EditSRS,0);
    }
    private void showChangeSideMessage() {
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
                startActivityAndFinish(SrsPreTestActivity.class);

            } else {
                startActivityAndFinish(SrsResult01Activity.class);

            }

        }
    }

    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }


}