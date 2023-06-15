package com.eraybd.project;

public class activity_request_listdata {

    private String donationType, includes, city, branch, quantity,dateReceipt;
    private Integer deliveryID;

    // _detailed sayfasinda gosterilecek olan bilgiler
    public activity_request_listdata(String donationType, String quantity, String includes, String city, String branch,String dateReceipt,Integer deliveryID) {
        this.donationType = donationType;
        this.quantity = quantity;
        this.includes = includes;
        this.city = city;
        this.branch = branch;
        this.city = city;
        this.branch = branch;
        this.dateReceipt = dateReceipt;
        this.deliveryID = deliveryID;
    }

    public String getDonationType() {
        return donationType;
    }

    public String getIncludes() {
        return includes;
    }

    public String getCity() {
        return city;
    }

    public String getBranch() {
        return branch;
    }

    public String getQuantity() {
        return quantity;
    }
    public Integer getdeliveryID() {
        return deliveryID;
    }

    public String getdateREceipt() {
        return dateReceipt;
    }
}
