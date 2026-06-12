package com.rentcar.model;

public class Mobil {
    private int id;
    private String kodeMobil;
    private String namaMobil;
    private String merk;
    private int tahun;
    private String warna;
    private String noPlat;
    private int kapasitas;
    private long hargaSewa;
    private String status;

    public Mobil() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getKodeMobil() { return kodeMobil; }
    public void setKodeMobil(String kodeMobil) { this.kodeMobil = kodeMobil; }
    public String getNamaMobil() { return namaMobil; }
    public void setNamaMobil(String namaMobil) { this.namaMobil = namaMobil; }
    public String getMerk() { return merk; }
    public void setMerk(String merk) { this.merk = merk; }
    public int getTahun() { return tahun; }
    public void setTahun(int tahun) { this.tahun = tahun; }
    public String getWarna() { return warna; }
    public void setWarna(String warna) { this.warna = warna; }
    public String getNoPlat() { return noPlat; }
    public void setNoPlat(String noPlat) { this.noPlat = noPlat; }
    public int getKapasitas() { return kapasitas; }
    public void setKapasitas(int kapasitas) { this.kapasitas = kapasitas; }
    public long getHargaSewa() { return hargaSewa; }
    public void setHargaSewa(long hargaSewa) { this.hargaSewa = hargaSewa; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
