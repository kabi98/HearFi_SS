package com.example.hearfiss_01.audioTest;

import static org.junit.Assert.assertNotEquals;

import com.example.hearfiss_01.audioTest.SRS.SentUnit;

import junit.framework.TestCase;

import org.junit.Test;

public class SentUnitTest extends TestCase {
    @Test
    public void testSentUnitCreate() {

        SentUnit unitTemp = null;
        assertNull(unitTemp);

        unitTemp = new SentUnit();
        assertNotNull(unitTemp);
    }

    @Test
    public void testSentUnitCreate_02() {

        SentUnit unitTemp = new SentUnit();
        assertNotNull(unitTemp);

        SentUnit unitAdd = new SentUnit();
        unitAdd.set_Question("백화점에 가서 목걸이와 반지를 샀습니다.");
        assertNotEquals(unitTemp, unitAdd);

        assertEquals("백화점에 가서 목걸이와 반지를 샀습니다.", unitAdd.get_Question());
    }


    @Test
    public void testSentUnitCreate_03() {
        String strQuestion =  "백화점에 가서 목걸이와 반지를 샀습니다";
        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다"};
        int[] arrIdx  =   {0, 1, 2, 3, 4};

        String strAnswer   =  "백화점에 가서 목걸이와 반지를 샀습니다";

        SentUnit unitAdd = new SentUnit();

        unitAdd.set_Question(strQuestion);
        for(int i = 0; i<arrQuestion.length; i++){
            unitAdd.addWordNIdx(arrQuestion[i], arrIdx[i]);
        }

        unitAdd.printWordList();

        assertEquals(strQuestion, unitAdd.get_Question());
        assertEquals(5, unitAdd.get_alWordUnit().size() );
        assertEquals(5, unitAdd.get_alWordIdx().size() );

    }

    @Test
    public void testSentUnitSaveAnswer() {
        String strQuestion =  "백화점에 가서 목걸이와 반지를 샀습니다";
        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다"};
        int[] arrIdx  =   {0, 1, 2, 3, 4};

        String strAnswer   =  "   백화점에 가서 목걸이와 반지를 샀습니다   ";
        String[] arrAnswer =  {"백화점에", "가서", "목걸이와", "반지를", "샀습니다"};

        SentUnit unitAdd = new SentUnit();

        unitAdd.set_Question(strQuestion);
        for(int i = 0; i<arrQuestion.length; i++){
            unitAdd.addWordNIdx(arrQuestion[i], arrIdx[i]);
        }

        unitAdd.saveAnswer(strAnswer);

        unitAdd.printWordList();

        assertEquals(strQuestion, unitAdd.get_Question());
        assertEquals(5, unitAdd.get_alWordUnit().size() );
        assertEquals(5, unitAdd.get_alWordIdx().size() );

    }

    @Test
    public void testSentUnitSaveAnswer_02() {
        String strQuestion =  "백화점에 가서 목걸이와 반지를 샀습니다";
        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다"};
        int[] arrIdx  =   {0, 1, 2, 3, 4};

        String strAnswer   =  "   백화점에 가서 목걸이와 반지를 샀습니다   ";
        String[] arrAnswer =  {"백화점에", "가서", "목걸이와", "반지를", "샀습니다"};

        SentUnit unitAdd = new SentUnit();

        unitAdd.set_Question(strQuestion);
        for(int i = 0; i<arrQuestion.length; i++){
            unitAdd.addWordNIdx(arrQuestion[i], arrIdx[i]);
        }

        unitAdd.saveAnswer(strAnswer);

        unitAdd.printWordList();

        for(int i=0; i< arrQuestion.length; i++){
            assertEquals(arrQuestion[i], unitAdd.get_alWordUnit().get(i).get_Question());
            assertEquals(arrAnswer[i], unitAdd.get_alWordUnit().get(i).get_Answer());
        }

    }

    @Test
    public void testSentUnitSaveAnswer_03() {

        String strQuestion =  "내가 퇴근하는 시간은 항상 같다";
        String[] arrQuestion =  {"퇴근", "시간", "항상", "같다"};
        int[] arrIdx  =   {1, 2, 3, 4};

        String strAnswer   =  "   내가 퇴근하는 시간은 항상 같다   ";
        String[] arrAnswer =  {"퇴근하는", "시간은", "항상", "같다"};

        SentUnit unitAdd = new SentUnit();

        unitAdd.set_Question(strQuestion);
        for(int i = 0; i<arrQuestion.length; i++){
            unitAdd.addWordNIdx(arrQuestion[i], arrIdx[i]);
        }

        unitAdd.saveAnswer(strAnswer);

        unitAdd.printWordList();

        for(int i=0; i< arrQuestion.length; i++){
            assertEquals(arrQuestion[i], unitAdd.get_alWordUnit().get(i).get_Question());
            assertEquals(arrAnswer[i], unitAdd.get_alWordUnit().get(i).get_Answer());
        }

    }

    @Test
    public void testSentUnitSaveAnswer_04() {

        String strQuestion =  "내가 퇴근하는 시간은 항상 같다";
        String[] arrQuestion =  {"퇴근", "시간", "항상", "같다"};
        int[] arrIdx  =   {1, 2, 3, 4};

        String strAnswer   =  "   내가 퇴근하는    ";
        String[] arrAnswer =  {"퇴근하는", "", "", ""};

        SentUnit unitAdd = new SentUnit();

        unitAdd.set_Question(strQuestion);
        for(int i = 0; i<arrQuestion.length; i++){
            unitAdd.addWordNIdx(arrQuestion[i], arrIdx[i]);
        }

        unitAdd.saveAnswer(strAnswer);

        unitAdd.printWordList();

        for(int i=0; i< arrQuestion.length; i++){
            assertEquals(arrQuestion[i], unitAdd.get_alWordUnit().get(i).get_Question());
            assertEquals(arrAnswer[i], unitAdd.get_alWordUnit().get(i).get_Answer());
        }

    }

    @Test
    public void testSentUnitScoring_01() {

        String strQuestion =  "내가 퇴근하는 시간은 항상 같다";
        String[] arrQuestion =  {"퇴근", "시간", "항상", "같다"};
        int[] arrIdx  =   {1, 2, 3, 4};

        String strAnswer   =  "   내가 퇴근하는    ";
        String[] arrAnswer =  {"퇴근하는", "", "", ""};

        SentUnit unitAdd = new SentUnit();

        unitAdd.set_Question(strQuestion);
        for(int i = 0; i<arrQuestion.length; i++){
            unitAdd.addWordNIdx(arrQuestion[i], arrIdx[i]);
        }

        unitAdd.saveAnswer(strAnswer);
        unitAdd.scoring();

        unitAdd.printWordList();

        assertEquals(1, unitAdd.get_iWordCorrect());
        assertEquals(4, unitAdd.get_iWordQuestion());
    }


    @Test
    public void testSentUnitScoring_02() {

        String strQuestion =  "백화점에 가서 목걸이와 반지를 샀습니다.";
        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다."};
        int[] arrIdx  =   {0, 1, 2, 3, 4};

        String strAnswer   =  "   내가 퇴근하는    ";

        SentUnit unitAdd = new SentUnit();

        unitAdd.set_Question(strQuestion);
        for(int i = 0; i<arrQuestion.length; i++){
            unitAdd.addWordNIdx(arrQuestion[i], arrIdx[i]);
        }

        unitAdd.saveAnswer(strAnswer);
        unitAdd.scoring();

        unitAdd.printWordList();

        assertEquals(0, unitAdd.get_iWordCorrect());
        assertEquals(5, unitAdd.get_iWordQuestion());
    }


    @Test
    public void testSentUnitScoring_03() {

        String strQuestion =  "백화점에 가서 목걸이와 반지를 샀습니다.";
        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다."};
        int[] arrIdx  =   {0, 1, 2, 3, 4};

        String strAnswer =  "백화점에 가서 목걸이와 반지를 샀습니다.";

        SentUnit unitAdd = new SentUnit();

        unitAdd.set_Question(strQuestion);
        for(int i = 0; i<arrQuestion.length; i++){
            unitAdd.addWordNIdx(arrQuestion[i], arrIdx[i]);
        }

        unitAdd.saveAnswer(strAnswer);
        unitAdd.scoring();

        unitAdd.printWordList();

        assertEquals(5, unitAdd.get_iWordCorrect());
        assertEquals(5, unitAdd.get_iWordQuestion());
    }

    public void testAddWordNIdx() {
        String strQuestion =  "백화점에 가서 목걸이와 반지를 샀습니다.";
        String[] arrQuestion =  {"백화점", "가서", "목걸이", "반지", "샀습니다."};
        int[] arrIdx  =   {0, 1, 2, 3, 4};

        String strAnswer =  "백화점에 가서 목걸이와 반지를 샀습니다.";

        SentUnit unitAdd = new SentUnit();

        unitAdd.set_Question(strQuestion);
        for(int i = 0; i<arrQuestion.length; i++){
            unitAdd.addWordNIdx(arrQuestion[i], arrIdx[i]);
        }
        unitAdd.saveAnswer(strAnswer);
        unitAdd.scoring();

        unitAdd.printWordList();

        assertEquals(5, unitAdd.get_iWordCorrect());
        assertEquals(5, unitAdd.get_iWordQuestion());

    }
}