package com.example.hearfiss_01.views.SRT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.audioTest.PTT.PttThreshold;
import com.example.hearfiss_01.db.DTO.Account;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.PTT.HlinfoActivity;
import com.example.hearfiss_01.views.PTT.PttResult02Activity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class SrtResult02Activity extends AppCompatActivity  implements View.OnClickListener  {

    String m_TAG = "SrtResult02Activity";
    Context m_Context;

    TextView rsultContent;
    TextView m_TextTotal, m_TextCorrect, m_TextUserName, m_TextDate;


    AppCompatButton m_AppBtnHlInfo;
    ImageButton m_ImgBtnBack;
    ImageButton m_ImgBtnHome;

    TextView[] textViews = new TextView[6];
    View[] views = new View[6];

    int[] textId = {R.id.hr1, R.id.hr2, R.id.hr3, R.id.hr4, R.id.hr5, R.id.hr6};

    int[] viewId = {R.id.v1, R.id.v2, R.id.v3, R.id.v4, R.id.v5, R.id.v6};
    public static ArrayList<PttThreshold> m_alRightResult = new ArrayList<>();
    public static ArrayList<PttThreshold> m_alLeftResult = new ArrayList<>();


    Account m_Account;
    int m_TgId;
    HrTestGroup m_TestGroup;
    HrTestSet m_TestSetLeft;
    HrTestSet m_TestSetRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srt_result02);

        m_Context = SrtResult02Activity.this;

        m_Account = GlobalVar.g_AccLogin;
        m_TgId = GlobalVar.g_TestGroup.getTg_id();
        getSrtResultFromDatabase();

        m_AppBtnHlInfo = findViewById(R.id.hlinfoBtn);
        m_AppBtnHlInfo.setOnClickListener(this);
        findAndSetHomeBack();

        m_TextUserName = findViewById(R.id.textAccountName);
        m_TextDate = findViewById(R.id.textDate);
        m_TextTotal = findViewById(R.id.textTotal);
        m_TextCorrect = findViewById(R.id.textCorrect);


        for (int i=0; i<textViews.length; i++){
            textViews[i] = findViewById(textId[i]);
        }
        for (int i=0; i<views.length; i++){
            views[i] = findViewById(viewId[i]);
        }

        setResultTextFromDB();


        m_TextUserName.setText(m_Account.getAcc_name()+"님의 테스트 결과는");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.hlinfoBtn) {
            Log.v(m_TAG, "onClick - hlinfoBtn");
            Intent intent = new Intent(getApplicationContext(), HlinfoActivity.class);
            startActivity(intent);

        }
        onClickHomeBack(view);
    }

    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }

    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);
    }


    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
            dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnHome");
            startActivityAndFinish(MenuActivity.class);
        }
    }


    private void setResultTextFromDB() {
    }


    private void getSrtResultFromDatabase() {
    }


}