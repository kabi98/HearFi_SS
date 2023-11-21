package com.example.hearfiss_01.views.SRT;

import static android.content.DialogInterface.BUTTON_POSITIVE;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.audioTest.PTT.PTT;
import com.example.hearfiss_01.audioTest.SRT.SRT;
import com.example.hearfiss_01.db.sql.SQLiteControl;
import com.example.hearfiss_01.db.sql.SQLiteHelper;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.ImageProgress;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.PTT.PttDesc01Activity;

import java.util.ArrayList;
import java.util.Timer;

public class SrtTestActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnKeyListener {

    String m_TAG = "SrtTestActivity";

    AppCompatButton m_AppBtnNext, m_AppBtnAnswerVoice, sttFinishBtn;

    LinearLayout STTView;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    ProgressBar m_ProgressBar = null;

    EditText m_EditSRT;

    Context context;

    Intent stt_intent;

    final int PERMISSION = 1;

    TextView m_TextViewTestSide, sttTextView2;



    SRT m_SRT = null;

    InputMethodManager imm = null;


    @Override
    public void onBackPressed() {
        return;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_test);

        m_SRT = new SRT(SrtTestActivity.this);
        m_SRT.startTest();
        m_SRT.playCurrent();

        //--------------------------------TEST TYPE CONMENT TEXTVIEW ---------------------------//
        String[] type_En = getResources().getStringArray(R.array.type_en);
        String[] type_kr = getResources().getStringArray(R.array.type_kr);
        m_TextViewTestSide = (TextView) findViewById(R.id.srtTestSideTitle);
        for(int i=0; i<type_En.length; i++){
            if(GlobalVar.g_MenuType.equals(type_En[i])){
                m_TextViewTestSide.setText(type_kr[i]);
                break;
            }
        }
        //--------------------------------TEST DETAIL CONMENT TEXTVIEW ---------------------------//
        String g_MS = GlobalVar.g_MenuSide;
        String side = "";
        if(g_MS.equals("RIGHT")){
            side = getResources().getString(R.string.RIGHT);
        }else{
            side = getResources().getString(R.string.LEFT);
        }
        m_TextViewTestSide = (TextView) findViewById(R.id.srtTestSideTitle);
        m_TextViewTestSide.setText(side);

        // ------------------------------Home Button----------------------------------------------//
        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

        //--------------------------------USER EDITER TEXT BOX------------------------------------//

        m_EditSRT = (EditText) findViewById(R.id.srt_Edit);
        m_EditSRT.setOnKeyListener(this);

        //-------------------------------- 음성 인식 버튼-------------------------------------------//
        m_AppBtnAnswerVoice = findViewById(R.id.srtVoiceAnswerBtn);
        m_AppBtnAnswerVoice.setOnClickListener(this);

        //--------------------------------NEXT QUESTION BUTTON------------------------------------//

        m_AppBtnNext = (AppCompatButton) findViewById(R.id.srtnextBtn);
        m_AppBtnNext.setOnClickListener(this);

        //--------------------------------TEST PROGRESSBAR ---------------------------------------//

        m_ProgressBar = findViewById(R.id.progress_bar);
        // PROGRESSBAR
        // -> setProgress 함수를 사용하기 위해서는 setIndeterminate(불확정적 모드를 false)해야 한다.
        m_ProgressBar.setIndeterminate(false);
        // setProgress(0)으로 초기화
        m_ProgressBar.setProgress(0);
        // setVisibility(params)
        //  View.GONE : 해당 뷰를 감춘다.
        //  View.VISIBLE : 해당 뷰를 보여준다.
        //  View.INVISIBLE : 해당 뷰를 감춘다(공간은 차지).
        m_ProgressBar.setVisibility(View.GONE); // Progressbar 시각기능 x

        //------------------------------ 음성 인식 기능 권한 허용 여부--------------------------------//
        if ( Build.VERSION.SDK_INT >= 23 ){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }
        stt_intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        stt_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        stt_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        STTView = findViewById(R.id.STTView);
        sttTextView2 = findViewById(R.id.sttTextView2);
        sttFinishBtn = findViewById(R.id.sttFinishBtn);

    }
    public void SideMessage(Context context){
        if(GlobalVar.g_MenuSide.equals("RIGHT")) {
            String info = "오른쪽 테스트 종료되었습니다.\n" +
                    "왼쪽 테스트 진행하겠습니다.\n";
            Toast toast = Toast.makeText(context,info,Toast.LENGTH_SHORT);
            toast.show();
        }else{
            String info = "왼쪽 테스트 종료되었습니다.\n" +
                    "결과 화면으로 넘어가겠습니다.\n";
            Toast toast = Toast.makeText(context,info,Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void SideChkNStart(Context context){
        m_AppBtnNext.setVisibility(View.GONE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(GlobalVar.g_MenuSide.equals("RIGHT")) {
                    GlobalVar.g_MenuSide = "LEFT";
                    Intent intent = new Intent(getApplicationContext(), SrtPreTestActivity.class);
                    startActivity(intent);
                } else {
                    GlobalVar.g_MenuSide = "";
                    GlobalVar.g_TestGroup = null;
                    Intent intent = new Intent(getApplicationContext(), SrtResult01Activity.class);
                    startActivity(intent);
                }
            }
        }, 2000);

        return;
    }

    public int PlayBtnClick() {
        Log.v("speechTestMain", "PlayBtnClick()");
        m_AppBtnNext = (AppCompatButton) findViewById(R.id.srtnextBtn);
        m_AppBtnNext.setVisibility(View.VISIBLE);
        if(GlobalVar.g_MenuType.equals("SRT")) {
            Log.v("speechTestMain", "어음 청취 역치 테스트");
            return m_SRT.playCurrent();
        }
        return 1;
    }

    public int NextBtnClick() {
        Log.v("speechTestMain", "NextBtnClick()");
        Log.v("230824", GlobalVar.g_MenuSide);

            String strAnswer = m_EditSRT.getText().toString();
            m_EditSRT.setText("");
            m_SRT.saveAnswer(strAnswer);
            if (m_SRT.isEnd()) {
                m_SRT.endTest();
                SideMessage(SrtTestActivity.this);
                SideChkNStart(SrtTestActivity.this);
            } else {
                m_SRT.playNext();
            }
            return 1;
        }
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.imgBtnBack) {
                    Log.v("speechTestMain", "imgBtnBack.OnClick()");
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                } else if(view.getId() == R.id.srtnextBtn) {
                    Log.v("speechTestMain", "srtnextBtn.OnClick()");
                    NextBtnClick();
                } else if(view.getId() == R.id.srtVoiceAnswerBtn) {
                    Log.v("speechTestMain", "srtVoicAnswerBtn.OnClick()");
                    if(!GlobalVar.g_MenuType.equals("SRT")){
                        m_ProgressBar.setVisibility(View.VISIBLE);
                    }

                    if(m_AppBtnAnswerVoice.isSelected()){
                        //음원 일시 중지
                        m_AppBtnAnswerVoice.setSelected(false);
                        Log.v("check" ,"value : play");
                    }else{
                        //음원 재생 중
                        m_AppBtnAnswerVoice.setSelected(true);
                        Log.v("check" ,"value : pause");
                        PlayBtnClick();
                    }
                }

            }
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if(keyCode == keyEvent.KEYCODE_ENTER
                && keyEvent.getAction() == KeyEvent.ACTION_UP){
            if(view.getId() == R.id.srt_Edit){
                NextBtnClick();
                return true;
            }
        }
        return false;
    }

}
