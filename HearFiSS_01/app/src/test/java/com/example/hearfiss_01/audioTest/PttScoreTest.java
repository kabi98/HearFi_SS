package com.example.hearfiss_01.audioTest;

import static org.junit.Assert.assertNotEquals;

import android.util.Log;

import com.example.hearfiss_01.audioTest.PTT.PttScore;
import com.example.hearfiss_01.audioTest.PTT.PttThreshold;
import com.example.hearfiss_01.audioTest.PTT.PttUnit;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

public class PttScoreTest extends TestCase {
    
    String m_TAG = "PttScoreTest";
    
    @Test
    public void testPttScoreCreate() {
        Log.v(m_TAG, "testPttScoreCreate");

        PttScore scoreTemp = null;
        assertNull(scoreTemp);

        scoreTemp = new PttScore();
        assertNotNull(scoreTemp);
    }

    @Test
    public void testAddTestUnit() {

        Log.v(m_TAG, "testAddTestUnit");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();

        PttUnit unitAdd = new PttUnit(1000, 60, 1);
        scoreTemp.addAnswer(unitAdd);

        PttUnit unitGet = new PttUnit();
        assertNotEquals(unitAdd, unitGet);

        ArrayList<PttUnit> alUnitGet = scoreTemp.getUnitList();
        assertEquals(1, alUnitGet.size());

    }

    @Test
    public void testAddTestUnit_2() {
        Log.v(m_TAG, "testAddTestUnit_2");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();

        int arrUnit[][] = { {1000, 60, 1}, {1000, 55, 1}, {1000, 50, 0}, {1000, 55, 0}, {1000, 60, 1} };

        for(int i=0; i<arrUnit.length; i++){
            PttUnit unitAdd = new PttUnit(arrUnit[i][0], arrUnit[i][1], arrUnit[i][2]);
            scoreTemp.addAnswer(unitAdd);
        }

        ArrayList<PttUnit> alUnitGet = scoreTemp.getUnitList();
        assertEquals(5, alUnitGet.size());

        scoreTemp.print();

        for(int i=0; i<alUnitGet.size(); i++){
            assertEquals(arrUnit[i][0], alUnitGet.get(i).get_Hz());
            assertEquals(arrUnit[i][1], alUnitGet.get(i).get_DBHL());
            assertEquals(arrUnit[i][2], alUnitGet.get(i).get_IsHearing());
        }
    }

    @Test
    public void testClear() {
        Log.v(m_TAG, "testClear");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();

        int arrUnit[][] = { {1000, 60, 1}, {1000, 55, 1}, {1000, 50, 0}, {1000, 55, 0}, {1000, 60, 1} };

        for(int i=0; i<arrUnit.length; i++){
            PttUnit unitAdd = new PttUnit(arrUnit[i][0], arrUnit[i][1], arrUnit[i][2]);
            scoreTemp.addAnswer(unitAdd);
        }

        ArrayList<PttUnit> alUnitGet = null;
        alUnitGet = scoreTemp.getUnitList();
        assertEquals(5, alUnitGet.size());

        scoreTemp.clear();
        alUnitGet = scoreTemp.getUnitList();
        assertEquals(0, alUnitGet.size());
    }

    @Test
    public void testAddAnswer() {
        Log.v(m_TAG, "testAddAnswer");
        PttScore scoreTemp = null;
        PttUnit unitAdd = null;
        scoreTemp = new PttScore();

        int arrUnit[][] = { {1000, 60, 1}, {1000, 55, 1}, {1000, 50, 0}, {1000, 55, 0}, {1000, 60, 1}};
        int expectTurnUp[] = { 0, 0, 1, 1, 1 };

        for(int i=0; i<arrUnit.length; i++){
            unitAdd = new PttUnit(arrUnit[i][0], arrUnit[i][1], arrUnit[i][2]);
            scoreTemp.addAnswer(unitAdd);
            Log.v(m_TAG, String.format("i:%d, expect:%d, get:%d, prev:%d, cur:%d, next:%d",
                                                i, expectTurnUp[i], scoreTemp.get_iTurnUp(),
                                                scoreTemp.get_iPrevDbHL(), scoreTemp.get_iCurDbHL(),
                                                scoreTemp.get_iNextDbHL()));
            assertEquals(expectTurnUp[i], scoreTemp.get_iTurnUp());
        }
    }

    @Test
    public void testAddAnswer_2() {
        Log.v(m_TAG, "testAddTestUnit_2");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrHz[]     =   {     1000,   1000,   1000,   1000,   1000 };
        int arrDBHL[]   =   {     60,     55,     50,     55,     60 };
        int arrIsHear[] =   {     1,      1,      0,      0,      1 };
        int expectTurnUp[] =    { 0,      0,      1,      1,      1 };
        int expectTurnDown[] =  { 1,      1,      1,      1,      2 };

        for(int i=0; i<arrHz.length; i++){
            unitAdd = new PttUnit(arrHz[i], arrDBHL[i], arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            Log.v(m_TAG, String.format("i:%d, TurnUp:%d, TurnDown:%d, prev:%d, cur:%d, next:%d",
                    i, scoreTemp.get_iTurnUp(), scoreTemp.get_iTurnDown(),
                    scoreTemp.get_iPrevDbHL(), scoreTemp.get_iCurDbHL(),
                    scoreTemp.get_iNextDbHL()));
            assertEquals(expectTurnUp[i], scoreTemp.get_iTurnUp());
            assertEquals(expectTurnDown[i], scoreTemp.get_iTurnDown());

        }

    }

    @Test
    public void testAddAnswer_3() {
        Log.v(m_TAG, "testAddAnswer_3");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrHz[]             = {     1000,   1000,   1000,   1000,   1000,   1000,   1000,   1000,   1000,   1000,};
        int arrDBHL[]           = {     60,     55,     50,     55,     60,     55,     50,     55,     60,     65 };
        int arrIsHear[]         = {     1,      1,      0,      0,      1,      1,      0,      0,      0,      1 };

        int expectTurnUp[]      = {     0,      0,      1,      1,      1,      1,      2,      2,      2,      2 };
        int expectTurnDown[]    = {     1,      1,      1,      1,      2,      2,      2,      2,      2,      3 };

        for(int i=0; i<arrHz.length; i++){
            unitAdd = new PttUnit(arrHz[i], arrDBHL[i], arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            Log.v(m_TAG, String.format("i:%d, up:%d, down:%d, prev:%d, cur:%d, next:%d",
                    i, scoreTemp.get_iTurnUp(), scoreTemp.get_iTurnDown(),
                    scoreTemp.get_iPrevDbHL(), scoreTemp.get_iCurDbHL(),
                    scoreTemp.get_iNextDbHL()));
            assertEquals(expectTurnUp[i], scoreTemp.get_iTurnUp());
            assertEquals(expectTurnDown[i], scoreTemp.get_iTurnDown());

        }

    }

    @Test
    public void testAddAnswer_4() {
        Log.v(m_TAG, "testAddAnswer_4");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrHz[]             = {     1000,   1000,   1000,   1000,   1000,   1000,   1000,   1000,   1000,   1000,};
        int arrIsHear[]         = {     1,      1,      0,      0,      1,      1,      0,      0,      0,      1 };

        int expectTurnUp[]      = {     0,      0,      1,      1,      1,      1,      2,      2,      2,      2 };
        int expectTurnDown[]    = {     1,      1,      1,      1,      2,      2,      2,      2,      2,      3 };

        scoreTemp.set_iStartDBHL(60);

        for(int i=0; i<arrHz.length; i++){
            unitAdd = new PttUnit(arrHz[i], scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            Log.v(m_TAG, String.format("i:%d, up:%d, down:%d, prev:%d, cur:%d, next:%d",
                    i, scoreTemp.get_iTurnUp(), scoreTemp.get_iTurnDown(),
                    scoreTemp.get_iPrevDbHL(), scoreTemp.get_iCurDbHL(),
                    scoreTemp.get_iNextDbHL()));
            assertEquals(expectTurnUp[i], scoreTemp.get_iTurnUp());
            assertEquals(expectTurnDown[i], scoreTemp.get_iTurnDown());

        }

    }

    @Test
    public void testCheckHzTestEnd() {
        Log.v(m_TAG, "testCheckHzTestEnd");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[]         = {     1,      1,      0,      0,      1,      };

        boolean expectIsHzEnd[]  = {    false,  false,  false,  false,  true,    };

        scoreTemp.set_iStartDBHL(60);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            Log.v(m_TAG, String.format("i:%d, up:%d, down:%d, prev:%d, cur:%d, next:%d",
                    i, scoreTemp.get_iTurnUp(), scoreTemp.get_iTurnDown(),
                    scoreTemp.get_iPrevDbHL(), scoreTemp.get_iCurDbHL(),
                    scoreTemp.get_iNextDbHL()));

            assertEquals(expectIsHzEnd[i], scoreTemp.isEndHzTest());
            scoreTemp.checkHzThreshold();
            scoreTemp.checkHzTestChange();
        }

    }

    @Test
    public void testCheckHzTestEnd_2() {
        Log.v(m_TAG, "testCheckHzTestEnd_2");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[]         = {     1,      1,      0,      0,      1,      1,      1,      0,      0,      1, };
        boolean expectIsHzEnd[]  = {    false,  false,  false,  false,  true,   false,  false,  false,  false,  true, };
        boolean isEnd = false;

        scoreTemp.set_iStartDBHL(60);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            Log.v(m_TAG, String.format("i:%d, Hz:%d, dB:%d, isHear:%d, prev:%d, cur:%d, next:%d, idEnd:%b",
                    i, unitAdd.get_Hz(), unitAdd.get_DBHL(), unitAdd.get_IsHearing(),
                    scoreTemp.get_iPrevDbHL(), scoreTemp.get_iCurDbHL(),
                    scoreTemp.get_iNextDbHL(), scoreTemp.isEndHzTest()));

            isEnd = scoreTemp.isEndHzTest();
            assertEquals(expectIsHzEnd[i], isEnd);
            scoreTemp.checkHzThreshold();
            scoreTemp.checkHzTestChange();
        }

    }

    @Test
    public void testCheckHzTestEnd_3() {
        Log.v(m_TAG, "testCheckHzTestEnd_3");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[]         = {     1,      1,      0,      0,      1,     1,      1,      0,      0,      1,
                                        1,      1,      0,      0,      1,     1,      1,      0,      0,      1,
                                        1,      1,      0,      0,      1,     1,      1,      0,      0,      1, };

        boolean expectIsEnd[]  = {      false,  false,  false,  false,  true,  false,  false,  false,  false,  true,
                                        false,  false,  false,  false,  true,  false,  false,  false,  false,  true,
                                        false,  false,  false,  false,  true,  false,  false,  false,  false,  true, };

        scoreTemp.set_iStartDBHL(60);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            Log.v(m_TAG, String.format("i:%d, Hz:%d, dB:%d, isHear:%d, prev:%d, cur:%d, next:%d",
                    i, unitAdd.get_Hz(), unitAdd.get_DBHL(), unitAdd.get_IsHearing(),
                    scoreTemp.get_iPrevDbHL(), scoreTemp.get_iCurDbHL(),
                    scoreTemp.get_iNextDbHL()));

            assertEquals(expectIsEnd[i], scoreTemp.isEndHzTest());
            scoreTemp.checkHzThreshold();
            scoreTemp.checkHzTestChange();
        }

    }

    @Test
    public void testSaveThresold() {
        Log.v(m_TAG, "testSaveThresold");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[] = {
                1,      1,      0,      0,      1,     1,      1,      0,      0,      1,
                1,      1,      0,      0,      1,     1,      1,      0,      0,      1,
                1,      1,      0,      0,      1,     1,      1,      0,      0,      1, };
        int arrExpectHz[]       = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectDBHL[]     = {   50,       50,     50,     50,     50,     50 };

        scoreTemp.set_iStartDBHL(60);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            scoreTemp.checkHzThreshold();
            scoreTemp.checkHzTestChange();
        }

        ArrayList<PttThreshold> alThList = scoreTemp.getThresholdList();
        Log.v(m_TAG, String.format("Thresold sizei:%d", alThList.size()) );

        for(int i=0; i<alThList.size(); i++){
            Log.v(m_TAG, String.format("Thresold i:%d, Hz:%d, dB:%d",
                    i, alThList.get(i).get_Hz(), alThList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], alThList.get(i).get_Hz());
            assertEquals(arrExpectDBHL[i], alThList.get(i).get_DBHL());
        }

    }

    @Test
    public void testSaveThresold_2() {
        Log.v(m_TAG, "testSaveThresold_2");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[] = {
                1,      0,      0,      0,      1,
                1,      1,      1,      0,      1,
                1,      1,      1,      0,      0,      0,      1,
                1,      1,      1,      1,      0,      0,      1,
                1,      0,      0,      0,      0,      0,      1,
                1,      1,      1,      1,      1,      0,      1,
        };

        int arrExpectHz[]       = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectDBHL[]     = {   65,       35,     45,     30,     75,     15 };

        scoreTemp.set_iStartDBHL(60);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            scoreTemp.checkHzThreshold();
            scoreTemp.checkHzTestChange();
        }

        ArrayList<PttThreshold> alThList = scoreTemp.getThresholdList();
        Log.v(m_TAG, String.format("Thresold sizei:%d", alThList.size()) );

        for(int i=0; i<alThList.size(); i++){
            Log.v(m_TAG, String.format("Thresold i:%d, Hz:%d, dB:%d",
                    i, alThList.get(i).get_Hz(), alThList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], alThList.get(i).get_Hz());
            assertEquals(arrExpectDBHL[i], alThList.get(i).get_DBHL());
        }

    }

    @Test
    public void testSaveThresold_3() {
        Log.v(m_TAG, "testSaveThresold_3");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[] = {
                1,      0,      0,      0,      1,
                1,      1,      0,      0,      0,      1,
                1,      1,      1,      0,      0,      0,      1,
                1,      1,      1,      1,      0,      0,      0,      1,
                1,      1,      1,      1,      1,      0,      0,      0,      1,
                1,      1,      1,      1,      1,      1,      0,      0,      0,      1,
        };

        int arrExpectHz[]       = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectDBHL[]     = {   65,       55,     45,     35,     25,     15 };

        scoreTemp.set_iStartDBHL(60);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            scoreTemp.checkHzThreshold();
            scoreTemp.checkHzTestChange();
        }

        ArrayList<PttThreshold> alThList = scoreTemp.getThresholdList();
        Log.v(m_TAG, String.format("Thresold sizei:%d", alThList.size()) );

        for(int i=0; i<alThList.size(); i++){
            Log.v(m_TAG, String.format("Thresold i:%d, Hz:%d, dB:%d",
                    i, alThList.get(i).get_Hz(), alThList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], alThList.get(i).get_Hz());
            assertEquals(arrExpectDBHL[i], alThList.get(i).get_DBHL());
        }

    }

    @Test
    public void testSaveThresold_4() {
        Log.v(m_TAG, "testSaveThresold_4");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[] = {
                1,      0,      0,      0,      1,
                1,      0,      0,      0,      0,      1,
                1,      0,      0,      0,      0,      0,      1,
                1,      0,      0,      0,      0,      0,      0,      1,
                1,      0,      0,      0,      0,      0,      0,      0,      1,
                1,      0,      0,      0,      0,      0,      0,      0,      0,      1,
        };

        int arrExpectHz[]       = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectDBHL[]     = {   65,       70,     75,     80,     85,     90 };

        scoreTemp.set_iStartDBHL(60);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            scoreTemp.checkHzThreshold();
            scoreTemp.checkHzTestChange();
        }

        ArrayList<PttThreshold> alThList = scoreTemp.getThresholdList();
        Log.v(m_TAG, String.format("Thresold sizei:%d", alThList.size()) );

        for(int i=0; i<alThList.size(); i++){
            Log.v(m_TAG, String.format("Thresold i:%d, Hz:%d, dB:%d",
                    i, alThList.get(i).get_Hz(), alThList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], alThList.get(i).get_Hz());
            assertEquals(arrExpectDBHL[i], alThList.get(i).get_DBHL());
        }

    }

    @Test
    public void testMinDBHLTest() {
        Log.v(m_TAG, "testMinDBHLTest");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[]         = {     1,      1,      1,      1,      1,      };

        int arrNextDBHL[]       = {     10,     0,      0,      0,      0,      };

        scoreTemp.set_iStartDBHL(20);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            scoreTemp.checkHzThreshold();
            Log.v(m_TAG, String.format("i:%d, up:%d, down:%d, prev:%d, cur:%d, next:%d",
                    i, scoreTemp.get_iTurnUp(), scoreTemp.get_iTurnDown(),
                    scoreTemp.get_iPrevDbHL(), scoreTemp.get_iCurDbHL(),
                    scoreTemp.get_iNextDbHL()));

            assertEquals(arrNextDBHL[i], scoreTemp.get_iNextDbHL());
        }

    }

    @Test
    public void testMinDBHLTest_2() {
        Log.v(m_TAG, "testMinDBHLTest_2");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[]         = {     1,      1,      1,      1,      1,      };

        int arrNextDBHL[]       = {     10,     0,      0,      10,     0,      };

        scoreTemp.set_iStartDBHL(20);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            Log.v(m_TAG, String.format("i:%d, up:%d, down:%d, prev:%d, cur:%d, next:%d",
                    i, scoreTemp.get_iTurnUp(), scoreTemp.get_iTurnDown(),
                    scoreTemp.get_iPrevDbHL(), scoreTemp.get_iCurDbHL(),
                    scoreTemp.get_iNextDbHL()));

            assertEquals(arrNextDBHL[i], scoreTemp.get_iNextDbHL());
            scoreTemp.checkHzThreshold();
            scoreTemp.checkHzTestChange();
        }

    }

    @Test
    public void testMinDBHLTest_3() {
        Log.v(m_TAG, "testMinDBHLTest_3");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[]         = {
                1,      1,      1, // 1000
                1,      1,      1, // 2000
                1,      1,      1, // 4000
                1,      1,      1, // 8000
                1,      1,      1, // 500
                1,      1,      1, // 250
                1,      };

        int arrNextDBHL[]       = {
                10,      0,      0, // 1000
                10,      0,      0, // 2000
                10,      0,      0, // 4000
                10,      0,      0, // 8000
                10,      0,      0, // 500
                10,      0,      0, // 250
                0,            };
        int arrExpectHz[]       = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectDBHL[]     = {   0,        0,      0,      0,      0,      0 };

        scoreTemp.set_iStartDBHL(20);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            assertEquals(arrNextDBHL[i], scoreTemp.get_iNextDbHL());
            scoreTemp.checkHzThreshold();
            scoreTemp.checkHzTestChange();
        }

        ArrayList<PttThreshold> alThList = scoreTemp.getThresholdList();
        Log.v(m_TAG, String.format("Thresold sizei:%d", alThList.size()) );

        for(int i=0; i<alThList.size(); i++){
            Log.v(m_TAG, String.format("Thresold i:%d, Hz:%d, dB:%d",
                    i, alThList.get(i).get_Hz(), alThList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], alThList.get(i).get_Hz());
            assertEquals(arrExpectDBHL[i], alThList.get(i).get_DBHL());
        }

    }

    @Test
    public void testMaxDBHLTest() {
        Log.v(m_TAG, "testMaxDBHLTest");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[]         = {     0,      0,      0,      0,      0,      };

        int arrNextDBHL[]       = {     95,     100,    100,    100,    100,      };

        scoreTemp.set_iStartDBHL(90);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            Log.v(m_TAG, String.format("i:%d, up:%d, down:%d, prev:%d, cur:%d, next:%d",
                    i, scoreTemp.get_iTurnUp(), scoreTemp.get_iTurnDown(),
                    scoreTemp.get_iPrevDbHL(), scoreTemp.get_iCurDbHL(),
                    scoreTemp.get_iNextDbHL()));

            assertEquals(arrNextDBHL[i], scoreTemp.get_iNextDbHL());
        }
    }

    @Test
    public void testMaxDBHLTest_2() {
        Log.v(m_TAG, "testMaxDBHLTest_2");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[]         = {     0,      0,      0,      0,      0,      };

        int arrNextDBHL[]       = {     95,     100,    100,    95,    100,      };

        scoreTemp.set_iStartDBHL(90);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            Log.v(m_TAG, String.format("i:%d, up:%d, down:%d, prev:%d, cur:%d, next:%d",
                    i, scoreTemp.get_iTurnUp(), scoreTemp.get_iTurnDown(),
                    scoreTemp.get_iPrevDbHL(), scoreTemp.get_iCurDbHL(),
                    scoreTemp.get_iNextDbHL()));

            assertEquals(arrNextDBHL[i], scoreTemp.get_iNextDbHL());
            scoreTemp.checkHzThreshold();
            scoreTemp.checkHzTestChange();
        }
    }

    @Test
    public void testMaxDBHLTest_3() {
        Log.v(m_TAG, "testMaxDBHLTest_2");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[]         = {
                0,      0,      0, // 1000
                0,      0,      0, // 2000
                0,      0,      0, // 4000
                0,      0,      0, // 8000
                0,      0,      0, // 500
                0,      0,      0, // 250
                0,      };

        int arrNextDBHL[]       = {
                95,     100,    100, // 1000
                95,     100,    100, // 2000
                95,     100,    100, // 4000
                95,     100,    100, // 8000
                95,     100,    100, // 500
                95,     100,    100, // 250
                100,            };
        int arrExpectHz[]       = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectDBHL[]     = {   100,      100,    100,    100,    100,    100 };

        scoreTemp.set_iStartDBHL(90);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            assertEquals(arrNextDBHL[i], scoreTemp.get_iNextDbHL());
            scoreTemp.checkHzThreshold();
            scoreTemp.checkHzTestChange();
        }

        ArrayList<PttThreshold> alThList = scoreTemp.getThresholdList();
        Log.v(m_TAG, String.format("Threshold sizei:%d", alThList.size()) );

        for(int i=0; i<alThList.size(); i++){
            Log.v(m_TAG, String.format("Thresold i:%d, Hz:%d, dB:%d",
                    i, alThList.get(i).get_Hz(), alThList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], alThList.get(i).get_Hz());
            assertEquals(arrExpectDBHL[i], alThList.get(i).get_DBHL());
        }
    }

    @Test
    public void testOutBoundayMin_01() {
        Log.v(m_TAG, "testMinDBHLTest_3");
        PttScore scoreTemp = null;
        scoreTemp = new PttScore();
        PttUnit unitAdd = null;

        int arrIsHear[]         = {
                1,      1,      1, // 1000
                1,      1,      1, // 2000
                1,      1,      1, // 4000
                1,      1,      1, // 8000
                1,      1,      1, // 500
                1,      1,      1, // 250
                1,      };

        int arrNextDBHL[]       = {
                5,      0,      0, // 1000
                5,      0,      0, // 2000
                5,      0,      0, // 4000
                5,      0,      0, // 8000
                5,      0,      0, // 500
                5,      0,      0, // 250
                0,            };
        int arrExpectHz[]       = {   1000,     2000,   4000,   8000,   500,    250 };
        int arrExpectDBHL[]     = {   0,        0,      0,      0,      0,      0 };

        scoreTemp.set_iStartDBHL(15);

        int iRet = 0;
        for(int i=0; i<arrIsHear.length; i++){
            unitAdd = new PttUnit(scoreTemp.get_iCurHz(), scoreTemp.get_iNextDbHL(), arrIsHear[i]);
            scoreTemp.addAnswer(unitAdd);
            assertEquals(arrNextDBHL[i], scoreTemp.get_iNextDbHL());
//            Log.v(m_TAG, String.format("Get Next DBHL:%d", scoreTemp.get_iNextDbHL()) );
            scoreTemp.checkHzThreshold();
            scoreTemp.checkHzTestChange();
        }

        ArrayList<PttThreshold> alThList = scoreTemp.getThresholdList();
        Log.v(m_TAG, String.format("Threshold size:%d", alThList.size()) );

        for(int i=0; i<alThList.size(); i++){
            Log.v(m_TAG, String.format("Threshold i:%d, Hz:%d, dB:%d",
                    i, alThList.get(i).get_Hz(), alThList.get(i).get_DBHL()));
            assertEquals(arrExpectHz[i], alThList.get(i).get_Hz());
            assertEquals(arrExpectDBHL[i], alThList.get(i).get_DBHL());
        }

    }



}