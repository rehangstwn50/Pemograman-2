# RentCar System — Panduan Setup NetBeans

## Teknologi
- Java JDK 25
- Apache Tomcat 9
- MySQL 8.x
- NetBeans IDE

---

## LANGKAH 1 — Import Database

1. Buka **phpMyAdmin** atau **MySQL Workbench**
2. Buat database baru atau jalankan script:
   ```
   File: database/rentcar.sql
   ```
3. Import file SQL tersebut
4. Default login aplikasi: `admin` / `admin123`

---

## LANGKAH 2 — Tambah MySQL Connector

1. Download **mysql-connector-j-8.3.0.jar** dari:
   - https://dev.mysql.com/downloads/connector/j/
   - Atau: https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.3.0/mysql-connector-j-8.3.0.jar

2. Copy file `.jar` ke folder:
   ```
   RentCar/src/main/webapp/WEB-INF/lib/
   ```

3. Di NetBeans: klik kanan project → **Properties** → **Libraries** → **Add JAR/Folder** → pilih file jar

---

## LANGKAH 3 — Buka di NetBeans

1. Buka NetBeans
2. **File** → **Open Project** → pilih folder `RentCar`
3. Tunggu project loading

---

## LANGKAH 4 — Konfigurasi Database

Edit file:
```
src/main/java/com/rentcar/util/DBConnection.java
```

Sesuaikan:
```java
private static final String URL = "jdbc:mysql://localhost:3306/rentcar?...";
private static final String USER = "root";      // ganti username MySQL Anda
private static final String PASS = "";           // ganti password MySQL Anda
```

---

## LANGKAH 5 — Set Tomcat Server

1. Di NetBeans: klik kanan project → **Properties** → **Run**
2. Pilih **Server**: Apache Tomcat 9
3. Jika belum ada: **Tools** → **Servers** → **Add Server** → Tomcat 9

---

## LANGKAH 6 — Jalankan Project

1. Klik kanan project → **Clean and Build**
2. Klik kanan project → **Run**
3. Browser akan otomatis terbuka di: `http://localhost:8080/RentCar/`
4. Login dengan: `admin` / `admin123`

---

## Struktur Project

```
RentCar/
├── database/
│   └── rentcar.sql          ← Script database
├── src/main/
│   ├── java/com/rentcar/
│   │   ├── controller/      ← Servlet (Login, Mobil, Customer, Sewa, Kembali, Laporan)
│   │   ├── dao/             ← Database access (MobilDAO, CustomerDAO, TransaksiDAO)
│   │   ├── model/           ← POJO model (Mobil, Customer, TransaksiSewa)
│   │   └── util/            ← DBConnection, AuthFilter
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── lib/         ← Taruh mysql-connector di sini
│       │   └── web.xml
│       ├── css/style.css
│       ├── login.jsp
│       ├── dashboard.jsp
│       ├── mobil.jsp
│       ├── customer.jsp
│       ├── sewa.jsp
│       ├── kembali.jsp
│       └── laporan.jsp
└── nbproject/               ← Konfigurasi NetBeans
```

---

## Fitur Aplikasi

| Halaman | Fitur |
|---------|-------|
| Login | Autentikasi username/password dengan MD5 |
| Dashboard | Statistik total mobil, customer, transaksi terbaru |
| Data Mobil | CRUD mobil + modal form + search |
| Data Customer | CRUD customer + modal form + search |
| Transaksi Sewa | Input sewa, auto-hitung lama & total biaya |
| Transaksi Kembali | Cari transaksi, hitung denda, selesaikan pengembalian |
| Laporan | Filter periode, export Excel, print PDF |

---

## Masalah Umum

**Error: MySQL Driver tidak ditemukan**
→ Pastikan file mysql-connector-j-8.3.0.jar sudah ada di WEB-INF/lib dan sudah di-add di Libraries NetBeans

**Error: Access denied for user 'root'**
→ Cek username/password di DBConnection.java

**Halaman tidak muncul, redirect ke login terus**
→ Pastikan session berjalan, cek Tomcat sudah start
