package com.example.hearfiss_01.db.DTO;

public class PttTestDevice {
    int     td_id; // PRIMARY KEY
    int     tg_id;
    String  td_phone;
    String  td_device;

    public PttTestDevice() {
    }

    public PttTestDevice(int td_id, int tg_id, String td_phone, String td_device) {
        this.td_id = td_id;
        this.tg_id = tg_id;
        this.td_phone = td_phone;
        this.td_device = td_device;
    }

    @Override
    public String toString() {
        return "PttTestDevice{" +
                "td_id=" + td_id +
                ", tg_id=" + tg_id +
                ", td_phone='" + td_phone + '\'' +
                ", td_device='" + td_device + '\'' +
                '}';
    }

    public int getTdId() {
        return td_id;
    }

    public void setTdId(int td_id) {
        this.td_id = td_id;
    }

    public int getTgId() {
        return tg_id;
    }

    public void setTgId(int tg_id) {
        this.tg_id = tg_id;
    }

    public String getTdPhone() {
        return td_phone;
    }

    public String getTdDevice() {
        return td_device;
    }

    public void setTdPhone(String td_phone) {
        this.td_phone = td_phone;
    }

    public void setTdDevice(String td_device) {
        this.td_device = td_device;
    }
}
