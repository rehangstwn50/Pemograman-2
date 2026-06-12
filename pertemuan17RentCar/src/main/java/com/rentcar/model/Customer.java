package com.rentcar.model;

public class Customer {
    private int id;
    private String kodeCustomer;
    private String namaLengkap;
    private String noKtp;
    private String tempatLahir;
    private String tanggalLahir;
    private String jenisKelamin;
    private String noTelepon;
    private String email;
    private String alamat;

    public Customer() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getKodeCustomer() { return kodeCustomer; }
    public void setKodeCustomer(String kodeCustomer) { this.kodeCustomer = kodeCustomer; }
    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }
    public String getNoKtp() { return noKtp; }
    public void setNoKtp(String noKtp) { this.noKtp = noKtp; }
    public String getTempatLahir() { return tempatLahir; }
    public void setTempatLahir(String tempatLahir) { this.tempatLahir = tempatLahir; }
    public String getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(String tanggalLahir) { this.tanggalLahir = tanggalLahir; }
    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
}
