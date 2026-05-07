package manajemenproduk;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ManajemenProdukGUI extends JFrame {

    private static final Logger logger = Logger.getLogger(ManajemenProdukGUI.class.getName());

    private StackProduk stackProduk = new StackProduk();
    private DefaultTableModel tableModel;
    private JTable tabel;

    private JTextField txtNama, txtKategori, txtHarga, txtStok, txtCari;
    private JLabel lblStatus;

    public ManajemenProdukGUI() {
        setTitle("Aplikasi Manajemen Produk");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ===== PANEL INPUT =====
        JPanel panelInput = new JPanel(new GridLayout(5, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createTitledBorder("Input Produk"));

        panelInput.add(new JLabel("Nama Produk:"));
        txtNama = new JTextField();
        panelInput.add(txtNama);

        panelInput.add(new JLabel("Kategori:"));
        txtKategori = new JTextField();
        panelInput.add(txtKategori);

        panelInput.add(new JLabel("Harga (Rp):"));
        txtHarga = new JTextField();
        panelInput.add(txtHarga);

        panelInput.add(new JLabel("Stok:"));
        txtStok = new JTextField();
        panelInput.add(txtStok);

        JButton btnTambah = new JButton("Tambah Produk");
        JButton btnHapus = new JButton("Hapus Produk (Pop)");
        btnTambah.setBackground(new Color(70, 130, 180));
        btnTambah.setForeground(Color.WHITE);
        btnHapus.setBackground(new Color(220, 80, 60));
        btnHapus.setForeground(Color.WHITE);
        panelInput.add(btnTambah);
        panelInput.add(btnHapus);

        // ===== PANEL TABEL =====
        String[] kolom = {"Nama", "Kategori", "Harga (Rp)", "Stok"};
        tableModel = new DefaultTableModel(kolom, 0);
        tabel = new JTable(tableModel);
        tabel.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(tabel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Produk (Stack)"));

        // ===== PANEL BAWAH (Search + Sort) =====
        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelBawah.setBorder(BorderFactory.createTitledBorder("Pencarian & Sorting"));

        panelBawah.add(new JLabel("Cari Nama:"));
        txtCari = new JTextField(12);
        panelBawah.add(txtCari);

        JButton btnCari = new JButton("Cari");
        JButton btnSortHarga = new JButton("Sort by Harga");
        JButton btnSortKategori = new JButton("Sort by Kategori");
        JButton btnReset = new JButton("Tampilkan Semua");

        panelBawah.add(btnCari);
        panelBawah.add(btnSortHarga);
        panelBawah.add(btnSortKategori);
        panelBawah.add(btnReset);

        // ===== STATUS BAR =====
        lblStatus = new JLabel("Selamat datang di Aplikasi Manajemen Produk.");
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblStatus.setForeground(new Color(50, 100, 50));

        // ===== LAYOUT UTAMA =====
        add(panelInput, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBawah, BorderLayout.SOUTH);
        add(lblStatus, BorderLayout.PAGE_END);

        // ===== DATA CONTOH =====
        stackProduk.push(new Produk("Beras 5kg", "Sembako", 65000, 10));
        stackProduk.push(new Produk("Minyak Goreng", "Sembako", 28000, 5));
        stackProduk.push(new Produk("Sabun Mandi", "Kebersihan", 5000, 20));
        refreshTabel(stackProduk.getAll());

        // ===== ACTION LISTENERS =====

        btnTambah.addActionListener(e -> {
            try {
                String nama = txtNama.getText().trim();
                String kategori = txtKategori.getText().trim();
                String hargaStr = txtHarga.getText().trim();
                String stokStr = txtStok.getText().trim();

                if (nama.isEmpty() || kategori.isEmpty() || hargaStr.isEmpty() || stokStr.isEmpty()) {
                    throw new Exception("Semua field wajib diisi!");
                }

                double harga = Double.parseDouble(hargaStr);
                int stok = Integer.parseInt(stokStr);

                if (harga < 0 || stok < 0) {
                    throw new Exception("Harga dan stok tidak boleh negatif!");
                }

                Produk p = new Produk(nama, kategori, harga, stok);
                stackProduk.push(p);
                refreshTabel(stackProduk.getAll());
                setStatus("Produk '" + nama + "' berhasil ditambahkan.", true);
                clearInput();

            } catch (NumberFormatException ex) {
                setStatus("Error: Harga dan Stok harus berupa angka!", false);
                logger.log(Level.WARNING, "Format angka salah: {0}", ex.getMessage());
            } catch (Exception ex) {
                setStatus("Error: " + ex.getMessage(), false);
                logger.log(Level.WARNING, ex.getMessage());
            }
        });

        btnHapus.addActionListener(e -> {
            try {
                Produk removed = stackProduk.pop();
                refreshTabel(stackProduk.getAll());
                setStatus("Produk '" + removed.getNama() + "' berhasil dihapus dari stack.", true);
            } catch (Exception ex) {
                setStatus("Error: " + ex.getMessage(), false);
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Peringatan", JOptionPane.WARNING_MESSAGE);
                logger.log(Level.WARNING, ex.getMessage());
            }
        });

        btnCari.addActionListener(e -> {
            String keyword = txtCari.getText().trim();
            if (keyword.isEmpty()) {
                setStatus("Masukkan nama produk untuk dicari.", false);
                return;
            }
            Produk hasil = stackProduk.cariByNama(keyword);
            if (hasil != null) {
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{
                    hasil.getNama(), hasil.getKategori(), hasil.getHarga(), hasil.getStok()
                });
                setStatus("Produk '" + keyword + "' ditemukan!", true);
            } else {
                setStatus("Produk '" + keyword + "' tidak ditemukan.", false);
            }
        });

        btnSortHarga.addActionListener(e -> {
            refreshTabel(stackProduk.sortByHarga());
            setStatus("Produk diurutkan berdasarkan harga.", true);
        });

        btnSortKategori.addActionListener(e -> {
            refreshTabel(stackProduk.sortByKategori());
            setStatus("Produk diurutkan berdasarkan kategori.", true);
        });

        btnReset.addActionListener(e -> {
            refreshTabel(stackProduk.getAll());
            setStatus("Menampilkan semua produk.", true);
        });
    }

    // Refresh isi tabel
    private void refreshTabel(LinkedList<Produk> list) {
        tableModel.setRowCount(0);
        for (Produk p : list) {
            tableModel.addRow(new Object[]{
                p.getNama(), p.getKategori(), p.getHarga(), p.getStok()
            });
        }
    }

    // Set pesan status
    private void setStatus(String pesan, boolean sukses) {
        lblStatus.setText(pesan);
        lblStatus.setForeground(sukses ? new Color(30, 120, 30) : new Color(180, 30, 30));
    }

    // Kosongkan input field
    private void clearInput() {
        txtNama.setText("");
        txtKategori.setText("");
        txtHarga.setText("");
        txtStok.setText("");
    }
}