package com.nityam.career.Model;

import java.util.Date;

/**
 * Created by nityamshrestha on 12/7/17.
 */

public class JobPost {

    String company = null;
    String position = null;
    Date date= null;
    String refName= null;
    String refEmail= null;
    String recName= null;
    String recEmail= null;
    String status= null;
    String city= null;

    public JobPost(String company, String position, Date date, String status, String city) {
        this.company = company;
        this.position = position;
        this.date = date;
        this.status = status;
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefEmail() {
        return refEmail;
    }

    public void setRefEmail(String refEmail) {
        this.refEmail = refEmail;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getRecEmail() {
        return recEmail;
    }

    public void setRecEmail(String recEmail) {
        this.recEmail = recEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
