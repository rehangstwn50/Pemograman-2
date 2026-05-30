<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*,model.MataKuliah"%>
<%
    if(session.getAttribute("user")==null){response.sendRedirect(request.getContextPath()+"/login");return;}
    List<MataKuliah> list = (List<MataKuliah>) request.getAttribute("listMataKuliah");
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Mata Kuliah - UNPAM</title>
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
    <h2>Data Mata Kuliah</h2>
    <div class="form-box">
      <b>Tambah Mata Kuliah</b>
      <form method="post" action="${pageContext.request.contextPath}/matakuliah">
        <input type="hidden" name="action" value="tambah"/>
        <div class="form-row">
          <input type="text" name="kode_mk" placeholder="Kode MK (cth: TI301)" required style="width:130px;"/>
          <input type="text" name="nama_mk" placeholder="Nama Mata Kuliah" required style="width:250px;"/>
          <input type="number" name="sks" placeholder="SKS" min="1" max="6" required style="width:70px;"/>
          <button type="submit" class="btn-tambah">+ Tambah</button>
        </div>
      </form>
    </div>
    <div class="tbl-wrap">
    <table class="data-table">
      <tr>
        <th>No</th><th>Kode MK</th><th>Nama Mata Kuliah</th><th>SKS</th><th>Aksi</th>
      </tr>
      <% int no=1; for(MataKuliah mk : list) { %>
      <tr>
        <td><%= no++ %></td>
        <td><%= mk.getKodeMk() %></td>
        <td class="td-left"><%= mk.getNamaMk() %></td>
        <td><%= mk.getSks() %></td>
        <td>
          <form method="post" action="${pageContext.request.contextPath}/matakuliah" style="display:inline;">
            <input type="hidden" name="action" value="hapus"/>
            <input type="hidden" name="kode_mk" value="<%= mk.getKodeMk() %>"/>
            <button type="submit" class="btn-hapus" onclick="return confirm('Hapus <%= mk.getNamaMk() %>?')">Hapus</button>
          </form>
        </td>
      </tr>
      <% } %>
      <% if(list.isEmpty()) { %>
      <tr><td colspan="5" style="padding:20px;color:#999;">Belum ada data mata kuliah.</td></tr>
      <% } %>
    </table>
    </div>
  </div>
</div>
<div class="footer"><p>Copyright &copy; 2016 Universitas Pamulang</p></div>
</body></html>
