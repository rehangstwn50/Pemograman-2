package com.rentcar.controller;

import com.rentcar.util.DBConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT * FROM users WHERE username=? AND password=MD5(?)")) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HttpSession session = req.getSession();
                    session.setAttribute("username", rs.getString("username"));
                    session.setAttribute("namaUser", rs.getString("nama_lengkap"));
                    session.setAttribute("role", rs.getString("role"));
                    res.sendRedirect(req.getContextPath() + "/dashboard");
                } else {
                    req.setAttribute("error", "Username atau password salah!");
                    req.getRequestDispatcher("/login.jsp").forward(req, res);
                }
            }
        } catch (SQLException e) {
            req.setAttribute("error", "Gagal koneksi database: " + e.getMessage());
            req.getRequestDispatcher("/login.jsp").forward(req, res);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, res);
    }
}
