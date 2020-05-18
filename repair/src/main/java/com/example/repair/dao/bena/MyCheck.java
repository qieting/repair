package com.example.repair.dao.bena;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MyCheck implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column
    private  String dj;

    @Column
    private  String wh;

    @Column
    private  String comment;

    @Column
    private  String img;

    @Column
    private  String user;

    @Column
    private Date time;

    @Column
    private  int device_id;

    @Column
    private  String state;

    @Column
    private  String verify;

    @Column
    private  Date verify_date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getWh() {
        return wh;
    }

    public void setWh(String wh) {
        this.wh = wh;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public Date getVerify_date() {
        return verify_date;
    }

    public void setVerify_date(Date verify_date) {
        this.verify_date = verify_date;
    }
}
