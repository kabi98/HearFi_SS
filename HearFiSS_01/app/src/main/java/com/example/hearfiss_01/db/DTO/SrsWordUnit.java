package com.example.hearfiss_01.db.DTO;

public class SrsWordUnit {

/*
    CREATE TABLE srs_word_unit (
            su_id 			INTEGER PRIMARY KEY AUTOINCREMENT
            , tu_id			INTEGER
            , su_question 	TEXT
            , su_answer		TEXT
            , su_iscorrect	INTEGER
            , su_idx		INTEGER

            , FOREIGN KEY(tu_id) REFERENCES hrtest_unit(tu_id)
            );

*/
    String m_TAG = "SrsWordUnit";

    int     su_id; // PRIMARY KEY
    int     tu_id; // 외래키 (세트 번호)
    String  su_question;
    String  su_answer;
    int     su_iscorrect;
    int     su_idx;

    public SrsWordUnit() {
    }

    public SrsWordUnit(int su_id, int tu_id, String su_question, String su_answer, int su_iscorrect, int su_idx) {
        this.su_id = su_id;
        this.tu_id = tu_id;
        this.su_question = su_question;
        this.su_answer = su_answer;
        this.su_iscorrect = su_iscorrect;
        this.su_idx = su_idx;
    }

    public int getSu_id() {
        return su_id;
    }

    public void setSu_id(int su_id) {
        this.su_id = su_id;
    }

    public int getTu_id() {
        return tu_id;
    }

    public void setTu_id(int tu_id) {
        this.tu_id = tu_id;
    }

    public String getSu_question() {
        return su_question;
    }

    public void setSu_question(String su_question) {
        this.su_question = su_question;
    }

    public String getSu_answer() {
        return su_answer;
    }

    public void setSu_answer(String su_answer) {
        this.su_answer = su_answer;
    }

    public int getSu_iscorrect() {
        return su_iscorrect;
    }

    public void setSu_iscorrect(int su_iscorrect) {
        this.su_iscorrect = su_iscorrect;
    }

    public int getSu_idx() {
        return su_idx;
    }

    public void setSu_idx(int su_idx) {
        this.su_idx = su_idx;
    }

    @Override
    public String toString() {
        return "SrsWordUnit{" +
                "su_id=" + su_id +
                ", tu_id=" + tu_id +
                ", su_question='" + su_question + '\'' +
                ", su_answer='" + su_answer + '\'' +
                ", su_iscorrect=" + su_iscorrect +
                ", su_idx=" + su_idx +
                '}';
    }
}
