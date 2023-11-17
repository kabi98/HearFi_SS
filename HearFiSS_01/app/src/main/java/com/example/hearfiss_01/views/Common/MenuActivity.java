package com.example.hearfiss_01.views.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.views.History.HistoryListActivity;
import com.example.hearfiss_01.views.PTT.PttDesc01Activity;
import com.example.hearfiss_01.views.WRS.WrsDesc01Activity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    String m_TAG = "MenuActivity";
    TextView m_TextUser;



    AppCompatButton pttStartBtn;
    AppCompatButton wrsStartBtn;
    AppCompatButton historyStartBtn;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;
    LinearLayout HomeLayout,PtaLayout,WrsLayout,TestLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.v(m_TAG, String.format("onCreate ") );

        m_TextUser = findViewById(R.id.userID);
        String strUser = GlobalVar.g_AccLogin.getAcc_name() + " 님";
        m_TextUser.setText(strUser);


        pttStartBtn = findViewById(R.id.imgBtnStartPtt);
        pttStartBtn.setOnClickListener(this);

        wrsStartBtn = findViewById(R.id.imgBtnStartSpeech);
        wrsStartBtn.setOnClickListener(this);

        historyStartBtn = findViewById(R.id.imgBtnStartHistory);
        historyStartBtn.setOnClickListener(this);


        findAndSetHomeBack();
        findAndSetNavigationBar();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imgBtnStartPtt) {
            Intent intent = new Intent(getApplicationContext(), PttDesc01Activity.class);
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.imgBtnStartSpeech) {
            Intent intent = new Intent(getApplicationContext(), WrsDesc01Activity.class);
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.imgBtnStartHistory) {
            Intent intent = new Intent(getApplicationContext(), HistoryListActivity.class);
            startActivity(intent);
            finish();
        }

        onClickHomeBack(view);
        onClickNavigationBar(view);
    }

    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

    }

    private void findAndSetNavigationBar(){
        HomeLayout = findViewById(R.id.HomeLayout);
        PtaLayout = findViewById(R.id.PtaLayout);
        WrsLayout = findViewById(R.id.WrsLayout);
        TestLayout = findViewById(R.id.TestLayout);

        HomeLayout.setOnClickListener(this);
        PtaLayout.setOnClickListener(this);
        WrsLayout.setOnClickListener(this);
        TestLayout.setOnClickListener(this);

    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - onClickHomeBack");
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();

        } else if (view.getId() == R.id.imgBtnHome) {
//           Log.d(m_TAG, "onClick - onClickHomeBack");
//           Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
//            startActivity(intent);
        }

    }

    private void onClickNavigationBar(View view) {
        if (view.getId() == R.id.HomeLayout) {
//            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
//            startActivity(intent);

        }else if (view.getId() == R.id.PtaLayout) {
            Intent intent = new Intent(getApplicationContext(), PttDesc01Activity.class);
            startActivity(intent);
            finish();

        }else if (view.getId() == R.id.WrsLayout) {
            Intent intent = new Intent(getApplicationContext(), WrsDesc01Activity.class);
            startActivity(intent);
            finish();

        }else if (view.getId() == R.id.TestLayout) {
            Intent intent = new Intent(getApplicationContext(), HistoryListActivity.class);
            startActivity(intent);
            finish();

        }

    }


}