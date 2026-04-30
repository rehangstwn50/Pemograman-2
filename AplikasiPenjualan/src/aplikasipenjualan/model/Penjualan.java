package aplikasipenjualan.model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Penjualan {
    private String          noNota;
    private Date            tanggal;
    private String          kodeCustomer;
    private String          namaCustomer;
    private double          subtotal;
    private double          diskonPersen;
    private double          diskonNominal;
    private double          totalBayar;
    private double          bayar;
    private double          kembalian;
    private String          status;
    private List<DetailPenjualan> details = new ArrayList<>();

    // Inner class untuk detail item
    public static class DetailPenjualan {
        public String kodeBarang;
        public String namaBarang;
        public double hargaSatuan;
        public int    qty;
        public double diskon;
        public double subtotal;

        public DetailPenjualan(String kodeBarang, String namaBarang,
                               double hargaSatuan, int qty,
                               double diskon, double subtotal) {
            this.kodeBarang  = kodeBarang;
            this.namaBarang  = namaBarang;
            this.hargaSatuan = hargaSatuan;
            this.qty         = qty;
            this.diskon      = diskon;
            this.subtotal    = subtotal;
        }
    }

    // Getter & Setter
    public String getNoNota()               { return noNota; }
    public void   setNoNota(String v)       { this.noNota = v; }

    public Date   getTanggal()              { return tanggal; }
    public void   setTanggal(Date v)        { this.tanggal = v; }

    public String getKodeCustomer()         { return kodeCustomer; }
    public void   setKodeCustomer(String v) { this.kodeCustomer = v; }

    public String getNamaCustomer()         { return namaCustomer; }
    public void   setNamaCustomer(String v) { this.namaCustomer = v; }

    public double getSubtotal()             { return subtotal; }
    public void   setSubtotal(double v)     { this.subtotal = v; }

    public double getDiskonPersen()         { return diskonPersen; }
    public void   setDiskonPersen(double v) { this.diskonPersen = v; }

    public double getDiskonNominal()        { return diskonNominal; }
    public void   setDiskonNominal(double v){ this.diskonNominal = v; }

    public double getTotalBayar()           { return totalBayar; }
    public void   setTotalBayar(double v)   { this.totalBayar = v; }

    public double getBayar()                { return bayar; }
    public void   setBayar(double v)        { this.bayar = v; }

    public double getKembalian()            { return kembalian; }
    public void   setKembalian(double v)    { this.kembalian = v; }

    public String getStatus()               { return status; }
    public void   setStatus(String v)       { this.status = v; }

    public List<DetailPenjualan> getDetails()              { return details; }
    public void addDetail(DetailPenjualan d)               { details.add(d); }
}