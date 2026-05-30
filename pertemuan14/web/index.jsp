<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% if(session.getAttribute("user")==null){response.sendRedirect(request.getContextPath()+"/login");return;} %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Dashboard - UNPAM</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css"></head>
<body>
<%@ include file="header.jsp" %>
<div class="content">
  <div class="sidebar">
    <div class="sidebar-section">Master Data</div>
    <a href="${pageContext.request.contextPath}/mahasiswa" class="sidebar-link">Mahasiswa</a>
    <a href="${pageContext.request.contextPath}/matakuliah" class="sidebar-link">Mata Kuliah</a>
    <div class="sidebar-section">Transaksi</div>
    <a href="${pageContext.request.contextPath}/nilai" class="sidebar-link">Nilai</a>
    <div class="sidebar-section">Laporan</div>
    <a href="${pageContext.request.contextPath}/laporan" class="sidebar-link">Nilai</a>
    <div class="sidebar-section">Akun</div>
    <a href="${pageContext.request.contextPath}/logout" class="sidebar-link">Logout</a>
  </div>
  <div class="main">
    <div class="welcome-box">
      <h2>Selamat Datang</h2>
      <p>Sistem Informasi Akademik Universitas Pamulang.<br/>Gunakan menu di samping atau navbar di atas untuk mengelola data.</p>
    </div>
  </div>
</div>
<div class="footer"><p>Copyright &copy; 2016 Universitas Pamulang</p><p>Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten</p></div>
</body></html>
