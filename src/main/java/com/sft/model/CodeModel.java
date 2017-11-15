package com.sft.model;

import com.sft.util.DateUtil;

public class CodeModel {

    private String phone;
    private String code;
    private String create_date;
    private int duration = 5;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = DateUtil.getDeletePointDate(create_date);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
