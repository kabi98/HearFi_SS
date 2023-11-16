package com.example.hearfi_03.db.DTO;

public class ResultDTO {
    int tg_id;
    String tg_Date;
    String tg_type;
    int acc_id;
    String Result;
    String left_Result;
    String right_Result;
    int left_id;
    int right_id;

    public ResultDTO() {

    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "tg_id=" + tg_id +
                ", tg_Date='" + tg_Date + '\'' +
                ", tg_type='" + tg_type + '\'' +
                ", acc_id=" + acc_id +
                ", Result='" + Result + '\'' +
                ", left_Result='" + left_Result + '\'' +
                ", right_Result='" + right_Result + '\'' +
                ", left_id=" + left_id +
                ", right_id=" + right_id +
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

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getLeft_Result() {
        return left_Result;
    }

    public void setLeft_Result(String left_Result) {
        this.left_Result = left_Result;
    }

    public String getRight_Result() {
        return right_Result;
    }

    public void setRight_Result(String right_Result) {
        this.right_Result = right_Result;
    }

    public int getLeft_id() {
        return left_id;
    }

    public void setLeft_id(int left_id) {
        this.left_id = left_id;
    }

    public int getRight_id() {
        return right_id;
    }

    public void setRight_id(int right_id) {
        this.right_id = right_id;
    }

    public ResultDTO(int tg_id, String tg_Date, String tg_type, int acc_id, String result, String left_Result, String right_Result, int left_id, int right_id) {
        this.tg_id = tg_id;
        this.tg_Date = tg_Date;
        this.tg_type = tg_type;
        this.acc_id = acc_id;
        Result = result;
        this.left_Result = left_Result;
        this.right_Result = right_Result;
        this.left_id = left_id;
        this.right_id = right_id;
    }
}



