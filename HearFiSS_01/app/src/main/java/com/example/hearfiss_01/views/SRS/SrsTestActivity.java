package com.example.hearfiss_01.views.SRS;

import android.content.Context;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.audioTest.SRS.SRS;
import com.example.hearfiss_01.db.DTO.AmTrack;
import com.example.hearfiss_01.db.dao.SrsDAO;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.MenuActivity;

import java.util.ArrayList;

public class SrsTestActivity extends AppCompatActivity
        implements View.OnClickListener, TextView.OnEditorActionListener{

    String m_TAG = "SrsTestActivity";
    Context m_Context;
    Intent stt_intent;
    LinearLayout STTView, STTActiveLayout;

    TextView  sttTextView1, sttTextView2;

    AppCompatButton m_AppBtnNext, m_AppBtnVoiceAnswer,  sttFinishBtn;
    TextView m_TextViewTestSide;
    EditText m_EditSRS;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    SRS m_SRS = null;
    ProgressBar m_ProgressBar;
    int m_iLimit = 10;
    boolean m_isActChanging;

    InputMethodManager imm = null;

    String user_Answer = "";

    AmTrack m_atCur = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srs_test);

        m_Context = SrsTestActivity.this;

        m_iLimit = GlobalVar.g_srsNumber;

        m_AppBtnVoiceAnswer = findViewById(R.id.srsVoiceAnswerBtn);

        m_AppBtnNext = findViewById(R.id.srsnextBtn);
        m_AppBtnNext.setOnClickListener(this);


        stt_intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        stt_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        stt_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        STTActiveLayout = findViewById(R.id.STTActiveLayout);
        STTView = findViewById(R.id.STTView);
        sttTextView1 = findViewById(R.id.sttTextView1);
        sttTextView2 = findViewById(R.id.sttTextView2);
        sttFinishBtn = findViewById(R.id.sttFinishBtn);



        m_AppBtnVoiceAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                STTView.setVisibility(View.VISIBLE);
                m_AppBtnVoiceAnswer.setVisibility(View.GONE);
                sttFinishBtn.setClickable(true);
                STTActiveLayout.setVisibility(View.VISIBLE);
                imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(m_EditSRS.getWindowToken(), 0);
                sttTextView2.setText("");
                user_Answer = "";
                sttTextView1.setText(user_Answer);
                sttTest();
            }
        });

        sttFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_AppBtnVoiceAnswer.setVisibility(View.VISIBLE);
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
//        super.onBackPressed();
        startActivityAndFinish(SrsDesc01Activity.class);
    }


    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

    }

    private void initAct(){
        Log.v(m_TAG, "initAct");
        if(TConst.T_RIGHT == GlobalVar.g_TestSide) {
            GlobalVar.g_alSrsRight.clear();
            GlobalVar.g_alSrsLeft.clear();
        }
        m_isActChanging = false;
        m_SRS = new SRS(m_Context);
        m_SRS.setM_tsLimit(m_iLimit );
        m_SRS.setUserVolume(GlobalVar.g_srsUserVolume);
        if (GlobalVar.g_TestSide == TConst.T_LEFT){
            m_SRS.setM_Type("sl_a2");
        }else{
            m_SRS.setM_Type("sl_a1");
        }

    }

    private void finalAct(){
        Log.v(m_TAG, "finalAct");
        m_SRS.endTest();
        m_AppBtnNext.setClickable(false);
        m_AppBtnVoiceAnswer.setClickable(false);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(m_EditSRS.getWindowToken(), 0);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.srsnextBtn) {
            Log.v(m_TAG, "onClick - nextBtn Click ");
            m_AppBtnVoiceAnswer.setVisibility(View.VISIBLE);

            Log.v(m_TAG, String.format("onClick - nextBtn Click %s ", m_AppBtnNext.getText().toString()) );
            String strBtnText = m_AppBtnNext.getText().toString();

            if( strBtnText.equals("시작 하기") ){
                Log.v(m_TAG, String.format("onClick - nextBtn is Equal %s %s", m_AppBtnNext.getText().toString(), "시작 하기") );
                m_SRS.playCurrent();
                m_AppBtnNext.setText("다음 문제");

            } else {
                Log.v(m_TAG, String.format("onClick - nextBtn is Not Equal %s %s", m_AppBtnNext.getText().toString(), "시작 하기") );
                ClickedSrsNextBtn();

            }

        } else if(m_AppBtnVoiceAnswer.isSelected()){
            Log.v(m_TAG, "onClick - voice answer Click");
            STTView.setVisibility(View.VISIBLE);
            Log.v(m_TAG, "STTView set Visible");
        }



        onClickHomeBack(view);
    }

    public void ClickedSrsNextBtn(){
        Log.v(m_TAG, "NextBtnClick - 문장 인지도 테스트");

        if(m_isActChanging) {
            return;
        }
        String strAnswer = m_EditSRS.getText().toString();

        Log.v(m_TAG, "NextBtnClick - Answer" + strAnswer);
        int unitSize = m_SRS.SaveAnswer(strAnswer);

        m_EditSRS.setText("");

        Log.v(m_TAG, "srstest - save answer : "+ strAnswer);

        int iProgress = (int)( ( ( (float)(m_SRS.getCount() + 1) / m_iLimit) ) * 100);

        m_ProgressBar.setProgress( iProgress );

        m_SRS.playNext();

        if(m_SRS.isEnd()){
            int s = m_SRS.scoring();
            Log.v(m_TAG, "문장 인지도 테스트 결과 : " + s);
            checkTestEndAndNextPlay();
        }

        Log.v(m_TAG, "NextBtnClick - progressbar value : " + iProgress);


        /*
        String strAnswer = m_EditWRS.getText().toString();
        m_EditWRS.setText("");

        m_WRS.SaveAnswer(strAnswer);
        int curPercent = m_WRS.getCurrentProgress();
        m_ProgressBar.setProgress(curPercent);
        Log.v(m_TAG, "NextBtnClick - progressbar value : " + curPercent);



 */
    }

    private void checkTestEndAndNextPlay() {
        if (m_SRS.isEnd()){
            m_ProgressBar.setProgress(100);
            m_AppBtnNext.setClickable(false);
            m_AppBtnVoiceAnswer.setClickable(false);

            saveResultAndChangeAct();
        }else {
            m_SRS.playNext();
        }

    }

    private void saveResultAndChangeAct() {
        m_isActChanging = true;
        if (TConst.T_RIGHT == GlobalVar.g_TestSide){
            saveSrsResultToGlobalVar();
            m_SRS.endTest();
        }else if (TConst.T_LEFT == GlobalVar.g_TestSide){
            saveSrsResultToGlobalVar();
            if (m_SRS.isEnd()){
                saveSrsResultToDatabase();
            }
            m_SRS.endTest();
        }

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
    private void saveSrsResultToGlobalVar() {
        Log.v(m_TAG, String.format("saveSrsResultToGlobalVar"));
        if(GlobalVar.g_TestSide == TConst.T_RIGHT) {
            GlobalVar.g_alSrsRight = m_SRS.getScoreList();
            for(int i=0; i< GlobalVar.g_alSrsRight.size(); i++){
                Log.v(m_TAG,
                        String.format(" SRS RESULT Right : %d, %s ",
                                i , GlobalVar.g_alSrsRight.get(i).toString()) );
            }

        } else  {
            GlobalVar.g_alSrsLeft = m_SRS.getScoreList();
            for(int i=0; i< GlobalVar.g_alSrsLeft.size(); i++){
                Log.v(m_TAG,
                        String.format(" SRS RESULT Left : %d, %s ",
                                i , GlobalVar.g_alSrsLeft.get(i).toString() ) );
            }

        }
    }


    private void saveSrsResultToDatabase() {
        Log.v(m_TAG, "saveSrsResultToDatabase");

        SrsDAO srsDAO = new SrsDAO(m_Context);
        Log.v(m_TAG, "SrsDAO object created");


        srsDAO.setAccount(GlobalVar.g_AccLogin);

        srsDAO.setResultList(GlobalVar.g_alSrsLeft, GlobalVar.g_alSrsRight);
        Log.v(m_TAG, "Result list set for Left: " + GlobalVar.g_alSrsLeft.size() + " items, Right: " + GlobalVar.g_alSrsRight.size() + " items");


        srsDAO.saveTestResults();

        GlobalVar.g_TestGroup = srsDAO.getTestGroup();

        if (GlobalVar.g_TestGroup != null) {
            Log.v(m_TAG, "Test Group: " + GlobalVar.g_TestGroup.toString());
        } else {
            Log.v(m_TAG, "Test Group is null");
        }


        srsDAO.releaseAndClose();
    }
       /*
        Log.v(m_TAG, String.format("saveWrsResultToDatabase") );

        printBothResult();

        WrsDAO wrsDAO = new WrsDAO(m_Context);
        wrsDAO.setAccount(GlobalVar.g_AccLogin);
        wrsDAO.setResultList(GlobalVar.g_alWrsLeft, GlobalVar.g_alWrsRight);

        wrsDAO.saveTestResults();
        GlobalVar.g_TestGroup = wrsDAO.getTestGroup();

        wrsDAO.releaseAndClose();

        */





    private void setSideTextAndProgressBar() {
        //----------------------------------SIDE TEXT SETTING-------------------------------------//
        m_TextViewTestSide = findViewById(R.id.srsTestSideTitle);
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
            ClickedSrsNextBtn();
            return true;
        }


        return false;
    }


    private void setupAnswerEditAndShowSoftKeyboard() {
        // 키보드 자동완성 제거

        m_EditSRS = findViewById(R.id.srs_Edit);
        m_EditSRS.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        m_EditSRS.setFocusableInTouchMode(true);
        m_EditSRS.setOnEditorActionListener(this);

        InputMethodManager imm = (InputMethodManager) getSystemService((Context.INPUT_METHOD_SERVICE));
        m_EditSRS.requestFocus();
        imm.showSoftInput(m_EditSRS,0);


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
                startActivityAndFinish(SrsPreTestActivity.class);

            } else {
                startActivityAndFinish(SrsResult01Activity.class);

            }

        }
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
                m_EditSRS.setText(matches.get(i));
                user_Answer = m_EditSRS.getText().toString();
                sttTextView2.setText(user_Answer);
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {}

        @Override
        public void onEvent(int eventType, Bundle params) {}
    };

    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }


}