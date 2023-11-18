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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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
        implements View.OnClickListener, DialogInterface.OnClickListener, View.OnKeyListener {

    String m_TAG = "SrtTestActivity";
    Context m_Context;

    AppCompatButton m_AppBtnStop;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    Button m_NextAnswerBtn;
    ImageButton     speechTestMainPlayPause;
    ProgressBar m_SrtProgress_Bar = null;
    TextView m_TextViewTestSide, m_TextViewCurHz;
    EditText m_EditAnswer;
    InputMethodManager imm = null;
//    SRT m_SRT;
    int             m_iCur;

    long m_LastTimeNoHearingDelay = 0;
    Timer m_DelayTimer = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_test);

        m_Context = SrtTestActivity.this;
        Log.v(m_TAG, "onCreate");

        m_TextViewTestSide = findViewById(R.id.SrtTestSideText);

        m_EditAnswer = (EditText) findViewById(R.id.srtEditAnswer);
        m_EditAnswer.setOnKeyListener(this);

        speechTestMainPlayPause = (ImageButton) findViewById(R.id.speechTestMainPlayPause);
        speechTestMainPlayPause.setOnClickListener(this);

        m_NextAnswerBtn = (Button) findViewById(R.id.NextAnswerBtn);
        m_NextAnswerBtn.setOnClickListener(this);

        m_TextViewCurHz = findViewById(R.id.HzText);

        findAndSetHomeBack();

        m_SrtProgress_Bar = findViewById(R.id.progress_bar);
        // PROGRESSBAR
        // -> setProgress 함수를 사용하기 위해서는 setIndeterminate(불확정적 모드를 false)해야 한다.
        m_SrtProgress_Bar.setIndeterminate(false);
        // setProgress(0)으로 초기화
        m_SrtProgress_Bar.setProgress(0);
        // setVisibility(params)
        //  View.GONE : 해당 뷰를 감춘다.
        //  View.VISIBLE : 해당 뷰를 보여준다.
        //  View.INVISIBLE : 해당 뷰를 감춘다(공간은 차지).
        m_SrtProgress_Bar.setVisibility(View.GONE);

//        initAct();
    }

    @Override
    protected void onDestroy() {
        Log.v(m_TAG, "onDestroy");
        super.onDestroy();
//        finalAct();
    }

    @Override
    public void onBackPressed() {
        startActivityAndFinish(SrtDesc01Activity.class);
    }


//    private void initAct() {
//        Log.v(m_TAG, String.format("initAct") );
//        if(TConst.T_RIGHT == GlobalVar.g_TestSide) {
//            GlobalVar.g_alPttRightThreshold.clear();
//            GlobalVar.g_alPttLeftThreshold.clear();
//        }
//
//        checkTestSideAndSetText();
//
//        m_PTT = new PTT(m_Context);
//        m_PTT.playCurrent();
//        m_TextViewCurHz.setText(Integer.toString(m_PTT.get_iCurHz())+"Hz");
//    }

//    private void finalAct() {
//        Log.v(m_TAG, String.format("finalAct") );
//        try{
//            disableAllBtns();
//            m_PTT.endTest();
//
//        }catch (Exception e) {
//            Log.v(m_TAG, "finalAct Exception " + e);
//            m_PTT.endTest();
//
//        }
//
//    }

    private void disableAllBtns() {
        Log.v(m_TAG, String.format("disableAllBtns") );

        m_AppBtnStop.setEnabled(false);
        m_ImgBtnBack.setEnabled(false);
        m_ImgBtnHome.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.speechTestMainPlayPause) {
            Log.v(m_TAG, "onClick - speechTestMainPlayPause");
//            m_PTT.playCurrent();

        }
        onClickTestStop(view);
        onClickHomeBack(view);
    }

    private void onClickTestStop(View view) {
        if (view.getId() == R.id.speechTestMainPlayPause) {
            Log.v(m_TAG, "onClick - speechTestMainPlayPause");
//            m_PTT.playStop();

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
            startActivityAndFinish(SrtDesc01Activity.class);

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


    private void checkTestSideAndSetText() {
        if(TConst.T_LEFT == GlobalVar.g_TestSide){
            m_TextViewTestSide.setText("왼쪽 테스트");

        } else if(TConst.T_RIGHT == GlobalVar.g_TestSide) {
            m_TextViewTestSide.setText("오른쪽 테스트");
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (BUTTON_POSITIVE == which){
            Toast.makeText(getApplicationContext(), "메뉴로 이동합니다.", Toast.LENGTH_SHORT).show();
            startActivityAndFinish(MenuActivity.class);
        } else {
            Toast.makeText(getApplicationContext(),"테스트를 계속합니다.", Toast.LENGTH_SHORT).show();
//            m_PTT.playCurrent();
        }

    }
}