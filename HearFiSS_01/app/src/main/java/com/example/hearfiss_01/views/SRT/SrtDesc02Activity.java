package com.example.hearfiss_01.views.SRT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.MenuActivity;

public class SrtDesc02Activity extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    String m_TAG = "SrtDesc02Activity";

    AppCompatButton m_ImgBtnNext;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;
    String m_packName;

    ToggleButton m_CheckBtnOne, m_CheckBtnTwo, m_CheckBtnThree;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_srt_desc02);
        m_packName = getPackageName();

        m_ImgBtnNext = findViewById(R.id.imgBtnSrtDesc02Next);
        m_ImgBtnNext.setOnClickListener(this);
        m_ImgBtnNext.setClickable(false);

        GlobalVar.g_TestType = TConst.T_SRT;
        GlobalVar.g_TestSide = TConst.T_RIGHT;

        GlobalVar.g_alSrtLeft.clear();
        GlobalVar.g_alSrtRight.clear();


        changeTextColorFromStartToEnd(R.id.textView, "#0181F8", 9, 14);

        m_CheckBtnOne = findViewById(R.id.check1Btn);
        m_CheckBtnOne.setOnCheckedChangeListener(this);

        m_CheckBtnTwo = findViewById(R.id.check2Btn);
        m_CheckBtnTwo.setOnCheckedChangeListener(this);

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
        startActivityAndFinish(SrtDesc01Activity.class);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgBtnSrtDesc02Next) {
            startActivityAndFinish(SrtPreTestActivity.class);

        }
        onClickHomeBack(view);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            compoundButton.setTextColor(getColor(R.color.white));

        } else {
            compoundButton.setTextColor(getColor(R.color.gray));

        }
        CheckCheck();
    }


    private void CheckCheck() {
        if((m_CheckBtnOne.isChecked())
                & (m_CheckBtnTwo.isChecked())
                & (m_CheckBtnThree.isChecked())){

            m_ImgBtnNext.setClickable(true);
            m_ImgBtnNext.setBackgroundResource(getResources().getIdentifier("blue_button","drawable", m_packName));
            m_ImgBtnNext.setOnClickListener(this);

        }else{
            m_ImgBtnNext.setClickable(false);
            m_ImgBtnNext.setBackgroundResource(getResources().getIdentifier("gray_button","drawable", m_packName));

        }
    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(SrtDesc01Activity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnHome");
            startActivityAndFinish(MenuActivity.class);

        }
    }

    private void changeTextColorFromStartToEnd(int idRes, String strColor, int iStart, int iEnd) {
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