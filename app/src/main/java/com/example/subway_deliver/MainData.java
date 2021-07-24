package com.example.subway_deliver;

public class MainData {


    private String tv_delivery_no;
    private String tv_delivery_category;
    private String tv_delivery_date;
    private String tv_delivery_pickup;
    private String tv_delivery_destination;

    public MainData(String tv_delivery_no, String tv_delivery_category, String tv_delivery_date, String tv_delivery_pickup, String tv_delivery_destination) {
        this.tv_delivery_no = tv_delivery_no;
        this.tv_delivery_category = tv_delivery_category;
        this.tv_delivery_date = tv_delivery_date;
        this.tv_delivery_pickup = tv_delivery_pickup;
        this.tv_delivery_destination = tv_delivery_destination;
    }

    public String getTv_delivery_no() {
        return tv_delivery_no;
    }

    public void setTv_delivery_no(String tv_delivery_no) {
        this.tv_delivery_no = tv_delivery_no;
    }

    public String getTv_delivery_category() {
        return tv_delivery_category;
    }

    public void setTv_delivery_category(String tv_delivery_category) {
        this.tv_delivery_category = tv_delivery_category;
    }

    public String getTv_delivery_date() {
        return tv_delivery_date;
    }

    public void setTv_delivery_date(String tv_delivery_date) {
        this.tv_delivery_date = tv_delivery_date;
    }

    public String getTv_delivery_pickup() {
        return tv_delivery_pickup;
    }

    public void setTv_delivery_pickup(String tv_delivery_pickup) {
        this.tv_delivery_pickup = tv_delivery_pickup;
    }

    public String getTv_delivery_destination() {
        return tv_delivery_destination;
    }

    public void setTv_delivery_destination(String tv_delivery_destination) {
        this.tv_delivery_destination = tv_delivery_destination;
    }
}


