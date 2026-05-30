package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("admin".equals(username) && "admin".equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", username);
            res.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("error", "Username atau password salah!");
            req.getRequestDispatcher("/login.jsp").forward(req, res);
        }
    }
}
