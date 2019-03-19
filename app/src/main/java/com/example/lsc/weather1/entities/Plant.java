package com.example.lsc.weather1.entities;

public class Plant {
    private int id;
    private String name;
    private String subject;
    private String imageUrl;
    private String intro;
    private String[] advice=new String[4];
    private double[] k=new double[6];

    public Plant() {
    }

    public Plant(int id, String name, String subject, String imageUrl, String intro, String[] advice, double[] k) {
        this.id = id;
        this.name = name;
        this.subject=subject;
        this.imageUrl = imageUrl;
        this.intro = intro;
        this.advice = advice;
        this.k = k;
    }

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String[] getAdvice() {
        return advice;
    }

    public void setAdvice(String[] advice) {
        this.advice = advice;
    }

    public double[] getK() {
        return k;
    }

    public void setK(double[] k) {
        this.k = k;
    }
}
