<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.rentcar.model.Customer" %>
<%
    request.setAttribute("currentPage","customer");
    List<Customer> listCustomer = (List<Customer>) request.getAttribute("listCustomer");
    String nextKode = (String) request.getAttribute("nextKode");
    String namaUser = (String) session.getAttribute("namaUser");
%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Data Customer — RentCar</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/sidebar.jsp"/>
<div class="topbar">
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
            <h3>Data Customer</h3>
            <div style="display:flex;gap:10px;align-items:center;">
                <input type="text" class="form-control" id="tblSearch" placeholder="Cari customer..." onkeyup="filterTable()" style="width:200px;">
                <button class="btn btn-primary" onclick="openModal()">+ Tambah Customer</button>
            </div>
        </div>
        <div class="card-body">
            <table id="custTable">
                <thead>
                    <tr>
                        <th>NO</th><th>KODE</th><th>NAMA LENGKAP</th><th>NO. KTP</th>
                        <th>NO. TELEPON</th><th>ALAMAT</th><th>AKSI</th>
                    </tr>
                </thead>
                <tbody>
                <% if (listCustomer != null) {
                    int i = 1;
                    for (Customer cu : listCustomer) {
                        String alamat = cu.getAlamat() != null && cu.getAlamat().length() > 40
                            ? cu.getAlamat().substring(0,40) + "..." : cu.getAlamat(); %>
                <tr>
                    <td><%= i++ %></td>
                    <td><strong><%= cu.getKodeCustomer() %></strong></td>
                    <td><%= cu.getNamaLengkap() %></td>
                    <td><%= cu.getNoKtp() %></td>
                    <td><%= cu.getNoTelepon() %></td>
                    <td title="<%= cu.getAlamat() %>"><%= alamat %></td>
                    <td>
                        <button class="btn btn-info btn-sm" onclick="editCustomer(<%= cu.getId() %>,'<%= cu.getKodeCustomer() %>','<%= cu.getNamaLengkap().replace("'","\\'") %>','<%= cu.getNoKtp() %>','<%= cu.getTempatLahir() != null ? cu.getTempatLahir() : "" %>','<%= cu.getTanggalLahir() %>','<%= cu.getJenisKelamin() %>','<%= cu.getNoTelepon() %>','<%= cu.getEmail() != null ? cu.getEmail() : "" %>','<%= cu.getAlamat() != null ? cu.getAlamat().replace("'","\\'") : "" %>')">✏</button>
                        <a href="<%= request.getContextPath() %>/customer?action=delete&id=<%= cu.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Hapus customer ini?')">🗑</a>
                    </td>
                </tr>
                <% } } %>
                </tbody>
            </table>
            <div style="padding:12px 16px;font-size:12px;color:#718096;">
                Menampilkan <%= listCustomer != null ? listCustomer.size() : 0 %> customer
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal-overlay" id="custModal">
    <div class="modal" style="width:580px;">
        <div class="modal-header">
            <h4 id="modalTitle">Tambah Data Customer</h4>
            <button class="modal-close" onclick="closeModal()">✕</button>
        </div>
        <form method="post" action="<%= request.getContextPath() %>/customer">
            <div class="modal-body">
                <input type="hidden" name="action" id="formAction" value="insert">
                <input type="hidden" name="id" id="fieldId">
                <div class="form-grid-2">
                    <div class="form-group">
                        <label>Kode Customer</label>
                        <input type="text" name="kode_customer" id="fieldKode" class="form-control" value="<%= nextKode != null ? nextKode : "" %>" readonly>
                    </div>
                    <div class="form-group">
                        <label>Nama Lengkap</label>
                        <input type="text" name="nama_lengkap" id="fieldNama" class="form-control" placeholder="Masukkan nama lengkap" required>
                    </div>
                    <div class="form-group">
                        <label>No. KTP (16 Digit)</label>
                        <input type="text" name="no_ktp" id="fieldKtp" class="form-control" placeholder="3275012345670001" maxlength="16" required>
                    </div>
                    <div class="form-group">
                        <label>Tempat Lahir</label>
                        <input type="text" name="tempat_lahir" id="fieldTempat" class="form-control" placeholder="Contoh: Jakarta">
                    </div>
                    <div class="form-group">
                        <label>Tanggal Lahir</label>
                        <input type="date" name="tanggal_lahir" id="fieldTgl" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Jenis Kelamin</label>
                        <div style="display:flex;gap:16px;margin-top:8px;">
                            <label style="display:flex;align-items:center;gap:6px;font-size:13px;font-weight:400;">
                                <input type="radio" name="jenis_kelamin" value="Laki-laki" id="jkL"> Laki-laki
                            </label>
                            <label style="display:flex;align-items:center;gap:6px;font-size:13px;font-weight:400;">
                                <input type="radio" name="jenis_kelamin" value="Perempuan" id="jkP"> Perempuan
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>No. Telepon</label>
                        <input type="text" name="no_telepon" id="fieldTelp" class="form-control" placeholder="08xx-xxxx-xxxx" required>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email" id="fieldEmail" class="form-control" placeholder="example@mail.com">
                    </div>
                </div>
                <div class="form-group">
                    <label>Alamat Lengkap</label>
                    <textarea name="alamat" id="fieldAlamat" class="form-control" rows="2" placeholder="Jl. Nama Jalan No. XX, Kecamatan, Kota..."></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="closeModal()">Batal</button>
                <button type="submit" class="btn btn-primary">Simpan</button>
            </div>
        </form>
    </div>
</div>

<script>
function openModal() {
    document.getElementById('modalTitle').textContent = 'Tambah Data Customer';
    document.getElementById('formAction').value = 'insert';
    document.getElementById('custModal').classList.add('show');
}
function closeModal() {
    document.getElementById('custModal').classList.remove('show');
}
function editCustomer(id,kode,nama,ktp,tempat,tgl,jk,telp,email,alamat) {
    document.getElementById('modalTitle').textContent = 'Edit Data Customer';
    document.getElementById('formAction').value = 'update';
    document.getElementById('fieldId').value = id;
    document.getElementById('fieldKode').value = kode;
    document.getElementById('fieldNama').value = nama;
    document.getElementById('fieldKtp').value = ktp;
    document.getElementById('fieldTempat').value = tempat;
    document.getElementById('fieldTgl').value = tgl;
    if (jk === 'Laki-laki') document.getElementById('jkL').checked = true;
    else document.getElementById('jkP').checked = true;
    document.getElementById('fieldTelp').value = telp;
    document.getElementById('fieldEmail').value = email;
    document.getElementById('fieldAlamat').value = alamat;
    document.getElementById('custModal').classList.add('show');
}
function filterTable() {
    var q = document.getElementById('tblSearch').value.toLowerCase();
    document.querySelectorAll('#custTable tbody tr').forEach(r => {
        r.style.display = r.textContent.toLowerCase().includes(q) ? '' : 'none';
    });
}
</script>
</body>
</html>
