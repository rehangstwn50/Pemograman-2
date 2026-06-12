package com.rentcar.controller;

import com.rentcar.dao.*;
import com.rentcar.model.TransaksiSewa;
import com.rentcar.util.DBConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class KembaliServlet extends HttpServlet {
    private TransaksiDAO txDAO = new TransaksiDAO();
    private MobilDAO mobilDAO = new MobilDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String noTx = req.getParameter("no_transaksi");
        try {
            req.setAttribute("listAktif", txDAO.getAktif());
            if (noTx != null && !noTx.isEmpty()) {
                TransaksiSewa ts = txDAO.getByNoTransaksi(noTx);
                req.setAttribute("transaksi", ts);
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
        req.getRequestDispatcher("/kembali.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            int txId = Integer.parseInt(req.getParameter("transaksi_id"));
            int mobilId = Integer.parseInt(req.getParameter("mobil_id"));
            String tglAktual = req.getParameter("tanggal_kembali_aktual");
            String tglRencana = req.getParameter("tanggal_kembali_rencana");
            String kondisi = req.getParameter("kondisi_mobil");
            String keterangan = req.getParameter("keterangan_kerusakan");
            long biayaKerusakan = 0;
            String bk = req.getParameter("biaya_kerusakan");
            if (bk != null && !bk.isEmpty()) biayaKerusakan = Long.parseLong(bk);

            // Hitung denda keterlambatan
            LocalDate aktual = LocalDate.parse(tglAktual);
            LocalDate rencana = LocalDate.parse(tglRencana);
            long terlambat = ChronoUnit.DAYS.between(rencana, aktual);
            long dendaPerHari = Long.parseLong(req.getParameter("harga_sewa"));
            long denda = terlambat > 0 ? terlambat * dendaPerHari : 0;

            long biayaSewa = Long.parseLong(req.getParameter("total_biaya"));
            long jaminan = Long.parseLong(req.getParameter("uang_jaminan"));
            long totalBayar = biayaSewa + denda + biayaKerusakan - jaminan;

            // Simpan ke transaksi_kembali
            String sql = "INSERT INTO transaksi_kembali (transaksi_sewa_id,tanggal_kembali_aktual,kondisi_mobil,keterangan_kerusakan,biaya_kerusakan,denda_keterlambatan,total_bayar) VALUES (?,?,?,?,?,?,?)";
            try (Connection c = DBConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, txId);
                ps.setString(2, tglAktual);
                ps.setString(3, kondisi);
                ps.setString(4, keterangan);
                ps.setLong(5, biayaKerusakan);
                ps.setLong(6, denda);
                ps.setLong(7, totalBayar);
                ps.executeUpdate();
            }

            txDAO.updateStatus(txId, "Selesai");
            mobilDAO.updateStatus(mobilId, "Tersedia");
            req.getSession().setAttribute("successMsg", "Pengembalian berhasil diproses!");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMsg", "Gagal: " + e.getMessage());
        }
        res.sendRedirect(req.getContextPath() + "/kembali");
    }
}
