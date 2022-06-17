package com.example.fcfs;

public class OrderModel {

    static String order;
    static String phone;

    public OrderModel(){}

    public OrderModel(String order, String phone) {
        this.order = order;
        this.phone = phone;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
