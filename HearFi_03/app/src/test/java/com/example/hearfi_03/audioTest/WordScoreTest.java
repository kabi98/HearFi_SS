package com.example.hearfi_03.audioTest;

import static org.junit.Assert.assertNotEquals;

import android.util.Log;

import com.example.hearfi_03.audioTest.WRS.WordScore;
import com.example.hearfi_03.audioTest.WRS.WordUnit;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

public class WordScoreTest extends TestCase {
    String m_TAG = "WordScoreTest";
    @Test
    public void testWordScoreCreate() {

        WordScore scoreTemp = null;
        assertNull(scoreTemp);

        scoreTemp = new WordScore();
        assertNotNull(scoreTemp);
    }

    @Test
    public void testAddWordUnit() {

        WordScore scoreTemp = null;
        scoreTemp = new WordScore();

        WordUnit wuAdd = new WordUnit("편지", "편지", -1);
        WordUnit unitGet = new WordUnit();
        assertNotEquals(wuAdd, unitGet);

        scoreTemp.addWordUnit(wuAdd);

        ArrayList<WordUnit> alUnit = scoreTemp.getM_alWord();
        unitGet = alUnit.get(0);

        assertEquals(wuAdd, unitGet);
    }

    public void testAddWordUnit_NULL() {

        WordScore scoreTemp = null;
        scoreTemp = new WordScore();

        WordUnit wuAdd = null;
        assertNull(wuAdd);
        scoreTemp.addWordUnit(wuAdd);

        ArrayList<WordUnit> alUnit = scoreTemp.getM_alWord();
        assertEquals(0,alUnit.size());
    }

    public void testAddWordUnit_Many() {

        WordScore scoreTemp = null;
        WordUnit wuAdd = null;

        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다"};
        String[] arrAnswer  =   {"백화점", "가서", "목걸이", "반지", "샀습니다"};

        scoreTemp = new WordScore();
        scoreTemp.addWordUnit(wuAdd);

        for(int i=0; i<arrQuestion.length; i++){
            wuAdd = new WordUnit(arrQuestion[i], arrAnswer[i], -1);
            scoreTemp.addWordUnit(wuAdd);
        }

        ArrayList<WordUnit> alWord = scoreTemp.getM_alWord();

        scoreTemp.printWordList();
        assertEquals(5,alWord.size());
    }

    public void testAddWordUnit_Many_02() {

        WordScore scoreTemp = null;
        WordUnit wuAdd = null;

        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다"};
        String[] arrAnswer  =   {"백화점", "가서", "목이", "지", "샀습니다"};
        int[]  expectCorrect =  { 1, 1, 0, 0, 1};

        scoreTemp = new WordScore();
        scoreTemp.addWordUnit(wuAdd);

        for(int i=0; i<arrQuestion.length; i++){
            wuAdd = new WordUnit(arrQuestion[i], arrAnswer[i], -1);
            scoreTemp.addWordUnit(wuAdd);
        }
        scoreTemp.printWordList();

        ArrayList<WordUnit> alWord = scoreTemp.getM_alWord();
        assertEquals(5,alWord.size());

        for(int i=0; i<alWord.size(); i++){
            assertEquals(arrQuestion[i], alWord.get(i).get_Question());
            assertEquals(arrAnswer[i], alWord.get(i).get_Answer());
            assertEquals(expectCorrect[i], alWord.get(i).get_Correct());
        }

    }

    public void testScoringWordList() {

        WordScore scoreTemp = null;
        WordUnit wuAdd = null;

        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다"};
        String[] arrAnswer  =   {"백화점", "가서", "목이", "지", "샀습니다"};
        int[]  expectCorrect =  { 1, 1, 0, 0, 1};

        scoreTemp = new WordScore();
        scoreTemp.addWordUnit(wuAdd);

        for(int i=0; i<arrQuestion.length; i++){
            wuAdd = new WordUnit(arrQuestion[i], arrAnswer[i], -1);
            scoreTemp.addWordUnit(wuAdd);
        }
        scoreTemp.printWordList();

        scoreTemp.scoringWordList();

        assertEquals(5,scoreTemp.getM_iQuestion());
        assertEquals(3,scoreTemp.getM_iCorrect());
        assertEquals(60,scoreTemp.getM_iScore());

    }

    public void testScoringWordList_02() {

        WordScore scoreTemp = null;
        WordUnit wuAdd = null;

        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다"};
        String[] arrAnswer  =   {"백화점", "가서", "목걸이", "반지", "샀습니다"};

        scoreTemp = new WordScore();
        scoreTemp.addWordUnit(wuAdd);

        for(int i=0; i<arrQuestion.length; i++){
            wuAdd = new WordUnit(arrQuestion[i], arrAnswer[i], -1);
            scoreTemp.addWordUnit(wuAdd);
        }
        scoreTemp.printWordList();

        scoreTemp.scoringWordList();

        assertEquals(5,scoreTemp.getM_iQuestion());
        assertEquals(5,scoreTemp.getM_iCorrect());
        assertEquals(100,scoreTemp.getM_iScore());

    }

    public void testScoringWordList_03() {

        WordScore scoreTemp = null;
        WordUnit wuAdd = null;

        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다"};
        String[] arrAnswer  =   {"백화", "서", "걸이", "지", "습니다"};

        scoreTemp = new WordScore();
        scoreTemp.addWordUnit(wuAdd);

        for(int i=0; i<arrQuestion.length; i++){
            wuAdd = new WordUnit(arrQuestion[i], arrAnswer[i], -1);
            scoreTemp.addWordUnit(wuAdd);
        }
        scoreTemp.printWordList();
        scoreTemp.scoringWordList();

        assertEquals(5,scoreTemp.getM_iQuestion());
        assertEquals(0,scoreTemp.getM_iCorrect());
        assertEquals(0,scoreTemp.getM_iScore());
    }

    public void testScoringWordList_04() {

        WordScore scoreTemp = null;
        WordUnit wuAdd = null;

        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다", "휴지", "버려", "주세요", "우체국", "병원", "앞", "있어요", "약", "하루", "두", "번씩", "드세요"};
        String[] arrAnswer   =  {"백화점", "가서", "목걸이", "반지", "샀습니다", "휴지", "버려", "주세요", "우체국", "병원", "앞", "있어요", "약", "하루", "두", "번씩", "드세요"};

        scoreTemp = new WordScore();
        scoreTemp.addWordUnit(wuAdd);

        for(int i=0; i<arrQuestion.length; i++){
            wuAdd = new WordUnit(arrQuestion[i], arrAnswer[i], -1);
            scoreTemp.addWordUnit(wuAdd);
        }
        scoreTemp.printWordList();
        scoreTemp.scoringWordList();

        assertEquals(17,scoreTemp.getM_iQuestion());
        assertEquals(17,scoreTemp.getM_iCorrect());
        assertEquals(100,scoreTemp.getM_iScore());
    }

    public void testScoringWordList_05() {

        WordScore scoreTemp = null;
        WordUnit wuAdd = null;

        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다", "휴지", "버려", "주세요", "우체국", "병원", "앞", "있어요", "약", "하루", "두", "번씩", "드세요"};
        String[] arrAnswer   =  {"백화점", "가서", "목", "반지", "습니다", "휴지", "버려", "주세요", "체국", "병원", "앞", "어요", "약", "하루", "두", "번씩", "드세요"};

        scoreTemp = new WordScore();
        scoreTemp.addWordUnit(wuAdd);

        for(int i=0; i<arrQuestion.length; i++){
            wuAdd = new WordUnit(arrQuestion[i], arrAnswer[i], -1);
            scoreTemp.addWordUnit(wuAdd);
        }
        scoreTemp.printWordList();
        scoreTemp.scoringWordList();

        assertEquals(17,scoreTemp.getM_iQuestion());
        assertEquals(13,scoreTemp.getM_iCorrect());
        assertEquals(76,scoreTemp.getM_iScore());
    }

    public void testGetGradeFromScore_01() {
        WordScore scoreTemp = null;
        String strGet;

        int [] arrScore             =  { 150,       100,        96,         95,     86,     85,     80,     79,      70,    69,    50,    49,          0,           -100 };
        String[] arrExpectGrade     =  { "매우 우수", "매우 우수", "매우 우수", "우수", "우수",  "좋음",  "좋음",  "보통",  "보통", "저조", "저조", "매우 저조",  "매우 저조",  "매우 저조"};

        scoreTemp = new WordScore();

        for(int i=0; i<arrScore.length; i++){
            scoreTemp.setM_iScore(arrScore[i]);
            strGet = scoreTemp.getGradeFromScore();
            Log.v(m_TAG, String.format("testGetGradeFromScore i:%d, Score:%d, Grade:%s, Get:%s",
                    i, arrScore[i], arrExpectGrade[i], strGet));
            assertEquals(arrExpectGrade[i], strGet);
        }
    }
}