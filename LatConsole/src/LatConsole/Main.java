package LatConsole;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Membuat objek dari class Mahasiswa
        Mahasiswa mhs = new Mahasiswa();

        System.out.println("run:");
        System.out.println("data:");

        // Input data ke dalam atribut objek mhs
        System.out.print("nim: ");
        mhs.nim = input.nextLine();

        System.out.print("nama: ");
        mhs.nama = input.nextLine();

        System.out.print("nilai UTS: ");
        mhs.uts = input.nextDouble();

        System.out.print("nilai UAS: ");
        mhs.uas = input.nextDouble();

        // Tampilkan Output Tabel
        System.out.println("\n=============================================================");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s\n", "Nim", "Nama", "UTS", "UAS", "Rata2", "Grade");
        System.out.println("=============================================================");
        
        System.out.printf("%-10s %-10s %-10.1f %-10.1f %-10.1f %-10s\n", 
                mhs.nim, 
                mhs.nama, 
                mhs.uts, 
                mhs.uas, 
                mhs.hitungRata2(), 
                mhs.tentukanGrade());
        
        System.out.println("\nBUILD SUCCESSFUL");
    }
}