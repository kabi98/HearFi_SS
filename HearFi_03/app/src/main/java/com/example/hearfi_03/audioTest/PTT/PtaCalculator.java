package com.example.hearfi_03.audioTest.PTT;

import android.util.Log;

import com.example.hearfi_03.global.TConst;

import java.util.ArrayList;

public class PtaCalculator {
    String m_TAG = "PtaCalculator";

/*****************************************************************************************
//    three-frequency average(3FA) - 3분법
//          3FA = (500Hz + 1000Hz + 2000Hz) / 3;
//    weighted three-frequency average (W3FA) - 4분법
//          W3FA = (500Hz + 1000Hz + 1000Hz + 2000Hz) / 4;
//    weighted four-frequency average (W4FA) - 6분법 - 청각 장애 진단시 사용.
//          W4FA = (500Hz + 1000Hz + 1000Hz + 2000Hz + 2000Hz + 4000Hz) / 6;
//    six-frequency average (6FA) - 자체공식 6개 평균.
//          6FA = (250Hz + 500Hz + 1000Hz + 2000Hz + 4000Hz + 8000Hz) / 6;
*****************************************************************************************/

    ArrayList<PttThreshold> m_alThreshold = new ArrayList<>();

    public PtaCalculator() {
    }

    public void setThresholdList(ArrayList<PttThreshold> _alThreshold) {
        this.m_alThreshold = _alThreshold;
    }

    public ArrayList<PttThreshold> getThresholdList() {
        return m_alThreshold;
    }


/*****************************************************************************************
//    three-frequency average(3FA)
//          3FA = (500Hz + 1000Hz + 2000Hz) / 3;
 *****************************************************************************************/
    public int calculatePTA_3FA(){
        Log.v(m_TAG, "calculatePTA_3FA");

        int [] arrFindHz = { 500, 1000, 2000 };
        return calculateAverageFromHzArray(arrFindHz);
    }

    /*****************************************************************************************
     //    weighted three-frequency average (W3FA)
     //          W3FA = (500Hz + 1000Hz + 1000Hz + 2000Hz) / 4;
     *****************************************************************************************/
    public int calculatePTA_W3FA(){
        Log.v(m_TAG, "calculatePTA_W3FA");

        int [] arrFindHz = { 500, 1000, 1000, 2000 };
        return calculateAverageFromHzArray(arrFindHz);
    }

    /*****************************************************************************************
     //    weighted four-frequency average (W4FA)
     //          W4FA = (500Hz + 1000Hz + 1000Hz + 2000Hz + 2000Hz + 4000Hz) / 6;
     *****************************************************************************************/
    public int calculatePTA_W4FA(){
        Log.v(m_TAG, "calculatePTA_W3FA");

        int [] arrFindHz = { 500, 1000, 1000, 2000, 2000, 4000 };
        return calculateAverageFromHzArray(arrFindHz);
    }

    /*****************************************************************************************
     //    six-frequency average (6FA)
     //          6FA = (250Hz + 500Hz + 1000Hz + 2000Hz + 4000Hz + 8000Hz) / 6;
     *****************************************************************************************/
    public int calculatePTA_6FA(){
        Log.v(m_TAG, "calculatePTA_6FA");

        int [] arrFindHz = { 250, 500, 1000, 2000, 4000, 8000 };
        return calculateAverageFromHzArray(arrFindHz);
    }

    private int calculateAverageFromHzArray(int [] arrFindHz){
        Log.v(m_TAG, String.format("calculateAverageFromArray FindHz Size:%d",
                arrFindHz.length ) );

        int iFindDBHL = -1;
        int iSumDBHL = 0;
        for(int iHz : arrFindHz){
            iFindDBHL = findDbhlFromHz(iHz);
            if(-1 == iFindDBHL){
                return -1;
            }
            iSumDBHL += iFindDBHL;
        }
        return (iSumDBHL / arrFindHz.length);
    }


    public int findDbhlFromHz(int iHz){

        for(PttThreshold findThreshold : m_alThreshold){
            if(findThreshold.get_Hz() == iHz){
                return findThreshold.get_DBHL();
            }
        }
        return -1;
    }

    public String getHearingLossStr(int iPTA) {

        for(int i = 0; i < TConst.HEARING_LOSS_PTA.length; i++ ){
            if (iPTA <= TConst.HEARING_LOSS_PTA[i]){
                return TConst.HEARING_LOSS_STR[i];
            }
        }
        return TConst.HEARING_LOSS_STR[TConst.HEARING_LOSS_PTA.length-1];
    }

}
