package com.example.hearfiss_01.audioTest.WRS;

import android.util.Log;

import com.example.hearfiss_01.global.TConst;

import java.util.ArrayList;

public class WordScore {
    ArrayList<WordUnit> m_alWord = null;
    int m_iQuestion, m_iCorrect, m_iScore;

    public WordScore() {
        m_iQuestion = -1;
        m_alWord = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "WordScore{" +
                "m_alWord=" + m_alWord +
                ", m_iQuestion=" + m_iQuestion +
                ", m_iCorrect=" + m_iCorrect +
                ", m_iScore=" + m_iScore +
                '}';
    }

    public ArrayList<WordUnit> getM_alWord() {
        return m_alWord;
    }

    public void setM_alWord(ArrayList<WordUnit> m_alWord) {
        this.m_alWord = m_alWord;
    }

    public int getM_iQuestion() {
        return m_iQuestion;
    }

    public void setM_iQuestion(int m_iQuestion) {
        this.m_iQuestion = m_iQuestion;
    }

    public int getM_iCorrect() {
        return m_iCorrect;
    }

    public void setM_iCorrect(int m_iCorrect) {
        this.m_iCorrect = m_iCorrect;
    }

    public int getM_iScore() {
        return m_iScore;
    }

    public void setM_iScore(int m_iScore) {
        this.m_iScore = m_iScore;
    }

    public int addWordUnit(WordUnit wuAdd){
        if(wuAdd == null)
            return 0;

        if(wuAdd.get_Question().equals(wuAdd.get_Answer())) {
            wuAdd.set_Correct(1);
        } else {
            wuAdd.set_Correct(0);
        }

        m_alWord.add(wuAdd);
        return 1;
    }

    public int printWordList(){
        Log.v("WordScore", "printWordList" );

        if( m_alWord.size() <= 0 )
            return 0;

        for(WordUnit unitOne : m_alWord) {
            Log.v("WordScore printWordList", unitOne.toString() );
        }
        return 1;
    }

    public int scoringWordList(){
        Log.v("WordScore", "scoringWordList" );

        if( m_alWord.size() <= 0 )
            return 0;

        countCorrectAnswer();

        m_iScore = (int)(((float)m_iCorrect/(float)m_iQuestion) * 100);

        return 1;
    }

    private void countCorrectAnswer(){
        m_iQuestion = m_alWord.size();
        m_iCorrect = 0;
        m_iScore = 0;

        for(int i=0; i<m_alWord.size(); i++) {
            if(m_alWord.get(i).get_Correct() == 1){
                m_iCorrect ++;
            }
        }
    }

    public String getGradeFromScore() {
        for(int i = 0; i < TConst.WRS_SCORE_BOUNDARY.length; i++ ){
            if (m_iScore >= TConst.WRS_SCORE_BOUNDARY[i]){
                return TConst.WRS_SCORE_STR[i];
            }
        }
        return TConst.WRS_SCORE_STR[TConst.HEARING_LOSS_PTA.length-1];
    }

    public String getGradeFromScore(int iScore) {
        for(int i = 0; i < TConst.WRS_SCORE_BOUNDARY.length; i++ ){
            if (iScore >= TConst.WRS_SCORE_BOUNDARY[i]){
                return TConst.WRS_SCORE_STR[i];
            }
        }
        return TConst.WRS_SCORE_STR[TConst.HEARING_LOSS_PTA.length-1];
    }

}
