package com.example.hearfiss_01.db.DTO;

public class Account {
    int     acc_id;
    String  acc_phone_id;
    String  acc_phone;
    String  acc_pwd;
    String  acc_name;
    String  acc_gender;
    String  acc_birth;


    public Account() {
    }

    public Account(int acc_id, String acc_phone_id, String acc_phone, String acc_pwd, String acc_name, String acc_gender, String acc_birth) {
        this.acc_id = acc_id;
        this.acc_phone_id = acc_phone_id;
        this.acc_phone = acc_phone;
        this.acc_pwd = acc_pwd;
        this.acc_name = acc_name;
        this.acc_gender = acc_gender;
        this.acc_birth = acc_birth;
    }

    @Override
    public String toString() {
        return "Account{" +
                "acc_id=" + acc_id +
                ", acc_phone_id='" + acc_phone_id + '\'' +
                ", acc_phone='" + acc_phone + '\'' +
                ", acc_pwd='" + acc_pwd + '\'' +
                ", acc_name='" + acc_name + '\'' +
                ", acc_gender='" + acc_gender + '\'' +
                ", acc_birth='" + acc_birth + '\'' +
                '}';
    }

    public int getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(int acc_id) {
        this.acc_id = acc_id;
    }

    public String getAcc_phone_id() {
        return acc_phone_id;
    }

    public void setAcc_phone_id(String acc_phone_id) {
        this.acc_phone_id = acc_phone_id;
    }

    public String getAcc_phone() {
        return acc_phone;
    }

    public void setAcc_phone(String acc_phone) {
        this.acc_phone = acc_phone;
    }

    public String getAcc_pwd() {
        return acc_pwd;
    }

    public void setAcc_pwd(String acc_pwd) {
        this.acc_pwd = acc_pwd;
    }

    public String getAcc_name() {
        return acc_name;
    }

    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }

    public String getAcc_gender() {
        return acc_gender;
    }

    public void setAcc_gender(String acc_gender) {
        this.acc_gender = acc_gender;
    }

    public String getAcc_birth() {
        return acc_birth;
    }

    public void setAcc_birth(String acc_birth) {
        this.acc_birth = acc_birth;
    }
}
