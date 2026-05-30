<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*,model.Mahasiswa"%>
<%
    if(session.getAttribute("user")==null){response.sendRedirect(request.getContextPath()+"/login");return;}
    List<Mahasiswa> list = (List<Mahasiswa>) request.getAttribute("listMahasiswa");
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Data Mahasiswa - UNPAM</title>
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
    <h2>Data Mahasiswa</h2>
    <div class="form-box">
      <b>Tambah Mahasiswa</b>
      <form method="post" action="${pageContext.request.contextPath}/mahasiswa">
        <input type="hidden" name="action" value="tambah"/>
        <div class="form-row">
          <input type="text" name="nim" placeholder="NIM" required style="width:110px;"/>
          <input type="text" name="nama" placeholder="Nama Lengkap" required style="width:200px;"/>
          <input type="number" name="semester" placeholder="Sem" min="1" max="8" required style="width:65px;"/>
          <input type="text" name="kelas" placeholder="Kelas (cth: 06TPLM005)" required style="width:160px;"/>
          <input type="text" name="jurusan" placeholder="Jurusan" required style="width:190px;"/>
          <button type="submit" class="btn-tambah">+ Tambah</button>
        </div>
      </form>
    </div>
    <div class="tbl-wrap">
    <table class="data-table">
      <tr>
        <th>No</th><th>NIM</th><th>Nama</th><th>Semester</th><th>Kelas</th><th>Jurusan</th><th>Aksi</th>
      </tr>
      <% int no=1; for(Mahasiswa m : list) { %>
      <tr>
        <td><%= no++ %></td>
        <td><%= m.getNim() %></td>
        <td class="td-left"><%= m.getNama() %></td>
        <td><%= m.getSemester() %></td>
        <td><%= m.getKelas() %></td>
        <td class="td-left"><%= m.getJurusan() %></td>
        <td>
          <form method="post" action="${pageContext.request.contextPath}/mahasiswa" style="display:inline;">
            <input type="hidden" name="action" value="hapus"/>
            <input type="hidden" name="nim" value="<%= m.getNim() %>"/>
            <button type="submit" class="btn-hapus" onclick="return confirm('Hapus <%= m.getNama() %>?')">Hapus</button>
          </form>
        </td>
      </tr>
      <% } %>
      <% if(list.isEmpty()) { %>
      <tr><td colspan="7" style="padding:20px;color:#999;">Belum ada data mahasiswa.</td></tr>
      <% } %>
    </table>
    </div>
  </div>
</div>
<div class="footer"><p>Copyright &copy; 2016 Universitas Pamulang</p></div>
</body></html>
