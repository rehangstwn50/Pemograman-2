package model;

public class Nilai {
    private int id, semester, sks;
    private String nim, namaMahasiswa, kelas, kodeMk, namaMk, huruf, status;
    private double tugas, uts, uas, nilaiAkhir;

    public Nilai() {}

    public int getId() { return id; }
    public int getSemester() { return semester; }
    public int getSks() { return sks; }
    public String getNim() { return nim; }
    public String getNamaMahasiswa() { return namaMahasiswa; }
    public String getKelas() { return kelas; }
    public String getKodeMk() { return kodeMk; }
    public String getNamaMk() { return namaMk; }
    public String getHuruf() { return huruf; }
    public String getStatus() { return status; }
    public double getTugas() { return tugas; }
    public double getUts() { return uts; }
    public double getUas() { return uas; }
    public double getNilaiAkhir() { return nilaiAkhir; }

    public void setId(int id) { this.id = id; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setSks(int sks) { this.sks = sks; }
    public void setNim(String nim) { this.nim = nim; }
    public void setNamaMahasiswa(String n) { this.namaMahasiswa = n; }
    public void setKelas(String kelas) { this.kelas = kelas; }
    public void setKodeMk(String kodeMk) { this.kodeMk = kodeMk; }
    public void setNamaMk(String namaMk) { this.namaMk = namaMk; }
    public void setHuruf(String huruf) { this.huruf = huruf; }
    public void setStatus(String status) { this.status = status; }
    public void setTugas(double tugas) { this.tugas = tugas; }
    public void setUts(double uts) { this.uts = uts; }
    public void setUas(double uas) { this.uas = uas; }
    public void setNilaiAkhir(double nilaiAkhir) { this.nilaiAkhir = nilaiAkhir; }
}
