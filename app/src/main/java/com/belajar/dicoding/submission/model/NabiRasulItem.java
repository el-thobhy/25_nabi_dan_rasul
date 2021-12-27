package com.belajar.dicoding.submission.model;

public class NabiRasulItem {
    private String name;
    private String detail;
    private int photo;
    private String umur;
    private String umat;
    private String key_id;
    private String favStatus;

    public NabiRasulItem() {
    }

    public NabiRasulItem(String name, String detail, int photo, String umur, String umat, String key_id, String favStatus) {
        this.name = name;
        this.detail = detail;
        this.photo = photo;
        this.umur = umur;
        this.umat = umat;
        this.key_id = key_id;
        this.favStatus = favStatus;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }

    public String getUmat() {
        return umat;
    }

    public void setUmat(String umat) {
        this.umat = umat;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
