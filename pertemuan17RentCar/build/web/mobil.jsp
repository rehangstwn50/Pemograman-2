<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.rentcar.model.Mobil" %>
<%
    request.setAttribute("currentPage","mobil");
    List<Mobil> listMobil = (List<Mobil>) request.getAttribute("listMobil");
    Mobil editMobil = (Mobil) request.getAttribute("editMobil");
    String nextKode = (String) request.getAttribute("nextKode");
    String namaUser = (String) session.getAttribute("namaUser");
%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Data Mobil — RentCar</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/sidebar.jsp"/>
<div class="topbar">
    <div class="topbar-search">
        <span>🔍</span>
        <input type="text" placeholder="Search data..." id="searchInput" onkeyup="filterTable()">
    </div>
    <div style="flex:1"></div>
    <div class="topbar-icons"><a href="#" title="Notif">🔔</a><a href="#" title="Settings">⚙</a></div>
    <div class="user-chip">
        <div class="av"><%= namaUser != null ? namaUser.substring(0,1).toUpperCase() : "A" %></div>
        <span>Admin</span>
    </div>
</div>

<div class="main-content">
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">⚠ <%= request.getAttribute("error") %></div>
    <% } %>

    <div class="card">
        <div class="card-header">
            <div>
                <h3>Data Mobil</h3>
                <div style="font-size:12px;color:#718096;margin-top:2px;">Dashboard / Data Mobil</div>
            </div>
            <div style="display:flex;gap:10px;align-items:center;">
                <input type="text" class="form-control" id="tblSearch" placeholder="Cari mobil..." onkeyup="filterTable()" style="width:180px;">
                <button class="btn btn-primary" onclick="openModal()">+ Tambah Mobil</button>
            </div>
        </div>
        <div class="card-body">
            <table id="mobilTable">
                <thead>
                    <tr>
                        <th>NO</th><th>KODE</th><th>NAMA MOBIL</th><th>MERK</th>
                        <th>TAHUN</th><th>WARNA</th><th>HARGA SEWA (RP)</th><th>STATUS</th><th>AKSI</th>
                    </tr>
                </thead>
                <tbody>
                <% if (listMobil != null) {
                    int i = 1;
                    for (Mobil m : listMobil) { %>
                <tr>
                    <td><%= i++ %></td>
                    <td><strong><%= m.getKodeMobil() %></strong></td>
                    <td><%= m.getNamaMobil() %></td>
                    <td><%= m.getMerk() %></td>
                    <td><%= m.getTahun() %></td>
                    <td><%= m.getWarna() %></td>
                    <td>Rp <%= String.format("%,d", m.getHargaSewa()).replace(",",".") %></td>
                    <td>
                        <% String st = m.getStatus();
                           String bc = "Tersedia".equals(st) ? "badge-success" : "Disewa".equals(st) ? "badge-warning" : "badge-secondary"; %>
                        <span class="badge <%= bc %>"><%= st %></span>
                    </td>
                    <td>
                        <button class="btn btn-info btn-sm" onclick="editMobil(<%= m.getId() %>,'<%= m.getKodeMobil() %>','<%= m.getNamaMobil().replace("'","\\'") %>','<%= m.getMerk() %>',<%= m.getTahun() %>,'<%= m.getWarna() %>','<%= m.getNoPlat() %>',<%= m.getKapasitas() %>,<%= m.getHargaSewa() %>,'<%= m.getStatus() %>')">✏ Edit</button>
                        <a href="<%= request.getContextPath() %>/mobil?action=delete&id=<%= m.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Hapus mobil ini?')">🗑 Hapus</a>
                    </td>
                </tr>
                <% } } %>
                </tbody>
            </table>
            <div style="padding:12px 16px;font-size:12px;color:#718096;">
                Menampilkan <%= listMobil != null ? listMobil.size() : 0 %> mobil
            </div>
        </div>
    </div>
</div>

<!-- Modal Tambah/Edit Mobil -->
<div class="modal-overlay" id="mobilModal">
    <div class="modal">
        <div class="modal-header">
            <h4 id="modalTitle">Tambah Data Mobil</h4>
            <button class="modal-close" onclick="closeModal()">✕</button>
        </div>
        <form method="post" action="<%= request.getContextPath() %>/mobil">
            <div class="modal-body">
                <input type="hidden" name="action" id="formAction" value="insert">
                <input type="hidden" name="id" id="fieldId">
                <div class="form-grid-2">
                    <div class="form-group">
                        <label>Kode Mobil</label>
                        <input type="text" name="kode_mobil" id="fieldKode" class="form-control" value="<%= nextKode != null ? nextKode : "" %>" readonly>
                    </div>
                    <div class="form-group">
                        <label>Nama Mobil</label>
                        <input type="text" name="nama_mobil" id="fieldNama" class="form-control" placeholder="Masukkan nama mobil" required>
                    </div>
                    <div class="form-group">
                        <label>Merk/Brand</label>
                        <select name="merk" id="fieldMerk" class="form-control">
                            <option value="">Pilih Merk</option>
                            <option value="Toyota">Toyota</option>
                            <option value="Honda">Honda</option>
                            <option value="Mitsubishi">Mitsubishi</option>
                            <option value="Suzuki">Suzuki</option>
                            <option value="Hyundai">Hyundai</option>
                            <option value="Daihatsu">Daihatsu</option>
                            <option value="Nissan">Nissan</option>
                            <option value="Mazda">Mazda</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Tahun</label>
                        <input type="number" name="tahun" id="fieldTahun" class="form-control" value="2024" min="2000" max="2030" required>
                    </div>
                    <div class="form-group">
                        <label>Warna</label>
                        <input type="text" name="warna" id="fieldWarna" class="form-control" placeholder="Contoh: Merah" required>
                    </div>
                    <div class="form-group">
                        <label>No. Plat</label>
                        <input type="text" name="no_plat" id="fieldPlat" class="form-control" placeholder="B 1234 ABC" required>
                    </div>
                    <div class="form-group">
                        <label>Kapasitas</label>
                        <input type="number" name="kapasitas" id="fieldKap" class="form-control" value="5" min="2" max="20" required>
                    </div>
                    <div class="form-group">
                        <label>Harga Sewa / Hari (Rp)</label>
                        <input type="number" name="harga_sewa" id="fieldHarga" class="form-control" placeholder="Rp" required>
                    </div>
                </div>
                <div class="form-group">
                    <label>Status Ketersediaan</label>
                    <div style="display:flex;gap:20px;margin-top:6px;">
                        <label style="display:flex;align-items:center;gap:6px;font-size:13px;font-weight:400;">
                            <input type="radio" name="status" value="Tersedia" checked> Tersedia
                        </label>
                        <label style="display:flex;align-items:center;gap:6px;font-size:13px;font-weight:400;">
                            <input type="radio" name="status" value="Tidak Tersedia"> Tidak Tersedia
                        </label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="closeModal()">Batal</button>
                <button type="submit" class="btn btn-primary">Simpan Data</button>
            </div>
        </form>
    </div>
</div>

<script>
function openModal() {
    document.getElementById('modalTitle').textContent = 'Tambah Data Mobil';
    document.getElementById('formAction').value = 'insert';
    document.getElementById('mobilModal').classList.add('show');
}
function closeModal() {
    document.getElementById('mobilModal').classList.remove('show');
}
function editMobil(id, kode, nama, merk, tahun, warna, plat, kap, harga, status) {
    document.getElementById('modalTitle').textContent = 'Edit Data Mobil';
    document.getElementById('formAction').value = 'update';
    document.getElementById('fieldId').value = id;
    document.getElementById('fieldKode').value = kode;
    document.getElementById('fieldNama').value = nama;
    document.getElementById('fieldMerk').value = merk;
    document.getElementById('fieldTahun').value = tahun;
    document.getElementById('fieldWarna').value = warna;
    document.getElementById('fieldPlat').value = plat;
    document.getElementById('fieldKap').value = kap;
    document.getElementById('fieldHarga').value = harga;
    var radios = document.querySelectorAll('input[name="status"]');
    radios.forEach(r => { if (r.value === status) r.checked = true; });
    document.getElementById('mobilModal').classList.add('show');
}
function filterTable() {
    var q = document.getElementById('tblSearch').value.toLowerCase();
    var rows = document.querySelectorAll('#mobilTable tbody tr');
    rows.forEach(r => {
        r.style.display = r.textContent.toLowerCase().includes(q) ? '' : 'none';
    });
}
</script>
</body>
</html>
