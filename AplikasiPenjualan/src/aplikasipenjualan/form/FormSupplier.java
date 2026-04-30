package aplikasipenjualan.form;

import aplikasipenjualan.koneksi.KoneksiDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FormSupplier extends JFrame {

    private JTextField  txtKode, txtNama, txtTelepon, txtEmail, txtContact, txtCari;
    private JTextArea   txtAlamat;
    private JTable      tblSupplier;
    private DefaultTableModel tblModel;
    private JButton     btnSimpan, btnHapus, btnBaru;

    public FormSupplier() {
        initComponents();
        loadData();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Master Data Supplier");
        setSize(850, 520);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Input Data Supplier"));
        pnlForm.setPreferredSize(new Dimension(300, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        String[] lbl={"Kode Supplier *","Nama Supplier *","Alamat",
                      "Telepon","Email","Contact Person"};
        for (int i=0;i<lbl.length;i++){
            gbc.gridx=0;gbc.gridy=i;gbc.weightx=0;
            pnlForm.add(new JLabel(lbl[i]+":"),gbc);
        }
        txtKode    = new JTextField(15);
        txtNama    = new JTextField(15);
        txtAlamat  = new JTextArea(3,15); txtAlamat.setLineWrap(true);
        txtTelepon = new JTextField(15);
        txtEmail   = new JTextField(15);
        txtContact = new JTextField(15);

        Component[] fields={txtKode,txtNama,new JScrollPane(txtAlamat),
                            txtTelepon,txtEmail,txtContact};
        for (int i=0;i<fields.length;i++){
            gbc.gridx=1;gbc.gridy=i;gbc.weightx=1;
            pnlForm.add(fields[i],gbc);
        }

        JPanel pnlBtn=new JPanel(new FlowLayout());
        btnSimpan=new JButton("💾 Simpan");
        btnHapus =new JButton("🗑 Hapus");
        btnBaru  =new JButton("➕ Baru");
        btnSimpan.setBackground(new Color(46,125,110)); btnSimpan.setForeground(Color.WHITE);
        btnHapus.setBackground(new Color(200,50,50));   btnHapus.setForeground(Color.WHITE);
        pnlBtn.add(btnSimpan);pnlBtn.add(btnHapus);pnlBtn.add(btnBaru);
        gbc.gridx=0;gbc.gridy=lbl.length;gbc.gridwidth=2;
        pnlForm.add(pnlBtn,gbc);

        String[] kolom={"Kode","Nama Supplier","Telepon","Email","Contact"};
        tblModel=new DefaultTableModel(kolom,0){
            @Override public boolean isCellEditable(int r,int c){return false;}};
        tblSupplier=new JTable(tblModel);tblSupplier.setRowHeight(22);

        JPanel pnlTabel=new JPanel(new BorderLayout(3,3));
        pnlTabel.setBorder(BorderFactory.createTitledBorder("Data Supplier"));
        JPanel pnlCari=new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtCari=new JTextField(20);
        pnlCari.add(new JLabel("Cari:"));pnlCari.add(txtCari);
        pnlTabel.add(pnlCari,BorderLayout.NORTH);
        pnlTabel.add(new JScrollPane(tblSupplier),BorderLayout.CENTER);
        add(pnlForm,BorderLayout.WEST);add(pnlTabel,BorderLayout.CENTER);

        btnSimpan.addActionListener(e->simpan());
        btnHapus.addActionListener(e->hapus());
        btnBaru.addActionListener(e->clearForm());
        tblSupplier.addMouseListener(new MouseAdapter(){
            @Override public void mouseClicked(MouseEvent e){isiForm();}});
        txtCari.addKeyListener(new KeyAdapter(){
            @Override public void keyReleased(KeyEvent e){cari();}});
    }

    private void loadData() {
        tblModel.setRowCount(0);
        String sql="SELECT kode_supplier,nama_supplier,telepon,email,contact_person"
                 +" FROM supplier ORDER BY nama_supplier";
        try(Connection con=KoneksiDB.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next())
                tblModel.addRow(new Object[]{rs.getString(1),rs.getString(2),
                    rs.getString(3),rs.getString(4),rs.getString(5)});
        }catch(Exception e){e.printStackTrace();}
    }

    private void cari(){
        String kw="%"+txtCari.getText().trim()+"%";
        tblModel.setRowCount(0);
        String sql="SELECT kode_supplier,nama_supplier,telepon,email,contact_person"
                 +" FROM supplier WHERE nama_supplier LIKE ? OR kode_supplier LIKE ?";
        try(Connection con=KoneksiDB.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,kw);ps.setString(2,kw);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
                tblModel.addRow(new Object[]{rs.getString(1),rs.getString(2),
                    rs.getString(3),rs.getString(4),rs.getString(5)});
        }catch(Exception e){e.printStackTrace();}
    }

    private void simpan(){
        String kode=txtKode.getText().trim();
        String nama=txtNama.getText().trim();
        if(kode.isEmpty()||nama.isEmpty()){
            JOptionPane.showMessageDialog(this,"Kode dan Nama wajib diisi!");return;}
        try(Connection con=KoneksiDB.getConnection()){
            PreparedStatement cek=con.prepareStatement(
                "SELECT kode_supplier FROM supplier WHERE kode_supplier=?");
            cek.setString(1,kode);
            boolean ada=cek.executeQuery().next();
            String sql=ada
                ?"UPDATE supplier SET nama_supplier=?,alamat=?,telepon=?,email=?,contact_person=? WHERE kode_supplier=?"
                :"INSERT INTO supplier(kode_supplier,nama_supplier,alamat,telepon,email,contact_person) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(sql);
            if(ada){ps.setString(1,nama);ps.setString(2,txtAlamat.getText());
                    ps.setString(3,txtTelepon.getText());ps.setString(4,txtEmail.getText());
                    ps.setString(5,txtContact.getText());ps.setString(6,kode);}
            else{   ps.setString(1,kode);ps.setString(2,nama);
                    ps.setString(3,txtAlamat.getText());ps.setString(4,txtTelepon.getText());
                    ps.setString(5,txtEmail.getText());ps.setString(6,txtContact.getText());}
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Berhasil!");
            loadData();clearForm();
        }catch(Exception e){JOptionPane.showMessageDialog(this,"Error: "+e.getMessage());}
    }

    private void hapus(){
        int row=tblSupplier.getSelectedRow();
        if(row<0){JOptionPane.showMessageDialog(this,"Pilih data dulu!");return;}
        String kode=tblModel.getValueAt(row,0).toString();
        if(JOptionPane.showConfirmDialog(this,"Hapus "+kode+"?","Konfirmasi",
           JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
        try(Connection con=KoneksiDB.getConnection();
            PreparedStatement ps=con.prepareStatement(
                "DELETE FROM supplier WHERE kode_supplier=?")){
            ps.setString(1,kode);ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Berhasil dihapus!");
            loadData();clearForm();
        }catch(Exception e){JOptionPane.showMessageDialog(this,"Error: "+e.getMessage());}
    }

    private void isiForm(){
        int row=tblSupplier.getSelectedRow();if(row<0)return;
        txtKode.setText(tblModel.getValueAt(row,0).toString());txtKode.setEditable(false);
        txtNama.setText(tblModel.getValueAt(row,1).toString());
        txtTelepon.setText(tblModel.getValueAt(row,2)!=null?tblModel.getValueAt(row,2).toString():"");
        txtEmail.setText(tblModel.getValueAt(row,3)!=null?tblModel.getValueAt(row,3).toString():"");
        txtContact.setText(tblModel.getValueAt(row,4)!=null?tblModel.getValueAt(row,4).toString():"");
    }

    private void clearForm(){
        txtKode.setText("");txtKode.setEditable(true);
        txtNama.setText("");txtAlamat.setText("");
        txtTelepon.setText("");txtEmail.setText("");txtContact.setText("");
        tblSupplier.clearSelection();
    }
}