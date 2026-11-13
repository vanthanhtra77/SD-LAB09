package com.lab9.model;

/**
 * Thông tin khách hàng đặt hàng.
 */
public class Customer {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private boolean isVip;

    public Customer(String id, String name, String email, String phone, String address, boolean isVip) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.isVip = isVip;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public boolean isVip() { return isVip; }

    @Override
    public String toString() {
        return name + " (" + (isVip ? "VIP" : "Thường") + ") - SĐT: " + phone + " - Đ/c: " + address;
    }
}
