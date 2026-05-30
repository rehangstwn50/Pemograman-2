package model;

public class MataKuliah {
    private String kodeMk, namaMk;
    private int sks;

    public MataKuliah() {}
    public MataKuliah(String kodeMk, String namaMk, int sks) {
        this.kodeMk = kodeMk; this.namaMk = namaMk; this.sks = sks;
    }

    public String getKodeMk() { return kodeMk; }
    public String getNamaMk() { return namaMk; }
    public int getSks() { return sks; }
    public void setKodeMk(String kodeMk) { this.kodeMk = kodeMk; }
    public void setNamaMk(String namaMk) { this.namaMk = namaMk; }
    public void setSks(int sks) { this.sks = sks; }
}
