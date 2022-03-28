package com.example.subway_deliver;

public class MainData {


//    private String receiver;
//    private String userPhone;
//    private String receiverPhone;
    private String pickupAdd;
    private String deliverAdd;
    private String deliverObj;
    private String deliverEst;
    private String numTag;
    private String requestDate;
//    private String acceptDate;

    private String idx;

//    public MainData(String pickupAdd, String deliverAdd, String deliverObj, String deliverEst, String numTag) {
//        this.pickupAdd = pickupAdd;

//        this.deliverAdd = deliverAdd;
//        this.deliverObj = deliverObj;
//        this.deliverEst = deliverEst;
//        this.numTag = numTag;
//    }


    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getNumTag() {
        return numTag;
    }

    public void setNumTag(String numTag) {
        this.numTag = numTag;
    }

    public String getPickupAdd() {
        return pickupAdd;
    }

    public void setPickupAdd(String pickupAdd) {
        this.pickupAdd = pickupAdd;
    }

    public String getDeliverAdd() {
        return deliverAdd;
    }

    public void setDeliverAdd(String deliverAdd) {
        this.deliverAdd = deliverAdd;
    }

    public String getDeliverObj() {
        return deliverObj;
    }

    public void setDeliverObj(String deliverObj) {
        this.deliverObj = deliverObj;
    }

    public String getDeliverEst() {
        return deliverEst;
    }

    public void setDeliverEst(String deliverEst) {
        this.deliverEst = deliverEst;
    }
}


