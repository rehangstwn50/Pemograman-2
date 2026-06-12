<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.rentcar.model.TransaksiSewa" %>
<%
    request.setAttribute("currentPage","dashboard");
    List<TransaksiSewa> recent = (List<TransaksiSewa>) request.getAttribute("transaksiTerbaru");
    int totalMobil = request.getAttribute("totalMobil") != null ? (int)request.getAttribute("totalMobil") : 0;
    int mobilTersedia = request.getAttribute("mobilTersedia") != null ? (int)request.getAttribute("mobilTersedia") : 0;
    int mobilDisewa = request.getAttribute("mobilDisewa") != null ? (int)request.getAttribute("mobilDisewa") : 0;
    int totalCustomer = request.getAttribute("totalCustomer") != null ? (int)request.getAttribute("totalCustomer") : 0;
    String namaUser = (String) session.getAttribute("namaUser");
%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Dashboard — RentCar</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/sidebar.jsp"/>
<div class="topbar">
    <span class="topbar-title">Dashboard</span>
    <div class="topbar-search">
        <span>🔍</span>
        <input type="text" placeholder="Search data...">
    </div>
    <div class="topbar-icons">
        <a href="#" title="Notifikasi">🔔</a>
        <a href="#" title="Pengaturan">⚙</a>
    </div>
    <div class="user-chip">
        <div class="av"><%= namaUser != null ? namaUser.substring(0,1).toUpperCase() : "A" %></div>
        <span><%= namaUser != null ? namaUser : "Admin" %></span>
    </div>
</div>

<div class="main-content">
    <% if (request.getAttribute("dbError") != null) { %>
    <div class="alert alert-danger">⚠ Koneksi database gagal: <%= request.getAttribute("dbError") %></div>
    <% } %>

    <div class="stat-cards">
        <div class="stat-card">
            <div class="stat-icon blue">🚗</div>
            <div class="stat-info">
                <p>Total Mobil</p>
                <h2><%= totalMobil %></h2>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon green">✅</div>
            <div class="stat-info">
                <p>Mobil Tersedia</p>
                <h2><%= mobilTersedia %></h2>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon amber">🔑</div>
            <div class="stat-info">
                <p>Mobil Disewa</p>
                <h2><%= mobilDisewa %></h2>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon teal">👥</div>
            <div class="stat-info">
                <p>Total Customer</p>
                <h2><%= totalCustomer %></h2>
            </div>
        </div>
    </div>

    <div class="card">
        <div class="card-header">
            <h3>Transaksi Terbaru</h3>
            <a href="<%= request.getContextPath() %>/laporan" class="btn btn-secondary btn-sm">Lihat Semua</a>
        </div>
        <div class="card-body">
            <table>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>No Transaksi</th>
                        <th>Nama Customer</th>
                        <th>Mobil</th>
                        <th>Tgl Sewa</th>
                        <th>Tgl Kembali</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                <% if (recent != null && !recent.isEmpty()) {
                    int i = 1;
                    for (TransaksiSewa t : recent) { %>
                <tr>
                    <td><%= i++ %></td>
                    <td><strong><%= t.getNoTransaksi() %></strong></td>
                    <td><%= t.getNamaCustomer() %></td>
                    <td><%= t.getNamaMobil() %></td>
                    <td><%= t.getTanggalSewa() %></td>
                    <td><%= t.getTanggalKembaliRencana() %></td>
                    <td>
                        <% String st = t.getStatus();
                           String bc = "Aktif".equals(st) ? "badge-info" : "Selesai".equals(st) ? "badge-success" : "badge-danger"; %>
                        <span class="badge <%= bc %>"><%= st %></span>
                    </td>
                </tr>
                <% } } else { %>
                <tr><td colspan="7" style="text-align:center;padding:24px;color:#a0aec0;">Belum ada transaksi</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
