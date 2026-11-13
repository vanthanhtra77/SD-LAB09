# BÀI TẬP LAB 09: THIẾT KẾ MẪU TEMPLATE METHOD
**Đề tài:** Hệ Thống Xử Lý Đơn Hàng Thương Mại Điện Tử (E-Commerce Order Processing)

---

## 📂 Cấu trúc thư mục dự án
```
D:\SD\lab9\
├── src\
│   └── com\lab9\
│       ├── model\
│       │   ├── Customer.java       # Khách hàng (Họ tên, SĐT, Địa chỉ, VIP)
│       │   ├── OrderItem.java      # Chi tiết sản phẩm, khối lượng, tính chất dễ vỡ
│       │   └── Order.java          # Đối tượng đơn hàng tổng hợp
│       ├── template\
│       │   ├── OrderProcessor.java              # Abstract Class (chứa Template Method: processOrder)
│       │   ├── StandardOrderProcessor.java      # Concrete Class 1 (Đơn tiêu chuẩn - COD, GHN)
│       │   ├── FragileOrderProcessor.java       # Concrete Class 2 (Đơn hàng dễ vỡ - Thẻ, Xốp nổ, VTP)
│       │   └── InternationalOrderProcessor.java # Concrete Class 3 (Đơn quốc tế - PayPal, Hải quan, DHL)
│       └── Main.java               # Chương trình kiểm thử 3 kịch bản thực tế
├── uml\
│   ├── class_diagram.puml          # Mã nguồn PlantUML Sơ đồ lớp (Class Diagram)
│   └── sequence_diagram.puml      # Mã nguồn PlantUML Sơ đồ tuần tự (Sequence Diagram)
├── docs\
│   ├── BaoCao_Lab09_TemplateMethod.md # Báo cáo ngắn (2-3 trang) chuẩn nộp
│   └── KetQuaThucNghiem.txt        # Toàn bộ log kết quả chạy chương trình
├── bin\                            # Thư mục chứa file .class sau khi biên dịch
└── README.md                       # Hướng dẫn chi tiết này
```

---

## 🚀 Hướng dẫn biên dịch và chạy chương trình

Mở Terminal / PowerShell tại thư mục `D:\SD\lab9` và thực thi:

### 1. Biên dịch mã nguồn Java:
```powershell
javac -d bin -encoding UTF-8 (Get-ChildItem -Recurse -Path src -Filter *.java).FullName
```

### 2. Chạy chương trình:
```powershell
java -cp bin "-Dfile.encoding=UTF-8" com.lab9.Main
```

---

## 📊 Hướng dẫn vẽ sơ đồ UML với PlantUML

Bạn có thể tạo ảnh sơ đồ từ mã nguồn PlantUML trong thư mục `uml\` bằng 2 cách:

### Cách 1: Sử dụng trang web trực tuyến (Nhanh nhất - Không cần cài đặt)
1. Mở trang web: [https://www.planttext.com/](https://www.planttext.com/) hoặc [https://www.plantuml.com/plantuml/uml/](https://www.plantuml.com/plantuml/uml/)
2. Mở file `uml/class_diagram.puml` (hoặc `uml/sequence_diagram.puml`), copy toàn bộ nội dung và dán vào ô soạn thảo.
3. Nhấn **Generate** hoặc **Submit**, trang web sẽ tự động vẽ sơ đồ cực đẹp. Bạn chỉ cần nhấn chuột phải tải ảnh PNG/SVG về để nộp hoặc dán vào báo cáo.

### Cách 2: Sử dụng Extension trong VS Code / IntelliJ IDEA
* **VS Code:** Cài extension `PlantUML` của tác giả *jebbs*, mở file `.puml` và bấm tổ hợp phím `Alt + D` để xem trước và xuất ảnh.
* **IntelliJ IDEA:** Cài plugin `PlantUML Integration`, mở file `.puml` sẽ tự động hiển thị tab preview bên cạnh.

---

## 📝 Danh mục sản phẩm nộp của sinh viên
1. **Mã nguồn:** Toàn bộ thư mục `src\` đã tổ chức đúng chuẩn Java OOP.
2. **Sơ đồ UML:** File `uml/class_diagram.puml` và `uml/sequence_diagram.puml`.
3. **Kết quả thực nghiệm:** Xem tại `docs/KetQuaThucNghiem.txt` (bạn có thể chụp ảnh màn hình console khi chạy lệnh ở mục trên).
4. **Báo cáo:** Nội dung chi tiết chuẩn mực 2-3 trang tại `docs/BaoCao_Lab09_TemplateMethod.md`.
