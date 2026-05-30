package controller;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.Koneksi;
import model.Mahasiswa;

public class MahasiswaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (req.getSession(false) == null || req.getSession(false).getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/login"); return;
        }
        List<Mahasiswa> list = new ArrayList<>();
        try (Connection con = Koneksi.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM mahasiswa ORDER BY nim");
            while (rs.next())
                list.add(new Mahasiswa(
                    rs.getString("nim"), rs.getString("nama"),
                    rs.getInt("semester"), rs.getString("kelas"), rs.getString("jurusan")));
        } catch (Exception e) { e.printStackTrace(); }
        req.setAttribute("listMahasiswa", list);
        req.getRequestDispatcher("/mahasiswa.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (req.getSession(false) == null || req.getSession(false).getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/login"); return;
        }
        String action  = req.getParameter("action");
        String nim     = req.getParameter("nim");
        String nama    = req.getParameter("nama");
        String kelas   = req.getParameter("kelas");
        String jurusan = req.getParameter("jurusan");
        int semester   = 1;
        try { semester = Integer.parseInt(req.getParameter("semester")); } catch (Exception e) {}

        try (Connection con = Koneksi.getConnection()) {
            if ("tambah".equals(action)) {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO mahasiswa(nim,nama,semester,kelas,jurusan) VALUES(?,?,?,?,?)");
                ps.setString(1, nim); ps.setString(2, nama); ps.setInt(3, semester);
                ps.setString(4, kelas); ps.setString(5, jurusan);
                ps.executeUpdate();
            } else if ("hapus".equals(action)) {
                PreparedStatement ps = con.prepareStatement("DELETE FROM mahasiswa WHERE nim=?");
                ps.setString(1, nim); ps.executeUpdate();
            }
        } catch (Exception e) { e.printStackTrace(); }
        res.sendRedirect(req.getContextPath() + "/mahasiswa");
    }
}
