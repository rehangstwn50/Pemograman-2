-- =============================================
-- DATABASE: rentcar
-- =============================================
CREATE DATABASE IF NOT EXISTS rentcar CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE rentcar;

-- Tabel Mobil
CREATE TABLE IF NOT EXISTS mobil (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kode_mobil VARCHAR(10) NOT NULL UNIQUE,
    nama_mobil VARCHAR(100) NOT NULL,
    merk VARCHAR(50) NOT NULL,
    tahun INT NOT NULL,
    warna VARCHAR(50) NOT NULL,
    no_plat VARCHAR(15) NOT NULL UNIQUE,
    kapasitas INT NOT NULL DEFAULT 5,
    harga_sewa DECIMAL(12,0) NOT NULL,
    status ENUM('Tersedia','Disewa','Tidak Tersedia') DEFAULT 'Tersedia',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabel Customer
CREATE TABLE IF NOT EXISTS customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kode_customer VARCHAR(10) NOT NULL UNIQUE,
    nama_lengkap VARCHAR(100) NOT NULL,
    no_ktp VARCHAR(16) NOT NULL UNIQUE,
    tempat_lahir VARCHAR(50),
    tanggal_lahir DATE,
    jenis_kelamin ENUM('Laki-laki','Perempuan'),
    no_telepon VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    alamat TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabel Transaksi Sewa
CREATE TABLE IF NOT EXISTS transaksi_sewa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    no_transaksi VARCHAR(15) NOT NULL UNIQUE,
    customer_id INT NOT NULL,
    mobil_id INT NOT NULL,
    tanggal_sewa DATE NOT NULL,
    tanggal_kembali_rencana DATE NOT NULL,
    lama_sewa INT NOT NULL,
    total_biaya DECIMAL(12,0) NOT NULL,
    uang_jaminan DECIMAL(12,0) DEFAULT 0,
    catatan TEXT,
    status ENUM('Aktif','Selesai','Terlambat') DEFAULT 'Aktif',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (mobil_id) REFERENCES mobil(id)
);

-- Tabel Transaksi Kembali
CREATE TABLE IF NOT EXISTS transaksi_kembali (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transaksi_sewa_id INT NOT NULL UNIQUE,
    tanggal_kembali_aktual DATE NOT NULL,
    kondisi_mobil ENUM('Baik','Ada Kerusakan') DEFAULT 'Baik',
    keterangan_kerusakan TEXT,
    biaya_kerusakan DECIMAL(12,0) DEFAULT 0,
    denda_keterlambatan DECIMAL(12,0) DEFAULT 0,
    total_bayar DECIMAL(12,0) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (transaksi_sewa_id) REFERENCES transaksi_sewa(id)
);

-- Tabel User (login)
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nama_lengkap VARCHAR(100),
    role ENUM('admin','operator') DEFAULT 'admin',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Data default user: admin / admin123
INSERT INTO users (username, password, nama_lengkap, role) VALUES
('admin', MD5('admin123'), 'Fleet Admin', 'admin');

-- Data sample mobil
INSERT INTO mobil (kode_mobil, nama_mobil, merk, tahun, warna, no_plat, kapasitas, harga_sewa, status) VALUES
('MBL-001', 'Toyota Avanza', 'Toyota', 2023, 'Putih', 'B 1234 ABC', 7, 350000, 'Tersedia'),
('MBL-002', 'Honda Brio', 'Honda', 2022, 'Merah', 'B 5678 DEF', 5, 250000, 'Tersedia'),
('MBL-003', 'Mitsubishi Xpander', 'Mitsubishi', 2023, 'Abu-abu', 'B 9999 GHI', 7, 450000, 'Tersedia'),
('MBL-004', 'Toyota Innova', 'Toyota', 2022, 'Hitam', 'B 2233 JKL', 8, 500000, 'Tersedia');

-- Data sample customer
INSERT INTO customer (kode_customer, nama_lengkap, no_ktp, tempat_lahir, tanggal_lahir, jenis_kelamin, no_telepon, email, alamat) VALUES
('CST-001', 'Budi Santoso', '3275012345670001', 'Jakarta', '1990-05-15', 'Laki-laki', '0812-3456-7890', 'budi@email.com', 'Jl. Sudirman No. 45, Jakarta Selatan'),
('CST-002', 'Siti Aminah', '3275019876540002', 'Bogor', '1995-08-20', 'Perempuan', '0856-9876-5432', 'siti@email.com', 'Perum Citra Indah Blok 01/12, Bogor'),
('CST-003', 'Andi Wijaya', '3172024455660003', 'Tangerang', '1988-03-10', 'Laki-laki', '0877-1122-3344', 'andi@email.com', 'Jl. Melati No. 8, Tangerang Kota');
