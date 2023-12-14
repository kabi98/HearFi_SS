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

        return 1;
    }

    public void setM_alSentUnit(ArrayList<SentUnit> _alSentence) {
        this._alSentence = _alSentence;

    }
}
