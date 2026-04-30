package aplikasipenjualan.form;

import aplikasipenjualan.koneksi.KoneksiDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FormCustomer extends JFrame {

    private JTextField  txtKode, txtNama, txtTelepon, txtEmail, txtCari;
    private JTextArea   txtAlamat;
    private JComboBox<String> cmbJK;
    private JTable      tblCustomer;
    private DefaultTableModel tblModel;
    private JButton     btnSimpan, btnHapus, btnBaru;

    public FormCustomer() {
        initComponents();
        loadData();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Master Data Customer");
        setSize(850, 550);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // ===== PANEL FORM =====
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Input Data Customer"));
        pnlForm.setPreferredSize(new Dimension(300, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        String[] lbl = {"Kode Customer *","Nama Customer *","Alamat",
                        "Telepon","Email","Jenis Kelamin"};
        for (int i = 0; i < lbl.length; i++) {
            gbc.gridx=0; gbc.gridy=i; gbc.weightx=0;
            pnlForm.add(new JLabel(lbl[i]+":"), gbc);
        }

        txtKode    = new JTextField(15);
        txtNama    = new JTextField(15);
        txtAlamat  = new JTextArea(3,15); txtAlamat.setLineWrap(true);
        txtTelepon = new JTextField(15);
        txtEmail   = new JTextField(15);
        cmbJK      = new JComboBox<>(new String[]{"Laki-laki","Perempuan"});

        Component[] fields = {txtKode, txtNama,
            new JScrollPane(txtAlamat), txtTelepon, txtEmail, cmbJK};
        for (int i = 0; i < fields.length; i++) {
            gbc.gridx=1; gbc.gridy=i; gbc.weightx=1;
            pnlForm.add(fields[i], gbc);
        }

        JPanel pnlBtn = new JPanel(new FlowLayout());
        btnSimpan = new JButton("💾 Simpan");
        btnHapus  = new JButton("🗑 Hapus");
        btnBaru   = new JButton("➕ Baru");
        btnSimpan.setBackground(new Color(46,125,110));
        btnSimpan.setForeground(Color.WHITE);
        btnHapus.setBackground(new Color(200,50,50));
        btnHapus.setForeground(Color.WHITE);
        pnlBtn.add(btnSimpan); pnlBtn.add(btnHapus); pnlBtn.add(btnBaru);
        gbc.gridx=0; gbc.gridy=lbl.length; gbc.gridwidth=2;
        pnlForm.add(pnlBtn, gbc);

        // ===== PANEL TABEL =====
        String[] kolom = {"Kode","Nama Customer","Telepon","Email","Jenis Kelamin"};
        tblModel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblCustomer = new JTable(tblModel);
        tblCustomer.setRowHeight(22);

        JPanel pnlTabel = new JPanel(new BorderLayout(3,3));
        pnlTabel.setBorder(BorderFactory.createTitledBorder("Data Customer"));
        JPanel pnlCari = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtCari = new JTextField(20);
        pnlCari.add(new JLabel("Cari:")); pnlCari.add(txtCari);
        pnlTabel.add(pnlCari, BorderLayout.NORTH);
        pnlTabel.add(new JScrollPane(tblCustomer), BorderLayout.CENTER);

        add(pnlForm,  BorderLayout.WEST);
        add(pnlTabel, BorderLayout.CENTER);

        // ===== EVENTS =====
        btnSimpan.addActionListener(e -> simpan());
        btnHapus.addActionListener(e  -> hapus());
        btnBaru.addActionListener(e   -> clearForm());
        tblCustomer.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { isiForm(); }
        });
        txtCari.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) { cari(); }
        });
    }

    private void loadData() {
        tblModel.setRowCount(0);
        String sql = "SELECT kode_customer, nama_customer, telepon, email,"
                   + " CASE jenis_kelamin WHEN 'L' THEN 'Laki-laki'"
                   + " ELSE 'Perempuan' END as jk"
                   + " FROM customer ORDER BY nama_customer";
        try (Connection con = KoneksiDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                tblModel.addRow(new Object[]{
                    rs.getString(1),rs.getString(2),
                    rs.getString(3),rs.getString(4),rs.getString(5)
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void cari() {
        String kw = "%"+txtCari.getText().trim()+"%";
        tblModel.setRowCount(0);
        String sql = "SELECT kode_customer, nama_customer, telepon, email,"
                   + " CASE jenis_kelamin WHEN 'L' THEN 'Laki-laki'"
                   + " ELSE 'Perempuan' END"
                   + " FROM customer WHERE LOWER(nama_customer) LIKE LOWER(?)"
                   + " OR kode_customer LIKE ?";
        try (Connection con = KoneksiDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, kw); ps.setString(2, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tblModel.addRow(new Object[]{
                    rs.getString(1),rs.getString(2),
                    rs.getString(3),rs.getString(4),rs.getString(5)
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void simpan() {
        String kode = txtKode.getText().trim();
        String nama = txtNama.getText().trim();
        if (kode.isEmpty()||nama.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Kode dan Nama wajib diisi!");
            return;
        }
        String jk = cmbJK.getSelectedIndex()==0 ? "L" : "P";
        try (Connection con = KoneksiDB.getConnection()) {
            PreparedStatement cek = con.prepareStatement(
                "SELECT kode_customer FROM customer WHERE kode_customer=?");
            cek.setString(1, kode);
            boolean ada = cek.executeQuery().next();
            String sql = ada
                ? "UPDATE customer SET nama_customer=?,alamat=?,telepon=?,email=?,jenis_kelamin=? WHERE kode_customer=?"
                : "INSERT INTO customer(kode_customer,nama_customer,alamat,telepon,email,jenis_kelamin) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            if (ada) {
                ps.setString(1,nama); ps.setString(2,txtAlamat.getText());
                ps.setString(3,txtTelepon.getText()); ps.setString(4,txtEmail.getText());
                ps.setString(5,jk); ps.setString(6,kode);
            } else {
                ps.setString(1,kode); ps.setString(2,nama);
                ps.setString(3,txtAlamat.getText()); ps.setString(4,txtTelepon.getText());
                ps.setString(5,txtEmail.getText()); ps.setString(6,jk);
            }
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Berhasil disimpan!");
            loadData(); clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+e.getMessage());
        }
    }

    private void hapus() {
        int row = tblCustomer.getSelectedRow();
        if (row<0){JOptionPane.showMessageDialog(this,"Pilih data dulu!");return;}
        String kode = tblModel.getValueAt(row,0).toString();
        if(JOptionPane.showConfirmDialog(this,"Hapus "+kode+"?","Konfirmasi",
           JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION) return;
        try(Connection con=KoneksiDB.getConnection();
            PreparedStatement ps=con.prepareStatement(
                "DELETE FROM customer WHERE kode_customer=?")){
            ps.setString(1,kode); ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Berhasil dihapus!");
            loadData(); clearForm();
        }catch(Exception e){JOptionPane.showMessageDialog(this,"Error: "+e.getMessage());}
    }

    private void isiForm() {
        int row = tblCustomer.getSelectedRow();
        if (row<0) return;
        txtKode.setText(tblModel.getValueAt(row,0).toString());
        txtKode.setEditable(false);
        txtNama.setText(tblModel.getValueAt(row,1).toString());
        txtTelepon.setText(tblModel.getValueAt(row,2)!=null?tblModel.getValueAt(row,2).toString():"");
        txtEmail.setText(tblModel.getValueAt(row,3)!=null?tblModel.getValueAt(row,3).toString():"");
        String jk = tblModel.getValueAt(row,4).toString();
        cmbJK.setSelectedIndex(jk.equals("Laki-laki")?0:1);
    }

    private void clearForm() {
        txtKode.setText(""); txtKode.setEditable(true);
        txtNama.setText(""); txtAlamat.setText("");
        txtTelepon.setText(""); txtEmail.setText("");
        cmbJK.setSelectedIndex(0);
        tblCustomer.clearSelection();
    }
}