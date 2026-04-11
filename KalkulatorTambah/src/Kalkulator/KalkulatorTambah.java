package Kalkulator;

import javax.swing.*;
import java.awt.event.*;

/**
 * Proyek: Kalkulator Penjumlahan Sederhana
 * Target: Java Swing GUI
 */
public class KalkulatorTambah extends JFrame {
    
    // 1. Deklarasi Komponen (Global agar bisa diakses di semua method)
    private final JLabel lblAngka1;
    private final JLabel lblAngka2;
    private final JLabel lblHasil;
    private JTextField txtAngka1, txtAngka2, txtHasil;
    private final JButton btnTambah;
    private final JButton btnHapus;
    private final JButton btnExit;

    public KalkulatorTambah() {
        // 2. Pengaturan Frame (Jendela Aplikasi)
        setTitle("Aplikasi Pertambahan Angka");
        setSize(400, 300);
        setLayout(null); // Menggunakan koordinat manual
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Agar muncul di tengah layar

        // 3. Inisialisasi & Posisi Label
        lblAngka1 = new JLabel("Angka Pertama");
        lblAngka1.setBounds(30, 30, 120, 25);
        add(lblAngka1);

        lblAngka2 = new JLabel("Angka Kedua");
        lblAngka2.setBounds(30, 70, 120, 25);
        add(lblAngka2);

        lblHasil = new JLabel("Hasil");
        lblHasil.setBounds(30, 130, 120, 25);
        add(lblHasil);

        // 4. Inisialisasi & Posisi Kotak Input (TextField)
        txtAngka1 = new JTextField();
        txtAngka1.setBounds(150, 30, 150, 25);
        add(txtAngka1);

        txtAngka2 = new JTextField();
        txtAngka2.setBounds(150, 70, 150, 25);
        add(txtAngka2);

        txtHasil = new JTextField();
        txtHasil.setBounds(150, 130, 150, 25);
        txtHasil.setEditable(false); // Supaya hasil tidak bisa diedit manual
        add(txtHasil);

        // 5. Inisialisasi & Posisi Tombol
        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(30, 200, 100, 30);
        add(btnTambah);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(140, 200, 100, 30);
        add(btnHapus);

        btnExit = new JButton("Exit");
        btnExit.setBounds(250, 200, 100, 30);
        add(btnExit);

        // --- 6. LOGIKA TOMBOL (Event Handling) ---

        // Aksi Tombol Tambah
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Mengambil teks dan mengubah ke tipe Double (angka)
                    double a1 = Double.parseDouble(txtAngka1.getText());
                    double a2 = Double.parseDouble(txtAngka2.getText());
                    double hasil = a1 + a2;
                    
                    // Menampilkan hasil ke txtHasil
                    txtHasil.setText(String.valueOf(hasil));
                } catch (NumberFormatException ex) {
                    // Jika input bukan angka, munculkan peringatan
                    JOptionPane.showMessageDialog(null, "Error: Masukkan angka yang valid!");
                }
            }
        });

        // Aksi Tombol Hapus
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtAngka1.setText("");
                txtAngka2.setText("");
                txtHasil.setText("");
                txtAngka1.requestFocus(); // Mengembalikan kursor ke kotak pertama
            }
        });

        // Aksi Tombol Exit
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Menutup aplikasi
            }
        });
    }

    // 7. Main Method (Pintu masuk program)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new KalkulatorTambah().setVisible(true);
        });
    }
}