package aplikasipenjualan.model;

public class Customer {
    private String kodeCustomer;
    private String namaCustomer;
    private String alamat;
    private String telepon;
    private String email;
    private int    poin;

    public Customer() {}

    public Customer(String kodeCustomer, String namaCustomer,
                    String alamat, String telepon, String email) {
        this.kodeCustomer = kodeCustomer;
        this.namaCustomer = namaCustomer;
        this.alamat       = alamat;
        this.telepon      = telepon;
        this.email        = email;
    }

    public String getKodeCustomer()           { return kodeCustomer; }
    public void   setKodeCustomer(String v)   { this.kodeCustomer = v; }

    public String getNamaCustomer()           { return namaCustomer; }
    public void   setNamaCustomer(String v)   { this.namaCustomer = v; }

    public String getAlamat()                 { return alamat; }
    public void   setAlamat(String v)         { this.alamat = v; }

    public String getTelepon()                { return telepon; }
    public void   setTelepon(String v)        { this.telepon = v; }

    public String getEmail()                  { return email; }
    public void   setEmail(String v)          { this.email = v; }

    public int    getPoin()                   { return poin; }
    public void   setPoin(int v)              { this.poin = v; }

    @Override
    public String toString() { return namaCustomer; }
}