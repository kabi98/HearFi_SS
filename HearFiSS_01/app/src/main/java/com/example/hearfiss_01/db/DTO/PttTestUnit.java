package com.example.hearfiss_01.db.DTO;

public class PttTestUnit {

    int     tu_id; // PRIMARY KEY
    int     ts_id; // 외래키 (세트 번호)
    int     tu_hz;
    int     tu_dBHL;

    public PttTestUnit() {

    }

    public PttTestUnit(int tu_id, int ts_id, int tu_hz, int tu_dBHL) {
        this.tu_id = tu_id;
        this.ts_id = ts_id;
        this.tu_hz = tu_hz;
        this.tu_dBHL = tu_dBHL;
    }

    @Override
    public String toString() {
        return "PttTestUnit{" +
                "tu_id=" + tu_id +
                ", ts_id=" + ts_id +
                ", tu_hz=" + tu_hz +
                ", tu_dBHL=" + tu_dBHL +
                '}';
    }

    public int getTu_id() {
        return tu_id;
    }

    public void setTu_id(int tu_id) {
        this.tu_id = tu_id;
    }

    public int getTs_id() {
        return ts_id;
    }

    public void setTs_id(int ts_id) {
        this.ts_id = ts_id;
    }

    public int getTu_hz() {
        return tu_hz;
    }

    public void setTu_hz(int tu_hz) {
        this.tu_hz = tu_hz;
    }

    public int getTu_dBHL() {
        return tu_dBHL;
    }

    public void setTu_dBHL(int tu_dBHL) {
        this.tu_dBHL = tu_dBHL;
    }
}




