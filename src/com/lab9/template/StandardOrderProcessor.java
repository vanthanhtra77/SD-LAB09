package com.lab9.template;

import com.lab9.model.Order;

/**
 * LỚP CỤ THỂ 1 (Concrete Class 1)
 * Xử lý đơn hàng nội địa tiêu chuẩn (quần áo, sách vở, đồ gia dụng thường).
 */
public class StandardOrderProcessor extends OrderProcessor {

    @Override
    protected void processPayment(Order order) {
        System.out.println("[Bước 3] Xử lý thanh toán Đơn hàng tiêu chuẩn:");
        order.setPaymentMethod("COD (Thanh toán tiền mặt khi nhận hàng)");
        System.out.println("  ✓ Khách hàng lựa chọn hình thức: COD - Trả tiền khi nhận hàng.");
        System.out.printf("  ✓ Số tiền cần thu tại địa chỉ giao: %,.0f VND\n", order.getFinalTotal());
    }

    @Override
    protected void packItems(Order order) {
        System.out.println("[Bước 4] Quy cách đóng gói Đơn hàng tiêu chuẩn:");
        System.out.println("  ✓ Xếp các sản phẩm vào thùng carton tiêu chuẩn 3 lớp.");
        System.out.println("  ✓ Dán băng dính niêm phong logo thương hiệu.");
        System.out.printf("  ✓ Tổng trọng lượng kiện hàng: %.2f kg\n", order.getTotalWeightKg());
    }

    @Override
    protected void shipOrder(Order order) {
        System.out.println("[Bước 7] Bàn giao vận chuyển nội địa:");
        double shippingFee = 30000; // Cước cố định nội địa 30,000 VND
        order.setShippingFee(shippingFee);
        order.setShippingMethod("Giao Hàng Nhanh (GHN Standard)");
        
        // Tạo mã vận đơn nội địa
        String tracking = "GHN-VN-" + System.currentTimeMillis() % 1000000;
        order.setTrackingCode(tracking);

        System.out.printf("  ✓ Đơn vị vận chuyển: %s\n", order.getShippingMethod());
        System.out.printf("  ✓ Cước phí vận chuyển: %,.0f VND\n", shippingFee);
        System.out.printf("  ✓ Mã vận đơn phát hành: %s\n", tracking);
        System.out.println("  ✓ Thời gian dự kiến giao: 2 - 3 ngày làm việc.");
    }
}
