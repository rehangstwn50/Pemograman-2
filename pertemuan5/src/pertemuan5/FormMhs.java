package pertemuan5;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormMhs extends javax.swing.JFrame {

    DefaultTableModel model;
    Connection conn;
    Statement st;
    ResultSet rs;

    public FormMhs() {
        initComponents();
        koneksi();
        loadTable();
    }

    // 🔹 KONEKSI DATABASE
    private void koneksi() {
        try {
            String url = "jdbc:mysql://localhost:3306/pertemuan5";
            String user = "root";
            String pass = "";

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conn = DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
        }
    }

    // 🔹 LOAD DATA
    private void loadTable() {
        model = new DefaultTableModel();
        model.addColumn("NIM");
        model.addColumn("Nama");
        model.addColumn("Semester");
        model.addColumn("Kelas");

        jTable1.setModel(model);

        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM datamhs");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getInt("semester"),
                    rs.getString("kelas")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // 🔹 SIMPAN
    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "INSERT INTO datamhs VALUES ('"
                    + txtNim.getText() + "','"
                    + txtNama.getText() + "','"
                    + txtSemester.getText() + "','"
                    + txtKelas.getText() + "')";

            st = conn.createStatement();
            st.execute(sql);

            loadTable();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // 🔹 HAPUS
    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "DELETE FROM datamhs WHERE nim='" + txtNim.getText() + "'";
            st = conn.createStatement();
            st.execute(sql);

            loadTable();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // 🔹 UPDATE
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "UPDATE datamhs SET "
                    + "nama='" + txtNama.getText() + "',"
                    + "semester='" + txtSemester.getText() + "',"
                    + "kelas='" + txtKelas.getText() + "' "
                    + "WHERE nim='" + txtNim.getText() + "'";

            st = conn.createStatement();
            st.execute(sql);

            loadTable();
            JOptionPane.showMessageDialog(null, "Data berhasil diupdate");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // 🔹 BERSIH
    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {
        txtNim.setText("");
        txtNama.setText("");
        txtSemester.setText("");
        txtKelas.setText("");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        JLabel lblNim = new JLabel("NIM");
        JLabel lblNama = new JLabel("Nama");
        JLabel lblSemester = new JLabel("Semester");
        JLabel lblKelas = new JLabel("Kelas");

        txtNim = new JTextField();
        txtNama = new JTextField();
        txtSemester = new JTextField();
        txtKelas = new JTextField();

        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnUpdate = new JButton("Update");
        btnBersih = new JButton("Bersih");

        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Mahasiswa");
        setLayout(null);

        // LABEL
        lblNim.setBounds(20, 20, 80, 25);
        add(lblNim);

        lblNama.setBounds(20, 50, 80, 25);
        add(lblNama);

        lblSemester.setBounds(20, 80, 80, 25);
        add(lblSemester);

        lblKelas.setBounds(20, 110, 80, 25);
        add(lblKelas);

        // TEXTFIELD
        txtNim.setBounds(100, 20, 150, 25);
        add(txtNim);

        txtNama.setBounds(100, 50, 150, 25);
        add(txtNama);

        txtSemester.setBounds(100, 80, 150, 25);
        add(txtSemester);

        txtKelas.setBounds(100, 110, 150, 25);
        add(txtKelas);

        // BUTTON
        btnSimpan.setBounds(280, 20, 100, 25);
        btnSimpan.addActionListener(evt -> btnSimpanActionPerformed(evt));
        add(btnSimpan);

        btnHapus.setBounds(280, 50, 100, 25);
        btnHapus.addActionListener(evt -> btnHapusActionPerformed(evt));
        add(btnHapus);

        btnUpdate.setBounds(280, 80, 100, 25);
        btnUpdate.addActionListener(evt -> btnUpdateActionPerformed(evt));
        add(btnUpdate);

        btnBersih.setBounds(280, 110, 100, 25);
        btnBersih.addActionListener(evt -> btnBersihActionPerformed(evt));
        add(btnBersih);

        // TABLE
        jTable1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"NIM", "Nama", "Semester", "Kelas"}
        ));
        jScrollPane1.setViewportView(jTable1);
        jScrollPane1.setBounds(20, 160, 420, 150);
        add(jScrollPane1);

        setSize(480, 360);
        setLocationRelativeTo(null);
    }

    private JButton btnBersih;
    private JButton btnHapus;
    private JButton btnSimpan;
    private JButton btnUpdate;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JTextField txtKelas;
    private JTextField txtNama;
    private JTextField txtNim;
    private JTextField txtSemester;

    public static void main(String args[]) {
        new FormMhs().setVisible(true);
    }
}