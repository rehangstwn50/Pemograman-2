CREATE DATABASE IF NOT EXISTS db_unpam;
USE db_unpam;

DROP TABLE IF EXISTS nilai;
DROP TABLE IF EXISTS mahasiswa;
DROP TABLE IF EXISTS mata_kuliah;

CREATE TABLE mahasiswa (
    nim VARCHAR(20) PRIMARY KEY,
    nama VARCHAR(100),
    semester INT DEFAULT 1,
    kelas VARCHAR(20),
    jurusan VARCHAR(100)
);

CREATE TABLE mata_kuliah (
    kode_mk VARCHAR(10) PRIMARY KEY,
    nama_mk VARCHAR(100),
    sks INT
);

CREATE TABLE nilai (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nim VARCHAR(20),
    kode_mk VARCHAR(10),
    tugas DECIMAL(5,1) DEFAULT 0,
    uts DECIMAL(5,1) DEFAULT 0,
    uas DECIMAL(5,1) DEFAULT 0,
    nilai_akhir DECIMAL(5,1) DEFAULT 0,
    huruf VARCHAR(2),
    status VARCHAR(15)
);
