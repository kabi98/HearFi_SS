package com.example.hearfiss_01.audioTest.SRS;

import android.util.Log;

import com.example.hearfiss_01.audioTest.WRS.WordUnit;

import java.util.ArrayList;

public class SentUnit {
    String  _Question, _Answer;
    int     _Correct;

    int     _iWordQuestion;
    int     _iWordCorrect;

    SentScore m_SentScore = null;

    ArrayList<WordUnit> _alWordUnit;
    ArrayList<Integer> _alWordIdx;



    public SentUnit() {
        _Question = "";
        _Answer = "";
        _Correct = -1;
        _iWordQuestion = -1;
        _iWordCorrect = -1;

        _alWordUnit = new ArrayList<>();
        _alWordIdx = new ArrayList<>();

    }

    public SentUnit(String question, String answer, int correct) {
        this._Question = question;
        this._Answer = answer;
        this._Correct = correct;
        _iWordQuestion = -1;
        _iWordCorrect = -1;

        _alWordUnit = new ArrayList<>();
        _alWordIdx = new ArrayList<>();
    }



    public int get_iWordQuestion() {
        return _iWordQuestion;
    }

    public int get_iWordCorrect() {
        return _iWordCorrect;
    }

    public String get_Question() {
        return _Question;
    }

    public void set_Question(String _Question) {
        this._Question = _Question;
    }

    public String get_Answer() {
        return _Answer;
    }

    public void set_Answer(String _Answer) {
        this._Answer = _Answer;
    }

    public int get_Correct() {
        return _Correct;
    }

    public void set_Correct(int _Correct) {
        this._Correct = _Correct;
    }

    public ArrayList<WordUnit> get_alWordUnit() {
        return _alWordUnit;
    }
    public ArrayList<Integer> get_alWordIdx() {
        return _alWordIdx;
    }

    public void set_alWordUnit(ArrayList<WordUnit> _alWordUnit) {
        this._alWordUnit = _alWordUnit;
    }

    public void set_alWordIdx(ArrayList<Integer> _alWordIdx) {
        this._alWordIdx = _alWordIdx;
    }

    public void printWordList(){
        for(int i = 0; i< _alWordUnit.size(); i++){
            Log.v("SenUnit", String.format("%d: id:%d Q:%s, A:%s, C:%d",
                    i, _alWordIdx.get(i),
                    _alWordUnit.get(i).get_Question(),
                    _alWordUnit.get(i).get_Answer(),
                    _alWordUnit.get(i).get_Correct() ));
        }
    }

    public int addWordNIdx(String Word, int idx){
        _alWordUnit.add(new WordUnit(Word, "", -1));
        _alWordIdx.add(idx);
        return 1;
    }

    public int saveQnA(String strQuest, String strAnswer){
        _Question = strQuest;
        _Answer = strAnswer;
        saveAnswer(strAnswer);
        scoring();
        return 1;
    }

    public int saveAnswer(String strInput){
        String strTemp = strInput.trim();

        int iLen = -1;
        while(iLen != strTemp.length()){
            iLen = strTemp.length();
            strTemp = strTemp.replace("  ", " ");
        }

        String[] arrStr = strTemp.split(" ");

        int idxWord = -1;
        for(int i = 0; i< _alWordIdx.size(); i++)
        {
            idxWord = _alWordIdx.get(i);
            if(0 <= idxWord && idxWord < arrStr.length){
                _alWordUnit.get(i).set_Answer(arrStr[idxWord]);
            }
        }

        return 1;
    }

    public int scoring(){
        _Correct = -1;
        _iWordQuestion = _alWordUnit.size();
        _iWordCorrect = 0;

        for(WordUnit wuIter : _alWordUnit){
            if( wuIter.get_Answer().contains(wuIter.get_Question()) ) {
                wuIter.set_Correct(1);
                _iWordCorrect++;
            }
            else {
                wuIter.set_Correct(0);
            }
        }

        if(_iWordQuestion == _iWordCorrect){
            _Correct = 1;
        } else {
            _Correct = 0;
        }

        return 1;
    }



    @Override
    public String toString() {
        return "SentUnit{" +
                "_Question='" + _Question + '\'' +
                ", _Answer='" + _Answer + '\'' +
                ", _Correct=" + _Correct +
                ", m_alWordUnit=" + _alWordUnit +
                '}';
    }
}
