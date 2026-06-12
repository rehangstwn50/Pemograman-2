package com.rentcar.controller;

import com.rentcar.dao.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            MobilDAO mobilDAO = new MobilDAO();
            CustomerDAO custDAO = new CustomerDAO();
            TransaksiDAO txDAO = new TransaksiDAO();

            req.setAttribute("totalMobil", mobilDAO.countAll());
            req.setAttribute("mobilTersedia", mobilDAO.countByStatus("Tersedia"));
            req.setAttribute("mobilDisewa", mobilDAO.countByStatus("Disewa"));
            req.setAttribute("totalCustomer", custDAO.countAll());
            req.setAttribute("transaksiTerbaru", txDAO.getRecent(5));
        } catch (Exception e) {
            req.setAttribute("dbError", e.getMessage());
        }
        req.getRequestDispatcher("/dashboard.jsp").forward(req, res);
    }
}
