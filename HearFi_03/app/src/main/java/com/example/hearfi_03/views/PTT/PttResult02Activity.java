package com.example.hearfi_03.views.PTT;

import static com.example.hearfi_03.R.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.hearfi_03.audioTest.PTT.PttThreshold;
import com.example.hearfi_03.db.dao.PttDAO;
import com.example.hearfi_03.db.DTO.HrTestGroup;
import com.example.hearfi_03.db.DTO.HrTestSet;
import com.example.hearfi_03.db.DTO.Account;
import com.example.hearfi_03.global.GlobalVar;
import com.example.hearfi_03.global.TConst;
import com.example.hearfi_03.views.Common.MenuActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;

public class PttResult02Activity extends AppCompatActivity implements View.OnClickListener {
    String m_TAG = "PttResult02Activity";
    Context m_Context;

    TextView m_TextLeftResult, m_TextRightResult, m_TextUserName, chartTitle;

    LineChart m_LineChartPTT;
    LineDataSet m_DataSetLeft;
    LineDataSet m_DataSetRight;
    AppCompatButton m_AppBtnHlInfo;
    ImageButton m_ImgBtnBack;
    ImageButton m_ImgBtnHome;

    TextView[] textViews = new TextView[6];
    View[] views = new View[6];

    int[] textId = {id.hr1, id.hr2, id.hr3, id.hr4, id.hr5, id.hr6};

    int[] viewId = {id.v1, id.v2, id.v3, id.v4, id.v5, id.v6};
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
        setContentView(layout.activity_ptt_result02);

        m_Context = PttResult02Activity.this;

        m_Account = GlobalVar.g_AccLogin;
        m_TgId = GlobalVar.g_TestGroup.getTg_id();
        getPttResultFromDatabase();

        m_AppBtnHlInfo = findViewById(id.hlinfoBtn);
        m_AppBtnHlInfo.setOnClickListener(this);
        findAndSetHomeBack();

        m_TextLeftResult = findViewById(id.textLeftResult);
        m_TextRightResult = findViewById(id.textRightResult);
        m_TextUserName = findViewById(id.textAccountName);

        chartTitle = findViewById(id.chartTitle);
        for (int i=0; i<textViews.length; i++){
            textViews[i] = findViewById(textId[i]);
        }
        for (int i=0; i<views.length; i++){
            views[i] = findViewById(viewId[i]);
        }

        setResultTextFromDB();

//        String result = ResultTextSetting();
//        Log.v(m_TAG,"onCreate - result - value : " + result);
//        m_TextLeftResult.setText(result);

        setBlueColorAccountHearingLevel();

        m_TextUserName.setText(m_Account.getAcc_name()+"님의 테스트 결과는");
        chartTitle.setText(m_Account.getAcc_name()+"님의 오디오 그램");

        settingAudioGramChart();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivityAndFinish(MenuActivity.class);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == id.hlinfoBtn) {
            Log.v(m_TAG, "onClick - hlinfoBtn");
//            startActivityAndFinish(HlinfoActivity.class);
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
        m_ImgBtnBack = findViewById(id.imgBtnBack);
        m_ImgBtnBack.setOnClickListener(this);

        m_ImgBtnHome = findViewById(id.imgBtnHome);
        m_ImgBtnHome.setOnClickListener(this);

    }

    private void onClickHomeBack(View view) {
        if (view.getId() == id.imgBtnBack) {
            Log.d(m_TAG, "onClick - imgBtnBack");
//            startActivityAndFinish(MenuActivity.class);
            dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
            dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        } else if (view.getId() == id.imgBtnHome) {
            Log.d(m_TAG, "onClick - imgBtnHome");
            startActivityAndFinish(MenuActivity.class);

        }

    }


    private void getPttResultFromDatabase(){
        Log.v(m_TAG, String.format("getPttResultFromDatabase") );

        PttDAO pttDAO = new PttDAO(m_Context);

        pttDAO.setAccount(m_Account);
        pttDAO.loadPttResultsFromTestGroupId(m_TgId);

        m_TestGroup = pttDAO.getTestGroup();

        m_TestSetLeft = pttDAO.getTestSetLeft();
        m_TestSetRight = pttDAO.getTestSetRight();

        m_alRightResult = pttDAO.getRightThresholdList();
        m_alLeftResult = pttDAO.getLeftThresholdList();

        pttDAO.releaseAndClose();

        for(PttThreshold pttOne: m_alRightResult){
            Log.v(m_TAG, String.format("right Threshold Hz:%d, dB:%d",
                    pttOne.get_Hz(), pttOne.get_DBHL()) );
        }

        for(PttThreshold pttOne: m_alLeftResult){
            Log.v(m_TAG, String.format("left Threshold Hz:%d, dB:%d",
                    pttOne.get_Hz(), pttOne.get_DBHL()) );
        }
        checkListSizeErrorAndFakeAdd();
    }

    public void addFakeUnitList(ArrayList<PttThreshold> alResult) {

        PttThreshold unitPtt;
        alResult.clear();
        for(int i=0; i<TConst.HZ_ORDER.length; i++){
            unitPtt = new PttThreshold(TConst.HZ_ORDER[i], 0);
            alResult.add(unitPtt);
        }
        Collections.sort(alResult);

    }

    public void checkListSizeErrorAndFakeAdd() {
        if(m_alRightResult.size() != TConst.HZ_ORDER.length){
            addFakeUnitList(m_alRightResult);
        }

        if(m_alLeftResult.size() != TConst.HZ_ORDER.length){
            addFakeUnitList(m_alLeftResult);
        }
    }


    private void settingAudioGramChart(){
        try {
            trySettingAudioGramChart();

        } catch (Exception e) {
            Log.v(m_TAG, "settingAudioGramChart Exception " + e);
        }
    }

    private void trySettingAudioGramChart(){

        m_LineChartPTT = (LineChart) findViewById(id.LineChart);

        getDataSetFromPttResult();

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(m_DataSetLeft);
        dataSets.add(m_DataSetRight);

        LineData data = new LineData(dataSets);
        m_LineChartPTT.setData(data);

        int iLeftColor = Color.rgb(7, 143, 255);
        setDataSetColorAndLine(m_DataSetLeft, iLeftColor);

        int iRightColor = Color.rgb(242, 76, 61);
        setDataSetColorAndLine(m_DataSetRight, iRightColor);

        setChartDisplayOption();

        setupChartXYAxis();

        m_LineChartPTT.invalidate();
    }

    void getDataSetFromPttResult(){
        Drawable markerIconX = ContextCompat.getDrawable(getApplicationContext(), drawable.blue_x_solid);
        ArrayList<Entry> alEntryLeft = getEntryListFromPttResult(m_alLeftResult, markerIconX);
        m_DataSetLeft = new LineDataSet(alEntryLeft, "왼쪽");
        m_DataSetLeft.setDrawValues(false);

        Drawable markerIconO = ContextCompat.getDrawable(getApplicationContext(), drawable.o_solid);
        ArrayList<Entry> alEntryRight = getEntryListFromPttResult(m_alRightResult, markerIconO);
        m_DataSetRight = new LineDataSet(alEntryRight, "오른쪽");
        m_DataSetRight.setDrawValues(false);
    }

    private ArrayList<Entry> getEntryListFromPttResult(ArrayList<PttThreshold> alPttResult, Drawable markerIcon) {
        ArrayList<Entry> chartData = new ArrayList<>();

        float x = 0;
        float y = 0;
        for(int i = 0; i<alPttResult.size(); i++){
            x = i + 1;
            y = (-1) * alPttResult.get(i).get_DBHL();
            chartData.add(new Entry(x, y, markerIcon));
        }

        return chartData;
    }

    private void setDataSetColorAndLine(LineDataSet dataSet, int iColor){
        dataSet.setLineWidth(2); // 선 두께
        dataSet.setColor(iColor); // 선 색상
        dataSet.setCircleColor(iColor);
        dataSet.setCircleColorHole(iColor);
        dataSet.setCircleRadius(2);
    }

    private void setChartDisplayOption(){
        Description description = new Description();
        description.setText("Audiogram");
        m_LineChartPTT.setDescription(description);
        // ------------------------------------ CHART DISPLAY OPTION ---------------------------- //
        m_LineChartPTT.setDrawGridBackground(false); // 배경 격자 설정
        m_LineChartPTT.setDrawBorders(true); // 테두리 설정
        m_LineChartPTT.setBorderWidth(2); // 테두리 두께
        m_LineChartPTT.setBorderColor(Color.BLACK); // 테두리 색상

    }

    private void setupChartXYAxis(){
        // ------------------------------------ X AXIS DISABLED --------------------------------- //
        XAxis xAxis = m_LineChartPTT.getXAxis();
//        xAxis.setAxisMaximum(0.01f);
//        xAxis.setAxisMaximum(9000);
//        xAxis.setSpaceMax(0.1f);
//        xAxis.setSpaceMin(0.1f);
        String[] axisValue = new String[]{"125Hz","250Hz","500Hz","1000Hz","2000Hz","4000Hz","8000Hz"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(axisValue));
        xAxis.setTextSize(8.5f);

        xAxis.setGranularity(1);
        float barSpace = 0.05f;
        // groupSpace : 그룹 간의 간격을 지정
        float groupSpace = 0.16f;

        YAxis yAxis = m_LineChartPTT.getAxisLeft();
        yAxis.setMinWidth(20);

        yAxis.setLabelCount(12, true);
        yAxis.setAxisMinimum(-100f);
        yAxis.setAxisMaximum(10f);
        yAxis.setValueFormatter((new MyYaxisValueFormatter()));

        // ------------------------------------ RIGHT AXIS DISABLED ----------------------------- //
//        m_LineChartPTT.setVisibleYRange(0,110, yAxis.getAxisDependency());
        m_LineChartPTT.getAxisRight().setEnabled(false);
    }

    private class MyYaxisValueFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
//            if(value<0){
//                value *= -1;
//            }
            value *= -1;
            int iValue = (int) Math.round(value);
            return iValue + "dB";
        }
    }

    private class MyXaxisValueFormatter implements IAxisValueFormatter{

        String[] axisValue = {"125","250","500","1000","2000","4000","8000"};
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            String temp = Integer.toString((int) value);

            return temp;
        }
    }

    public void setBlueColorAccountHearingLevel(){
        int iIndex = geIndexFromtHearingLossString(m_TestGroup.getTg_result());
        if(iIndex >= 0){
            textViews[iIndex].setTextColor(getColor(color.blue));
            views[iIndex].setBackgroundColor(getColor(color.blue));
        }

    }

    public void setResultTextFromDB() {
        String strLeft = String.format(" 왼쪽 : %s ", m_TestSetLeft.getTs_Comment());
        m_TextLeftResult.setText(strLeft);

        String strRight = String.format(" 오른쪽 : %s ", m_TestSetRight.getTs_Comment());
        m_TextRightResult.setText(strRight);
    }

    public String ResultTextSetting() {
        int iIndex = geIndexFromtHearingLossString(m_TestGroup.getTg_result());

        int iStart, iEnd;
        if(iIndex == 0){
            iStart = 0;
        } else {
            iStart = TConst.HEARING_LOSS_PTA[iIndex-1]+1;
        }
        iEnd = TConst.HEARING_LOSS_PTA[iIndex];

        return String.format("%s(%d~%ddBHL)", TConst.HEARING_LOSS_STR[iIndex], iStart, iEnd);
    }

    private int geIndexFromtHearingLossString(String strHearingLoss){
        for(int i = 0; i < TConst.HEARING_LOSS_STR.length; i++ ){
            if (strHearingLoss.equals(TConst.HEARING_LOSS_STR[i])){
                return i;
            }
        }
        return -1;
    }


}