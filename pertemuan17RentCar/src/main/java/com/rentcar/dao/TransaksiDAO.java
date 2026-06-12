package com.rentcar.dao;

import com.rentcar.model.TransaksiSewa;
import com.rentcar.util.DBConnection;
import java.sql.*;
import java.util.*;

public class TransaksiDAO {

    public List<TransaksiSewa> getAll() throws SQLException {
        List<TransaksiSewa> list = new ArrayList<>();
        String sql = "SELECT ts.*, c.nama_lengkap, m.nama_mobil, m.no_plat " +
                     "FROM transaksi_sewa ts " +
                     "JOIN customer c ON ts.customer_id=c.id " +
                     "JOIN mobil m ON ts.mobil_id=m.id " +
                     "ORDER BY ts.id DESC";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapJoin(rs));
        }
        return list;
    }

    public List<TransaksiSewa> getAktif() throws SQLException {
        List<TransaksiSewa> list = new ArrayList<>();
        String sql = "SELECT ts.*, c.nama_lengkap, m.nama_mobil, m.no_plat " +
                     "FROM transaksi_sewa ts " +
                     "JOIN customer c ON ts.customer_id=c.id " +
                     "JOIN mobil m ON ts.mobil_id=m.id " +
                     "WHERE ts.status='Aktif' ORDER BY ts.id DESC";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapJoin(rs));
        }
        return list;
    }

    public TransaksiSewa getById(int id) throws SQLException {
        String sql = "SELECT ts.*, c.nama_lengkap, m.nama_mobil, m.no_plat " +
                     "FROM transaksi_sewa ts " +
                     "JOIN customer c ON ts.customer_id=c.id " +
                     "JOIN mobil m ON ts.mobil_id=m.id WHERE ts.id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapJoin(rs);
            }
        }
        return null;
    }

    public TransaksiSewa getByNoTransaksi(String no) throws SQLException {
        String sql = "SELECT ts.*, c.nama_lengkap, m.nama_mobil, m.no_plat " +
                     "FROM transaksi_sewa ts " +
                     "JOIN customer c ON ts.customer_id=c.id " +
                     "JOIN mobil m ON ts.mobil_id=m.id WHERE ts.no_transaksi=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, no);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapJoin(rs);
            }
        }
        return null;
    }

    public String generateNoTransaksi() throws SQLException {
        String sql = "SELECT no_transaksi FROM transaksi_sewa ORDER BY id DESC LIMIT 1";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String last = rs.getString(1);
                String[] parts = last.split("-");
                int num = Integer.parseInt(parts[parts.length - 1]) + 1;
                return String.format("TR-%d-%03d", java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), num);
            }
        }
        return String.format("TR-%d-001", java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
    }

    public void insert(TransaksiSewa ts) throws SQLException {
        String sql = "INSERT INTO transaksi_sewa (no_transaksi,customer_id,mobil_id,tanggal_sewa,tanggal_kembali_rencana,lama_sewa,total_biaya,uang_jaminan,catatan,status) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, ts.getNoTransaksi());
            ps.setInt(2, ts.getCustomerId());
            ps.setInt(3, ts.getMobilId());
            ps.setString(4, ts.getTanggalSewa());
            ps.setString(5, ts.getTanggalKembaliRencana());
            ps.setInt(6, ts.getLamaSewa());
            ps.setLong(7, ts.getTotalBiaya());
            ps.setLong(8, ts.getUangJaminan());
            ps.setString(9, ts.getCatatan());
            ps.setString(10, "Aktif");
            ps.executeUpdate();
        }
    }

    public void updateStatus(int id, String status) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE transaksi_sewa SET status=? WHERE id=?")) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public List<TransaksiSewa> getLaporan(String dari, String sampai, String jenis, String mobilId) throws SQLException {
        List<TransaksiSewa> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT ts.*, c.nama_lengkap, m.nama_mobil, m.no_plat " +
            "FROM transaksi_sewa ts " +
            "JOIN customer c ON ts.customer_id=c.id " +
            "JOIN mobil m ON ts.mobil_id=m.id WHERE 1=1");
        if (dari != null && !dari.isEmpty()) sql.append(" AND ts.tanggal_sewa >= '").append(dari).append("'");
        if (sampai != null && !sampai.isEmpty()) sql.append(" AND ts.tanggal_sewa <= '").append(sampai).append("'");
        if (jenis != null && !jenis.isEmpty() && !jenis.equals("Semua")) sql.append(" AND ts.status='").append(jenis).append("'");
        if (mobilId != null && !mobilId.isEmpty() && !mobilId.equals("0")) sql.append(" AND ts.mobil_id=").append(mobilId);
        sql.append(" ORDER BY ts.tanggal_sewa DESC");
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapJoin(rs));
        }
        return list;
    }

    public int countAktif() throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM transaksi_sewa WHERE status='Aktif'");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public List<TransaksiSewa> getRecent(int limit) throws SQLException {
        List<TransaksiSewa> list = new ArrayList<>();
        String sql = "SELECT ts.*, c.nama_lengkap, m.nama_mobil, m.no_plat " +
                     "FROM transaksi_sewa ts " +
                     "JOIN customer c ON ts.customer_id=c.id " +
                     "JOIN mobil m ON ts.mobil_id=m.id " +
                     "ORDER BY ts.id DESC LIMIT " + limit;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapJoin(rs));
        }
        return list;
    }

    private TransaksiSewa mapJoin(ResultSet rs) throws SQLException {
        TransaksiSewa ts = new TransaksiSewa();
        ts.setId(rs.getInt("id"));
        ts.setNoTransaksi(rs.getString("no_transaksi"));
        ts.setCustomerId(rs.getInt("customer_id"));
        ts.setMobilId(rs.getInt("mobil_id"));
        ts.setTanggalSewa(rs.getString("tanggal_sewa"));
        ts.setTanggalKembaliRencana(rs.getString("tanggal_kembali_rencana"));
        ts.setLamaSewa(rs.getInt("lama_sewa"));
        ts.setTotalBiaya(rs.getLong("total_biaya"));
        ts.setUangJaminan(rs.getLong("uang_jaminan"));
        ts.setCatatan(rs.getString("catatan"));
        ts.setStatus(rs.getString("status"));
        ts.setNamaCustomer(rs.getString("nama_lengkap"));
        ts.setNamaMobil(rs.getString("nama_mobil"));
        ts.setNoPlat(rs.getString("no_plat"));
        return ts;
    }
}
