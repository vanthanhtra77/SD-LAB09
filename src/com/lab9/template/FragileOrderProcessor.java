package com.lab9.template;

import com.lab9.model.Order;

/**
 * LỚP CỤ THỂ 2 (Concrete Class 2)
 * Xử lý đơn hàng chứa hàng dễ vỡ, đồ điện tử công nghệ cao hoặc đồ gốm sứ giá trị lớn.
 */
public class FragileOrderProcessor extends OrderProcessor {

    @Override
    protected void processPayment(Order order) {
        System.out.println("[Bước 3] Xử lý thanh toán Đơn hàng dễ vỡ / Giá trị cao:");
        // Với đơn hàng giá trị cao và dễ vỡ, bắt buộc thanh toán trực tuyến trước để bảo đảm
        order.setPaymentMethod("Thẻ Tín Dụng Quốc Tế (Visa/Mastercard 3D-Secure OTP)");
        System.out.println("  ✓ Kiểm tra cổng thanh toán: Đã xác thực bảo mật qua OTP ngân hàng thành công.");
        System.out.printf("  ✓ Đã khấu trừ số tiền: %,.0f VND vào tài khoản thẻ khách hàng.\n", order.getFinalTotal());
    }

    @Override
    protected void packItems(Order order) {
        System.out.println("[Bước 4] Quy cách đóng gói ĐẶC BIỆT cho HÀNG DỄ VỠ:");
        System.out.println("  ✓ Quấn từng sản phẩm bằng 5 lớp màng xốp nổ bọc khí (Bubble wrap).");
        System.out.println("  ✓ Chèn mút xốp định hình EPS 4 góc và hạt xốp chống chấn động.");
        System.out.println("  ✓ Sử dụng thùng carton chịu lực 5 lớp phủ màng chống thấm nước.");
        System.out.println("  ⚠ Dán nhãn cảnh báo kích thước lớn: [HÀNG DỄ VỠ - XIN NHẸ TAY / FRAGILE].");
        System.out.printf("  ✓ Trọng lượng sau khi gia cố đệm chống sốc: %.2f kg\n", order.getTotalWeightKg() + 0.5);
    }

    @Override
    protected void performSpecialHandling(Order order) {
        // Ghi đè Hook method: Thực hiện kiểm định và chụp ảnh hiện trạng trước khi xuất kho
        System.out.println("[Bước 6 - Hook] Nghiệp vụ kiểm soát rủi ro hàng dễ vỡ:");
        System.out.println("  📷 Chụp 4 ảnh hiện trạng góc cạnh sản phẩm và tem bảo hành trước khi niêm phong.");
        System.out.println("  ✓ Lưu trữ hình ảnh vào hồ sơ bằng chứng bảo hiểm hàng hóa.");
    }

    @Override
    protected void shipOrder(Order order) {
        System.out.println("[Bước 7] Bàn giao vận chuyển Hỏa Tốc & Bảo Hiểm Toàn Diện:");
        // Phí vận chuyển = phí hỏa tốc + 1% phí bảo hiểm giá trị đơn hàng
        double baseFee = 60000;
        double insuranceFee = order.getSubtotal() * 0.01;
        double totalShippingFee = baseFee + insuranceFee;

        order.setShippingFee(totalShippingFee);
        order.setShippingMethod("Chuyển Phát Hỏa Tốc Có Bảo Hiểm (Viettel Post Express)");

        String tracking = "VTP-FRAGILE-" + System.currentTimeMillis() % 1000000;
        order.setTrackingCode(tracking);

        System.out.printf("  ✓ Đơn vị vận chuyển: %s\n", order.getShippingMethod());
        System.out.printf("  ✓ Cước hỏa tốc: %,.0f VND | Phí bảo hiểm 100%% hư hỏng: %,.0f VND\n", baseFee, insuranceFee);
        System.out.printf("  ✓ Tổng cước vận chuyển: %,.0f VND\n", totalShippingFee);
        System.out.printf("  ✓ Mã vận đơn ưu tiên cao: %s\n", tracking);
        System.out.println("  ✓ Cam kết giao trong vòng 24 giờ với xe vận tải chuyên dụng có giảm chấn.");
    }
}
