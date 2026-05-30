package controller;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.Koneksi;
import model.MataKuliah;

public class MataKuliahController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (req.getSession(false) == null || req.getSession(false).getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/login"); return;
        }
        List<MataKuliah> list = new ArrayList<>();
        try (Connection con = Koneksi.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM mata_kuliah ORDER BY kode_mk");
            while (rs.next())
                list.add(new MataKuliah(rs.getString("kode_mk"), rs.getString("nama_mk"), rs.getInt("sks")));
        } catch (Exception e) { e.printStackTrace(); }
        req.setAttribute("listMataKuliah", list);
        req.getRequestDispatcher("/matakuliah.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (req.getSession(false) == null || req.getSession(false).getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/login"); return;
        }
        String action = req.getParameter("action");
        String kodeMk = req.getParameter("kode_mk");
        String namaMk = req.getParameter("nama_mk");
        int sks = 0;
        try { sks = Integer.parseInt(req.getParameter("sks")); } catch (Exception e) {}

        try (Connection con = Koneksi.getConnection()) {
            if ("tambah".equals(action)) {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO mata_kuliah(kode_mk,nama_mk,sks) VALUES(?,?,?)");
                ps.setString(1, kodeMk); ps.setString(2, namaMk); ps.setInt(3, sks);
                ps.executeUpdate();
            } else if ("hapus".equals(action)) {
                PreparedStatement ps = con.prepareStatement("DELETE FROM mata_kuliah WHERE kode_mk=?");
                ps.setString(1, kodeMk); ps.executeUpdate();
            } else if ("edit".equals(action)) {
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE mata_kuliah SET nama_mk=?, sks=? WHERE kode_mk=?");
                ps.setString(1, namaMk); ps.setInt(2, sks); ps.setString(3, kodeMk);
                ps.executeUpdate();
            }
        } catch (Exception e) { e.printStackTrace(); }
        res.sendRedirect(req.getContextPath() + "/matakuliah");
    }
}
