package Latihan1;

import java.util.Scanner;

/**
 *
 * @author rehan
 */
public class Nilaimhs {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("run:");
        System.out.println("data:");

        // Input data
        System.out.print("nim: ");
        String nim = input.nextLine();

        System.out.print("nama: ");
        String nama = input.nextLine();

        System.out.print("nilai UTS: ");
        double uts = input.nextDouble();

        System.out.print("nilai UAS: ");
        double uas = input.nextDouble();

        // Hitung rata-rata
        double rata2 = (uts + uas) / 2;

        // Tentukan Grade
        String grade;
        if (rata2 >= 80) {
            grade = "A";
        } else if (rata2 >= 70) {
            grade = "B";
        } else if (rata2 >= 60) {
            grade = "C";
        } else if (rata2 >= 50) {
            grade = "D";
        } else {
            grade = "E";
        }

        // Tampilkan Output Tabel
        System.out.println("=============================================================");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s\n", "Nim", "Nama", "UTS", "UAS", "Rata2", "Grade");
        System.out.println("=============================================================");
        System.out.printf("%-10s %-10s %-10.1f %-10.1f %-10.1f %-10s\n", nim, nama, uts, uas, rata2, grade);
    }
}