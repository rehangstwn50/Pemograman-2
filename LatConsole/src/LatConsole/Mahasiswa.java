package LatConsole;

public class Mahasiswa {
    // Atribut
    public String nim;
    public String nama;
    public double uts;
    public double uas;

    // Method untuk menghitung rata-rata
    public double hitungRata2() {
        return (uts + uas) / 2;
    }

    // Method untuk menentukan grade
    public String tentukanGrade() {
        double rata = hitungRata2();
        if (rata >= 80) return "A";
        else if (rata >= 70) return "B";
        else if (rata >= 60) return "C";
        else if (rata >= 50) return "D";
        else return "E";
    }
}