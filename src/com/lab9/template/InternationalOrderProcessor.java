package com.lab9.template;

import com.lab9.model.Order;

/**
 * LỚP CỤ THỂ 3 (Concrete Class 3 - Bổ sung mở rộng)
 * Xử lý đơn hàng quốc tế / xuất khẩu (vận chuyển ra nước ngoài, qua đường hàng không).
 */
public class InternationalOrderProcessor extends OrderProcessor {

    @Override
    protected void processPayment(Order order) {
        System.out.println("[Bước 3] Xử lý thanh toán Đơn hàng quốc tế:");
        order.setPaymentMethod("Cổng thanh toán quốc tế PayPal / Stripe (USD)");
        double feeRatio = 0.035; // 3.5% phí xử lý giao dịch ngoại tệ
        double internationalFee = order.getSubtotal() * feeRatio;
        System.out.printf("  ✓ Đã kết nối Stripe/PayPal API: Xác thực thẻ thanh toán quốc tế.\n");
        System.out.printf("  ✓ Phí chuyển đổi ngoại tệ (3.5%%): %,.0f VND\n", internationalFee);
        System.out.printf("  ✓ Thanh toán hoàn tất, tiền đã chuyển vào tài khoản Escrow an toàn.\n");
    }

    @Override
    protected void packItems(Order order) {
        System.out.println("[Bước 4] Quy cách đóng gói Đơn hàng xuất khẩu quốc tế:");
        System.out.println("  ✓ Cho sản phẩm vào túi chống ẩm, hút chân không để ngăn ngừa hơi muối biển/độ ẩm.");
        System.out.println("  ✓ Đóng thùng carton 7 lớp tiêu chuẩn hàng không IATA (International Air Transport Association).");
        System.out.println("  ✓ Dán mã vạch quốc tế, niêm phong dây đai nhựa và khóa seal hải quan.");
        System.out.printf("  ✓ Trọng lượng quy đổi sau đóng gói: %.2f kg\n", order.getTotalWeightKg() + 1.2);
    }

    @Override
    protected void performSpecialHandling(Order order) {
        // Ghi đè Hook method: Khai báo hải quan và mã số HS Code cho đơn hàng quốc tế
        System.out.println("[Bước 6 - Hook] Xử lý thủ tục Hải quan xuất khẩu (Customs Clearance):");
        System.out.println("  📋 Tự động xuất biểu mẫu tờ khai hải quan quốc tế CN22 / CN23.");
        System.out.println("  ✓ Khai báo mã số phân loại hàng hóa xuất nhập khẩu (HS Code 8517.13.00).");
        System.out.println("  ✓ Kiểm tra danh mục hàng cấm vận chuyển hàng không: ĐẠT TIÊU CHUẨN.");
    }

    @Override
    protected void shipOrder(Order order) {
        System.out.println("[Bước 7] Bàn giao vận chuyển quốc tế Hàng không:");
        // Cước quốc tế: Cước cơ bản 350.000 VND + 120.000 VND/kg
        double baseFee = 350000;
        double weightFee = order.getTotalWeightKg() * 120000;
        double totalShippingFee = baseFee + weightFee;

        order.setShippingFee(totalShippingFee);
        order.setShippingMethod("DHL Express International Air Cargo");

        String tracking = "DHL-INT-" + System.currentTimeMillis() % 10000000;
        order.setTrackingCode(tracking);

        System.out.printf("  ✓ Đơn vị giao vận: %s\n", order.getShippingMethod());
        System.out.printf("  ✓ Cước cơ bản: %,.0f VND | Cước khối lượng (%.1f kg): %,.0f VND\n",
                baseFee, order.getTotalWeightKg(), weightFee);
        System.out.printf("  ✓ Tổng cước vận chuyển quốc tế: %,.0f VND\n", totalShippingFee);
        System.out.printf("  ✓ Mã vận đơn quốc tế: %s\n", tracking);
        System.out.println("  ✓ Lịch trình dự kiến: Thông quan tại Nội Bài/Tân Sơn Nhất -> Giao trong 3-5 ngày làm việc.");
    }
}
