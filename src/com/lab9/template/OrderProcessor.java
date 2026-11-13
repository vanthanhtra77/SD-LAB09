package com.lab9.template;

import com.lab9.model.Order;
import com.lab9.model.OrderItem;

/**
 * LỚP TRỪU TƯỢNG (Abstract Class)
 * Định nghĩa khung xương thuật toán xử lý đơn hàng (Template Method).
 */
public abstract class OrderProcessor {

    /**
     * TEMPLATE METHOD: Được khai báo là 'final' để ngăn chặn các lớp con
     * ghi đè và làm thay đổi cấu trúc/trình tự quy trình xử lý đơn hàng.
     */
    public final void processOrder(Order order) {
        System.out.println(">>> BẮT ĐẦU XỬ LÝ ĐƠN HÀNG: " + order.getOrderId() + " <<<");
        
        // Bước 1: Xác thực đơn hàng (Bước chung - Bất biến)
        validateOrder(order);

        // Bước 2: Áp dụng chiết khấu/khuyến mãi (Bước chung có hook)
        applyDiscounts(order);

        // Bước 3: Xử lý thanh toán (Bước biến đổi - Lớp con tự triển khai)
        processPayment(order);

        // Bước 4: Đóng gói sản phẩm (Bước biến đổi - Lớp con tự triển khai)
        packItems(order);

        // Bước 5: Xử lý bọc quà (Hook method - tùy chọn)
        if (isGiftWrappingRequired(order)) {
            wrapGift(order);
        }

        // Bước 6: Xử lý nghiệp vụ đặc biệt (Hook method - tùy lớp con ghi đè nếu cần)
        performSpecialHandling(order);

        // Bước 7: Bàn giao vận chuyển (Bước biến đổi - Lớp con tự triển khai)
        shipOrder(order);

        // Bước 8: Gửi thông báo cho khách hàng (Bước chung - Bất biến)
        sendNotification(order);

        // Bước 9: Cập nhật trạng thái và ghi log hệ thống (Bước chung - Bất biến)
        recordAuditLog(order);

        System.out.println(">>> HOÀN TẤT XỬ LÝ ĐƠN HÀNG: " + order.getOrderId() + " <<<\n");
    }

    // =========================================================================
    // CÁC BƯỚC CHUNG / BẤT BIẾN (Concrete Methods)
    // =========================================================================

    /**
     * Bước 1: Kiểm tra tính hợp lệ của đơn hàng (danh sách món, địa chỉ, khách hàng)
     */
    protected void validateOrder(Order order) {
        System.out.println("[Bước 1] Xác thực đơn hàng...");
        if (order == null) {
            throw new IllegalArgumentException("Lỗi: Đối tượng đơn hàng null!");
        }
        if (order.getCustomer() == null) {
            throw new IllegalStateException("Lỗi: Đơn hàng thiếu thông tin khách hàng!");
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalStateException("Lỗi: Đơn hàng không có sản phẩm nào!");
        }
        System.out.printf("  ✓ Đơn hàng hợp lệ. Khách hàng: %s | Số lượng sản phẩm: %d | Tổng trọng lượng: %.1f kg\n",
                order.getCustomer().getName(), order.getItems().size(), order.getTotalWeightKg());
    }

    /**
     * Bước 2: Tính toán giảm giá theo chính sách (ví dụ khách hàng VIP)
     */
    protected void applyDiscounts(Order order) {
        System.out.println("[Bước 2] Áp dụng chính sách khuyến mãi & giảm giá...");
        double discount = 0;
        if (order.getCustomer().isVip()) {
            discount = order.getSubtotal() * 0.10; // Giảm 10% cho khách hàng VIP
            System.out.printf("  ✓ Khách hàng VIP được giảm 10%%: -%,.0f VND\n", discount);
        } else {
            System.out.println("  ✓ Khách hàng phổ thông: Không áp dụng chiết khấu VIP.");
        }
        order.setDiscountAmount(discount);
    }

    /**
     * Bước 8: Gửi email/SMS thông báo xác nhận và mã vận đơn cho khách hàng
     */
    protected void sendNotification(Order order) {
        System.out.println("[Bước 8] Gửi thông báo đến khách hàng...");
        System.out.printf("  ✉ Gửi Email tới <%s>: \"Đơn hàng %s đã được đóng gói và bàn giao. Mã tra cứu: %s\"\n",
                order.getCustomer().getEmail(), order.getOrderId(), order.getTrackingCode());
        System.out.printf("  📱 Gửi SMS tới <%s>: \"Don hang %s dang tren duong giao. Tong tien: %,.0f VND\"\n",
                order.getCustomer().getPhone(), order.getOrderId(), order.getFinalTotal());
    }

    /**
     * Bước 9: Ghi nhận lịch sử giao dịch vào cơ sở dữ liệu / ERP
     */
    protected void recordAuditLog(Order order) {
        System.out.println("[Bước 9] Ghi log kiểm toán hệ thống...");
        order.setStatus("ĐÃ_XUẤT_KHO");
        System.out.printf("  [AUDIT-LOG] Lưu thành công: Mã đơn: %s | Trạng thái: %s | Tổng thu: %,.0f VND\n",
                order.getOrderId(), order.getStatus(), order.getFinalTotal());
    }

    // =========================================================================
    // CÁC BƯỚC BIẾN ĐỔI (Abstract Methods - Primitive Operations)
    // Các lớp con bắt buộc phải ghi đè theo nghiệp vụ riêng
    // =========================================================================

    /**
     * Bước 3: Phương thức thanh toán riêng biệt của từng loại đơn
     */
    protected abstract void processPayment(Order order);

    /**
     * Bước 4: Quy cách đóng gói hàng hóa theo tính chất mặt hàng
     */
    protected abstract void packItems(Order order);

    /**
     * Bước 7: Đơn vị và hình thức giao vận phù hợp
     */
    protected abstract void shipOrder(Order order);

    // =========================================================================
    // CÁC HOOK METHODS (Phương thức móc - Lớp con có thể ghi đè hoặc giữ mặc định)
    // =========================================================================

    /**
     * Hook: Kiểm tra xem đơn hàng có yêu cầu gói quà hay không
     */
    protected boolean isGiftWrappingRequired(Order order) {
        return order.isGiftWrapRequested();
    }

    /**
     * Thao tác gói quà nếu Hook isGiftWrappingRequired() trả về true
     */
    protected void wrapGift(Order order) {
        System.out.println("[Bước 5 - Hook] Xử lý gói quà tặng cao cấp kèm thiệp chúc mừng theo yêu cầu.");
    }

    /**
     * Hook: Xử lý nghiệp vụ đặc biệt (mặc định không làm gì, lớp con ghi đè nếu có)
     */
    protected void performSpecialHandling(Order order) {
        // Mặc định không có xử lý đặc biệt
    }
}
