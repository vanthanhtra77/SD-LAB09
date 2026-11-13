package com.lab9.model;

/**
 * Chi tiết từng mặt hàng trong đơn hàng.
 */
public class OrderItem {
    private String productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double weightKg;
    private boolean isFragile;

    public OrderItem(String productId, String productName, int quantity, double unitPrice, double weightKg, boolean isFragile) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.weightKg = weightKg;
        this.isFragile = isFragile;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getWeightKg() { return weightKg; }
    public boolean isFragile() { return isFragile; }

    public double getTotalPrice() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        return String.format("- %s (x%d) | Đơn giá: %,.0f VND | Khối lượng: %.1fkg%s",
                productName, quantity, unitPrice, weightKg * quantity, (isFragile ? " [HÀNG DỄ VỠ]" : ""));
    }
}
