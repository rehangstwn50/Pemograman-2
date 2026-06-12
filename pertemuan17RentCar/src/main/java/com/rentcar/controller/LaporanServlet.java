package com.rentcar.controller;

import com.rentcar.dao.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LaporanServlet extends HttpServlet {
    private TransaksiDAO txDAO = new TransaksiDAO();
    private MobilDAO mobilDAO = new MobilDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String dari = req.getParameter("dari");
        String sampai = req.getParameter("sampai");
        String jenis = req.getParameter("jenis");
        String mobilId = req.getParameter("mobil_id");

        try {
            req.setAttribute("listMobil", mobilDAO.getAll());
            if (dari != null && !dari.isEmpty()) {
                var list = txDAO.getLaporan(dari, sampai, jenis, mobilId);
                req.setAttribute("listLaporan", list);
                long total = list.stream().mapToLong(t -> t.getTotalBiaya()).sum();
                req.setAttribute("totalPendapatan", total);
                req.setAttribute("totalTransaksi", list.size());
                long avgSewa = list.isEmpty() ? 0 : list.stream().mapToLong(t -> t.getLamaSewa()).sum() / list.size();
                req.setAttribute("avgLamaSewa", avgSewa);
                req.setAttribute("dari", dari);
                req.setAttribute("sampai", sampai);
                req.setAttribute("jenis", jenis);
                req.setAttribute("mobilId", mobilId);
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
        req.getRequestDispatcher("/laporan.jsp").forward(req, res);
    }
}
