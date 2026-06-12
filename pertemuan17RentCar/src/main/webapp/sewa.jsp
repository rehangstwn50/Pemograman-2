<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.rentcar.model.Mobil, com.rentcar.model.Customer" %>
<%
    request.setAttribute("currentPage","sewa");
    List<Customer> listCustomer = (List<Customer>) request.getAttribute("listCustomer");
    List<Mobil> listMobil = (List<Mobil>) request.getAttribute("listMobil");
    String nextNo = (String) request.getAttribute("nextNo");
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
    <title>Transaksi Sewa — RentCar</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <style>
        .form-page { background:#fff; border:1px solid #e8eaf0; border-radius:12px; padding:24px; }
        .summary-row { display:flex; gap:16px; margin-top:16px; }
        .summary-row .sum-left { flex:1; }
        .summary-row .sum-right { width:240px; flex-shrink:0; }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/sidebar.jsp"/>
<div class="topbar">
    <div class="topbar-search">
        <span>🔍</span>
        <input type="text" placeholder="Cari transaksi...">
    </div>
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

    <div style="margin-bottom:16px;">
        <h2 style="font-size:20px;font-weight:700;color:#1a2e4a;">Transaksi Penyewaan Mobil</h2>
        <p style="font-size:13px;color:#718096;">Input data penyewaan kendaraan baru dalam sistem.</p>
    </div>

    <form method="post" action="<%= request.getContextPath() %>/sewa" id="sewaForm">
        <!-- Section 1: Data Penyewaan -->
        <div class="form-page" style="margin-bottom:16px;">
            <div class="section-title">Data Penyewaan</div>
            <div class="form-grid-2">
                <div class="form-group">
                    <label>No. Transaksi</label>
                    <input type="text" name="no_transaksi" class="form-control" value="<%= nextNo != null ? nextNo : "" %>" readonly style="background:#f5f6fa;font-weight:600;color:#1a2e4a;">
                </div>
                <div class="form-group">
                    <label>Tanggal Sewa</label>
                    <input type="date" name="tanggal_sewa" id="tglSewa" class="form-control" value="<%= today %>" required onchange="hitungLama()">
                </div>
                <div class="form-group">
                    <label>Tanggal Kembali</label>
                    <input type="date" name="tanggal_kembali" id="tglKembali" class="form-control" required onchange="hitungLama()">
                </div>
                <div class="form-group">
                    <label>Lama Sewa</label>
                    <input type="text" id="lamaSewaTxt" class="form-control" value="0 Hari" readonly style="background:#e3f2fd;color:#1565c0;font-weight:600;">
                    <input type="hidden" name="lama_sewa" id="lamaSewa" value="0">
                </div>
            </div>
        </div>

        <!-- Section 2: Pilih Customer -->
        <div class="form-page" style="margin-bottom:16px;">
            <div class="section-title">Pilih Customer</div>
            <div class="form-group">
                <label>Customer</label>
                <select name="customer_id" id="custSelect" class="form-control" required onchange="showCustomerInfo()">
                    <option value="">👤 Cari Nama atau Kode Customer</option>
                    <% if (listCustomer != null) for (Customer cu : listCustomer) { %>
                    <option value="<%= cu.getId() %>"
                        data-nama="<%= cu.getNamaLengkap() %>"
                        data-ktp="<%= cu.getNoKtp() %>"
                        data-telp="<%= cu.getNoTelepon() %>">
                        <%= cu.getKodeCustomer() %> — <%= cu.getNamaLengkap() %>
                    </option>
                    <% } %>
                </select>
            </div>
            <div class="info-box" id="custInfo" style="display:none;">
                <div style="display:grid;grid-template-columns:1fr 1fr 1fr;gap:12px;">
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">NAMA</div><div id="infoCustNama" style="font-weight:600;font-size:13px;">—</div></div>
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">NO. KTP</div><div id="infoCustKtp" style="font-weight:600;font-size:13px;">—</div></div>
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">TELEPON</div><div id="infoCustTelp" style="font-weight:600;font-size:13px;">—</div></div>
                </div>
            </div>
        </div>

        <!-- Section 3: Pilih Mobil -->
        <div class="form-page" style="margin-bottom:16px;">
            <div class="section-title">Pilih Mobil</div>
            <div class="form-group">
                <label>Mobil (Tersedia)</label>
                <select name="mobil_id" id="mobilSelect" class="form-control" required onchange="showMobilInfo()">
                    <option value="">🚗 Pilih Mobil (Tersedia)</option>
                    <% if (listMobil != null) for (Mobil m : listMobil) { %>
                    <option value="<%= m.getId() %>"
                        data-nama="<%= m.getNamaMobil() %>"
                        data-merk="<%= m.getMerk() %>"
                        data-plat="<%= m.getNoPlat() %>"
                        data-harga="<%= m.getHargaSewa() %>">
                        <%= m.getKodeMobil() %> — <%= m.getNamaMobil() %> (<%= m.getNoPlat() %>)
                    </option>
                    <% } %>
                </select>
            </div>
            <div class="info-box" id="mobilInfo" style="display:none;">
                <div style="display:grid;grid-template-columns:1fr 1fr 1fr 1fr;gap:12px;">
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">NAMA MOBIL</div><div id="infoMobilNama" style="font-weight:600;font-size:13px;">—</div></div>
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">MERK</div><div id="infoMobilMerk" style="font-weight:600;font-size:13px;">—</div></div>
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">NO. PLAT</div><div id="infoMobilPlat" style="font-weight:600;font-size:13px;">—</div></div>
                    <div><div style="font-size:11px;color:#718096;text-transform:uppercase;margin-bottom:3px;">HARGA/HARI</div><div id="infoMobilHarga" style="font-weight:600;font-size:13px;color:#1a2e4a;">Rp 0</div></div>
                </div>
            </div>
        </div>

        <!-- Section 4: Keterangan & Summary -->
        <div class="form-page">
            <div class="section-title">Keterangan Tambahan</div>
            <div class="summary-row">
                <div class="sum-left">
                    <div class="form-group">
                        <label>Uang Jaminan (Deposit)</label>
                        <input type="number" name="uang_jaminan" class="form-control" placeholder="Rp 0" min="0">
                    </div>
                    <div class="form-group">
                        <label>Catatan / Keterangan</label>
                        <textarea name="catatan" class="form-control" rows="3" placeholder="Tambahkan instruksi khusus atau kondisi awal mobil..."></textarea>
                    </div>
                </div>
                <div class="sum-right">
                    <div class="summary-box">
                        <p>TOTAL ESTIMASI BIAYA</p>
                        <h2 id="totalBiayaTxt">Rp 0</h2>
                        <input type="hidden" name="total_biaya" id="totalBiaya" value="0">
                        <div style="margin-top:10px;padding:6px 12px;background:rgba(255,255,255,0.12);border-radius:6px;font-size:12px;text-align:center;">
                            Durasi Sewa: <span id="durasiLabel">0 Hari</span>
                        </div>
                    </div>
                </div>
            </div>
            <div style="display:flex;justify-content:flex-end;gap:12px;margin-top:20px;">
                <button type="reset" class="btn btn-secondary" onclick="resetForm()">Reset Form</button>
                <button type="submit" class="btn btn-success">✅ Proses Penyewaan</button>
            </div>
        </div>

        <div style="margin-top:12px;font-size:12px;color:#718096;display:flex;align-items:center;gap:6px;">
            ℹ Data transaksi akan dikunci setelah diproses dan status mobil akan berubah menjadi
            <span class="badge badge-warning" style="font-size:11px;">Disewa</span>
        </div>
    </form>
</div>

<script>
var hargaSewa = 0;

function hitungLama() {
    var tglS = document.getElementById('tglSewa').value;
    var tglK = document.getElementById('tglKembali').value;
    if (tglS && tglK) {
        var d1 = new Date(tglS), d2 = new Date(tglK);
        var diff = Math.ceil((d2 - d1) / (1000*60*60*24));
        if (diff > 0) {
            document.getElementById('lamaSewa').value = diff;
            document.getElementById('lamaSewaTxt').value = diff + ' Hari';
            document.getElementById('durasiLabel').textContent = diff + ' Hari';
            hitungTotal(diff);
        } else {
            document.getElementById('lamaSewa').value = 0;
            document.getElementById('lamaSewaTxt').value = '0 Hari';
        }
    }
}

function hitungTotal(lama) {
    var total = hargaSewa * lama;
    document.getElementById('totalBiaya').value = total;
    document.getElementById('totalBiayaTxt').textContent = 'Rp ' + total.toLocaleString('id-ID');
}

function showCustomerInfo() {
    var sel = document.getElementById('custSelect');
    var opt = sel.options[sel.selectedIndex];
    if (opt && opt.value) {
        document.getElementById('infoCustNama').textContent = opt.dataset.nama;
        document.getElementById('infoCustKtp').textContent = opt.dataset.ktp;
        document.getElementById('infoCustTelp').textContent = opt.dataset.telp;
        document.getElementById('custInfo').style.display = 'block';
    } else {
        document.getElementById('custInfo').style.display = 'none';
    }
}

function showMobilInfo() {
    var sel = document.getElementById('mobilSelect');
    var opt = sel.options[sel.selectedIndex];
    if (opt && opt.value) {
        hargaSewa = parseInt(opt.dataset.harga) || 0;
        document.getElementById('infoMobilNama').textContent = opt.dataset.nama;
        document.getElementById('infoMobilMerk').textContent = opt.dataset.merk;
        document.getElementById('infoMobilPlat').textContent = opt.dataset.plat;
        document.getElementById('infoMobilHarga').textContent = 'Rp ' + hargaSewa.toLocaleString('id-ID');
        document.getElementById('mobilInfo').style.display = 'block';
        var lama = parseInt(document.getElementById('lamaSewa').value) || 0;
        if (lama > 0) hitungTotal(lama);
    } else {
        document.getElementById('mobilInfo').style.display = 'none';
        hargaSewa = 0;
    }
}

function resetForm() {
    hargaSewa = 0;
    document.getElementById('custInfo').style.display = 'none';
    document.getElementById('mobilInfo').style.display = 'none';
    document.getElementById('totalBiayaTxt').textContent = 'Rp 0';
    document.getElementById('lamaSewaTxt').value = '0 Hari';
    document.getElementById('durasiLabel').textContent = '0 Hari';
}
</script>
</body>
</html>
