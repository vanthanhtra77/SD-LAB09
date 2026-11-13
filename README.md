# SD-LAB09: TEMPLATE METHOD PATTERN
**Đề tài:** Hệ Thống Xử Lý Đơn Hàng Thương Mại Điện Tử (E-Commerce Order Processing System)

---

## 📂 Cấu trúc mã nguồn dự án

```
src/
└── com/lab9/
    ├── model/
    │   ├── Customer.java                     # Thông tin khách hàng & phân loại VIP
    │   ├── OrderItem.java                    # Chi tiết sản phẩm trong đơn hàng
    │   └── Order.java                        # Đối tượng đơn hàng tổng hợp
    ├── template/
    │   ├── OrderProcessor.java               # [Abstract Class] Chứa Template Method: processOrder()
    │   ├── StandardOrderProcessor.java       # [Concrete Class 1] Đơn hàng tiêu chuẩn (COD, GHN)
    │   ├── FragileOrderProcessor.java        # [Concrete Class 2] Đơn hàng dễ vỡ (Thẻ OTP, ViettelPost)
    │   └── InternationalOrderProcessor.java  # [Concrete Class 3] Đơn xuất khẩu (PayPal, Hải quan, DHL)
    └── Main.java                             # Chương trình kiểm thử 3 kịch bản thực tế
```

---

## 🚀 Hướng dẫn biên dịch và chạy chương trình

### 1. Sử dụng dòng lệnh (CLI):
```powershell
# Biên dịch mã nguồn Java:
javac -d bin -encoding UTF-8 (Get-ChildItem -Recurse -Path src -Filter *.java).FullName

# Chạy chương trình:
java -cp bin "-Dfile.encoding=UTF-8" com.lab9.Main
```

### 2. Sử dụng Eclipse IDE:
1. Mở Eclipse -> **File** -> **Import...** -> **Existing Projects into Workspace**.
2. Chọn thư mục dự án và nhấn **Finish**.
3. Chuột phải vào `Main.java` chọn **Run As** -> **Java Application**.
