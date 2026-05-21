package com.unpam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HitungNilai extends HttpServlet {

    private String tentukanGrade(double nilai) {
        if (nilai >= 85) return "A";
        if (nilai >= 75) return "B";
        if (nilai >= 65) return "C";
        if (nilai >= 50) return "D";
        return "E";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String nama = request.getParameter("nama");
        String nim = request.getParameter("nim");
        double tugas = Double.parseDouble(request.getParameter("tugas"));
        double uts = Double.parseDouble(request.getParameter("uts"));
        double uas = Double.parseDouble(request.getParameter("uas"));

        double nilaiAkhir = (tugas * 0.30) + (uts * 0.30) + (uas * 0.40);
        String grade = tentukanGrade(nilaiAkhir);

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset='UTF-8'><title>Hasil Nilai</title>");
            out.println("<link rel='stylesheet' href='assets/style.css'></head><body>");
            out.println("<div class='card'><h1>Hasil Perhitungan Nilai</h1>");
            out.println("<table>");
            out.println("<tr><td>Nama</td><td>" + nama + "</td></tr>");
            out.println("<tr><td>NIM</td><td>" + nim + "</td></tr>");
            out.println("<tr><td>Nilai Tugas</td><td>" + tugas + "</td></tr>");
            out.println("<tr><td>Nilai UTS</td><td>" + uts + "</td></tr>");
            out.println("<tr><td>Nilai UAS</td><td>" + uas + "</td></tr>");
            out.println("<tr><td>Nilai Akhir</td><td>" + String.format("%.2f", nilaiAkhir) + "</td></tr>");
            out.println("</table>");
            out.println("<div class='grade'>Grade: " + grade + "</div>");
            out.println("<p class='center'><a href='index.jsp'>Hitung Lagi</a></p>");
            out.println("</div></body></html>");
        }
    }
}
