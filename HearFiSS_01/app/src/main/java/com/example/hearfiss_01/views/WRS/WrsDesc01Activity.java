package com.example.hearfiss_01.views.WRS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatToggleButton;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.global.TConst;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.History.HistoryListActivity;
import com.example.hearfiss_01.views.PTT.PttDesc01Activity;

public class WrsDesc01Activity extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    String m_TAG = "WrsDesc01Activity";

    ImageButton m_ImgBtnBack, m_ImgBtnHome;

    AppCompatToggleButton wrs10,wrs20,wrs25;

    AppCompatButton imgBtnWrsDesc01Next;
    LinearLayout HomeLayout,PtaLayout,WrsLayout,TestLayout;

    int [] m_ToggleBtnId = {R.id.wrs10, R.id.wrs20, R.id.wrs25};

    String m_packname = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrs_desc01);
        m_packname = getPackageName();

        wrs10 = findViewById(R.id.wrs10);
        wrs20 = findViewById(R.id.wrs20);
        wrs25 = findViewById(R.id.wrs25);


        imgBtnWrsDesc01Next = findViewById(R.id.imgBtnWrsDesc01Next);
        imgBtnWrsDesc01Next.setOnClickListener(this);

        findAndSetHomeBack();
        findAndSetNavigationBar();

        wrs10.setOnCheckedChangeListener(this);
        wrs20.setOnCheckedChangeListener(this);
        wrs25.setOnCheckedChangeListener(this);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivityAndFinish(MenuActivity.class);
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



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgBtnWrsDesc01Next) {
            GlobalVar.g_TestSide = TConst.T_RIGHT;
            GlobalVar.g_wrsNumber = getWrsNum();

            startActivityAndFinish(WrsDesc02Activity.class);
        }

        onClickHomeBack(view);
        onClickNavigationBar(view);
    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnWrsDesc01Back");
            startActivityAndFinish(MenuActivity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnWrsDesc01Home");
            startActivityAndFinish(MenuActivity.class);

        }

    }

    private void onClickNavigationBar(View view) {
        if (view.getId() == R.id.HomeLayout) {
            startActivityAndFinish(MenuActivity.class);

        }else if (view.getId() == R.id.PtaLayout) {
            startActivityAndFinish(PttDesc01Activity.class);

//        }else if (view.getId() == R.id.WrsLayout) {
//            finish();
//            Intent intent = new Intent(getApplicationContext(), WrsDesc01Activity.class);
//            startActivity(intent);
//
        }else if (view.getId() == R.id.TestLayout) {
            startActivityAndFinish(HistoryListActivity.class);

        }

    }

    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }



    public void Clickable(){
        imgBtnWrsDesc01Next.setClickable(true);
        imgBtnWrsDesc01Next.setOnClickListener(this);
        imgBtnWrsDesc01Next.setBackgroundResource(getResources().getIdentifier("blue_button","drawable", m_packname));
    }
    public void unClickable(){
        imgBtnWrsDesc01Next.setBackgroundResource(getResources().getIdentifier("gray_button","drawable", m_packname));
        imgBtnWrsDesc01Next.setClickable(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            compoundButton.setTextColor(getColor(R.color.white));
            checkOtherBtnToggle(compoundButton.getId());
        } else {
            compoundButton.setTextColor(getColor(R.color.gray));
        }
        checkAndNextBtnClickable();
    }

    private void checkOtherBtnToggle(int idCheckedBtn){
        AppCompatToggleButton findBtn;
        for(int i=0; i<m_ToggleBtnId.length; i++){
            if(m_ToggleBtnId[i] == idCheckedBtn)
                continue;

            findBtn = findViewById(m_ToggleBtnId[i]);
            if(findBtn.isChecked())
                findBtn.performClick();
        }
    }

    private void checkAndNextBtnClickable(){
        if((wrs10.isChecked()) | (wrs20.isChecked()) | (wrs25.isChecked())){
            Clickable();
        }else{
            unClickable();
        }
    }

    private int getWrsNum() {
        if(wrs25.isChecked()) {
            return 25;
        } else if(wrs20.isChecked()) {
            return 20;
        } else {
            return 10;
        }
    }
}