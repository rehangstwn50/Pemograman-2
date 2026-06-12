<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String currentPage = (String) request.getAttribute("currentPage");
    if (currentPage == null) currentPage = "";
    String namaUser = (String) session.getAttribute("namaUser");
    if (namaUser == null) namaUser = "Admin";
    String initials = namaUser.length() > 1 ? namaUser.substring(0,1).toUpperCase() : "A";
%>
<div class="sidebar">
    <div class="sidebar-brand">
        <h1>FleetManager</h1>
        <p>Admin Console</p>
    </div>
    <nav class="sidebar-nav">
        <a href="<%= request.getContextPath() %>/dashboard" class="nav-item <%= "dashboard".equals(currentPage)?"active":"" %>">
            <span>⊞</span> Dashboard
        </a>
        <a href="<%= request.getContextPath() %>/mobil" class="nav-item <%= "mobil".equals(currentPage)?"active":"" %>">
            <span>🚗</span> Data Mobil
        </a>
        <a href="<%= request.getContextPath() %>/customer" class="nav-item <%= "customer".equals(currentPage)?"active":"" %>">
            <span>👥</span> Data Customer
        </a>
        <a href="<%= request.getContextPath() %>/sewa" class="nav-item <%= "sewa".equals(currentPage)?"active":"" %>">
            <span>📋</span> Transaksi Sewa
        </a>
        <a href="<%= request.getContextPath() %>/kembali" class="nav-item <%= "kembali".equals(currentPage)?"active":"" %>">
            <span>↩</span> Transaksi Kembali
        </a>
        <a href="<%= request.getContextPath() %>/laporan" class="nav-item <%= "laporan".equals(currentPage)?"active":"" %>">
            <span>📊</span> Laporan
        </a>
    </nav>
    <div class="sidebar-footer">
        <div class="avatar"><%= initials %></div>
        <div class="user-info">
            <p><%= namaUser %></p>
            <span><%= session.getAttribute("role") %></span>
        </div>
    </div>
</div>
