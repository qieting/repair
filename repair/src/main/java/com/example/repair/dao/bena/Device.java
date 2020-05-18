package com.example.repair.dao.bena;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Device implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private String lv;

    @Column
    private  String dj;

    @Column
    private  String wh;

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

    @Column
    private String type;

    @Column
    private String dept;

    @Column
    private String loc;

    @Column
    private String img;

    @Column
    private Date gmtTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getGmtTime() {
        return gmtTime;
    }

    public void setGmtTime(Date gmtTime) {
        this.gmtTime = gmtTime;
    }
}
