package com.example.hearfi_03.db.DTO;

public class AmTrack {
    int     at_id;
    String  at_file_name;
    String  at_file_ext;
    String  at_type;
    String  at_content;

    public AmTrack() {
    }

    public AmTrack(int at_id, String at_file_name, String at_file_ext, String at_type, String at_content) {
        this.at_id = at_id;
        this.at_file_name = at_file_name;
        this.at_file_ext = at_file_ext;
        this.at_type = at_type;
        this.at_content = at_content;
    }

    public int getAt_id() {
        return at_id;
    }

    public void setAt_id(int at_id) {
        this.at_id = at_id;
    }

    public String getAt_file_name() {
        return at_file_name;
    }

    public void setAt_file_name(String at_file_name) {
        this.at_file_name = at_file_name;
    }

    public String getAt_file_ext() {
        return at_file_ext;
    }

    public void setAt_file_ext(String at_file_ext) {
        this.at_file_ext = at_file_ext;
    }

    public String getAt_type() {
        return at_type;
    }

    public void setAt_type(String at_type) {
        this.at_type = at_type;
    }

    public String getAt_content() {
        return at_content;
    }

    public void setAt_content(String at_content) {
        this.at_content = at_content;
    }
}

