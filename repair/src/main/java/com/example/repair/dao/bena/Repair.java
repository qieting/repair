package com.example.repair.dao.bena;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Repair implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column
    private int device_Id;

    @Column
    private String state;

    @Column
    private String downTime;

    @Column
    private String part;

    @Column
    private String comment;

    @Column
    private String img;

    @Column
    private String user;

    @Column
    private Date bgTime;

    @Column
    private String spa;

    @Column
    private String remark;

    @Column
    private String rpImg;

    @Column
    private String rpTime;

    @Column
    private String fuser;

    @Column
    private  String verify;

    @Column
    private  Date verify_date;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDevice_Id() {
        return device_Id;
    }

    public void setDevice_Id(int device_Id) {
        this.device_Id = device_Id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDownTime() {
        return downTime;
    }

    public void setDownTime(String downTime) {
        this.downTime = downTime;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
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

    public Date getBgTime() {
        return bgTime;
    }

    public void setBgTime(Date bgTime) {
        this.bgTime = bgTime;
    }

    public String getSpa() {
        return spa;
    }

    public void setSpa(String spa) {
        this.spa = spa;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRpImg() {
        return rpImg;
    }

    public void setRpImg(String rpImg) {
        this.rpImg = rpImg;
    }

    public String getRpTime() {
        return rpTime;
    }

    public void setRpTime(String rpTime) {
        this.rpTime = rpTime;
    }

    public String getFuser() {
        return fuser;
    }

    public void setFuser(String fuser) {
        this.fuser = fuser;
    }
}

