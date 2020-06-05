package com.codebee.contactsapp;

import java.io.Serializable;

public class Contact implements Serializable {
    private String Id;
    private String Fname;
    private String LName;
    private long Mobile;
    private long Work;
    private String Email;
    private long Custom1;
    private long Custom2;
    private long Custom3;

    public Contact() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public long getMobile() {
        return Mobile;
    }

    public void setMobile(long mobile) {
        Mobile = mobile;
    }

    public long getWork() {
        return Work;
    }

    public void setWork(long work) {
        Work = work;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public long getCustom1() {
        return Custom1;
    }

    public void setCustom1(long custom1) {
        Custom1 = custom1;
    }

    public long getCustom2() {
        return Custom2;
    }

    public void setCustom2(long custom2) {
        Custom2 = custom2;
    }

    public long getCustom3() {
        return Custom3;
    }

    public void setCustom3(long custom3) {
        Custom3 = custom3;
    }
}
