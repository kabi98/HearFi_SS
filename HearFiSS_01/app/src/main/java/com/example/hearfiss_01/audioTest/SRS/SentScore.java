package com.example.hearfiss_01.audioTest.SRS;

import android.util.Log;

import java.util.ArrayList;

public class SentScore {
    String m_TAG = "SentScore";
    ArrayList <SentUnit> _alSentence = null;

    int _iSentQuest, _iSentCorrect, _iSentScore;
    int _iWordQuest, _iWordCorrect, _iWordScore;


    public SentScore() {
        _iSentQuest     = -1;
        _iSentCorrect   = -1;
        _iSentScore     = -1;
        _iWordQuest     = -1;
        _iWordCorrect   = -1;
        _iWordScore     = -1;

        _alSentence = new ArrayList<>();
    }

    public ArrayList<SentUnit> get_alSentence(){
        return _alSentence;
    }
    public int get_iSentQuest() {
        return _iSentQuest;
    }

    public int get_iSentCorrect() {
        return _iSentCorrect;
    }

    public int get_iSentScore() {
        return _iSentScore;
    }

    public int get_iWordQuest() {
        return _iWordQuest;
    }

    public int get_iWordCorrect() {
        return _iWordCorrect;
    }

    public int get_iWordScore() {
        return _iWordScore;
    }

    public int addSentUnit(SentUnit unitAdd){
        _alSentence.add(unitAdd);
        return 1;
    }

    public void printSentence(){
        int iOrder = 0;
        for(SentUnit unitPrt : _alSentence) {
            Log.v(m_TAG, String.format("printSentence %d : %s ", iOrder++, unitPrt.get_Question()));
            unitPrt.printWordList();
        }
    }

    public int scoring(){
        _iSentQuest     = 0;
        _iSentCorrect   = 0;
        _iSentScore     = 0;

        _iSentQuest = _alSentence.size();
        for(SentUnit sentence :_alSentence ){
            if(sentence.get_Correct() > 0){
                _iSentCorrect++;
            }
        }
        _iSentScore = (int)(((float)_iSentCorrect/(float)_iSentQuest) * 100);

        _iWordQuest     = 0;
        _iWordCorrect   = 0;
        _iWordScore     = 0;

        for(SentUnit sentence :_alSentence ){
            _iWordQuest += sentence.get_iWordQuestion();
            _iWordCorrect += sentence.get_iWordCorrect();
        }
        _iWordScore = (int)(((float)_iWordCorrect/(float)_iWordQuest) * 100);
        Log.v(m_TAG,String.format("문장 : 질문 - %d, 정답 - %d, 점수 - %d  / 단어 : 질문 - %d, 정답 - %d, 점수 - %d", _iSentQuest,_iSentCorrect,_iSentScore,_iWordQuest,_iWordCorrect,_iWordScore));

        return 1;
    }

    public void setAlSentence(ArrayList<SentUnit> _alSentence) {
        this._alSentence = _alSentence;
        for(int i=0; i<_alSentence.size(); i++){
            Log.v("Test log" , i + "value : " + _alSentence.get(i).toString());
        }

    }

    /*
    public String getCorrectStringList() {
        StringBuilder correctStringBuilder = new StringBuilder();
        for (SentUnit unit : _alSentence) {
            if (unit.get_Correct() > 0) {
                correctStringBuilder.append(unit.get_Question()).append(" ");
            }
        }
        return correctStringBuilder.toString().trim();
    }
    */

    public String getCorrectStringList() {
        StringBuilder correctStringBuilder = new StringBuilder();
        for (SentUnit unit : _alSentence) {
            if (unit.get_Correct() > 0) {
                correctStringBuilder.append(unit.get_Question()).append("\n"); // 줄바꿈 추가
            }
        }
        return correctStringBuilder.toString().trim();
    }


    public String getWrongStringList() {
        StringBuilder wrongStringBuilder = new StringBuilder();
        for (SentUnit unit : _alSentence) {
            if (unit.get_Correct() <= 0) {
                wrongStringBuilder.append(unit.get_Question()).append("\n");
            }
        }
        return wrongStringBuilder.toString().trim();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SentScore{");
        sb.append("_iSentQuest=").append(_iSentQuest);
        sb.append(", _iSentCorrect=").append(_iSentCorrect);
        sb.append(", _iSentScore=").append(_iSentScore);
        sb.append(", _iWordQuest=").append(_iWordQuest);
        sb.append(", _iWordCorrect=").append(_iWordCorrect);
        sb.append(", _iWordScore=").append(_iWordScore);
        sb.append(", Sentences=").append(_alSentence);
        sb.append('}');
        return sb.toString();
    }


}
