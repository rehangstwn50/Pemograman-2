package com.rentcar.dao;

import com.rentcar.model.Mobil;
import com.rentcar.util.DBConnection;
import java.sql.*;
import java.util.*;

public class MobilDAO {

    public List<Mobil> getAll() throws SQLException {
        List<Mobil> list = new ArrayList<>();
        String sql = "SELECT * FROM mobil ORDER BY kode_mobil";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Mobil> getTersedia() throws SQLException {
        List<Mobil> list = new ArrayList<>();
        String sql = "SELECT * FROM mobil WHERE status='Tersedia' ORDER BY kode_mobil";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Mobil getById(int id) throws SQLException {
        String sql = "SELECT * FROM mobil WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public String generateKode() throws SQLException {
        String sql = "SELECT kode_mobil FROM mobil ORDER BY id DESC LIMIT 1";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String last = rs.getString(1);
                int num = Integer.parseInt(last.split("-")[1]) + 1;
                return String.format("MBL-%03d", num);
            }
        }
        return "MBL-001";
    }

    public void insert(Mobil m) throws SQLException {
        String sql = "INSERT INTO mobil (kode_mobil,nama_mobil,merk,tahun,warna,no_plat,kapasitas,harga_sewa,status) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getKodeMobil());
            ps.setString(2, m.getNamaMobil());
            ps.setString(3, m.getMerk());
            ps.setInt(4, m.getTahun());
            ps.setString(5, m.getWarna());
            ps.setString(6, m.getNoPlat());
            ps.setInt(7, m.getKapasitas());
            ps.setLong(8, m.getHargaSewa());
            ps.setString(9, m.getStatus());
            ps.executeUpdate();
        }
    }

    public void update(Mobil m) throws SQLException {
        String sql = "UPDATE mobil SET nama_mobil=?,merk=?,tahun=?,warna=?,no_plat=?,kapasitas=?,harga_sewa=?,status=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getNamaMobil());
            ps.setString(2, m.getMerk());
            ps.setInt(3, m.getTahun());
            ps.setString(4, m.getWarna());
            ps.setString(5, m.getNoPlat());
            ps.setInt(6, m.getKapasitas());
            ps.setLong(7, m.getHargaSewa());
            ps.setString(8, m.getStatus());
            ps.setInt(9, m.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM mobil WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE mobil SET status=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public int countAll() throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM mobil");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int countByStatus(String status) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM mobil WHERE status=?")) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    private Mobil map(ResultSet rs) throws SQLException {
        Mobil m = new Mobil();
        m.setId(rs.getInt("id"));
        m.setKodeMobil(rs.getString("kode_mobil"));
        m.setNamaMobil(rs.getString("nama_mobil"));
        m.setMerk(rs.getString("merk"));
        m.setTahun(rs.getInt("tahun"));
        m.setWarna(rs.getString("warna"));
        m.setNoPlat(rs.getString("no_plat"));
        m.setKapasitas(rs.getInt("kapasitas"));
        m.setHargaSewa(rs.getLong("harga_sewa"));
        m.setStatus(rs.getString("status"));
        return m;
    }
}
