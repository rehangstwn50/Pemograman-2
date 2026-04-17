package Callfrom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * MainFrame - Form Utama
 * Berisi tombol untuk memanggil FormMahasiswa
 * @author rehan
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Main");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Label judul
        JLabel lblJudul = new JLabel("MEMANGGIL FORM MAHASISWA", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblJudul, BorderLayout.CENTER);

        // Tombol panggil form
        JButton btnPanggil = new JButton("PANGGIL FORM");
        JPanel panelBtn = new JPanel(); // supaya tombol tidak meregang
        panelBtn.add(btnPanggil);
        add(panelBtn, BorderLayout.SOUTH);

        // Aksi tombol: panggil FormMahasiswa
        btnPanggil.addActionListener(e -> {
            FormMahasiswa fm = new FormMahasiswa();
            fm.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}