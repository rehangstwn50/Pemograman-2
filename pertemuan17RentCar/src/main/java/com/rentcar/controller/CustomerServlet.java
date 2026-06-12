package com.rentcar.controller;

import com.rentcar.dao.CustomerDAO;
import com.rentcar.model.Customer;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {
    private CustomerDAO dao = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("delete".equals(action)) {
                dao.delete(Integer.parseInt(req.getParameter("id")));
                res.sendRedirect(req.getContextPath() + "/customer");
                return;
            }
            if ("edit".equals(action)) {
                Customer cu = dao.getById(Integer.parseInt(req.getParameter("id")));
                req.setAttribute("editCustomer", cu);
            }
            req.setAttribute("listCustomer", dao.getAll());
            req.setAttribute("nextKode", dao.generateKode());
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
        req.getRequestDispatcher("/customer.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            Customer cu = new Customer();
            cu.setKodeCustomer(req.getParameter("kode_customer"));
            cu.setNamaLengkap(req.getParameter("nama_lengkap"));
            cu.setNoKtp(req.getParameter("no_ktp"));
            cu.setTempatLahir(req.getParameter("tempat_lahir"));
            cu.setTanggalLahir(req.getParameter("tanggal_lahir"));
            cu.setJenisKelamin(req.getParameter("jenis_kelamin"));
            cu.setNoTelepon(req.getParameter("no_telepon"));
            cu.setEmail(req.getParameter("email"));
            cu.setAlamat(req.getParameter("alamat"));

            if ("update".equals(action)) {
                cu.setId(Integer.parseInt(req.getParameter("id")));
                dao.update(cu);
            } else {
                dao.insert(cu);
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
        res.sendRedirect(req.getContextPath() + "/customer");
    }
}
