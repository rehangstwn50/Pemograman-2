package aplikasipenjualan.form;

import aplikasipenjualan.koneksi.KoneksiDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

public class FormBarang extends JFrame {

    // ===== KOMPONEN UI =====
    private JTextField  txtKode, txtNama, txtHargaBeli, txtHargaJual, txtCari;
    private JComboBox<String> cmbKategori, cmbSatuan, cmbSupplier;
    private JSpinner    spnStok, spnStokMin;
    private JTextArea   txtDeskripsi;
    private JTable      tblBarang;
    private DefaultTableModel tblModel;
    private JButton     btnSimpan, btnHapus, btnBaru;

    private String[] kodeSupplierList; // menyimpan kode supplier sesuai index combo

    public FormBarang() {
        initComponents();
        loadSupplier();
        loadData();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Master Data Barang");
        setSize(900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // ===== PANEL FORM (KIRI) =====
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Input Data Barang"));
        pnlForm.setPreferredSize(new Dimension(320, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(4, 4, 4, 4);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.anchor  = GridBagConstraints.WEST;

        // Baris form
        String[] labels = {"Kode Barang *","Nama Barang *","Kategori",
                           "Satuan","Harga Beli *","Harga Jual *",
                           "Stok Awal","Stok Minimum","Supplier","Deskripsi"};
        int row = 0;
        for (String lbl : labels) {
            gbc.gridx=0; gbc.gridy=row; gbc.weightx=0;
            pnlForm.add(new JLabel(lbl+":"), gbc);
            row++;
        }

        // Input fields
        txtKode      = new JTextField(15);
        txtNama      = new JTextField(15);
        cmbKategori  = new JComboBox<>(new String[]{"Elektronik","Aksesoris","ATK","Makanan","Minuman","Lainnya"});
        cmbSatuan    = new JComboBox<>(new String[]{"pcs","unit","rim","lusin","kg","liter","box","dus"});
        txtHargaBeli = new JTextField("0");
        txtHargaJual = new JTextField("0");
        spnStok      = new JSpinner(new SpinnerNumberModel(0,0,999999,1));
        spnStokMin   = new JSpinner(new SpinnerNumberModel(5,0,999,1));
        cmbSupplier  = new JComboBox<>();
        txtDeskripsi = new JTextArea(3,15);
        txtDeskripsi.setLineWrap(true);
        JScrollPane spDesc = new JScrollPane(txtDeskripsi);

        Component[] fields = {txtKode, txtNama, cmbKategori, cmbSatuan,
                              txtHargaBeli, txtHargaJual, spnStok, spnStokMin,
                              cmbSupplier, spDesc};
        for (int i = 0; i < fields.length; i++) {
            gbc.gridx=1; gbc.gridy=i; gbc.weightx=1;
            pnlForm.add(fields[i], gbc);
        }

        // Tombol
        JPanel pnlBtn = new JPanel(new FlowLayout());
        btnSimpan = new JButton("💾 Simpan");
        btnHapus  = new JButton("🗑 Hapus");
        btnBaru   = new JButton("➕ Baru");
        btnSimpan.setBackground(new Color(46,125,110));
        btnSimpan.setForeground(Color.WHITE);
        btnHapus.setBackground(new Color(200,50,50));
        btnHapus.setForeground(Color.WHITE);
        pnlBtn.add(btnSimpan);
        pnlBtn.add(btnHapus);
        pnlBtn.add(btnBaru);

        gbc.gridx=0; gbc.gridy=labels.length; gbc.gridwidth=2;
        pnlForm.add(pnlBtn, gbc);

        // ===== PANEL TABEL (KANAN) =====
        String[] kolom = {"Kode","Nama Barang","Kategori","Harga Jual","Stok","Supplier"};
        tblModel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblBarang = new JTable(tblModel);
        tblBarang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblBarang.getTableHeader().setReorderingAllowed(false);
        tblBarang.setRowHeight(22);

        JPanel pnlTabel = new JPanel(new BorderLayout(3,3));
        pnlTabel.setBorder(BorderFactory.createTitledBorder("Data Barang"));

        // Cari
        JPanel pnlCari = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtCari = new JTextField(20);
        pnlCari.add(new JLabel("Cari:"));
        pnlCari.add(txtCari);
        pnlTabel.add(pnlCari, BorderLayout.NORTH);
        pnlTabel.add(new JScrollPane(tblBarang), BorderLayout.CENTER);

        add(pnlForm,  BorderLayout.WEST);
        add(pnlTabel, BorderLayout.CENTER);

        // ===== EVENTS =====
        btnSimpan.addActionListener(e -> simpanBarang());
        btnHapus.addActionListener(e  -> hapusBarang());
        btnBaru.addActionListener(e   -> clearForm());

        tblBarang.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { isiFormDariTabel(); }
        });

        txtCari.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) { cariBarang(); }
        });
    }

    // ===== LOAD SUPPLIER KE COMBO =====
    private void loadSupplier() {
        cmbSupplier.removeAllItems();
        cmbSupplier.addItem("-- Tanpa Supplier --");
        java.util.List<String> kodeList = new java.util.ArrayList<>();
        kodeList.add(null);

        String sql = "SELECT kode_supplier, nama_supplier FROM supplier ORDER BY nama_supplier";
        try (Connection con = KoneksiDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cmbSupplier.addItem(rs.getString("nama_supplier"));
                kodeList.add(rs.getString("kode_supplier"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load supplier: "+e.getMessage());
        }
        kodeSupplierList = kodeList.toArray(new String[0]);
    }

    // ===== LOAD DATA KE TABEL =====
    private void loadData() {
        tblModel.setRowCount(0);
        String sql = "SELECT b.kode_barang, b.nama_barang, b.kategori,"
                   + " b.harga_jual, b.stok, COALESCE(s.nama_supplier,'-') as supplier"
                   + " FROM barang b"
                   + " LEFT JOIN supplier s ON b.kode_supplier=s.kode_supplier"
                   + " ORDER BY b.nama_barang";
        try (Connection con = KoneksiDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("id","ID"));
            while (rs.next()) {
                tblModel.addRow(new Object[]{
                    rs.getString("kode_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori"),
                    "Rp " + nf.format(rs.getDouble("harga_jual")),
                    rs.getInt("stok"),
                    rs.getString("supplier")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load data: "+e.getMessage());
        }
    }

    // ===== CARI BARANG =====
    private void cariBarang() {
        String keyword = txtCari.getText().trim().toLowerCase();
        tblModel.setRowCount(0);
        String sql = "SELECT b.kode_barang, b.nama_barang, b.kategori,"
                   + " b.harga_jual, b.stok, COALESCE(s.nama_supplier,'-') as supplier"
                   + " FROM barang b"
                   + " LEFT JOIN supplier s ON b.kode_supplier=s.kode_supplier"
                   + " WHERE LOWER(b.kode_barang) LIKE ? OR LOWER(b.nama_barang) LIKE ?"
                   + " ORDER BY b.nama_barang";
        try (Connection con = KoneksiDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%"+keyword+"%");
            ps.setString(2, "%"+keyword+"%");
            ResultSet rs = ps.executeQuery();
            NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("id","ID"));
            while (rs.next()) {
                tblModel.addRow(new Object[]{
                    rs.getString(1), rs.getString(2), rs.getString(3),
                    "Rp "+nf.format(rs.getDouble(4)), rs.getInt(5), rs.getString(6)
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ===== SIMPAN / UPDATE BARANG =====
    private void simpanBarang() {
        String kode = txtKode.getText().trim();
        String nama = txtNama.getText().trim();
        if (kode.isEmpty() || nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kode dan Nama Barang wajib diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double hBeli, hJual;
        try {
            hBeli = Double.parseDouble(txtHargaBeli.getText().replaceAll("[^0-9]",""));
            hJual = Double.parseDouble(txtHargaJual.getText().replaceAll("[^0-9]",""));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!");
            return;
        }

        int stok     = (int) spnStok.getValue();
        int stokMin  = (int) spnStokMin.getValue();
        String supp  = kodeSupplierList[cmbSupplier.getSelectedIndex()];
        String kat   = cmbKategori.getSelectedItem().toString();
        String sat   = cmbSatuan.getSelectedItem().toString();
        String desk  = txtDeskripsi.getText().trim();

        try (Connection con = KoneksiDB.getConnection()) {
            // Cek apakah kode sudah ada
            PreparedStatement cek = con.prepareStatement(
                "SELECT kode_barang FROM barang WHERE kode_barang=?");
            cek.setString(1, kode);
            boolean ada = cek.executeQuery().next();

            String sql;
            if (ada) {
                sql = "UPDATE barang SET nama_barang=?, kategori=?, satuan=?,"
                    + " harga_beli=?, harga_jual=?, stok_minimum=?,"
                    + " kode_supplier=?, deskripsi=? WHERE kode_barang=?";
            } else {
                sql = "INSERT INTO barang (kode_barang,nama_barang,kategori,satuan,"
                    + " harga_beli,harga_jual,stok,stok_minimum,kode_supplier,deskripsi)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?)";
            }

            PreparedStatement ps = con.prepareStatement(sql);
            if (ada) {
                ps.setString(1, nama); ps.setString(2, kat);
                ps.setString(3, sat);  ps.setDouble(4, hBeli);
                ps.setDouble(5, hJual);ps.setInt(6, stokMin);
                ps.setString(7, supp); ps.setString(8, desk);
                ps.setString(9, kode);
            } else {
                ps.setString(1, kode); ps.setString(2, nama);
                ps.setString(3, kat);  ps.setString(4, sat);
                ps.setDouble(5, hBeli);ps.setDouble(6, hJual);
                ps.setInt(7, stok);    ps.setInt(8, stokMin);
                ps.setString(9, supp); ps.setString(10, desk);
            }
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this,
                ada ? "Data berhasil diupdate!" : "Data berhasil disimpan!");
            loadData();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan: "+e.getMessage());
        }
    }

    // ===== HAPUS BARANG =====
    private void hapusBarang() {
        int row = tblBarang.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih barang yang ingin dihapus!");
            return;
        }
        String kode = tblModel.getValueAt(row, 0).toString();
        int opt = JOptionPane.showConfirmDialog(this,
            "Hapus barang " + kode + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;

        try (Connection con = KoneksiDB.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "DELETE FROM barang WHERE kode_barang=?")) {
            ps.setString(1, kode);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            loadData();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal hapus: "+e.getMessage());
        }
    }

    // ===== ISI FORM DARI KLIK TABEL =====
    private void isiFormDariTabel() {
        int row = tblBarang.getSelectedRow();
        if (row < 0) return;
        String kode = tblModel.getValueAt(row, 0).toString();

        String sql = "SELECT * FROM barang WHERE kode_barang=?";
        try (Connection con = KoneksiDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, kode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtKode.setText(rs.getString("kode_barang"));
                txtKode.setEditable(false);
                txtNama.setText(rs.getString("nama_barang"));
                cmbKategori.setSelectedItem(rs.getString("kategori"));
                cmbSatuan.setSelectedItem(rs.getString("satuan"));
                txtHargaBeli.setText(String.valueOf((long)rs.getDouble("harga_beli")));
                txtHargaJual.setText(String.valueOf((long)rs.getDouble("harga_jual")));
                spnStok.setValue(rs.getInt("stok"));
                spnStokMin.setValue(rs.getInt("stok_minimum"));
                txtDeskripsi.setText(rs.getString("deskripsi"));

                // Set supplier di combo
                String ks = rs.getString("kode_supplier");
                for (int i = 0; i < kodeSupplierList.length; i++) {
                    if (ks != null && ks.equals(kodeSupplierList[i])) {
                        cmbSupplier.setSelectedIndex(i); break;
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ===== CLEAR FORM =====
    private void clearForm() {
        txtKode.setText(""); txtKode.setEditable(true);
        txtNama.setText(""); txtHargaBeli.setText("0");
        txtHargaJual.setText("0"); txtDeskripsi.setText("");
        spnStok.setValue(0); spnStokMin.setValue(5);
        cmbKategori.setSelectedIndex(0);
        cmbSatuan.setSelectedIndex(0);
        cmbSupplier.setSelectedIndex(0);
        tblBarang.clearSelection();
        txtKode.requestFocus();
    }
}