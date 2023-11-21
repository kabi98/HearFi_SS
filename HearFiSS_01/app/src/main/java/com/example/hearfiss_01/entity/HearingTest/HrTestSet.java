package com.example.hearfiss_01.entity.HearingTest;

public class HrTestSet {

    /*
        CREATE TABLE IF NOT EXISTS hrtest_set (
                ts_id 			INTEGER PRIMARY KEY AUTOINCREMENT
                , tg_id 		INTEGER
                , ts_side		TEXT
                , ts_result		TEXT
                , ts_comment	TEXT
                , FOREIGN KEY(tg_id) REFERENCES hrtest_group(tg_id) );

    */
    int     ts_id;
    int     tg_id;
    String  ts_side;
    String  ts_Result;
    String  ts_Comment;

    public HrTestSet() {
    }

    public HrTestSet(int ts_id, int tg_id, String ts_side, String ts_Result, String ts_Comment) {
        this.ts_id = ts_id;
        this.tg_id = tg_id;
        this.ts_side = ts_side;
        this.ts_Result = ts_Result;
        this.ts_Comment = ts_Comment;
    }

    @Override
    public String toString() {
        return "HrTestSet{" +
                "ts_id=" + ts_id +
                ", tg_id=" + tg_id +
                ", ts_side='" + ts_side + '\'' +
                ", ts_Result='" + ts_Result + '\'' +
                ", ts_Comment='" + ts_Comment + '\'' +
                '}';
    }

    public int getTs_id() {
        return ts_id;
    }

    public void setTs_id(int ts_id) {
        this.ts_id = ts_id;
    }

    public int getTg_id() {
        return tg_id;
    }

    public void setTg_id(int tg_id) {
        this.tg_id = tg_id;
    }

    public String getTs_side() {
        return ts_side;
    }

    public void setTs_side(String ts_side) {
        this.ts_side = ts_side;
    }

    public String getTs_Result() {
        return ts_Result;
    }

    public void setTs_Result(String ts_Result) {
        this.ts_Result = ts_Result;
    }

    public String getTs_Comment() {
        return ts_Comment;
    }

    public void setTs_Comment(String ts_Comment) {
        this.ts_Comment = ts_Comment;
    }
}
