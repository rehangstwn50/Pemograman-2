package aplikasipenjualan.form;

import aplikasipenjualan.koneksi.KoneksiDB;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.Calendar;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;

public class FormLaporan extends JFrame {

    private JComboBox<String> cmbJenis;
    private JSpinner spnAwal, spnAkhir;
    private JButton  btnCetak, btnExportPDF;
    private JLabel   lblInfo;

    public FormLaporan() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Laporan Aplikasi Penjualan");
        setSize(500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBorder(BorderFactory.createTitledBorder("Pilih Laporan"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        cmbJenis = new JComboBox<>(new String[]{
            "Laporan Transaksi Penjualan",
            "Laporan Inventory Stok Barang"
        });

        SpinnerDateModel mdlAwal  = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        SpinnerDateModel mdlAkhir = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        spnAwal  = new JSpinner(mdlAwal);
        spnAkhir = new JSpinner(mdlAkhir);
        spnAwal.setEditor(new JSpinner.DateEditor(spnAwal,   "dd-MM-yyyy"));
        spnAkhir.setEditor(new JSpinner.DateEditor(spnAkhir, "dd-MM-yyyy"));

        lblInfo = new JLabel("* File .jrxml dikompilasi otomatis saat cetak");
        lblInfo.setForeground(new Color(80, 80, 80));
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));

        gbc.gridx=0; gbc.gridy=0; gbc.weightx=0; pnl.add(new JLabel("Jenis Laporan:"), gbc);
        gbc.gridx=1; gbc.weightx=1;               pnl.add(cmbJenis, gbc);
        gbc.gridx=0; gbc.gridy=1; gbc.weightx=0;  pnl.add(new JLabel("Tanggal Awal:"), gbc);
        gbc.gridx=1; gbc.weightx=1;               pnl.add(spnAwal, gbc);
        gbc.gridx=0; gbc.gridy=2; gbc.weightx=0;  pnl.add(new JLabel("Tanggal Akhir:"), gbc);
        gbc.gridx=1; gbc.weightx=1;               pnl.add(spnAkhir, gbc);
        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2; pnl.add(lblInfo, gbc);

        JPanel pnlBtn = new JPanel(new FlowLayout());
        btnCetak     = new JButton("🖨  Tampilkan Laporan");
        btnExportPDF = new JButton("📄 Export ke PDF");
        btnCetak.setBackground(new Color(46, 125, 110));
        btnCetak.setForeground(Color.WHITE);
        btnCetak.setFont(new Font("Arial", Font.BOLD, 12));
        btnExportPDF.setBackground(new Color(50, 100, 200));
        btnExportPDF.setForeground(Color.WHITE);
        pnlBtn.add(btnCetak);
        pnlBtn.add(btnExportPDF);

        add(pnl,    BorderLayout.CENTER);
        add(pnlBtn, BorderLayout.SOUTH);

        cmbJenis.addActionListener(e -> {
            boolean perluTgl = (cmbJenis.getSelectedIndex() == 0);
            spnAwal.setEnabled(perluTgl);
            spnAkhir.setEnabled(perluTgl);
        });

        btnCetak.addActionListener(e     -> cetakLaporan(false));
        btnExportPDF.addActionListener(e -> cetakLaporan(true));
    }

    // ================================================================
    //  SET TEMP DIR — agar JasperReports tidak nulis ke Program Files
    // ================================================================
    private void setTempDir() {
        // Arahkan ke temp folder user agar tidak perlu permission admin
        String tempDir = System.getProperty("java.io.tmpdir");
        System.setProperty("jasper.reports.compile.temp",            tempDir);
        System.setProperty("net.sf.jasperreports.compiler.temp.dir", tempDir);

        // Buat subfolder jasper di temp agar lebih rapi
        File jasperTemp = new File(tempDir + File.separator + "jasper_temp");
        if (!jasperTemp.exists()) {
            jasperTemp.mkdirs();
        }
        System.setProperty("jasper.reports.compile.temp",            jasperTemp.getAbsolutePath());
        System.setProperty("net.sf.jasperreports.compiler.temp.dir", jasperTemp.getAbsolutePath());
    }

    // ================================================================
    //  CARI FILE JRXML DI 4 LOKASI
    // ================================================================
    private InputStream cariFileJrxml(String namaFile) {
        InputStream is = null;

        // =============================================
        // COBA 1: Folder laporan/ di samping JAR/EXE
        //         → untuk versi sudah diinstall
        // =============================================
        try {
            String jarDir = new File(
                FormLaporan.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI()
            ).getParent();

            File fileLaporan = new File(
                jarDir + File.separator + "laporan" + File.separator + namaFile
            );

            if (fileLaporan.exists()) {
                System.out.println("Laporan ditemukan di: " + fileLaporan.getAbsolutePath());
                return new FileInputStream(fileLaporan);
            }
        } catch (URISyntaxException | java.io.FileNotFoundException e) {
            System.out.println("Coba 1 gagal: " + e.getMessage());
        }

        // =============================================
        // COBA 2: Classpath
        //         → untuk development di NetBeans
        // =============================================
        is = getClass().getResourceAsStream(
            "/aplikasipenjualan/laporan/" + namaFile);
        if (is != null) {
            System.out.println("Laporan ditemukan di classpath: " + namaFile);
            return is;
        }

        // =============================================
        // COBA 3: Folder kerja saat ini
        // =============================================
        File f3 = new File("laporan" + File.separator + namaFile);
        if (f3.exists()) {
            try {
                System.out.println("Laporan ditemukan di: " + f3.getAbsolutePath());
                return new FileInputStream(f3);
            } catch (java.io.FileNotFoundException e) {
                System.out.println("Coba 3 gagal: " + e.getMessage());
            }
        }

        // =============================================
        // COBA 4: Path absolut Program Files
        // =============================================
        String[] pathList = {
            "C:\\Program Files (x86)\\AplikasiPenjualan\\laporan\\" + namaFile,
            "C:\\Program Files\\AplikasiPenjualan\\laporan\\"       + namaFile
        };

        for (String path : pathList) {
            File f4 = new File(path);
            if (f4.exists()) {
                try {
                    System.out.println("Laporan ditemukan di: " + f4.getAbsolutePath());
                    return new FileInputStream(f4);
                } catch (java.io.FileNotFoundException e) {
                    System.out.println("Coba 4 gagal: " + e.getMessage());
                }
            }
        }

        return null;
    }

    // ================================================================
    //  METHOD UTAMA — compile .jrxml → fill → tampil / export
    // ================================================================
    private void cetakLaporan(boolean exportPDF) {

        Date tglAwal  = (Date)((SpinnerDateModel)spnAwal.getModel()).getValue();
        Date tglAkhir = (Date)((SpinnerDateModel)spnAkhir.getModel()).getValue();

        if (cmbJenis.getSelectedIndex() == 0 && tglAwal.after(tglAkhir)) {
            JOptionPane.showMessageDialog(this,
                "Tanggal awal tidak boleh setelah tanggal akhir!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String namaFile;
        switch (cmbJenis.getSelectedIndex()) {
            case 0:  namaFile = "LaporanTransaksi.jrxml"; break;
            case 1:  namaFile = "LaporanInventory.jrxml"; break;
            default: namaFile = "LaporanTransaksi.jrxml"; break;
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnCetak.setEnabled(false);
        btnExportPDF.setEnabled(false);

        final String finalNamaFile = namaFile;
        final Date   finalAwal     = tglAwal;
        final Date   finalAkhir    = tglAkhir;

        new Thread(() -> {
            try {
                // ===== SET TEMP DIR DULU =====
                setTempDir();

                // Cari file jrxml
                InputStream is = cariFileJrxml(finalNamaFile);

                if (is == null) {
                    SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this,
                            "File laporan tidak ditemukan: " + finalNamaFile
                            + "\n\nPastikan folder laporan/ ada di:\n"
                            + "C:\\Program Files (x86)\\AplikasiPenjualan\\laporan\\",
                            "File Tidak Ditemukan", JOptionPane.ERROR_MESSAGE));
                    return;
                }

                // Compile .jrxml → JasperReport (di memory)
                JasperReport jasperReport = JasperCompileManager.compileReport(is);

                // Parameter laporan
                HashMap<String, Object> params = new HashMap<>();
                params.put("tgl_awal",      new java.sql.Date(finalAwal.getTime()));
                params.put("tgl_akhir",     new java.sql.Date(finalAkhir.getTime()));
                params.put("nama_toko",     "Toko Sejahtera");
                params.put("REPORT_LOCALE", new Locale("id", "ID"));

                // Fill laporan dari database
                Connection con = KoneksiDB.getConnection();
                JasperPrint jp = JasperFillManager.fillReport(jasperReport, params, con);

                // Tampilkan atau export PDF
                SwingUtilities.invokeLater(() -> {
                    try {
                        if (exportPDF) {
                            JFileChooser fc = new JFileChooser();
                            fc.setSelectedFile(new File(
                                "Laporan_" + new SimpleDateFormat("yyyyMMdd_HHmmss")
                                    .format(new Date()) + ".pdf"));
                            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                                JasperExportManager.exportReportToPdfFile(
                                    jp, fc.getSelectedFile().getAbsolutePath());
                                JOptionPane.showMessageDialog(this,
                                    "PDF berhasil disimpan!\n"
                                    + fc.getSelectedFile().getPath());
                            }
                        } else {
                            JasperViewer.viewReport(jp, false);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,
                            "Error export: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

            } catch (JRException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this,
                        "Gagal kompilasi laporan:\n" + ex.getMessage()
                        + "\n\nPastikan library JasperReports sudah ada di folder lib/",
                        "Error JasperReports", JOptionPane.ERROR_MESSAGE));
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this,
                        "Error tidak terduga:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE));
            } finally {
                SwingUtilities.invokeLater(() -> {
                    setCursor(Cursor.getDefaultCursor());
                    btnCetak.setEnabled(true);
                    btnExportPDF.setEnabled(true);
                });
            }
        }).start();
    }
}