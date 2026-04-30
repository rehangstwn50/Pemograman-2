package aplikasipenjualan.model;

public class Barang {
    private String kodeBarang;
    private String namaBarang;
    private String kategori;
    private String satuan;
    private double hargaBeli;
    private double hargaJual;
    private int    stok;
    private int    stokMinimum;
    private String kodeSupplier;
    private String namaSupplier;

    public Barang() {}

    public Barang(String kodeBarang, String namaBarang, String kategori,
                  String satuan, double hargaBeli, double hargaJual,
                  int stok, int stokMinimum, String kodeSupplier) {
        this.kodeBarang   = kodeBarang;
        this.namaBarang   = namaBarang;
        this.kategori     = kategori;
        this.satuan       = satuan;
        this.hargaBeli    = hargaBeli;
        this.hargaJual    = hargaJual;
        this.stok         = stok;
        this.stokMinimum  = stokMinimum;
        this.kodeSupplier = kodeSupplier;
    }

    // ===== GETTER & SETTER =====
    public String getKodeBarang()            { return kodeBarang; }
    public void   setKodeBarang(String v)    { this.kodeBarang = v; }

    public String getNamaBarang()            { return namaBarang; }
    public void   setNamaBarang(String v)    { this.namaBarang = v; }

    public String getKategori()              { return kategori; }
    public void   setKategori(String v)      { this.kategori = v; }

    public String getSatuan()                { return satuan; }
    public void   setSatuan(String v)        { this.satuan = v; }

    public double getHargaBeli()             { return hargaBeli; }
    public void   setHargaBeli(double v)     { this.hargaBeli = v; }

    public double getHargaJual()             { return hargaJual; }
    public void   setHargaJual(double v)     { this.hargaJual = v; }

    public int    getStok()                  { return stok; }
    public void   setStok(int v)             { this.stok = v; }

    public int    getStokMinimum()           { return stokMinimum; }
    public void   setStokMinimum(int v)      { this.stokMinimum = v; }

    public String getKodeSupplier()          { return kodeSupplier; }
    public void   setKodeSupplier(String v)  { this.kodeSupplier = v; }

    public String getNamaSupplier()          { return namaSupplier; }
    public void   setNamaSupplier(String v)  { this.namaSupplier = v; }

    @Override
    public String toString() { return namaBarang; }
}