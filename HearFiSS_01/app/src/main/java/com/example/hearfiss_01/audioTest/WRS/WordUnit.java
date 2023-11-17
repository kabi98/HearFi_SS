package com.example.hearfiss_01.audioTest.WRS;

import android.util.Log;

public class WordUnit {
    String m_TAG = "WordUnit";
    String  _Question;
    String  _Answer;
    int     _Correct;

    public WordUnit() {
    }

    public WordUnit(String _Question, String _Answer, int _Correct) {
        this._Question = _Question;
        this._Answer = _Answer;
        this._Correct = _Correct;
    }

    @Override
    public String toString() {
        return "WordUnit{" +
                "_Question='" + _Question + '\'' +
                ", _Answer='" + _Answer + '\'' +
                ", _Correct=" + _Correct +
                '}';
    }

    public void print() {
        Log.v(m_TAG,
                String.format("Q: %s, A: %s, C: %d",
                        _Question, _Answer, _Correct) );
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
        _Answer.trim();
        this._Answer = _Answer;
    }

    public int get_Correct() {
        return _Correct;
    }

    public void set_Correct(int _Correct) {
        this._Correct = _Correct;
    }


}
