package model;

public class Mahasiswa {
    private String nim, nama, kelas, jurusan;
    private int semester;

    public Mahasiswa() {}
    public Mahasiswa(String nim, String nama, int semester, String kelas, String jurusan) {
        this.nim=nim; this.nama=nama; this.semester=semester; this.kelas=kelas; this.jurusan=jurusan;
    }

    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public int getSemester() { return semester; }
    public String getKelas() { return kelas; }
    public String getJurusan() { return jurusan; }
    public void setNim(String nim) { this.nim = nim; }
    public void setNama(String nama) { this.nama = nama; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setKelas(String kelas) { this.kelas = kelas; }
    public void setJurusan(String jurusan) { this.jurusan = jurusan; }
}
