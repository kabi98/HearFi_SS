package com.example.hearfiss_01.audioTest;

import static org.junit.Assert.assertNotEquals;

import android.util.Log;

import com.example.hearfiss_01.audioTest.PTT.PtaCalculator;
import com.example.hearfiss_01.audioTest.PTT.PttThreshold;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

public class PtaCalculatorTest extends TestCase {
    String m_TAG = "PtaCalculatorTest";

    @Test
    public void testCreate() {
        Log.v(m_TAG, "testCreate");

        PtaCalculator calcTemp = null;
        assertNull(calcTemp);

        calcTemp = new PtaCalculator();
        assertNotNull(calcTemp);
    }

    @Test
    public void testSetThresholdList() {

        Log.v(m_TAG, "testSetThresholdList");
        int arrSetHz[]            = {   1000,   2000,   4000,   8000,   500,    250 };
        int arrSetDBHL[]          = {   55,     60,     65,     70,     75,     80  };

        PtaCalculator calcTemp = null;
        calcTemp = new PtaCalculator();
        ArrayList<PttThreshold> alThreshold = new ArrayList<>();
        ArrayList<PttThreshold> alGetThreshold = new ArrayList<>();

        alGetThreshold = calcTemp.getThresholdList();
        Log.v(m_TAG, String.format( "testSetThresholdList before size = %d", alGetThreshold.size() ));
        assertEquals(0, alGetThreshold.size());


        for(int i = 0; i<arrSetHz.length; i++){
            PttThreshold thresholdOne = new PttThreshold(arrSetHz[i], arrSetDBHL[i]);
            alThreshold.add(thresholdOne);
        }
        calcTemp.setThresholdList(alThreshold);

        alGetThreshold = calcTemp.getThresholdList();
        Log.v(m_TAG, String.format( "testSetThresholdList after size = %d", alGetThreshold.size() ));
        assertEquals(6, alGetThreshold.size());

    }

    @Test
    public void testSetThresholdListCheck() {

        Log.v(m_TAG, "testSetThresholdListCheck");
        int arrSetHz[]            = {   1000,   2000,   4000,   8000,   500,    250 };
        int arrSetDBHL[]          = {   55,     60,     65,     70,     75,     80  };

        PtaCalculator calcTemp = null;
        calcTemp = new PtaCalculator();
        ArrayList<PttThreshold> alThreshold = new ArrayList<>();
        ArrayList<PttThreshold> alGetThreshold = new ArrayList<>();

        for(int i = 0; i<arrSetHz.length; i++){
            PttThreshold thresholdOne = new PttThreshold(arrSetHz[i], arrSetDBHL[i]);
            alThreshold.add(thresholdOne);
        }
        calcTemp.setThresholdList(alThreshold);

        alGetThreshold = calcTemp.getThresholdList();
        for(int i = 0; i<alGetThreshold.size(); i++){
            Log.v(m_TAG, String.format( "testSetThresholdListCheck check Hz:%d, DBHL:%d ",
                    alGetThreshold.get(i).get_Hz(), alGetThreshold.get(i).get_DBHL() ));
            assertEquals(arrSetHz[i], alGetThreshold.get(i).get_Hz());
            assertEquals(arrSetDBHL[i], alGetThreshold.get(i).get_DBHL());
        }

    }

    @Test
    public void testFindDbhlFromHz() {
        Log.v(m_TAG, "testFindDbhlFromHz_01");
        int arrSetHz[]            = {   1000,   2000,   4000,   8000,   500,    250 };
        int arrSetDBHL[]          = {   55,     60,     65,     70,     75,     80  };

        PtaCalculator calcTemp = null;
        calcTemp = new PtaCalculator();
        ArrayList<PttThreshold> alThreshold = new ArrayList<>();
        ArrayList<PttThreshold> alGetThreshold = new ArrayList<>();

        for(int i = 0; i<arrSetHz.length; i++){
            PttThreshold thresholdOne = new PttThreshold(arrSetHz[i], arrSetDBHL[i]);
            alThreshold.add(thresholdOne);
        }
        calcTemp.setThresholdList(alThreshold);

        int iFindDBHL = -1;
        iFindDBHL = calcTemp.findDbhlFromHz(4000);
        Log.v(m_TAG, String.format( "testFindDbhlFromHz_01 Expect:%d, Find:%d, Hz:%d ",
                65, iFindDBHL, 4000));
        assertEquals(65, iFindDBHL);

        for(int i = 0; i<arrSetHz.length; i++){
            iFindDBHL = calcTemp.findDbhlFromHz(arrSetHz[i]);
            Log.v(m_TAG, String.format( "testFindDbhlFromHz_01 Expect:%d, Find:%d, Hz:%d ",
                    arrSetDBHL[i], iFindDBHL, arrSetHz[i]));
            assertEquals(arrSetDBHL[i], iFindDBHL);
        }

        iFindDBHL = calcTemp.findDbhlFromHz(100);
        Log.v(m_TAG, String.format( "testFindDbhlFromHz_01 Expect:%d, Find:%d, Hz:%d ",
                -1, iFindDBHL, 100));
        assertEquals(-1, iFindDBHL);

        iFindDBHL = calcTemp.findDbhlFromHz(10000);
        Log.v(m_TAG, String.format( "testFindDbhlFromHz_01 Expect:%d, Find:%d, Hz:%d ",
                -1, iFindDBHL, 10000));
        assertEquals(-1, iFindDBHL);

    }

    @Test
    public void testCaculatePTA_3FA() {

        Log.v(m_TAG, "testCaculatePTA_3FA");
        int arrSetHz[]            = {   1000,   2000,   4000,   8000,   500,    250 };
        int arrSetDBHL[]          = {   55,     60,     65,     70,     75,     80  };
        int iExpectPTA = 63;

//    three-frequency average(3FA)
//        3FA = (500Hz + 1000Hz + 2000Hz) / 3;
//        (75 + 55 + 60) / 3

        PtaCalculator calcTemp = null;
        calcTemp = new PtaCalculator();
        ArrayList<PttThreshold> alThreshold = new ArrayList<>();
        ArrayList<PttThreshold> alGetThreshold = new ArrayList<>();

        for(int i = 0; i<arrSetHz.length; i++){
            PttThreshold thresholdOne = new PttThreshold(arrSetHz[i], arrSetDBHL[i]);
            alThreshold.add(thresholdOne);
        }
        calcTemp.setThresholdList(alThreshold);
        int iPTA = calcTemp.calculatePTA_3FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_3FA PTA_3FA Expect:%d, Actual:%d ",
                iExpectPTA, iPTA));
        assertEquals(iExpectPTA, iPTA);
    }

    @Test
    public void testCaculatePTA_Calc_01() {

        Log.v(m_TAG, "testCaculatePTA_Calc_01");
        int arrSetHz[]            = {   250,    500,    1000,   2000,   4000,   8000 };
        int arrSetDBHL[]          = {   80,     75,     55,     60,     65,     70   };
        int iExpectPTA_3FA = 63;
        int iExpectPTA_W3FA = 61;
        int iExpectPTA_W4FA = 61;
        int iExpectPTA_6FA = 67;

        PtaCalculator calcTemp = null;
        calcTemp = new PtaCalculator();
        ArrayList<PttThreshold> alThreshold = new ArrayList<>();
        ArrayList<PttThreshold> alGetThreshold = new ArrayList<>();

        for(int i = 0; i<arrSetHz.length; i++){
            PttThreshold thresholdOne = new PttThreshold(arrSetHz[i], arrSetDBHL[i]);
            alThreshold.add(thresholdOne);
        }
        calcTemp.setThresholdList(alThreshold);


        int iPTA = -1;
        iPTA = calcTemp.calculatePTA_3FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_01 PTA_3FA Expect:%d, Actual:%d ",
                iExpectPTA_3FA, iPTA));
        assertEquals(iExpectPTA_3FA, iPTA);

        iPTA = calcTemp.calculatePTA_W3FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_01 PTA_W3FA Expect:%d, Actual:%d ",
                iExpectPTA_W3FA, iPTA));
        assertEquals(iExpectPTA_W3FA, iPTA);

        iPTA = calcTemp.calculatePTA_W4FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_01 PTA_W4FA Expect:%d, Actual:%d ",
                iExpectPTA_W4FA, iPTA));
        assertEquals(iExpectPTA_W4FA, iPTA);

        iPTA = calcTemp.calculatePTA_6FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_01 PTA_6FA Expect:%d, Actual:%d ",
                iExpectPTA_6FA, iPTA));
        assertEquals(iExpectPTA_6FA, iPTA);

    }

    @Test
    public void testCaculatePTA_Calc_02() {

        Log.v(m_TAG, "testCaculatePTA_Calc_01");
        int arrSetHz[]            = {   250,    500,    1000,   2000,   4000,   8000 };
        int arrSetDBHL[]          = {   10,     15,     20,     45,     35,     15   };
        int iExpectPTA_3FA = 26;
        int iExpectPTA_W3FA = 25;
        int iExpectPTA_W4FA = 30;
        int iExpectPTA_6FA = 23;

        PtaCalculator calcTemp = null;
        calcTemp = new PtaCalculator();
        ArrayList<PttThreshold> alThreshold = new ArrayList<>();
        ArrayList<PttThreshold> alGetThreshold = new ArrayList<>();

        for(int i = 0; i<arrSetHz.length; i++){
            PttThreshold thresholdOne = new PttThreshold(arrSetHz[i], arrSetDBHL[i]);
            alThreshold.add(thresholdOne);
        }
        calcTemp.setThresholdList(alThreshold);


        int iPTA = -1;
        iPTA = calcTemp.calculatePTA_3FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_01 PTA_3FA Expect:%d, Actual:%d ",
                iExpectPTA_3FA, iPTA));
        assertEquals(iExpectPTA_3FA, iPTA);

        iPTA = calcTemp.calculatePTA_W3FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_01 PTA_W3FA Expect:%d, Actual:%d ",
                iExpectPTA_W3FA, iPTA));
        assertEquals(iExpectPTA_W3FA, iPTA);

        iPTA = calcTemp.calculatePTA_W4FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_01 PTA_W4FA Expect:%d, Actual:%d ",
                iExpectPTA_W4FA, iPTA));
        assertEquals(iExpectPTA_W4FA, iPTA);

        iPTA = calcTemp.calculatePTA_6FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_01 PTA_6FA Expect:%d, Actual:%d ",
                iExpectPTA_6FA, iPTA));
        assertEquals(iExpectPTA_6FA, iPTA);

    }

    @Test
    public void testCaculatePTA_Calc_03() {

        Log.v(m_TAG, "testCaculatePTA_Calc_01");
        int arrSetHz[]            = {   500,    1000,   2000,   4000,   8000 };
        int arrSetDBHL[]          = {   15,     20,     45,     35,     15   };
        int iExpectPTA_3FA = 26;
        int iExpectPTA_W3FA = 25;
        int iExpectPTA_W4FA = 30;
        int iExpectPTA_6FA = -1;

        PtaCalculator calcTemp = null;
        calcTemp = new PtaCalculator();
        ArrayList<PttThreshold> alThreshold = new ArrayList<>();
        ArrayList<PttThreshold> alGetThreshold = new ArrayList<>();

        for(int i = 0; i<arrSetHz.length; i++){
            PttThreshold thresholdOne = new PttThreshold(arrSetHz[i], arrSetDBHL[i]);
            alThreshold.add(thresholdOne);
        }
        calcTemp.setThresholdList(alThreshold);


        int iPTA = -1;
        iPTA = calcTemp.calculatePTA_3FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_01 PTA_3FA Expect:%d, Actual:%d ",
                iExpectPTA_3FA, iPTA));
        assertEquals(iExpectPTA_3FA, iPTA);

        iPTA = calcTemp.calculatePTA_W3FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_01 PTA_W3FA Expect:%d, Actual:%d ",
                iExpectPTA_W3FA, iPTA));
        assertEquals(iExpectPTA_W3FA, iPTA);

        iPTA = calcTemp.calculatePTA_W4FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_01 PTA_W4FA Expect:%d, Actual:%d ",
                iExpectPTA_W4FA, iPTA));
        assertEquals(iExpectPTA_W4FA, iPTA);

        iPTA = calcTemp.calculatePTA_6FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_01 PTA_6FA Expect:%d, Actual:%d ",
                iExpectPTA_6FA, iPTA));
        assertEquals(iExpectPTA_6FA, iPTA);

    }

    @Test
    public void testCaculatePTA_Calc_LeftRight() {

        Log.v(m_TAG, "testCaculatePTA_Calc_01");
        int arrSetHz[]            = {   250,    500,    1000,   2000,   4000,   8000 };
        int arrSetLeftDBHL[]      = {   10,     15,     20,     45,     35,     15   };
        int arrSetRightDBHL[]     = {   80,     75,     55,     60,     65,     70   };
        int iExpectLeftPTA_3FA = 26;
        int iExpectRightPTA_3FA = 63;

        PtaCalculator calcTemp = null;
        calcTemp = new PtaCalculator();

        ArrayList<PttThreshold> alLeft = new ArrayList<>();
        ArrayList<PttThreshold> alRight = new ArrayList<>();

        for(int i = 0; i<arrSetHz.length; i++){
            PttThreshold thresholdOne = new PttThreshold(arrSetHz[i], arrSetLeftDBHL[i]);
            alLeft.add(thresholdOne);
        }
        for(int i = 0; i<arrSetHz.length; i++){
            PttThreshold thresholdOne = new PttThreshold(arrSetHz[i], arrSetRightDBHL[i]);
            alRight.add(thresholdOne);
        }

        int iPTA = -1;

        calcTemp.setThresholdList(alLeft);
        iPTA = calcTemp.calculatePTA_3FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_LeftRight Left PTA_3FA Expect:%d, Actual:%d ",
                iExpectLeftPTA_3FA, iPTA));
        assertEquals(iExpectLeftPTA_3FA, iPTA);

        calcTemp.setThresholdList(alRight);
        iPTA = calcTemp.calculatePTA_3FA();
        Log.v(m_TAG, String.format( "testCaculatePTA_Calc_LeftRight Right PTA_3FA Expect:%d, Actual:%d ",
                iExpectRightPTA_3FA, iPTA));
        assertEquals(iExpectRightPTA_3FA, iPTA);
    }

    @Test
    public void testGetHearingLossStr() {

        Log.v(m_TAG, "testCaculatePTA_Calc_01");
        int arrPTA[]            = { -10,    0,      10,     20,     30,         40,         50,          60,            70,             80,         90,       100,       110 };
        String strExpectHLS[]   = { "정상",  "정상", "정상",  "정상",  "경도난청",  "경도난청",  "중도난청",    "중고도난청",    "중고도난청",    "고도난청",  "고도난청", "심도난청", "심도난청"};

        PtaCalculator calcTemp = null;
        calcTemp = new PtaCalculator();

        String strGet = "";
        for(int i = 0; i<arrPTA.length; i++) {
            strGet = calcTemp.getHearingLossStr(arrPTA[i]);
            Log.v(m_TAG, String.format("testGetHearingLossStr PTA : %d - 난청정도 : %s ",
                    arrPTA[i], strGet));
            assertEquals( strExpectHLS[i], strGet );
        }
    }


}