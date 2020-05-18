package com.example.repair.dao.bena;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Entity
public class Message implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column
    private  String content;
    @Column
    private  String title;


    @Column
    private Date gmtTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getGmtTime() {
        return gmtTime;
    }

    public void setGmtTime(Date gmtTime) {
        this.gmtTime = gmtTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
