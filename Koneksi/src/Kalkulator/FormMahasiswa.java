import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormMahasiswa extends JFrame {

    // =====================================================
    // DEKLARASI KOMPONEN GUI
    // =====================================================
    private JLabel lblNim, lblNama, lblNil1, lblNil2, lblRata, lblTitle;
    private JTextField nimTF, namaTF, nil1TF, nil2TF, rataTF;
    private JButton btnCari, btnUpdate, btnHapus, btnBersihkan;
    private JPanel panelForm, panelButton;

    // Koneksi database
    private Connection koneksi;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================
    public FormMahasiswa() {
        koneksi = Koneksi.getKoneksi();
        initComponents();
        setTitle("Form Data Mahasiswa");
        setSize(400, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // =====================================================
    // INISIALISASI KOMPONEN GUI (Manual tanpa Designer)
    // =====================================================
    private void initComponents() {
        // Label
        lblTitle      = new JLabel("DATA NILAI MAHASISWA", SwingConstants.CENTER);
        lblNim        = new JLabel("NIM");
        lblNama       = new JLabel("Nama");
        lblNil1       = new JLabel("Nilai 1");
        lblNil2       = new JLabel("Nilai 2");
        lblRata       = new JLabel("Rata-rata");

        // TextField
        nimTF   = new JTextField(20);
        namaTF  = new JTextField(20);
        nil1TF  = new JTextField(20);
        nil2TF  = new JTextField(20);
        rataTF  = new JTextField(20);

        // Button
        btnCari      = new JButton("Cari");
        btnUpdate    = new JButton("Update");
        btnHapus     = new JButton("Hapus");
        btnBersihkan = new JButton("Bersihkan");

        // ---- Panel Form (GridLayout) ----
        panelForm = new JPanel(new GridLayout(5, 2, 5, 8));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelForm.add(lblNim);    panelForm.add(nimTF);
        panelForm.add(lblNama);   panelForm.add(namaTF);
        panelForm.add(lblNil1);   panelForm.add(nil1TF);
        panelForm.add(lblNil2);   panelForm.add(nil2TF);
        panelForm.add(lblRata);   panelForm.add(rataTF);

        // ---- Panel Button ----
        panelButton = new JPanel(new FlowLayout());
        panelButton.add(btnCari);
        panelButton.add(btnUpdate);
        panelButton.add(btnHapus);
        panelButton.add(btnBersihkan);

        // ---- Frame Layout ----
        setLayout(new BorderLayout(5, 5));
        add(lblTitle,    BorderLayout.NORTH);
        add(panelForm,   BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);

        // ---- Event Listener ----
        btnCari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cariData();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hapusData();
            }
        });

        btnBersihkan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bersihkanForm();
            }
        });
    }

    // =====================================================
    // 1. CARI DATA — Statement + ResultSet (LIKE)
    // =====================================================
    private void cariData() {
        try {
            Statement st = koneksi.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT * FROM datanil" +
                " WHERE nama LIKE('%" + namaTF.getText() + "%')"
            );

            if (rs.next()) {
                nimTF.setText(rs.getString("nim"));
                nil1TF.setText(rs.getString("nil1"));
                nil2TF.setText(rs.getString("nil2"));
                rataTF.setText(rs.getString("rata"));
            } else {
                JOptionPane.showMessageDialog(this,
                    "Data tidak ada/Salah",
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("koneksi gagal " + e.toString());
        }
    }

    // =====================================================
    // 2. UPDATE DATA — PreparedStatement
    // =====================================================
    private void updateData() {
        if (nimTF.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "NIM tidak boleh kosong!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            PreparedStatement ps = koneksi.prepareStatement(
                "UPDATE datanil SET nama=?, nil1=?, nil2=?, rata=? WHERE nim=?"
            );

            ps.setString(1, namaTF.getText());
            ps.setDouble(2, Double.parseDouble(nil1TF.getText()));
            ps.setDouble(3, Double.parseDouble(nil2TF.getText()));
            ps.setDouble(4, Double.parseDouble(rataTF.getText()));
            ps.setString(5, nimTF.getText());

            int baris = ps.executeUpdate();

            if (baris > 0) {
                JOptionPane.showMessageDialog(this,
                    "Data berhasil diupdate!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("koneksi gagal " + e.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Nilai 1, Nilai 2, dan Rata-rata harus berupa angka!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    // =====================================================
    // 3. HAPUS DATA — PreparedStatement
    // =====================================================
    private void hapusData() {
        if (nimTF.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "NIM tidak boleh kosong!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Yakin hapus data NIM: " + nimTF.getText() + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement ps = koneksi.prepareStatement(
                    "DELETE FROM datanil WHERE nim=?"
                );

                ps.setString(1, nimTF.getText());
                int baris = ps.executeUpdate();

                if (baris > 0) {
                    JOptionPane.showMessageDialog(this,
                        "Data berhasil dihapus!",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
                    bersihkanForm();
                }

            } catch (SQLException e) {
                System.out.println("koneksi gagal " + e.toString());
            }
        }
    }

    // =====================================================
    // 4. BERSIHKAN FORM
    // =====================================================
    private void bersihkanForm() {
        nimTF.setText("");
        namaTF.setText("");
        nil1TF.setText("");
        nil2TF.setText("");
        rataTF.setText("");
        namaTF.requestFocus();
    }

    // =====================================================
    // MAIN — Titik masuk program
    // =====================================================
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMahasiswa().setVisible(true);
            }
        });
    }
}