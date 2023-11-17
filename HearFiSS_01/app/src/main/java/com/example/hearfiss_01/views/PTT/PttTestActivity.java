package com.example.hearfiss_01.views.PTT;

import static android.content.DialogInterface.BUTTON_POSITIVE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.audioTest.PTT.PTT;
import com.example.hearfiss_01.audioTest.PTT.PttThreshold;
import com.example.hearfiss_01.db.dao.PttDAO;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.ImageProgress;
import com.example.hearfiss_01.views.Common.MenuActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PttTestActivity extends AppCompatActivity
        implements View.OnClickListener, DialogInterface.OnClickListener {
    String m_TAG = "PttTestActivity";
    Context m_Context;

    AppCompatButton m_ImgBtnlistenAgain;
    AppCompatButton m_AppBtnStop;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    ImageButton     m_ImgBtnHearing;
    ImageButton     m_ImgBtnNoHearing;
    ImageProgress   m_ProgressPTT;
    TextView        m_TextViewTestSide, m_TextViewCurHz;

    PTT             m_PTT;
    int             m_iCur;

    long m_LastTimeNoHearingDelay = 0;
    Timer m_DelayTimer = null;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptt_test);

        m_Context = PttTestActivity.this;
        Log.v(m_TAG, "onCreate");

        changeTextColorFromStartToEnd(R.id.pttTestText1, "#1DB85E", 8, 23);
        changeTextColorFromStartToEnd(R.id.pttTestText11, "#1DB85E", 11, 20);
        changeTextColorFromStartToEnd(R.id.pttTestText2, "#ff0000", 8, 26);
        changeTextColorFromStartToEnd(R.id.pttTestText21, "#ff0000", 8, 19);
        changeTextColorFromStartToEnd(R.id.pttTestText22, "#ff0000", 4, 10);

        m_TextViewTestSide = findViewById(R.id.PttTestSideText);

        m_ImgBtnlistenAgain = findViewById(R.id.imgBtnPttTestListenAgain);
        m_ImgBtnlistenAgain.setOnClickListener(this);

        m_ImgBtnHearing = findViewById(R.id.imgBtnPttTestHearing);
        m_ImgBtnHearing.setOnClickListener(this);

        m_ImgBtnNoHearing = findViewById(R.id.imgBtnPttTestNoHearing);
        m_ImgBtnNoHearing.setOnClickListener(this);

        m_AppBtnStop = findViewById(R.id.btnPttTestStop);
        m_AppBtnStop.setOnClickListener(this);

        m_TextViewCurHz = findViewById(R.id.HzText);

        findAndSetHomeBack();

        m_iCur = 1;
        m_ProgressPTT = findViewById(R.id.ImageProgress) ;
        m_ProgressPTT.setProgress(m_iCur);

        m_LastTimeNoHearingDelay = 0;

        initAct();
    }

    @Override
    protected void onDestroy() {
        Log.v(m_TAG, "onDestroy");
        super.onDestroy();
        finalAct();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivityAndFinish(PttDesc01Activity.class);
    }

    private void initAct(){
        Log.v(m_TAG, String.format("initAct") );
        if(TConst.T_RIGHT == GlobalVar.g_TestSide) {
            GlobalVar.g_alPttRightThreshold.clear();
            GlobalVar.g_alPttLeftThreshold.clear();
        }

        checkTestSideAndSetText();

        m_PTT = new PTT(m_Context);
        m_PTT.playCurrent();
        m_TextViewCurHz.setText(Integer.toString(m_PTT.get_iCurHz())+"Hz");

    }

    private void finalAct(){
        Log.v(m_TAG, String.format("finalAct") );
        try{
            disableAllBtns();
            m_PTT.endTest();

        }catch (Exception e) {
            Log.v(m_TAG, "finalAct Exception " + e);
            m_PTT.endTest();

        }

    }

    private void disableAllBtns() {
        Log.v(m_TAG, String.format("disableAllBtns") );

        m_ImgBtnHearing.setEnabled(false);
        m_ImgBtnNoHearing.setEnabled(false);
        m_ImgBtnlistenAgain.setEnabled(false);
        m_AppBtnStop.setEnabled(false);
        m_ImgBtnBack.setEnabled(false);
        m_ImgBtnHome.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgBtnPttTestListenAgain) {
            Log.v(m_TAG, "onClick - imgBtnPttTestListenAgain");
            m_PTT.playCurrent();

        }
        onClickHearingOrNoHearing(view);
        onClickTestStop(view);
        onClickHomeBack(view);

    }

    private void onClickTestStop(View view) {
        if (view.getId() == R.id.btnPttTestStop) {
            Log.v(m_TAG, "onClick - btnPttTestStop");
            m_PTT.playStop();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("테스트 중지하고 나가기").setMessage("테스트를 저장하지 않고 중지합니다.\n 메뉴로 나가시겠습니까?");
            builder.setPositiveButton("메뉴로 나가기", this);
            builder.setNegativeButton("테스트 계속하기", this);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }

    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack ) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(PttDesc01Activity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnHome");
            startActivityAndFinish(MenuActivity.class);

        }

    }

    private void onClickHearingOrNoHearing(View view) {
        if (view.getId() == R.id.imgBtnPttTestHearing) {
            onClickHearing();
        } else if (view.getId() == R.id.imgBtnPttTestNoHearing) {
            onClickNoHearing();
        }
    }

    private void onClickHearing() {
        Log.v(m_TAG, "onClick - imgBtnPttTestHearing");
        m_PTT.saveAnswer(TConst.HEARING);
        checkTestEndAndNextDBHLPlay();

        m_LastTimeNoHearingDelay = 0;

//        checkAndStopDelayTimer();
//        m_ImgBtnNoHearing.setVisibility(View.VISIBLE);
    }

    private void checkAndStopDelayTimer(){
        if(m_DelayTimer != null){
            m_DelayTimer.cancel();
            m_DelayTimer = null;
        }
    }

    private void onClickNoHearing() {
        Log.v(m_TAG, String.format(
                "onClick - imgBtnPttTestNoHearing : Cur=%tT %tL, Prev=%tT %tL",
                System.currentTimeMillis(), System.currentTimeMillis(),
                m_LastTimeNoHearingDelay, m_LastTimeNoHearingDelay) );

//        checkAndStopDelayTimer();
//        m_DelayTimer = new Timer();
//        m_DelayTimer.schedule(new TimerTaskShowNoHearingButton(), TConst.PTT_NO_HEARING_DELAY);
//        m_ImgBtnNoHearing.setVisibility(View.INVISIBLE);

        if( isNotYetTimeNoHearingDelay() ) {
            Log.v(m_TAG, "onClick - imgBtnPttTestNoHearing : Not Yet Time Then No Action");
            return;
        }

        m_PTT.saveAnswer(TConst.NO_HEARING);
        checkTestEndAndNextDBHLPlay();

        m_LastTimeNoHearingDelay = System.currentTimeMillis();
    }

    private boolean isNotYetTimeNoHearingDelay() {
        return System.currentTimeMillis() - m_LastTimeNoHearingDelay < TConst.PTT_NO_HEARING_DELAY;
    }

    public class TimerTaskShowNoHearingButton extends TimerTask {
        @Override
        public void run() {
            PttTestActivity.this.runOnUiThread(new RunShowNoHearingBtn());
            Log.v(m_TAG, "TimerTaskShowNoHearingButton");
        }

    }

    public class RunShowNoHearingBtn implements Runnable {
        @Override
        public void run() {
            m_ImgBtnNoHearing.setVisibility(View.VISIBLE);
        }

    }

    private void checkTestEndAndNextDBHLPlay() {
        if(m_PTT.isEnd()){
            saveResultAndChangeAct();
            return;
        }

        m_iCur =  m_PTT.get_iCurProgress() + 1;
        m_ProgressPTT.setProgress(m_iCur);

        m_PTT.playCurrent();
        m_TextViewCurHz.setText(Integer.toString(m_PTT.get_iCurHz())+"Hz");
    }

    private void saveResultAndChangeAct(){
        disableAllBtns();
        sortAndSaveResultToGlobalVar();
        showChangeSideMessage();
        checkSideAndStartActivity();

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
            if(TConst.T_RIGHT == GlobalVar.g_TestSide){
                GlobalVar.g_TestSide = TConst.T_LEFT;
                startActivityAndFinish(PttPreTestActivity.class);

            } else if(TConst.T_LEFT == GlobalVar.g_TestSide){
                savePttResultToDatabase();
                startActivityAndFinish(PttResult01Activity.class);
            }
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (BUTTON_POSITIVE == which){
            Toast.makeText(getApplicationContext(), "메뉴로 이동합니다.", Toast.LENGTH_SHORT).show();
            startActivityAndFinish(MenuActivity.class);
        } else {
            Toast.makeText(getApplicationContext(),"테스트를 계속합니다.", Toast.LENGTH_SHORT).show();
            m_PTT.playCurrent();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Log.d(m_TAG, "onKeyDown - KEYCODE_VOLUME_DOWN");
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                Log.d(m_TAG, "onKeyDown - KEYCODE_VOLUME_UP");
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Log.d(m_TAG, "onKeyUp - KEYCODE_VOLUME_DOWN");
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                Log.d(m_TAG, "onKeyUp - KEYCODE_VOLUME_UP");
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    private void savePttResultToDatabase() {
        try{
            trySavePttResultToDatabase();

        }catch (Exception e) {
            Log.v(m_TAG, "savePttResultToDatabase Exception " + e);
            m_PTT.endTest();
        }

    }

    private void trySavePttResultToDatabase() {
        Log.v(m_TAG, String.format("savePttResultToDatabase") );

        if( !m_PTT.isEnd() ) {
            Log.v(m_TAG, String.format("savePttResultToDatabase ---- return is not end PTT Test") );
            return;
        }

        PttDAO pttDAO = new PttDAO(m_Context);

        pttDAO.setAccount(GlobalVar.g_AccLogin);
        pttDAO.setResultThreshold(GlobalVar.g_alPttLeftThreshold, GlobalVar.g_alPttRightThreshold);
        pttDAO.savePttResult();

        GlobalVar.g_TestGroup = pttDAO.getTestGroup();

        pttDAO.releaseAndClose();

    }

    private void sortAndSaveResultToGlobalVar(){
        Log.v(m_TAG, String.format("sortAndSaveResultToGlobalVar") );

        ArrayList<PttThreshold> alPttResultSort = m_PTT.getSortedResultList();

        for(PttThreshold resultOne : alPttResultSort){
            Log.v(m_TAG, String.format("result Side:%d Hz:%d, dB:%d",
                    GlobalVar.g_TestSide, resultOne.get_Hz(), resultOne.get_DBHL()) );
        }

        if(TConst.T_RIGHT == GlobalVar.g_TestSide){
            GlobalVar.g_alPttRightThreshold = alPttResultSort;

        } else if(TConst.T_LEFT == GlobalVar.g_TestSide){
            GlobalVar.g_alPttLeftThreshold = alPttResultSort;

        }
    }

    private  void checkTestSideAndSetText(){
        if(TConst.T_LEFT == GlobalVar.g_TestSide){
            m_TextViewTestSide.setText("왼쪽 테스트");

        } else if(TConst.T_RIGHT == GlobalVar.g_TestSide) {
            m_TextViewTestSide.setText("오른쪽 테스트");
        }
    }

    private void changeTextColorFromStartToEnd(int idRes, String strColor, int iStart, int iEnd){
        TextView tvText = findViewById(idRes);
        String strText = tvText.getText().toString();
        SpannableStringBuilder ssbText = new SpannableStringBuilder(strText);
        ssbText.setSpan(new ForegroundColorSpan(Color.parseColor(strColor)),iStart,iEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvText.setText(ssbText);
    }

    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }


}