package com.example.hearfiss_01.views.SRT;

import static android.content.DialogInterface.BUTTON_POSITIVE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.audioTest.PTT.PTT;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.ImageProgress;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.PTT.PttDesc01Activity;

import java.util.Timer;

public class SrtTestActivity extends AppCompatActivity
        implements View.OnClickListener, TextView.OnEditorActionListener {

    String m_TAG = "SrtTestActivity";
    Context m_Context;

    AppCompatButton m_AppBtnNext, m_AppBtnReplay;
    TextView m_TextViewTestSide;
    EditText m_EditSRT;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    ProgressBar m_ProgressBar;
//    SRT m_SRT = null;
//    int m_iLimit = 0;
    boolean m_isActChanging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_test);

        m_Context = SrtTestActivity.this;
        Log.v(m_TAG, "onCreate");

        m_AppBtnReplay = findViewById(R.id.srtReplayBtn);
        m_AppBtnReplay.setOnClickListener(this);
//        m_AppBtnReplay.setEnabled(false);

        m_AppBtnNext = findViewById(R.id.srtnextBtn);
        m_AppBtnNext.setOnClickListener(this);
        setSideTextAndProgressBar();
        findAndSetHomeBack();

        setupAnswerEditAndShowSoftKeyboard();

//        initAct();
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

//  private void initAct() {

//    }


    private void finalAct() {
//        m_SRT.endTest();
        m_AppBtnNext.setClickable(false);
        m_AppBtnReplay.setClickable(false);

        // 키보드 제어
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(m_EditSRT.getWindowToken(), 0);
        // 채점기능
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.srtnextBtn) {
            Log.v(m_TAG, "onClick" + R.id.srtnextBtn);
            ClickedSrtNextBtn();

        } else if(view.getId() == R.id.srtReplayBtn){
            Log.v(m_TAG, "onClick - srtReplayBtn");
//            m_SRT.playCurrent();

        }

        onClickHomeBack(view);
    }

    private void ClickedSrtNextBtn() {
        Log.v(m_TAG, "NextBtnClick - 어음 역치 청취 테스트");
        startActivityAndFinish(SrtResult01Activity.class);


//        if(m_isActChanging) {
//            return;
//        }
//
//        String strAnswer = m_EditSRT.getText().toString();
//        m_EditSRT.setText("");
//
//        m_SRT.SaveAnswer(strAnswer);
//        int curPercent = m_SRT.getCurrentProgress();
//        m_ProgressBar.setProgress(curPercent);
//        Log.v(m_TAG, "NextBtnClick - progressbar value : " + curPercent);

//        checkTestEndAndNextPlay();



    }

//    private void checkTestEndAndNextPlay() {
//        if(m_SRT.isEnd()){
//            m_ProgressBar.setProgress(100);
//            m_AppBtnNext.setClickable(false);
//            m_AppBtnReplay.setClickable(false);
//
//            saveResultAndChangeAct();
//
//        } else {
//            m_SRT.playNext();
//        }
//    }

//    private void saveResultAndChangeAct() {
//        m_isActChanging = true;
//        if(TConst.T_RIGHT == GlobalVar.g_TestSide){
//            saveSrtResultToGlobalVar();
//            m_SRT.endTest();
//
//        } else if(TConst.T_LEFT == GlobalVar.g_TestSide){
//            saveSrtResultToGlobalVar();
//            if(m_SRT.isEnd()){
//                saveSrtResultToDatabase();
//            }
//            m_SRT.endTest();
//        }
//
//        showChangeSideMessage();
//        checkSideAndStartActivity();
//    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack ) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(SrtDesc01Activity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnHome");
            startActivityAndFinish(MenuActivity.class);

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



//    public void showChangeSideMessage(){
//        if(GlobalVar.g_TestSide == TConst.T_RIGHT) {
//            String info = "오른쪽 테스트 종료되었습니다.\n" +
//                    "왼쪽 테스트 진행하겠습니다.\n";
//            Toast toast = Toast.makeText(m_Context,info,Toast.LENGTH_SHORT);
//            toast.show();
//        }else{
//            String info = "왼쪽 테스트 종료되었습니다.\n" +
//                    "결과 화면으로 넘어가겠습니다.\n";
//            Toast toast = Toast.makeText(m_Context,info,Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }
//    private void checkSideAndStartActivity() {
//        Handler handler = new Handler();
//        handler.postDelayed(new RunCheckAndStartActivity(), 2000);
//    }




//    public class RunCheckAndStartActivity implements Runnable {
//
//        @Override
//        public void run() {
//
//            if(GlobalVar.g_TestSide == TConst.T_RIGHT) {
//                GlobalVar.g_TestSide = TConst.T_LEFT;
//                startActivityAndFinish(SrtPreTestActivity.class);
//
//            } else {
//                startActivityAndFinish(SrtResult01Activity.class);
//
//            }
//
//        }
//    }
//


    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }

}