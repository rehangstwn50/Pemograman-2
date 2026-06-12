<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.rentcar.model.TransaksiSewa, com.rentcar.model.Mobil" %>
<%
    request.setAttribute("currentPage","laporan");
    List<TransaksiSewa> listLaporan = (List<TransaksiSewa>) request.getAttribute("listLaporan");
    List<Mobil> listMobil = (List<Mobil>) request.getAttribute("listMobil");
    long totalPendapatan = request.getAttribute("totalPendapatan") != null ? (long)request.getAttribute("totalPendapatan") : 0;
    int totalTransaksi = request.getAttribute("totalTransaksi") != null ? (int)request.getAttribute("totalTransaksi") : 0;
    long avgLamaSewa = request.getAttribute("avgLamaSewa") != null ? (long)request.getAttribute("avgLamaSewa") : 0;
    String dari = request.getAttribute("dari") != null ? (String)request.getAttribute("dari") : "";
    String sampai = request.getAttribute("sampai") != null ? (String)request.getAttribute("sampai") : "";
    String jenis = request.getAttribute("jenis") != null ? (String)request.getAttribute("jenis") : "Semua";
    String mobilId = request.getAttribute("mobilId") != null ? (String)request.getAttribute("mobilId") : "0";
    String namaUser = (String) session.getAttribute("namaUser");
%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Laporan Transaksi — RentCar</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <style>
        @media print {
            .sidebar, .topbar, .no-print { display:none !important; }
            .main-content { margin:0 !important; padding:16px !important; }
            .kop-surat { display:block !important; }
        }
        .kop-surat { display:none; border-bottom:2px solid #1a2e4a; padding-bottom:12px; margin-bottom:16px; }
        .kop-surat h2 { font-size:18px; color:#1a2e4a; }
        .kop-surat p { font-size:12px; color:#555; }
        .filter-card { background:#fff; border:1px solid #e8eaf0; border-radius:12px; padding:16px 20px; margin-bottom:16px; }
        .filter-row { display:flex; gap:12px; align-items:flex-end; flex-wrap:wrap; }
        .filter-row .form-group { margin:0; }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/sidebar.jsp"/>
<div class="topbar no-print">
    <div class="topbar-search"><span>🔍</span><input type="text" placeholder="Cari data laporan..."></div>
    <div style="flex:1"></div>
    <div class="topbar-icons"><a href="#">🔔</a><a href="#">⚙</a></div>
    <div class="user-chip">
        <div class="av"><%= namaUser != null ? namaUser.substring(0,1).toUpperCase() : "A" %></div>
        <span>Admin</span>
    </div>
</div>

<div class="main-content">
    <!-- Kop surat (hanya tampil saat print) -->
    <div class="kop-surat">
        <h2>🚗 RentCar System</h2>
        <p>Jl. Contoh No. 1, Kota — Telp: (021) 12345678</p>
        <h3 style="margin-top:8px;font-size:15px;">LAPORAN TRANSAKSI PENYEWAAN MOBIL</h3>
        <p>Periode: <%= dari %> s/d <%= sampai %> &nbsp;|&nbsp; Dicetak: <%= new java.util.Date() %></p>
    </div>

    <div style="display:flex;align-items:center;justify-content:space-between;margin-bottom:16px;" class="no-print">
        <div>
            <div style="font-size:12px;color:#718096;">Laporan / Transaksi Penyewaan</div>
            <h2 style="font-size:20px;font-weight:700;color:#1a2e4a;">Laporan Transaksi Penyewaan</h2>
        </div>
        <div style="display:flex;gap:10px;">
            <button class="btn btn-outline" onclick="window.print()">🖨 Print / PDF</button>
            <button class="btn btn-success" onclick="exportExcel()">📊 Export Excel</button>
        </div>
    </div>

    <!-- Filter -->
    <div class="filter-card no-print">
        <form method="get" action="<%= request.getContextPath() %>/laporan">
            <div class="filter-row">
                <div class="form-group">
                    <label>Periode Dari</label>
                    <input type="date" name="dari" class="form-control" value="<%= dari %>" style="width:150px;">
                </div>
                <div class="form-group">
                    <label>Sampai</label>
                    <input type="date" name="sampai" class="form-control" value="<%= sampai %>" style="width:150px;">
                </div>
                <div class="form-group">
                    <label>Jenis Laporan</label>
                    <select name="jenis" class="form-control" style="width:160px;">
                        <option value="Semua" <%= "Semua".equals(jenis)?"selected":"" %>>Semua</option>
                        <option value="Aktif" <%= "Aktif".equals(jenis)?"selected":"" %>>Penyewaan Aktif</option>
                        <option value="Selesai" <%= "Selesai".equals(jenis)?"selected":"" %>>Sudah Dikembalikan</option>
                        <option value="Terlambat" <%= "Terlambat".equals(jenis)?"selected":"" %>>Terlambat</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Mobil</label>
                    <select name="mobil_id" class="form-control" style="width:180px;">
                        <option value="0">Semua</option>
                        <% if (listMobil != null) for (Mobil m : listMobil) { %>
                        <option value="<%= m.getId() %>" <%= String.valueOf(m.getId()).equals(mobilId) ? "selected" : "" %>><%= m.getNamaMobil() %></option>
                        <% } %>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary" style="height:38px;">🔍 Tampilkan</button>
            </div>
        </form>
    </div>

    <% if (listLaporan != null) { %>
    <!-- Summary Cards -->
    <div class="stat-cards" style="grid-template-columns:repeat(3,1fr);margin-bottom:16px;">
        <div class="stat-card">
            <div class="stat-icon blue">📋</div>
            <div class="stat-info"><p>Total Transaksi</p><h2><%= totalTransaksi %></h2></div>
        </div>
        <div class="stat-card">
            <div class="stat-icon green">💰</div>
            <div class="stat-info"><p>Total Pendapatan</p><h2 style="font-size:18px;">Rp <%= String.format("%,d", totalPendapatan).replace(",",".") %></h2></div>
        </div>
        <div class="stat-card">
            <div class="stat-icon teal">📅</div>
            <div class="stat-info"><p>Rata-rata Lama Sewa</p><h2><%= avgLamaSewa %> Hari</h2></div>
        </div>
    </div>

    <!-- Tabel Laporan -->
    <div class="card">
        <div class="card-body">
            <table id="laporanTable">
                <thead>
                    <tr>
                        <th>NO</th>
                        <th>NO. TRANSAKSI</th>
                        <th>NAMA CUSTOMER</th>
                        <th>MOBIL</th>
                        <th>TGL SEWA</th>
                        <th>TGL KEMBALI</th>
                        <th>LAMA SEWA</th>
                        <th>TOTAL BIAYA</th>
                        <th>STATUS</th>
                    </tr>
                </thead>
                <tbody>
                <% if (!listLaporan.isEmpty()) {
                    int i = 1;
                    for (TransaksiSewa t : listLaporan) { %>
                <tr>
                    <td><%= i++ %></td>
                    <td><strong><%= t.getNoTransaksi() %></strong></td>
                    <td><%= t.getNamaCustomer() %></td>
                    <td><%= t.getNamaMobil() %> — <%= t.getNoPlat() %></td>
                    <td><%= t.getTanggalSewa() %></td>
                    <td><%= t.getTanggalKembaliRencana() %></td>
                    <td><%= t.getLamaSewa() %> Hari</td>
                    <td>Rp <%= String.format("%,d", t.getTotalBiaya()).replace(",",".") %></td>
                    <td>
                        <% String st = t.getStatus();
                           String bc = "Aktif".equals(st) ? "badge-info" : "Selesai".equals(st) ? "badge-success" : "badge-danger"; %>
                        <span class="badge <%= bc %>"><%= st %></span>
                    </td>
                </tr>
                <% } } else { %>
                <tr><td colspan="9" style="text-align:center;padding:32px;color:#a0aec0;">Tidak ada data pada periode ini</td></tr>
                <% } %>
                </tbody>
                <tfoot>
                    <tr style="background:#f8f9fc;font-weight:600;">
                        <td colspan="7" style="padding:12px 16px;text-align:right;font-size:13px;color:#1a2e4a;">Total Pendapatan (Periode Ini):</td>
                        <td style="padding:12px 16px;font-size:14px;color:#1a2e4a;font-weight:700;">Rp <%= String.format("%,d", totalPendapatan).replace(",",".") %></td>
                        <td></td>
                    </tr>
                </tfoot>
            </table>
            <div style="padding:12px 16px;font-size:12px;color:#718096;">
                Menampilkan 1–<%= listLaporan.size() %> dari <%= listLaporan.size() %> data
            </div>
        </div>
    </div>
    <% } %>
</div>

<script>
function exportExcel() {
    var table = document.getElementById('laporanTable');
    if (!table) { alert('Tampilkan data laporan terlebih dahulu!'); return; }
    var html = '<html><head><meta charset="UTF-8"></head><body>' + table.outerHTML + '</body></html>';
    var blob = new Blob([html], {type:'application/vnd.ms-excel;charset=utf-8'});
    var url = URL.createObjectURL(blob);
    var a = document.createElement('a');
    a.href = url;
    a.download = 'Laporan_RentCar_<%= dari %>_<%= sampai %>.xls';
    a.click();
    URL.revokeObjectURL(url);
}
</script>
</body>
</html>
