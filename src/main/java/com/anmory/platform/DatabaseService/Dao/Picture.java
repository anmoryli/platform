package com.anmory.platform.DatabaseService.Dao;

import java.util.Date;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-23 下午3:22
 */

public class Picture {
    private int id;
    private int herbId;
    private String picName;
    private String picCate;
    private String picPart;
    private String fileName;
    private String description;
    private String picPlace;
    private Date picTime;
    private String picPerson;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", herbId=" + herbId +
                ", picName='" + picName + '\'' +
                ", picCate='" + picCate + '\'' +
                ", picPart='" + picPart + '\'' +
                ", fileName='" + fileName + '\'' +
                ", description='" + description + '\'' +
                ", picPlace='" + picPlace + '\'' +
                ", picTime=" + picTime +
                ", picPerson='" + picPerson + '\'' +
                '}';
    }

    public int getHerbId() {
        return herbId;
    }

    public void setHerbId(int herbId) {
        this.herbId = herbId;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicCate() {
        return picCate;
    }

    public void setPicCate(String picCate) {
        this.picCate = picCate;
    }

    public String getPicPart() {
        return picPart;
    }

    public void setPicPart(String picPart) {
        this.picPart = picPart;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicPlace() {
        return picPlace;
    }

    public void setPicPlace(String picPlace) {
        this.picPlace = picPlace;
    }

    public Date getPicTime() {
        return picTime;
    }

    public void setPicTime(Date picTime) {
        this.picTime = picTime;
    }

    public String getPicPerson() {
        return picPerson;
    }

    public void setPicPerson(String picPerson) {
        this.picPerson = picPerson;
    }

    public Picture(int id, int herbId, String picName, String picCate, String picPart, String fileName, String description, String picPlace, Date picTime, String picPerson) {
        this.id = id;
        this.herbId = herbId;
        this.picName = picName;
        this.picCate = picCate;
        this.picPart = picPart;
        this.fileName = fileName;
        this.description = description;
        this.picPlace = picPlace;
        this.picTime = picTime;
        this.picPerson = picPerson;
    }
}
