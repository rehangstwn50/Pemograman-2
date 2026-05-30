package controller;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.Koneksi;

public class LaporanNilaiController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        if (req.getSession(false) == null ||
            req.getSession(false).getAttribute("user") == null) {

            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {

            Connection con = Koneksi.getConnection();

            ResultSet rs = con.createStatement().executeQuery(
                "SELECT n.id, n.nim, m.nama, m.semester, m.kelas, " +
                "n.kode_mk, mk.nama_mk, mk.sks, n.tugas, n.uts, " +
                "n.uas, n.nilai_akhir, n.huruf, n.status " +
                "FROM nilai n " +
                "JOIN mahasiswa m ON n.nim = m.nim " +
                "JOIN mata_kuliah mk ON n.kode_mk = mk.kode_mk " +
                "ORDER BY n.nim, n.kode_mk"
            );

            String nim = "";
            String nama = "";
            String kelas = "";
            String semester = "";

            java.util.List<String[]> rows = new java.util.ArrayList<>();

            while (rs.next()) {

                if (nim.isEmpty()) {
                    nim = rs.getString("nim");
                    nama = rs.getString("nama");
                    kelas = rs.getString("kelas");
                    semester = String.valueOf(rs.getInt("semester"));
                }

                rows.add(new String[]{
                    rs.getString("kode_mk"),
                    rs.getString("nama_mk"),
                    String.valueOf(rs.getInt("sks")),
                    String.valueOf(rs.getDouble("tugas")),
                    String.valueOf(rs.getDouble("uts")),
                    String.valueOf(rs.getDouble("uas")),
                    String.valueOf(rs.getDouble("nilai_akhir")),
                    rs.getString("huruf"),
                    rs.getString("status")
                });
            }

            res.setContentType("text/html;charset=UTF-8");

            PrintWriter out = res.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Laporan Nilai</title>");

            out.println("<style>");

            out.println("*{");
            out.println("margin:0;");
            out.println("padding:0;");
            out.println("box-sizing:border-box;");
            out.println("font-family:Arial,sans-serif;");
            out.println("}");

            out.println("body{");
            out.println("background:#ffffff;");
            out.println("padding:30px 40px;");
            out.println("color:#222;");
            out.println("}");

            out.println(".toolbar{");
            out.println("margin-bottom:20px;");
            out.println("display:flex;");
            out.println("gap:10px;");
            out.println("}");

            out.println(".btn{");
            out.println("padding:8px 20px;");
            out.println("border:none;");
            out.println("border-radius:4px;");
            out.println("cursor:pointer;");
            out.println("font-size:13px;");
            out.println("text-decoration:none;");
            out.println("display:inline-block;");
            out.println("}");

            out.println(".btn-print{");
            out.println("background:#003399;");
            out.println("color:white;");
            out.println("}");

            out.println(".btn-back{");
            out.println("background:#666;");
            out.println("color:white;");
            out.println("}");

            out.println(".judul{");
            out.println("text-align:center;");
            out.println("margin-bottom:25px;");
            out.println("}");

            out.println(".logo{");
            out.println("width:95px;");
            out.println("height:95px;");
            out.println("object-fit:contain;");
            out.println("margin-bottom:10px;");
            out.println("}");

            out.println(".judul h2{");
            out.println("font-size:15px;");
            out.println("font-weight:normal;");
            out.println("letter-spacing:1px;");
            out.println("}");

            out.println(".judul h1{");
            out.println("font-size:24px;");
            out.println("font-weight:bold;");
            out.println("letter-spacing:2px;");
            out.println("margin:4px 0;");
            out.println("color:#003399;");
            out.println("}");

            out.println(".judul p{");
            out.println("font-size:12px;");
            out.println("color:#444;");
            out.println("}");

            out.println(".info{");
            out.println("margin-bottom:16px;");
            out.println("font-size:13px;");
            out.println("}");

            out.println(".info table{");
            out.println("border:none;");
            out.println("}");

            out.println(".info td{");
            out.println("padding:3px 6px;");
            out.println("border:none;");
            out.println("vertical-align:top;");
            out.println("}");

            out.println(".info td:first-child{");
            out.println("width:90px;");
            out.println("}");

            out.println(".info td:nth-child(2){");
            out.println("width:10px;");
            out.println("}");

            out.println("table.data{");
            out.println("width:100%;");
            out.println("border-collapse:collapse;");
            out.println("font-size:13px;");
            out.println("}");

            out.println("table.data th{");
            out.println("background:#003399;");
            out.println("color:white;");
            out.println("padding:8px 10px;");
            out.println("border:1px solid #2244aa;");
            out.println("text-align:center;");
            out.println("}");

            out.println("table.data td{");
            out.println("padding:7px 10px;");
            out.println("border:1px solid #ccc;");
            out.println("text-align:center;");
            out.println("}");

            out.println("table.data td.td-left{");
            out.println("text-align:left;");
            out.println("}");

            out.println("table.data tr:nth-child(even) td{");
            out.println("background:#f5f8ff;");
            out.println("}");

            out.println(".footer{");
            out.println("text-align:center;");
            out.println("margin-top:20px;");
            out.println("font-size:11px;");
            out.println("color:#666;");
            out.println("border-top:1px solid #ccc;");
            out.println("padding-top:10px;");
            out.println("}");

            out.println("@media print{");
            out.println(".toolbar{display:none;}");
            out.println("body{padding:15px 20px;}");
            out.println("}");

            out.println("</style>");
            out.println("</head>");

            out.println("<body>");

            out.println("<div class='toolbar'>");

            out.println("<button class='btn btn-print' onclick='window.print()'>");
            out.println("&#128438; Cetak / Simpan PDF");
            out.println("</button>");

            out.println("<a href='" + req.getContextPath() + "/nilai' class='btn btn-back'>");
            out.println("&#8592; Kembali");
            out.println("</a>");

            out.println("</div>");

            // HEADER + LOGO
            out.println("<div class='judul'>");

            out.println("<img src='" + req.getContextPath() + "/logo.png' class='logo'>");

            out.println("<h2>LAPORAN NILAI MAHASISWA</h2>");

            out.println("<h1>UNIVERSITAS PAMULANG</h1>");

            out.println("<p>Jl. Surya Kencana No. 1 Pamulang, Tangerang, Banten</p>");

            out.println("</div>");

            // INFO MAHASISWA
            out.println("<div class='info'>");
            out.println("<table>");

            out.println("<tr>");
            out.println("<td>Semester</td>");
            out.println("<td>:</td>");
            out.println("<td>" + semester + "</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>Kelas</td>");
            out.println("<td>:</td>");
            out.println("<td>" + kelas + "</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>NIM</td>");
            out.println("<td>:</td>");
            out.println("<td>" + nim + "</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>Nama</td>");
            out.println("<td>:</td>");
            out.println("<td>" + nama + "</td>");
            out.println("</tr>");

            out.println("</table>");
            out.println("</div>");

            // TABEL
            out.println("<table class='data'>");

            out.println("<tr>");
            out.println("<th>No.</th>");
            out.println("<th>Kode MK</th>");
            out.println("<th>Nama Mata Kuliah</th>");
            out.println("<th>SKS</th>");
            out.println("<th>Tugas</th>");
            out.println("<th>UTS</th>");
            out.println("<th>UAS</th>");
            out.println("<th>Nilai Akhir</th>");
            out.println("<th>Huruf</th>");
            out.println("<th>Status</th>");
            out.println("</tr>");

            int no = 1;

            for (String[] r : rows) {

                out.println("<tr>");

                out.println("<td>" + no++ + "</td>");
                out.println("<td>" + r[0] + "</td>");

                out.println("<td class='td-left'>");
                out.println(r[1]);
                out.println("</td>");

                out.println("<td>" + r[2] + "</td>");
                out.println("<td>" + r[3] + "</td>");
                out.println("<td>" + r[4] + "</td>");
                out.println("<td>" + r[5] + "</td>");

                out.println("<td><b>" + r[6] + "</b></td>");

                out.println("<td><b>" + r[7] + "</b></td>");

                out.println("<td style='color:"
                        + ("Lulus".equals(r[8]) ? "green" : "red")
                        + ";font-weight:bold;'>"
                        + r[8]
                        + "</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<div class='footer'>");
            out.println("Copyright &copy; 2016 Universitas Pamulang");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            res.getWriter().println("Error : " + e.getMessage());
        }
    }
}