package com.example.hearfi_03.audioTest;

import android.util.Log;

import com.example.hearfi_03.audioTest.PTT.PTT;
import com.example.hearfi_03.audioTest.PTT.PttScore;
import com.example.hearfi_03.audioTest.PTT.PttThreshold;
import com.example.hearfi_03.global.GlobalVar;
import com.example.hearfi_03.global.TConst;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

public class PTTTest extends TestCase {
    String m_TAG = "PTTTest";

    @Test
    public void testPttCreate() {
        Log.v(m_TAG, "testPttCreate");

        PTT pttTemp = null;
        assertNull(pttTemp);

        pttTemp = new PTT(null);
        assertNotNull(pttTemp);
    }

    @Test
    public void testStartTest() {

        GlobalVar.g_TestType = TConst.T_PTT;
        GlobalVar.g_TestSide = TConst.T_RIGHT;
        GlobalVar.g_PttRightDBHL = 50;
        GlobalVar.g_PttLeftDBHL = 70;

///////////////////////////////////////////////////////////////////////////////////////
        PTT pttTemp = null;
        pttTemp = new PTT(null);
        pttTemp.startTest();

        assertEquals(TConst.T_RIGHT, pttTemp.get_iCurSide());
        assertEquals(TConst.HZ_ORDER[0], pttTemp.get_iCurHz());
        assertEquals(50, pttTemp.get_iCurDBHL());
        assertNotNull(pttTemp.get_CurScore());
    }


    public void testSaveAnswer_1_Right() {

        int arrIsHear[] = {
                1,      0,      0,      0,      1, // 1000
                1,      0,      0,      0,      1, // 2000
                1,      0,      0,      0,      1, // 4000
                1,      0,      0,      0,      1, // 8000
                1,      0,      0,      0,      1, // 500
                1,      0,      0,      0,      1, // 250
        };

        GlobalVar.g_TestType = TConst.T_PTT;
        GlobalVar.g_TestSide = TConst.T_RIGHT;
        GlobalVar.g_PttRightDBHL = 50;

///////////////////////////////////////////////////////////////////////////////////////
        PTT pttTemp = null;
        pttTemp = new PTT(null);
        pttTemp.startTest();

        for(int i=0; i<arrIsHear.length; i++){
            pttTemp.saveAnswer(arrIsHear[i]);
        }

///////////////////////////////////////////////////////////////////////////////////////
        int arrExpectHz[]            = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectRightDBHL[]     = {   55,       55,     55,     55,     55,     55 };


        PttScore RightScore = pttTemp.get_CurScore();

        ArrayList<PttThreshold> RightThresholdList = RightScore.getThresholdList();
        Log.v(m_TAG, String.format("testSaveAnswer Thrd size:%d", RightThresholdList.size()) );

        for(int i=0; i<RightThresholdList.size(); i++){
            Log.v(m_TAG, String.format("right Thresold i:%d, Hz:%d, dB:%d",
                    i, RightThresholdList.get(i).get_Hz(), RightThresholdList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], RightThresholdList.get(i).get_Hz());
            assertEquals(arrExpectRightDBHL[i], RightThresholdList.get(i).get_DBHL());
        }

    }

    public void testSaveAnswer_2_Left() {

        int arrIsHear[] = {
                1,      0,      0,      0,      1, // 1000
                1,      0,      0,      0,      1, // 2000
                1,      0,      0,      0,      1, // 4000
                1,      0,      0,      0,      1, // 8000
                1,      0,      0,      0,      1, // 500
                1,      0,      0,      0,      1, // 250
        };

        GlobalVar.g_TestType = TConst.T_PTT;
        GlobalVar.g_TestSide = TConst.T_LEFT;
        GlobalVar.g_PttLeftDBHL = 70;

///////////////////////////////////////////////////////////////////////////////////////
        PTT pttTemp = null;
        pttTemp = new PTT(null);
        pttTemp.startTest();

        for(int i=0; i<arrIsHear.length; i++){
            pttTemp.saveAnswer(arrIsHear[i]);
        }

///////////////////////////////////////////////////////////////////////////////////////
        int arrExpectHz[]            = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectLeftDBHL[]      = {   75,       75,     75,     75,     75,     75 };


        ArrayList<PttThreshold> CurThresholdList = pttTemp.get_CurScore().getThresholdList();
        Log.v(m_TAG, String.format("testSaveAnswer Thrd size:%d", CurThresholdList.size()) );

        for(int i=0; i<CurThresholdList.size(); i++){
            Log.v(m_TAG, String.format("right Thresold i:%d, Hz:%d, dB:%d",
                    i, CurThresholdList.get(i).get_Hz(), CurThresholdList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], CurThresholdList.get(i).get_Hz());
            assertEquals(arrExpectLeftDBHL[i], CurThresholdList.get(i).get_DBHL());
        }

    }

    public void testSaveAnswer_3_Right() {

        int arrIsHear[] = {
                1,      0,      0,      0,      1, // 1000
                1,      0,      0,      0,      1, // 2000
                1,      0,      0,      0,      1, // 4000
                1,      0,      0,      0,      1, // 8000
                1,      0,      0,      0,      1, // 500
                1,      0,      0,      0,      1, // 250
        };
        int arrExpectHz[]            = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectRightDBHL[]     = {   55,       55,     55,     55,     55,     55 };

        GlobalVar.g_TestType = TConst.T_PTT;
        GlobalVar.g_TestSide = TConst.T_RIGHT;
        GlobalVar.g_PttRightDBHL = 50;

///////////////////////////////////////////////////////////////////////////////////////
        PTT pttTemp = null;
        pttTemp = new PTT(null);
        pttTemp.startTest();

        for(int i=0; i<arrIsHear.length; i++){
            pttTemp.saveAnswer(arrIsHear[i]);
        }

///////////////////////////////////////////////////////////////////////////////////////

        ArrayList<PttThreshold> CurThresholdList = pttTemp.get_CurScore().getThresholdList();
        Log.v(m_TAG, String.format("testSaveAnswer Thrd size:%d", CurThresholdList.size()) );

        for(int i=0; i<CurThresholdList.size(); i++){
            Log.v(m_TAG, String.format("right Thresold i:%d, Hz:%d, dB:%d",
                    i, CurThresholdList.get(i).get_Hz(), CurThresholdList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], CurThresholdList.get(i).get_Hz());
            assertEquals(arrExpectRightDBHL[i], CurThresholdList.get(i).get_DBHL());
        }

    }

    public void testSaveAnswer_3_Left() {

        int arrIsHear[] = {
                1,      0,      0,      0,      1, // 1000
                1,      0,      0,      0,      1, // 2000
                1,      0,      0,      0,      1, // 4000
                1,      0,      0,      0,      1, // 8000
                1,      0,      0,      0,      1, // 500
                1,      0,      0,      0,      1, // 250
        };
        int arrExpectHz[]            = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectLeftDBHL[]      = {   75,       75,     75,     75,     75,     75 };

        GlobalVar.g_TestType = TConst.T_PTT;
        GlobalVar.g_TestSide = TConst.T_LEFT;
        GlobalVar.g_PttLeftDBHL = 70;

///////////////////////////////////////////////////////////////////////////////////////

        PTT pttTemp = null;
        pttTemp = new PTT(null);
        pttTemp.startTest();

        for(int i=0; i<arrIsHear.length; i++){
            pttTemp.saveAnswer(arrIsHear[i]);
        }

        ArrayList<PttThreshold> CurThresholdList = pttTemp.get_CurScore().getThresholdList();
        Log.v(m_TAG, String.format("testSaveAnswer Thrd size:%d", CurThresholdList.size()) );

        for(int i=0; i<CurThresholdList.size(); i++){
            Log.v(m_TAG, String.format("right Thresold i:%d, Hz:%d, dB:%d",
                    i, CurThresholdList.get(i).get_Hz(), CurThresholdList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], CurThresholdList.get(i).get_Hz());
            assertEquals(arrExpectLeftDBHL[i], CurThresholdList.get(i).get_DBHL());
        }

    }

    public void testSaveAnswer_MinCheck() {

        int arrIsHear[] = {
                1,      1,      1,  // 1000
                1,      1,      1,  // 2000
                1,      1,      1,  // 4000
                1,      1,      1,  // 8000
                1,      1,      1,  // 500
                1,      1,      1,  // 250
        };

        GlobalVar.g_TestType = TConst.T_PTT;
        GlobalVar.g_TestSide = TConst.T_RIGHT;
        GlobalVar.g_PttRightDBHL = 10;
        GlobalVar.g_PttLeftDBHL = 10;

///////////////////////////////////////////////////////////////////////////////////////
        PTT pttTemp = null;
        pttTemp = new PTT(null);
        pttTemp.startTest();

        for(int i=0; i<arrIsHear.length; i++){
            pttTemp.saveAnswer(arrIsHear[i]);
        }

///////////////////////////////////////////////////////////////////////////////////////
        int arrExpectHz[]            = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectRightDBHL[]     = {   0,        0,      0,      0,      0,      0 };
        int arrExpectLeftDBHL[]      = {   0,        0,      0,      0,      0,      0 };


        PttScore curScore = pttTemp.get_CurScore();

        ArrayList<PttThreshold> alPttThreshold = curScore.getThresholdList();
        Log.v(m_TAG, String.format("testSaveAnswer Thrd size:%d", alPttThreshold.size()) );

        for(int i=0; i<alPttThreshold.size(); i++){
            Log.v(m_TAG, String.format("right Thresold i:%d, Hz:%d, dB:%d",
                    i, alPttThreshold.get(i).get_Hz(), alPttThreshold.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], alPttThreshold.get(i).get_Hz());
            assertEquals(arrExpectRightDBHL[i], alPttThreshold.get(i).get_DBHL());
        }
    }

    public void testSaveAnswer_MaxCheck() {

        int arrIsHear[] = {
                0,      0,      0,  // 1000
                0,      0,      0,  // 2000
                0,      0,      0,  // 4000
                0,      0,      0,  // 8000
                0,      0,      0,  // 500
                0,      0,      0,  // 250
        };

        GlobalVar.g_TestType = TConst.T_PTT;
        GlobalVar.g_TestSide = TConst.T_RIGHT;
        GlobalVar.g_PttRightDBHL = 90;
        GlobalVar.g_PttLeftDBHL = 90;

///////////////////////////////////////////////////////////////////////////////////////
        PTT pttTemp = null;
        pttTemp = new PTT(null);
        pttTemp.startTest();

        for(int i=0; i<arrIsHear.length; i++){
            pttTemp.saveAnswer(arrIsHear[i]);
        }

///////////////////////////////////////////////////////////////////////////////////////
        int arrExpectHz[]            = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectRightDBHL[]     = {   100,      100,    100,    100,    100,    100 };
        int arrExpectLeftDBHL[]      = {   100,      100,    100,    100,    100,    100 };


        PttScore curScore = pttTemp.get_CurScore();

        ArrayList<PttThreshold> alPttThreshold = curScore.getThresholdList();
        Log.v(m_TAG, String.format("testSaveAnswer Thrd size:%d", alPttThreshold.size()) );

        for(int i=0; i<alPttThreshold.size(); i++){
            Log.v(m_TAG, String.format("current Thresold i:%d, Hz:%d, dB:%d",
                    i, alPttThreshold.get(i).get_Hz(), alPttThreshold.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], alPttThreshold.get(i).get_Hz());
            assertEquals(arrExpectRightDBHL[i], alPttThreshold.get(i).get_DBHL());
        }

    }

    public void testSaveAnswer_Sample_01_Right() {

        int arrIsHear[] = {
                1,      0,    1,  // 1000
                1,      0, 0,    1,  // 2000
                1,      0, 0, 0,    1,  // 4000
                1,      0, 0, 0, 0,    1,  // 8000
                1,      0, 0, 0, 0, 0,   1,  // 500
                1,      0, 0, 0, 0, 0, 0,   1,  // 250

        };

        int arrExpectHz[]            = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectRightDBHL[]     = {   45,       50,     55,     60,     65,     70 };

        GlobalVar.g_TestType = TConst.T_PTT;
        GlobalVar.g_TestSide = TConst.T_RIGHT;
        GlobalVar.g_PttRightDBHL = 50;

///////////////////////////////////////////////////////////////////////////////////////
        PTT pttTemp = null;
        pttTemp = new PTT(null);
        pttTemp.startTest();

        for(int i=0; i<arrIsHear.length; i++){
            pttTemp.saveAnswer(arrIsHear[i]);
        }

///////////////////////////////////////////////////////////////////////////////////////

        ArrayList<PttThreshold> RightThresholdList = pttTemp.get_CurScore().getThresholdList();
        Log.v(m_TAG, String.format("testSaveAnswer Thrd size:%d", RightThresholdList.size()) );

        for(int i=0; i<RightThresholdList.size(); i++){
            Log.v(m_TAG, String.format("right Thresold i:%d, Hz:%d, dB:%d",
                    i, RightThresholdList.get(i).get_Hz(), RightThresholdList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], RightThresholdList.get(i).get_Hz());
            assertEquals(arrExpectRightDBHL[i], RightThresholdList.get(i).get_DBHL());
        }

    }



    public void testSaveAnswer_Sample_02_Left() {

        int arrIsHear[] = {
                1,      0,    1,  // 1000
                1,      0, 0,    1,  // 2000
                1,      0, 0, 0,    1,  // 4000
                1,      0, 0, 0, 0,    1,  // 8000
                1,      0, 0, 0, 0, 0,   1,  // 500
                1,      0, 0, 0, 0, 0, 0,   1,  // 250

        };

        int arrExpectHz[]            = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectLeftDBHL[]      = {   55,       60,     65,     70,     75,     80 };


        GlobalVar.g_TestType = TConst.T_PTT;
        GlobalVar.g_TestSide = TConst.T_LEFT;
        GlobalVar.g_PttLeftDBHL = 60;

///////////////////////////////////////////////////////////////////////////////////////
        PTT pttTemp = null;
        pttTemp = new PTT(null);
        pttTemp.startTest();

        for(int i=0; i<arrIsHear.length; i++){
            pttTemp.saveAnswer(arrIsHear[i]);
        }
///////////////////////////////////////////////////////////////////////////////////////
        ArrayList<PttThreshold> LeftThresholdList = pttTemp.get_CurScore().getThresholdList();
        Log.v(m_TAG, String.format("testSaveAnswer Thrd size:%d", LeftThresholdList.size()) );

        for(int i=0; i<LeftThresholdList.size(); i++){
            Log.v(m_TAG, String.format("left Thresold i:%d, Hz:%d, dB:%d",
                    i, LeftThresholdList.get(i).get_Hz(), LeftThresholdList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], LeftThresholdList.get(i).get_Hz());
            assertEquals(arrExpectLeftDBHL[i], LeftThresholdList.get(i).get_DBHL());
        }

    }


    public void testSaveAnswer_Sample_03_Right() {

        int arrIsHear[] = {
                1,      0,    1,  // 1000
                1,      1, 0,    1,  // 2000
                1,      1, 1, 0,    1,  // 4000
                1,      1, 1, 1, 0,    1,  // 8000
                1,      1, 1, 1, 1, 0,   1,  // 500
                1,      1, 1, 1, 1, 1, 0,   1,  // 250

        };

        int arrExpectHz[]            = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectRightDBHL[]     = {   45,       35,     25,     15,     5,      0   };


        GlobalVar.g_TestType = TConst.T_PTT;
        GlobalVar.g_TestSide = TConst.T_RIGHT;
        GlobalVar.g_PttRightDBHL = 50;

///////////////////////////////////////////////////////////////////////////////////////
        PTT pttTemp = null;
        pttTemp = new PTT(null);
        pttTemp.startTest();

        for(int i=0; i<arrIsHear.length; i++){
            pttTemp.saveAnswer(arrIsHear[i]);
        }
///////////////////////////////////////////////////////////////////////////////////////

        ArrayList<PttThreshold> RightThresholdList = pttTemp.get_CurScore().getThresholdList();
        Log.v(m_TAG, String.format("testSaveAnswer Thrd size:%d", RightThresholdList.size()) );

        for(int i=0; i<RightThresholdList.size(); i++){
            Log.v(m_TAG, String.format("right Thresold i:%d, Hz:%d, dB:%d",
                    i, RightThresholdList.get(i).get_Hz(), RightThresholdList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], RightThresholdList.get(i).get_Hz());
            assertEquals(arrExpectRightDBHL[i], RightThresholdList.get(i).get_DBHL());
        }
    }

    public void testSaveAnswer_Sample_04_Left() {

        int arrIsHear[] = {
                1,      0,    1,  // 1000
                1,      1, 0,    1,  // 2000
                1,      1, 1, 0,    1,  // 4000
                1,      1, 1, 1, 0,    1,  // 8000
                1,      1, 1, 1, 1, 0,   1,  // 500
                1,      1, 1, 1, 1, 1, 0,   1,  // 250

        };

        int arrExpectHz[]            = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectLeftDBHL[]      = {   65,       55,     45,     35,     25,     15   };


        GlobalVar.g_TestType = TConst.T_PTT;
        GlobalVar.g_TestSide = TConst.T_LEFT;
        GlobalVar.g_PttLeftDBHL = 70;

///////////////////////////////////////////////////////////////////////////////////////
        PTT pttTemp = null;
        pttTemp = new PTT(null);
        pttTemp.startTest();

        for(int i=0; i<arrIsHear.length; i++){
            pttTemp.saveAnswer(arrIsHear[i]);
        }
///////////////////////////////////////////////////////////////////////////////////////

        ArrayList<PttThreshold> CurThresholdList = pttTemp.get_CurScore().getThresholdList();
        Log.v(m_TAG, String.format("testSaveAnswer Thrd size:%d", CurThresholdList.size()) );

        for(int i=0; i<CurThresholdList.size(); i++){
            Log.v(m_TAG, String.format("right Thresold i:%d, Hz:%d, dB:%d",
                    i, CurThresholdList.get(i).get_Hz(), CurThresholdList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], CurThresholdList.get(i).get_Hz());
            assertEquals(arrExpectLeftDBHL[i], CurThresholdList.get(i).get_DBHL());
        }
    }
}