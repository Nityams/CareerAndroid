package com.nityam.career.Model;

import java.io.Serializable;

/**
 * Created by nityamshrestha on 12/7/17.
 */

public class JobPost implements Serializable {

    String id = null;
    String company = null;
    String position = null;
    String date= null;
    String refName= null;
    String refEmail= null;
    String recName= null;
    String recEmail= null;
    String status= null;
    String city= null;

    public JobPost(){

    }

    public JobPost(String id, String company, String position,  String city, String date, String refName, String refEmail, String recName, String recEmail, String status) {
        this.id = id;
        this.company = company;
        this.position = position;
        this.date = date;
        this.refName = refName;
        this.refEmail = refEmail;
        this.recName = recName;
        this.recEmail = recEmail;
        this.status = status;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
