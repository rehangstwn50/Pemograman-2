package pertemuan7;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.*;
import java.io.InputStream;

public class Pertemuan7 extends JFrame {

    Connection conn;
    Statement st;
    ResultSet rs;
    DefaultTableModel model;

    public Pertemuan7() {
        initComponents();
        koneksi();
        tampilData();
    }

    // ===================== KONEKSI =====================
    public void koneksi() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/datamhs",
                "root",
                ""
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Koneksi gagal: " + e.getMessage());
        }
    }

    // ===================== TAMPIL DATA =====================
    public void tampilData() {
        try {
            model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM nilai_mahasiswa");

            while (rs.next()) {
                Object[] row = {
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("mata_kuliah"),
                    rs.getDouble("nil1"),
                    rs.getDouble("nil2"),
                    rs.getDouble("rata_rata")
                };
                model.addRow(row);
            }

            lblHasil.setText("Total data: " + model.getRowCount() + " mahasiswa");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal tampil: " + e.getMessage());
        }
    }

    // ===================== SIMPAN =====================
    public void simpanData() {
        try {
            String nim = txtNim.getText();
            String nama = txtNama.getText();
            String matkul = txtMatkul.getText();
            double nil1 = Double.parseDouble(txtNil1.getText());
            double nil2 = Double.parseDouble(txtNil2.getText());
            double rata = (nil1 + nil2) / 2;

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO nilai_mahasiswa VALUES (?,?,?,?,?,?)");
            ps.setString(1, nim);
            ps.setString(2, nama);
            ps.setString(3, matkul);
            ps.setDouble(4, nil1);
            ps.setDouble(5, nil2);
            ps.setDouble(6, rata);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            tampilData();
            bersihForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan: " + e.getMessage());
        }
    }

    // ===================== UPDATE =====================
    public void updateData() {
        try {
            double rata = (Double.parseDouble(txtNil1.getText())
                         + Double.parseDouble(txtNil2.getText())) / 2;

            PreparedStatement ps = conn.prepareStatement(
                "UPDATE nilai_mahasiswa SET nama=?, mata_kuliah=?, nil1=?, nil2=?, rata_rata=? WHERE nim=?");

            ps.setString(1, txtNama.getText());
            ps.setString(2, txtMatkul.getText());
            ps.setDouble(3, Double.parseDouble(txtNil1.getText()));
            ps.setDouble(4, Double.parseDouble(txtNil2.getText()));
            ps.setDouble(5, rata);
            ps.setString(6, txtNim.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            tampilData();
            bersihForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal update: " + e.getMessage());
        }
    }

    // ===================== HAPUS =====================
    public void hapusData() {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM nilai_mahasiswa WHERE nim=?");
            ps.setString(1, txtNim.getText());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            tampilData();
            bersihForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal hapus: " + e.getMessage());
        }
    }

    // ===================== CETAK JASPER =====================
   public void cetakLaporan() {
    try {
        InputStream is = getClass().getResourceAsStream("/pertemuan7/Pertemuan7.jrxml");

        JasperReport jr = JasperCompileManager.compileReport(is);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

        JasperViewer.viewReport(jp, false);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal cetak: " + e.getMessage());
    }
}

    // ===================== BERSIH =====================
    public void bersihForm() {
        txtNim.setText("");
        txtNama.setText("");
        txtMatkul.setText("");
        txtNil1.setText("");
        txtNil2.setText("");
        lblRata.setText("0.0");
    }

    // ===================== HITUNG RATA =====================
    private void hitungRata() {
        try {
            double n1 = Double.parseDouble(txtNil1.getText());
            double n2 = Double.parseDouble(txtNil2.getText());
            lblRata.setText(String.valueOf((n1 + n2) / 2));
        } catch (Exception e) {
            lblRata.setText("0.0");
        }
    }

    // ===================== UI =====================
    private void initComponents() {

        setTitle("Form Nilai Mahasiswa");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel l1 = new JLabel("NIM"); l1.setBounds(20,20,100,25); add(l1);
        JLabel l2 = new JLabel("Nama"); l2.setBounds(20,50,100,25); add(l2);
        JLabel l3 = new JLabel("Mata Kuliah"); l3.setBounds(20,80,100,25); add(l3);
        JLabel l4 = new JLabel("Nilai 1"); l4.setBounds(20,110,100,25); add(l4);
        JLabel l5 = new JLabel("Nilai 2"); l5.setBounds(20,140,100,25); add(l5);
        JLabel l6 = new JLabel("Rata-rata"); l6.setBounds(20,170,100,25); add(l6);

        txtNim = new JTextField(); txtNim.setBounds(130,20,200,25); add(txtNim);
        txtNama = new JTextField(); txtNama.setBounds(130,50,200,25); add(txtNama);
        txtMatkul = new JTextField(); txtMatkul.setBounds(130,80,200,25); add(txtMatkul);
        txtNil1 = new JTextField(); txtNil1.setBounds(130,110,200,25); add(txtNil1);
        txtNil2 = new JTextField(); txtNil2.setBounds(130,140,200,25); add(txtNil2);

        lblRata = new JLabel("0.0"); lblRata.setBounds(130,170,200,25); add(lblRata);
        lblHasil = new JLabel("Total data: 0"); lblHasil.setBounds(20,200,300,25); add(lblHasil);

        JButton b1 = new JButton("Simpan"); b1.setBounds(20,230,100,30); add(b1);
        JButton b2 = new JButton("Update"); b2.setBounds(130,230,100,30); add(b2);
        JButton b3 = new JButton("Hapus"); b3.setBounds(240,230,100,30); add(b3);
        JButton b4 = new JButton("Bersih"); b4.setBounds(350,230,100,30); add(b4);
        JButton b5 = new JButton("Cetak"); b5.setBounds(460,230,100,30); add(b5);

        jTable1 = new JTable(new DefaultTableModel(
            new Object[][]{},
            new String[]{"NIM","Nama","Mata Kuliah","Nilai1","Nilai2","Rata-rata"}
        ));
        JScrollPane sp = new JScrollPane(jTable1);
        sp.setBounds(20,270,540,150);
        add(sp);

        b1.addActionListener(e -> simpanData());
        b2.addActionListener(e -> updateData());
        b3.addActionListener(e -> hapusData());
        b4.addActionListener(e -> bersihForm());
        b5.addActionListener(e -> cetakLaporan());

        txtNil1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { hitungRata(); }
        });
        txtNil2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { hitungRata(); }
        });
    }

    public static void main(String[] args) {
        new Pertemuan7().setVisible(true);
    }

    private JTextField txtNim, txtNama, txtMatkul, txtNil1, txtNil2;
    private JLabel lblRata, lblHasil;
    private JTable jTable1;
}