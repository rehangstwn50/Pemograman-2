package controller;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.Koneksi;
import model.Nilai;
import model.Mahasiswa;
import model.MataKuliah;

public class NilaiController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        if (req.getSession(false) == null ||
            req.getSession(false).getAttribute("user") == null) {

            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Nilai> list = new ArrayList<>();
        List<Mahasiswa> listMhs = new ArrayList<>();
        List<MataKuliah> listMk = new ArrayList<>();

        try (Connection con = Koneksi.getConnection()) {

            ResultSet rs = con.createStatement().executeQuery(
                "SELECT n.id, n.nim, m.nama, m.semester, m.kelas, " +
                "n.kode_mk, mk.nama_mk, mk.sks, n.tugas, n.uts, " +
                "n.uas, n.nilai_akhir, n.huruf, n.status " +
                "FROM nilai n " +
                "JOIN mahasiswa m ON n.nim=m.nim " +
                "JOIN mata_kuliah mk ON n.kode_mk=mk.kode_mk " +
                "ORDER BY n.id"
            );

            while (rs.next()) {

                Nilai n = new Nilai();

                n.setId(rs.getInt("id"));
                n.setNim(rs.getString("nim"));
                n.setNamaMahasiswa(rs.getString("nama"));
                n.setSemester(rs.getInt("semester"));
                n.setKelas(rs.getString("kelas"));
                n.setKodeMk(rs.getString("kode_mk"));
                n.setNamaMk(rs.getString("nama_mk"));
                n.setSks(rs.getInt("sks"));
                n.setTugas(rs.getDouble("tugas"));
                n.setUts(rs.getDouble("uts"));
                n.setUas(rs.getDouble("uas"));
                n.setNilaiAkhir(rs.getDouble("nilai_akhir"));
                n.setHuruf(rs.getString("huruf"));
                n.setStatus(rs.getString("status"));

                list.add(n);
            }

            ResultSet rsMhs = con.createStatement().executeQuery(
                "SELECT * FROM mahasiswa ORDER BY nim"
            );

            while (rsMhs.next()) {

                listMhs.add(
                    new Mahasiswa(
                        rsMhs.getString("nim"),
                        rsMhs.getString("nama"),
                        rsMhs.getInt("semester"),
                        rsMhs.getString("kelas"),
                        rsMhs.getString("jurusan")
                    )
                );
            }

            ResultSet rsMk = con.createStatement().executeQuery(
                "SELECT * FROM mata_kuliah ORDER BY kode_mk"
            );

            while (rsMk.next()) {

                listMk.add(
                    new MataKuliah(
                        rsMk.getString("kode_mk"),
                        rsMk.getString("nama_mk"),
                        rsMk.getInt("sks")
                    )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("listNilai", list);
        req.setAttribute("listMahasiswa", listMhs);
        req.setAttribute("listMataKuliah", listMk);

        req.getRequestDispatcher("/nilai.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        if (req.getSession(false) == null ||
            req.getSession(false).getAttribute("user") == null) {

            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");

        try (Connection con = Koneksi.getConnection()) {

            // TAMBAH DATA
            if ("tambah".equals(action)) {

                double tugas = Double.parseDouble(req.getParameter("tugas"));
                double uts   = Double.parseDouble(req.getParameter("uts"));
                double uas   = Double.parseDouble(req.getParameter("uas"));

                double nilaiAkhir =
                        Math.round(((tugas * 0.30) +
                                    (uts * 0.35) +
                                    (uas * 0.35)) * 10.0) / 10.0;

                String huruf;
                String status;

                if (nilaiAkhir >= 80) {
                    huruf = "A";
                    status = "Lulus";
                } else if (nilaiAkhir >= 70) {
                    huruf = "B";
                    status = "Lulus";
                } else if (nilaiAkhir >= 60) {
                    huruf = "C";
                    status = "Lulus";
                } else if (nilaiAkhir >= 50) {
                    huruf = "D";
                    status = "Lulus";
                } else {
                    huruf = "E";
                    status = "Tidak Lulus";
                }

                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO nilai " +
                    "(nim,kode_mk,tugas,uts,uas,nilai_akhir,huruf,status) " +
                    "VALUES(?,?,?,?,?,?,?,?)"
                );

                ps.setString(1, req.getParameter("nim"));
                ps.setString(2, req.getParameter("kode_mk"));
                ps.setDouble(3, tugas);
                ps.setDouble(4, uts);
                ps.setDouble(5, uas);
                ps.setDouble(6, nilaiAkhir);
                ps.setString(7, huruf);
                ps.setString(8, status);

                ps.executeUpdate();

                // PESAN DIALOG TAMBAH
                req.getSession().setAttribute(
                    "pesan",
                    "Data nilai berhasil ditambahkan!"
                );

            }

            // HAPUS DATA
            else if ("hapus".equals(action)) {

                PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM nilai WHERE id=?"
                );

                ps.setInt(
                    1,
                    Integer.parseInt(req.getParameter("id"))
                );

                ps.executeUpdate();

                // PESAN DIALOG HAPUS
                req.getSession().setAttribute(
                    "pesan",
                    "Data nilai berhasil dihapus!"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            req.getSession().setAttribute(
                "pesan",
                "Terjadi kesalahan!"
            );
        }

        res.sendRedirect(req.getContextPath() + "/nilai");
    }
}