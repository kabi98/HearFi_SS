package com.example.hearfiss_01.views.History;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.db.dao.MyTest;
import com.example.hearfiss_01.db.DTO.HrTestGroup;
import com.example.hearfiss_01.db.DTO.HrTestSet;
import com.example.hearfiss_01.db.DTO.ResultDTO;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.views.Common.MenuActivity;
import com.example.hearfiss_01.views.PTT.PttDesc01Activity;
import com.example.hearfiss_01.views.SRS.SrsDesc01Activity;
import com.example.hearfiss_01.views.SRT.SrtDesc01Activity;
import com.example.hearfiss_01.views.WRS.WrsDesc01Activity;

import java.util.ArrayList;

public class HistoryListActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    String m_TAG = "Recycle View";
    Context m_Context;

    TextView m_TextUser;
    ArrayList<HrTestGroup> groups = new ArrayList<>();
    ArrayList<HrTestSet> sets = new ArrayList<>();
    LinearLayout HomeLayout,PtaLayout,WrsLayout,TestLayout;
    ArrayList<ResultDTO> dataList = new ArrayList<>();
    private HistoryListAdapter mAdapter;

    ImageButton m_ImgBtnBack, m_ImgBtnHome;


//            ██████╗  ██████╗ ███╗   ██╗████████╗    ████████╗ ██████╗ ██╗   ██╗ ██████╗██╗  ██╗
//            ██╔══██╗██╔═══██╗████╗  ██║╚══██╔══╝    ╚══██╔══╝██╔═══██╗██║   ██║██╔════╝██║  ██║
//            ██║  ██║██║   ██║██╔██╗ ██║   ██║          ██║   ██║   ██║██║   ██║██║     ███████║
//            ██║  ██║██║   ██║██║╚██╗██║   ██║          ██║   ██║   ██║██║   ██║██║     ██╔══██║
//            ██████╔╝╚██████╔╝██║ ╚████║   ██║          ██║   ╚██████╔╝╚██████╔╝╚██████╗██║  ██║
//            ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝          ╚═╝    ╚═════╝  ╚═════╝  ╚═════╝╚═╝  ╚═╝

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        m_Context = HistoryListActivity.this;

        m_TextUser = findViewById(R.id.userID);

        String strUser = GlobalVar.g_AccLogin.getAcc_name() + " 님";
        m_TextUser.setText(strUser);


        HomeLayout = findViewById(R.id.HomeLayout);
        PtaLayout = findViewById(R.id.PtaLayout);
        WrsLayout = findViewById(R.id.WrsLayout);
        TestLayout = findViewById(R.id.TestLayout);

        HomeLayout.setOnClickListener(this);
        PtaLayout.setOnClickListener(this);
        WrsLayout.setOnClickListener(this);
        TestLayout.setOnClickListener(this);

        getDataListFromDatabase();

        mRecyclerView = findViewById(R.id.my_recycler_view); // XML 레이아웃에서 RecyclerView를 찾습니다.
        if (mRecyclerView != null) {
            Log.v(m_TAG,"value : " + mRecyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new HistoryListAdapter(dataList);
            mRecyclerView.setAdapter(mAdapter);

        }

        findAndSetHomeBack();
    }

    private void getDataListFromDatabase() {
        MyTest myTest = new MyTest(m_Context);
        groups = myTest.searchHrTestGroup();
        if(groups == null) {
            myTest.closeDatabase();
            return;
        }

        for(int i=0; i<groups.size(); i++){
            sets = myTest.searchHrTestSet(groups.get(i).getTg_id());
            ResultDTO result = new ResultDTO();
            for(HrTestSet setOne : sets){
                if(setOne.getTs_side().equals("LEFT")){
                    result.setLeft_Result(setOne.getTs_Comment());
                }else{
                    result.setRight_Result(setOne.getTs_Comment());
                }
            }
            result.setTg_id(groups.get(i).getTg_id());
            result.setAcc_id(groups.get(i).getAcc_id());
            result.setResult(groups.get(i).getTg_result());
            result.setTg_Date(groups.get(i).getTg_Date());
            if(groups.get(i).getTg_type().equals("PTA")){
                result.setTg_type("순음 청력 검사(PTA)");
            }else{
                result.setTg_type("단어 인지도 검사(WRS)");
            }

            dataList.add(result);
        }

        myTest.closeDatabase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.HomeLayout) {
            startActivityAndFinish(MenuActivity.class);

        }else if (view.getId() == R.id.WrsLayout) {
            startActivityAndFinish(SrsDesc01Activity.class);

        }else if (view.getId() == R.id.PtaLayout) {
            startActivityAndFinish(SrtDesc01Activity.class);
        }

        onClickHomeBack(view);
    }

    private void onClickHomeBack(View view) {
        if (view.getId() == R.id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(MenuActivity.class);

        } else if (view.getId() == R.id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnBack");
            startActivityAndFinish(MenuActivity.class);

        }

    }

    private void startActivityAndFinish(Class<?> clsStart) {
        Intent intent = new Intent(getApplicationContext(), clsStart);
        startActivity(intent);
        finish();
    }

}