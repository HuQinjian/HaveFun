package com.example.havefun;

/**
 * @Project: HaveFun
 * @Author: hqj
 * @Date: 2023/3/13 15:31
 * @Introduce:
 **/
public class boreBean {
    private String activity;
    private String type;
    private int participants;
    private float price;
    private float minprice;
    private float maxprice;
    private String link;
    private String key;
    private float accessibility;
    private float minaccessibility;
    private float maxaccessibility;

    public boreBean(String activity, String type, int participants, float price, float minprice, float maxprice, String link, String key, float accessibility, float minaccessibility, float maxaccessibility) {
        this.activity = activity;
        this.type = type;
        this.participants = participants;
        this.price = price;
        this.minprice = minprice;
        this.maxprice = maxprice;
        this.link = link;
        this.key = key;
        this.accessibility = accessibility;
        this.minaccessibility = minaccessibility;
        this.maxaccessibility = maxaccessibility;
    }

    @Override
    public String toString() {
        return "boreBean{" +
                "activity='" + activity + '\'' +
                ", type='" + type + '\'' +
                ", participants=" + participants +
                ", price=" + price +
                ", minprice=" + minprice +
                ", maxprice=" + maxprice +
                ", link='" + link + '\'' +
                ", key='" + key + '\'' +
                ", accessibility=" + accessibility +
                ", minaccessibility=" + minaccessibility +
                ", maxaccessibility=" + maxaccessibility +
                '}';
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMinprice() {
        return minprice;
    }

    public void setMinprice(float minprice) {
        this.minprice = minprice;
    }

    public float getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(float maxprice) {
        this.maxprice = maxprice;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public float getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(float accessibility) {
        this.accessibility = accessibility;
    }

    public float getMinaccessibility() {
        return minaccessibility;
    }

    public void setMinaccessibility(float minaccessibility) {
        this.minaccessibility = minaccessibility;
    }

    public float getMaxaccessibility() {
        return maxaccessibility;
    }

    public void setMaxaccessibility(float maxaccessibility) {
        this.maxaccessibility = maxaccessibility;
    }
}
