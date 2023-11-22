package com.example.hearfiss_01.db.DTO;

public class HrTestGroup {
    int     tg_id;
    String  tg_Date;
    String  tg_type;
    String  tg_result;
    int     acc_id;

    public HrTestGroup() {
    }

    public HrTestGroup(int tg_id, String tg_Date, String tg_type, String tg_result, int acc_id) {
        this.tg_id = tg_id;
        this.tg_Date = tg_Date;
        this.tg_type = tg_type;
        this.tg_result = tg_result;
        this.acc_id = acc_id;
    }

    @Override
    public String toString() {
        return "HrTestGroup{" +
                "tg_id=" + tg_id +
                ", tg_Date='" + tg_Date + '\'' +
                ", tg_type='" + tg_type + '\'' +
                ", tg_result='" + tg_result + '\'' +
                ", acc_id=" + acc_id +
                '}';
    }

    public int getTg_id() {
        return tg_id;
    }

    public void setTg_id(int tg_id) {
        this.tg_id = tg_id;
    }

    public String getTg_Date() {
        return tg_Date;
    }

    public void setTg_Date(String tg_Date) {
        this.tg_Date = tg_Date;
    }

    public String getTg_type() {
        return tg_type;
    }

    public void setTg_type(String tg_type) {
        this.tg_type = tg_type;
    }

    public int getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(int acc_id) {
        this.acc_id = acc_id;
    }

    public String getTg_result() {
        return tg_result;
    }

    public void setTg_result(String tg_result) {
        this.tg_result = tg_result;
    }
}

/*
    CREATE TABLE IF NOT EXISTS hrtest_group (
        tg_id 			INTEGER PRIMARY KEY AUTOINCREMENT
        , tg_date 		TEXT
        , tg_type		TEXT
        , acc_id		INTEGER
        , FOREIGN KEY(acc_id) REFERENCES account(acc_id) );
 */
