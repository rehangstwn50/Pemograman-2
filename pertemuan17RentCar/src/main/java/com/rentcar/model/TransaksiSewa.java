package com.rentcar.model;

public class TransaksiSewa {
    private int id;
    private String noTransaksi;
    private int customerId;
    private int mobilId;
    private String tanggalSewa;
    private String tanggalKembaliRencana;
    private int lamaSewa;
    private long totalBiaya;
    private long uangJaminan;
    private String catatan;
    private String status;
    // Join fields
    private String namaCustomer;
    private String namaMobil;
    private String noPlat;

    public TransaksiSewa() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNoTransaksi() { return noTransaksi; }
    public void setNoTransaksi(String noTransaksi) { this.noTransaksi = noTransaksi; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public int getMobilId() { return mobilId; }
    public void setMobilId(int mobilId) { this.mobilId = mobilId; }
    public String getTanggalSewa() { return tanggalSewa; }
    public void setTanggalSewa(String tanggalSewa) { this.tanggalSewa = tanggalSewa; }
    public String getTanggalKembaliRencana() { return tanggalKembaliRencana; }
    public void setTanggalKembaliRencana(String t) { this.tanggalKembaliRencana = t; }
    public int getLamaSewa() { return lamaSewa; }
    public void setLamaSewa(int lamaSewa) { this.lamaSewa = lamaSewa; }
    public long getTotalBiaya() { return totalBiaya; }
    public void setTotalBiaya(long totalBiaya) { this.totalBiaya = totalBiaya; }
    public long getUangJaminan() { return uangJaminan; }
    public void setUangJaminan(long uangJaminan) { this.uangJaminan = uangJaminan; }
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNamaCustomer() { return namaCustomer; }
    public void setNamaCustomer(String namaCustomer) { this.namaCustomer = namaCustomer; }
    public String getNamaMobil() { return namaMobil; }
    public void setNamaMobil(String namaMobil) { this.namaMobil = namaMobil; }
    public String getNoPlat() { return noPlat; }
    public void setNoPlat(String noPlat) { this.noPlat = noPlat; }
}
