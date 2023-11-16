package com.example.hearfi_03.views.PTT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hearfi_03.R;
import com.example.hearfi_03.global.GlobalVar;
import com.example.hearfi_03.global.TConst;
import com.example.hearfi_03.views.Common.MenuActivity;

public class PttDesc02Activity extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    String m_TAG = "PttDesc02Activity";

    AppCompatButton m_ImgBtnNext;
    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    String m_packname;
    ToggleButton m_CheckBtnOne, m_CheckBtnTwo_1,m_CheckBtnTwo_2, m_CheckBtnThree;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptt_desc02);
        m_packname = getPackageName();

        m_ImgBtnNext = findViewById(R.id.imgBtnPttDesc02Next);
        m_ImgBtnNext.setOnClickListener(this);
        m_ImgBtnNext.setClickable(false);

        GlobalVar.g_TestType = TConst.T_PTT;
        GlobalVar.g_TestSide = TConst.T_RIGHT;

        changeTextColorFromStartToEnd(R.id.textView, "#0181F8", 9, 14);

        m_CheckBtnOne = findViewById(R.id.check1Btn);
        m_CheckBtnOne.setOnCheckedChangeListener(this);

        m_CheckBtnTwo_1 = findViewById(R.id.check2_1Btn);
        m_CheckBtnTwo_1.setOnCheckedChangeListener(this);

        m_CheckBtnTwo_2 = findViewById(R.id.check2_2Btn);
        m_CheckBtnTwo_2.setOnCheckedChangeListener(this);

        m_CheckBtnThree = findViewById(R.id.check3Btn);
        m_CheckBtnThree.setOnCheckedChangeListener(this);

        findAndSetHomeBack();

    }

    private void findAndSetHomeBack() {
        m_ImgBtnBack = findViewById(R.id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(R.id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivityAndFinish(PttDesc01Activity.class);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgBtnPttDesc02Next) {
            startActivityAndFinish(PttPreTestActivity.class);

        }
        onClickHomeBack(view);
    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(PttDesc01Activity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnHome");
            startActivityAndFinish(MenuActivity.class);

        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            compoundButton.setTextColor(getColor(R.color.white));
            onCheckedToggleEarHead(compoundButton);

        } else {
            compoundButton.setTextColor(getColor(R.color.gray));

        }
        CheckCheck();
    }

    private void onCheckedToggleEarHead(CompoundButton compoundButton) {
        if(compoundButton.getId() == R.id.check2_1Btn){
//            GlobalVar.g_PttTypeEarOrHead = TConst.EARPHONE;

            GlobalVar.g_PttStrPhone = TConst.DEFAULT_PHONE;
            GlobalVar.g_PttStrDevice = TConst.DEFAULT_EARPHONE;

            m_CheckBtnTwo_2.setChecked(false);

        } else if(compoundButton.getId() == R.id.check2_2Btn){
//            GlobalVar.g_PttTypeEarOrHead = TConst.HEADSET;

            GlobalVar.g_PttStrPhone = TConst.DEFAULT_PHONE;
            GlobalVar.g_PttStrDevice = TConst.DEFAULT_HEADPHONE;
            m_CheckBtnTwo_1.setChecked(false);

        }


    }

    public void CheckCheck(){
        if((m_CheckBtnOne.isChecked())
                & ((m_CheckBtnTwo_1.isChecked())|(m_CheckBtnTwo_2.isChecked()))
                & (m_CheckBtnThree.isChecked())){

            m_ImgBtnNext.setClickable(true);
            m_ImgBtnNext.setBackgroundResource(getResources().getIdentifier("blue_button","drawable", m_packname));
            m_ImgBtnNext.setOnClickListener(this);

        }else{
            m_ImgBtnNext.setClickable(false);
            m_ImgBtnNext.setBackgroundResource(getResources().getIdentifier("gray_button","drawable", m_packname));

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