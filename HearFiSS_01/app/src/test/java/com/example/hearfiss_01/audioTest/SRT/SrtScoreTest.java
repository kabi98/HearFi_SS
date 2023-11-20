package com.example.hearfiss_01.audioTest.SRT;

import static org.junit.Assert.assertNotEquals;

import android.util.Log;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

public class SrtScoreTest extends TestCase {

    @Test
    public void testSrtScoreCreate() {

        SrtScore scoreTemp = null;
        assertNull(scoreTemp);

        scoreTemp = new SrtScore();
        assertNotNull(scoreTemp);
    }
    @Test
    public void testAddTestUnit() {

        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();

        SrtUnit unitAdd = new SrtUnit("편지", "편지", 1, 60, 0);
        scoreTemp.addTestUnit(unitAdd);

        SrtUnit unitGet = new SrtUnit();
        assertNotEquals(unitAdd, unitGet);

        ArrayList<SrtUnit> alUnit = scoreTemp.getM_alSrtUnit();
        unitGet = alUnit.get(0);

        assertEquals(unitAdd, unitGet);
    }

    @Test
    public void testAddTestUnit2() {

        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAdd = null;

        unitAdd = new SrtUnit("편지", "편지", 1, 60, 0);
        scoreTemp.addTestUnit(unitAdd);

        unitAdd = new SrtUnit("땅콩", "땅콩", 1, 60, 0);
        scoreTemp.addTestUnit(unitAdd);

        SrtUnit unitGet = new SrtUnit();
        assertNotEquals(unitAdd, unitGet);

        ArrayList<SrtUnit> alUnit = scoreTemp.getM_alSrtUnit();
        unitGet = alUnit.get(1);

        assertEquals(unitAdd, unitGet);
    }


    @Test
    public void testAddTestUnit3() {

        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAdd = null;

        String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        String[] arrAnswer  =   {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        int[]    arrDB  =         { 60, 60, 50, 50, 40, 40, 30, 30, 20, 20, 10, 10, 0, 0};
        int iTsid = 1;
        int iAccId = 1;

        for(int i=0; i<arrQuestion.length; i++){
            unitAdd = new SrtUnit(arrQuestion[i], arrAnswer[i], 0, 0, 0);
            scoreTemp.addTestUnit(unitAdd);
        }

        ArrayList<SrtUnit> alUnit = scoreTemp.getM_alSrtUnit();
        assertEquals(14, alUnit.size());
    }

    public void testCountRecentDbHL() {
        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAnswer = null;
        int isCorrect = -1;
        String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        String[] arrAnswer   =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        int[]  arrDB         =  {   60,    60,    50,     50,    40,    40,    30,     30,    20,    20,    10,    10,      0,     0};
        int[]  expectQuest   =  {    1,     2,     1,      2,     1,     2,     1,      2,     1,     2,     1,      2,     1,     2};
        int[]  expectCorrect =  {    1,     2,     1,      2,     1,     2,     1,      2,     1,     2,     1,      2,     1,     2};

        unitAnswer = new SrtUnit("편지", "편지", -1, 60, 0);
        scoreTemp.countRecentDbHL(unitAnswer);
        assertEquals(1, scoreTemp.getM_iQuestion());
        assertEquals(1, scoreTemp.getM_iCorrect());

        unitAnswer = new SrtUnit("편지", "땅콩", -1, 60, 0);
        scoreTemp.countRecentDbHL(unitAnswer);
        assertEquals(1, scoreTemp.getM_iQuestion());
        assertEquals(0, scoreTemp.getM_iCorrect());
    }

    public void testCountRecentDbHL_2() {
        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAnswer = null;
        int isCorrect = -1;
        String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        String[] arrAnswer   =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        int[]  arrDB         =  {   60,    60,    50,     50,    40,    40,    30,     30,    20,    20,    10,    10,      0,     0};
        int[]  expectQuest   =  {    1,     2,     1,      2,     1,     2,     1,      2,     1,     2,     1,      2,     1,     2};
        int[]  expectCorrect =  {    1,     2,     1,      2,     1,     2,     1,      2,     1,     2,     1,      2,     1,     2};


        for(int i = 0; i<expectQuest.length; i++)
        {
            unitAnswer = new SrtUnit(arrQuestion[i], arrAnswer[i], -1, arrDB[i], 0);
            scoreTemp.countRecentDbHL(unitAnswer);
            assertEquals(expectQuest[i], scoreTemp.getM_iQuestion());
            assertEquals(expectCorrect[i], scoreTemp.getM_iCorrect());

            scoreTemp.addAnswer(unitAnswer);
        }

    }

    public void testCalcNextDbNSaveAnswer() {

        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAnswer = null;
        int isCorrect = -1;
        String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        String[] arrAnswer   =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        int[]  arrDB         =  {   60,    60,    50,     50,    40,    40,    30,     30,    20,    20,    10,    10,      0,     0};
        int[]  expectQuest   =  {    1,     2,     1,      2,     1,     2,     1,      2,     1,     2,     1,      2,     1,     2};
        int[]  expectCorrect =  {    1,     2,     1,      2,     1,     2,     1,      2,     1,     2,     1,      2,     1,     2};
        int[]  expectNextDB  =  {   60,    50,    50,     40,    40,    30,    30,     20,    20,    10,    10,      0,     0,     0};


        for(int i = 0; i<expectQuest.length; i++)
        {
            unitAnswer = new SrtUnit(arrQuestion[i], arrAnswer[i], -1, arrDB[i], 0);
            scoreTemp.calcNextDbNSaveAnswer(unitAnswer);
            assertEquals(expectQuest[i], scoreTemp.getM_iQuestion());
            assertEquals(expectCorrect[i], scoreTemp.getM_iCorrect());
            assertEquals(expectNextDB[i], unitAnswer.get_NextDb());
        }

    }

    public void testCalcNextDbNSaveAnswer_2() {

        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAnswer = null;
        int isCorrect = -1;
        String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        String[] arrAnswer   =  {"땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래", "편지"};
        int[]  arrDB         =  {   60,    60,    65,     65,    90,    90,    95,     95,   100,   100,   100,   100,    100,   100};
        int[]  expectQuest   =  {    1,     2,     1,      2,     1,     2,     1,      2,     1,     2,     3,      4,     5,     6};
        int[]  expectCorrect =  {    0,     0,     0,      0,     0,     0,     0,      0,     0,     0,     0,      0,     0,     0};
        int[]  expectNextDB  =  {   60,    65,    65,     70,    90,    95,    95,    100,   100,   100,   100,    100,   100,   100};


        for(int i = 0; i<expectQuest.length; i++)
        {
            unitAnswer = new SrtUnit(arrQuestion[i], arrAnswer[i], -1, arrDB[i], 0);
            scoreTemp.calcNextDbNSaveAnswer(unitAnswer);
            assertEquals(expectQuest[i], scoreTemp.getM_iQuestion());
            assertEquals(expectCorrect[i], scoreTemp.getM_iCorrect());
            assertEquals(expectNextDB[i], unitAnswer.get_NextDb());
        }

    }

    public void testCalcNextDbNSaveAnswer_3() {

        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAnswer = null;
        int isCorrect = -1;
        String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        String[] arrAnswer   =  {"땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래", "편지"};
        int[]  arrDB         =  {   60,    60,    65,     65,    90,    90,    95,     95,   100,   100,   100,   100,    100,   100};
        int[]  expectQuest   =  {    1,     2,     1,      2,     1,     2,     1,      2,     1,     2,     3,      4,     5,     6};
        int[]  expectCorrect =  {    0,     0,     0,      0,     0,     0,     0,      0,     0,     0,     0,      0,     0,     0};
        int[]  expectNextDB  =  {   60,    65,    65,     70,    90,    95,    95,    100,   100,   100,   100,    100,   100,   100};


        for(int i = 0; i<expectQuest.length; i++)
        {
            unitAnswer = new SrtUnit(arrQuestion[i], arrAnswer[i], -1, arrDB[i], 0);
            scoreTemp.calcNextDbNSaveAnswer(unitAnswer);
            assertEquals(expectQuest[i], scoreTemp.getM_iQuestion());
            assertEquals(expectCorrect[i], scoreTemp.getM_iCorrect());
            assertEquals(expectNextDB[i], unitAnswer.get_NextDb());
        }

    }

    public void testCalcNextDbNSaveAnswer_4() {

        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAnswer = null;
        int isCorrect = -1;
        String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        String[] arrAnswer   =  {"편지", "zdz", "저녁", "aaa", "달걀", "노래", "시간", "욕심", "aadd", "ddd", "신발", "rrr", "저녁", "노래"};
        int[]  arrDB         =  {   60,    60,    60,     50,    50,    50,    40,     40,    30,    30,    35,     35,    35,    25};
        int[]  expectQuest   =  {    1,     2,     3,      1,     2,     3,     1,      2,     1,     2,     1,      2,     3,     1};
        int[]  expectCorrect =  {    1,     1,     2,      0,     1,     2,     1,      2,     0,     0,     1,      1,     2,     1};
        int[]  expectNextDB  =  {   60,    60,    50,     50,    50,    40,    40,     30,    30,    35,    35,     35,    25,    25};


        for(int i = 0; i<expectQuest.length; i++)
        {
            Log.v("SrtScoreTest-testCalcNextDbNSaveAnswer_4",
                    String.format("i: %d", i));
            unitAnswer = new SrtUnit(arrQuestion[i], arrAnswer[i], -1, arrDB[i], 0);
            scoreTemp.calcNextDbNSaveAnswer(unitAnswer);
            assertEquals(expectQuest[i], scoreTemp.getM_iQuestion());
            assertEquals(expectCorrect[i], scoreTemp.getM_iCorrect());
            assertEquals(expectNextDB[i], unitAnswer.get_NextDb());
        }

    }

    public void testCalcNextDbNSaveAnswer_5() {

        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAnswer = null;
        int isCorrect = -1;
        String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        String[] arrAnswer   =  {"편지", "zdz", "저녁", "aaa", "달걀", "노래", "시간", "욕심", "aadd", "ddd", "신발", "rrr", "저녁", "노래"};
        int[]  arrDB         =  {   60,    60,    60,     50,    50,    50,    40,     40,    30,    30,    35,     35,    35,    25};
        int[]  expectQuest   =  {    1,     2,     3,      1,     2,     3,     1,      2,     1,     2,     1,      2,     3,     1};
        int[]  expectCorrect =  {    1,     1,     2,      0,     1,     2,     1,      2,     0,     0,     1,      1,     2,     1};
        int[]  expectNextDB  =  {   60,    60,    50,     50,    50,    40,    40,     30,    30,    35,    35,     35,    25,    25};

        int[]  expectPassTd  =  { 1000,  1000,    60,     60,    60,    50,    50,     40,    40,    40,    40,     40,    35,    35};


        for(int i = 0; i<expectQuest.length; i++)
        {
            Log.v("SrtScoreTest-testCalcNextDbNSaveAnswer_5",
                    String.format("i: %d", i));
            unitAnswer = new SrtUnit(arrQuestion[i], arrAnswer[i], -1, arrDB[i], 0);
            scoreTemp.calcNextDbNSaveAnswer(unitAnswer);
            assertEquals(expectQuest[i], scoreTemp.getM_iQuestion());
            assertEquals(expectCorrect[i], scoreTemp.getM_iCorrect());
            assertEquals(expectNextDB[i], unitAnswer.get_NextDb());
            assertEquals(expectPassTd[i], scoreTemp.getM_iPassTrsd());
        }

    }

    public void testCalcNextDbNSaveAnswer_End_1() {

        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAnswer = null;
        String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        String[] arrAnswer   =  {"편지", "aaa", "bbb", "ccc",  "ccc", "ddd", "ddd",  "eee", "eee", "ㄹㄹ", "ㄹㄹ", "마음", "저녁", "노래"};
        int[]  arrDB         =  {   80,    80,    80,     85,    85,    90,    90,     95,    95,   100,   100,    100,   100,   100};
        int[]  expectNextDB  =  {   80,    80,    85,     85,    90,    90,    95,     95,   100,   100,   100,    100,   100,   90};

        int[]  expectPassTd  =  { 1000,  1000,  1000,   1000,  1000,  1000,  1000,   1000,  1000,  1000,  1000,   1000,  1000,   100};
        boolean[] expectIsEnd = {false,  false,false,  false, false, false, false,  false, false, false,  true,   true,  true,  true};


        for(int i = 0; i<arrDB.length; i++)
        {
            Log.v("SrtScoreTest-testCalcNextDbNSaveAnswer_6",
                    String.format("i: %d", i));
            unitAnswer = new SrtUnit(arrQuestion[i], arrAnswer[i], -1, arrDB[i], 0);
            scoreTemp.calcNextDbNSaveAnswer(unitAnswer);
            assertEquals(expectNextDB[i], unitAnswer.get_NextDb());
            assertEquals(expectPassTd[i], scoreTemp.getM_iPassTrsd());
            assertEquals(expectIsEnd[i], scoreTemp.getM_isEnd());
        }

    }

    public void testCalcNextDbNSaveAnswer_End_2() {

        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAnswer = null;
        String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        String[] arrAnswer   =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        int[]  arrDB         =  {   20,    20,    10,     10,     0,     0,     0,     0,      0,     0,     0,     0,      0,     0};
        int[]  expectNextDB  =  {   20,    10,    10,      0,     0,     0,     0,     0,      0,     0,     0,     0,      0,     0};
        int[]  expectPassTd  =  { 1000,    20,    20,     10,    10,     0,     0,     0,      0,     0,     0,     0,      0,     0};
        boolean[] expectIsEnd = {false,  false,false,  false, false,   true, true,   true,  true,  true,  true,   true,  true,  true};


        for(int i = 0; i<arrDB.length; i++)
        {
            Log.v("SrtScoreTest-testCalcNextDbNSaveAnswer_6",
                    String.format("i: %d", i));
            unitAnswer = new SrtUnit(arrQuestion[i], arrAnswer[i], -1, arrDB[i], 0);
            scoreTemp.calcNextDbNSaveAnswer(unitAnswer);
            assertEquals(expectNextDB[i], unitAnswer.get_NextDb());
            assertEquals(expectPassTd[i], scoreTemp.getM_iPassTrsd());
            assertEquals(expectIsEnd[i], scoreTemp.getM_isEnd());
        }

    }


    public void testCalcNextDbNSaveAnswer_End_3() {

        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAnswer = null;
        int isCorrect = -1;
        String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        String[] arrAnswer   =  {"편지", "zdz", "저녁", "aaa", "달걀", "노래", "시간", "욕심", "aadd", "ddd", "신발", "rrr", "저녁", "노래"};
        int[]  arrDB         =  {   60,    60,    60,     50,    50,    50,    40,     40,    30,    30,    35,     35,    35,    25};
        int[]  expectCorrect =  {    1,     1,     2,      0,     1,     2,     1,      2,     0,     0,     1,      1,     2,     1};
        int[]  expectNextDB  =  {   60,    60,    50,     50,    50,    40,    40,     30,    30,    35,    35,     35,    25,    25};
        int[]  expectPassTd  =  { 1000,  1000,    60,     60,    60,    50,    50,     40,    40,    40,    40,     40,    35,    35};

        int[]  expectCurDb   =  {   60,    60,    60,     50,    50,    50,    40,     40,    30,    30,    35,     35,    35,    25};
        int[]  expectPrevDb  =  {    0,     0,     0,     60,    60,    60,    50,     50,    40,    40,    30,     30,    30,    35};
        boolean[] expectIsEnd = {false,  false,false,  false, false, false, false,  false, false, false, false,  false,  true,  true};


        for(int i = 0; i<arrQuestion.length; i++)
        {
            Log.v("SrtScoreTest-testCalcNextDbNSaveAnswer_5",
                    String.format("i: %d", i));
            unitAnswer = new SrtUnit(arrQuestion[i], arrAnswer[i], -1, arrDB[i], 0);
            scoreTemp.calcNextDbNSaveAnswer(unitAnswer);
            assertEquals(expectCorrect[i], scoreTemp.getM_iCorrect());
            assertEquals(expectNextDB[i], unitAnswer.get_NextDb());
            assertEquals(expectPassTd[i], scoreTemp.getM_iPassTrsd());
            assertEquals(expectIsEnd[i], scoreTemp.getM_isEnd());
            assertEquals(expectCurDb[i], scoreTemp.getM_iCurDb());
            assertEquals(expectPrevDb[i], scoreTemp.getM_iPrevDb());
        }

    }

    public void testCalcNextDbNSaveAnswer_End_4() {

        SrtScore scoreTemp = null;
        scoreTemp = new SrtScore();
        SrtUnit unitAnswer = null;
        int isCorrect = -1;
        String[] arrQuestion =  {"편지", "땅콩", "저녁", "안개", "달걀", "노래", "시간", "욕심", "육군", "허리", "신발", "마음", "저녁", "노래"};
        String[] arrAnswer   =  {"asf", "zdz", "asd", "dddf", "dfe", "rtr", "시간", "욕심", "육군", "허리", "ㅇㅇ", "ㅁㅁ", "저녁", "노래"};
        int[]  arrDB         =  {   60,    60,    65,     65,    70,    70,    75,     75,    65,    65,    55,     55,    60,    60};
        int[]  expectCorrect =  {    0,     0,     0,      0,     0,     0,     1,      2,     1,     2,     0,      0,     1,     2};
        int[]  expectNextDB  =  {   60,    65,    65,     70,    70,    75,    75,     65,    65,    55,    55,     60,    60,    50};
        int[]  expectPassTd  =  { 1000,  1000,  1000,   1000, 1000,   1000,  1000,     75,    75,    65,    65,     65,    65,    60};
        int[]  expectCurDb   =  {   60,    60,    65,     65,    70,    70,    75,     75,    65,    65,    55,     55,    60,    60};
        int[]  expectPrevDb  =  {    0,     0,    60,     60,    65,    65,    70,     70,    75,    75,    65,     65,    55,    55};
        boolean[] expectIsEnd = {false,  false,false,  false, false, false, false,  false, false, false, false,  false, false,  true};


        for(int i = 0; i<arrQuestion.length; i++)
        {
            Log.v("SrtScoreTest-testCalcNextDbNSaveAnswer_5",
                    String.format("i: %d", i));
            unitAnswer = new SrtUnit(arrQuestion[i], arrAnswer[i], -1, arrDB[i], 0);
            scoreTemp.calcNextDbNSaveAnswer(unitAnswer);
            assertEquals(expectCorrect[i], scoreTemp.getM_iCorrect());
            assertEquals(expectNextDB[i], unitAnswer.get_NextDb());
            assertEquals(expectPassTd[i], scoreTemp.getM_iPassTrsd());
            assertEquals(expectIsEnd[i], scoreTemp.getM_isEnd());
            assertEquals(expectCurDb[i], scoreTemp.getM_iCurDb());
            assertEquals(expectPrevDb[i], scoreTemp.getM_iPrevDb());
        }

    }
}