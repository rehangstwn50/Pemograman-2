package aplikasipenjualan.form;

import aplikasipenjualan.koneksi.KoneksiDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class FormPenjualan extends JFrame {

    private JTextField  txtNoNota, txtCariBarang, txtNamaBarang;
    private JTextField  txtHargaSatuan, txtSubtotal, txtTotalBayar, txtBayar, txtKembalian;
    private JSpinner    spnQty;
    private JComboBox<String> cmbCustomer;
    private JLabel      lblTanggal, lblStok;
    private JTable      tblKeranjang;
    private DefaultTableModel tblModel;
    private JButton     btnTambah, btnHapusItem, btnSimpan, btnBatal, btnCari;

    private String[]    kodeCustomerList;
    private String      kodeBarangDipilih = "";
    private double      hargaBarangDipilih = 0;
    private double      totalBayarValue = 0;

    private NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("id","ID"));

    public FormPenjualan() {
        initComponents();
        loadCustomer();
        generateNoNota();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Form Transaksi Penjualan");
        setSize(1000, 620);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(5,5));

        // ===== PANEL ATAS (INFO NOTA) =====
        JPanel pnlAtas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlAtas.setBorder(BorderFactory.createTitledBorder("Informasi Nota"));
        txtNoNota = new JTextField(18); txtNoNota.setEditable(false);
        txtNoNota.setFont(new Font("Arial", Font.BOLD, 13));
        lblTanggal = new JLabel("Tanggal: " +
            new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        cmbCustomer = new JComboBox<>();
        pnlAtas.add(new JLabel("No Nota:")); pnlAtas.add(txtNoNota);
        pnlAtas.add(Box.createHorizontalStrut(20));
        pnlAtas.add(lblTanggal);
        pnlAtas.add(Box.createHorizontalStrut(20));
        pnlAtas.add(new JLabel("Customer:")); pnlAtas.add(cmbCustomer);
        add(pnlAtas, BorderLayout.NORTH);

        // ===== PANEL TENGAH =====
        JPanel pnlTengah = new JPanel(new BorderLayout(5,5));

        // Panel cari barang
        JPanel pnlBarang = new JPanel(new GridBagLayout());
        pnlBarang.setBorder(BorderFactory.createTitledBorder("Tambah Item"));
        pnlBarang.setPreferredSize(new Dimension(300, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        txtCariBarang  = new JTextField(10);
        btnCari        = new JButton("🔍 Cari");
        btnCari.setBackground(new Color(70,130,180));
        btnCari.setForeground(Color.WHITE);

        // Panel kode barang + tombol cari
        JPanel pnlKode = new JPanel(new BorderLayout(3,0));
        pnlKode.add(txtCariBarang, BorderLayout.CENTER);
        pnlKode.add(btnCari, BorderLayout.EAST);

        txtNamaBarang  = new JTextField(15); txtNamaBarang.setEditable(false);
        txtHargaSatuan = new JTextField(15); txtHargaSatuan.setEditable(false);
        lblStok        = new JLabel("Stok: -");
        spnQty         = new JSpinner(new SpinnerNumberModel(1,1,9999,1));
        txtSubtotal    = new JTextField(15); txtSubtotal.setEditable(false);
        btnTambah      = new JButton("➕ Tambah ke Keranjang");
        btnHapusItem   = new JButton("🗑 Hapus Item");
        btnTambah.setBackground(new Color(46,125,110)); btnTambah.setForeground(Color.WHITE);
        btnHapusItem.setBackground(new Color(200,50,50)); btnHapusItem.setForeground(Color.WHITE);

        String[]    lbl = {"Kode Barang *","Nama Barang","Harga Satuan","","Qty","Subtotal Item"};
        Component[] fld = {pnlKode, txtNamaBarang, txtHargaSatuan, lblStok, spnQty, txtSubtotal};

        for(int i = 0; i < lbl.length; i++){
            gbc.gridx=0; gbc.gridy=i; gbc.weightx=0;
            pnlBarang.add(new JLabel(lbl[i].isEmpty() ? "" : lbl[i]+" :"), gbc);
            gbc.gridx=1; gbc.weightx=1;
            pnlBarang.add(fld[i], gbc);
        }
        gbc.gridx=0; gbc.gridy=lbl.length; gbc.gridwidth=2;
        pnlBarang.add(btnTambah, gbc);
        gbc.gridy=lbl.length+1;
        pnlBarang.add(btnHapusItem, gbc);

        // Tabel keranjang
        String[] kolom = {"Kode","Nama Barang","Harga Satuan","Qty","Subtotal"};
        tblModel = new DefaultTableModel(kolom, 0){
            @Override public boolean isCellEditable(int r, int c){ return false; }
        };
        tblKeranjang = new JTable(tblModel);
        tblKeranjang.setRowHeight(22);

        pnlTengah.add(pnlBarang, BorderLayout.WEST);
        pnlTengah.add(new JScrollPane(tblKeranjang), BorderLayout.CENTER);
        add(pnlTengah, BorderLayout.CENTER);

        // ===== PANEL BAWAH (TOTAL & BAYAR) =====
        JPanel pnlBawah = new JPanel(new GridLayout(1,2,10,0));
        pnlBawah.setBorder(BorderFactory.createTitledBorder("Pembayaran"));

        JPanel pnlTotal = new JPanel(new GridLayout(3,2,5,5));
        txtTotalBayar = new JTextField("0"); txtTotalBayar.setEditable(false);
        txtTotalBayar.setFont(new Font("Arial",Font.BOLD,16));
        txtTotalBayar.setForeground(new Color(46,125,110));
        txtBayar     = new JTextField("0");
        txtKembalian = new JTextField("0"); txtKembalian.setEditable(false);
        pnlTotal.add(new JLabel("Total Bayar (Rp):")); pnlTotal.add(txtTotalBayar);
        pnlTotal.add(new JLabel("Uang Bayar (Rp):"));  pnlTotal.add(txtBayar);
        pnlTotal.add(new JLabel("Kembalian (Rp):"));   pnlTotal.add(txtKembalian);

        JPanel pnlAksi = new JPanel(new FlowLayout());
        btnSimpan = new JButton("✅ Simpan Transaksi");
        btnBatal  = new JButton("❌ Batal / Kosongkan");
        btnSimpan.setBackground(new Color(46,125,110)); btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFont(new Font("Arial",Font.BOLD,13));
        btnBatal.setBackground(new Color(150,150,150)); btnBatal.setForeground(Color.WHITE);
        pnlAksi.add(btnSimpan); pnlAksi.add(btnBatal);

        pnlBawah.add(pnlTotal); pnlBawah.add(pnlAksi);
        add(pnlBawah, BorderLayout.SOUTH);

        // ===== EVENTS =====
        // Tekan Enter di field kode barang
        txtCariBarang.addActionListener(e -> cariBarang());

        // Pindah fokus dari field kode barang → otomatis cari
        txtCariBarang.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!txtCariBarang.getText().trim().isEmpty()) {
                    cariBarang();
                }
            }
        });

        // Tombol cari
        btnCari.addActionListener(e -> cariBarang());

        spnQty.addChangeListener(e -> hitungSubtotalItem());
        btnTambah.addActionListener(e -> tambahKeKeranjang());
        btnHapusItem.addActionListener(e -> hapusItem());
        btnSimpan.addActionListener(e -> simpanTransaksi());
        btnBatal.addActionListener(e -> batalTransaksi());
        txtBayar.addKeyListener(new KeyAdapter(){
            @Override public void keyReleased(KeyEvent e){ hitungKembalian(); }
        });
    }

    // ===== LOAD CUSTOMER =====
    private void loadCustomer() {
        cmbCustomer.removeAllItems();
        cmbCustomer.addItem("-- Tanpa Customer (Tunai) --");
        java.util.List<String> kodeList = new java.util.ArrayList<>();
        kodeList.add(null);
        String sql = "SELECT kode_customer, nama_customer FROM customer ORDER BY nama_customer";
        try(Connection con = KoneksiDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                cmbCustomer.addItem(rs.getString("nama_customer"));
                kodeList.add(rs.getString("kode_customer"));
            }
        }catch(Exception e){ e.printStackTrace(); }
        kodeCustomerList = kodeList.toArray(new String[0]);
    }

    // ===== GENERATE NOMOR NOTA =====
    private void generateNoNota() {
        String tgl    = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String prefix = "NOTA-"+tgl+"-";
        String sql    = "SELECT COALESCE(MAX(CAST(SUBSTRING(no_nota,LENGTH(?)+1) AS UNSIGNED)),0)+1"
                      + " FROM penjualan WHERE no_nota LIKE CONCAT(?,'%')";
        try(Connection con = KoneksiDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, prefix); ps.setString(2, prefix);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int urut = rs.getInt(1);
                txtNoNota.setText(prefix + String.format("%04d", urut));
            }
        }catch(Exception e){
            txtNoNota.setText(prefix + "0001");
        }
    }

    // ===== CARI BARANG =====
    private void cariBarang() {
        String kw = txtCariBarang.getText().trim();
        if(kw.isEmpty()) return;

        // Hindari pencarian ulang jika barang sudah dipilih dengan kode yang sama
        if(kw.equals(kodeBarangDipilih)) return;

        String sql = "SELECT kode_barang, nama_barang, harga_jual, stok"
                   + " FROM barang WHERE kode_barang=? OR nama_barang LIKE ? LIMIT 1";
        try(Connection con = KoneksiDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, kw);
            ps.setString(2, "%"+kw+"%");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                kodeBarangDipilih  = rs.getString("kode_barang");
                hargaBarangDipilih = rs.getDouble("harga_jual");
                int stok           = rs.getInt("stok");
                txtNamaBarang.setText(rs.getString("nama_barang"));
                txtHargaSatuan.setText(nf.format(hargaBarangDipilih));
                lblStok.setText("Stok: " + stok);
                ((SpinnerNumberModel)spnQty.getModel()).setMaximum(stok);
                spnQty.setValue(1);
                hitungSubtotalItem();
                // Fokus ke qty setelah barang ditemukan
                spnQty.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Barang tidak ditemukan!", 
                    "Info", JOptionPane.INFORMATION_MESSAGE);
                txtCariBarang.selectAll();
                txtCariBarang.requestFocus();
            }
        }catch(Exception e){ e.printStackTrace(); }
    }

    // ===== HITUNG SUBTOTAL ITEM =====
    private void hitungSubtotalItem() {
        int qty    = (int) spnQty.getValue();
        double sub = hargaBarangDipilih * qty;
        txtSubtotal.setText(nf.format((long) sub));
    }

    // ===== TAMBAH KE KERANJANG =====
    private void tambahKeKeranjang() {
        if(kodeBarangDipilih.isEmpty()){
            JOptionPane.showMessageDialog(this, "Pilih barang dulu!");
            txtCariBarang.requestFocus();
            return;
        }
        int qty    = (int) spnQty.getValue();
        double sub = hargaBarangDipilih * qty;

        // Cek apakah barang sudah ada di keranjang, update qty-nya
        for(int i = 0; i < tblModel.getRowCount(); i++){
            if(tblModel.getValueAt(i,0).equals(kodeBarangDipilih)){
                int qtyLama    = Integer.parseInt(tblModel.getValueAt(i,3).toString());
                int qtyBaru    = qtyLama + qty;
                double subBaru = hargaBarangDipilih * qtyBaru;
                tblModel.setValueAt(qtyBaru, i, 3);
                tblModel.setValueAt(nf.format((long)subBaru), i, 4);
                hitungTotal();
                clearInputBarang();
                return;
            }
        }

        tblModel.addRow(new Object[]{
            kodeBarangDipilih,
            txtNamaBarang.getText(),
            nf.format((long) hargaBarangDipilih),
            qty,
            nf.format((long) sub)
        });
        hitungTotal();
        clearInputBarang();
    }

    // ===== HAPUS ITEM DARI KERANJANG =====
    private void hapusItem() {
        int row = tblKeranjang.getSelectedRow();
        if(row < 0){
            JOptionPane.showMessageDialog(this, "Pilih item dulu!");
            return;
        }
        tblModel.removeRow(row);
        hitungTotal();
    }

    // ===== HITUNG TOTAL =====
    private void hitungTotal() {
        totalBayarValue = 0;
        for(int i = 0; i < tblModel.getRowCount(); i++){
            String subStr = tblModel.getValueAt(i,4).toString().replaceAll("[^0-9]","");
            totalBayarValue += Double.parseDouble(subStr.isEmpty() ? "0" : subStr);
        }
        txtTotalBayar.setText(nf.format((long) totalBayarValue));
        hitungKembalian();
    }

    // ===== HITUNG KEMBALIAN =====
    private void hitungKembalian() {
        try{
            double bayar     = Double.parseDouble(txtBayar.getText().replaceAll("[^0-9]",""));
            double kembalian = bayar - totalBayarValue;
            txtKembalian.setText(nf.format((long) Math.max(0, kembalian)));
            txtKembalian.setForeground(kembalian < 0 ? Color.RED : new Color(46,125,110));
        }catch(Exception e){ txtKembalian.setText("0"); }
    }

    // ===== SIMPAN TRANSAKSI =====
    private void simpanTransaksi() {
        if(tblModel.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "Keranjang kosong!"); return;
        }
        double bayar;
        try{
            bayar = Double.parseDouble(txtBayar.getText().replaceAll("[^0-9]",""));
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Masukkan jumlah bayar!"); return;
        }
        if(bayar < totalBayarValue){
            JOptionPane.showMessageDialog(this, "Uang bayar kurang!", "Error",
                JOptionPane.ERROR_MESSAGE); return;
        }

        Connection con = KoneksiDB.getConnection();
        try{
            con.setAutoCommit(false);
            String noNota       = txtNoNota.getText();
            String kodeCustomer = kodeCustomerList[cmbCustomer.getSelectedIndex()];
            double kembalian    = bayar - totalBayarValue;
            String tgl          = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            // INSERT header penjualan
            String sqlH = "INSERT INTO penjualan(no_nota,tanggal,kode_customer,"
                        + "subtotal,diskon_persen,diskon_nominal,total_bayar,bayar,kembalian,status)"
                        + " VALUES(?,?,?,?,0,0,?,?,?,'lunas')";
            PreparedStatement psH = con.prepareStatement(sqlH);
            psH.setString(1, noNota); psH.setString(2, tgl);
            psH.setString(3, kodeCustomer);
            psH.setDouble(4, totalBayarValue); psH.setDouble(5, totalBayarValue);
            psH.setDouble(6, bayar); psH.setDouble(7, kembalian);
            psH.executeUpdate();

            // INSERT detail
            String sqlD = "INSERT INTO detail_penjualan"
                        + "(no_nota,kode_barang,nama_barang,harga_satuan,qty,diskon,subtotal)"
                        + " VALUES(?,?,?,?,?,0,?)";
            PreparedStatement psD = con.prepareStatement(sqlD);
            for(int i = 0; i < tblModel.getRowCount(); i++){
                double hrg = Double.parseDouble(
                    tblModel.getValueAt(i,2).toString().replaceAll("[^0-9]",""));
                int qty    = Integer.parseInt(tblModel.getValueAt(i,3).toString());
                double sub = Double.parseDouble(
                    tblModel.getValueAt(i,4).toString().replaceAll("[^0-9]",""));
                psD.setString(1, noNota);
                psD.setString(2, tblModel.getValueAt(i,0).toString());
                psD.setString(3, tblModel.getValueAt(i,1).toString());
                psD.setDouble(4, hrg); psD.setInt(5, qty); psD.setDouble(6, sub);
                psD.addBatch();
            }
            psD.executeBatch();
            con.commit();

            JOptionPane.showMessageDialog(this,
                "Transaksi berhasil!\nNo Nota: " + noNota
                + "\nTotal: Rp " + nf.format((long) totalBayarValue)
                + "\nKembalian: Rp " + nf.format((long) kembalian));
            batalTransaksi();
            generateNoNota();

        }catch(Exception e){
            try{ con.rollback(); }catch(Exception ex){}
            JOptionPane.showMessageDialog(this, "GAGAL: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            try{ con.setAutoCommit(true); }catch(Exception ex){}
        }
    }

    // ===== BATAL TRANSAKSI =====
    private void batalTransaksi() {
        tblModel.setRowCount(0);
        totalBayarValue = 0;
        txtTotalBayar.setText("0");
        txtBayar.setText("0");
        txtKembalian.setText("0");
        cmbCustomer.setSelectedIndex(0);
        clearInputBarang();
    }

    // ===== CLEAR INPUT BARANG =====
    private void clearInputBarang() {
        txtCariBarang.setText("");
        txtNamaBarang.setText("");
        txtHargaSatuan.setText("");
        txtSubtotal.setText("0");
        lblStok.setText("Stok: -");
        kodeBarangDipilih  = "";
        hargaBarangDipilih = 0;
        spnQty.setValue(1);
        txtCariBarang.requestFocus();
    }
}