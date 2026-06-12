package com.rentcar.dao;

import com.rentcar.model.Customer;
import com.rentcar.util.DBConnection;
import java.sql.*;
import java.util.*;

public class CustomerDAO {

    public List<Customer> getAll() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY kode_customer";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Customer getById(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM customer WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public String generateKode() throws SQLException {
        String sql = "SELECT kode_customer FROM customer ORDER BY id DESC LIMIT 1";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String last = rs.getString(1);
                int num = Integer.parseInt(last.split("-")[1]) + 1;
                return String.format("CST-%03d", num);
            }
        }
        return "CST-001";
    }

    public void insert(Customer cu) throws SQLException {
        String sql = "INSERT INTO customer (kode_customer,nama_lengkap,no_ktp,tempat_lahir,tanggal_lahir,jenis_kelamin,no_telepon,email,alamat) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cu.getKodeCustomer());
            ps.setString(2, cu.getNamaLengkap());
            ps.setString(3, cu.getNoKtp());
            ps.setString(4, cu.getTempatLahir());
            ps.setString(5, cu.getTanggalLahir());
            ps.setString(6, cu.getJenisKelamin());
            ps.setString(7, cu.getNoTelepon());
            ps.setString(8, cu.getEmail());
            ps.setString(9, cu.getAlamat());
            ps.executeUpdate();
        }
    }

    public void update(Customer cu) throws SQLException {
        String sql = "UPDATE customer SET nama_lengkap=?,no_ktp=?,tempat_lahir=?,tanggal_lahir=?,jenis_kelamin=?,no_telepon=?,email=?,alamat=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cu.getNamaLengkap());
            ps.setString(2, cu.getNoKtp());
            ps.setString(3, cu.getTempatLahir());
            ps.setString(4, cu.getTanggalLahir());
            ps.setString(5, cu.getJenisKelamin());
            ps.setString(6, cu.getNoTelepon());
            ps.setString(7, cu.getEmail());
            ps.setString(8, cu.getAlamat());
            ps.setInt(9, cu.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM customer WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public int countAll() throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM customer");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    private Customer map(ResultSet rs) throws SQLException {
        Customer cu = new Customer();
        cu.setId(rs.getInt("id"));
        cu.setKodeCustomer(rs.getString("kode_customer"));
        cu.setNamaLengkap(rs.getString("nama_lengkap"));
        cu.setNoKtp(rs.getString("no_ktp"));
        cu.setTempatLahir(rs.getString("tempat_lahir"));
        cu.setTanggalLahir(rs.getString("tanggal_lahir") != null ? rs.getString("tanggal_lahir") : "");
        cu.setJenisKelamin(rs.getString("jenis_kelamin"));
        cu.setNoTelepon(rs.getString("no_telepon"));
        cu.setEmail(rs.getString("email"));
        cu.setAlamat(rs.getString("alamat"));
        return cu;
    }
}
