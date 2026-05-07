package manajemenproduk;

public class Produk {
    private String nama;
    private String kategori;
    private double harga;
    private int stok;

    public Produk(String nama, String kategori, double harga, int stok) {
        this.nama = nama;
        this.kategori = kategori;
        this.harga = harga;
        this.stok = stok;
    }

    public String getNama()      { return nama; }
    public String getKategori()  { return kategori; }
    public double getHarga()     { return harga; }
    public int getStok()         { return stok; }

    public void setStok(int stok) { this.stok = stok; }

    @Override
    public String toString() {
        return nama + " | " + kategori + " | Rp" + harga + " | Stok: " + stok;
    }
}