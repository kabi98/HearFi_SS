package com.example.hearfiss_01.db.DTO;

public class PureToneTrack {
    int         pt_id;
    String      pt_file_name;
    String      pt_file_ext;
    int         frequency;
    int         dBHL;
    String      phone;
    String      device;

    public PureToneTrack() {
    }

    public PureToneTrack(int pt_id, String pt_file_name, String pt_file_ext,
                         int frequency, int dBHL, String phone, String device) {
        this.pt_id = pt_id;
        this.pt_file_name = pt_file_name;
        this.pt_file_ext = pt_file_ext;
        this.frequency = frequency;
        this.dBHL = dBHL;
        this.phone = phone;
        this.device = device;
    }

    public int getPt_id() {
        return pt_id;
    }

    public void setPt_id(int pt_id) {
        this.pt_id = pt_id;
    }

    public String getPt_file_name() {
        return pt_file_name;
    }

    public void setPt_file_name(String pt_file_name) {
        this.pt_file_name = pt_file_name;
    }

    public String getPt_file_ext() {
        return pt_file_ext;
    }

    public void setPt_file_ext(String pt_file_ext) {
        this.pt_file_ext = pt_file_ext;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getdBHL() {
        return dBHL;
    }

    public void setdBHL(int dBHL) {
        this.dBHL = dBHL;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "PureToneTrack{" +
                "pt_id=" + pt_id +
                ", pt_file_name='" + pt_file_name + '\'' +
                ", pt_file_ext='" + pt_file_ext + '\'' +
                ", frequency=" + frequency +
                ", dBHL=" + dBHL +
                ", phone='" + phone + '\'' +
                ", device='" + device + '\'' +
                '}';
    }
}
