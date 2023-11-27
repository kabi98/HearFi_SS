package com.example.hearfiss_01.views.SRT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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
import com.example.hearfiss_01.audioTest.SRT.SrtThreshold;
import com.example.hearfiss_01.audioTest.SRT.SrtUnit;
import com.example.hearfiss_01.db.DTO.HrTestUnit;
import com.example.hearfiss_01.db.sql.SQLiteControl;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.MenuActivity;

import java.util.ArrayList;

public class SrtTestActivity extends AppCompatActivity
        implements View.OnClickListener, TextView.OnEditorActionListener {

    String m_TAG = "SrtTestActivity";
    Context m_Context;

    AppCompatButton m_AppBtnNext, m_AppBtnAnswerVoice, sttFinishBtn;

    LinearLayout STTView, STTActiveLayout;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    EditText m_EditSRT;

    Intent stt_intent;



    TextView m_TextViewTestSide, sttTextView1, sttTextView2;

    String user_Answer = "";

    ArrayList<HrTestUnit> user_lists= new ArrayList<HrTestUnit>();

    ArrayList<SrtUnit> m_alSrtUnit = null;

    SrtScore m_SrtScore = null;

    int m_iLimit = 0;

    boolean m_isActChanging;

    SRT m_SRT = null;

    final int PERMISSION = 1;


    InputMethodManager imm = null;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_test);

        if ( Build.VERSION.SDK_INT >= 23 ){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

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

        m_AppBtnNext = findViewById(R.id.srtnextBtn);
        m_AppBtnNext.setOnClickListener(this);

        stt_intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        stt_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        stt_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        STTActiveLayout = findViewById(R.id.STTActiveLayout);
        STTView = findViewById(R.id.STTView);
        sttTextView1 = findViewById(R.id.sttTextView1);
        sttTextView2 = findViewById(R.id.sttTextView2);
        sttFinishBtn = findViewById(R.id.sttFinishBtn);


        m_AppBtnAnswerVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                STTView.setVisibility(View.VISIBLE);
                m_AppBtnAnswerVoice.setVisibility(View.GONE);
                sttFinishBtn.setClickable(true);
                STTActiveLayout.setVisibility(View.VISIBLE);
                imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(m_EditSRT.getWindowToken(), 0);
                sttTextView2.setText("");
                user_Answer = "";
                sttTextView1.setText(user_Answer);
                sttTest();
            }
        });

        sttFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_AppBtnAnswerVoice.setVisibility(View.VISIBLE);
                STTView.setVisibility(View.GONE);
            }
        });

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
        m_SRT.startTest();
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
            m_AppBtnAnswerVoice.setVisibility(View.VISIBLE);
            m_AppBtnNext.setText("다음 문제");
            ClickedSrtNextBtn();

        } else if(m_AppBtnAnswerVoice.isSelected()){
            Log.v(m_TAG, "onClick - srtVoiceAnswerBtn");
            // 음성 인식 기능 활성화
            STTView.setVisibility(View.VISIBLE);
            Log.v(m_TAG, "STTView set Visible");
        }

        onClickHomeBack(view);

    }
    private void ClickedSrtNextBtn() {
        Log.v(m_TAG, "onClick - sound play");
        if (m_AppBtnNext.isSelected()) {
            m_SRT.playCurrent();
            initAct();
        }
        if(m_isActChanging) {
            return;
        }

        Log.v(m_TAG, "onClick - inputText");
        String strAnswer = m_EditSRT.getText().toString();
        m_EditSRT.setText("");

        Log.v( m_TAG, String.format("onClick - Pre save Answer %s", strAnswer) );

        m_SRT.saveAnswer(strAnswer);
        // 진행바 생략 여부 -------------
        // int curPercent = m_SRT.getCurrentProgress();
        // m_ProgressBar.setProgress(curPercent);

        checkTestEndAndNextPlay();
    }

    private void checkTestEndAndNextPlay() {
        Log.v( m_TAG, String.format("****** checkTestEndAndNextPlay isEnd :%s", m_SRT.isEnd()) );
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

        Log.v( m_TAG, String.format("****** saveResultAndChangeAct Side :%d", GlobalVar.g_TestSide) );
        if(TConst.T_RIGHT == GlobalVar.g_TestSide){
            sortAndSaveResultToGlobalVar();

            m_SRT.endTest();

        } else if(TConst.T_LEFT == GlobalVar.g_TestSide){
            sortAndSaveResultToGlobalVar();

            if(m_SRT.isEnd()){
                saveSrtResultToDatabase();
            }
            m_SRT.endTest();
        }

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


    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(SrtDesc01Activity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(MenuActivity.class);

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
                saveSrtResultToDatabase();
                startActivityAndFinish(SrtResult01Activity.class);

            }

        }
    }

    private void sortAndSaveResultToGlobalVar() {
        Log.v(m_TAG, String.format("sortAndSaveResultToGlobalVar") );

        ArrayList<SrtThreshold> alSrtResultSort = m_SRT.getSortedResultList();

        for(SrtThreshold resultOne : alSrtResultSort){
            Log.v(m_TAG, String.format("result Side:%d  dB:%d",
                    GlobalVar.g_TestSide, resultOne.get_DBHL()) );
        }

        if(TConst.T_RIGHT == GlobalVar.g_TestSide){
            GlobalVar.g_alSrtRightThreshold = alSrtResultSort;

        } else if(TConst.T_LEFT == GlobalVar.g_TestSide){
            GlobalVar.g_alSrtLeftThreshold = alSrtResultSort;

        }
    }
    private void saveSrtResultToDatabase() {
        // not yet
        try{
            trySaveSrtResultToDatabase();

        }catch (Exception e) {
            Log.v(m_TAG, "saveSrtResultToDatabase Exception " + e);
            m_SRT.endTest();
        }
    }

    private void trySaveSrtResultToDatabase() {

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
        }

        */
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











    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }
    private void sttTest() {
        SpeechRecognizer mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(stt_intent);
    }
    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getApplicationContext(),"음성인식을 시작합니다.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {}

        @Override
        public void onRmsChanged(float rmsdB) {}

        @Override
        public void onBufferReceived(byte[] buffer) {}

        @Override
        public void onEndOfSpeech() {}

        @Override
        public void onError(int error) {
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다. : " + message,Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onResults(Bundle results) {
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어준다.
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for(int i = 0; i < matches.size() ; i++){
                m_EditSRT.setText(matches.get(i));
                user_Answer = m_EditSRT.getText().toString();
                sttTextView2.setText(user_Answer);
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {}

        @Override
        public void onEvent(int eventType, Bundle params) {}
    };

}
