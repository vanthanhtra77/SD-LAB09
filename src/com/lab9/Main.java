package com.lab9;

import com.lab9.model.Customer;
import com.lab9.model.Order;
import com.lab9.model.OrderItem;
import com.lab9.template.FragileOrderProcessor;
import com.lab9.template.InternationalOrderProcessor;
import com.lab9.template.OrderProcessor;
import com.lab9.template.StandardOrderProcessor;

/**
 * CHƯƠNG TRÌNH CHÍNH (MAIN TEST PROGRAM)
 * Minh họa và kiểm thử mẫu thiết kế Template Method trong xử lý đơn hàng.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("#################################################################");
        System.out.println("#      BÀI TẬP LAB 09 - MẪU THIẾT KẾ TEMPLATE METHOD            #");
        System.out.println("#      ĐỀ TÀI: HỆ THỐNG XỬ LÝ ĐƠN HÀNG THƯƠNG MẠI ĐIỆN TỬ       #");
        System.out.println("#################################################################\n");

        // =====================================================================
        // TRƯỜNG HỢP 1: ĐƠN HÀNG NỘI ĐỊA TIÊU CHUẨN (STANDARD ORDER)
        // =====================================================================
        System.out.println("*****************************************************************");
        System.out.println("* KỊCH BẢN 1: XỬ LÝ ĐƠN HÀNG TIÊU CHUẨN (STANDARD ORDER)        *");
        System.out.println("*****************************************************************");
        Customer customer1 = new Customer(
                "KH001",
                "Nguyễn Văn An",
                "an.nguyen@gmail.com",
                "0912345678",
                "Số 45, Đường Lê Lợi, Phường Bến Nghé, Quận 1, TP. Hồ Chí Minh",
                false // Khách hàng phổ thông
        );

        Order order1 = new Order("ORD-VN-2026-001", customer1);
        order1.addItem(new OrderItem("P101", "Sách Lập trình Hướng đối tượng Java", 2, 120000, 0.8, false));
        order1.addItem(new OrderItem("P102", "Áo thun Polo Nam Cotton cao cấp", 1, 250000, 0.3, false));

        // Khởi tạo bộ xử lý mẫu: StandardOrderProcessor
        OrderProcessor standardProcessor = new StandardOrderProcessor();
        standardProcessor.processOrder(order1);
        order1.printSummary();

        // =====================================================================
        // TRƯỜNG HỢP 2: ĐƠN HÀNG HÀNG DỄ VỠ & GIÁ TRỊ CAO (FRAGILE ORDER)
        // Có kích hoạt Hook: Khách VIP (chiết khấu 10%) & Có yêu cầu bọc quà
        // =====================================================================
        System.out.println("*****************************************************************");
        System.out.println("* KỊCH BẢN 2: XỬ LÝ ĐƠN HÀNG DỄ VỠ - GIÁ TRỊ CAO (FRAGILE ORDER)*");
        System.out.println("*****************************************************************");
        Customer customer2 = new Customer(
                "KH002",
                "Trần Thị Bích Ngọc",
                "bichngoc.tran@techcorp.vn",
                "0987654321",
                "Biệt thự BT-12, Khu đô thị Ciputra, Tây Hồ, Hà Nội",
                true // Khách hàng VIP
        );

        Order order2 = new Order("ORD-FRAGILE-2026-002", customer2);
        order2.addItem(new OrderItem("P201", "Bình hoa gốm sứ mạ vàng Bát Tràng nghệ nhân", 1, 3500000, 2.5, true));
        order2.addItem(new OrderItem("P202", "Màn hình Gaming OLED UltraSharp 27 inch 4K", 1, 15000000, 5.2, true));
        order2.setGiftWrapRequested(true); // Yêu cầu gói quà tặng cao cấp

        // Khởi tạo bộ xử lý mẫu: FragileOrderProcessor
        OrderProcessor fragileProcessor = new FragileOrderProcessor();
        fragileProcessor.processOrder(order2);
        order2.printSummary();

        // =====================================================================
        // TRƯỜNG HỢP 3: ĐƠN HÀNG QUỐC TẾ XUẤT KHẨU (INTERNATIONAL ORDER)
        // Có kích hoạt Hook: Xử lý hải quan quốc tế & thanh toán Stripe/PayPal
        // =====================================================================
        System.out.println("*****************************************************************");
        System.out.println("* KỊCH BẢN 3: XỬ LÝ ĐƠN HÀNG QUỐC TẾ XUẤT KHẨU (INTERNATIONAL) *");
        System.out.println("*****************************************************************");
        Customer customer3 = new Customer(
                "KH003",
                "Takeshi Yamamoto",
                "yamamoto.t@tokyodev.jp",
                "+81-90-1234-5678",
                "Shinjuku-ku, Nishi-Shinjuku 2-8-1, Tokyo, Japan",
                false // Khách hàng quốc tế
        );

        Order order3 = new Order("ORD-INT-2026-003", customer3);
        order3.addItem(new OrderItem("P301", "Bộ cà phê chồn Robusta Đắk Lắk truyền thống", 3, 600000, 1.5, false));
        order3.addItem(new OrderItem("P302", "Tượng gỗ mỹ nghệ trầm hương thủ công", 1, 5200000, 3.0, true));

        // Khởi tạo bộ xử lý mẫu: InternationalOrderProcessor
        OrderProcessor intlProcessor = new InternationalOrderProcessor();
        intlProcessor.processOrder(order3);
        order3.printSummary();

        System.out.println("=================================================================");
        System.out.println("=> KẾT LUẬN: Cả 3 trường hợp đều tuân thủ chặt chẽ khung sườn thuật");
        System.out.println("   toán định nghĩa trong OrderProcessor.processOrder() (Template Method),");
        System.out.println("   đồng thời linh hoạt thay đổi các bước thanh toán, đóng gói, vận");
        System.out.println("   chuyển và các Hook nghiệp vụ mà không làm trùng lặp mã nguồn.");
        System.out.println("=================================================================");
    }
}
