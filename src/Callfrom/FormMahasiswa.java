package Callfrom;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * FormMahasiswa - Form yang dipanggil dari MainFrame
 * Berisi input NIM, Nama Mahasiswa, Nilai dan JTable
 * @author rehan
 */
public class FormMahasiswa extends JFrame {

    private JTextField txtNIM, txtNama, txtNilai;
    private DefaultTableModel tblModel;
    private JTable tblMahasiswa;

    public FormMahasiswa() {
        initComponents();
        inisialisasiTabel();
        isiDataAwal();
    }

    private void initComponents() {
        setTitle("Form Mahasiswa");
        setSize(400, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // tutup form ini saja, bukan seluruh app
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        // =============================================
        // Panel Input (GridBagLayout agar rapi)
        // =============================================
        JPanel panelInput = new JPanel(new GridBagLayout());
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        // Baris NIM
        gbc.gridx = 0; gbc.gridy = 0;
        panelInput.add(new JLabel("NIM"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNIM = new JTextField(15);
        panelInput.add(txtNIM, gbc);

        // Tombol TABEL di sebelah kanan NIM
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JButton btnTabel = new JButton("TABEL");
        panelInput.add(btnTabel, gbc);

        // Baris Nama Mahasiswa
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        panelInput.add(new JLabel("Nama Mahasiswa"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNama = new JTextField(15);
        panelInput.add(txtNama, gbc);

        // Baris Nilai
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelInput.add(new JLabel("Nilai"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNilai = new JTextField(15);
        panelInput.add(txtNilai, gbc);

        add(panelInput, BorderLayout.NORTH);

        // =============================================
        // JTable
        // =============================================
        tblMahasiswa = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblMahasiswa);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // =============================================
        // Aksi tombol TABEL: tambah data ke tabel
        // =============================================
        btnTabel.addActionListener(e -> tambahDataKeTable());

        // Klik baris tabel → tampilkan di input
        tblMahasiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tblMahasiswa.getSelectedRow();
                if (baris >= 0) {
                    txtNIM.setText(tblModel.getValueAt(baris, 0).toString());
                    txtNama.setText(tblModel.getValueAt(baris, 1).toString());
                    txtNilai.setText(tblModel.getValueAt(baris, 2).toString());
                }
            }
        });
    }

    /**
     * Inisialisasi kolom tabel dengan DefaultTableModel (Cara 2 - sesuai materi)
     */
    private void inisialisasiTabel() {
        tblModel = new DefaultTableModel();
        tblMahasiswa.setModel(tblModel);

        tblModel.addColumn("NIM");
        tblModel.addColumn("Nama Mahasiswa");
        tblModel.addColumn("Nilai");
    }

    /**
     * Memasukkan data input ke JTable (sesuai materi slide 9)
     */
    private void tambahDataKeTable() {
        String nim   = txtNIM.getText().trim();
        String nama  = txtNama.getText().trim();
        String nilai = txtNilai.getText().trim();

        if (nim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM tidak boleh kosong!");
            return;
        }

        Object[] nmVardata = new Object[3];
        nmVardata[0] = nim;
        nmVardata[1] = nama;
        nmVardata[2] = nilai;

        tblModel.addRow(nmVardata);

        // Bersihkan input setelah ditambah
        txtNIM.setText("");
        txtNama.setText("");
        txtNilai.setText("");
        txtNIM.requestFocus();
    }

    /**
     * Data awal sesuai gambar contoh
     */
    private void isiDataAwal() {
        Object[] data1 = {"231011402544", "Awan", "85"};
        Object[] data2 = {"231011402545", "Gusti",   "95"};
        tblModel.addRow(data1);
        tblModel.addRow(data2);
    }
}