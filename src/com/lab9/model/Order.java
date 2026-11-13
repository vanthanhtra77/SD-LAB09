package com.lab9.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Đối tượng đơn hàng tổng quát trong hệ thống.
 */
public class Order {
    private String orderId;
    private Customer customer;
    private List<OrderItem> items;
    private LocalDateTime orderDate;
    private String paymentMethod;
    private String shippingMethod;
    private String trackingCode;
    private String status;
    private double shippingFee;
    private double discountAmount;
    private boolean giftWrapRequested;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.status = "CHỜ_XỬ_LÝ";
        this.shippingFee = 0;
        this.discountAmount = 0;
        this.giftWrapRequested = false;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (OrderItem item : items) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    public double getTotalWeightKg() {
        double totalWeight = 0;
        for (OrderItem item : items) {
            totalWeight += item.getWeightKg() * item.getQuantity();
        }
        return totalWeight;
    }

    public boolean hasFragileItems() {
        for (OrderItem item : items) {
            if (item.isFragile()) {
                return true;
            }
        }
        return false;
    }

    public double getFinalTotal() {
        return Math.max(0, getSubtotal() - discountAmount + shippingFee);
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public List<OrderItem> getItems() { return items; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getShippingMethod() { return shippingMethod; }
    public void setShippingMethod(String shippingMethod) { this.shippingMethod = shippingMethod; }
    public String getTrackingCode() { return trackingCode; }
    public void setTrackingCode(String trackingCode) { this.trackingCode = trackingCode; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getShippingFee() { return shippingFee; }
    public void setShippingFee(double shippingFee) { this.shippingFee = shippingFee; }
    public double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }
    public boolean isGiftWrapRequested() { return giftWrapRequested; }
    public void setGiftWrapRequested(boolean giftWrapRequested) { this.giftWrapRequested = giftWrapRequested; }

    public void printSummary() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("================================================================");
        System.out.printf("  ĐƠN HÀNG: %s | Ngày tạo: %s\n", orderId, orderDate.format(dtf));
        System.out.printf("  Khách hàng: %s\n", customer.getName());
        System.out.printf("  Địa chỉ giao hàng: %s\n", customer.getAddress());
        System.out.printf("  Số điện thoại: %s | Email: %s\n", customer.getPhone(), customer.getEmail());
        System.out.println("  Danh sách sản phẩm:");
        for (OrderItem item : items) {
            System.out.println("    " + item.toString());
        }
        System.out.printf("  Tạm tính:         %,12.0f VND\n", getSubtotal());
        if (discountAmount > 0) {
            System.out.printf("  Giảm giá (Voucher/VIP): -%,10.0f VND\n", discountAmount);
        }
        System.out.printf("  Phí vận chuyển:   +%,12.0f VND (%s)\n", shippingFee, shippingMethod != null ? shippingMethod : "Chưa chọn");
        System.out.println("  --------------------------------------------------------------");
        System.out.printf("  TỔNG THANH TOÁN:  %,12.0f VND\n", getFinalTotal());
        System.out.printf("  Phương thức TT:   %s\n", paymentMethod != null ? paymentMethod : "Chưa thanh toán");
        System.out.printf("  Mã vận đơn:       %s\n", trackingCode != null ? trackingCode : "Chưa phát hành");
        System.out.printf("  Trạng thái đơn:   [%s]\n", status);
        System.out.println("================================================================\n");
    }
}
