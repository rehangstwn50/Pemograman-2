package aplikasipenjualan.model;

public class Supplier {
    private String kodeSupplier;
    private String namaSupplier;
    private String alamat;
    private String telepon;
    private String email;
    private String contactPerson;

    public Supplier() {}

    public Supplier(String kodeSupplier, String namaSupplier,
                    String alamat, String telepon,
                    String email, String contactPerson) {
        this.kodeSupplier  = kodeSupplier;
        this.namaSupplier  = namaSupplier;
        this.alamat        = alamat;
        this.telepon       = telepon;
        this.email         = email;
        this.contactPerson = contactPerson;
    }

    public String getKodeSupplier()            { return kodeSupplier; }
    public void   setKodeSupplier(String v)    { this.kodeSupplier = v; }

    public String getNamaSupplier()            { return namaSupplier; }
    public void   setNamaSupplier(String v)    { this.namaSupplier = v; }

    public String getAlamat()                  { return alamat; }
    public void   setAlamat(String v)          { this.alamat = v; }

    public String getTelepon()                 { return telepon; }
    public void   setTelepon(String v)         { this.telepon = v; }

    public String getEmail()                   { return email; }
    public void   setEmail(String v)           { this.email = v; }

    public String getContactPerson()           { return contactPerson; }
    public void   setContactPerson(String v)   { this.contactPerson = v; }

    @Override
    public String toString() { return namaSupplier; }
}