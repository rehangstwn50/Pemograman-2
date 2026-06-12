package com.rentcar.controller;

import com.rentcar.dao.*;
import com.rentcar.model.TransaksiSewa;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class SewaServlet extends HttpServlet {
    private TransaksiDAO txDAO = new TransaksiDAO();
    private MobilDAO mobilDAO = new MobilDAO();
    private CustomerDAO custDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            req.setAttribute("listCustomer", custDAO.getAll());
            req.setAttribute("listMobil", mobilDAO.getTersedia());
            req.setAttribute("nextNo", txDAO.generateNoTransaksi());
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
        req.getRequestDispatcher("/sewa.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            TransaksiSewa ts = new TransaksiSewa();
            ts.setNoTransaksi(req.getParameter("no_transaksi"));
            ts.setCustomerId(Integer.parseInt(req.getParameter("customer_id")));
            ts.setMobilId(Integer.parseInt(req.getParameter("mobil_id")));
            ts.setTanggalSewa(req.getParameter("tanggal_sewa"));
            ts.setTanggalKembaliRencana(req.getParameter("tanggal_kembali"));
            ts.setLamaSewa(Integer.parseInt(req.getParameter("lama_sewa")));
            ts.setTotalBiaya(Long.parseLong(req.getParameter("total_biaya")));
            String jaminan = req.getParameter("uang_jaminan");
            ts.setUangJaminan(jaminan != null && !jaminan.isEmpty() ? Long.parseLong(jaminan) : 0);
            ts.setCatatan(req.getParameter("catatan"));

            txDAO.insert(ts);
            mobilDAO.updateStatus(ts.getMobilId(), "Disewa");
            req.getSession().setAttribute("successMsg", "Transaksi penyewaan berhasil disimpan!");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMsg", "Gagal: " + e.getMessage());
        }
        res.sendRedirect(req.getContextPath() + "/sewa");
    }
}
