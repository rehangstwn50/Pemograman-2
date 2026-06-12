<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.rentcar.model.TransaksiSewa" %>
<%
    request.setAttribute("currentPage","kembali");
    List<TransaksiSewa> listAktif = (List<TransaksiSewa>) request.getAttribute("listAktif");
    TransaksiSewa transaksi = (TransaksiSewa) request.getAttribute("transaksi");
    String namaUser = (String) session.getAttribute("namaUser");
    String successMsg = (String) session.getAttribute("successMsg");
    String errorMsg = (String) session.getAttribute("errorMsg");
    if (successMsg != null) session.removeAttribute("successMsg");
    if (errorMsg != null) session.removeAttribute("errorMsg");
    java.time.LocalDate today = java.time.LocalDate.now();
%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Pengembalian Mobil — RentCar</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <style>
        .page-layout { display:grid; grid-template-columns:1fr 280px; gap:16px; align-items:start; }
        .ringkasan-card { background:#fff; border:1px solid #e8eaf0; border-radius:12px; overflow:hidden; position:sticky; top:72px; }
        .ringkasan-header { background:#1a2e4a; padding:16px 18px; }
        .ringkasan-header h4 { color:#fff; font-size:14px; font-weight:600; }
        .ringkasan-body { padding:16px 18px; }
        .ring-row { display:flex; justify-content:space-between; padding:8px 0; border-bottom:1px solid #f0f1f5; font-size:13px; }
        .ring-row:last-child { border:none; }
        .ring-row .label { color:#718096; }
        .ring-row .val { font-weight:500; color:#2d3748; }
        .ring-row.total .val { font-size:18px; font-weight:700; color:#1a2e4a; }
        .ring-row.denda .val { color:#c62828; }
        .form-card { background:#fff; border:1px solid #e8eaf0; border-radius:12px; padding:20px; margin-bottom:14px; }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/sidebar.jsp"/>
<div class="topbar">
    <div class="topbar-search"><span>🔍</span><input type="text" placeholder="Cari data pengembalian..."></div>
    <div style="flex:1"></div>
    <div class="topbar-icons"><a href="#">🔔</a><a href="#">⚙</a></div>
    <div class="user-chip">
        <div class="av"><%= namaUser != null ? namaUser.substring(0,1).toUpperCase() : "A" %></div>
        <span>Rental Admin</span>
    </div>
</div>

<div class="main-content">
    <% if (successMsg != null) { %><div class="alert alert-success">✅ <%= successMsg %></div><% } %>
    <% if (errorMsg != null) { %><div class="alert alert-danger">⚠ <%= errorMsg %></div><% } %>

    <div class="page-layout">
        <div>
            <!-- Step 1: Cari Transaksi -->
            <div class="form-card">
                <div class="section-title">Cari Transaksi Sewa</div>
                <p style="font-size:12px;color:#718096;margin-bottom:12px;">Cari berdasarkan Nomor Transaksi atau pilih dari daftar sewa aktif.</p>
                <form method="get" action="<%= request.getContextPath() %>/kembali" style="display:flex;gap:10px;align-items:flex-end;">
                    <div class="form-group" style="flex:1;margin:0;">
                        <label>No. Transaksi Sewa</label>
                        <input type="text" name="no_transaksi" class="form-control" placeholder="No. Transaksi Sewa (Contoh: TR-2024-042)"
                            value="<%= transaksi != null ? transaksi.getNoTransaksi() : "" %>">
                    </div>
                    <button type="submit" class="btn btn-primary" style="height:38px;">🔍 Cari</button>
                </form>
                <% if (listAktif != null && !listAktif.isEmpty()) { %>
                <div style="margin-top:14px;">
                    <label style="font-size:12px;color:#718096;font-weight:600;display:block;margin-bottom:6px;">ATAU PILIH DARI TRANSAKSI AKTIF</label>
                    <select class="form-control" onchange="if(this.value) window.location='<%= request.getContextPath() %>/kembali?no_transaksi='+this.value">
                        <option value="">— Pilih Transaksi Aktif —</option>
                        <% for (TransaksiSewa t : listAktif) { %>
                        <option value="<%= t.getNoTransaksi() %>" <%= transaksi != null && t.getNoTransaksi().equals(transaksi.getNoTransaksi()) ? "selected" : "" %>>
                            <%= t.getNoTransaksi() %> — <%= t.getNamaCustomer() %> — <%= t.getNamaMobil() %>
                        </option>
                        <% } %>
                    </select>
                </div>
                <% } %>
            </div>

            <% if (transaksi != null) { %>
            <!-- Step 2: Detail Transaksi -->
            <div class="form-card">
                <div class="section-title">Detail Transaksi</div>
                <div style="display:grid;grid-template-columns:1fr 1fr;gap:12px;">
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">NO. TRANSAKSI</div><div style="font-weight:700;color:#1a2e4a;"><%= transaksi.getNoTransaksi() %></div></div>
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">CUSTOMER</div><div style="font-weight:600;"><%= transaksi.getNamaCustomer() %></div></div>
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">MOBIL</div><div style="font-weight:600;"><%= transaksi.getNamaMobil() %> — <%= transaksi.getNoPlat() %></div></div>
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">TGL SEWA</div><div style="font-weight:600;"><%= transaksi.getTanggalSewa() %></div></div>
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">TGL KEMBALI RENCANA</div><div style="font-weight:600;"><%= transaksi.getTanggalKembaliRencana() %></div></div>
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">TOTAL BIAYA AWAL</div><div style="font-weight:700;color:#1a2e4a;">Rp <%= String.format("%,d", transaksi.getTotalBiaya()).replace(",",".") %></div></div>
                </div>
            </div>

            <!-- Step 3: Form Pengembalian -->
            <form method="post" action="<%= request.getContextPath() %>/kembali" id="kembaliForm">
                <input type="hidden" name="transaksi_id" value="<%= transaksi.getId() %>">
                <input type="hidden" name="mobil_id" value="<%= transaksi.getMobilId() %>">
                <input type="hidden" name="tanggal_kembali_rencana" value="<%= transaksi.getTanggalKembaliRencana() %>">
                <input type="hidden" name="total_biaya" value="<%= transaksi.getTotalBiaya() %>">
                <input type="hidden" name="uang_jaminan" value="<%= transaksi.getUangJaminan() %>">
                <input type="hidden" name="harga_sewa" id="hargaSewa"
                    value="<%= transaksi.getLamaSewa() > 0 ? transaksi.getTotalBiaya() / transaksi.getLamaSewa() : 0 %>">

                <div class="form-card">
                    <div class="section-title">Form Pengembalian</div>
                    <div class="form-grid-2">
                        <div class="form-group">
                            <label>Tanggal Kembali Aktual</label>
                            <input type="date" name="tanggal_kembali_aktual" id="tglAktual" class="form-control"
                                value="<%= today %>" required onchange="hitungDenda()">
                        </div>
                        <div class="form-group">
                            <label>Kondisi Mobil</label>
                            <div style="display:flex;gap:20px;margin-top:8px;">
                                <label style="display:flex;align-items:center;gap:6px;font-size:13px;font-weight:400;">
                                    <input type="radio" name="kondisi_mobil" value="Baik" checked onchange="toggleKerusakan(false)"> ✅ Baik
                                </label>
                                <label style="display:flex;align-items:center;gap:6px;font-size:13px;font-weight:400;">
                                    <input type="radio" name="kondisi_mobil" value="Ada Kerusakan" onchange="toggleKerusakan(true)"> ⚠ Ada Kerusakan
                                </label>
                            </div>
                        </div>
                    </div>

                    <!-- Notif keterlambatan -->
                    <div id="dendaAlert" style="display:none;" class="alert alert-warning">
                        ⚠ <strong>Keterlambatan Terdeteksi:</strong>
                        Terlambat <span id="terlambatHari">0</span> hari ×
                        Rp <span id="dendaPerHari">0</span> = <strong>Rp <span id="totalDenda">0</span></strong>
                    </div>
                    <input type="hidden" name="denda" id="hidDenda" value="0">

                    <!-- Form kerusakan -->
                    <div id="kerusakanSection" style="display:none;">
                        <div class="form-grid-2">
                            <div class="form-group">
                                <label>Biaya Kerusakan (Rp)</label>
                                <input type="number" name="biaya_kerusakan" id="biayaKerusakan" class="form-control"
                                    placeholder="0" min="0" value="0" onchange="hitungTotal()">
                            </div>
                            <div class="form-group">
                                <label>Keterangan Kerusakan</label>
                                <input type="text" name="keterangan_kerusakan" class="form-control" placeholder="Deskripsi kerusakan jika ada...">
                            </div>
                        </div>
                    </div>
                </div>

                <div style="display:flex;gap:12px;justify-content:flex-end;margin-top:4px;">
                    <a href="<%= request.getContextPath() %>/kembali" class="btn btn-secondary">Batal</a>
                    <button type="submit" class="btn btn-success" onclick="return confirm('Proses pengembalian ini?')">✅ Selesaikan Pengembalian</button>
                </div>

                <div style="margin-top:14px;font-size:12px;color:#718096;display:flex;align-items:center;gap:6px;">
                    ✅ <em>Mobil akan otomatis diubah statusnya menjadi <span class="badge badge-success" style="font-size:11px;">Tersedia</span> setelah transaksi ini diproses.</em>
                </div>
            </form>
            <% } %>
        </div>

        <!-- Ringkasan Pembayaran (Right Panel) -->
        <% if (transaksi != null) { %>
        <div class="ringkasan-card">
            <div class="ringkasan-header"><h4>Ringkasan Pembayaran</h4></div>
            <div class="ringkasan-body">
                <div class="ring-row">
                    <span class="label">Biaya Sewa</span>
                    <span class="val">Rp <%= String.format("%,d", transaksi.getTotalBiaya()).replace(",",".") %></span>
                </div>
                <div class="ring-row denda">
                    <span class="label">Denda Keterlambatan</span>
                    <span class="val" id="ringDenda">Rp 0</span>
                </div>
                <div class="ring-row">
                    <span class="label">Biaya Kerusakan</span>
                    <span class="val" id="ringKerusakan">Rp 0</span>
                </div>
                <div class="ring-row">
                    <span class="label">Uang Jaminan</span>
                    <span class="val" style="color:#2e7d32;">- Rp <%= String.format("%,d", transaksi.getUangJaminan()).replace(",",".") %></span>
                </div>
                <div class="ring-row total" style="margin-top:4px;padding-top:12px;border-top:2px solid #e8eaf0;">
                    <span class="label" style="font-weight:600;color:#1a2e4a;">Total Bayar</span>
                    <span class="val" id="ringTotal">Rp <%= String.format("%,d", transaksi.getTotalBiaya() - transaksi.getUangJaminan()).replace(",",".") %></span>
                </div>
            </div>
        </div>
        <% } else { %>
        <div class="ringkasan-card">
            <div class="ringkasan-header"><h4>Ringkasan Pembayaran</h4></div>
            <div class="ringkasan-body" style="text-align:center;padding:32px 16px;color:#a0aec0;">
                <div style="font-size:32px;margin-bottom:8px;">🔍</div>
                <p style="font-size:13px;">Cari transaksi terlebih dahulu untuk melihat ringkasan pembayaran</p>
            </div>
        </div>
        <% } %>
    </div>
</div>

<script>
var biayaSewa = <%= transaksi != null ? transaksi.getTotalBiaya() : 0 %>;
var jaminan = <%= transaksi != null ? transaksi.getUangJaminan() : 0 %>;
var tglRencana = '<%= transaksi != null ? transaksi.getTanggalKembaliRencana() : "" %>';
var hargaPerHari = parseInt(document.getElementById('hargaSewa') ? document.getElementById('hargaSewa').value : 0) || 0;

function hitungDenda() {
    if (!tglRencana) return;
    var aktual = new Date(document.getElementById('tglAktual').value);
    var rencana = new Date(tglRencana);
    var diff = Math.ceil((aktual - rencana) / (1000*60*60*24));
    var denda = diff > 0 ? diff * hargaPerHari : 0;
    document.getElementById('hidDenda').value = denda;
    if (diff > 0) {
        document.getElementById('terlambatHari').textContent = diff;
        document.getElementById('dendaPerHari').textContent = hargaPerHari.toLocaleString('id-ID');
        document.getElementById('totalDenda').textContent = denda.toLocaleString('id-ID');
        document.getElementById('dendaAlert').style.display = 'flex';
    } else {
        document.getElementById('dendaAlert').style.display = 'none';
    }
    hitungTotal();
}

function hitungTotal() {
    var denda = parseInt(document.getElementById('hidDenda').value) || 0;
    var kerusakan = 0;
    var bk = document.getElementById('biayaKerusakan');
    if (bk) kerusakan = parseInt(bk.value) || 0;
    var total = biayaSewa + denda + kerusakan - jaminan;
    if (document.getElementById('ringDenda'))
        document.getElementById('ringDenda').textContent = 'Rp ' + denda.toLocaleString('id-ID');
    if (document.getElementById('ringKerusakan'))
        document.getElementById('ringKerusakan').textContent = 'Rp ' + kerusakan.toLocaleString('id-ID');
    if (document.getElementById('ringTotal'))
        document.getElementById('ringTotal').textContent = 'Rp ' + total.toLocaleString('id-ID');
}

function toggleKerusakan(show) {
    document.getElementById('kerusakanSection').style.display = show ? 'block' : 'none';
    if (!show && document.getElementById('biayaKerusakan')) {
        document.getElementById('biayaKerusakan').value = 0;
        hitungTotal();
    }
}

// Hitung otomatis saat load
window.onload = function() { hitungDenda(); };
</script>
</body>
</html>
