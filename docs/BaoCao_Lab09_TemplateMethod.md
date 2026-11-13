# BÁO CÁO BÀI TẬP LAB 09: THIẾT KẾ PHẦN MỀM
## ĐỀ TÀI: ÁP DỤNG MẪU THIẾT KẾ TEMPLATE METHOD TRONG HỆ THỐNG XỬ LÝ ĐƠN HÀNG THƯƠNG MẠI ĐIỆN TỬ

---

* **Học phần:** Thiết kế Phần mềm / Kiến trúc Phần mềm (Software Architecture & Design Patterns)
* **Giảng viên hướng dẫn:** ThS. Phạm Văn Việt
* **Đơn vị:** Khoa Công nghệ Thông tin
* **Ngôn ngữ thực hiện:** Java (JDK 17)

---

## 1. MÔ TẢ BÀI TOÁN THỰC TẾ
Trong các nền tảng thương mại điện tử (E-Commerce) hiện đại như Shopee, Lazada hay Amazon, việc xử lý đơn hàng (Order Fulfillment) là một quy trình nghiệp vụ xương sống. Một đơn hàng từ lúc đặt mua đến khi xuất kho giao đến tay khách hàng phải trải qua một chuỗi các công đoạn nối tiếp nhau. 

Tuy nhiên, tùy thuộc vào loại đơn hàng (đơn hàng tiêu dùng nội địa, đơn hàng đồ điện tử / gốm sứ dễ vỡ giá trị cao, hay đơn hàng xuất khẩu quốc tế), các bước triển khai cụ thể sẽ có sự khác biệt lớn:
* **Phương thức thanh toán:** Đơn thông thường chấp nhận COD (tiền mặt); đơn giá trị cao/dễ vỡ bắt buộc thanh toán trực tuyến qua thẻ tín dụng có mã OTP; đơn quốc tế thanh toán qua cổng PayPal/Stripe kèm phí quy đổi ngoại tệ.
* **Quy chuẩn đóng gói:** Đơn thường chỉ cần hộp carton tiêu chuẩn; đơn dễ vỡ cần bọc 5 lớp màng xốp khí và thùng gia cường chịu lực; đơn quốc tế cần hút chân không chống ẩm và niêm phong hải quan theo tiêu chuẩn hàng không IATA.
* **Thủ tục đặc thù:** Đơn hàng quốc tế bắt buộc phải khai báo tờ khai hải quan (Customs Clearance) và mã số HS Code; đơn hàng dễ vỡ cần chụp ảnh hiện trạng lưu hồ sơ bảo hiểm.
* **Hình thức vận chuyển:** Giao hàng tiêu chuẩn (GHN/GHTK), giao hàng hỏa tốc có bảo hiểm, hoặc vận chuyển đường hàng không quốc tế (DHL Express).

**Vấn đề đặt ra:** Nếu không áp dụng mẫu thiết kế phù hợp, mã nguồn sẽ xuất hiện hàng loạt câu lệnh `if-else` hoặc `switch-case` lồng nhau phức tạp, trùng lặp mã kiểm tra hợp lệ, thông báo và ghi log, dẫn đến vi phạm nghiêm trọng nguyên lý Đóng/Mở (Open/Closed Principle) và Đơn trách nhiệm (Single Responsibility Principle).

---

## 2. GIẢI PHÁP THIẾT KẾ VỚI TEMPLATE METHOD PATTERN

### 2.1. Khái niệm và Lý do lựa chọn
**Template Method (Mẫu Phương Thức Mẫu)** là một mẫu thiết kế thuộc nhóm hành vi (Behavioral Pattern). Mẫu này cho phép định nghĩa khung sườn (skeleton) của một thuật toán trong một lớp trừu tượng (Abstract Class), nhưng trì hoãn việc cài đặt một số bước cho các lớp con (Concrete Subclasses). Nhờ đó, các lớp con có thể định nghĩa lại một số bước của thuật toán mà không làm thay đổi cấu trúc tổng thể của nó.

Mẫu này đặc biệt tuân thủ **Hollywood Principle** (*"Don't call us, we'll call you"*): Lớp cha gọi các thao tác của lớp con khi cần thiết, thay vì để lớp con tự do can thiệp vào luồng xử lý chính.

### 2.2. Phân tích các bước trong quy trình xử lý đơn hàng

| STT | Bước nghiệp vụ | Loại phương thức | Vai trò / Cơ chế hoạt động |
|:---:|:---|:---:|:---|
| 1 | `validateOrder(Order)` | **Concrete (Chung)** | Kiểm tra tính hợp lệ của đơn hàng (thông tin khách, danh sách món, địa chỉ giao). Không đổi giữa các loại đơn. |
| 2 | `applyDiscounts(Order)` | **Concrete (Chung)** | Áp dụng chính sách giảm giá (ví dụ: chiết khấu 10% cho khách VIP). |
| 3 | `processPayment(Order)` | **Abstract (Biến đổi)** | Bắt buộc lớp con ghi đè để xử lý thanh toán (COD, Thẻ tín dụng, PayPal/Stripe). |
| 4 | `packItems(Order)` | **Abstract (Biến đổi)** | Bắt buộc lớp con ghi đè để đóng gói theo đúng tính chất mặt hàng. |
| 5 | `isGiftWrappingRequired(Order)` / `wrapGift(Order)` | **Hook (Tùy chọn)** | Cho phép kích hoạt gói quà và viết thiệp chúc mừng nếu khách hàng có yêu cầu. |
| 6 | `performSpecialHandling(Order)` | **Hook (Tùy chọn)** | Mặc định không làm gì; lớp con ghi đè khi có thủ tục đặc thù (kiểm định bảo hiểm, khai hải quan). |
| 7 | `shipOrder(Order)` | **Abstract (Biến đổi)** | Bắt buộc lớp con ghi đè để bàn giao đơn vị vận chuyển và tính cước phí. |
| 8 | `sendNotification(Order)` | **Concrete (Chung)** | Gửi email và SMS thông báo mã vận đơn đến khách hàng. |
| 9 | `recordAuditLog(Order)` | **Concrete (Chung)** | Cập nhật trạng thái `ĐÃ_XUẤT_KHO` và ghi log kiểm toán vào hệ thống. |

---

## 3. KIẾN TRÚC VÀ CÀI ĐẶT CHƯƠNG TRÌNH

### 3.1. Cấu trúc thư mục mã nguồn
```
lab9/
├── src/
│   └── com/
│       └── lab9/
│           ├── model/
│           │   ├── Customer.java       # Thông tin khách hàng & phân loại VIP
│           │   ├── OrderItem.java      # Chi tiết sản phẩm trong giỏ hàng
│           │   └── Order.java          # Thực thể đơn hàng tổng quát
│           ├── template/
│           │   ├── OrderProcessor.java              # Lớp trừu tượng định nghĩa Template Method
│           │   ├── StandardOrderProcessor.java      # Lớp con: Đơn hàng nội địa tiêu chuẩn
│           │   ├── FragileOrderProcessor.java       # Lớp con: Đơn hàng dễ vỡ & giá trị cao
│           │   └── InternationalOrderProcessor.java # Lớp con: Đơn hàng xuất khẩu quốc tế
│           └── Main.java               # Chương trình thử nghiệm & kiểm thử 3 kịch bản
├── uml/
│   ├── class_diagram.puml              # Mã nguồn PlantUML sơ đồ lớp
│   └── sequence_diagram.puml          # Mã nguồn PlantUML sơ đồ tuần tự
└── docs/
    ├── BaoCao_Lab09_TemplateMethod.md  # Báo cáo chi tiết đồ án
    └── KetQuaThucNghiem.txt            # Nhật ký kết quả thực thi chương trình
```

### 3.2. Cốt lõi của Lớp trừu tượng (OrderProcessor)
```java
public abstract class OrderProcessor {

    // TEMPLATE METHOD: Đặt từ khóa 'final' để ngăn lớp con phá vỡ khung quy trình
    public final void processOrder(Order order) {
        validateOrder(order);           // Bước 1: Chung
        applyDiscounts(order);          // Bước 2: Chung
        processPayment(order);          // Bước 3: Trừu tượng (Lớp con triển khai)
        packItems(order);               // Bước 4: Trừu tượng (Lớp con triển khai)
        
        if (isGiftWrappingRequired(order)) { // Bước 5: Hook method
            wrapGift(order);
        }
        performSpecialHandling(order);  // Bước 6: Hook method
        shipOrder(order);               // Bước 7: Trừu tượng (Lớp con triển khai)
        sendNotification(order);        // Bước 8: Chung
        recordAuditLog(order);          // Bước 9: Chung
    }

    // Các phương thức trừu tượng buộc lớp con phải định nghĩa
    protected abstract void processPayment(Order order);
    protected abstract void packItems(Order order);
    protected abstract void shipOrder(Order order);

    // Hook methods
    protected boolean isGiftWrappingRequired(Order order) { return order.isGiftWrapRequested(); }
    protected void wrapGift(Order order) { System.out.println("Gói quà tặng cao cấp..."); }
    protected void performSpecialHandling(Order order) {}
}
```

---

## 4. SƠ ĐỒ THIẾT KẾ UML

Hệ thống cung cấp đầy đủ mã nguồn **PlantUML** trong thư mục `uml/` để sinh viên có thể tự render sang ảnh PNG/SVG thông qua công cụ PlantUML hoặc trang web [www.planttext.com](https://www.planttext.com).

### 4.1. Sơ đồ lớp (Class Diagram)
* File mã nguồn: `uml/class_diagram.puml`
* Mô tả:
  * `OrderProcessor`: Khai báo `processOrder(Order): void` với thuộc tính `{final}` đại diện cho Template Method.
  * Ba lớp con `StandardOrderProcessor`, `FragileOrderProcessor`, `InternationalOrderProcessor` quan hệ kế thừa (`--|>`) từ `OrderProcessor`.
  * Lớp `Order` quan hệ kết tập với `OrderItem` và liên kết với `Customer`.

### 4.2. Sơ đồ tuần tự (Sequence Diagram)
* File mã nguồn: `uml/sequence_diagram.puml`
* Mô tả sự tương tác giữa đối tượng Client, Abstract Class và Concrete Subclasses qua từng bước của thuật toán.

---

## 5. KẾT QUẢ THỰC NGHIỆM VÀ KIỂM THỬ

Chương trình được kiểm thử thành công qua 3 kịch bản thực tế tương ứng với 3 lớp cụ thể:

### Kịch bản 1: Đơn hàng tiêu chuẩn (Standard Order)
* Khách hàng: Nguyễn Văn An (Khách phổ thông).
* Sản phẩm: Sách Java OOP (2 cuốn), Áo polo nam (1 chiếc).
* Kết quả xử lý:
  * Thanh toán: Thu hộ tiền mặt khi nhận hàng (COD).
  * Đóng gói: Thùng carton tiêu chuẩn 3 lớp, dán băng keo niêm phong thương hiệu.
  * Vận chuyển: Giao Hàng Nhanh (GHN Standard), phí cố định 30,000 VND.
  * Tổng thanh toán: 520,000 VND.

### Kịch bản 2: Đơn hàng dễ vỡ & Giá trị cao (Fragile Order)
* Khách hàng: Trần Thị Bích Ngọc (Khách VIP - được giảm giá 10% tương đương 1,850,000 VND). Có yêu cầu bọc quà tặng.
* Sản phẩm: Bình hoa gốm sứ mạ vàng Bát Tràng (1 chiếc), Màn hình Gaming OLED 4K (1 chiếc).
* Kết quả xử lý:
  * Thanh toán: Bắt buộc thẻ tín dụng quốc tế bảo mật 3D-Secure OTP.
  * Đóng gói: Bọc 5 lớp xốp nổ khí, đệm mút EPS chống chấn 4 góc, dán nhãn đỏ cảnh báo "HÀNG DỄ VỠ - XIN NHẸ TAY".
  * Hook kích hoạt: Gói quà tặng thiệp mừng & Chụp 4 ảnh hiện trạng sản phẩm lưu hồ sơ bảo hiểm.
  * Vận chuyển: Chuyển phát hỏa tốc Viettel Post kèm gói bảo hiểm hàng hóa 100%.

### Kịch bản 3: Đơn hàng xuất khẩu quốc tế (International Order)
* Khách hàng: Takeshi Yamamoto (Tokyo, Nhật Bản).
* Sản phẩm: Cà phê chồn Robusta Đắk Lắk (3 hộp), Tượng trầm hương thủ công (1 tượng).
* Kết quả xử lý:
  * Thanh toán: Cổng thanh toán Stripe/PayPal USD (phí ngoại tệ 3.5%).
  * Đóng gói: Hút chân không chống ẩm, thùng carton 7 lớp chuẩn hàng không IATA, kẹp seal hải quan.
  * Hook kích hoạt: Xuất biểu mẫu hải quan CN22/CN23, kiểm tra danh mục hàng cấm bay và khai báo mã HS Code (8517.13.00).
  * Vận chuyển: Vận chuyển đường hàng không DHL Express International.

*(Toàn bộ log chi tiết được ghi tự động tại file `docs/KetQuaThucNghiem.txt`).*

---

## 6. ĐÁNH GIÁ ƯU ĐIỂM VÀ LỢI ÍCH ĐẠT ĐƯỢC

Việc áp dụng mẫu Template Method trong bài toán xử lý đơn hàng mang lại các giá trị kỹ thuật to lớn:
1. **Loại bỏ trùng lặp mã nguồn (Tuân thủ nguyên lý DRY - Don't Repeat Yourself):** Toàn bộ các khâu kiểm tra tính hợp lệ (`validateOrder`), tính toán chiết khấu (`applyDiscounts`), thông báo qua Email/SMS (`sendNotification`) và ghi log kiểm toán (`recordAuditLog`) chỉ cần viết một lần duy nhất tại `OrderProcessor`.
2. **Tuân thủ nguyên lý Đóng/Mở (Open/Closed Principle - OCP):** Khi doanh nghiệp mở rộng thêm loại đơn hàng mới (ví dụ: *Đơn hàng giao siêu tốc trong 2 giờ GrabExpress*, hoặc *Đơn hàng thực phẩm đông lạnh bảo ôn*), lập trình viên chỉ cần tạo thêm một lớp con mới kế thừa `OrderProcessor` và ghi đè các bước biến đổi, hoàn toàn không phải chỉnh sửa code cũ.
3. **Bảo vệ luồng nghiệp vụ cốt lõi:** Nhờ từ khóa `final` ở phương thức `processOrder()`, các lập trình viên khác không thể vô tình hay cố ý đảo lộn thứ tự (ví dụ: không thể giao hàng trước khi thanh toán, hoặc không thể xuất kho mà bỏ qua bước đóng gói).
4. **Linh hoạt nhờ Hook Methods:** Cho phép các lớp con tùy biến hành vi bổ sung (như khai báo hải quan, chụp ảnh kiểm định) mà không bắt buộc các lớp con thông thường khác phải triển khai phức tạp.

---

## 7. KẾT LUẬN
Bài tập đã giải quyết trọn vẹn và hoàn chỉnh bài toán thực tế về xử lý đơn hàng thương mại điện tử bằng mẫu thiết kế **Template Method**. Hệ thống đảm bảo tính trừu tượng hóa cao, kiến trúc hướng đối tượng chuẩn mực, dễ bảo trì, dễ mở rộng và kiểm thử thực nghiệm thành công 100%.
