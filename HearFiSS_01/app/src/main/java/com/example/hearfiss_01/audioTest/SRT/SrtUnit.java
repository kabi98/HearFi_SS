package com.example.hearfiss_01.audioTest.SRT;

public class SrtUnit {
    String  _Question;
    String  _Answer;
    int     _Correct;
    int     _CurDb;
    int     _NextDb;

    public SrtUnit() {
    }

    public SrtUnit(String _Question, String _Answer, int _Correct, int _CurDb, int _NextDb) {
        this._Question = _Question;
        this._Answer = _Answer;
        this._Correct = _Correct;
        this._CurDb = _CurDb;
        this._NextDb = _NextDb;
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

    public int get_CurDb() {
        return _CurDb;
    }

    public void set_CurDb(int _CurDb) {
        this._CurDb = _CurDb;
    }

    public int get_NextDb() {
        return _NextDb;
    }

    public void set_NextDb(int _NextDb) {
        this._NextDb = _NextDb;
    }

    @Override
    public String toString() {
        return "SrtUnit{" +
                "_Question='" + _Question + '\'' +
                ", _Answer='" + _Answer + '\'' +
                ", _Correct=" + _Correct +
                ", _CurDb=" + _CurDb +
                ", _NextDb=" + _NextDb +
                '}';
    }
}