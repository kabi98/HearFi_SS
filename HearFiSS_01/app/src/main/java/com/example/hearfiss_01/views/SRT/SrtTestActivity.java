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
import com.example.hearfiss_01.entity.HearingTest.HrTestUnit;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.ImageProgress;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.PTT.PttDesc01Activity;
import com.example.hearfiss_01.views.WRS.WrsPreTestActivity;
import com.example.hearfiss_01.views.WRS.WrsResult01Activity;
import com.example.hearfiss_01.views.WRS.WrsTestActivity;

import java.util.ArrayList;
import java.util.Timer;

public class SrtTestActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnKeyListener {

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

    int limit = 0;


    SRT m_SRT = null;

    InputMethodManager imm = null;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_test);
        m_Context = SrtTestActivity.this;

        //--------------------------------TEST DETAIL CONMENT TEXTVIEW ---------------------------//
        m_TextViewTestSide = findViewById(R.id.srtTestSideTitle);
        if(GlobalVar.g_TestSide == TConst.T_LEFT){
            m_TextViewTestSide.setText("왼쪽 귀 테스트 중입니다.");
        } else {
            m_TextViewTestSide.setText("오른쪽 귀 테스트 중입니다.");
        }

/*
        String g_MS = GlobalVar.g_MenuSide;
        String side = "";
        if(g_MS.equals("RIGHT")){
            side = getResources().getString(R.string.RIGHT);
        }else{
            side = getResources().getString(R.string.LEFT);
        }
        m_TextViewTestSide = (TextView) findViewById(R.id.srtTestSideTitle);
        m_TextViewTestSide.setText(side);
*/

        m_AppBtnNext = (AppCompatButton) findViewById(R.id.srtnextBtn);
        m_AppBtnNext.setOnClickListener(this);

/*
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

        // ------------------------------Home Button----------------------------------------------//
        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

        //--------------------------------USER EDITER TEXT BOX------------------------------------//

        m_EditSRT = (EditText) findViewById(R.id.srt_Edit);
        m_EditSRT.setOnKeyListener(this);

        //-------------------------------- 음성 인식 버튼-------------------------------------------//
//        m_AppBtnAnswerVoice = findViewById(R.id.srtVoiceAnswerBtn);
//        m_AppBtnAnswerVoice.setOnClickListener(this);

        //--------------------------------NEXT QUESTION BUTTON------------------------------------//

        m_AppBtnNext = (AppCompatButton) findViewById(R.id.srtnextBtn);
        m_AppBtnNext.setOnClickListener(this);

        //--------------------------------TEST PROGRESSBAR ---------------------------------------//

//        m_ProgressBar = findViewById(R.id.progress_bar);
//        // PROGRESSBAR
//        // -> setProgress 함수를 사용하기 위해서는 setIndeterminate(불확정적 모드를 false)해야 한다.
//        m_ProgressBar.setIndeterminate(false);
//        // setProgress(0)으로 초기화
//        m_ProgressBar.setProgress(0);
//        // setVisibility(params)
//        //  View.GONE : 해당 뷰를 감춘다.
//        //  View.VISIBLE : 해당 뷰를 보여준다.
//        //  View.INVISIBLE : 해당 뷰를 감춘다(공간은 차지).
//        m_ProgressBar.setVisibility(View.GONE); // Progressbar 시각기능 x

        //------------------------------ 음성 인식 기능 권한 허용 여부--------------------------------//
//        if ( Build.VERSION.SDK_INT >= 23 ){
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,Manifest.permission.RECORD_AUDIO}, PERMISSION);
//        }
//        stt_intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        stt_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
//        stt_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");
//
//        STTView = findViewById(R.id.STTView);
//        sttFinishBtn = findViewById(R.id.sttFinishBtn);
//        sttTextView2 = findViewById(R.id.sttTextView2);
//
//        m_ProgressBar = findViewById(R.id.progress_bar);
//        m_ProgressBar.setIndeterminate(false);
//        m_ProgressBar.setProgress(0);
*/


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
//        m_AppBtnNext.setVisibility(View.GONE);
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
//        m_ProgressBar.setVisibility(View.VISIBLE);
        if(m_AppBtnNext.equals("시작 하기")) {
            Log.v("speechTestMain", "어음 청취 역치 테스트");
            m_AppBtnNext.setText("다음 문제");
            return m_SRT.playCurrent();
        }
        return 1;
    }

    public int NextBtnClick() {
        Log.v(m_TAG, "NextBtnClick()");
        Log.v(m_TAG, String.format("NextBtnClick() type : %d, side : %d ", GlobalVar.g_TestType, GlobalVar.g_TestSide) );

        showChangeSideMessage();
        checkSideAndStartActivity();

//        String strAnswer = m_EditSRT.getText().toString();
//        m_EditSRT.setText("");
//            m_SRT.saveAnswer(strAnswer);

//             int progress = (int)(((float) user_lists.size() / limit) * 100);
//            m_ProgressBar.setProgress(progress);
//            sttTextView2.setText("");
//            STTView.setVisibility(View.GONE);


//            if (m_SRT.isEnd()) {
//                m_SRT.endTest();
//                SideMessage(SrtTestActivity.this);
//                SideChkNStart(SrtTestActivity.this);
//            } else {
//                m_SRT.playNext();
//            }
        return 1;
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



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.v("speechTestMain", "imgBtnBack.OnClick()");
            Intent intent = new Intent(getApplicationContext(), SrtPreTestActivity.class);
            startActivity(intent);
        } else if(view.getId() == R.id.srtnextBtn) {
            Log.v("speechTestMain", "srtnextBtn.OnClick()");
            NextBtnClick();
        } else if(view.getId() == R.id.srtVoiceAnswerBtn) {
//            Log.v("speechTestMain", "srtVoiceAnswer.OnClick()");
//                    sttTextView2.setText("");
//                    STTView.setVisibility(View.VISIBLE);
//                    SttTest();
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

//    public void SttTest(){
//        SpeechRecognizer mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
//        mRecognizer.setRecognitionListener(listener);
//        mRecognizer.startListening(stt_intent);
//    }
//
//    private RecognitionListener listener = new RecognitionListener() {
//        @Override
//        public void onReadyForSpeech(Bundle params) {
//            Toast.makeText(getApplicationContext(),"음성인식을 시작합니다.",Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onBeginningOfSpeech() {}
//
//        @Override
//        public void onRmsChanged(float rmsdB) {}
//
//        @Override
//        public void onBufferReceived(byte[] buffer) {}
//
//        @Override
//        public void onEndOfSpeech() {}
//
//        @Override
//        public void onError(int error) {
//            String message;
//
//            switch (error) {
//                case SpeechRecognizer.ERROR_AUDIO:
//                    message = "오디오 에러";
//                    break;
//                case SpeechRecognizer.ERROR_CLIENT:
//                    message = "클라이언트 에러";
//                    break;
//                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
//                    message = "퍼미션 없음";
//                    break;
//                case SpeechRecognizer.ERROR_NETWORK:
//                    message = "네트워크 에러";
//                    break;
//                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
//                    message = "네트웍 타임아웃";
//                    break;
//                case SpeechRecognizer.ERROR_NO_MATCH:
//                    message = "찾을 수 없음";
//                    break;
//                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
//                    message = "RECOGNIZER가 바쁨";
//                    break;
//                case SpeechRecognizer.ERROR_SERVER:
//                    message = "서버가 이상함";
//                    break;
//                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
//                    message = "말하는 시간초과";
//                    break;
//                default:
//                    message = "알 수 없는 오류임";
//                    break;
//            }
//
//            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다. : " + message,Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onResults(Bundle results) {
//            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어준다.
//            ArrayList<String> matches =
//                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//
//            for(int i = 0; i < matches.size() ; i++){
//                sttTextView2.setText(matches.get(i));
//                user_Answer = sttTextView2.getText().toString();
//            }
//        }
//
//        @Override
//        public void onPartialResults(Bundle partialResults) {}
//
//        @Override
//        public void onEvent(int eventType, Bundle params) {}
//    };


}
