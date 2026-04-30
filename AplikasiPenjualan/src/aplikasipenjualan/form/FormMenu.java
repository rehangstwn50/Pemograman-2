package aplikasipenjualan.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormMenu extends JFrame {

    public FormMenu() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setTitle("Aplikasi Penjualan Barang");
        setSize(800, 130);
        setResizable(false);

        // ===== MENU BAR =====
        JMenuBar menuBar = new JMenuBar();

        // Menu Master
        JMenu menuMaster = new JMenu("Master Data");
        JMenuItem miBarang    = new JMenuItem("Data Barang");
        JMenuItem miCustomer  = new JMenuItem("Data Customer");
        JMenuItem miSupplier  = new JMenuItem("Data Supplier");

        miBarang.addActionListener(e   -> new FormBarang().setVisible(true));
        miCustomer.addActionListener(e -> new FormCustomer().setVisible(true));
        miSupplier.addActionListener(e -> new FormSupplier().setVisible(true));

        menuMaster.add(miBarang);
        menuMaster.add(miCustomer);
        menuMaster.add(miSupplier);

        // Menu Transaksi
        JMenu menuTransaksi = new JMenu("Transaksi");
        JMenuItem miPenjualan = new JMenuItem("Penjualan");
        miPenjualan.addActionListener(e -> new FormPenjualan().setVisible(true));
        menuTransaksi.add(miPenjualan);

        // Menu Laporan
        JMenu menuLaporan = new JMenu("Laporan");
        JMenuItem miLaporan = new JMenuItem("Cetak Laporan");
        miLaporan.addActionListener(e -> new FormLaporan().setVisible(true));
        menuLaporan.add(miLaporan);

        // Menu Keluar
        JMenu menuKeluar = new JMenu("Keluar");
        JMenuItem miKeluar = new JMenuItem("Exit");
        miKeluar.addActionListener(e -> {
            int opt = JOptionPane.showConfirmDialog(this,
                "Yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) System.exit(0);
        });
        menuKeluar.add(miKeluar);

        menuBar.add(menuMaster);
        menuBar.add(menuTransaksi);
        menuBar.add(menuLaporan);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(menuKeluar);
        setJMenuBar(menuBar);

        // Panel status bar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.add(new JLabel("Selamat datang di Aplikasi Penjualan Barang"));
        add(statusBar, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormMenu().setVisible(true));
    }
}