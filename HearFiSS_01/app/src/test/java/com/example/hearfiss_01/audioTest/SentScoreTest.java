package com.example.hearfiss_01.audioTest;

import android.util.Log;

import com.example.hearfiss_01.audioTest.SRS.SentScore;
import com.example.hearfiss_01.audioTest.SRS.SentUnit;

import junit.framework.TestCase;

import org.junit.Test;

public class SentScoreTest extends TestCase {
    String m_TAG = "SentScoreTest";

    @Test
    public void testSentScoreCreate() {

        SentScore scoreTemp = null;
        assertNull(scoreTemp);

        scoreTemp = new SentScore();
        assertNotNull(scoreTemp);
    }

    @Test
    public void testSentScoreCreate_02() {

        SentScore scoreTemp = new SentScore();
        String[] arrQuestion = {"백화점에 가서 목걸이와 반지를 샀습니다.", "휴지를 버려 주세요" };
        String[] arrAnswer = {"백화점에 가서 목걸이와 반지를 샀습니다.", "휴지를 버려 주세요" };
        String[][] arrQuestWord = {
                {"백화점", "가서", "목걸이", "반지", "샀습니다"},
                {"휴지", "버려", "주세요"}};
        int[][] arrIdx  =   {
                {0, 1, 2, 3, 4},
                {0, 1, 2} };

        SentUnit unitAdd = null;

        for(int j=0; j<arrQuestion.length; j++){
            unitAdd = new SentUnit();
            unitAdd.set_Question(arrQuestion[j]);
            for(int i = 0; i<arrQuestWord[j].length; i++){
                unitAdd.addWordNIdx(arrQuestWord[j][i], arrIdx[j][i]);
            }
            unitAdd.saveAnswer(arrAnswer[j]);
            unitAdd.scoring();

            scoreTemp.addSentUnit(unitAdd);
        }

        scoreTemp.printSentence();
        scoreTemp.scoring();

        assertEquals(8, scoreTemp.get_iWordCorrect());
        assertEquals(8, scoreTemp.get_iWordQuest());
        assertEquals(100, scoreTemp.get_iWordScore());

        assertEquals(2, scoreTemp.get_iSentCorrect());
        assertEquals(2, scoreTemp.get_iSentQuest());
        assertEquals(100, scoreTemp.get_iSentScore());

    }

    @Test
    public void testSentScoreCreate_03() {

        SentScore scoreTemp = new SentScore();
        String[] arrQuestion = {"백화점에 가서 목걸이와 반지를 샀습니다.", "휴지를 버려 주세요" };
        String[] arrAnswer = {"가서 목걸이와 반지를 샀습니다 백화점에 .", "버려 주세요    휴지를 " };
        String[][] arrQuestWord = {
                {"백화점", "가서", "목걸이", "반지", "샀습니다"},
                {"휴지", "버려", "주세요"}};
        int[][] arrIdx  =   {
                {0, 1, 2, 3, 4},
                {0, 1, 2} };

        SentUnit unitAdd = null;

        for(int j=0; j<arrQuestion.length; j++){
            unitAdd = new SentUnit();
            for(int i = 0; i<arrQuestWord[j].length; i++){
                unitAdd.addWordNIdx(arrQuestWord[j][i], arrIdx[j][i]);
            }
            unitAdd.saveQnA(arrQuestion[j], arrAnswer[j]);

            scoreTemp.addSentUnit(unitAdd);
        }

        scoreTemp.printSentence();
        scoreTemp.scoring();

        assertEquals(0, scoreTemp.get_iWordCorrect());
        assertEquals(8, scoreTemp.get_iWordQuest());
        assertEquals(0, scoreTemp.get_iWordScore());

        assertEquals(0, scoreTemp.get_iSentCorrect());
        assertEquals(2, scoreTemp.get_iSentQuest());
        assertEquals(0, scoreTemp.get_iSentScore());

    }

    @Test
    public void testSentScoreCreate_04() {

        SentScore scoreTemp = new SentScore();
        String[] arrQuestion = {"백화점에 가서 목걸이와 반지를 샀습니다.", "휴지를 버려 주세요" };
        String[] arrAnswer = {"백화점에 가   목걸이와 반지를 샀습니다.", "휴지를 ㄴㄴ 주세요" };
        String[][] arrQuestWord = {
                {"백화점", "가서", "목걸이", "반지", "샀습니다"},
                {"휴지", "버려", "주세요"}};
        int[][] arrIdx  =   {
                {0, 1, 2, 3, 4},
                {0, 1, 2} };

        SentUnit unitAdd = null;

        for(int j=0; j<arrQuestion.length; j++){
            unitAdd = new SentUnit();
            for(int i = 0; i<arrQuestWord[j].length; i++){
                unitAdd.addWordNIdx(arrQuestWord[j][i], arrIdx[j][i]);
            }

//            unitAdd.set_Question(arrQuestion[j]);
//            unitAdd.saveAnswer(arrAnswer[j]);
//            unitAdd.scoring();
            unitAdd.saveQnA(arrQuestion[j], arrAnswer[j]);

            scoreTemp.addSentUnit(unitAdd);
        }

        scoreTemp.printSentence();
        scoreTemp.scoring();

        assertEquals(6, scoreTemp.get_iWordCorrect());
        assertEquals(8, scoreTemp.get_iWordQuest());
        assertEquals(75, scoreTemp.get_iWordScore());

        assertEquals(0, scoreTemp.get_iSentCorrect());
        assertEquals(2, scoreTemp.get_iSentQuest());
        assertEquals(0, scoreTemp.get_iSentScore());

    }
    @Test
    public void testSentScoreCreate_05() {

        SentScore scoreTemp = new SentScore();
        String[] arrQuestion = {"백화점에 가서 목걸이와 반지를 샀습니다.", "휴지를 버려 주세요" ,"물이 차다"};
        String[] arrAnswer = {"백화점에 가   목걸이와 반지를 샀습니다.", "휴지를 ㄴㄴ 주세요" ,"물이   차다"};
        String[][] arrQuestWord = {
                {"백화점", "가서", "목걸이", "반지", "샀습니다"},
                {"휴지", "버려", "주세요"},
                {"물이","차다"}};
        int[][] arrIdx  =   {
                {0, 1, 2, 3, 4},
                {0, 1, 2},
                {0,1}};

        SentUnit unitAdd = null;

        for(int j=0; j<arrQuestion.length; j++){
            unitAdd = new SentUnit();
            for(int i = 0; i<arrQuestWord[j].length; i++){
                unitAdd.addWordNIdx(arrQuestWord[j][i], arrIdx[j][i]);
            }

//            unitAdd.set_Question(arrQuestion[j]);
//            unitAdd.saveAnswer(arrAnswer[j]);
//            unitAdd.scoring();
            unitAdd.saveQnA(arrQuestion[j], arrAnswer[j]);

            scoreTemp.addSentUnit(unitAdd);
        }

        scoreTemp.printSentence();
        scoreTemp.scoring();

        assertEquals(8, scoreTemp.get_iWordCorrect());
        assertEquals(10, scoreTemp.get_iWordQuest());
        assertEquals(80, scoreTemp.get_iWordScore());

        assertEquals(1, scoreTemp.get_iSentCorrect());
        assertEquals(3, scoreTemp.get_iSentQuest());
        assertEquals(33, scoreTemp.get_iSentScore());

    }

    @Test
    public void testSentScoreCreate_06() {

        SentScore scoreTemp = new SentScore();
        String[] arrQuestion = {"백화점에 가서 목걸이와 반지를 샀습니다."};
        String[] arrAnswer = {"백화점에 가   목걸이와 반지를 샀습니다."};
        String[][] arrQuestWord = {
                {"백화점", "가서", "목걸이", "반지", "샀습니다"}
        };

        int[][] arrIdx  =   {
                {0, 1, 2, 3, 4}
                };

        SentUnit unitAdd = null;

        unitAdd = new SentUnit();
        for(int i = 0; i<arrQuestWord[0].length; i++){
            unitAdd.addWordNIdx(arrQuestWord[0][i], arrIdx[0][i]);
        }

        unitAdd.saveQnA(arrQuestion[0], arrAnswer[0]);

        scoreTemp.addSentUnit(unitAdd);

        Log.v(m_TAG, unitAdd.get_Question());
        Log.v(m_TAG, unitAdd.get_Answer());
        Log.v(m_TAG, unitAdd.get_alWordIdx().toString());
        Log.v(m_TAG, unitAdd.get_alWordUnit().toString());

        scoreTemp.printSentence();
        scoreTemp.scoring();

    }

}