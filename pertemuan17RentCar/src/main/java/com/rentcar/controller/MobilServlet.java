package com.rentcar.controller;

import com.rentcar.dao.MobilDAO;
import com.rentcar.model.Mobil;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class MobilServlet extends HttpServlet {
    private MobilDAO dao = new MobilDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("delete".equals(action)) {
                dao.delete(Integer.parseInt(req.getParameter("id")));
                res.sendRedirect(req.getContextPath() + "/mobil");
                return;
            }
            if ("edit".equals(action)) {
                Mobil m = dao.getById(Integer.parseInt(req.getParameter("id")));
                req.setAttribute("editMobil", m);
            }
            req.setAttribute("listMobil", dao.getAll());
            req.setAttribute("nextKode", dao.generateKode());
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
        req.getRequestDispatcher("/mobil.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            Mobil m = new Mobil();
            m.setKodeMobil(req.getParameter("kode_mobil"));
            m.setNamaMobil(req.getParameter("nama_mobil"));
            m.setMerk(req.getParameter("merk"));
            m.setTahun(Integer.parseInt(req.getParameter("tahun")));
            m.setWarna(req.getParameter("warna"));
            m.setNoPlat(req.getParameter("no_plat"));
            m.setKapasitas(Integer.parseInt(req.getParameter("kapasitas")));
            m.setHargaSewa(Long.parseLong(req.getParameter("harga_sewa")));
            m.setStatus(req.getParameter("status"));

            if ("update".equals(action)) {
                m.setId(Integer.parseInt(req.getParameter("id")));
                dao.update(m);
            } else {
                dao.insert(m);
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
        res.sendRedirect(req.getContextPath() + "/mobil");
    }
}
